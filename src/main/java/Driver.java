import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

class Driver {
    @AllArgsConstructor
    @Getter
    static  class Entity {
        public String id;
        public String parentId;
        public String name;
        public String type;
    }

    static Map<String,List<Entity>> map;
    static List<String> ans;
    public static  void makeGraph(List<Entity>tasksList){
        map = new HashMap<>();
        for (Entity task : tasksList){
           map.putIfAbsent(task.parentId,new ArrayList<>());
           map.get(task.parentId).add(task);
        }
    }
    static void dfs(Entity task){
        ans.add(task.name);
        for (Entity child : map.getOrDefault(task.id,new ArrayList<>())){
            dfs(child);
        }
    }

    public static void main(String []args){
        ans = new ArrayList<>();
//        Task parent = new Task("p1",0,"Project");
//        Task epic1 = new Task(2,1,"epic1");
//        Task epic2 = new Task(3,1,"epic2");
//        Task taks1 = new Task(4,2,"task1");
//        Task task2 = new Task(4,3,"task2");
//        List<Task> taskList = List.of(parent,epic1,epic2,taks1,task2);
//        makeGraph(taskList);
//        dfs(map.get(0).get(0));
//        for (String task : ans){
//            System.out.println(task);
//        }
        System.out.println("done");
    }
}