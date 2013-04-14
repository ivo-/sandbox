// -*- coding: utf-8 -*-
// Selection sort
// ==================================
//
// От семейството алгоритми със сложност => O(n^2). Доста неефективен
// за сортиране на големи масиви.
//
// Разделяме масива на 2 части - една за сортираните елементи и една
// за несортираните елементи. На всяка итерация вземаме най-малкия от
// несортираните елементи и го добавяме в края на сортираните и така
// докато има елементи в несортираната част.
//

#include <iostream>
using namespace std;

const int LEN  = 12;
int input[LEN] = {10, 1, 5, 6, 2, 3, 7, 7, 10, 0, 14, 99};

void selection_sort()
{
    int i, j, m, l = LEN-1;
    for(i=0; i<l; i++)
    {
        for(m=i, j=i+1; j<LEN; j++)
        {
            if (input[j] < input[m] ) m = j;
        }

        if (i != m)
        {
            swap(input[i], input[m]);
        }
    }
}

int main()
{
    selection_sort();

    for(int i=0; i<LEN; i++)
        cout << input[i] << " ";

    cout << endl;

    return 0;
}
