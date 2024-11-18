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

public class FindByProviderNif implements Command<List<SupplyDto>> {

    private String nif;
    private SupplyRepository rep = Factories.repository.forSupply();

    public FindByProviderNif(String nif) {
        ArgumentChecks.isNotNull(nif, "invalid nif");
        this.nif = nif;
    }

    @Override
    public List<SupplyDto> execute() throws BusinessException {
        List<Supply> sup = rep.findByProviderNif(nif);

        if (sup.isEmpty()) {
            return new ArrayList<>();
        }

        return DtoAssembler.toSupplyDtoList(sup);
    }
}
