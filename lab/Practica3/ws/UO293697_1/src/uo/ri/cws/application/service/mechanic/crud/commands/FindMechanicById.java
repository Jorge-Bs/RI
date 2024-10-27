package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();

    private String id;

    public FindMechanicById(String id) {
        ArgumentChecks.isNotNull(id,
            "No se puede buscar con un elemento vacio");
        ArgumentChecks.isNotBlank(id, "El id es invalido");
        this.id = id;
    }

    @Override
    public Optional<MechanicDto> execute() throws BusinessException {
        Optional<MechanicRecord> m = mg.findById(id);
        if (m.isEmpty()) {
            return Optional.ofNullable(null);
        } else {
            return Optional.of(DtoAssembler.toDto(m.get()));
        }
    }
}
