package uo.ri.cws.application.service.spare.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.provider.crud.AddProvider;
import uo.ri.cws.application.service.spare.provider.crud.DeleteProvider;
import uo.ri.cws.application.service.spare.provider.crud.FindByName;
import uo.ri.cws.application.service.spare.provider.crud.FindByNif;
import uo.ri.cws.application.service.spare.provider.crud.FindBySparePartCode;
import uo.ri.cws.application.service.spare.provider.crud.UpadteProvider;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ProvidersCrudServiceImpl implements ProvidersCrudService {

	private CommandExecutor executor = Factories.executor.forExecutor();
	
	@Override
	public ProviderDto add(ProviderDto dto) throws BusinessException {
		return executor.execute(new AddProvider(dto));
	}

	@Override
	public void delete(String nif) throws BusinessException {
		executor.execute(new DeleteProvider(nif));

	}

	@Override
	public void update(ProviderDto dto) throws BusinessException {
		executor.execute(new UpadteProvider(dto));
	}

	@Override
	public Optional<ProviderDto> findByNif(String nif) throws BusinessException {
		return executor.execute(new FindByNif(nif));
	}

	@Override
	public List<ProviderDto> findByName(String name) throws BusinessException {
		return executor.execute(new FindByName(name));
	}

	@Override
	public List<ProviderDto> findBySparePartCode(String code) throws BusinessException {
		return executor.execute(new FindBySparePartCode(code));
	}

}
