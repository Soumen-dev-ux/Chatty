import React, { useState } from 'react';
import ChatInterface from './components/ChatInterface';
import SentimentAnalyzer from './components/SentimentAnalyzer';
import './App.css';

function App() {
  const [currentView, setCurrentView] = useState('chat');

  return (
    <div className="App">
      <header className="App-header">
        <h1>Chatty</h1>
        <nav>
          <button 
            onClick={() => setCurrentView('chat')}
            className={currentView === 'chat' ? 'active' : ''}
          >
            Chat
          </button>
          <button 
            onClick={() => setCurrentView('sentiment')}
            className={currentView === 'sentiment' ? 'active' : ''}
          >
            Sentiment Analysis
          </button>
        </nav>
      </header>
      <main>
        {currentView === 'chat' ? <ChatInterface /> : <SentimentAnalyzer />}
      </main>
    </div>
  );
}

export default App;