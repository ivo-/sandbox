/*
 * Selecton sort
 *
 * O(n^2)
 *
 */

#include <stdio.h>
#include <assert.h>
#include "../helpers.c"

/* -----------------------------------------------------------------------------
 * Algorithm
 * -------------------------------------------------------------------------- */

/*
 * Sort elements in iterval [s..n]
 */
void selection(int A[], int s, int n) {
  int i, ii, j, k, temp;

  /* NOTE: n is the index of last element */
  for (i = s; i < n; i++) {
    k = i;

    for (j = i + 1; j <= n; j++) {
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
  selection(A, 0, 7);
  assert(is_sorted(A, 0, 7));

  /* print_array(A, 0, 7); */
}

int main() {
  tests();

  return 0;
}
