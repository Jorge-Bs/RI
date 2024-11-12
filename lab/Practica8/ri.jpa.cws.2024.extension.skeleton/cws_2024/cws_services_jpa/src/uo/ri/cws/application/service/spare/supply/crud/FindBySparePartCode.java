package uo.ri.cws.application.service.spare.supply.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supply.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindBySparePartCode implements Command<List<SupplyDto>> {
	
	private String code;
	private SupplyRepository rep = Factories.repository.forSupply();

	public FindBySparePartCode(String code) {
		ArgumentChecks.isNotNull(code);
		this.code = code;
	}

	@Override
	public List<SupplyDto> execute() throws BusinessException {
		List<Supply> sup = rep.findBySparePartCode(code);
		
		if(sup.isEmpty()) return new ArrayList<>();
		
		return DtoAssembler.toSupplyDtoList(sup);
	}
}
