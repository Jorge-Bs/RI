package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;


import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.command.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.command.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.command.FindAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.command.FindMechanicById;
import uo.ri.cws.application.service.mechanic.crud.command.FindMechanicByNif;
import uo.ri.cws.application.service.mechanic.crud.command.UpdateMechanic;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	
	private CommandExecutor executor = Factories.executor.forExecutor();

	@Override
	public MechanicDto addMechanic(MechanicDto dto) throws BusinessException {
		return executor.execute(new AddMechanic( dto ));
	}

	@Override
	public void updateMechanic(MechanicDto dto) throws BusinessException {
		executor.execute(new UpdateMechanic( dto ));
	}

	@Override
	public void deleteMechanic(String iddto) throws BusinessException {
		executor.execute(new DeleteMechanic(iddto));
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return executor.execute(new FindAllMechanics());
	}

	@Override
	public Optional<MechanicDto> findMechanicById(String id) throws BusinessException {
		return executor.execute( new FindMechanicById(id));
	}

	@Override
	public Optional<MechanicDto> findMechanicByNif(String nif)
			throws BusinessException {
		return executor.execute(new FindMechanicByNif(nif));
	}

}