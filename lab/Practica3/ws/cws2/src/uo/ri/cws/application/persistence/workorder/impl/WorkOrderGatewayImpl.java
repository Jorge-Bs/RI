package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Conf;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	@Override
	public void add(WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<WorkOrderRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderRecord> findByIds(List<String> workOrderIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderRecord> findByVehicleId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderRecord> findByMechanicId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet st = null;

		try {
			c = DriverManager.getConnection(
					Conf.getProperty("DB_URL"),
					Conf.getProperty("DB_USER"),
					Conf.getProperty("DB_PASS"));
			
			pst = c.prepareStatement(Queries.get("TWORKORDERS_FINDBYMECHANICID"));
			pst.setString(1, id);
			
			
			st = pst.executeQuery();
			
			return RecordAssembler.toWorkOrderRecordList(st);
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (st != null) try { st.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

	@Override
	public List<WorkOrderRecord> findByState(String state) {
		// TODO Auto-generated method stub
		return null;
	}

}
