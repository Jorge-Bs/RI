package uo.ri.cws.application.service.spare.providers.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProvider implements Command<Void>{
    
    
    
    private ProviderGateway pg = Factories.persistence.forProvider();
    private OrderGateway og = Factories.persistence.forOrder();
    private SuppliesGateway sg = Factories.persistence.forSupplies();
    
    
    private String nif;
    private Optional<ProviderRecord> provider;
    
    public DeleteProvider(String nif) {
        ArgumentChecks.isNotBlank(nif, "El nif es invalido");
        this.nif=nif;
    }
    
    
    @Override
    public Void execute() throws BusinessException {
        checkNifExist();
        checkOrdersLinesProvider();
        checkProviderSupplier();
        
        pg.remove(provider.get().id);
        
        return null;
    }
    
    
    private void checkProviderSupplier() throws BusinessException {
        List<SuppliesRecord> lista = sg.findByProviderId(provider.get().id);
        BusinessChecks.isTrue(lista.size()==0,
            "No se puede eliminar, es proveedor");
    }


    private void checkOrdersLinesProvider() throws BusinessException {
        OrderRecord or = new OrderRecord();
        or.state = "PENDING";
        or.providerId=provider.get().id;
        List<OrderRecord> lista= og.findByStateAndProviderID(or);
        BusinessChecks.isTrue(lista.size()==0,
            "No se puede eliminar, posee pedidos en progreso");
    }


    private void checkNifExist() throws BusinessException {
        provider = pg.findByNif(nif);
        BusinessChecks.exists(provider ,
            "Ya existe un proveedor con ese nif");
         
     }

}
