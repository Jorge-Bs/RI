package uo.ri.cws.application.service.spare.supplies.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto.SuppliedSparePartDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto.SupplierProviderDto;
import uo.ri.cws.application.service.spare.supplies.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindSuppliesByNifAndCode implements Command<Optional<SupplyDto>> {

    private String nif;
    private String code;

    private SparePartGateway spg = Factories.persistence.forSpareParts();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SuppliesGateway supg = Factories.persistence.forSupplies();

    public FindSuppliesByNifAndCode(String nif, String code)
        throws BusinessException {
        ArgumentChecks.isNotEmpty(nif, "nif invalido");
        ArgumentChecks.isNotEmpty(code, "code invalido");

        this.nif = nif;
        this.code = code;
    }

    @Override
    public Optional<SupplyDto> execute() throws BusinessException {
        SparePartRecord spr = obtainSparePart();
        ProviderRecord pr = obtainProvider();
        Optional<SuppliesRecord> sup;
        sup= checkExistSupply(spr.id, pr.id);

        if(sup.isEmpty()) {
            return Optional.ofNullable(null);
        }
        
        SupplyDto dto =DtoAssembler.toDto(sup.get());
        dto.provider=new SupplierProviderDto();
        dto.provider.id=pr.id;
        dto.provider.nif=pr.nif;
        dto.provider.name=pr.name;
        
        dto.sparePart=new SuppliedSparePartDto();
        dto.sparePart.id=spr.id;
        dto.sparePart.code=spr.code;
        dto.sparePart.description=spr.description;
        
        return Optional.of(dto);
    }

    private Optional<SuppliesRecord> checkExistSupply(String spareId,
        String providerId) throws BusinessException {
        
        Optional<SuppliesRecord> rec;
        SuppliesRecord record = new SuppliesRecord();
        record.providerId = providerId;
        record.sparepartId = spareId;
        rec = supg.findBySparePartIdAndProviderId(record);
        return rec;
    }

    private ProviderRecord obtainProvider() throws BusinessException {
        Optional<ProviderRecord> rec = pg.findByNif(nif);
        BusinessChecks.exists(rec, "no existe la pieza");
        return rec.get();
    }

    private SparePartRecord obtainSparePart() throws BusinessException {
        Optional<SparePartRecord> rec = spg.findByCode(code);
        BusinessChecks.exists(rec, "no existe la pieza");
        return rec.get();
    }

}
