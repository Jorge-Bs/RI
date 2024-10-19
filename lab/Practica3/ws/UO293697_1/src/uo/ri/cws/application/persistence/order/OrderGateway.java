package uo.ri.cws.application.persistence.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public interface OrderGateway extends Gateway<OrderRecord> {

    
    public List<OrderRecord> findByProviderId(String providerId);
    
    public Optional<OrderRecord> findByCode(String code);
    
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
