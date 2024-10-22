package uo.ri.cws.application.service.spare.orders.create.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway.OrderLineRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedSpareDto;
import uo.ri.cws.application.service.spare.orders.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListOrdersByCode implements Command<Optional<OrderDto>>{

    private String id;
    
    private OrderGateway og = Factories.persistence.forOrder();
    private OrderLineGateway olg = Factories.persistence.forOrderLine();
    private SparePartGateway spg = Factories.persistence.forSpareParts();
    
    public ListOrdersByCode(String id) {
        ArgumentChecks.isNotNull(id, "El id no puede ser null");
        this.id=id;
    }

    @Override
    public Optional<OrderDto> execute() throws BusinessException {
        Optional<OrderRecord> recor = og.findByCode(id);
        if(recor.isEmpty()) {
            return Optional.ofNullable(null);
        }
        Optional<OrderDto> dto = DtoAssembler.toDto(recor);
        if(dto.isPresent()) {
            dto.get().provider.id=recor.get().providerId;
        }
        List<OrderLineDto> lines = getLines(recor.get().id);
        dto.get().lines=lines;
        return dto;
    }

    private List<OrderLineDto> getLines(String id) {
       List<OrderLineRecord> lista = olg.findAllbyOrderId(id);
       List<OrderLineDto> resultado = new ArrayList<>();
       
       lista.forEach(t ->{
           OrderLineDto dto = new OrderLineDto();
           dto.price=t.price;
           dto.quantity=t.quantity;
           Optional<SparePartRecord> piezas = spg.findById(t.sparePartId);
           OrderedSpareDto sp = new OrderedSpareDto();
           sp.code = piezas.get().code;
           sp.description = piezas.get().description;
           sp.id=piezas.get().id;
           dto.sparePart=sp;
           resultado.add(dto);
       });
       return resultado;
    }
    
}
