package MyGate;

import java.util.List;

public class Request {
    int id;
    int flatId;
    String purpose;
    List<Options> allowedOptions;
    Status status;

    public Request(int id, String purpose, List<Options> allowedOptions, int flatId) {
        this.id = id;
        this.allowedOptions = allowedOptions;
        this.purpose = purpose;
        this.flatId = flatId;
        this.status = Status.CREATED;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
