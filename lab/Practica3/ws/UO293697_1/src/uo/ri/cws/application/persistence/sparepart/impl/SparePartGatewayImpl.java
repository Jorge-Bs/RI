package uo.ri.cws.application.persistence.sparepart.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class SparePartGatewayImpl implements SparePartGateway {

    @Override
    public void add(SparePartRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(SparePartRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSPAREPARTS_UPDATE"));
            try {
                pst.setString(1, t.code);
                pst.setString(2, t.description);
                pst.setInt(3, t.maxStock);
                pst.setInt(4, t.minStock);
                pst.setDouble(5, t.price);
                pst.setInt(6, t.stock);
                pst.setString(7, t.id);
                
                pst.execute();

            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<SparePartRecord> findById(String id) {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSPAREPARTS_FINDBYID"));
            try {
                pst.setString(1, id);

                return RecordAssembler.toSparePartRecord(pst.executeQuery());

            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<SparePartRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<SparePartRecord> findByCode(String code) {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSPAREPARTS_FINDBYCODE"));
            try {
                pst.setString(1, code);

                return RecordAssembler.toSparePartRecord(pst.executeQuery());

            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
