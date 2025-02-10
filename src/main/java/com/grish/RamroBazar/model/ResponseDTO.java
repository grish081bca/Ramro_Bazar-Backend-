package com.grish.RamroBazar.model;

import java.util.Map;

public class ResponseDTO {
    private String status;
    private String code;
    private String message;
    private Map<String,Object> details;

    public ResponseDTO(String status, String code, String message, Map<String,Object> details) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }
}
