package com.viettel.construction.model.api.plan;

import com.viettel.construction.model.api.ResultInfo;
import com.viettel.construction.model.api.SysUserRequest;
import com.viettel.construction.model.api.version.AppParamDTO;
import com.viettel.construction.model.api.wo.MapDataWoForChartDTO;
import com.viettel.construction.model.api.wo.MapDataWoPlanForChartDTO;
import com.viettel.construction.model.api.wo.WoMappingChecklistDTO;
import com.viettel.construction.model.api.wo.WoSimpleFtDTO;
import com.viettel.construction.model.api.wo.WoWorkLogsBO;

import java.util.List;

public class WoDTOResponse {
    private ResultInfo resultInfo;
    private SysUserRequest sysUser;
    private List<WoDTO> lstWos;
    private List<WoMappingChecklistDTO> lstChecklistsOfWo;
    private List<AppParamDTO> lstDataForComboBox;
    private List<WoWorkLogsBO> logs;
    private List<WoSimpleFtDTO> listFtToAssign;

    private MapDataWoForChartDTO mapDataWoForChart;
    private MapDataWoPlanForChartDTO mapDataWoPlanForChart;

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

    public List<AppParamDTO> getLstDataForComboBox() {
        return lstDataForComboBox;
    }

    public void setLstDataForComboBox(List<AppParamDTO> lstDataForComboBox) {
        this.lstDataForComboBox = lstDataForComboBox;
    }

    public List<WoWorkLogsBO> getLogs() {
        return logs;
    }

    public void setLogs(List<WoWorkLogsBO> logs) {
        this.logs = logs;
    }

    public List<WoSimpleFtDTO> getListFtToAssign() {
        return listFtToAssign;
    }

    public void setListFtToAssign(List<WoSimpleFtDTO> listFtToAssign) {
        this.listFtToAssign = listFtToAssign;
    }

    public MapDataWoForChartDTO getMapDataWoForChart() {
        return mapDataWoForChart;
    }

    public void setMapDataWoForChart(MapDataWoForChartDTO mapDataWoForChart) {
        this.mapDataWoForChart = mapDataWoForChart;
    }

    public MapDataWoPlanForChartDTO getMapDataWoPlanForChart() {
        return mapDataWoPlanForChart;
    }

    public void setMapDataWoPlanForChart(MapDataWoPlanForChartDTO mapDataWoPlanForChart) {
        this.mapDataWoPlanForChart = mapDataWoPlanForChart;
    }
}
