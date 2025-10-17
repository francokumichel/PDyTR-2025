# Práctica 3

### Ejercicio 1: Manejo de Errores de Conectividad

Usando como base el programa [ejemplo](https://github.com/pdytr/pdytr-grpc-demo.git) de gRPC, realice los siguientes experimentos para simular y observar fallos de conectividad tanto del lado del cliente como del servidor:

a) Introduzca cambios mínimos, como la inclusión de `exit()`, para provocar situaciones donde no se reciban comunicaciones o no haya un receptor disponible. Agregar screenshots de los errores encontrados.

Para este caso, tengo en cuenta dos posibles casos:

1. **El servidor se cae antes de responder**: en este caso se modifica `GreetingServiceImpl.java` agregando un `exit()` antes de que el servidor responda
<br>
    ```java
    @Override
    public void greeting(GreetingServiceOuterClass.HelloRequest request,
        StreamObserver<GreetingServiceOuterClass.HelloResponse> responseObserver) {

        System.out.println("Servidor recibió solicitud de: " + request.getName());

        // Con este exit es que simulamos la caida del servidor
        System.exit(1);

        // Esto nunca se va a ejecutar, se hace el exit antes por eso
        GreetingServiceOuterClass.HelloResponse response =
            GreetingServiceOuterClass.HelloResponse.newBuilder()
                .setGreeting("Hello there, " + request.getName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    ```
    
    Y al ejecutar servidor y cliente, ocurre un error, donde en particular se puede notar lo siguiente:
<br>
    ```java
    io.grpc.StatusRuntimeException: UNAVAILABLE
    ```
    Que es lo que se esperaba al no estar disponible el servidor.

<br>

2. **El cliente se corta antes de recibir la respuesta**: para este caso modifico el `Client.java` para que haga un `exit()` antes de usar la response como tal
<br>

    ```java
    GreetingServiceOuterClass.HelloResponse response =
    stub.greeting(request);

    // Acá es donde utiizo el exit, el cliente la ripea antes de usar la respuesta :(
    System.exit(1);

    System.out.println(response);
    ```

    En este caso desde el lado del servidor se puede observar la siguiente respuesta:
<br>
    ```powershell
    Server started
    name: "Ray"

    oct 09, 2025 4:42:58 P. M. io.grpc.netty.NettyServerTransport notifyTerminated
    INFO: Transport failed
    java.net.SocketException: Connection reset
    ```

    Que es lo que se esperaba, ya que el cliente termina antes de mostrar el resultado, y en el servidor se puede ver que proceso la request pero el cliente no esta esperando la respuesta.
<br>    

b) Configure un DEADLINE y modifique el código (agregando, por ejemplo, la función `sleep()`) para provocar la excepción correspondiente. Agregar screenshots de los errores encontrados.

La idea acá va a ser simular el caso en que el cliente tenga un tiempo máximo de espera y que el servidor sea lo suficientemente lento.

Para esto, modificamos `Client.java` agregando lo siguiente:

```java
GreetingServiceGrpc.GreetingServiceBlockingStub stub =
                GreetingServiceGrpc.newBlockingStub(channel)
                        .withDeadlineAfter(2, java.util.concurrent.TimeUnit.SECONDS);
```

Con esto seteamos que el cliente espere como mucho 2 segundos. Por otro lado, modificamos `GreetingServiceImpl.java` agregando lo siguiente:

```java
try {
    System.out.println("Iniciando conexion...");
    Thread.sleep(50000);
} catch(Exception e) {
    e.printStackTrace();
}
```

Donde lo importante es observar que se agrego un `sleep()` de 5 segundos. Con esto cuando el cliente haga una request, va a esperar 2 segundos y como el servidor tiene el sleep previamente dicho, el cliente alcanza el timeout. Desde el lado del cliente la salida es la siguiente:

```java
io.grpc.StatusRuntimeException: DEADLINE_EXCEEDED: deadline exceeded after 1969402400ns
```

### Ejercicio 2: Análisis de APIs en gRPC

Describa y analice los distintos tipos de APIs que ofrece gRPC. Con base en el análisis, elabore una conclusión sobre cuál sería la mejor opción para los siguientes escenarios:

a) Un sistema de **pub/sub**.
b) Un sistema de **archivos FTP**.

**Nota**: Desarrolle una conclusión fundamentada considerando los siguientes aspectos para ambos escenarios (pub/sub y FTP):

- **Escalabilidad**: ¿Cómo se comporta cada API en situaciones con múltiples clientes y conexiones simultáneas?
- **Consistencia vs. Disponibilidad**: ¿Qué importancia tiene mantener la consistencia de los datos frente a la disponibilidad del sistema?
- **Seguridad**: ¿Qué mecanismos de autenticación, autorización y cifrado se deben utilizar para proteger los datos y las comunicaciones?
- **Facilidad de implementación y mantenimiento**: ¿Qué tan fácil esimplementar y mantener la solución para cada API?

En la [documentación de gRPC](https://grpc.io/docs/what-is-grpc/core-concepts/#rpc-life-cycle), en la sección overview describe 4 tipos de servicios:

- **Unary RPCs**, donde el cliente envia una petición al servidor y recibe del mismo una respuesta, tal y como si fuera una función.
- **Server streaming RPCs** donde el cliente envia una petición al servidor y este responde con unsa serie de mensajes en forma de stream. El cliente lee dicho stream de datos hasta que no haya más mensajes. gRPC asegura que los mensajes mantengan el orden como si fuera una sola llamada RPC.
- **Client streaming RPCs** donde el cliente escribe una secuencia de mensajes y envia estos al servidor por medio de un stream. Una vez el cliente termino de escribir los mensajes, este espera a que el servidor los lea y retorne su respuesta. RPCs asegura que el mensaje se reciba de forma ordenada como si fuera una llamada RPC.
- **Bidirectional streaming RPCs** donde tanto cliente como servidor envian una secuencia de mensajes utilizando streams de lectura-escritura. Los dos streams operan de manera independiente, por lo que cliente y servidor pueden leer y escribir en el orden que ellos quieran. Por ejemplo, el servidor podria esperar a recibir todos los mensajes del cliente antes de escribir sus respuestas, o podria de forma alternada leer un mensaje y luego escribir uno, o en general podria hacer cualquier combinación de lecturas y escrituras. El orden de los mensajes en cada stream se preserva.

A partir de lo descrito anteriormente, podemos hacer un análisis de los diferentes tipos. 

##### Sistema de **pub/sub**:
En la [documentación de google](https://cloud.google.com/pubsub/docs/overview?hl=es-419) se define al sistema pub/sub como un servicio de mensajería asíncrona y escalable que separa los servicios que producen mensajes de aquellos que procesan esos mensajes. Pub/Sub permite crear sistemas de productores y consumidores de eventos, llamados publicadores y suscriptores. Los publicadores se comunican con los suscriptores de forma asíncrona mediante la transmisión de eventos, en lugar de llamadas de procedimiento remoto (RPC) síncronas. 

##### Sistema de archivos FTP