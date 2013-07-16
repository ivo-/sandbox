/*
 * Merge + insertion sort
 *
 * O(nlogn)
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "_helpers.c"

/* -----------------------------------------------------------------------------
 * Algorithm
 * -------------------------------------------------------------------------- */

void insertion(int A[], int s, int n) {
  int i, j, key;
  for (i = s; i <= n; i++) {
    key = A[i],
    j = i - 1;

    while (j >= s && key < A[j]) {
      A[j + 1] = A[j];
      j--;
    }

    A[j + 1] = key;
  }
}

void combine(int A[], int s, int m, int n) {
  int nl = m - s + 1;
  int nr = n - m;

  int *L = (int*) malloc(nl * sizeof(int));
  memcpy(L, A + s, nl * sizeof(int));

  int *R = (int*) malloc(nr * sizeof(int));
  memcpy(R, A + m + 1, nr * sizeof(int));

  int l, r, k;
  for (l = r = 0, k = s; k <= n; k++) {
    if (l > nl) {
      A[k] = R[r++];
    } else if (r > nr) {
      A[k] = L[l++];
    } else if (L[l] <= R[r]) {
      A[k] = L[l++];
    } else {
      A[k] = R[r++];
    }
  }

  free(L);
  free(R);
}

void merge_insertion(int A[], int s, int n, int k) {
  if ((n - s) <= k) {
    insertion(A, s, n);
  } else {
    int q = (s + n)/2;

    merge_insertion(A, s, q, k);
    merge_insertion(A, q + 1, n, k);
    combine(A, s, q, n);
  }
}

/* -----------------------------------------------------------------------------
 * Tests
 * -------------------------------------------------------------------------- */

void tests() {
  int A[8] = {4, 1, 32, 12, 17, 5, 1, 13};
  merge_insertion(A, 0, 7, 2);
  assert(is_sorted(A, 0, 7));
  print_array(A, 0, 7);
}

int main() {
  tests();

  return 0;
}
