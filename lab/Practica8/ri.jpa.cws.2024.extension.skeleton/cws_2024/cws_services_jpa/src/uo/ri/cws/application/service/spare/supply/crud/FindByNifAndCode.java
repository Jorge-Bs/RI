package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNifAndCode implements Command<Optional<SupplyDto>> {
	
	private String code;
	private String nif;
	private SupplyRepository rep = Factories.repository.forSupply();

	public FindByNifAndCode(String nif,String code) {
		ArgumentChecks.isNotNull(nif, "invalid nif");
		ArgumentChecks.isNotNull(code, "invalid code");
		this.nif = nif;
		this.code = code;
	}

	@Override
	public Optional<SupplyDto> execute() throws BusinessException {
		Optional<Supply> sup = rep.findByNifAndCode(nif, code);
		
		if(sup.isEmpty()) return Optional.ofNullable(null);
		
		return Optional.ofNullable(DtoAssembler.toDto(sup.get()));
	}
}
