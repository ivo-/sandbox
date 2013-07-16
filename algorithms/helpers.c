#ifndef _H_HELPERS
#define _H_HELPERS

#include <stdio.h>
#include <assert.h>

int print_array(int A[], int s, int n) {
  int i;
  for (i = s; i < n; i++)
    printf("%d\n", A[i]);
}

int is_sorted(int A[], int s, int n) {
  int i, j;
  for (i = s; i < n; i++) {
    for (j = i + 1; j < n; j++) {
      if (A[j] < A[i])
        return 0;
    }
  }

  return 1;
}

#endif
