package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrders implements Command<List<InvoicingWorkOrderDto>> {
	
	/**
	 * Process:
	 * 
	 *   - Ask customer nif
	 *    
	 *   - Display all uncharged workorder  
	 *   		(state <> 'INVOICED'). For each workorder, display 
	 *   		id, vehicle id, date, state, amount and description
	 */	
	private String nif;
	private WorkOrderGateway wg = Factories.persistence.forWorkOrder();
	
	public FindNotInvoicedWorkOrders(String nif) {
		ArgumentChecks.isNotEmpty(nif, "nif invalido");
		this.nif=nif;
	}
	
	public List<InvoicingWorkOrderDto> execute() {
		return DtoAssembler.toInvoicingWorkOrderList(wg.findNotInvoicedByClientNif(nif));
	}
}
