package uo.ri.cws.application.ui.cashier.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersAction implements Action {
    /**
     * Process:
     * 
     * - Ask customer nif
     * 
     * - Display all uncharged workorder (status <> 'INVOICED'). For each
     * workorder, display id, vehicle id, date, status, amount and description
     */

    @Override
    public void execute() throws BusinessException {
        String nif = Console.readString("Client nif ");

        Console.println("\nClient's not invoiced work orders\n");

        InvoicingService service = Factories.service.forInvoicingService();
        Printer.printInvoicingWorkOrders(
                service.findNotInvoicedWorkOrdersByClientNif(nif));

    }

}