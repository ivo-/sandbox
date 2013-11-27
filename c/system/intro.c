/* fork - create new process */
/* exec - execute new program in current process */
/* exit - exit current process */
/* wait - wait child process */

/* errno - global variable containing error number. It is basically integer but
   there are defined symbolic constants for every error number. Its value is
   updated after system function returns -1. */
/*  */
/* ENOENT - file is not found */
/* EACCES - permission denied */
/* ... */
#include<errno.h>               /* errno definition and meanings */
#include<stdio.h>               /* error util functions */
#include<string.h>              /* fprintf .. */

void perror(const char *s);     /* Write to stdout s + current errno meaning. */
char *strerror(int errnum);     /* Returns string for given error number. */
int fprintf(FILE *stream, const char *format, ...);

long sysconf();                 /* System restrictions. */
long pathconf();                /* Files and dirs restrictions. */

/* System data types. */
#include<sys/types.t>


/* File descriptor - 0 stdin, 1 stdout, 2 stderr  */
#include<unistd.h>
STDIN_FILENO;
STDOUT_FILENO;
STDERR_FILENO;

/* File mode - r, w, r/w. */
/* File offset/pointer - r/w position, offset from start in bits.  */
#include<sys/types.h>
#include<sys/stat.h>
#include<fcnil.h>               /* oflag constants */

/* O_RDONLY, O_WRONLY, O_RDWR */
/* O_CREAT  - creates file if it does not exist. */
/* O_EXCL   - with O_CREAT returns error if file exists. */
/* O_TRUNC  - delete file contents if it exists. */
/* O_APPEND - append to file when writing.*/
/* O_SYNC   - synchronize writing. */
/*  */
/* Mode is file mode when creating. */
/*  */
int open(char *filename, int oflag[, mode_t mode]); /* FD or -1 */
int creat(char *filename, mode_t mode);             /* FD(w) or -1, create|clear */
int close(int fd);                                  /* 0 or -1, implicit on exit */

/* Read bytes, 0(EOF), -1 */
ssize_t read(int fd, void *buffer, size_t nbytes);
ssize_t write(int fd, void *buffer, size_t nbytes);
