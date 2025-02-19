package com.grish.RamroBazar.model;

import org.springframework.validation.ObjectError;

import java.util.Map;

public class ResponseDTO {
    private String status;
    private String code;
    private String message;
    private Map<String,Object> details;
    private Map<String,Object> detail;

    public ResponseDTO(String status, String code, String message, Map<String,Object> details) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = details;
        this.detail = detail;
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

    public Map<String,Object> getDetail(){
        return detail;
    }

    public void setDetail(Map<String, Object> detail){
        this.detail = detail;
    }
}
