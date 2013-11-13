/*
 * Heap sort
 * ----------
 *
 * ASYMPTOTIC TIME:
 *
 *  T(n) = Θ(nlgn)
 *
 * COMMENTARY:
 *
 * Unlike merge sort it always sorts in place: only a constant number of array
 * elements are stored outside the input array at any time. It uses a data
 * structure named "heap"(max) to manage information.
 *
 */


/*
 * (binary) Heap
 * ----------
 *
 * Array object that we can view as a nearly complete binary tree. Only the
 * lowest level of that three is possibly not complete and it is filled from the
 * left up to a point. Each node of the three corresponds to an element of the
 * array.
 *
 *   parent(i) -> i/2 (float)
 *   left(i)   -> i*2
 *   right(i)  -> i*2 + 1
 *
 *
 * A[PARENT(i)] >= A[i] -> MAX-HEAP-PROPETY for all nodes, but the root.
 * A[PARENT(i)] <= A[i] -> MIN-HEAP-PROPETY for all nodes, but the root.
 *
 * Height of a node -> number of edges on the longest simple downward path from
 * the node to a leaf. For n elements heap it is lgn.
 *
 * Basic heap operations take Θ(lgn) time.
 *
 */
