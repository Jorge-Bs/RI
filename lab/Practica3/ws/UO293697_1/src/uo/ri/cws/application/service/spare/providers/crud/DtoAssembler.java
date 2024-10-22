package uo.ri.cws.application.service.spare.providers.crud;

import java.util.List;

import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;

public class DtoAssembler {

	public static List<ProviderDto> toDtoList(List<ProviderRecord> list) {
		return list.stream()
				.map( m -> toDto(m) )
				.toList();
	}

	public static ProviderDto toDto(ProviderRecord record ) {
	    ProviderDto res = new ProviderDto();
		res.id = record.id;
		res.version = record.version;
		
		res.nif = record.nif;
		res.name = record.name;
		res.email = record.email;
		res.phone=record.phone;
		return res;
	}
	
	public static ProviderRecord toRecord (ProviderDto dto) {
	    ProviderRecord m = new ProviderRecord();
	    m.id = dto.id;
        m.version = dto.version;
        
        m.nif = dto.nif;
        m.name = dto.name;
        m.email = dto.email;
        m.phone=dto.phone;
        return m;
	}
}
