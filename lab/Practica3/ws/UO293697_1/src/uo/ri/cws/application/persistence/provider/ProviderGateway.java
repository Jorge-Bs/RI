package uo.ri.cws.application.persistence.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public interface ProviderGateway extends Gateway<ProviderRecord> {

    public Optional<ProviderRecord> findByNif(String nif);
    
    public List<ProviderRecord> findByName(String name);
    
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
