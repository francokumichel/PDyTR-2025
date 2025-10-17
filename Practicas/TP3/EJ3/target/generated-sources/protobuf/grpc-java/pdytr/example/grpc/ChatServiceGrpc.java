package pdytr.example.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Servicio principal
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: ChatService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "pdytr.example.grpc.ChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage,
      pdytr.example.grpc.Confirm> getConnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Connect",
      requestType = pdytr.example.grpc.ChatMessage.class,
      responseType = pdytr.example.grpc.Confirm.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage,
      pdytr.example.grpc.Confirm> getConnectMethod() {
    io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage, pdytr.example.grpc.Confirm> getConnectMethod;
    if ((getConnectMethod = ChatServiceGrpc.getConnectMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getConnectMethod = ChatServiceGrpc.getConnectMethod) == null) {
          ChatServiceGrpc.getConnectMethod = getConnectMethod =
              io.grpc.MethodDescriptor.<pdytr.example.grpc.ChatMessage, pdytr.example.grpc.Confirm>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Connect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pdytr.example.grpc.ChatMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pdytr.example.grpc.Confirm.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("Connect"))
              .build();
        }
      }
    }
    return getConnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage,
      pdytr.example.grpc.Confirm> getDisconnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Disconnect",
      requestType = pdytr.example.grpc.ChatMessage.class,
      responseType = pdytr.example.grpc.Confirm.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage,
      pdytr.example.grpc.Confirm> getDisconnectMethod() {
    io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage, pdytr.example.grpc.Confirm> getDisconnectMethod;
    if ((getDisconnectMethod = ChatServiceGrpc.getDisconnectMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getDisconnectMethod = ChatServiceGrpc.getDisconnectMethod) == null) {
          ChatServiceGrpc.getDisconnectMethod = getDisconnectMethod =
              io.grpc.MethodDescriptor.<pdytr.example.grpc.ChatMessage, pdytr.example.grpc.Confirm>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Disconnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pdytr.example.grpc.ChatMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pdytr.example.grpc.Confirm.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("Disconnect"))
              .build();
        }
      }
    }
    return getDisconnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage,
      pdytr.example.grpc.ChatMessageFromServer> getChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Chat",
      requestType = pdytr.example.grpc.ChatMessage.class,
      responseType = pdytr.example.grpc.ChatMessageFromServer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage,
      pdytr.example.grpc.ChatMessageFromServer> getChatMethod() {
    io.grpc.MethodDescriptor<pdytr.example.grpc.ChatMessage, pdytr.example.grpc.ChatMessageFromServer> getChatMethod;
    if ((getChatMethod = ChatServiceGrpc.getChatMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getChatMethod = ChatServiceGrpc.getChatMethod) == null) {
          ChatServiceGrpc.getChatMethod = getChatMethod =
              io.grpc.MethodDescriptor.<pdytr.example.grpc.ChatMessage, pdytr.example.grpc.ChatMessageFromServer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Chat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pdytr.example.grpc.ChatMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pdytr.example.grpc.ChatMessageFromServer.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("Chat"))
              .build();
        }
      }
    }
    return getChatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub>() {
        @java.lang.Override
        public ChatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceStub(channel, callOptions);
        }
      };
    return ChatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub>() {
        @java.lang.Override
        public ChatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub>() {
        @java.lang.Override
        public ChatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceFutureStub(channel, callOptions);
        }
      };
    return ChatServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Servicio principal
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Conectar usuario
     * </pre>
     */
    default void connect(pdytr.example.grpc.ChatMessage request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.Confirm> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Desconectar usuario
     * </pre>
     */
    default void disconnect(pdytr.example.grpc.ChatMessage request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.Confirm> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisconnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Chat grupal con streaming bidireccional
     * </pre>
     */
    default io.grpc.stub.StreamObserver<pdytr.example.grpc.ChatMessage> chat(
        io.grpc.stub.StreamObserver<pdytr.example.grpc.ChatMessageFromServer> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getChatMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChatService.
   * <pre>
   * Servicio principal
   * </pre>
   */
  public static abstract class ChatServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChatServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChatService.
   * <pre>
   * Servicio principal
   * </pre>
   */
  public static final class ChatServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ChatServiceStub> {
    private ChatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Conectar usuario
     * </pre>
     */
    public void connect(pdytr.example.grpc.ChatMessage request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.Confirm> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Desconectar usuario
     * </pre>
     */
    public void disconnect(pdytr.example.grpc.ChatMessage request,
        io.grpc.stub.StreamObserver<pdytr.example.grpc.Confirm> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisconnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Chat grupal con streaming bidireccional
     * </pre>
     */
    public io.grpc.stub.StreamObserver<pdytr.example.grpc.ChatMessage> chat(
        io.grpc.stub.StreamObserver<pdytr.example.grpc.ChatMessageFromServer> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getChatMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChatService.
   * <pre>
   * Servicio principal
   * </pre>
   */
  public static final class ChatServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Conectar usuario
     * </pre>
     */
    public pdytr.example.grpc.Confirm connect(pdytr.example.grpc.ChatMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Desconectar usuario
     * </pre>
     */
    public pdytr.example.grpc.Confirm disconnect(pdytr.example.grpc.ChatMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisconnectMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChatService.
   * <pre>
   * Servicio principal
   * </pre>
   */
  public static final class ChatServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Conectar usuario
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<pdytr.example.grpc.Confirm> connect(
        pdytr.example.grpc.ChatMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Desconectar usuario
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<pdytr.example.grpc.Confirm> disconnect(
        pdytr.example.grpc.ChatMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisconnectMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONNECT = 0;
  private static final int METHODID_DISCONNECT = 1;
  private static final int METHODID_CHAT = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONNECT:
          serviceImpl.connect((pdytr.example.grpc.ChatMessage) request,
              (io.grpc.stub.StreamObserver<pdytr.example.grpc.Confirm>) responseObserver);
          break;
        case METHODID_DISCONNECT:
          serviceImpl.disconnect((pdytr.example.grpc.ChatMessage) request,
              (io.grpc.stub.StreamObserver<pdytr.example.grpc.Confirm>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CHAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.chat(
              (io.grpc.stub.StreamObserver<pdytr.example.grpc.ChatMessageFromServer>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getConnectMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              pdytr.example.grpc.ChatMessage,
              pdytr.example.grpc.Confirm>(
                service, METHODID_CONNECT)))
        .addMethod(
          getDisconnectMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              pdytr.example.grpc.ChatMessage,
              pdytr.example.grpc.Confirm>(
                service, METHODID_DISCONNECT)))
        .addMethod(
          getChatMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              pdytr.example.grpc.ChatMessage,
              pdytr.example.grpc.ChatMessageFromServer>(
                service, METHODID_CHAT)))
        .build();
  }

  private static abstract class ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pdytr.example.grpc.ChatServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatService");
    }
  }

  private static final class ChatServiceFileDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier {
    ChatServiceFileDescriptorSupplier() {}
  }

  private static final class ChatServiceMethodDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ChatServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatServiceFileDescriptorSupplier())
              .addMethod(getConnectMethod())
              .addMethod(getDisconnectMethod())
              .addMethod(getChatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
