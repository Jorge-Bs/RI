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

public class FindBySparePartCode implements Command<List<ProviderDto>> {

    private String code;
    private ProviderRepository pre = Factories.repository.forProvider();

    public FindBySparePartCode(String code) {
        ArgumentChecks.isNotNull(code);
        this.code = code;
    }

    @Override
    public List<ProviderDto> execute() throws BusinessException {
        List<Provider> providerList = pre.findBySparePartCode(code);

        return DtoAssembler.toProvidersDtoList(providerList);
    }

}
