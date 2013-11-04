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
/* socket.c                                                                   */
/* -------------------------------------------------------------------------- */

getaddrinfo();

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
