const COW = 'COW';
const BULL = 'BULL';

/**
 * Check if `x` is a valid Bulls and Cows number.
 * @param {number} x
 * @returns {boolean}
 */
function isValidNumber(x) {
  if(typeof x !== 'number') return false;

  const digits = numberToDigits(x);
  const uniqDigits = new Set(digits);
  return digits.length === uniqDigits.size;
}

/**
 * Calculate array of digits for provided number.
 * @param {number} x
 * @returns {Array}
 */
function numberToDigits(x) {
  return Array.prototype.slice.call(x.toString()).map(x => x * 1);
}

/**
 * Calculate array of digits for provided number.
 * @param {number} number
 * @param {number} suggestion
 * @returns {Array} Array of COW and BULL values.
 */
function calcBullsAndCows(number, suggestion) {
  const numberDigits = numberToDigits(number);
  const suggestionDigits = numberToDigits(suggestion);

  return suggestionDigits.reduce((result, digit, i) => {
    if(digit === numberDigits[i]) {
      return [...result, BULL];
    } else if(numberDigits.includes(digit)) {
      return [...result, COW];
    }

    return result;
  }, []);
}

/**
 * Checks if the number is guessed.
 * @param {number} number
 * @param {Array} result Calculated with `calcBullsAndCows`.
 * @returns {Boolean}
 */
function isNumberGuessed(number, result) {
  return numberToDigits(number).length ===
    result.filter(item => item === BULL).length;
}

/**
 * Naive, but good enough for our purposes, valid Bulls vs Cows
 * number generator.
 * @returns {number}
 */
function genValidNumber() {
  const number = Math.round(1000 + Math.random() * 8999);
  return isValidNumber(number) ? number : genValidNumber();
}

module.exports = {
  COW, BULL,
  isValidNumber, genValidNumber, calcBullsAndCows,
  isNumberGuessed, numberToDigits,
};
