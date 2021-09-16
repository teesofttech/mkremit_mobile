package com.teesofttech.mkremit.models;

public class ServiceVendor {
    public String id;
    public String serviceId;
    public String serviceName;
    public String variationCode;
    public String name;
    public String variationAmount;
    public String fixedPrice;
    public String convinienceFee;

    public String getConvinienceFee() {
        return convinienceFee;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVariationCode() {
        return variationCode;
    }

    public void setVariationCode(String variationCode) {
        this.variationCode = variationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariationAmount() {
        return variationAmount;
    }

    public void setVariationAmount(String variationAmount) {
        this.variationAmount = variationAmount;
    }

    public String getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(String fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public void setConvinienceFee(String convinienceFee) {
        this.convinienceFee = convinienceFee;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
