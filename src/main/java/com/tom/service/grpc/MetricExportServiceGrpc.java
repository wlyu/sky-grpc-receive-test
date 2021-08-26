package com.tom.service.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: metric-exporter.proto")
public final class MetricExportServiceGrpc {

  private MetricExportServiceGrpc() {}

  public static final String SERVICE_NAME = "MetricExportService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<ExportMetricValue,
      ExportResponse> METHOD_EXPORT =
      io.grpc.MethodDescriptor.<ExportMetricValue, ExportResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "MetricExportService", "export"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              ExportMetricValue.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              ExportResponse.getDefaultInstance()))
          .setSchemaDescriptor(new MetricExportServiceMethodDescriptorSupplier("export"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<SubscriptionReq,
      SubscriptionsResp> METHOD_SUBSCRIPTION =
      io.grpc.MethodDescriptor.<SubscriptionReq, SubscriptionsResp>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "MetricExportService", "subscription"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              SubscriptionReq.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              SubscriptionsResp.getDefaultInstance()))
          .setSchemaDescriptor(new MetricExportServiceMethodDescriptorSupplier("subscription"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MetricExportServiceStub newStub(io.grpc.Channel channel) {
    return new MetricExportServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MetricExportServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MetricExportServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MetricExportServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MetricExportServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MetricExportServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<ExportMetricValue> export(
        io.grpc.stub.StreamObserver<ExportResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_EXPORT, responseObserver);
    }

    /**
     */
    public void subscription(SubscriptionReq request,
                             io.grpc.stub.StreamObserver<SubscriptionsResp> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBSCRIPTION, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_EXPORT,
            asyncClientStreamingCall(
              new MethodHandlers<
                ExportMetricValue,
                ExportResponse>(
                  this, METHODID_EXPORT)))
          .addMethod(
            METHOD_SUBSCRIPTION,
            asyncUnaryCall(
              new MethodHandlers<
                SubscriptionReq,
                SubscriptionsResp>(
                  this, METHODID_SUBSCRIPTION)))
          .build();
    }
  }

  /**
   */
  public static final class MetricExportServiceStub extends io.grpc.stub.AbstractStub<MetricExportServiceStub> {
    private MetricExportServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetricExportServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MetricExportServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetricExportServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ExportMetricValue> export(
        io.grpc.stub.StreamObserver<ExportResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_EXPORT, getCallOptions()), responseObserver);
    }

    /**
     */
    public void subscription(SubscriptionReq request,
                             io.grpc.stub.StreamObserver<SubscriptionsResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIPTION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MetricExportServiceBlockingStub extends io.grpc.stub.AbstractStub<MetricExportServiceBlockingStub> {
    private MetricExportServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetricExportServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MetricExportServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetricExportServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public SubscriptionsResp subscription(SubscriptionReq request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBSCRIPTION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MetricExportServiceFutureStub extends io.grpc.stub.AbstractStub<MetricExportServiceFutureStub> {
    private MetricExportServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetricExportServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MetricExportServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetricExportServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<SubscriptionsResp> subscription(
        SubscriptionReq request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBSCRIPTION, getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIPTION = 0;
  private static final int METHODID_EXPORT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MetricExportServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MetricExportServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIPTION:
          serviceImpl.subscription((SubscriptionReq) request,
              (io.grpc.stub.StreamObserver<SubscriptionsResp>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXPORT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.export(
              (io.grpc.stub.StreamObserver<ExportResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MetricExportServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MetricExportServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return MetricExporter.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MetricExportService");
    }
  }

  private static final class MetricExportServiceFileDescriptorSupplier
      extends MetricExportServiceBaseDescriptorSupplier {
    MetricExportServiceFileDescriptorSupplier() {}
  }

  private static final class MetricExportServiceMethodDescriptorSupplier
      extends MetricExportServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MetricExportServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MetricExportServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MetricExportServiceFileDescriptorSupplier())
              .addMethod(METHOD_EXPORT)
              .addMethod(METHOD_SUBSCRIPTION)
              .build();
        }
      }
    }
    return result;
  }
}
