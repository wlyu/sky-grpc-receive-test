package com.tom.service.impl;

import com.tom.service.PushService;
import com.tom.service.grpc.*;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;


@GRpcService
public class MetricExportServiceImpl extends MetricExportServiceGrpc.MetricExportServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String  DEBUG_ENTITY = System.getenv("DEBUG_ENTITY");

    @Value("${skywalking.metrics}")
    private String metrics;

    @Autowired
    private PushService pushService;

    @Override
    public StreamObserver<ExportMetricValue> export(StreamObserver<ExportResponse> responseObserver) {
        return new StreamObserver<ExportMetricValue>() {
            @Override
            public void onNext(ExportMetricValue value) {
                if(!StringUtils.isEmpty(DEBUG_ENTITY)
                        && value.getEntityName().contains(DEBUG_ENTITY)){
                        logger.info(value.toString());
                }
                pushService.pushSwMetrics(value);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage());
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }


    @Override
    public void subscription(SubscriptionReq request, StreamObserver<SubscriptionsResp> responseObserver) {
        logger.info("subscription");
        SubscriptionsResp.Builder builder = SubscriptionsResp.newBuilder();
        if (!StringUtils.isEmpty(metrics)) {
            String[] metricsArr = metrics.split(",");
            for (String metricsName : metricsArr) {
                builder.addMetricNames(metricsName.trim());
            }
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
