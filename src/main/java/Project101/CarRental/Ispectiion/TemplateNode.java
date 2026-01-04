package Project101.CarRental.Ispectiion;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class TemplateNode {
    int id;
    int parentId;
    String name;
    NodeType nodeType;
    InputType inputType;
    boolean isRequired;
    List<TemplateNode> child = new ArrayList<>();
    public TemplateNode(int id, int parentId, String name,NodeType nodeType,InputType inputType){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.nodeType = nodeType;
        this.inputType = inputType;
        this.isRequired = true;
    }
}
