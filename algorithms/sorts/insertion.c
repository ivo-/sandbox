/*
 * Insertion sort
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

void insertion(int A[], int s, int n) {
  int i, j, key;
  for (i = s; i < n; i++) {
    key = A[i],
    j = i - 1;

    while (j >= s && key < A[j]) {
      A[j + 1] = A[j];
      j--;
    }

    A[j + 1] = key;
  }
}

/* -----------------------------------------------------------------------------
 * Tests
 * -------------------------------------------------------------------------- */

void tests() {
  int A[8] = {4, 1, 32, 12, 17, 5, 1, 13};
  insertion(A, 0, 8);
  assert(is_sorted(A, 0, 8));

  print_array(A, 0, 8);
}

int main() {
  tests();

  return 0;
}
