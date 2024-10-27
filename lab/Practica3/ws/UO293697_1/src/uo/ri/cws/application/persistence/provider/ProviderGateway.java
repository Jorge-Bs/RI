package uo.ri.cws.application.persistence.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public interface ProviderGateway extends Gateway<ProviderRecord> {

    /**
     * Busca un proveedor por su nif
     * 
     * @param nif del proveedor
     * @return un Optional de proveedor si existe o un optional de null si no
     *         existe
     */
    public Optional<ProviderRecord> findByNif(String nif);

    /**
     * Busca todos los proveedores por nombre
     * 
     * @param name nombre del proveedor
     * @return una lista con todos los proveedores que se llamen igual que el
     *         parametro o una lista vacia si no hay registros
     */
    public List<ProviderRecord> findByName(String name);

    /**
     * Busca un proveedor que poseea los mismo campos de nombre, apellidos y
     * telefono
     * 
     * @param record con el proveedor a buscar
     * @return un Optional de proveedor si existe o un optional de null si no
     *         existe
     */
    public Optional<ProviderRecord> findEqualsFields(ProviderRecord name);

    public static class ProviderRecord {

        public String id;
        public String email;

        public String name;
        public String nif;
        public String phone;
        public long version;

    }
}
