const {
  isValidNumber,
  calcBullsAndCows,
  genValidNumber,
  numberToDigits,
  isNumberGuessed,
} = require('./game');


exports.testIsValidNumber = function(test) {
  test.equal(isValidNumber('str'), false, 'String is not a valid number.');
  test.equal(isValidNumber(1231), false, 'Number with repeated digits is not valid.');
  test.equal(isValidNumber(1234), true, 'Number with repeated digits is not valid.');

  test.done();
};

exports.testNumberToDigits = function(test) {
  test.deepEqual(numberToDigits(1), ['1']);
  test.deepEqual(numberToDigits(123), ['1', '2', '3']);

  test.done();
};

exports.testCalcBullsAndCows = function(test) {
  test.deepEqual(calcBullsAndCows(123, 345), ['COW']);
  test.deepEqual(calcBullsAndCows(123, 453), ['BULL']);
  test.deepEqual(calcBullsAndCows(123, 124), ['BULL', 'BULL']);
  test.deepEqual(calcBullsAndCows(123, 312), ['COW', 'COW', 'COW']);
  test.deepEqual(calcBullsAndCows(123, 123), ['BULL', 'BULL', 'BULL']);

  test.done()
};


exports.testIsNumberGuessed = function(test) {
  test.equal(isNumberGuessed(123, ['BULL', 'COW']), false);
  test.equal(isNumberGuessed(123, ['BULL', 'BULL', 'BULL']), true);

  test.done();
};
