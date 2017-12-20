const {
  FAILURE_STATUS, START_GAME, JOIN_GAME, SHOW_GAMES,
  API_PATH,
} = require('../shared/consts');

const {
  genValidNumber, calcBullsAndCows, isNumberGuessed
} = require('../shared/game');


if(typeof fetch === 'undefined') {
  global.fetch = require('node-fetch');
}

if(typeof EventSource === 'undefined') {
  global.EventSource = require('eventsource');
}

/**
 * Thin wrapper for EventSource object, that makes it convenient
 * to use in async/await situations.
 * TODO: Implement reconnecting.
 */
class Connection {
  constructor(url) {
    this.source = new EventSource(url);
    this.messages = [];
    this.requests = [];

    this.source.addEventListener('message', (e) => {
      let data = null;

      try {
        data = JSON.parse(e.data);
      } catch(e) {
        throw new Error(`Failed to parse: ${e.data}`);
      }

      if(this.requests.length) {
        const callback = this.requests.shift();
        this._throwOnFailure(data);
        callback(data);
      } else {
        this.messages.push(data);
      }
    });

    this.source.addEventListener('error', e => {
      throw e;
    });
  }

  getMessage() {
    return new Promise((resolve, reject) => {
      if(this.messages.length) {
        const msg = this.messages.shift();
        this._throwOnFailure(msg);
        resolve(msg);
      } else {
        this.requests.push(resolve);
      }
    });
  }

  _throwOnFailure(data) {
    if(data.status === FAILURE_STATUS) throw new Error(`Error: ${data.message}`);
  }
}

async function request(url) {
  const data = await (await fetch(url)).json();
  if(data.status === FAILURE_STATUS) throw new Error(`Error: ${data.message}`);
  return data;
}

async function start2p2Game(requestSuggestion, log) {
  let data;

  data = await request(`${API_PATH}/p2p?type=${SHOW_GAMES}`);
  const { games } = data;

  // DEBUG:
  //
  // log(`Games list: ${JSON.stringify(data)}`);
  //

  let conn;
  let gameId;
  let playerId;

  const number = genValidNumber();
  const isHost = games.length === 0;

  if(isHost) {
    log('Starting p2p game...');
    conn = new Connection(`${API_PATH}/p2p?type=${START_GAME}`);
    data = await conn.getMessage();

    // DEBUG:
    //
    // log(`Game: ${JSON.stringify(data)}`);
    //

    gameId = data.game;
    playerId = data.player;

    log('Waiting for opponent...');
    data = await conn.getMessage();

    log(`Opponent found.`);
  } else {
    log('Joining p2p game...');
    conn = new Connection(`${API_PATH}/p2p?type=${JOIN_GAME}&id=${games[0]}`);
    data = await conn.getMessage();

    // DEBUG:
    //
    // log(`Joined Game: ${JSON.stringify(data)}`);
    //

    gameId = data.game;
    playerId = data.player;
  }

  // DEBUG:
  //
  // console.log(`IsHost => ${isHost}`);
  // console.log(`GameId => ${gameId}`);
  // console.log(`PlayerId => ${playerId}`);
  //
  data = null;
  log(`Your number is: ${number}`);

  // TODO: I probably should add some abstractions here.
  while(true) {
    if(isHost) {
      log('Enter your suggestion...');
      const suggestion = await requestSuggestion();
      await request(`${API_PATH}/p2p?id=${gameId}&player=${playerId}&payload=${suggestion}`);

      data = await conn.getMessage();
      log(`Result: ${data.payload}`);
      if(data.payload === 'true') {
        log('You guessed');
        return true;
      }

      log('Waiting for suggestion from the opponent...');
      data = await conn.getMessage();

      let result;
      result = calcBullsAndCows(number, data.payload * 1);
      result = isNumberGuessed(number, result) || result;
      log(`Your opponent suggested ${data.payload} and gets ${result}`);

      await request(`${API_PATH}/p2p?id=${gameId}&player=${playerId}&payload=${result}`);
      if(result === true) {
        log('Your opponent guessed');
        return true;
      }
    } else {
      log('Waiting for suggestion from the opponent...');
      data = await conn.getMessage();

      let result;
      result = calcBullsAndCows(number, data.payload * 1);
      result = isNumberGuessed(number, result) || result;
      log(`Your opponent suggested ${data.payload} and gets ${result}`);

      await request(`${API_PATH}/p2p?id=${gameId}&player=${playerId}&payload=${result}`);
      if(result === true) {
        log('Your opponent guessed');
        return true;
      }

      log('Enter your suggestion...');
      const suggestion = await requestSuggestion();
      await request(`${API_PATH}/p2p?id=${gameId}&player=${playerId}&payload=${suggestion}`);

      data = await conn.getMessage();
      log(`Result: ${data.payload}`);

      if(data.payload === 'true') {
        log('You guessed');
        return true;
      }
    }
  }
}

module.exports = start2p2Game;
