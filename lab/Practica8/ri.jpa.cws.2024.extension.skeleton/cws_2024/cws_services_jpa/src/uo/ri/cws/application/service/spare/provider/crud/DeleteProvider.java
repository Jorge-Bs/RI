package uo.ri.cws.application.service.spare.provider.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProvider implements Command<Void>{

	private String nif;
	private ProviderRepository pre = Factories.repository.forProvider();
	
	public DeleteProvider(String nif) {
		ArgumentChecks.isNotNull(nif, "invalid nif");
		this.nif = nif;
	}
	
	
	@Override
	public Void execute() throws BusinessException {
		Optional<Provider> prov =pre.findByNif(nif);
		
		BusinessChecks.isFalse(prov.isEmpty(),"no existe el provider");
		Provider provider = prov.get();
		BusinessChecks.isTrue(provider.getOrders().size()==0,"tiene orders");
		BusinessChecks.isTrue(provider.getSupplies().size()==0,"tiene supplies");
		
		pre.remove(provider);
		return null;
	}

}
