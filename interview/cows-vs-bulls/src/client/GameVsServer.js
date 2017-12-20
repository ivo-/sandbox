const Game = require('./Game');
const startGameVsServer = require('../shared/gameVsServer');
const { DEFAULT_DATA, nextGuess } = require('../shared/solver');
const { genValidNumber, numberToDigits } = require('../shared/game');

class GameVsServer extends Game {
  componentDidMount() {
    if(!this.props.demo) {
      startGameVsServer(this.handleRequestInput, this.props.onAddLog)
        .then(data => this.props.onAddLog(`Game ended: Success`))
        .catch(reason => this.props.onAddLog(`Error: ${reason.message}`));

      return;
    }

    let d = { ...DEFAULT_DATA, current: numberToDigits(genValidNumber()) };
    let suggestionGenerator = (result) => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          d = nextGuess(d, result);

          const suggestion = d.current.join('') * 1;
          this.props.onAddLog(suggestion);
          resolve(suggestion);
        }, 1000);
      });
    }

    startGameVsServer(suggestionGenerator, this.props.onAddLog)
      .then(data => this.props.onAddLog(`Game ended: Success`))
      .catch(reason => this.props.onAddLog(`Error: ${reason.message}`));
  }
}

GameVsServer.propTypes = Game.propTypes;
module.exports = GameVsServer;
