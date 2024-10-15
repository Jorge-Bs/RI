package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Conf;
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
//			c = DriverManager.getConnection(
//					Conf.getProperty("DB_URL"),
//					Conf.getProperty("DB_USER"),
//					Conf.getProperty("DB_PASS"));
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Queries.get("TMECHANICS_ADD"));
			pst.setString(1, t.id);
			pst.setString(2, t.nif);
			pst.setString(3, t.name);
			pst.setString(4, t.surname);
			pst.setLong(5, t.version);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		
	}

	@Override
	public void remove(String id) throws PersistenceException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
//			c = DriverManager.getConnection(
//					Conf.getProperty("DB_URL"),
//					Conf.getProperty("DB_USER"),
//					Conf.getProperty("DB_PASS"));
			c = Jdbc.getCurrentConnection();
			
			ps = c.prepareStatement(Queries.get("TMECHANICS_REMOVE"));
			ps.setString(1, id);
			
			ps.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignore */ }
//			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

	@Override
	public void update(MechanicRecord t) throws PersistenceException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
//			c = DriverManager.getConnection(
//					Conf.getProperty("DB_URL"),
//					Conf.getProperty("DB_USER"),
//					Conf.getProperty("DB_PASS"));
			c = Jdbc.getCurrentConnection();
			
			ps = c.prepareStatement(Queries.get("TMECHANICS_UPDATE"));
			ps.setString(1, t.name);
			ps.setString(2, t.surname);
			ps.setString(3, t.id);
			
			ps.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (ps != null) try { ps.close(); } catch(SQLException e) { /* ignore */ }
//			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		//return null;
	}

	@Override
	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet st = null;

		try {
//			c = DriverManager.getConnection(
//					Conf.getProperty("DB_URL"),
//					Conf.getProperty("DB_USER"),
//					Conf.getProperty("DB_PASS"));
			
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Queries.get("TMECHANICS_FINDBYID"));
			pst.setString(1, id);
			
			
			st = pst.executeQuery();
			
			return RecordAssembler.toMechanicRecord(st);
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (st != null) try { st.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

	@Override
	public List<MechanicRecord> findAll() throws PersistenceException {
		Connection c = null;
		Statement st = null;
		ResultSet rt = null;

		try {
//			c = DriverManager.getConnection(
//					Conf.getProperty("DB_URL"),
//					Conf.getProperty("DB_USER"),
//					Conf.getProperty("DB_PASS"));
			c = Jdbc.getCurrentConnection();
//			
			st = c.createStatement();	
			
			rt = st.executeQuery(Queries.get("TMECHANICS_FINDALL"));
			
			
			return RecordAssembler.toMechanicRecordList(rt);
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rt != null) try { rt.close(); } catch(SQLException e) { /* ignore */ }
			if (st != null) try { st.close(); } catch(SQLException e) { /* ignore */ }
//			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

	@Override
	public Optional<MechanicRecord> findByNif(String nif) throws PersistenceException {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet st = null;

		try {
//			c = DriverManager.getConnection(
//					Conf.getProperty("DB_URL"),
//					Conf.getProperty("DB_USER"),
//					Conf.getProperty("DB_PASS"));
			
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Queries.get("TMECHANICS_FINDBYNIF"));
			pst.setString(1, nif);
			
			
			st = pst.executeQuery();
			
			return RecordAssembler.toMechanicRecord(st);
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (st != null) try { st.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

}
