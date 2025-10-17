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

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        error("Server:: ERROR opening socket");
    }

    if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0) {
        error("Server:: ERROR setting SO_REUSEADDR");
    }

    bzero((char *)&serv_addr, sizeof(serv_addr));

    portno = atoi(argv[1]);
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);

    if (bind(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        error("Server:: ERROR on binding");
    }

    listen(sockfd, 5);
    printf("Server:: Waiting for client...\n");

    clilen = sizeof(cli_addr);
    newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);
    if (newsockfd < 0) {
        error("Server:: ERROR on accept");
    }
    printf("Server:: Client connected\n");

    // buffer fijo de 10^6
    char buffer[lround(pow(10, 6))];
    int msg_len, bytes_read, offset = 0;
    
    // leer tamaño
    if (read(newsockfd, &msg_len, sizeof(msg_len)) <= 0) {
        error("Server:: ERROR reading message length");
    }
    printf("Server:: Will receive %d bytes\n", msg_len);

    bzero(buffer, msg_len);

    // leer datos hasta completar
    do {
        bytes_read = read(newsockfd, &buffer[offset], msg_len - offset);
        if (bytes_read <= 0) break;
        offset += bytes_read;
        printf("Server:: Read %d bytes (total: %d/%d)\n", bytes_read, offset, msg_len);
    } while (offset < msg_len);

    // verificación mínima
    for (int j = 0; j < msg_len; j++) {
        if (buffer[j] != 'A') {
            error("Server:: ERROR wrong data received");
        }
    }

    printf("Server:: Length of message received: %d\n", offset);

    // enviar ack
    write(newsockfd, "Received.", strlen("Received."));

    close(newsockfd);
    close(sockfd);
    printf("Server:: Finished.\n");

    return 0;
}
