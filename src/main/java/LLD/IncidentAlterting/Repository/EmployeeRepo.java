package LLD.IncidentAlterting.Repository;

import LLD.IncidentAlterting.Classes.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeeRepo {
    Map<Integer, Employee> employeeMap;
    EmployeeRepo(){
        employeeMap = new HashMap<>();
    }
    public boolean addEmployee(int id,String name,String emailId,int mobileNumber){
        Employee employee = new Employee(id,name,mobileNumber,emailId);
        employeeMap.put(employee.getId(),employee);
        return true;
    }

    public Employee getEmployeeById(int id){
        return this.employeeMap.get(id);
    }
}
