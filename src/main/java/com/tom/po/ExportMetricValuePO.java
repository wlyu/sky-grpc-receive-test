package com.tom.po;

public class ExportMetricValuePO {
    private String metricName;
    private String entityName;
    private Long totalValue;
    private Long num;

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
