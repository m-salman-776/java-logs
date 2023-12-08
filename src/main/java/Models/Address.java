package Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    public String stree;
    public int pinCode;
    public String country;
}
