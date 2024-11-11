package uo.ri.cws.application.service.spare.order.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByProviderNif implements Command<List<OrderDto>> {

	private String nif;
	private OrderRepository rep = Factories.repository.forOrder();
	
	public FindByProviderNif(String nif) {
		ArgumentChecks.isNotNull(nif, "invalid nif");
		this.nif=nif;
	}
	
	
	@Override
	public List<OrderDto> execute() throws BusinessException {
		
		List<Order> lista =  rep.findByProviderNif(nif);
		return DtoAssembler.toOrdersDtoList(lista);
	}

}
