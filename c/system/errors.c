#include<stdlib.h>
#include<errno.h>
#include<stdio.h>
#include<string.h>

void main()
{
    errno = EACCES;
    perror("EACCES message");
    exit(0);
}
