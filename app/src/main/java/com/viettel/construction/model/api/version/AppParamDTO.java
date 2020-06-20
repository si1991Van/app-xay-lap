package com.viettel.construction.model.api.version;

public class AppParamDTO {

    private String code;
    private String name;
    private String partOrder;
    private String parType;
    private String status;
    private long appParamId;
    private String amount;
    private String confirm;//0: Thêm mới, 1: Đã phê duyệt, 2: Từ chối
    private long constructionTaskDailyId;
    private String description;
    private String updatedDate;
    private String updatedBy;
    private String createdDate;
    private String createdBy;
    private String parOrder;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPartOrder() {
        return partOrder;
    }

    public void setPartOrder(String partOrder) {
        this.partOrder = partOrder;
    }

    public String getParType() {
        return parType;
    }

    public void setParType(String partType) {
        this.parType = partType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAppParamId() {
        return appParamId;
    }

    public void setAppParamId(long appParamId) {
        this.appParamId = appParamId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Long getConstructionTaskDailyId() {
        return constructionTaskDailyId;
    }

    public void setConstructionTaskDailyId(long constructionTaskDailyId) {
        this.constructionTaskDailyId = constructionTaskDailyId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getParOrder() {
        return parOrder;
    }

    public void setParOrder(String parOrder) {
        this.parOrder = parOrder;
    }
}
