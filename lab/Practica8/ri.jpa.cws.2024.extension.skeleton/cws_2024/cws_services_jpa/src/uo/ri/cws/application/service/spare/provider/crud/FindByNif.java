package uo.ri.cws.application.service.spare.provider.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNif implements Command<Optional<ProviderDto>> {

    private ProviderRepository pre = Factories.repository.forProvider();
    private String nif;

    public FindByNif(String nif) {
        ArgumentChecks.isNotNull(nif, "invalid");
        this.nif = nif;
    }

    @Override
    public Optional<ProviderDto> execute() throws BusinessException {
        Optional<Provider> prov = pre.findByNif(nif);

        if (prov.isEmpty()) {
            return Optional.ofNullable(null);
        }

        return Optional.of(DtoAssembler.toDto(prov.get()));
    }

}
