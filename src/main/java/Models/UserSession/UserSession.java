package Models.UserSession;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    public String userId;
    public String type;
//    public Type type2;
    public int timestamp;
}
enum Type {
    START ,
    END,
}


