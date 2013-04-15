// -*- coding: utf-8 -*-
// STD sort
// ==================================
//
// Вграден в C++. Използва оптимална комбинация от алгоритми за
// сортиране. Приема указател към първия и последния елемент на
// масива, който трябва да сортира.
//

#include <iostream>
#include <algorithm>
using namespace std;

const int LEN  = 12;
int input[LEN] = {10, 1, 5, 6, 2, 3, 7, 7, 10, 0, 14, 99};

int main()
{
    sort(input, input + LEN);

    for(unsigned i=0; i<LEN; i++)
        cout << input[i] << " ";

    cout << endl;

    return 0;
}
