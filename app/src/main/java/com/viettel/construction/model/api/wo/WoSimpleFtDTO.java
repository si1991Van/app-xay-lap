package com.viettel.construction.model.api.wo;

public class WoSimpleFtDTO {
    private long ftId;
    private String fullName;
    private long sysGroupId;
    private String employeeCode;
    private String email;

    public long getFtId() {
        return ftId;
    }

    public void setFtId(long ftId) {
        this.ftId = ftId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(long sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
