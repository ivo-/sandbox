
#include <stdio.h>

#include "../helpers.h"
#include "../testhelp.h"

#include "maximum-subarray.h"

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
