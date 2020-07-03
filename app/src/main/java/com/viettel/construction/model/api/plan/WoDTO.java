package com.viettel.construction.model.api.plan;

import java.io.Serializable;

public class WoDTO implements Serializable {
    private long woId;
    private String woCode;
    private String woName;
    private String woTypeId;
    private String woTypeName;
    private long trId;
    private String state;
    private long constructionId;
    private String constructionName;
    private long catWorkItemTypeId;
    private String catWorkItemTypeName;
    private String stationCode;
    private String userCreated;
    private String createdDate;
    private String finishDate;
    private int qoutaTime;
    private String executeMethod;
    private String quantityValue;
    private String cdLevel1;
    private String cdLevel2;
    private String cdLevel3;
    private String cdLevel1Name;
    private String cdLevel2Name;
    private String cdLevel3Name;
    private long ftId;
    private String ftName;
    private String acceptTime;
    private String endTime;
    private String executeLat;
    private String executeLong;
    private int status;
    private long totalMonthPlanId;
    private String totalMonthPlanName;
    private long moneyValue;
    private String moneyFlowBill;
    private String moneyFlowDate;
    private long moneyFlowValue;
    private long apConstructionType;
    private long apWorkSrc;
    private boolean inPlan;
    private boolean canFinish;
    private double remainLength;
    private String doneChecklistNumber;
    private boolean ft;


    public long getWoId() {
        return woId;
    }

    public void setWoId(long woId) {
        this.woId = woId;
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

    public String getWoTypeId() {
        return woTypeId;
    }

    public void setWoTypeId(String woTypeId) {
        this.woTypeId = woTypeId;
    }

    public long getTrId() {
        return trId;
    }

    public void setTrId(long trId) {
        this.trId = trId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(long constructionId) {
        this.constructionId = constructionId;
    }

    public long getCatWorkItemTypeId() {
        return catWorkItemTypeId;
    }

    public void setCatWorkItemTypeId(long catWorkItemTypeId) {
        this.catWorkItemTypeId = catWorkItemTypeId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getQoutaTime() {
        return qoutaTime;
    }

    public void setQoutaTime(int qoutaTime) {
        this.qoutaTime = qoutaTime;
    }

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public String getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(String quantityValue) {
        this.quantityValue = quantityValue;
    }

    public String getCdLevel1() {
        return cdLevel1;
    }

    public void setCdLevel1(String cdLevel1) {
        this.cdLevel1 = cdLevel1;
    }

    public String getCdLevel2() {
        return cdLevel2;
    }

    public void setCdLevel2(String cdLevel2) {
        this.cdLevel2 = cdLevel2;
    }

    public long getFtId() {
        return ftId;
    }

    public void setFtId(long ftId) {
        this.ftId = ftId;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExecuteLat() {
        return executeLat;
    }

    public void setExecuteLat(String executeLat) {
        this.executeLat = executeLat;
    }

    public String getExecuteLong() {
        return executeLong;
    }

    public void setExecuteLong(String executeLong) {
        this.executeLong = executeLong;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTotalMonthPlanId() {
        return totalMonthPlanId;
    }

    public void setTotalMonthPlanId(long totalMonthPlanId) {
        this.totalMonthPlanId = totalMonthPlanId;
    }

    public long getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(long moneyValue) {
        this.moneyValue = moneyValue;
    }

    public String getMoneyFlowBill() {
        return moneyFlowBill;
    }

    public void setMoneyFlowBill(String moneyFlowBill) {
        this.moneyFlowBill = moneyFlowBill;
    }

    public String getMoneyFlowDate() {
        return moneyFlowDate;
    }

    public void setMoneyFlowDate(String moneyFlowDate) {
        this.moneyFlowDate = moneyFlowDate;
    }

    public long getMoneyFlowValue() {
        return moneyFlowValue;
    }

    public void setMoneyFlowValue(long moneyFlowValue) {
        this.moneyFlowValue = moneyFlowValue;
    }

    public long getApConstructionType() {
        return apConstructionType;
    }

    public void setApConstructionType(long apConstructionType) {
        this.apConstructionType = apConstructionType;
    }

    public long getApWorkSrc() {
        return apWorkSrc;
    }

    public void setApWorkSrc(long apWorkSrc) {
        this.apWorkSrc = apWorkSrc;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public void setConstructionName(String constructionName) {
        this.constructionName = constructionName;
    }

    public String getCatWorkItemTypeName() {
        return catWorkItemTypeName;
    }

    public void setCatWorkItemTypeName(String catWorkItemTypeName) {
        this.catWorkItemTypeName = catWorkItemTypeName;
    }

    public String getCdLevel2Name() {
        return cdLevel2Name;
    }

    public void setCdLevel2Name(String cdLevel2Name) {
        this.cdLevel2Name = cdLevel2Name;
    }

    public String getFtName() {
        return ftName;
    }

    public void setFtName(String ftName) {
        this.ftName = ftName;
    }

    public String getTotalMonthPlanName() {
        return totalMonthPlanName;
    }

    public void setTotalMonthPlanName(String totalMonthPlanName) {
        this.totalMonthPlanName = totalMonthPlanName;
    }

    public String getWoTypeName() {
        return woTypeName;
    }

    public void setWoTypeName(String woTypeName) {
        this.woTypeName = woTypeName;
    }

    public boolean isInPlan() {
        return inPlan;
    }

    public void setInPlan(boolean inPlan) {
        this.inPlan = inPlan;
    }

    public boolean isCanFinish() {
        return canFinish;
    }

    public void setCanFinish(boolean canFinish) {
        this.canFinish = canFinish;
    }

    public double getRemainLength() {
        return remainLength;
    }

    public void setRemainLength(double remainLength) {
        this.remainLength = remainLength;
    }

    public String getDoneCheckListNumber() {
        return doneChecklistNumber;
    }

    public void setDoneCheckListNumber(String doneCheckListNumber) {
        this.doneChecklistNumber = doneCheckListNumber;
    }

    public String getCdLevel1Name() {
        return cdLevel1Name;
    }

    public void setCdLevel1Name(String cdLevel1Name) {
        this.cdLevel1Name = cdLevel1Name;
    }

    public String getCdLevel3Name() {
        return cdLevel3Name;
    }

    public void setCdLevel3Name(String cdLevel3Name) {
        this.cdLevel3Name = cdLevel3Name;
    }

    public boolean isFt() {
        return ft;
    }

    public void setFt(boolean ft) {
        ft = ft;
    }

    public String getCdLevel3() {
        return cdLevel3;
    }

    public void setCdLevel3(String cdLevel3) {
        this.cdLevel3 = cdLevel3;
    }

}
