package uo.ri.cws.application.service.spare.orders.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.orders.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ReceiveOrder implements Command<OrderDto> {

    private OrderGateway og = Factories.persistence.forOrder();
    private OrderLineGateway olg = Factories.persistence.forOrderLine();
    private SparePartGateway sp = Factories.persistence.forSpareParts();
    private ProviderGateway pg = Factories.persistence.forProvider();

    private String code;
    private OrderRecord record;
    private List<OrderLineDto> listaDtos = new ArrayList<>();

    public ReceiveOrder(String code) {
        ArgumentChecks.isNotNull(code, "invalid code");
        this.code = code;
    }

    @Override
    public OrderDto execute() throws BusinessException {
        checkOrderExist();
        checkOrderIsPending();

        updateOrder();
        updateStock();

        OrderDto dto = DtoAssembler.toDto(record);

        ProviderRecord prR = pg.findById(record.providerId).get();

        dto.lines = listaDtos;
        dto.provider.id = record.providerId;
        dto.provider.name = prR.name;
        dto.provider.nif = prR.nif;

        return dto;
    }

    /**
     * Actualiza el stock
     */
    private void updateStock() {
        List<OrderLineRecord> lista = olg.findAllbyOrderId(record.id);

        lista.forEach(t -> {
            SparePartRecord spare = sp.findById(t.sparePartId).get();
            int amount = spare.stock + t.quantity;
            double price = spare.price * spare.stock
                + t.price * 1.2 * t.quantity;
            price /= amount;

            spare.price = price;
            spare.stock = amount;

            sp.update(spare);

            OrderLineDto order = new OrderLineDto();
            order.price = t.price;
            order.quantity = t.quantity;

            order.sparePart.code = spare.code;
            order.sparePart.description = spare.description;
            order.sparePart.id = spare.id;

            listaDtos.add(order);
        });
    }

    /**
     * Actualiza el estado del pedido
     */
    private void updateOrder() {
        record.receptionDate = LocalDate.now();
        record.state = "RECEIVED";
        og.update(record);
    }

    /**
     * Cmprueba que el pedido este en estado pending
     * 
     * @throws BusinessException si no lo esta
     */
    private void checkOrderIsPending() throws BusinessException {
        String status = record.state;
        BusinessChecks.isFalse(status.equals("RECEIVED"), "estado invalido");

    }

    /**
     * Comprueba que el pedido exista
     * 
     * @throws BusinessException si no lo esta
     */
    private void checkOrderExist() throws BusinessException {
        Optional<OrderRecord> record = og.findByCode(code);
        BusinessChecks.exists(record, "no existe el pedido");
        this.record = record.get();
    }

}
