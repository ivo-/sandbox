// -*- coding: utf-8 -*-
// Quick sort
// ==================================
//
// Доста бърз алгоритъм за сортиране, който има и практически
// приложения. В общия случай сложността му е => O(n*log(n)), което го
// прави подходящ за сортиране на големи масиви. В най-лошия случай
// сложността му е O(n^2).
//

#include <iostream>
using namespace std;

const int LEN  = 12;
int input[LEN] = {10, 1, 5, 6, 2, 3, 7, 7, 10, 0, 14, 99};

void quick_sort(int left, int right)
{
    if (left == right) return;

    int pivot = input[(left + right)/2];
    int i=left, j=right;

    while (i <= j)
    {
        while (input[i] < pivot) i++;
        while (input[j] > pivot) j--;

        if (i <= j)
        {
            swap(input[i], input[j]);
            i++;
            j--;
        }
    }

    if (left < j) quick_sort(left, j);
    if (right > i) quick_sort(i, right);
}

int main()
{
    quick_sort(0, LEN - 1);

    for(int i=0; i<LEN; i++)
        cout << input[i] << " ";

    cout << endl;

    return 0;
}
