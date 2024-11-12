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

public class UpadteProvider implements Command<Void>{
	
	
	private ProviderDto dto;
	
	private ProviderRepository re = Factories.repository.forProvider();

	public UpadteProvider(ProviderDto dto) {
		ArgumentChecks.isNotNull(dto,"null dto");
		ArgumentChecks.isNotEmpty(dto.name, "invalid name");
		ArgumentChecks.isNotEmpty(dto.nif, "invalid nif");
		ArgumentChecks.isNotEmpty(dto.email, "invalid email");
		ArgumentChecks.isNotEmpty(dto.phone, "invalid phone");
		ArgumentChecks.isTrue(dto.email.contains("@"),"invalid email");
		this.dto=dto;
	}
	
	@Override
	public Void execute() throws BusinessException {
		Optional<Provider> provider = re.findByNif(dto.nif);
		
		BusinessChecks.isFalse(provider.isEmpty(),"no existe el mecanico");
		
		List<Provider> providers = re.findByNameMailPhone(dto.name,dto.email,dto.phone);
		
		BusinessChecks.isTrue(providers.size()==0,"existe un mecanico con esos datos");
		
		Provider pro = provider.get();
		BusinessChecks.hasVersion(dto.version,pro.getVersion(),"version desactualizada");
		
		pro.setName(dto.name);
		pro.setEmail(dto.email);
		pro.setPhone(dto.phone);
		
		return null;
		
	}

}
