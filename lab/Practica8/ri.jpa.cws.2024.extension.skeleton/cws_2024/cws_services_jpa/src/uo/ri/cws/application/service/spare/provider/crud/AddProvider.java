package uo.ri.cws.application.service.spare.provider.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProvider implements Command<ProviderDto>{
	
	
	private ProviderDto dto;
	
	private ProviderRepository re = Factories.repository.forProvider();

	public AddProvider(ProviderDto dto) {
		ArgumentChecks.isNotNull(dto,"null dto");
		ArgumentChecks.isNotEmpty(dto.name, "invalid name");
		ArgumentChecks.isNotEmpty(dto.nif, "invalid nif");
		ArgumentChecks.isNotEmpty(dto.email, "invalid email");
		ArgumentChecks.isNotEmpty(dto.phone, "invalid phone");
		ArgumentChecks.isTrue(dto.email.contains("@"),"invalid email");
		this.dto=dto;
	}
	
	@Override
	public ProviderDto execute() throws BusinessException {
		Optional<Provider> provider = re.findByNif(dto.nif);
		
		BusinessChecks.isTrue(provider.isEmpty(),"ya existe el mecanico");
		
		List<Provider> providers = re.findByNameMailPhone(dto.name,dto.email,dto.phone);
		
		BusinessChecks.isTrue(providers.size()==0,"existe un mecanico con esos datos");
		
		Provider prov = new Provider(dto.nif, dto.name, dto.phone, dto.email);
		
		re.add(prov);
		
		return DtoAssembler.toDto(prov);
	}

}
