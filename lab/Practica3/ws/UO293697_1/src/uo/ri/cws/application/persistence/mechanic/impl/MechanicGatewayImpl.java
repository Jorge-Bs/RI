package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {
    @Override
    public void add(MechanicRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TMECHANICS_ADD"));
            try {
                pst.setString(1, t.id);
                pst.setString(2, t.nif);
                pst.setString(3, t.name);
                pst.setString(4, t.surname);
                pst.setLong(5, t.version);

                pst.executeUpdate();
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
        PreparedStatement ps = null;

        try {
            c = Jdbc.getCurrentConnection();

            ps = c.prepareStatement(Queries.get("TMECHANICS_REMOVE"));
            try {
                ps.setString(1, id);

                ps.executeUpdate();
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(MechanicRecord t) throws PersistenceException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = Jdbc.getCurrentConnection();

            ps = c.prepareStatement(Queries.get("TMECHANICS_UPDATE"));
            try {
                ps.setString(1, t.name);
                ps.setString(2, t.surname);
                ps.setString(3, t.id);

                ps.executeUpdate();
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<MechanicRecord> findById(String id)
        throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {

            c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_FINDBYID"));
            try {
                pst.setString(1, id);

                st = pst.executeQuery();

                return RecordAssembler.toMechanicRecord(st);
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
    public List<MechanicRecord> findAll() throws PersistenceException {
        Connection c = null;
        Statement st = null;
        ResultSet rt = null;

        try {

            c = Jdbc.getCurrentConnection();

            st = c.createStatement();

            try {
                rt = st.executeQuery(Queries.get("TMECHANICS_FINDALL"));

                return RecordAssembler.toMechanicRecordList(rt);
            } finally {
                if (rt != null) {
                    rt.close();
                }
                if (st != null) {
                    st.close();
                }
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<MechanicRecord> findByNif(String nif)
        throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(Queries.get("TMECHANICS_FINDBYNIF"));
            try {
                pst.setString(1, nif);

                st = pst.executeQuery();

                return RecordAssembler.toMechanicRecord(st);
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
