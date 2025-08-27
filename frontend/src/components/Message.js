import React from 'react';

const Message = ({ message }) => {
  return (
    <div className={`message ${message.role}-message`}>
      <strong>{message.role === 'user' ? 'You' : 'AI'}:</strong> {message.content}
    </div>
  );
};

export default Message;