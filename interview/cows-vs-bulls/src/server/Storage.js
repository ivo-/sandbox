const levelup = require('levelup');
const leveldown = require('leveldown');

class Storage {
  constructor() {
    this.db = levelup(leveldown('./db'));
    this.uidGen = 1;
    this.games = {};

    return new Promise((resolve, reject) => {
      this.db.get('games', (err, games) => {
        if(err && err.type !== 'NotFoundError') reject(err);
        if(games) this.games = JSON.parse(games);

        this.db.get('uidGen', (err1, uidGen) => {
          if(err1 && err1.type !== 'NotFoundError') reject(err1);
          if(uidGen) this.uidGen = uidGen;

          resolve(this);
        });
      });
    });
  }

  _persist() {
    const result = Object.keys(this.games).reduce((store, id) => {
      const g = this.games[id];
      store[id] = g.type !== 'p2p' ? g : {
        ...g,
        // Don't store channels.
        player1Chan: null,
        player2Chan: null,
      };

      return store;
    }, {});
    this.db.put('games', JSON.stringify(result), function (err) {
      if(err) console.error(err);
    });

    this.db.put('uidGen', this.uidGen, function (err) {
      if(err) console.error(err);
    });
  }

  storeGame(game) {
    const id = this.getUID();
    this.games[id] = { id, ...game };

    this._persist();
    return this.games[id];
  }

  findGame(id) {
    return this.games && this.games[id];
  }

  getGames() {
    return Object.keys(this.games).map(id => this.games[id]);
  }

  updateGame(id, data) {
    const game = this.findGame(id);
    if(game) Object.assign(game, data);
    this._persist();
    return game;
  }

  getUID() {
    return this.uidGen++;
  }
}

module.exports = Storage;
