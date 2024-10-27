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

public class FindSuppliesByProviderNif implements Command<List<SupplyDto>> {

    private String nif;

    private ProviderRecord providerRec;

    private ProviderGateway pg = Factories.persistence.forProvider();
    private SparePartGateway sp = Factories.persistence.forSpareParts();
    private SuppliesGateway supg = Factories.persistence.forSupplies();

    public FindSuppliesByProviderNif(String nif) {
        ArgumentChecks.isNotEmpty(nif, "invalid nif");
        this.nif = nif;
    }

    @Override
    public List<SupplyDto> execute() throws BusinessException {
        try {
            findSupplier();
        } catch (BusinessException e) {
            return new ArrayList<>();
        }

        List<SuppliesRecord> lista = supg.findByProviderId(providerRec.id);
        List<SupplyDto> resultado = new ArrayList<>();

        lista.forEach(t -> {
            SparePartRecord rec = sp.findById(t.sparepartId).get();
            SupplyDto dto = DtoAssembler.toDto(t);
            dto.provider.name = providerRec.name;
            dto.provider.id = providerRec.id;
            dto.provider.nif = providerRec.nif;

            dto.sparePart.code = rec.code;
            dto.sparePart.description = rec.description;
            dto.sparePart.id = rec.id;
            resultado.add(dto);
        });
        return resultado;
    }

    /**
     * Obtiene el proveedor
     * 
     * @throws BusinessException si no existe
     */
    private void findSupplier() throws BusinessException {
        Optional<ProviderRecord> rec = pg.findByNif(nif);
        BusinessChecks.exists(rec, "no existen datos para ese nif");
        providerRec = rec.get();
    }

}
