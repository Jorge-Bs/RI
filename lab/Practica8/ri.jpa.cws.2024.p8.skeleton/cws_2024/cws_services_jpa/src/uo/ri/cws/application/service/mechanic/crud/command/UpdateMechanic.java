package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic {

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		//con los setter de mecanico y comprobar la version es correcta con busi...hasVersion
		return null;
	}

}
