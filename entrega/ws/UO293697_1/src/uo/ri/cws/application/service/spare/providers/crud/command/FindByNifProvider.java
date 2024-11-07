package uo.ri.cws.application.service.spare.providers.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.providers.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNifProvider implements Command<Optional<ProviderDto>> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private String nif;

    public FindByNifProvider(String nif) {
        ArgumentChecks.isNotBlank(nif, "El nif no puede estar vacio");
        this.nif = nif;
    }

    @Override
    public Optional<ProviderDto> execute() throws BusinessException {
        Optional<ProviderRecord> rec = pg.findByNif(nif);
        if (rec.isPresent()) {
            return Optional.of(DtoAssembler.toDto(rec.get()));
        }
        return Optional.ofNullable(null);
    }

}
