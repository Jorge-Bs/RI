package uo.ri.cws.application.service.spare.sparepart.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;

public class DtoAssembler {

	public static List<SparePartDto> toDtoList(List<SparePartRecord> list) {
		return list.stream()
				.map( m -> toDto(m) )
				.toList();
	}
	
	public static Optional<SparePartDto> toDto(Optional<SparePartRecord> record){
	    return record.isPresent() ? Optional.of(toDto(record.get())) 
	        : Optional.ofNullable(null);
	}

	public static SparePartDto toDto(SparePartRecord record) {
	    SparePartDto rec = new SparePartDto();
	    rec.code=record.code;
        rec.id=record.id;
        rec.description=record.description;
        rec.maxStock=record.maxStock;
        rec.minStock=record.minStock;
        rec.price=record.price;
        rec.stock=record.stock;
        rec.version=record.version;
		
		return rec;
	}
	
	public static SparePartRecord toRecord (SparePartDto dto) {
	    SparePartRecord record = new SparePartRecord();
	    
	    record.code=dto.code;
        record.id=dto.id;
        record.description=dto.description;
        record.maxStock=dto.maxStock;
        record.minStock=dto.minStock;
        record.price=dto.price;
        record.stock=dto.stock;
        record.version=dto.version;
        
        return record;
	}
	
}
