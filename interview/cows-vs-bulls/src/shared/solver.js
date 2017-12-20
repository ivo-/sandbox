const {
  COW, BULL,
  genValidNumber, numberToDigits,
} = require('./game');

// =============================================================================
// Constants

const UNKNOWN = 'UNKNOWN';

const OP_PERMUTATION = 'OP_PERMUTATION';
const OP_CHANGE_NUMBER = 'OP_CHANGE_NUMBER';

const DEFAULT_DATA = {
  current: null,
  prevGuess: null,
  prevCowsCount: null,
  prevBullsCount: null,

  found: [UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN],
  digits: numberToDigits(1234567890),

  op: null,
  opData: null,
};

// =============================================================================
// Solver

/**
 * Generate list of all array permutations.
 * @param {Array} arr
 * @returns {Array}
 */
function permutations(arr) {
  if (arr.length === 0)
    return [[]];

  const result = [];
  for (let i=0; i<arr.length; i++) {
    const copy = JSON.parse(JSON.stringify(arr));
    const head = copy.splice(i, 1);
    const rest = permutations(copy);

    for (let j=0; j<rest.length; j++)
      result.push(head.concat(rest[j]));
  }

  return result;
}

/**
 * Generate next guess using the previous guess data and the
 * other player response. This function is immutable and will
 * not mutate the provided arguments.
 *
 * @param {Object} data See DEFAULT_DATA
 * @param {Array} result List of COW, BULL items.
 * @returns {Object|Number} The new data or the guessed number.
 */
function nextGuess(data, result) {
  // Initialization
  if (!result) {
    return {
      ...DEFAULT_DATA,
      current: numberToDigits(genValidNumber()),
    };
  }

  const cows = result.filter(item => item === COW).length;
  const bulls = result.filter(item => item === BULL).length;

  const base =  {
    ...data,

    prevGuess: data.current,
    prevCowsCount: cows,
    prevBullsCount: bulls,
  };

  // Only bulls -> success
  if(bulls === 4) {
    return data.current.join('') * 1;
  }

  // Only cows and bulls
  //   => generate permutations for all the cows and try to make them bulls
  if((bulls + cows) === 4) {
    if(data.op === OP_PERMUTATION && bulls === data.prevBullsCount) {
      const items = data.opData[0].slice();

      return {
        ...base,

        current: result.reduce((res, x, i) => ([
          // Insert available cow digits at available non bull positions.
          ...res, x === BULL ? data.current[i] : items.shift()
        ]), []),
        opData: data.opData.slice(1),
      };
    }

    return nextGuess({
      ...base,
      current: data.current,

      op: OP_PERMUTATION,
      opData: permutations(data.current.filter((x, i) => (
        // We need permutations only for cow digits.
        result[i] !== BULL
      ))).slice(1),
    }, result);
  }

  // We change a digit and bulls count increases
  //   => current digit on this place is a bull
  //
  // We change a digit and bulls count decreases
  //   => prev digit on this place was a bull
  if(
    data.op === OP_CHANGE_NUMBER &&
      data.prevBullsCount !== bulls
  ) {
    const current = data.prevBullsCount < bulls ?
            data.current :
            data.prevGuess;

    const next = {
      ...base,

      current,
      op: null,
      opData: null,
      found: data.found.map((prev, i) => (
        i === data.opData.index ? BULL : prev
      )),
    };

    if(current === data.current) {
      return nextGuess(next, result);
    }

    return next;
  }

  // We change a digit and cows count increases
  //   => current digit on this place is a cow
  //
  // We change a digit and cows count decreases
  //   => prev digit on this place was a cow
  if(
    data.op === OP_CHANGE_NUMBER &&
      data.prevCowsCount !== cows
  ) {
    const current = data.prevCowsCount < cows ?
            data.current :
            data.prevGuess;

    const next = {
      ...base,

      current,
      op: null,
      opData: null,
      found: data.found.map((prev, i) => (
        i === data.opData.index ? COW : prev
      )),
    };

    if(current === data.current) {
      return nextGuess(next, result);
    }

    return next;
  }

  // No cows and no bulls
  //   => generate new random number
  if ((bulls + cows) === 0) {
    const digits = data.digits.filter(x => !data.current.includes(x));
    return {
      ...base,
      current: digits.slice(0, 4),
      digits,
    };
  }

  // Some known cows or bulls
  //   => test the first unknown digit with all the available digits
  if((bulls + cows) < 4) {
    const firstUnknownIndex = data.found.indexOf(UNKNOWN);

    let digits = data.digits;
    let availableDigits;

    if(data.op === OP_CHANGE_NUMBER) {
      // If previously picked digit, doesn't affect the score it
      // is obviously not part of the number.
      let f = x => (
        x !== data.current[data.opData.index]
      );
      availableDigits = data.opData.availableDigits.filter(f);
      // If there are now cows and the digit doesn't update the
      // score in any way, we can surely conclude that this digit
      // is not part of the number.
      if (cows === 0) digits = data.digits.filter(f);
    } else {
      availableDigits = data.digits.filter(x => (
        !data.current.includes(x)
      ));
    }

    // XXX: In the super edge case where there are 3 cows and last cow
    // is 0 but we are trying to find it for the first digit, this strategy
    // breaks.
    if(
      (firstUnknownIndex === 0 && availableDigits[0] === 0) ||
        firstUnknownIndex === -1
    ) {
      return {
        ...DEFAULT_DATA,
        current: numberToDigits(genValidNumber()),
      };
    }

    return {
      ...base,

      digits,
      current: data.current.map((x, i) => (
        i === firstUnknownIndex ? availableDigits[0] : x
      )),

      op: OP_CHANGE_NUMBER,
      opData: {
        index: firstUnknownIndex,
        availableDigits,
      },
    };
  }

  throw new Error('Ooops...?')
}

module.exports = {
  nextGuess,
  DEFAULT_DATA,
}
