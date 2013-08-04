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

/* -----------------------------------------------------------------------------
 * Algorithm
 * -------------------------------------------------------------------------- */

typedef struct {
  int left;
  int right;
  int sum;
} tuple;

tuple maximum_crossing_subarray(int A[], int low, int mid, int high) {
  int i, s;
  tuple l = {mid, mid, A[mid]},
        r = {mid + 1, mid + 1, A[mid + 1]};

  for (i = l.left, s = 0; i >= low; i--) {
    s += A[i];

    if (l.sum < s) {
      l.sum = s;
      l.left = i;
    }
  }

  for (i = r.right, s = 0; i <= high; i++) {
    s += A[i];

    if (r.sum < s) {
      r.sum = s;
      r.right = i;
    }
  }

  tuple c = {l.left, r.right, l.sum + r.sum};
  return c;
}

tuple maximum_subarray(int A[], int low, int high) {
  if (low == high) {
    tuple r = {low, high, A[low]};
    return r;
  }

  int mid = (low + high)/2;

  tuple l = maximum_subarray(A, low, mid);
  tuple r = maximum_subarray(A, mid + 1, high);
  tuple c = maximum_crossing_subarray(A, low, mid, high);

  if (l.sum >= r.sum && l.sum >= c.sum) return l;
  if (r.sum >= l.sum && r.sum >= c.sum) return r;
  return c;
}
