import React from 'react';

const PageHeader = () => {
  return (
    <header>
      <div>
        <div className="headerrow" align="center">
          <span>Know your favourite football team standing</span>
        </div>
        <img
          className="imgclass"
          src="football_splash.jpeg"
          alt="Splash"
          width="100%"
        />
      </div>
    </header>
  );
};

export default PageHeader;
