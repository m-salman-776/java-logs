package LLD.IncidentAlterting.Services;

import LLD.IncidentAlterting.Classes.Employee;
import LLD.IncidentAlterting.Repository.EmployeeRepo;

public class EmployeeService {
    // TODO could implement interface
    EmployeeRepo employeeRepo;
    EmployeeService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }
    public boolean addEmployee(int id,String name,String emailId,int mobileNumber){
        // todo validation
        this.employeeRepo.addEmployee(id,name,emailId,mobileNumber);
        return true;
    }

    public Employee getEmployeeById(int employeeId){
        return this.employeeRepo.getEmployeeById(employeeId);
    }
}
