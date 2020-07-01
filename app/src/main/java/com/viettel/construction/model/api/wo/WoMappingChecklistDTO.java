package com.viettel.construction.model.api.wo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WoMappingChecklistDTO implements Serializable {
    private long id;
    private long woId;
    private String woName;
    private long checklistId;
    private String checklistName;
    private String state;
    private long status;
    private long quantityByDate;
    private List<ImgChecklistDTO> lstImgs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWoId() {
        return woId;
    }

    public void setWoId(long woId) {
        this.woId = woId;
    }

    public String getWoName() {
        return woName;
    }

    public void setWoName(String woName) {
        this.woName = woName;
    }

    public long getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(long checklistId) {
        this.checklistId = checklistId;
    }

    public String getChecklistName() {
        return checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public List<ImgChecklistDTO> getLstImgs() {
        return lstImgs;
    }

    public void setLstImgs(List<ImgChecklistDTO> lstImgs) {
        this.lstImgs = lstImgs;
    }

    public long getQuantityByDate() {
        return quantityByDate;
    }

    public void setQuantityByDate(long quantityByDate) {
        this.quantityByDate = quantityByDate;
    }
}
