package Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    public String orderId;
    public String userId;
    public String productId;
    public Double amount ;
    public String toString(){
        return this.userId + " " + this.orderId + " " + this.productId +  " " + this.amount;
    }
}