package uo.ri.cws.application.ui.cashier.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersAction implements Action {

    @Override
    public void execute() throws BusinessException {
        String nif = Console.readString("Client nif ");

        Console.println("\nClient's not invoiced work orders\n");

        InvoicingService service = Factories.service.forInvoicingService();
        Printer.printInvoicingWorkOrders(
            service.findNotInvoicedWorkOrdersByClientNif(nif));

    }

}