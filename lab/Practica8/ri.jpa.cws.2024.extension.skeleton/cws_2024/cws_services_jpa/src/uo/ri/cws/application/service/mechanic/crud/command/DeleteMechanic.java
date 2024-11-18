package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository rep = Factories.repository.forMechanic();

    public DeleteMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId, "invalid id");
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {

        Optional<Mechanic> m = rep.findById(mechanicId);
        BusinessChecks.exists(m, "no existe el mecanico");
        canBeDeleted(m.get());
        rep.remove(m.get());

        return null;
    }

    private void canBeDeleted(Mechanic m) throws BusinessException {
        BusinessChecks.isTrue(m.getAssigned().isEmpty());
        BusinessChecks.isTrue(m.getInterventions().isEmpty());
    }

}
