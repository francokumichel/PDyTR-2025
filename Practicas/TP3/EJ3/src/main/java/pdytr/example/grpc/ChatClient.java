package pdytr.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su nombre: ");
        String name = scanner.nextLine();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        ChatServiceGrpc.ChatServiceBlockingStub blockingStub = ChatServiceGrpc.newBlockingStub(channel);
        ChatServiceGrpc.ChatServiceStub asyncStub = ChatServiceGrpc.newStub(channel);

        // Hacemos la conexión inicial
        Confirm confirm = blockingStub.connect(ChatMessage.newBuilder().setFrom(name).build());
        System.out.println(confirm.getMessage());

        // Aca es donde podemos ver lo del streaming bidireccional
        StreamObserver<ChatMessage> requestObserver = asyncStub.chat(new StreamObserver<ChatMessageFromServer>() {
            @Override
            public void onNext(ChatMessageFromServer value) {
                System.out.printf("[%s] %s: %s%n",
                        value.getTimestamp().getSeconds(),
                        value.getFrom(),
                        value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Ups... hubo un error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Chat finalizado.");
            }
        });

        // Esto es para el envío de mensajes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            blockingStub.disconnect(ChatMessage.newBuilder().setFrom(name).build());
            channel.shutdown();
        }));

        while (true) {
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("/salir")) {
                blockingStub.disconnect(ChatMessage.newBuilder().setFrom(name).build());
                channel.shutdown();
                break;
            }
            requestObserver.onNext(ChatMessage.newBuilder().setFrom(name).setMessage(msg).build());
        }
    }
}
