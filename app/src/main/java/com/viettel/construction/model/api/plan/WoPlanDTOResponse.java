package com.viettel.construction.model.api.plan;

import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;

import java.util.List;

public class WoPlanDTOResponse {
    private List<WoPlanDTO> woPlan;
    private List<WoMappingPlanDTO> woMappingPlanDTO;
    private ResultInfo resultInfo;
    private SysUserRequest sysUser;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<WoPlanDTO> getListWOPlan() {
        return woPlan;
    }

    public void setListWOPlan(List<WoPlanDTO> woPlan) {
        this.woPlan = woPlan;
    }

    public SysUserRequest getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserRequest sysUser) {
        this.sysUser = sysUser;
    }

    public List<WoMappingPlanDTO> getWoMappingPlanDTO() {
        return woMappingPlanDTO;
    }

    public void setWoMappingPlanDTO(List<WoMappingPlanDTO> woMappingPlanDTO) {
        this.woMappingPlanDTO = woMappingPlanDTO;
    }

    public List<WoPlanDTO> getWoPlan() {
        return woPlan;
    }

    public void setWoPlan(List<WoPlanDTO> woPlan) {
        this.woPlan = woPlan;
    }
}
