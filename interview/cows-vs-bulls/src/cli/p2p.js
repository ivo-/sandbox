const start2p2Game = require('../shared/p2pGame');

function requestSuggestion() {
  return new Promise((resolve, reject) => {
    process
      .openStdin()
      .once("data", d => resolve(d.toString().trim()));
  });
}

start2p2Game(requestSuggestion, console.log.bind(console))
  .then(data => console.log('Game ended: ', data))
  .catch(reason => console.log('Game ended: ', reason.message));
