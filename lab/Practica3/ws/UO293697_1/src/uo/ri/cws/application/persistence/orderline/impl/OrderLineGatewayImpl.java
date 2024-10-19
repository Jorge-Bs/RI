package uo.ri.cws.application.persistence.orderline.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class OrderLineGatewayImpl implements OrderLineGateway{


    @Override
    public void add(OrderLineRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(OrderLineRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Optional<OrderLineRecord> findById(String id)
        throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<OrderLineRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<OrderLineRecord> findAllbyOrderId(String id) {
        Connection c = null;
        PreparedStatement pst = null;
        
        try {
            c = Jdbc.getCurrentConnection();
            
            pst = c.prepareStatement(Queries.get("TORDERLINES_FINDBYORDERID"));
            try {
               pst.setString(1, id);
                
               return RecordAssembler.toOrderLineRecordList(pst.executeQuery());
                
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
