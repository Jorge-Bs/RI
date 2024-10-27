package uo.ri.cws.application.service.spare.supplies.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway;
import uo.ri.cws.application.persistence.supplies.SuppliesGateway.SuppliesRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSupplies implements Command<Void> {

    private String nif;
    private String code;

    private SparePartGateway spg = Factories.persistence.forSpareParts();
    private ProviderGateway pg = Factories.persistence.forProvider();
    private SuppliesGateway supg = Factories.persistence.forSupplies();

    public DeleteSupplies(String nif, String code) throws BusinessException {
        ArgumentChecks.isNotEmpty(nif, "nif invalido");
        ArgumentChecks.isNotEmpty(code, "code invalido");

        this.nif = nif;
        this.code = code;
    }

    @Override
    public Void execute() throws BusinessException {
        SparePartRecord spr = obtainSparePart();
        ProviderRecord pr = obtainProvider();
        SuppliesRecord sup = checkExistSupply(spr.id, pr.id);

        supg.remove(sup.id);

        return null;
    }

    /**
     * Comprueba que existe el sumistro
     * 
     * @param spareId    id de la pieza
     * @param providerId id del proveedor
     * @return suppliesRecord with the info
     * @throws BusinessException si no existe
     */
    private SuppliesRecord checkExistSupply(String spareId, String providerId)
        throws BusinessException {
        Optional<SuppliesRecord> rec;
        SuppliesRecord record = new SuppliesRecord();
        record.providerId = providerId;
        record.sparepartId = spareId;
        rec = supg.findBySparePartIdAndProviderId(record);
        BusinessChecks.exists(rec, "no existe la pieza");
        return rec.get();
    }

    /**
     * Busca al proveedor
     * 
     * @return
     * @throws BusinessException
     */
    private ProviderRecord obtainProvider() throws BusinessException {
        Optional<ProviderRecord> rec = pg.findByNif(nif);
        BusinessChecks.exists(rec, "no existe el proveedor");
        return rec.get();
    }

    /**
     * Obtiene la pieza
     * 
     * @return
     * @throws BusinessException
     */
    private SparePartRecord obtainSparePart() throws BusinessException {
        Optional<SparePartRecord> rec = spg.findByCode(code);
        BusinessChecks.exists(rec, "no existe la pieza");
        return rec.get();
    }

}
