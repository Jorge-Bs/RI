package uo.ri.cws.application.persistence.orderline;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway.OrderLineRecord;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {

    
    /**
     * Busca todas las orderlines pertenecientes a una order,
     * para buscar dichas orderlines se da el id de la orde
     * @param id de la orde a buscar
     * @return lista con todas las orderlines encontradas o lista vacia
     */
    public List<OrderLineRecord> findAllbyOrderId(String id);
    
    
    public static class OrderLineRecord {

        public double price;
        public int quantity;
        public String  sparePartId;
        public String orderId;
    }
}
