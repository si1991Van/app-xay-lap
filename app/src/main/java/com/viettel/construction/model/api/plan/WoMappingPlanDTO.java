package com.viettel.construction.model.api.plan;

import java.util.Date;

public class WoMappingPlanDTO {
    private Long id;
    private Long woPlanId;
    private String woId;
    private Date createdDate;
    private Long createdBy;
    private Long status;
    private String woCode;
    private String woName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWoPlanId() {
        return woPlanId;
    }

    public void setWoPlanId(Long woPlanId) {
        this.woPlanId = woPlanId;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getWoCode() {
        return woCode;
    }

    public void setWoCode(String woCode) {
        this.woCode = woCode;
    }

    public String getWoName() {
        return woName;
    }

    public void setWoName(String woName) {
        this.woName = woName;
    }
}
