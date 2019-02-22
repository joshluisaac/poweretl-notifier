package com.powerapps.monitor.batchmanager;

public enum BMStatus {


    FAILED(1,"FAIL","Failed"),
    SUCCESSFUL(2,"SUCCESS","Successful"),
    PENDING(3,"PENDING","In Progress");

    private final int statusId;
    private final String statusCode;
    private final String statusDesc;

    BMStatus(int statusId, String statusCode, String statusDesc ){
        this.statusId = statusId;
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}


