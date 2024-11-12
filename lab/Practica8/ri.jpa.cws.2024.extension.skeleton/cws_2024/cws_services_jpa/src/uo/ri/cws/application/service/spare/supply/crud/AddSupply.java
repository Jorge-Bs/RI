package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSupply implements Command<SupplyDto> {

	
	private SupplyDto dto;
	private SupplyRepository supRep = Factories.repository.forSupply();
	private ProviderRepository proRep = Factories.repository.forProvider();
	private SparePartRepository spareRepo = Factories.repository.forSparePart();
	
	
	public AddSupply(SupplyDto dto) throws BusinessException {
		ArgumentChecks.isNotNull(dto, "invalid dto");
		ArgumentChecks.isNotNull(dto.provider, "invalid provider");
		ArgumentChecks.isNotNull(dto.sparePart, "invalid sparePart");
		
		BusinessChecks.isTrue(dto.deliveryTerm>=0,"invalid delivery");
		BusinessChecks.isTrue(dto.price>=0,"invalid delivery");
		
		this.dto=dto;
	}
	
	@Override
	public SupplyDto execute() throws BusinessException {
		Optional<Supply> sup = supRep.findByNifAndCode(dto.provider.nif, dto.sparePart.code);
		
		BusinessChecks.isTrue(sup.isEmpty(),"ya existen registros");
		
		Optional<Provider> provider = proRep.findByNif(dto.provider.id);
		
		BusinessChecks.isTrue(provider.isPresent(),"no existe el proveedor");
		
		Optional<SparePart> pieza = spareRepo.findByCode(dto.sparePart.code);
		
		BusinessChecks.isTrue(provider.isPresent(),"no existe la pieza");
		
		Supply supply = new Supply(provider.get(), pieza.get(), 
				dto.price, dto.deliveryTerm);
		
		supRep.add(supply);
		
		return DtoAssembler.toDto(supply);
	}

}
