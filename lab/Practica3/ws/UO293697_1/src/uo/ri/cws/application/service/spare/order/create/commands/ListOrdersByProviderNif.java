package uo.ri.cws.application.service.spare.order.create.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.order.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ListOrdersByProviderNif implements Command<List<OrderDto>>{

    private String nif;
    
    private OrderGateway og = Factories.persistence.forOrder();
    private ProviderGateway pg = Factories.persistence.forProvider();
    
    public ListOrdersByProviderNif(String nif) {
        ArgumentChecks.isNotNull(nif, "El nif no puede ser null");
        this.nif=nif;
    }

    @Override
    public List<OrderDto> execute() throws BusinessException {
        Optional<ProviderRecord> id = getProviderId(nif);
        if(id.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrderRecord> lista = og.findByProviderId(id.get().id);
        
        List<OrderDto> resultado = DtoAssembler.toDtoList(lista);
        
        resultado.forEach(t->{
            t.provider.id=id.get().id;
            t.provider.name=id.get().name;
            t.provider.nif=id.get().nif;
        });
        
        return resultado;
    }
    
    private Optional<ProviderRecord> getProviderId(String nif)  {
        return pg.findByNif(nif);
    }
    
}
