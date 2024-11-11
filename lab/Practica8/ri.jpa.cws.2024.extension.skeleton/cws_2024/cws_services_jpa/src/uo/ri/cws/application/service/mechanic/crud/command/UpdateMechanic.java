package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void>{

	private MechanicDto dto;
	private MechanicRepository mer = Factories.repository.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto, "invalid dto");
		ArgumentChecks.isNotBlank(dto.nif, "nvalid nif");
		ArgumentChecks.isNotBlank(dto.name, "nvalid name");
		ArgumentChecks.isNotBlank(dto.surname, "nvalid surnam");
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		//con los setter de mecanico y comprobar la version es correcta con busi...hasVersion
		
		Optional<Mechanic> m = mer.findById(dto.id);
		
		if(m.isEmpty()) throw new BusinessException("no existe el mecanico");
		BusinessChecks.hasVersion(m.get().getVersion(), dto.version);
		
		Mechanic me = m.get();
		
		me.setSurname(dto.surname);
		me.setName(dto.name);
		
		return null;
	}

}
