const Storage = require('./Storage');
const {
  singleGame,
  p2pGame,
} = require('./gameModes');

const {
  SUCCESS_STATUS, FAILURE_STATUS,
  START_GAME, JOIN_GAME, SHOW_GAMES, GUESS_NUMBER,
} = require('../shared/consts');

function InMemoryStorage() {
  this.uidGen = 1;
  this.games = {};
  this._persist = () => {};
}
InMemoryStorage.prototype = Storage.prototype;

exports.testSingleGame = function(test) {
  const s = new InMemoryStorage();
  const d = {};

  test.deepEqual(
    singleGame(s, { type: START_GAME }),
    { result: 1, status: SUCCESS_STATUS }
  );

  test.deepEqual(
    singleGame(s, { type: GUESS_NUMBER, id: 2 }).status,
    FAILURE_STATUS,
    'Failure if game is not found.'
  );

  test.deepEqual(
    singleGame(s, { type: GUESS_NUMBER, id: 1 }).status,
    FAILURE_STATUS,
    'Failure if no suggestion.'
  );

  test.deepEqual(
    singleGame(s, { type: GUESS_NUMBER, id: 1, suggestion: 1234 }).status,
    SUCCESS_STATUS,
    'Success if game and suggestion.'
  );

  test.done();
};

exports.testP2PGame = function(test) {
  const s = new InMemoryStorage();
  const p1 = {};
  const p2 = {};
  const d = {};

  test.deepEqual(
    p2pGame(s, { type: SHOW_GAMES }, p1),
    {
      type: 'HTTP',
      result: {
        status: SUCCESS_STATUS,
        games: [],
      }
    },
    'No games initially'
  );

  test.deepEqual(
    p2pGame(s, { type: START_GAME }, p1),
    {
      to: p1,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
        game: 2,
        player: 1,
      }
    },
    'Starting a p2p game'
  );

  test.deepEqual(
    p2pGame(s, { type: SHOW_GAMES }, p1),
    {
      type: 'HTTP',
      result: {
        status: SUCCESS_STATUS,
        games: [2],
      }
    },
    'There is one game now'
  );

  test.deepEqual(
    p2pGame(s, { id: 2, player: 1, payload: 'data' }, p1).result.status,
    FAILURE_STATUS,
    'There is only one player and messages are not allowed'
  );

  test.deepEqual(
    p2pGame(s, { type: JOIN_GAME, id: 200 }, p2).result.status,
    FAILURE_STATUS,
    'Cannot join not existing game'
  );

  test.deepEqual(
    p2pGame(s, { type: JOIN_GAME, id: 2 }, p2),
    [{
      to: p2,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
        game: 2,
        player: 3,
      }
    }, {
      to: p1,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
      }
    }],
    'Joining existing game'
  );

  test.deepEqual(
    p2pGame(s, { id: 2, player: 3, payload: 'data' }, p2),
    [{
      type: 'HTTP',
      result: {
        status: SUCCESS_STATUS,
      }
    }, {
      to: p1,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
        payload: 'data'
      }
    }],
    'Player 2 can send messages'
  );

  test.deepEqual(
    p2pGame(s, { id: 2, player: 1, payload: 'data' }, p1),
    [{
      type: 'HTTP',
      result: {
        status: SUCCESS_STATUS,
      }
    }, {
      to: p2,
      type: 'SSE',
      result: {
        status: SUCCESS_STATUS,
        payload: 'data'
      }
    }],
    'Player 1 can send messages'
  );

  test.done();
};
