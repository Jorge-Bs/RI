package uo.ri.cws.application.service.spare.order.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class Receive implements Command<OrderDto> {

    private String code;
    private OrderRepository rep = Factories.repository.forOrder();

    public Receive(String code) {
        ArgumentChecks.isNotNull(code, "invalid code");
        this.code = code;
    }

    @Override
    public OrderDto execute() throws BusinessException {
        Optional<Order> order = rep.findByCode(code);
        BusinessChecks.isFalse(order.isEmpty(), "no exist");
        Order ord = order.get();

        BusinessChecks.isTrue(ord.isPending(), "no esta pendiente");
        ord.receive();

        return DtoAssembler.toDto(ord);
    }

}
