// -----------------------------------------------------------------------------
// Game commands

exports.SUPPORTED_GAMES = {
  p2p: true,
  demo: true,
  single: true,
};

exports.JOIN_GAME = 'JOIN_GAME';
exports.START_GAME = 'START_GAME';
exports.SHOW_GAMES = 'SHOW_GAMES';
exports.GUESS_NUMBER = 'GUESS_NUMBER';
exports.RECONNECT_GAME = 'RECONNECT_GAME';

// -----------------------------------------------------------------------------
// Response statuses

exports.SUCCESS_STATUS = 'SUCCESS_STATUS';
exports.FAILURE_STATUS = 'FAILURE_STATUS';

// -----------------------------------------------------------------------------
// Other

exports.API_PATH = 'http://localhost:8001';
