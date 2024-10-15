package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.ServiceFactory;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersAction implements Action {
	


	/**
	 * Process:
	 * 
	 *   - Ask customer nif
	 *    
	 *   - Display all uncharged workorder  
	 *   		(state <> 'INVOICED'). For each workorder, display 
	 *   		id, vehicle id, date, state, amount and description
	 */

	

	@Override
	public void execute() throws BusinessException {
		String nif = Console.readString("Client nif ");
		
		Console.println("\nClient's not invoiced work orders\n");  
		
		InvoicingService inv = Factories.service.forInvoicingService();
		
		List<InvoicingWorkOrderDto> lista = inv.findNotInvoicedWorkOrdersByClientNif(nif);
		
		lista.forEach((rs -> {
			Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n",  
					rs.id
					, rs.description
					, rs.date
					, rs.state
					, rs.amount
				);
		}));
		
		/*
		Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n",  
				rs.getString(1)
				, rs.getString(2) 
				, rs.getDate(3)
				, rs.getString(4)
				, rs.getDouble(5)
			);*/
	}

}