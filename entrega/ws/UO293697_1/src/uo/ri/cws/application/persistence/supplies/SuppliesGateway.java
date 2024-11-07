package uo.ri.cws.application.persistence.supplies;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;

public interface SuppliesGateway extends Gateway<SuppliesRecord> {

    /**
     * Busca los suministros que da un proveedor por su id
     * 
     * @param id del proveedor
     * @return una lista con todos los suministros o una lista vacia si no hay
     *         registros
     */
    public List<SuppliesRecord> findByProviderId(String id);

    /**
     * Busca los suministros que sumistran la pieza dada por id
     * 
     * @param id de la pieza
     * @return una lista con todos los suministros o una lista vacia si no hay
     *         registros
     */
    public List<SuppliesRecord> findBySparePartId(String id);

    /**
     * Busca un suminstro por el proveedor id y por la pieza id
     * 
     * @param record que contiene la informacion necesaria
     * @return un optional con la pieza si exista o un optional si no
     */
    public Optional<SuppliesRecord> findBySparePartIdAndProviderId(
        SuppliesRecord record);

    public class SuppliesRecord {
        public String id;
        public int deliveryTerm;
        public double price;
        public long version;
        public String providerId;
        public String sparepartId;
    }
}
