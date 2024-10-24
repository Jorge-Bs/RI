package uo.ri.cws.application.service.spare.supplies.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto.SuppliedSparePartDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto.SupplierProviderDto;

public class DtoAssembler {

	public static List<SupplyDto> toDtoList(List<SuppliesRecord> list) {
		return list.stream()
				.map( m -> toDto(m) )
				.toList();
	}
	
	public static Optional<SupplyDto> toDto(Optional<SuppliesRecord> record){
	    return record.isPresent() ? Optional.of(toDto(record.get())) 
	        : Optional.ofNullable(null);
	}

	public static SupplyDto toDto(SuppliesRecord record) {
	    SupplyDto rec = new SupplyDto();
	    rec.id=record.id;
	    rec.deliveryTerm=record.deliveryTerm;
	    rec.price=record.price;
	    rec.provider=new SupplierProviderDto();
	    rec.provider.id=record.providerId;
	    rec.sparePart=new SuppliedSparePartDto();
	    rec.sparePart.id=record.sparepartId;
		
		return rec;
	}
	
	public static SuppliesRecord toRecord (SupplyDto dto) {
	    SuppliesRecord record = new SuppliesRecord();
	    
	    
	    record.id=dto.id;
	    record.deliveryTerm=dto.deliveryTerm;
	    record.price=dto.price;
	    record.providerId=dto.provider.id;
	    record.sparepartId=dto.sparePart.id;
        
        return record;
	}
	
}
