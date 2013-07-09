#include <iostream>
#include <iomanip>
using namespace std;

#include "AVLTree.h"

int main()
{
    AVLTree<int> tree(20);

    // Balanced
    tree.Add(41);
    tree.Add(11);
    tree.Add(10);
    tree.Add(12);
    tree.Add(42);
    tree.Add(40);

    // Debalance left
    tree.Add(9);
    tree.Add(8);

    // Debalance right
    tree.Add(43);
    tree.Add(44);

    tree.Print();

    cout << "==============================" << endl;

    tree.Remove(44);
    tree.Print();

    return 0;
}
