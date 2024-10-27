package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private InterventionGateway ig = Factories.persistence.forIntervention();
    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();

    private String idMechanic;

    public DeleteMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId, "Id invalido");
        idMechanic = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        checkMechanicExist();
        checkCanBeDeleted();
        mg.remove(idMechanic);
        return null;

    }

    /**
     * comprueba si el mecanico puede ser eliminado
     * 
     * @throws BusinessException si no puede ser eliminado
     */
    private void checkCanBeDeleted() throws BusinessException {
        BusinessChecks.isTrue(ig.findByMechanicId(idMechanic).isEmpty(),
            "El mecanico tiene intervenciones");
        BusinessChecks.isTrue(wg.findByMechanicId(idMechanic).isEmpty(),
            "El mecanico tiene ordenes de trabajo");
    }

    /**
     * comprueba que existe el mecanico
     * 
     * @throws BusinessException si no existe
     */
    private void checkMechanicExist() throws BusinessException {
        BusinessChecks.exists(mg.findById(idMechanic), "No existe el mecanico");

    }
}
