// -*- coding: utf-8 -*-
// Bubble sort
// ==================================
//
// Сложност в най-добрия случай(когато масивът е вече сортиран),
// алгоритъмът ще има сложност => Ω(n).
//
// Сложност => O(n^2)
//
// Полезен е за сортиране по някакъв специфичен критерий, по който
// могат да се сравняват два елемента. Може да се намери доста бързо
// най-големия/малкия елемент в масива.
//
//

#include <iostream>
using namespace std;

const unsigned LEN  = 12;
unsigned input[LEN] = {10, 1, 5, 6, 2, 3, 7, 7, 10, 0, 14, 99};

void bubble_sort()
{
    for(unsigned i=0; i<LEN; i++)
    {
        for(unsigned j=1; j<(LEN-i); j++)
        {
            if (input[j-1] > input[j])
            {
                swap(input[j-1], input[j]);
            }
        }
    }
}

int main()
{
    bubble_sort();

    for(unsigned i=0; i<LEN; i++)
        cout << input[i] << " ";

    cout << endl;

    return 0;
}
