package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Round;

public class WorkOrdersBilling implements Command<InvoiceDto>{
//	private static final String URL = "jdbc:hsqldb:hsql://localhost";
//	private static final String USER = "sa";
//	private static final String PASSWORD = "";

//	private static final String SQL_CHECK_WORKORDER_STATE = 
//			"select state from TWorkOrders where id = ?";

//	private static final String SQL_LAST_INVOICE_NUMBER = 
//			"select max(number) from TInvoices";

//	private static final String SQL_FIND_WORKORDER_AMOUNT = 
//			"select amount from TWorkOrders where id = ?";
	
//	private static final String SQL_INSERT_INVOICE = 
//			"insert into TInvoices(id, number, date, vat, amount, state, version) "
//					+ "	values(?, ?, ?, ?, ?, ?, ?)";

//	private static final String SQL_LINK_WORKORDER_TO_INVOICE = 
//			"update TWorkOrders set invoice_id = ? where id = ?";

//	private static final String SQL_MARK_WORKORDER_AS_INVOICED = 
//			"update TWorkOrders set state = 'INVOICED' where id = ?";

//	private static final String SQL_FIND_WORKORDERS = 
//			"select * from TWorkOrders where id = ?";
	
//	private static final String SQL_UPDATEVERSION_WORKORDERS = 
//			"update TWorkOrders set version=version+1 where id = ?";
	
	private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
	private InvoiceGateway ing = Factories.persistence.forInvoice();
	private Connection connection;
	private List<String> workOrderIds = new ArrayList<>();
	
	public WorkOrdersBilling(List<String> lista) {
		lista.forEach((String id)->{
			ArgumentChecks.isNotBlank(id,"El id es invalido");
			this.workOrderIds.add(id);
		});
	}
	
	public InvoiceDto execute() throws BusinessException {
		InvoiceDto dto = new InvoiceDto();
		try {
			//connection = DriverManager.getConnection(URL, USER, PASSWORD);

			if (! checkWorkOrdersExist(workOrderIds) )
				throw new BusinessException ("Workorder does not exist");
			if (! checkWorkOrdersFinished(workOrderIds) )
				throw new BusinessException ("Workorder is not finished yet");

			long numberInvoice = generateInvoiceNumber();
			LocalDate dateInvoice = LocalDate.now();
			double amount = calculateTotalInvoice(workOrderIds); // vat not included
			double vat = vatPercentage(amount, dateInvoice);
			double total = amount * (1 + vat/100); // vat included
			total = Round.twoCents(total);

			String idInvoice = createInvoice(numberInvoice, dateInvoice, vat, total);
			
			update(idInvoice);
			
			dto.number=numberInvoice;
			dto.date=dateInvoice;
			dto.amount=total;
			dto.vat=vat;
			
			//displayInvoice(numberInvoice, dateInvoice, amount, vat, total);

			//connection.commit();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (connection != null) try { connection.close(); } catch(SQLException e) { /* ignore */ }
		}
		
		return dto;
	}

	private void update(String idInvoice) throws SQLException {
//		linkWorkordersToInvoice(idInvoice, workOrderIds);
//		markWorkOrderAsInvoiced(workOrderIds);
//		updateVersion(workOrderIds);
		
		
		for (String id : workOrderIds) {
			WorkOrderRecord rc =  wog.findById(id).get();
			
			rc.invoiceId=idInvoice;
			rc.state="INVOICED";
			
			wog.update(rc);
		}
		
		
	}
	
	

