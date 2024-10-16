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
			pst.setString(1, t.id);
			pst.setLong(2, t.number);
			pst.setDate(3,java.sql.Date.valueOf(t.date.toString()));
			pst.setDouble(4, t.vat);
			pst.setDouble(5, t.amount);
			pst.setString(6, t.state);
			pst.setLong(7, t.version);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InvoiceRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
	}

	@Override
	public Optional<InvoiceRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<InvoiceRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		// TODO Auto-generated method stub
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
			rt = st.executeQuery(Queries.get("TINVOICES_GETNEXTINVOICENUMBER"));
			
			if (rt.next()) {
				return rt.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rt != null) try { rt.close(); } catch(SQLException e) { /* ignore */ }
			if (st != null) try { st.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

}
