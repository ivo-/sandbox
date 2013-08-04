/*
 * Maximum-subarray problem
 * -----
 *
 *   Maximum-subarray is contiguous subarray within a one-dimensional
 * array of numbers which has the largest sum.
 *
 *   Find maximum-subarray into array and calculate its sum. In this
 * solution I use Divide-And-Conquer approach. Asymptotic running time
 * of the algorithms is Ø(n.lg(n)).
 *
 */

#include <stdlib.h>
#include <string.h>

typedef struct {
  int left;
  int right;
  int sum;
} tuple;

tuple maximum_crossing_subarray(int A[], int low, int mid, int high) {
  int i, sum;
  tuple larr = {mid, mid, A[mid]};
  tuple rarr = {mid + 1, mid + 1, A[mid + 1]};

  for (i = larr.left, sum = 0; i >= low; i--) {
    sum += A[i];

    if (larr.sum < sum) {
      larr.sum = sum;
      larr.left = i;
    }
  }

  for (i = rarr.right, sum = 0; i <= high; i++) {
    sum += A[i];

    if (rarr.sum < sum) {
      rarr.sum = sum;
      rarr.right = i;
    }
  }

  tuple carr = {larr.left, rarr.right, larr.sum + rarr.sum};
  return carr;
}

tuple maximum_subarray(int A[], int low, int high) {
  if (low == high) {
    tuple res = {low, high, A[low]};
    return res;
  }

  int mid = (low + high)/2;

  tuple larr = maximum_subarray(A, low, mid);
  tuple rarr = maximum_subarray(A, mid + 1, high);
  tuple carr = maximum_crossing_subarray(A, low, mid, high);

  if (larr.sum >= rarr.sum && larr.sum >= carr.sum) return larr;
  if (rarr.sum >= larr.sum && rarr.sum >= carr.sum) return rarr;
  return carr;
}
