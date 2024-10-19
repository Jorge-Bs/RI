package uo.ri.cws.application.persistence.sparepart;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;

public interface SparePartGateway extends Gateway<SparePartRecord> {

    
    public class SparePartRecord{
        public String id;
        public String code;
        public String description;
        public int maxStock;
        public int minStock;
        public double price;
        public int stock;
        public long version;
    }
}
