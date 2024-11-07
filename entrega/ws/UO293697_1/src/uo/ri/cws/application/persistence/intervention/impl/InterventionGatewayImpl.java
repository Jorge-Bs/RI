package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public void add(InterventionRecord t) throws PersistenceException {
    }

    @Override
    public void remove(String id) throws PersistenceException {
    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {
    }

    @Override
    public Optional<InterventionRecord> findById(String id)
        throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public List<InterventionRecord> findByMechanicId(String id)
        throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet st = null;

        try {
            c = Jdbc.getCurrentConnection();

            pst = c.prepareStatement(
                Queries.get("TINTERVENTIONS_FINDBYMECHANICID"));
            try {
                pst.setString(1, id);

                st = pst.executeQuery();

                return RecordAssembler.toInterventionRecordList(st);
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
