const { nextGuess, DEFAULT_DATA } = require('./solver');
const { calcBullsAndCows, genValidNumber, numberToDigits } = require('./game');

const assert = require('assert');

exports['test-solver-generative'] = function(test) {
  for(let j=0; j<1000; j++) {
    let n = genValidNumber();
    let d;
    let r;

    // DEBUG:
    // console.log(n);

    d = { ...DEFAULT_DATA, current: numberToDigits(genValidNumber()) };

    // DEBUG:
    // console.log('--------------------------------------------------');
    // console.log(d);

    let i=0;
    while(typeof d !== 'number') {
      r = calcBullsAndCows(d.current.join('')*1, n)
      d = nextGuess(d, r);

      // DEBUG:
      // console.log(r);
      // console.log('--------------------------------------------------');
      // console.log(d);

      i++;
      if(i > 50) throw new Error(`${JSON.stringify(d)}`);
    }

    test.equal(d, n, 'Number is guessed.');

    // DEBUG:
    // console.log(i);
  }

  test.done();
};
