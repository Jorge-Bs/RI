package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factories.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factories.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull( workOrderIds );
		ArgumentChecks.isFalse( workOrderIds.isEmpty() ,"invalids ids");
		ArgumentChecks.isFalse(workOrderIds.stream().anyMatch(i -> i == null),"invalids ids");
		
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		
		long number = invsRepo.getNextInvoiceNumber();
		List<WorkOrder> orders =wrkrsRepo.findByIds(this.workOrderIds);
		BusinessChecks.isTrue(orders.size()==workOrderIds.size(),"no existen ordenes para algunos ids");
		BusinessChecks.isTrue(orders.stream().allMatch(i -> i.isFinished()), "work order no acabada");
		Invoice i = new Invoice(number,orders);
		
		invsRepo.add(i);
		
		return DtoAssembler.toDto(i);
	}

}
