package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {
    }

    @Override
    public void remove(String id) throws PersistenceException {
    }

    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = Jdbc.getCurrentConnection();

            ps = c.prepareStatement(Queries.get("TWORKORDERS_UPDATE"));

            try {
                ps.setString(1, t.vehicleId);
                ps.setTimestamp(2, Timestamp.valueOf(t.date));
                ps.setString(3, t.description);
                ps.setDouble(4, t.amount);
                ps.setString(5, t.state);
                ps.setString(6, t.mechanicId);
                ps.setString(7, t.invoiceId);
                ps.setString(8, t.id);

                ps.executeUpdate();
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<WorkOrderRecord> findById(String id)
        throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TWORKORDERS_FINDBYID"));
            try {
                pst.setString(1, id);

                st = pst.executeQuery();
                if (!st.next()) {
                    return Optional.ofNullable(null);
                }

                return Optional.of(RecordAssembler.toWorkOrderRecord(st));
            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<WorkOrderRecord> lista = new ArrayList<>();

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(
                Queries.get("TWORKORDERS_FIND_NOT_INVOICED_BY_CLIENT_NIF"));
            try {
                pst.setString(1, nif);

                rs = pst.executeQuery();
                while (rs.next()) {

                    lista.add(RecordAssembler.toWorkOrderRecord(rs));
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public List<WorkOrderRecord> findByIds(List<String> workOrderIds) {
        List<WorkOrderRecord> lista = new ArrayList<>();
        workOrderIds.forEach((String id) -> {
            lista.add(findById(id).get());
        });
        return lista;
    }

    @Override
    public List<WorkOrderRecord> findByVehicleId(String id) {
        return null;
    }

    @Override
    public List<WorkOrderRecord> findByMechanicId(String id) {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c
                .prepareStatement(Queries.get("TWORKORDERS_FINDBYMECHANICID"));
            try {
                pst.setString(1, id);

                st = pst.executeQuery();

                return RecordAssembler.toWorkOrderRecordList(st);
            } finally {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkOrderRecord> findByState(String state) {
        return null;
    }

}
