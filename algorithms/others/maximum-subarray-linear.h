/*
 * Maximum-subarray problem
 * ------
 *
 * SEE: maximum-subarray.h
 *
 * This solution solves Maximum-subarray problem in linear time.
 * Algorithm uses single for loop to iterate array and in each
 * iteration keeps track of maximum subarray seen so far and maximum
 * subarray ending on counter index. Current maximum subarray for the
 * next interation will be grater ot the both from previous iteration.
 *
 * ASYMPTOTIC TIME:
 *
 *  T(n) = n
 *
 */

#include <stdlib.h>
#include <string.h>

typedef struct {
  int left;
  int right;
  int sum;
} subarr;

subarr maximum_subarray(int A[], int low, int high) {
  if (low == high) {
    subarr max_arr = {low, high, A[low]};
    return max_arr;
  }

  subarr curr_max_arr = {low, low, A[low]};
  subarr right_max_arr = {low, low, A[low]};

  unsigned i;
  for (i = 1; i <= high; i++) {
    if (A[i] > right_max_arr.sum && right_max_arr.sum < 0) {
      right_max_arr.left = i;
      right_max_arr.right = i;
      right_max_arr.sum = A[i];
    } else {
      right_max_arr.right = i;
      right_max_arr.sum += A[i];
    }

    if (right_max_arr.sum > curr_max_arr.sum) {
      curr_max_arr = right_max_arr;
    }
  }

  return curr_max_arr;
}
