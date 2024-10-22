package uo.ri.cws.application.persistence.supplies;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;

public interface SuppliesGateway extends Gateway<SuppliesRecord> {
    
    public List<SuppliesRecord> findByProviderId(String id);
    
    public List<SuppliesRecord> findBySparePartId(String id);

    public class SuppliesRecord{
        public String id;
        public int deliveryTerm;
        public double price;
        public long version;
        public String providerId;
        public String sparepartId;
    }
}
