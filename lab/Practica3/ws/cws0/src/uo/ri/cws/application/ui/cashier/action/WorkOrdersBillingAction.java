package uo.ri.cws.application.ui.cashier.action;


import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.ServiceFactory;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class WorkOrdersBillingAction implements Action {
		

	@Override
	public void execute() throws BusinessException {
		List<String> workOrderIds = new ArrayList<String>();

		// type work order ids to be invoiced in the invoice
		do {
			String id = Console.readString("Type work order ids:  ");
			workOrderIds.add(id);
		} while ( nextWorkorder() );

		InvoicingService inv = ServiceFactory.forInvoicingService();
		
		InvoiceDto dto =  inv.createInvoiceFor(workOrderIds);
		displayInvoice(dto);

	}

	/*
	 * read work order ids to invoice
	 */
	private boolean nextWorkorder() {
		return Console.readString(" Any other workorder? (y/n) ").equalsIgnoreCase("y");
	}
	
	private void displayInvoice(InvoiceDto dto) {

		Console.printf("Invoice number: %d\n", dto.number);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dto.date);
		Console.printf("\tAmount: %.2f €\n", (dto.amount/ (1+ dto.vat)));
		Console.printf("\tVAT: %.1f %% \n", dto.vat);
		Console.printf("\tTotal (including VAT): %.2f €\n", dto.amount);
	}


//	private void displayInvoice(long numberInvoice, LocalDate dateInvoice,
//			double totalInvoice, double vat, double totalConIva) {
//
//		Console.printf("Invoice number: %d\n", numberInvoice);
//		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
//		Console.printf("\tAmount: %.2f €\n", totalInvoice);
//		Console.printf("\tVAT: %.1f %% \n", vat);
//		Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
//	}

}
