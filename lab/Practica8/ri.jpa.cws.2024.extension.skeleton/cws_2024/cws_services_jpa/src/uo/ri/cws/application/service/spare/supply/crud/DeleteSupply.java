package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSupply implements Command<Void> {
	
	private String nif;
	private String code;
	private SupplyRepository rep = Factories.repository.forSupply();

	public DeleteSupply(String nif,String code) {
		ArgumentChecks.isNotNull(nif, "invalid nif");
		ArgumentChecks.isNotNull(code, "invalid code");
		this.nif=nif;
		this.code=code;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Supply> sup = rep.findByNifAndCode(nif, code);
		
		BusinessChecks.isTrue(sup.isPresent(),"no existe el supply");
		
		rep.remove(sup.get());
		
		return null;
	}
	
	
}
