package com.viettel.construction.model.api.plan;

import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;

import java.util.List;

public class PlanDTOResponse {
    private ResultInfo resultInfo;
    private SysUserRequest sysUser;
    private List<WoPlanDTO> woPlan;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public SysUserRequest getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserRequest sysUser) {
        this.sysUser = sysUser;
    }

    public List<WoPlanDTO> getWoPlan() {
        return woPlan;
    }

    public void setWoPlan(List<WoPlanDTO> woPlan) {
        this.woPlan = woPlan;
    }
}
