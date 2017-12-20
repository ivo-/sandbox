const {
  SUCCESS_STATUS, FAILURE_STATUS, RECONNECT_GAME,
  START_GAME, JOIN_GAME, SHOW_GAMES, GUESS_NUMBER,
} = require('../shared/consts');

const {
  isValidNumber,
  genValidNumber,
  isNumberGuessed,
  calcBullsAndCows,
} = require('../shared/game');


// =============================================================================
// Single game

function successResponse(result) {
  return { result, status: SUCCESS_STATUS };
}

function failureResponse(result) {
  return { result, status: FAILURE_STATUS };
}

/**
 * In single game mode the player(browser) plays against the server. The
 * Server generates the number and the player tries to guess it.
 * @param {Storage} storage
 * @param {object} data Request data
 * @returns {object} Response
 */
function singleGame(storage, data) {
  if(data.type === START_GAME) {
    const game = storage.storeGame({
      type: 'single',
      number: genValidNumber(),
    });
    return successResponse(game.id);
  }

  if(data.type === GUESS_NUMBER) {
    const game = storage.findGame(data.id);

    if(!game) {
      return failureResponse(`No game with id: ${data.id}`);
    }

    const suggestion = data.suggestion * 1;
    if(!isValidNumber(suggestion)) {
      return failureResponse(`Suggestion=${suggestion} is not a valid number`)
    }

    const result = calcBullsAndCows(game.number, suggestion);
    return successResponse(isNumberGuessed(game.number, result) || result);
  }

  return failureResponse(`Unknown data type=${data.type}`);
}

// =============================================================================
// P2P game

/**
 * In P2P game mode the player(browser) plays against another player. Every
 * player generates its own number the player that guesses first his opponent
 * number wins the game.
 * @param {Storage} storage
 * @param {object} data Request data
 * @param {object} player HTTP response object
 * @returns {object} Response
 */
function p2pGame(storage, data, player) {
  if(data.type === SHOW_GAMES) {
    return {
      type: 'HTTP',
      result: {
        status: SUCCESS_STATUS,
        games: storage.getGames().filter(g => (
          g.type === 'p2p' && g.player1 && g.player2 === null
        )).map(g => g.id),
      },
    };
  }

  if(data.type === START_GAME) {
    const game = storage.storeGame({
      type: 'p2p',
      player1: storage.getUID(),
      player1Chan: player,
      player2: null,
      player2Chan: null,
    });

    return {
      to: player,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
        game: game.id,
        player: game.player1,
      }
    };
  }

  const game = storage.findGame(data.id);
  if(!game) {
    return {
      type: 'HTTP',
      result: {
        status: FAILURE_STATUS,
        message: `Cannot find game: ${data.id}`,
      }
    };
  }

  if(data.type === JOIN_GAME) {
    const g = storage.updateGame(data.id, {
      player2: storage.getUID(),
      player2Chan: player,
    });

    return [{
      to: player,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
        game: g.id,
        player: g.player2,
      }
    }, {
      to: g.player1Chan,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
      }
    }];
  }

  if(data.type === RECONNECT_GAME) {
    const g = storage.updateGame(data.id, {
      [data.player * 1 === game.player1 ? 'player1Chan' : 'player2Chan']: player,
    });

    return {
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
      }
    };
  }

  const to = data.player * 1 === game.player1 ? game.player2Chan : game.player1Chan;
  if(!to) {
    return {
      type: 'HTTP',
      result: {
        status: FAILURE_STATUS,
        message: 'Cannot find other player',
      }
    };
  }
  return [{
    type: 'HTTP',
    result: {
      status: SUCCESS_STATUS,
    }
  }, {
    to,
    type: 'SSE',
    result: {
      status: SUCCESS_STATUS,
      payload: data.payload,
    },
  }];
}

// =============================================================================
// Exports

module.exports = {
  p2pGame,
  singleGame,
}
