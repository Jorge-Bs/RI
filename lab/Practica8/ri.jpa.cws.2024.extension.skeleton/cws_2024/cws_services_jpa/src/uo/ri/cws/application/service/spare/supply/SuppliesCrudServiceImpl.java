package uo.ri.cws.application.service.spare.supply;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.supply.crud.AddSupply;
import uo.ri.cws.application.service.spare.supply.crud.DeleteSupply;
import uo.ri.cws.application.service.spare.supply.crud.FindByNifAndCode;
import uo.ri.cws.application.service.spare.supply.crud.FindByProviderNif;
import uo.ri.cws.application.service.spare.supply.crud.FindBySparePartCode;
import uo.ri.cws.application.service.spare.supply.crud.UpdateSupply;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class SuppliesCrudServiceImpl implements SuppliesCrudService {

    private CommandExecutor executor = Factories.executor.forExecutor();

    @Override
    public SupplyDto add(SupplyDto dto) throws BusinessException {
        return executor.execute(new AddSupply(dto));
    }

    @Override
    public void delete(String nif, String code) throws BusinessException {
        executor.execute(new DeleteSupply(nif, code));

    }

    @Override
    public void update(SupplyDto dto) throws BusinessException {
        executor.execute(new UpdateSupply(dto));

    }

    @Override
    public Optional<SupplyDto> findByNifAndCode(String nif, String code)
        throws BusinessException {
        return executor.execute(new FindByNifAndCode(nif, code));
    }

    @Override
    public List<SupplyDto> findByProviderNif(String nif)
        throws BusinessException {
        return executor.execute(new FindByProviderNif(nif));
    }

    @Override
    public List<SupplyDto> findBySparePartCode(String code)
        throws BusinessException {
        return executor.execute(new FindBySparePartCode(code));
    }

}
