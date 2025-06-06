package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;
    private MechanicRepository rep = Factories.repository.forMechanic();

    public AddMechanic(MechanicDto dto) {
        ArgumentChecks.isNotNull(dto, "invalid dto");
        ArgumentChecks.isNotBlank(dto.nif, "nvalid nif");
        ArgumentChecks.isNotBlank(dto.name, "nvalid name");
        ArgumentChecks.isNotBlank(dto.surname, "nvalid surnam");
        this.dto = dto;
    }

    @Override
    public MechanicDto execute() throws BusinessException {

        Mechanic m = new Mechanic(dto.nif, dto.surname, dto.name);

        BusinessChecks.doesNotExist(rep.findByNif(dto.nif), "ya existe");

        rep.add(m);

        dto.id = m.getId();
        dto.version = m.getVersion();

        return dto;

    }

}
