package cn.itcast.domain;

import java.io.Serializable;
import java.util.List;

public class SelectedId implements Serializable {

    private String empId;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "SelectedId{" +
                "empId='" + empId + '\'' +
                '}';
    }
}
