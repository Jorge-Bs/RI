package uo.ri.cws.application.service.spare.provider.crud;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.provider.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByName implements Command<List<ProviderDto>> {

    private String name;
    private ProviderRepository pre = Factories.repository.forProvider();

    public FindByName(String name) {
        ArgumentChecks.isNotNull(name);
        this.name = name;
    }

    @Override
    public List<ProviderDto> execute() throws BusinessException {
        List<Provider> providerList = pre.findByName(name);

        return DtoAssembler.toProvidersDtoList(providerList);
    }

}
