const React = require('react');

const { SUPPORTED_GAMES } = require('../shared/consts');
const SelectGame = require('./SelectGame');
const GameVsServer = require('./GameVsServer');
const P2PGame = require('./P2PGame');


function getInitalState() {
  return {
    game: null,
    log: [],
  };
}

function selectGame(state, game) {
  if(!SUPPORTED_GAMES[game]) {
    throw new Error(`Unsupported game: ${game}`);
  }

  return { ...state, game };
}

function logAction(state, action) {
  return { ...state, log: [...state.log, action] };
}

class App extends React.Component {
  constructor(props) {
    super(props);

    this.handleAddLog = this.handleAddLog.bind(this);
    this.handleSelectGame = this.handleSelectGame.bind(this);
    this.handleRestartGame = this.handleRestartGame.bind(this);

    this.state = getInitalState();
  }

  handleRestartGame(game) {
    this.setState(getInitalState());
  }

  handleSelectGame(game) {
    this.setState(selectGame(this.state, game));
  }

  handleAddLog(action) {
    this.setState(logAction(this.state, action));
  }

  renderGame() {
    if(!this.state.game) {
      return <SelectGame onSelectGame={this.handleSelectGame} />;
    }

    if(this.state.game === 'demo') {
      return <GameVsServer
        demo={true}
        log={this.state.log}
        onAddLog={this.handleAddLog}
        onRestart={this.handleRestartGame}
      />;
    }

    if(this.state.game === 'single') {
      return <GameVsServer
        demo={false}
        log={this.state.log}
        onAddLog={this.handleAddLog}
        onRestart={this.handleRestartGame}
      />;
    }

    if(this.state.game === 'p2p') {
      return <P2PGame
        log={this.state.log}
        onAddLog={this.handleAddLog}
        onRestart={this.handleRestartGame}
      />;
    }

    throw new Error(`Rendering not implemented for game: ${this.state.game}`);
  }

  render() {
    return (
      <div className="main">
        <img alt="" src="https://mir-s3-cdn-cf.behance.net/project_modules/disp/4b5e1c15227903.5628e65346842.png" />
        {this.renderGame()}
      </div>
    )
  }
}

module.exports = App;
