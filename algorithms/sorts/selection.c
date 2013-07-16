/*
 * Selecton sort
 *
 * O(n^2)
 *
 */

#include <stdio.h>
#include <assert.h>
#include "_helpers.c"

/* -----------------------------------------------------------------------------
 * Algorithm
 * -------------------------------------------------------------------------- */

void selection(int A[], int s, int n) {
  int i, ii, j, k, temp;

  for (i = s, ii = n - 1; i < ii; i++) {
    k = i;

    for (j = i + 1; j < n; j++) {
      if (A[j] < A[k]) k = j;
    }

    if (k != i) {
      temp = A[i];
      A[i] = A[k];
      A[k] = temp;
    }
  }
}

/* -----------------------------------------------------------------------------
 * Tests
 * -------------------------------------------------------------------------- */

void tests() {
  int A[8] = {4, 1, 32, 12, 17, 5, 1, 13};
  selection(A, 0, 8);
  assert(is_sorted(A, 0, 8));

  /* print_array(A, 0, 8); */
}

int main() {
  tests();

  return 0;
}
