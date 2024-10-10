package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.console.Console;

public class FindNotInvoicedWorkOrders {
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASS = "";
	
	/**
	 * Process:
	 * 
	 *   - Ask customer nif
	 *    
	 *   - Display all uncharged workorder  
	 *   		(state <> 'INVOICED'). For each workorder, display 
	 *   		id, vehicle id, date, state, amount and description
	 */
	
	private static final String SQL =
			"select a.id, a.description, a.date, a.state, a.amount " +
			"from TWorkOrders as a, TVehicles as v, TClients as c " +
			"where a.vehicle_id = v.id " +
			"	and v.client_id = c.id " +
			"	and state <> 'INVOICED'" +
			"	and nif like ?";
	
	private String nif;
	
	public FindNotInvoicedWorkOrders(String nif) {
		ArgumentChecks.isNotEmpty(nif, "nif invalido");
		this.nif=nif;
	}
	
	public List<InvoicingWorkOrderDto> execute() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<InvoicingWorkOrderDto> lista = new ArrayList<>();

		try {
			c = DriverManager.getConnection(URL, USER, PASS);
			
			pst = c.prepareStatement(SQL);
			pst.setString(1, nif);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
				
				LocalDateTime localDateTime = 
						  LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getDate(3).getTime()), ZoneId.systemDefault());
				
				dto.id = rs.getString(1);
				dto.description = rs.getString(2);
				dto.date =  localDateTime;
				dto.state= rs.getString(4);
				dto.amount = rs.getDouble(5);
				
				lista.add(dto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		
		return lista;
	}
}
