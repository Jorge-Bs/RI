package uo.ri.cws.application.service.spare.supplies.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.supplies.crud.command.AddSupplies;
import uo.ri.cws.application.service.spare.supplies.crud.command.DeleteSupplies;
import uo.ri.cws.application.service.spare.supplies.crud.command.FindSuppliesByNifAndCode;
import uo.ri.cws.application.service.spare.supplies.crud.command.FindSuppliesByProviderNif;
import uo.ri.cws.application.service.spare.supplies.crud.command.FindSuppliesBySparePartCode;
import uo.ri.cws.application.service.spare.supplies.crud.command.UpdateSupplies;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class SuppliesCrudServiceImpl implements SuppliesCrudService{

    JdbcCommandExecutor executor = new JdbcCommandExecutor();
    
    @Override
    public SupplyDto add(SupplyDto dto) throws BusinessException {
        return executor.execute(new AddSupplies(dto));
    }

    @Override
    public void delete(String nif, String code) throws BusinessException {
        executor.execute(new DeleteSupplies(nif, code));
        
    }

    @Override
    public void update(SupplyDto dto) throws BusinessException {
        executor.execute(new UpdateSupplies(dto));
    }

    @Override
    public Optional<SupplyDto> findByNifAndCode(String nif, String code)
        throws BusinessException {
        return executor.execute(new FindSuppliesByNifAndCode(nif, code));
    }

    @Override
    public List<SupplyDto> findByProviderNif(String nif)
        throws BusinessException {
        return executor.execute(new FindSuppliesByProviderNif(nif));
    }

    @Override
    public List<SupplyDto> findBySparePartCode(String code)
        throws BusinessException {
        return executor.execute(new FindSuppliesBySparePartCode(code));
    }

}
