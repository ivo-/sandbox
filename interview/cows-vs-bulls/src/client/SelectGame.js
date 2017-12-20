const React = require('react');
const PropTypes = require('prop-types');

const { SUPPORTED_GAMES } = require('../shared/consts');

class SelectGame extends React.Component {
  handleSelectGame(game) {
    this.props.onSelectGame(game);
  }

  render() {
    const games = Object.keys(SUPPORTED_GAMES).map(g => (
      <button key={g} onClick={this.handleSelectGame.bind(this, g)}>
        {g.toUpperCase()}
      </button>
    ));
    return (
      <div className="select-game">
        Play <br /> {games}
      </div>
    );
  }
}

SelectGame.propTypes = {
  onSelectGame: PropTypes.func.isRequired,
};

module.exports = SelectGame;
