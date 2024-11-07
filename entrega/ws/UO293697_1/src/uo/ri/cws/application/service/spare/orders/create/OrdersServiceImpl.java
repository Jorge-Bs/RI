package uo.ri.cws.application.service.spare.orders.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.orders.create.commands.ListOrdersByCode;
import uo.ri.cws.application.service.spare.orders.create.commands.ListOrdersByProviderNif;
import uo.ri.cws.application.service.spare.orders.create.commands.ReceiveOrder;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class OrdersServiceImpl implements OrdersService {

    private JdbcCommandExecutor ex = new JdbcCommandExecutor();

    @Override
    public List<OrderDto> generateOrders() throws BusinessException {
        return null;
    }

    @Override
    public List<OrderDto> findByProviderNif(String nif)
        throws BusinessException {
        return ex.execute(new ListOrdersByProviderNif(nif));
    }

    @Override
    public Optional<OrderDto> findByCode(String code) throws BusinessException {
        return ex.execute(new ListOrdersByCode(code));
    }

    @Override
    public OrderDto receive(String code) throws BusinessException {
        return ex.execute(new ReceiveOrder(code));
    }

}
