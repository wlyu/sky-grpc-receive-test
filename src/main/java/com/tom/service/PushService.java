package com.tom.service;

import com.tom.service.grpc.ExportMetricValue;

public interface PushService {
    void pushSwMetrics(ExportMetricValue swMetricsVO);
}
