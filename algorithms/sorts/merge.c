/*
 * Merge sort
 *
 * O(nlogn)
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../helpers.c"

/* -----------------------------------------------------------------------------
 * Algorithm
 * -------------------------------------------------------------------------- */

/*
 * Combine sorted arrays [s..m] and [m+1..n].
 */
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

/*
 * Sort elements in iterval [s..n]. Bottom of the recursion is when
 * interval has one element. Then two arrays [a] and [b] are combined
 * into one sorted and so on ... until full array is restored.
 */
void merge(int A[], int s, int n) {
  if (s < n) {
    int q = (s + n)/2;

    merge(A, s, q);
    merge(A, q + 1, n);
    combine(A, s, q, n);
  }
}

/* -----------------------------------------------------------------------------
 * Tests
 * -------------------------------------------------------------------------- */

void tests() {
  int A[8] = {4, 1, 32, 12, 17, 5, 1, 13};
  merge(A, 0, 7);
  assert(is_sorted(A, 0, 7));

  /* print_array(A, 0, 7); */
}

int main() {
  tests();

  return 0;
}
