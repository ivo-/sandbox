const Game = require('./Game');
const startP2PGame = require('../shared/p2pGame');

class P2PGame extends Game {
  componentDidMount() {
    startP2PGame(this.handleRequestInput, this.props.onAddLog)
      .then(data => this.props.onAddLog(`Game ended: Success`))
      .catch(reason => this.props.onAddLog(`Game ended: Error ${reason.message}`));
  }
}

P2PGame.propTypes = Game.propTypes;
module.exports = P2PGame;
