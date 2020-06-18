package com.viettel.construction.model.api.plan;

import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;

import java.util.List;

public class WoDTORequest {
    private SysUserRequest sysUserRequest;
    private WoDTO woDTO;
    private FilterDTORequest filter;
    private List<WoMappingChecklistDTO> lstChecklistsOfWo;

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

    public WoDTO getWoDTO() {
        return woDTO;
    }

    public void setWoDTO(WoDTO woDTO) {
        this.woDTO = woDTO;
    }

    public FilterDTORequest getFilter() {
        return filter;
    }

    public void setFilter(FilterDTORequest filter) {
        this.filter = filter;
    }

    public List<WoMappingChecklistDTO> getLstChecklistsOfWo() {
        return lstChecklistsOfWo;
    }

    public void setLstChecklistsOfWo(List<WoMappingChecklistDTO> lstChecklistsOfWo) {
        this.lstChecklistsOfWo = lstChecklistsOfWo;
    }
}

