package uo.ri.cws.application.service.spare.providers.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.providers.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindProviderByName implements Command<List<ProviderDto>> {

    private ProviderGateway pg = Factories.persistence.forProvider();
    private String name;

    public FindProviderByName(String name) {
        ArgumentChecks.isNotEmpty(name, "El nombre no puede estar vacio");
        this.name = name;
    }

    @Override
    public List<ProviderDto> execute() throws BusinessException {
        return DtoAssembler.toDtoList(pg.findByName(name));
    }

}
