package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicGateway mg = Factories.persistence.forMechanic();
    private MechanicDto m = new MechanicDto();

    public UpdateMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto, "invalid dto");
        ArgumentChecks.isNotBlank(dto.name, "nombre invalido");
        ArgumentChecks.isNotBlank(dto.id, "apellido invalido");
        ArgumentChecks.isNotBlank(dto.surname, "id invalido");

        m.name = dto.name;
        m.surname = dto.surname;
        m.id = dto.id;
    }

    @Override
    public Void execute() throws BusinessException {
        checkMechanicExist();
        mg.update(DtoAssembler.toRecord(m));
        return null;
    }

    /**
     * comprueba que existe el mecanico
     * 
     * @throws BusinessException si no existe
     */
    private void checkMechanicExist() throws BusinessException {
        BusinessChecks.exists(mg.findById(m.id), "No existe el mecanico");

    }
}
