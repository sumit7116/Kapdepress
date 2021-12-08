package com.kapdepress.kapdepress.models;

public class MainServicesModel {
    int id;
    String serviceName;
    int serviceImage;

    public MainServicesModel() {

    }

    public MainServicesModel(int id, String serviceName, int serviceImage) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(int serviceImage) {
        this.serviceImage = serviceImage;
    }
}
