
#include <math.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>

double get_time(struct timespec start, struct timespec end)
{
    double sec = (double)(end.tv_sec - start.tv_sec);
    double nsec = (double)(end.tv_nsec - start.tv_nsec) / 1e9;
    return sec + nsec;
}

void error(char *msg) {
  perror(msg);
  exit(0);
}

int main(int argc, char *argv[]) {
  int sockfd, portno;
  double inicial_time, end_time, times_sum = 0.0, elapseds_times_sum = 0.0;
  struct sockaddr_in serv_addr;
  struct hostent *server;

  int exp = 0;
  scanf("%d", &exp);

  if (argc < 3) {
    fprintf(stderr, "Client:: usage %s hostname port\n", argv[0]);
    exit(0);
  }

  // Toma el numero de puerto de los argumentos
  portno = atoi(argv[2]);

  // Crea el file descriptor del socket para la conexion
  // 	AF_INET - familia del protocolo - ipv4 protocols internet
  // 	SOCK_STREAM - tipo de socket
  sockfd = socket(AF_INET, SOCK_STREAM, 0);

  if (sockfd < 0) {
    error("Client:: ERROR opening socket");
  }

  // Toma la direccion del server de los argumentos
  server = gethostbyname(argv[1]);
  if (server == NULL) {
    fprintf(stderr, "Client:: ERROR, no such host\n");
    exit(0);
  }
  bzero((char *)&serv_addr, sizeof(serv_addr));
  serv_addr.sin_family = AF_INET;

  // Copia la direccion ip y el puerto del servidor a la estructura del socket
  bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr,
        server->h_length);
  serv_addr.sin_port = htons(portno);

  // Descriptor - direccion - tamaño direccion
  while (connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0);

  // Comienzo de envio de mensajes
  int msg_len = lround(pow(10, exp));
  char buffer[lround(pow(10, 6))];
  struct timespec t_start, t_end;
  
  printf("Client:: Realizando experimento para 10^%d bytes\n", exp);

  // Comienzo de medición del tiempo
  clock_gettime(CLOCK_REALTIME, &t_start);

  // Enviamos tamaño de mensaje
  if (write(sockfd, &msg_len, sizeof(msg_len)) < 0) {
    error("Client:: ERROR writing message size");
  }
  
  bzero(buffer, msg_len);
  memset(buffer, 'A', msg_len);

  // Enviamos el mensaje
  printf("Client:: Sending a %d (10^%d) bytes long message\n", msg_len, exp);
  if (write(sockfd, buffer, msg_len) < 0) {
    error("Client:: ERROR writing to socket");
  }
  
  // Esperamos la confirmacion del servidor
  read(sockfd, buffer, strlen("Received."));
  
  // Fin de medición del tiempo
  clock_gettime(CLOCK_REALTIME, &t_end);
  double elapsed_time = get_time(t_start, t_end) * 1000; // en ms

  printf("Client:: Tiempo total (ida y vuelta): %f ms\n", elapsed_time);
  printf("Client:: Tiempo one-way (estimado): %f ms\n", elapsed_time / 2);

  close(sockfd);

  return 0;
}
