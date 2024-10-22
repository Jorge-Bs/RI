package uo.ri.cws.application.service.spare.orders.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;


public class DtoAssembler {

	public static List<OrderDto> toDtoList(List<OrderRecord> list) {
		return list.stream()
				.map( m -> toDto(m) )
				.toList();
	}
	
	public static Optional<OrderDto> toDto(Optional<OrderRecord> record){
	    return record.isPresent() ? Optional.of(toDto(record.get())) 
	        : Optional.ofNullable(null);
	}

	public static OrderDto toDto(OrderRecord record) {
	    OrderDto res = new OrderDto();
		res.id = record.id;
		res.version = record.version;
		res.amount = record.amount;
		res.code=record.code;
		res.orderedDate=record.orderDate;
		res.receptionDate=record.receptionDate;
		res.state=record.state;
		
		return res;
	}
	
	public static OrderRecord toRecord (OrderDto dto) {
	    OrderRecord m = new OrderRecord();
	    m.id = dto.id;
        m.version = dto.version;
        m.amount = dto.amount;
        m.code=dto.code;
        m.orderDate=dto.orderedDate;
        m.receptionDate=dto.receptionDate;
        m.state=dto.state;
        return m;
	}
	
}
