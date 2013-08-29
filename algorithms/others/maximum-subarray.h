/*
 * Maximum-subarray problem
 * ------
 *
 *   Maximum-subarray is contiguous subarray within a one-dimensional
 * array of numbers which has the largest sum.
 *
 *   Find maximum-subarray into array and calculate its sum. In this
 * solution I use Divide-And-Conquer approach, which is not the
 * optimal solution for this problem, but it is good demonstration of
 * how powerful Divide-And-Conquer can be.
 *
 * RECURRENCE:
 *
 *  T(1) = Ø(1)
 *  T(n) = 2T(n/2) + Ø(n)
 *
 * ASYMPTOTIC TIME:
 *
 *  T(n) = Ø(n.lg(n))
 *
 */

#include <stdlib.h>
#include <string.h>

typedef struct {
  int left;
  int right;
  int sum;
} subarr;

subarr maximum_crossing_subarray(int A[], int low, int mid, int high) {
  subarr left_arr = {mid, mid, A[mid]};
  subarr right_arr = {mid + 1, mid + 1, A[mid + 1]};

  int i, sum;
  for (i = left_arr.left, sum = 0; i >= low; i--) {
    sum += A[i];

    if (left_arr.sum < sum) {
      left_arr.sum = sum;
      left_arr.left = i;
    }
  }

  for (i = right_arr.right, sum = 0; i <= high; i++) {
    sum += A[i];

    if (right_arr.sum < sum) {
      right_arr.sum = sum;
      right_arr.right = i;
    }
  }

  subarr max_arr = {left_arr.left, right_arr.right, left_arr.sum + right_arr.sum};
  return max_arr;
}

subarr maximum_subarray(int A[], int low, int high) {
  if (low == high) {
    subarr max_arr = {low, high, A[low]};
    return max_arr;
  }

  int mid = (low + high)/2;

  subarr left_arr = maximum_subarray(A, low, mid);
  subarr right_arr = maximum_subarray(A, mid + 1, high);
  subarr crossing_arr = maximum_crossing_subarray(A, low, mid, high);

  if (left_arr.sum >= right_arr.sum && left_arr.sum >= crossing_arr.sum) return left_arr;
  if (right_arr.sum >= left_arr.sum && right_arr.sum >= crossing_arr.sum) return right_arr;
  return crossing_arr;
}
