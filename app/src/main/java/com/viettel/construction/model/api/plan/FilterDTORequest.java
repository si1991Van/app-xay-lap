package com.viettel.construction.model.api.plan;

public class FilterDTORequest {
    private String state;
    private String parType;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParType() {
        return parType;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }
}
