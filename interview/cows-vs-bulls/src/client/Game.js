const React = require('react');
const PropTypes = require('prop-types');

class Game extends React.Component {
  constructor(props) {
    super(props);

    this.handleInput = this.handleInput.bind(this);
    this.handleRequestInput = this.handleRequestInput.bind(this);

    this._inputRequested = false;
    this._onInputRequest = null;
  }

  componentDidUpdate(){
    this.logElm.scrollTo(0, this.logElm.scrollHeight);
  }

  handleInput(e) {
    if(e.keyCode === 13) {
      if(this._inputRequested) {
        const value = e.target.value;
        this._onInputRequest(value);
        this.props.onAddLog(value);

        this._inputRequested = false;
        this._onInputRequest = null;

        e.target.value = '';
      }
    }
  }

  handleRequestInput() {
    if(this._inputRequested) {
      throw new Error(`Input is already requested`);
    }

    this._inputRequested = true;
    return new Promise((resolve, reject) => {
      this._onInputRequest = resolve;
    });
  }

  render() {
    const log = this.props.log.map((msg, i) => (
      <li key={i} className="game--log--item">
        {msg}
      </li>
    ));

    return (
      <div className="game">
        <ul
          className="game--log"
          ref={(logElm) => { this.logElm = logElm; }}
        >
          {log}
        </ul>
        <input
          type="number"
          onKeyUp={this.handleInput}
          disabled={this.props.demo}
          placeholder="Choose a number and click ENTER"
        />
        <button onClick={this.props.onRestart}>Restart</button>
      </div>
    )
  }
}

Game.propTypes = {
  log: PropTypes.array.isRequired,
  onAddLog: PropTypes.func.isRequired,
  onRestart: PropTypes.func.isRequired,
  demo: PropTypes.bool,
};

module.exports = Game;
