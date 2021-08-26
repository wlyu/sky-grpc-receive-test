package com.tom.service.impl;

import com.tom.po.ExportMetricValuePO;
import com.tom.service.PushService;
import com.tom.service.grpc.ExportMetricValue;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

@Service
public class PushServiceImpl implements PushService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${promethus.gateway}")
    private String pushGatewayUrl;

    @Value("${skywalking.metrics}")
    private String metrics;

    private PushGateway pushGateway;
    private final CollectorRegistry registry = new CollectorRegistry();
    private Map<String, Gauge> dataContainerMap = new HashMap<>();
    /**
     * url正则前缀
     */
    private Map<String, Pattern> urlPrefixMap = new HashMap();

    @Value("${promethus.batchIntervalMs:30000}")
    private long batchIntervalMs;

    @Value("${skywalking.urlPrefixList}")
    private String urlPrefixList;

    private List<ExportMetricValue> fromList = new ArrayList();
    private List<ExportMetricValue> toList = new ArrayList();
    private long exceptMinTime;

    private ScheduledExecutorService scheduler;



    @PostConstruct
    public void init() throws MalformedURLException {
        pushGateway = new PushGateway(new URL(pushGatewayUrl));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        exceptMinTime = Long.valueOf(simpleDateFormat.format(new Date()));

        if (!StringUtils.isEmpty(metrics)) {
            String[] metricsArr = metrics.split(",");
            for (String metricName : metricsArr) {
                Gauge data = Gauge.build()
                        .help("Metrics From Skywalking")
                        .name(metricName)
                        .labelNames("metricName", "entityName")
                        .register(registry);
                dataContainerMap.put(metricName, data);
            }
        }

        this.scheduler = Executors.newScheduledThreadPool(1);
        this.scheduler.scheduleWithFixedDelay(() -> {
            synchronized (this) {
                try {
                    flush();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }, batchIntervalMs, batchIntervalMs, TimeUnit.MILLISECONDS);

        if (!StringUtils.isEmpty(urlPrefixList)) {
            String[] urlPrefixs = urlPrefixList.split(",");
            for (String urlPrefix : urlPrefixs) {
                urlPrefix = urlPrefix.trim();
                urlPrefixMap.put(urlPrefix, Pattern.compile(urlPrefix));
            }
        }
    }

    @Override
    public void pushSwMetrics(ExportMetricValue exportMetricValue) {
        add(exportMetricValue);
    }

    private synchronized void add(ExportMetricValue exportMetricValue) {
        long timeBucket = exportMetricValue.getTimeBucket();

        if(timeBucket > exceptMinTime){
            exceptMinTime = timeBucket;
            toList = fromList;
            fromList = new ArrayList<>();
        }

        fromList.add(exportMetricValue);
    }


    private void flush() {
        logger.info("[flush.size]"+toList.size());
        if(toList.size()==0){
           return;
        }
        //如果存在,聚合相同的URL结果
        Map<String, ExportMetricValuePO> gather = new HashMap<>();

        for (ExportMetricValue curr : toList) {
            String metricName = curr.getMetricName();
            String entityName = curr.getEntityName();
            Long value = curr.getLongValue();
            if (!metricName.contains("endpoint")) {
                dataContainerMap.get(metricName).labels(metricName, entityName).set(value);
                continue;
            }
            entityName = getEntityName(curr.getEntityName());
            ExportMetricValuePO po = null;
            String key = metricName + "@" + entityName;
            if (!gather.containsKey(key)) {
                po = new ExportMetricValuePO();
                po.setMetricName(metricName);
                po.setEntityName(entityName);
                po.setTotalValue(value);
                po.setNum(1L);
            } else {
                po = gather.get(key);
                po.setTotalValue(value + po.getTotalValue());
                po.setNum(po.getNum() + 1);
            }
            gather.put(key, po);
        }
        //填充数据
        gather.forEach((k, v) -> {
            String metricName = v.getMetricName();
            String entityName = v.getEntityName();
            long num="endpoint_cpm".equals(metricName)?1L:v.getNum();
            Long value = (v.getTotalValue() / num);
            dataContainerMap.get(metricName).labels(metricName, entityName).set(value);
        });

        try {
            pushGateway.pushAdd(registry, "skywalking");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getEntityName(String entityName) {

        for (Map.Entry<String, Pattern> psItem : urlPrefixMap.entrySet()) {
            String k = psItem.getKey();
            Pattern v = psItem.getValue();
            if (v.matcher(entityName).find()) {
                return k;
            }
        }

        String uri = entityName
                .replaceAll("/\\d+/", "/{}/")
                .replaceAll("/\\d+$", "/{}")
                .replaceAll("/\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}/", "/{}/")
                .replaceAll("/\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}", "/{}")
                .replaceAll("/\\w{32}/", "/{}/")
                .replaceAll("/\\w{32}", "/{}");

        return uri;
    }

}
