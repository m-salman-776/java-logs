package Project101.Splitwise.code;

import java.util.ArrayList;
import java.util.List;

public class GroupControllers {
    List<Group> groupList;
    public GroupControllers(){
        groupList = new ArrayList<>();
    }
    public void createNewGroup(String groupId, String groupName, User createdByUser){
        Group group = new Group(groupName);
        group.addMember(createdByUser);
        groupList.add(group);
    }

    public Group getGroup(long id){
        for (Group group : groupList){
            if (group.getId() == id) return group;
        }
        return null;
    }
}
