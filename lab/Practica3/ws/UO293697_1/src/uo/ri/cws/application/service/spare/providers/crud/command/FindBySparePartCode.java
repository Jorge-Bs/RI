package uo.ri.cws.application.service.spare.providers.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.providers.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindBySparePartCode implements Command<List<ProviderDto>> {

    private String code;
    
    private SparePartGateway sp = Factories.persistence.forSpareParts();
    private SuppliesGateway ssp = Factories.persistence.forSupplies();
    private ProviderGateway pg = Factories.persistence.forProvider();
    
    public FindBySparePartCode(String code) {
        ArgumentChecks.isNotBlank(code, "El codigo es invalido");
        this.code=code;
    }
    
    @Override
    public List<ProviderDto> execute() throws BusinessException {
        Optional<SparePartRecord> pieza = sp.findByCode(code);
        
        BusinessChecks.exists(pieza,
                "No existe la pieza con el codigo proporcionado");
       
        List<SuppliesRecord> proveedores = ssp.findBySparePartId(pieza.get().id);
        List<ProviderDto> resultado = new ArrayList<>();
        
        proveedores.forEach(proveedor->{
            resultado.add(DtoAssembler.toDto(
                pg.findById(proveedor.providerId).get()));
        });
        return resultado;
    }

}
