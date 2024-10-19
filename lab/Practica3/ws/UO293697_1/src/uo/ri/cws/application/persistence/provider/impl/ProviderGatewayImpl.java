package uo.ri.cws.application.persistence.provider.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class ProviderGatewayImpl implements ProviderGateway {

    @Override
    public void add(ProviderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(ProviderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<ProviderRecord> findById(String id)
        throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<ProviderRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ProviderRecord> findByNif(String nif) {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(Queries.get("TPROVIDERS_FINDBYNIF"));
            try {
               pst.setString(1, nif);
               
               
                
               return RecordAssembler.toProviderRecord(pst.executeQuery());
                
            }finally{
                if(pst!=null) {
                    pst.close();
                }
            }
        }catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
