package com.viettel.construction.model.api.plan;

import java.io.Serializable;
import java.util.List;

public class WoPlanDTO implements Serializable {

    private long id;
    private long ftId;
    private long staffId;
    private String code;
    private String name;
    private String planType;
    private String createdDate;
    private long createdUser;
    private String updateDate;
    private long updateBy;
    private String fromDate;
    private String toDate;
    private long status;
    private List<WoMappingPlanDTO> listData;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(long createdUser) {
        this.createdUser = createdUser;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public List<WoMappingPlanDTO> getListData() {
        return listData;
    }

    public void setListData(List<WoMappingPlanDTO> listData) {
        this.listData = listData;
    }

    public long getFtId() {
        return ftId;
    }

    public void setFtId(long ftId) {
        this.ftId = ftId;
    }
}
