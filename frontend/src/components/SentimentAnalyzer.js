import React, { useState, useEffect } from 'react';
import * as use from '@tensorflow-models/universal-sentence-encoder';
import * as tf from '@tensorflow/tfjs';

const SentimentAnalyzer = () => {
  const [model, setModel] = useState(null);
  const [text, setText] = useState('');
  const [sentiment, setSentiment] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    loadModel();
  }, []);

  const loadModel = async () => {
    setIsLoading(true);
    try {
      const loadedModel = await use.load();
      setModel(loadedModel);
    } catch (error) {
      console.error('Error loading model:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const analyzeSentiment = async () => {
    if (!model || !text.trim()) return;

    setIsLoading(true);
    try {
      const embeddings = await model.embed([text]);
      const prediction = embeddings.mean().arraySync();
      
      // Simple sentiment analysis based on embedding values
      const score = prediction[0];
      let sentimentResult = 'Neutral';
      
      if (score > 0.6) sentimentResult = 'Very Positive';
      else if (score > 0.3) sentimentResult = 'Positive';
      else if (score < -0.6) sentimentResult = 'Very Negative';
      else if (score < -0.3) sentimentResult = 'Negative';
      
      setSentiment(sentimentResult);
    } catch (error) {
      console.error('Error analyzing sentiment:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="sentiment-container">
      <h2>Sentiment Analyser</h2>
      <textarea
        className="sentiment-textarea"
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Enter text to analyze sentiment"
        disabled={isLoading || !model}
      />
      <button 
        onClick={analyzeSentiment} 
        disabled={isLoading || !model || !text.trim()}
      >
        {isLoading ? 'Analyzing...' : 'Analyze Sentiment'}
      </button>
      
      {sentiment && (
        <div className="sentiment-result">
          <h3>Sentiment: {sentiment}</h3>
        </div>
      )}
      
      {!model && !isLoading && (
        <p>Loading TensorFlow model...</p>
      )}
    </div>
  );
};

export default SentimentAnalyzer;