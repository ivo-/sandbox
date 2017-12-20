const Storage = require('./Storage');

function InMemoryStorage() {
  this.uidGen = 1;
  this.games = {};
  this._persist = () => {};
}
InMemoryStorage.prototype = Storage.prototype;

exports.testStorage = function(test) {
  const s = new InMemoryStorage();
  const g = s.storeGame({ name: 'g' });

  test.equal(s.findGame(g.id).name, 'g');
  s.updateGame(g.id, { name: 'h' });
  test.equal(s.findGame(g.id).name, 'h');

  s.storeGame({ name: 'i' });
  s.storeGame({ name: 'j' });
  s.storeGame({ name: 'k' });
  test.equal(s.getGames().map(item => item.name).join(''), 'hijk');

  test.done();
};
