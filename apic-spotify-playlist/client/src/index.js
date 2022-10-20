import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <div className='fuzzy' style={{ 
    backgroundImage: "url(/img/pour4.JPG)",
    backgroundSize: "cover",
    height: 1300
   }}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </div>
);
