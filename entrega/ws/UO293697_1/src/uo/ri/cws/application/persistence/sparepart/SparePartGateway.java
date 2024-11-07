package uo.ri.cws.application.persistence.sparepart;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;

public interface SparePartGateway extends Gateway<SparePartRecord> {

    /**
     * Busca una SparePArt por el codigo de esta
     * 
     * @param code de la spare part
     * @return un optional con la pieza o un optional de null si no existe
     */
    public Optional<SparePartRecord> findByCode(String code);

    public class SparePartRecord {
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
