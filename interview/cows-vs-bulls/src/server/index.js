const Storage = require('./Storage');
const { singleGame, p2pGame } = require('./gameModes');
const { SUCCESS_STATUS, FAILURE_STATUS } = require('../shared/consts');

function startServer(storage) {
  const url = require('url');
  const http = require('http');

  const handleP2PGame = p2pGame.bind(global, storage);
  const handleSingleGame = singleGame.bind(global, storage);

  http.createServer((req, res) => {
    // --------------------------------------------------
    // CORS

    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Request-Method', '*');
    res.setHeader('Access-Control-Allow-Methods', 'OPTIONS, GET');
    res.setHeader('Access-Control-Allow-Headers', '*');

    if(req.method === 'OPTIONS') {
      res.writeHead(200);
      res.end();
      return;
    }

    // --------------------------------------------------
    // Handlers

    const parsedURL = url.parse(req.url, true);
    const data = parsedURL.query;

    if(parsedURL.pathname === '/p2p') {
      console.log(data);

      let response;
      response = handleP2PGame(data, res);
      response = Array.isArray(response) ? response : [response];
      response.forEach((r) => {
        if(r.type === 'HTTP') {
          res.end(JSON.stringify(r.result));
          return;
        } else if(r.type === 'SSE') {
          if(!r.to.headersSent) {
            r.to.writeHead(200, {
              'Content-Type': 'text/event-stream',
              'Cache-Control': 'no-cache',
              'Connection': 'keep-alive'
            });
            r.to.write('\n');
          }

          console.log(r.result);
          r.to.write(`data: ${JSON.stringify(r.result)}\n\n`);
        } else {
          throw new Error(`Invalid response type: ${r.type}`);
        }
      });
      return;
    }

    if(parsedURL.pathname === '/single') {
      const response = handleSingleGame(data);

      res.writeHead(200);
      res.end(JSON.stringify(response));
      return;
    }

    res.writeHead(404);
    res.end();
  }).listen(8001)

  console.log('Server running on port 8001...')
}

function start() {
  return (new Storage()).then(storage => {
    startServer(storage);
  });
}

start();
