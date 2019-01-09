package com.SelfServiceBarWeb.DeviceController.Files;

/**
 * Title: Response.java
 * Description: this class is object of the gateway it contain the information of the Respounce.txt.
 *
 * @author Jie Ji
 * @version 1.0
 */
public class Response {
    private int deviceNo;
    private String type;
    private int result;
    private String respounceInfo;
    private int reType;

    private String date;

    public Response() {
    }

    public Response(int deviceNo, String type, int result, String respounceInfo, int reType, String date) {
        this.deviceNo = deviceNo;
        this.type = type;
        this.result = result;
        this.respounceInfo = respounceInfo;
        this.reType = reType;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(int deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getRespounceInfo() {
        return respounceInfo;
    }

    public void setRespounceInfo(String respounceInfo) {
        this.respounceInfo = respounceInfo;
    }

    public int getReType() {
        return reType;
    }

    public void setReType(int reType) {
        this.reType = reType;
    }

    @Override
    public String toString() {
        return deviceNo + " " + type + " " + result + " " + respounceInfo + " " + reType;
    }
}
