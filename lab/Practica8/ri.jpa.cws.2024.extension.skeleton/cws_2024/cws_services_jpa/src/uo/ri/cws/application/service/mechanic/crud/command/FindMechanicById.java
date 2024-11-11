package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindMechanicById implements Command<Optional<MechanicDto>>{

	private String id;
	private MechanicRepository rep = Factories.repository.forMechanic();

	public FindMechanicById(String id) {
		ArgumentChecks.isNotNull(id, "invalid id");;
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {

		Optional<Mechanic>  me = rep.findById(id);
		
		if(me.isEmpty()) {
			return Optional.ofNullable(null);
		}
		
		Mechanic m = me.get();
		
		return Optional.of(DtoAssembler.toDto(m));
	}

}
