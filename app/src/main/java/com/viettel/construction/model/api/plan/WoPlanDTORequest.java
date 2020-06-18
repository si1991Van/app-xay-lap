package com.viettel.construction.model.api.plan;

import com.viettel.construction.model.api.SysUserRequest;

import java.util.List;

public class WoPlanDTORequest {
    private SysUserRequest sysUserRequest;
    private long woPlanId;
    private WoPlanDTO woPlanDTO;
    private List<WoDTO> lstWosOfPlan;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public long getWoPlanId() {
        return woPlanId;
    }

    public void setWoPlanId(long woPlanId) {
        this.woPlanId = woPlanId;
    }

    public WoPlanDTO getWoPlanDTO() {
        return woPlanDTO;
    }

    public void setWoPlanDTO(WoPlanDTO woPlanDTO) {
        this.woPlanDTO = woPlanDTO;
    }

    public List<WoDTO> getLstWosOfPlan() {
        return lstWosOfPlan;
    }

    public void setLstWosOfPlan(List<WoDTO> lstWosOfPlan) {
        this.lstWosOfPlan = lstWosOfPlan;
    }
}
