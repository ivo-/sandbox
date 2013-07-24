/* It takes as input an array of numbers, and it determines the
 * contiguous subarray whose values have the greatest sum. */

/* Find maximum sub-arrays A[s..m], A[m+1..n] and A[m-i..m+1+j]
 * (i,j >= 0). One of them with bigger sum is the maximum subarray in
 * the whole A[s..n] array. */

/* 1 if high == low */
/* 2 return .low; high;AOElow/ // base case: only one element */
/* 3 else mid D b.low C high/=2c */
/* 4 .left-low; left-high; left-sum/ D */
/* FIND-MAXIMUM-SUBARRAY.A; low; mid/ */
/* 5 .right-low; right-high; right-sum/ D */
/* FIND-MAXIMUM-SUBARRAY.A; mid C 1; high/ */
/* 6 .cross-low; cross-high; cross-sum/ D */
/* FIND-MAX-CROSSING-SUBARRAY.A; low; mid; high/ */
/* 7 if left-sum  right-sum and left-sum  cross-sum */
/* 8 return .left-low; left-high; left-sum/ */
/* 9 elseif right-sum  left-sum and right-sum  cross-sum */
/* 10 return .right-low; right-high; right-sum/ */
/* 11 else return .cross-low; cross-high; cross-sum/ */

int *maximum_crossing_subarray(int A[], int low, int mid, int high) {

}

int *maximum_subarray(int A[], int low, int high) {

}
