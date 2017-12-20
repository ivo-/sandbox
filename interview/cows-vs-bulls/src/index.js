// Using commonjs modules for consistency
const React = require('react');
const ReactDOM = require('react-dom');

const App = require('./client/App');

require('./client/css/index.css');


const init = module.exports = function() {
  ReactDOM.render(
    <App />,
    document.getElementById('root')
  );
}

init();
