package uo.ri.cws.application.service.spare.supplies.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supplies.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindSuppliesBySparePartCode implements Command<List<SupplyDto>>{

    private String code;
    
    private SparePartRecord record;
    
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SparePartGateway sp = Factories.persistence.forSpareParts();
    private SuppliesGateway supg = Factories.persistence.forSupplies();
    
    public FindSuppliesBySparePartCode(String code) {
        ArgumentChecks.isNotEmpty(code, "invalid nif");
        this.code=code;
    }
    
    
    @Override
    public List<SupplyDto> execute() throws BusinessException {
        try {
            findSparePart();
        }catch(BusinessException e) {
            return new ArrayList<>();
        }
        
        List<SuppliesRecord> lista = supg.findBySparePartId(record.id);
        List<SupplyDto> resultado = new ArrayList<>();
        
        lista.forEach(t->{
            ProviderRecord rec = pg.findById(t.providerId).get();
            SupplyDto dto = DtoAssembler.toDto(t);
            dto.provider.name=rec.name;
            dto.provider.id=rec.id;
            dto.provider.nif=rec.nif;
            
            dto.sparePart.code=record.code;
            dto.sparePart.description=record.description;
            dto.sparePart.id=record.id;
            resultado.add(dto);
        });
        return resultado;
    }


    private void findSparePart() throws BusinessException {
       Optional<SparePartRecord> rec = sp.findByCode(code);
        BusinessChecks.exists(rec, "no existen datos para ese codigo");
       this.record=rec.get();
    }

}
