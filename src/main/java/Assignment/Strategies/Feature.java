package Assignment.Strategies;

import Assignment.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Feature {
    public int featureId;
    public String name;
    public Status status;
    public Feature(int featureId, String name, Status status) {
        this.featureId = featureId;
        this.name = name;
        this.status = status;
    }
}
