/*
 * Quick sort
 * ------
 *
 */

#include <stdlib.h>
#include <string.h>
#include "../helpers.h"

void quick_sort_combine(int A[], int low, int mid, int high) {
  int pivot = A[mid];
  swap(&A[mid], &A[high]);

  int i = low;
  int r = high - 1;
  for (; i != r; i++) {
    if (A[i] >= pivot) {
      swap(&A[r--], &A[i]);
      i--;
    }
  }

  swap(&A[i], &A[high]);
}

void quick_sort(int A[], int low, int high) {
  if (low < high) {
    int mid = (low + high)/2;

    quick_sort_combine(A, low, mid, high);

    quick_sort(A, low, mid);
    quick_sort(A, mid + 1, high);
  }
}
