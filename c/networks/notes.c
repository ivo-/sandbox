//==============================================================================
// Notes while learning about C sockets.
//==============================================================================

/* -------------------------------------------------------------------------- */
/* Intro                                                                      */
/* -------------------------------------------------------------------------- */

/*
  Sockets are a to speak to other programs using standard Unix file descriptors.

  Any sort of I/O in Unix is done by reading or writing to a file descriptor.
  Event Internet communication is don this way.

*/

/* Stream sockets - reliable two-way connected communication streams. Order of
   sending is the order of receiving. They will also be error-free. They use
   TCP protocol. The better half of TCP/IP. IP deals primarily with Internet
   routing and it is not responsible for data integrity. */

/* Datagram sockets - conectionles sockets, but they can be connect() if you
   really want. Once send datagram may arrive(out of order) and if does it will
   be error-free. Datagram sockets also use IP for transportation, but instead
   of TCP, UDP is used. Here you don't have to maintain open connection like in
   stram sockets, just build packet and send. Sometimes other protocols above
   UDP are concerned with data validation. Reliability here is traded for
   speed. */

/* -------------------------------------------------------------------------- */
/* datatypes                                                                  */
/* -------------------------------------------------------------------------- */

int descriptor;                 /* A socket descriptor. */

struct addrinfo {
  int ai_flags;                 // AI_PASSIVE, AI_CANONNAME, etc. =>
  int ai_family;                // AF_INET, AF_INET6, AF_UNSPEC   => IP version
  int ai_socktype;              // SOCK_STREAM, SOCK_DGRAM
  int ai_protocol;              // use 0 for "any"
  size_t ai_addrlen;            // size of ai_addr in bytes
  struct sockaddr *ai_addr;     // struct sockaddr_in or _in6
  char *ai_canonname;           // full canonical hostname
  struct addrinfo *ai_next;     // linked list, next node
};

/*! Can be cast to sockaddr_in/sockaddr_in6 and vice-versa. */
struct sockaddr {
  unsigned short sa_family;     // address family, AF_xxx
  char sa_data[14];             // 14 bytes of protocol address
};

// (IPv4 only--see struct sockaddr_in6 for IPv6)
struct sockaddr_in {
  short int sin_family;         // Address family, AF_INET
  unsigned short int sin_port;  // Port number (!htons())
  struct in_addr sin_addr;      // Internet address
  unsigned char sin_zero[8];    // Same size as struct sockaddr
};

// (IPv4 only--see struct in6_addr for IPv6)
// Internet address (a structure for historical reasons)
struct in_addr {
  uint32_t s_addr;              // that's a 32-bit int (4 bytes)
};

// (IPv6 only--see struct sockaddr_in and struct in_addr for IPv4)
struct sockaddr_in6 {
  u_int16_t sin6_family;        // address family, AF_INET6
  u_int16_t sin6_port;          // port number, Network Byte Order
  u_int32_t sin6_flowinfo;      // IPv6 flow information
  struct in6_addr sin6_addr;    // IPv6 address
  u_int32_t sin6_scope_id;      // Scope ID
};
struct in6_addr {
  unsigned char s6_addr[16]; // IPv6 address
};

/* Can be casted to both sockaddr_in or sockaddr_in6. It is useful when we
   don't know in advance what we will have to use. */
struct sockaddr_storage {
  sa_family_t ss_family; // address family

  // all this is padding, implementation specific, ignore it:
  char __ss_pad1[_SS_PAD1SIZE];
  int64_t __ss_align;
  char __ss_pad2[_SS_PAD2SIZE];
};

/* -------------------------------------------------------------------------- */
/* IP addresses                                                               */
/* -------------------------------------------------------------------------- */

/* Converts IP address in numbers-and-dots notation into in_addr or id6_addr.
   It returns -1 for error, 0 if address is messed up, 1 for success. */
struct sockaddr_in sa;                                          // IPv4
struct sockaddr_in6 sa6;                                        // IPv6
inet_pton(AF_INET, "192.0.2.1", &(sa.sin_addr));                // IPv4
inet_pton(AF_INET6, "2001:db8:63b3:1::3490", &(sa6.sin6_addr)); // IPv6

char ip4[INET_ADDRSTRLEN];      // space to hold the IPv4 string
struct sockaddr_in sa;          // pretend this is loaded with something
inet_ntop(AF_INET, &(sa.sin_addr), ip4, INET_ADDRSTRLEN);

char ip6[INET6_ADDRSTRLEN];     // space to hold the IPv6 string
struct sockaddr_in6 sa6;        // pretend this is loaded with something
inet_ntop(AF_INET6, &(sa6.sin6_addr), ip6, INET6_ADDRSTRLEN);


/* -------------------------------------------------------------------------- */
/* Info
/* -------------------------------------------------------------------------- */

#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>

/* Fills up the basic structs that will be needed trough all the program. */
int getaddrinfo(const char *node, // Host name or IP address.
                // Port name or a service name. SEE: /etc/services
                  const char *service,
                //
                  const struct addrinfo *hints,
                  struct addrinfo **res);

/* EXAMPLE: */

int status;
struct addrinfo hints;
struct addrinfo *servinfo;
memset(&hints, 0, sizeof hints); // Make sure the struct is empty.

hints.ai_family   = AF_UNSPEC;   // Don't care IPv4 or IPv6.
hints.ai_socktype = SOCK_STREAM; // TCP stream sockets.
hints.ai_flags    = AI_PASSIVE;  // Set *node to my IP, NULL in call.

if ((status = getaddrinfo(NULL, "3490", &hints, &servinfo)) != 0) {
  fprintf(stderr, "getaddrinfo error: %s\n", gai_strerror(status));
  exit(1);
}

// servinfo now points to a linked list of 1 or more struct addrinfos
// ... do everything until you don't need servinfo anymore ....

freeaddrinfo(servinfo); // free the linked-list

/* EXAMPLE(2):  */
/* hints.ai_family   = AF_UNSPEC; */
/* hints.ai_socktype = SOCK_STREAM; */
/* status = getaddrinfo("www.example.net", "3490", &hints, &servinfo); */

/* -------------------------------------------------------------------------- */
/* Sockets
/* -------------------------------------------------------------------------- */

SOCK_STREAM;
SOCK_DGRAM;

socet();                        /* Returns a socket descriptor. */
send();                         /*  */
recv();                         /*  */

/* Functions for working with files also works fine. */
read();
write();

/* Big-Endian vs Little-Endian. How a sequence of bytes representing integers is
   stored in computer memory. In Big-endian bytes are arranged left-to right
   starting from the most senior. Little-endian - reversed. */
htons();                        /* host-to-network-short */
ntohs();                        /* network-to-host-short */
htonl();                        /* host-to-network-long */
ntohl();                        /* network-to-host-long */
