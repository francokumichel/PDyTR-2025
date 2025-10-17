
/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <math.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

void error(char *msg) {
  perror(msg);
  exit(1);
}

int main(int argc, char *argv[]) {
  int sockfd, newsockfd, portno, clilen;
  struct sockaddr_in serv_addr, cli_addr;
  int reuse = 1;

  if (argc < 2) {
    fprintf(stderr, "Server:: ERROR, no port provided\n");
    exit(1);
  }

  // crea el file descriptor del socket para la conexion
  sockfd = socket(AF_INET, SOCK_STREAM, 0);
  
  if (sockfd < 0) {
    error("Server:: ERROR opening socket");
  }

  // Para permitir la reutilización del puerto
  if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0) {
    error("Server:: ERROR setting SO_REUSEADDR");
  }

  bzero((char *)&serv_addr, sizeof(serv_addr));

  // asigna el puerto pasado por argumento
  // asigna la ip en donde escucha (su propia ip)
  portno = atoi(argv[1]);
  serv_addr.sin_family = AF_INET;
  serv_addr.sin_addr.s_addr = INADDR_ANY;
  serv_addr.sin_port = htons(portno);

  // vincula el file descriptor con la direccion y el puerto
  if (bind(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
    error("Server:: ERROR on binding");
  }

  // setea la cantidad que pueden esperar mientras se maneja una conexion
  listen(sockfd, 256);

  // se bloquea a esperar una conexion
  clilen = sizeof(cli_addr);
  newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);

  // devuelve un nuevo descriptor por el cual se van a realizar las
  // comunicaciones
  if (newsockfd < 0) {
    error("Server:: ERROR on accept");
  }

  // lee los mensajes del cliente
  // sabemos de antemano que el tamaño máximo es de 10^6 bytes
  char buffer[lround(pow(10, 6))];
  int msg_len, bytes_read;
  char ready_signal[10];

  read(newsockfd, ready_signal, strlen("READY"));
  for (int i = 1; i < 7; i++) {
    int msg_len = lround(pow(10, i));
    // longitud del mensaje que toca recibir

    bzero(buffer, msg_len);

    // lee el mensaje del cliente
    printf("Server:: Read a %d (10^%d) bytes long message\n", msg_len, i);

    bytes_read = read(newsockfd, buffer, msg_len);

    if (bytes_read < 0) {
        error("Server:: ERROR reading from socket");
    }

    printf("Server:: Length of message received: %d\n", bytes_read);
    printf("-----------------------------------------------------------\n");

    // avisa que el mensaje fue recibido
    write(newsockfd, "Received.", strlen("Received."));

  }
  close(sockfd);
  close(newsockfd);

  return 0;
}
