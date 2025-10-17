package pdytr.example.grpc;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private static final Set<StreamObserver<ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();
    private static final List<String> history = Collections.synchronizedList(new ArrayList<>());
    private static final Path historyFile = Paths.get("historial_chat.txt");

    @Override
    public void connect(ChatMessage request, StreamObserver<Confirm> responseObserver) {
        String welcomeMsg = "Bienvenido " + request.getFrom() + " al chat grupal!";
        System.out.println(welcomeMsg);
        responseObserver.onNext(Confirm.newBuilder().setMessage(welcomeMsg).setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void disconnect(ChatMessage request, StreamObserver<Confirm> responseObserver) {
        String byeMsg = request.getFrom() + " se ha desconectado.";
        System.out.println(byeMsg);
        responseObserver.onNext(Confirm.newBuilder().setMessage(byeMsg).setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ChatMessage> chat(StreamObserver<ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<ChatMessage>() {
            @Override
            public void onNext(ChatMessage value) {
                String content = value.getMessage();

                if (content.equalsIgnoreCase("/historial")) {
                    sendHistory(responseObserver);
                    return;
                }

                String record = String.format("[%s] %s: %s",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd - HH:mm:ss")),
                        value.getFrom(), content);

                history.add(record);
                saveToFile(record);

                ChatMessageFromServer msg = ChatMessageFromServer.newBuilder()
                        .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
                        .setFrom(value.getFrom())
                        .setMessage(content)
                        .build();

                for (StreamObserver<ChatMessageFromServer> observer : observers) {
                    observer.onNext(msg);
                }

                System.out.println(record);
            }

            @Override
            public void onError(Throwable t) {
                observers.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
                responseObserver.onCompleted();
            }
        };
    }

    private void sendHistory(StreamObserver<ChatMessageFromServer> observer) {
        try {
            List<String> lines = Files.readAllLines(historyFile);
            for (String line : lines) {
                observer.onNext(ChatMessageFromServer.newBuilder()
                        .setFrom("Servidor")
                        .setMessage(line)
                        .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
                        .build());
            }
        } catch (IOException e) {
            observer.onNext(ChatMessageFromServer.newBuilder()
                    .setFrom("Servidor")
                    .setMessage("No hay historial disponible.")
                    .build());
        }
    }

    private void saveToFile(String message) {
        try {
            Files.write(historyFile, (message + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al guardar historial: " + e.getMessage());
        }
    }
}
