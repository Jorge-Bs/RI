package uo.ri.cws.application.service.invoice.create;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class DtoAssembler {

	public static InvoiceDto toDto(ResultSet rs) throws SQLException {
		InvoiceDto result = new InvoiceDto();
		result.id = rs.getString("id");
		result.number = rs.getLong("number");
		result.state = rs.getString("state");
		result.date = rs.getDate("date").toLocalDate();
		result.amount = rs.getDouble("amount");
		result.vat = rs.getDouble("vat");
		return result;
	}

	public static InvoicingWorkOrderDto toInvoicingWODto(ResultSet rs) throws SQLException {
		InvoicingWorkOrderDto result = new InvoicingWorkOrderDto();
		result.id = rs.getString("id");
		result.description = rs.getString("description");
		result.state = rs.getString("state");
		result.date = rs.getTimestamp("date").toLocalDateTime();
		result.amount = rs.getDouble("amount");
		return result;
	}

}
