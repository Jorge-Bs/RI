package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Conf;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

	@Override
	public void add(InterventionRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InterventionRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<InterventionRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<InterventionRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InterventionRecord> findByMechanicId(String id) throws PersistenceException {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet st = null;

		try {
			c = DriverManager.getConnection(
					Conf.getProperty("DB_URL"),
					Conf.getProperty("DB_USER"),
					Conf.getProperty("DB_PASS"));
			
			pst = c.prepareStatement(Queries.get("TINTERVENTIONS_FINDBYMECHANICID"));
			pst.setString(1, id);
			
			
			st = pst.executeQuery();
			
			return RecordAssembler.toInterventionRecordList(st);
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (st != null) try { st.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

}
