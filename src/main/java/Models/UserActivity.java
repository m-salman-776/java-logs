package Models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserActivity {
    public String userId;
    public int timestamp;
    public String type;
}