	/*
	 * checks whether every work order exist	 
	 */
	private boolean checkWorkOrdersExist(List<String> workOrderIDS){
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		try {
//			pst = connection.prepareStatement(SQL_FIND_WORKORDERS);
//
//			for (String workOrderID : workOrderIDS) {
//				pst.setString(1, workOrderID);
//
//				rs = pst.executeQuery();
//				if (rs.next() == false) {
//					return false;
//				}
//
//			}
//		} finally {
//			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
//		return true;
		
		for(String id:workOrderIDS) {
			try {
				wog.findById(id).get();
			}catch(NoSuchElementException e) {
				return false;
			}
		}
		return true;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private boolean checkWorkOrdersFinished(List<String> workOrderIDS) throws SQLException, BusinessException {
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//
//		try {
//			pst = connection.prepareStatement(SQL_CHECK_WORKORDER_STATE);
//
//			for (String workOrderID : workOrderIDS) {
//				pst.setString(1, workOrderID);
//
//				rs = pst.executeQuery();
//				rs.next();
//				String state = rs.getString(1); 
//				if (! "FINISHED".equalsIgnoreCase(state) ) {
//					return false;
//				}
//
//			}
//		} finally {
//			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
//		return true;
		
		for(String id:workOrderIDS) {
			WorkOrderRecord rs =  wog.findById(id).get();
			
			String state = rs.state; 
			if (! "FINISHED".equalsIgnoreCase(state) ) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber() throws SQLException {
		
		return ing.getNextInvoiceNumber();
		
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//
//		try {
//			pst = connection.prepareStatement(SQL_LAST_INVOICE_NUMBER);
//			rs = pst.executeQuery();
//
//			if (rs.next()) {
//				return rs.getLong(1) + 1; // +1, next
//			} else { // there is none yet
//				return 1L;
//			}
//		} finally {
//			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
	}

	/*
	 * Compute total amount of the invoice  (as the total of individual work orders' amount 
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS) throws BusinessException, SQLException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private Double getWorkOrderTotal(String workOrderID) throws SQLException, BusinessException {
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		Double money = 0.0;
//
//		try {
//			pst = connection.prepareStatement(SQL_FIND_WORKORDER_AMOUNT);
//			pst.setString(1, workOrderID);
//
//			rs = pst.executeQuery();
//			if (rs.next() == false) {
//				throw new BusinessException("Workorder " + workOrderID + " doesn't exist");
//			}
//
//			money = rs.getDouble(1); 
//
//		} finally {
//			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
//		return money;
		
		WorkOrderRecord rc;
		
		try {
			rc =wog.findById(workOrderID).get();
		}catch(NoSuchElementException e) {
			throw new BusinessException("Workorder " + workOrderID + " doesn't exist");
		}
			return rc.amount;
	}

	/*
	 * returns vat percentage 
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private String createInvoice(long numberInvoice, LocalDate dateInvoice, 
			double vat, double total){
		String idInvoice = UUID.randomUUID().toString();
		InvoiceRecord rc = new InvoiceRecord();
		
		rc.id=idInvoice;
		rc.amount=total;
		rc.vat=vat;
		rc.number=numberInvoice;
		rc.date=dateInvoice;
		rc.state="NOT_YET_PAID";
		rc.version=1L;
		
		ing.add(rc);
		
		return idInvoice;
//		PreparedStatement pst = null;
//		String idInvoice;
//
//		try {
//			idInvoice = UUID.randomUUID().toString();
//			
//			pst = connection.prepareStatement(SQL_INSERT_INVOICE);
//			pst.setString(1, idInvoice);
//			pst.setLong(2, numberInvoice);
//			pst.setDate(3, java.sql.Date.valueOf(dateInvoice));
//			pst.setDouble(4, vat);
//			pst.setDouble(5, total);
//			pst.setString(6, "NOT_YET_PAID");
//			pst.setLong(7, 1L);
//
//			pst.executeUpdate();
//
//		} finally {
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
//		return idInvoice; 
	}
	
//	/*
//	 * Set the invoice number field in work order table to the invoice number generated
//	 */
//	private void linkWorkordersToInvoice (String invoiceId, List<String> workOrderIDS) throws SQLException {
//
//		PreparedStatement pst = null;
//		try {
//			pst = connection.prepareStatement(SQL_LINK_WORKORDER_TO_INVOICE);
//
//			for (String workOrderId : workOrderIDS) {
//				
//				WorkOrderRecord rc =  wog.findById(workOrderId).get();
//				
//				pst.setString(1, invoiceId);
//				pst.setString(2, workOrderId);
//
//				pst.executeUpdate();
//			}
//		} finally {
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
//	}
//	
//	/*
//	 * Sets state to INVOICED for every workorder
//	 */
//	private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {
//
//		PreparedStatement pst = null;
//		try {
//			pst = connection.prepareStatement(SQL_MARK_WORKORDER_AS_INVOICED);
//
//			for (String id: ids) {
//				pst.setString(1, id);
//
//				pst.executeUpdate();
//			}
//		} finally {
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}
//	}
//	
//	private void updateVersion(List<String> workOrderIds) throws SQLException {
//		PreparedStatement pst = null;
//		
//		try {
//			pst = connection.prepareStatement(SQL_UPDATEVERSION_WORKORDERS);
//
//			for (String workOrderID : workOrderIds) {
//				pst.setString(1, workOrderID);
//				pst.executeUpdate();
//				}
//		} finally {
//			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
//		}		
//	}
	
}
