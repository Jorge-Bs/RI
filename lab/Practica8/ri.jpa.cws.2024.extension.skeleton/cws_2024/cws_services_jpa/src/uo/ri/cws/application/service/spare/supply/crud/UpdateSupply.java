package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateSupply implements Command<Void> {

	private SupplyDto dto;
	private SupplyRepository repo = Factories.repository.forSupply();
	
	public UpdateSupply(SupplyDto dto) throws BusinessException {
		ArgumentChecks.isNotNull(dto,"invalid dto");
		ArgumentChecks.isNotNull(dto.provider, "invalid provider");
		ArgumentChecks.isNotNull(dto.sparePart, "invalid sparepart");
		
		BusinessChecks.isTrue(dto.deliveryTerm>=0,"invalid delivery");
		BusinessChecks.isTrue(dto.price>=0,"invalid delivery");
		this.dto = dto;
	}
	
	
	@Override
	public Void execute() throws BusinessException {
		Optional<Supply> supply;
		supply = repo.findByNifAndCode(dto.provider.nif, dto.sparePart.code);
		
		BusinessChecks.isTrue(supply.isPresent(),"no existe");
		
		Supply sup = supply.get();
		
		BusinessChecks.hasVersion(sup.getVersion(), dto.version);
		
		sup.setDeliveryTerm(dto.deliveryTerm);
		sup.setPrice(dto.price);
		
		return null;
	}

}
