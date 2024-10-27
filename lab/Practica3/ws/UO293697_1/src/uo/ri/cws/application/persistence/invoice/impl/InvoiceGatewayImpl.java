package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TINVOICES_ADD"));
            try {
                pst.setString(1, t.id);
                pst.setLong(2, t.number);
                pst.setDate(3, java.sql.Date.valueOf(t.date.toString()));
                pst.setDouble(4, t.vat);
                pst.setDouble(5, t.amount);
                pst.setString(6, t.state);
                pst.setLong(7, t.version);

                pst.executeUpdate();
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {
    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
        throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public Optional<InvoiceRecord> findByNumber(Long number) {
        return Optional.empty();
    }

    @Override
    public Long getNextInvoiceNumber() throws PersistenceException {
        Connection c = null;
        Statement st = null;
        ResultSet rt = null;

        try {

            c = Jdbc.getCurrentConnection();
            st = c.createStatement();
            try {
                rt = st.executeQuery(
                    Queries.get("TINVOICES_GETNEXTINVOICENUMBER"));

                if (rt.next()) {
                    return rt.getLong(1) + 1;
                } else {
                    return 1L;
                }
            } finally {
                if (rt != null) {
                    rt.close();
                }
                if (st != null) {
                    st.close();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
