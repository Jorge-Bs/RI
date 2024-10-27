package uo.ri.cws.application.service.spare.supplies.crud.command;

import java.util.Optional;
import java.util.UUID;

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

public class AddSupplies implements Command<SupplyDto> {

    private SupplyDto dto;
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SparePartGateway sp = Factories.persistence.forSpareParts();
    private SuppliesGateway supplieg = Factories.persistence.forSupplies();

    public AddSupplies(SupplyDto dto) throws BusinessException {
        ArgumentChecks.isNotNull(dto, "Objeto nulo");
        ArgumentChecks.isNotNull(dto.provider, "proveedor invalido");
        ArgumentChecks.isNotNull(dto.sparePart, "pieza invalido");
        BusinessChecks.isTrue(dto.price >= 0.0, "precio invalido");
        BusinessChecks.isTrue(dto.deliveryTerm >= 0, "dias invalidos");

        this.dto = dto;
        this.dto.id = UUID.randomUUID().toString();
        this.dto.version = 1L;
    }

    @Override
    public SupplyDto execute() throws BusinessException {
        checkExistProvider();
        checkExistSparePart();
        checkExistsSupply();

        supplieg.add(DtoAssembler.toRecord(dto));

        return dto;
    }

    /**
     * Comprueba que existe el suministro
     * 
     * @throws BusinessException si ya existe
     */
    private void checkExistsSupply() throws BusinessException {
        Optional<SuppliesRecord> rec;
        rec = supplieg
            .findBySparePartIdAndProviderId(DtoAssembler.toRecord(dto));
        BusinessChecks.doesNotExist(rec, "ya existen registros");
    }

    /**
     * Comprueba que la pieza exista
     * 
     * @throws BusinessException si no existe
     */
    private void checkExistSparePart() throws BusinessException {
        Optional<SparePartRecord> pieza = sp.findById(dto.sparePart.id);
        BusinessChecks.exists(pieza, "no existe la pieza");
    }

    /**
     * Comprueba que exista el proveedor
     * 
     * @throws BusinessException si no existe
     */
    private void checkExistProvider() throws BusinessException {
        Optional<ProviderRecord> proveedor = pg.findById(dto.provider.id);
        BusinessChecks.exists(proveedor, "no existe la pieza");
    }

}
