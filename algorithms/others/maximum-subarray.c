#include <stdio.h>

#include "../helpers.h"
#include "../testhelp.h"
#include "maximum-subarray.h"

void tests() {
  int A[8] = {4, 1, -32, 12, 17, -5, 1, 13};

  tuple m = maximum_subarray(A, 0, 7);

  test_cond("Check if maximum-subarray sum is 38.", m.sum == 38);
  test_cond("Check if maximum-subarray left is 3.", m.left == 3);
  test_cond("Check if maximum-subarray right is 7.", m.right == 7);

  test_report();
}

int main() {
  tests();

  return 0;
}
