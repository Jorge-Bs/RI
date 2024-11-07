package uo.ri.cws.application.persistence.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public interface OrderGateway extends Gateway<OrderRecord> {

    /**
     * Busca un pedido por el provedor id
     * 
     * @param providerId
     * @return lista de pedidos al proveedor
     */
    public List<OrderRecord> findByProviderId(String providerId);

    /**
     * Busca un pedido por el codigo de orden (no es el mismo que el id)
     * 
     * @param order code
     * @return Un optional de order recor si existe o un optional of null si no
     *         existe
     */
    public Optional<OrderRecord> findByCode(String code);

    /**
     * Busca una lista de order por el id de proveedor y por estado de la order
     * 
     * @param record
     * @return lista con las order record encontradas o una lista vacia
     */
    public List<OrderRecord> findByStateAndProviderID(OrderRecord record);

    public static class OrderRecord {

        public String id;
        public double amount;

        public String code;
        public LocalDate orderDate;
        public LocalDate receptionDate;
        public String state;
        public long version;
        public String providerId;
    }
}
