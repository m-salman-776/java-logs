package Project101.IncidentAlterting.Classes;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Getter
public class Project {
    int id;
    String name;
    String desc;
    Map<Integer,Integer> levelEmployeeMapping;
    HashSet<Integer> employeeSet;
    public Project(String name,String desc){
        this.name = name;
        this.desc = desc;
        levelEmployeeMapping = new HashMap<>();
        employeeSet = new HashSet<>();
    }
    public void addEmployeeLevel(int employeeId, int level){
        if (!this.employeeSet.contains(employeeId)) return;
        levelEmployeeMapping.put(level,employeeId);
    }

    public void assignProject(int employeeId){
        this.employeeSet.add(employeeId);
    }

    public void unsetLevel(int level){
        levelEmployeeMapping.remove(level);
    }

    public int getEmployeeIdAtLeve(int level){
        return this.levelEmployeeMapping.get(level);
    }

}
