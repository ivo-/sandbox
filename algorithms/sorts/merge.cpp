// -*- coding: utf-8 -*-
// Merge sort
// ==================================
//
// Разделяй и владей алгоритъм.
//
// РАЗДЕЛЯ последователността от n-елемента на n/2-елементни последователности.
// СОРТИРА под последователноститите рекурсивно.
// СЛИВА под последователноститите.
// TODO:
//

#include <iostream>
using namespace std;

const int LEN  = 12;
int input[LEN] = {10, 1, 5, 6, 2, 3, 7, 7, 10, 0, 14, 99};

void merge_sort()
{

}

int main()
{
    merge_sort();

    for(int i=0; i<LEN; i++)
        cout << input[i] << " ";

    cout << endl;

    return 0;
}
