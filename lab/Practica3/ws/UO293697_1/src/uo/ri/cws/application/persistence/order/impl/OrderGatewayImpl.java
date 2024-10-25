package uo.ri.cws.application.persistence.order.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class OrderGatewayImpl implements OrderGateway {

    @Override
    public void add(OrderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(OrderRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(
                Queries.get("TORDER_UPDATE"));
            try {
                pst.setDouble(1, t.amount);
                pst.setString(2,t.code);
                pst.setDate(3, Date.valueOf(t.orderDate));
                pst.setDate(4, Date.valueOf(t.receptionDate));
                pst.setString(5, t.state);
                pst.setString(6, t.providerId);
                pst.setString(7, t.id);
               
               pst.execute();
                
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
    public Optional<OrderRecord> findById(String id){
        return Optional.empty();
    }

    @Override
    public List<OrderRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrderRecord> findByProviderId(String providerId) {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(Queries.get("TORDERS_FINDBYPROVIDERID"));
            try {
               pst.setString(1, providerId);
                
               return RecordAssembler.toOrderRecordList(pst.executeQuery());
                
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
    public Optional<OrderRecord> findByCode(String code) {
            Connection c = null;
            PreparedStatement pst = null;
            
            try {
                c = Jdbc.getCurrentConnection();
                
                pst = c.prepareStatement(Queries.get("TORDERS_FINDBYCODE"));
                try {
                   pst.setString(1, code);
                   
                   
                   return RecordAssembler.toOrderRecord(pst.executeQuery());
                    
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
    public List<OrderRecord> findByStateAndProviderID(OrderRecord record) {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(
                Queries.get("TORDER_FINDBYSTATEANDPROVIDERID"));
            try {
               pst.setString(1, record.state);
               pst.setString(2, record.providerId);
               
               
               return RecordAssembler.toOrderRecordList(pst.executeQuery());
                
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
