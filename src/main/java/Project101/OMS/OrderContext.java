package Project101.OMS;

import Project101.OMS.OrderStates.OrderState;
import Project101.OMS.Services.InventoryService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderContext {
    Order order;
    OrderState orderState;
    InventoryService inventoryService;
}
