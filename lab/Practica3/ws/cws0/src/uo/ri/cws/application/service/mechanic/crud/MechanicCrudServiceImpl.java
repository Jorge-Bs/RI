package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.FindAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

	@Override
	public MechanicDto addMechanic(MechanicDto dto) throws BusinessException {
		return new AddMechanic(dto).execute();
	}

	@Override
	public void deleteMechanic(String mechanicId) throws BusinessException {
		new DeleteMechanic(mechanicId).execute();

	}

	@Override
	public void updateMechanic(MechanicDto dto) throws BusinessException {
		new UpdateMechanic(dto).execute();

	}

	@Override
	public Optional<MechanicDto> findMechanicById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<MechanicDto> findMechanicByNif(String nif) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return new FindAllMechanics().execute();
	}

}
