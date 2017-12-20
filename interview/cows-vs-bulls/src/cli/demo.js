const startGameVsServer = require('../shared/gameVsServer');

const { DEFAULT_DATA, nextGuess } = require('../shared/solver');
const { genValidNumber, numberToDigits } = require('../shared/game');

function requestSuggestion() {
  let data = {
    ...DEFAULT_DATA,
    current: numberToDigits(genValidNumber())
  };

  return (result) => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        data = nextGuess(data, result);

        const suggestion = data.current.join('') * 1;
        console.log(suggestion);
        resolve(suggestion);
      }, 1000);
    });
  }
}

startGameVsServer(requestSuggestion(), console.log.bind(console))
  .then(data => console.log('Game ended: ', data))
  .catch(reason => console.log('Game ended: ', reason.message));
