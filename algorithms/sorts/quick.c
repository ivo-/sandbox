/*
 * Quick sort
 *
 */

#include <stdio.h>
#include <assert.h>
#include "quick.h"

/* -----------------------------------------------------------------------------
 * Tests
 * -------------------------------------------------------------------------- */

void tests() {
  int A[8] = {4, 1, 32, 12, 17, 5, 1, 13};
  quick_sort(A, 0, 7);
  assert(is_sorted(A, 0, 7));
  print_array(A, 0, 7);
}

int main() {
  tests();

  return 0;
}
