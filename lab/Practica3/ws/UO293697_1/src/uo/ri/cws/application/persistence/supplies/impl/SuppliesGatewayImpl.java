package uo.ri.cws.application.persistence.supplies.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSUPPLIES_ADD"));
            try {
                pst.setString(1, t.id);
                pst.setInt(2, t.deliveryTerm);
                pst.setDouble(3, t.price);
                pst.setLong(4, t.version);
                pst.setString(5, t.providerId);
                pst.setString(6, t.sparepartId);

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
    public void remove(String id) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSUPPLIES_DELETE"));
            try {
                pst.setString(1, id);

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
    public void update(SuppliesRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSUPPLIES_UPDATE"));
            try {

                pst.setInt(1, t.deliveryTerm);
                pst.setDouble(2, t.price);
                pst.setString(3, t.providerId);
                pst.setString(4, t.sparepartId);

                pst.setString(5, t.id);

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
    public Optional<SuppliesRecord> findById(String id)
        throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<SuppliesRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public List<SuppliesRecord> findByProviderId(String id) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TSUPPLIES_FINDBYPROVIDERID"));
            try {
                pst.setString(1, id);

                st = pst.executeQuery();

                return RecordAssembler.toSuppliesRecordList(st);

            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<SuppliesRecord> findBySparePartId(String id) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c
                .prepareStatement(Queries.get("TSUPPLIES_FINDBYSPAREPARTID"));
            try {
                pst.setString(1, id);
                st = pst.executeQuery();

                return RecordAssembler.toSuppliesRecordList(st);

            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<SuppliesRecord> findBySparePartIdAndProviderId(
        SuppliesRecord record) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(
                Queries.get("TSUPPLIES_FINDBYSPAREPARTIDANDPROVIDERID"));
            try {
                pst.setString(1, record.sparepartId);
                pst.setString(2, record.providerId);

                st = pst.executeQuery();

                return RecordAssembler.toSuppliesRecord(st);

            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
