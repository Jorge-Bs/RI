package uo.ri.cws.application.persistence.orderline;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway.OrderLineRecord;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {

    public List<OrderLineRecord> findAllbyOrderId(String id);
    
    
    public static class OrderLineRecord {

        public double price;
        public int quantity;
        public String  sparePartId;
        public String orderId;
    }
}
