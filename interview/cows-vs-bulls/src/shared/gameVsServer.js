const {
  SUCCESS_STATUS, FAILURE_STATUS,
  START_GAME, GUESS_NUMBER,
  API_PATH,
} = require('../shared/consts');

if(typeof fetch === 'undefined') {
  global.fetch = require('node-fetch');
}

async function startGameVsServer(requestSuggestion, log) {
  let data;

  log('Starting the game...');
  data = await (await fetch(
    `${API_PATH}/single?type=${START_GAME}`
  )).json();

  // DEBUG:
  //
  // log(`Response received: ${JSON.stringify(data)}`);
  //

  if(data.status === FAILURE_STATUS) throw new Error(
    `Cannot start game: ${data.result}`
  );

  console.log(data);
  const id = data.result;
  data = null;

  do {
    log('Enter suggestion...');
    const suggestion = await requestSuggestion(data && data.result);

    // DEBUG:
    //
    // log(`Suggestion received - ${suggestion}`);
    // log('Sending suggestion...');
    //
    data = await (await fetch(
      `${API_PATH}/single?type=${GUESS_NUMBER}&id=${id}&suggestion=${suggestion}`
    )).json();

    if(data.status === FAILURE_STATUS) throw new Error(
      `Cannot send suggestion: ${data.result}`
    );

    if (data.result === true) {
      log(`You guessed it. The number is ${suggestion}`);
    } else {
      log(`Response received... ${data.result}`);
    }
  } while(
    data.status !== SUCCESS_STATUS ||
      data.result !== true
  );

  return true;
}

module.exports = startGameVsServer;
