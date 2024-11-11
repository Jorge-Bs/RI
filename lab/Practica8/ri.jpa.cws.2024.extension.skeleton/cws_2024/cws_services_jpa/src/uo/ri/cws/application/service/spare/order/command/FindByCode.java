package uo.ri.cws.application.service.spare.order.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByCode implements Command<Optional<OrderDto>>{
	
	private OrderRepository re = Factories.repository.forOrder();
	private String code;
	
	public FindByCode(String code) {
		ArgumentChecks.isNotNull(code, "invalid code");
		this.code=code;
	}

	@Override
	public Optional<OrderDto> execute() throws BusinessException {
		
		Optional<Order> order =  re.findByCode(code);
		
		if(order.isEmpty()) return Optional.ofNullable(null);
		
		return Optional.ofNullable(DtoAssembler.toDto(order.get()));
	}

}
