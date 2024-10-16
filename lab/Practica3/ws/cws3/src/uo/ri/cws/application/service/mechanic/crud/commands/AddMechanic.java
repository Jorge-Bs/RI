package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic  implements Command<MechanicDto>{
	
	private MechanicGateway mg = Factories.persistence.forMechanic();

	
	private MechanicDto m;
	
	public AddMechanic(MechanicDto arg) {
		ArgumentChecks.isNotBlank(arg.nif, "Invalid nif");
		ArgumentChecks.isNotBlank(arg.name, "Invalid name");
		ArgumentChecks.isNotBlank(arg.surname, "Invalid surname");
		
		m = new MechanicDto();
		
		m.id = UUID.randomUUID().toString();
		
		m.nif=arg.nif;
		m.name=arg.name;
		m.surname=arg.surname;
		m.version = 1L;
	}
	
	public MechanicDto execute() throws BusinessException {
		checkMechanicDoesNotExist();
		insertMechanic();
		
		return m;
	}

	private void insertMechanic() {
		
		mg.add(DtoAssembler.toRecord(m));
	}

	private void checkMechanicDoesNotExist() throws  BusinessException {
		BusinessChecks.doesNotExist(mg.findByNif(m.nif),"Ya existe el mecanico");	
		
	}
}
