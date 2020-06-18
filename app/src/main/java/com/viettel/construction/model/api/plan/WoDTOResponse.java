package com.viettel.construction.model.api.plan;

import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;

import java.util.List;

public class WoDTOResponse {
    private ResultInfo resultInfo;
    private SysUserRequest sysUser;
    private List<WoDTO> lstWos;
    private List<WoMappingChecklistDTO> lstChecklistsOfWo;

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

    public List<WoDTO> getLstWos() {
        return lstWos;
    }

    public void setLstWos(List<WoDTO> lstWos) {
        this.lstWos = lstWos;
    }

    public List<WoMappingChecklistDTO> getLstChecklistsOfWo() {
        return lstChecklistsOfWo;
    }

    public void setLstChecklistsOfWo(List<WoMappingChecklistDTO> lstChecklistsOfWo) {
        this.lstChecklistsOfWo = lstChecklistsOfWo;
    }
}
