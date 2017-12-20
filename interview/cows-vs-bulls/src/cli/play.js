const startGameVsServer = require('../shared/gameVsServer');

function requestSuggestion() {
  return new Promise((resolve, reject) => {
    process
      .openStdin()
      .once("data", d => resolve(d.toString().trim()));
  });
}

startGameVsServer(requestSuggestion, console.log.bind(console))
  .then(data => console.log('Game ended: ', data))
  .catch(reason => console.log('Game ended: ', reason.message));
