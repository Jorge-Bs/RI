package uo.ri.cws.application.persistence.supplies.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class SuppliesGatewayImpl implements SuppliesGateway {

    @Override
    public void add(SuppliesRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(SuppliesRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<SuppliesRecord> findById(String id)
        throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<SuppliesRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SuppliesRecord> findByProviderId(String id) {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(
                Queries.get("TSUPPLIES_FINDBYPROVIDERID"));
            try {
               pst.setString(1,id);
               
               
               return RecordAssembler.toSuppliesRecordList(pst.executeQuery());
                
            }finally{
                if(pst!=null) {
                    pst.close();
                }
            }
        }catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<SuppliesRecord> findBySparePartId(String id) {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(
                Queries.get("TSUPPLIES_FINDBYSPAREPARTID"));
            try {
               pst.setString(1,id);
               
               
               return RecordAssembler.toSuppliesRecordList(pst.executeQuery());
                
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
