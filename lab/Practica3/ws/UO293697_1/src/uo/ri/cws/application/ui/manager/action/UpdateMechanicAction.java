package uo.ri.cws.application.ui.manager.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String id = Console.readString("Type mechahic id to update");
        String name = Console.readString("Name");
        String surname = Console.readString("Surname");

        MechanicDto mecanico = new MechanicDto();

        mecanico.id = id;
        mecanico.name = name;
        mecanico.surname = surname;

        MechanicCrudService m = Factories.service.forMechanicService();
        m.updateMechanic(mecanico);

        Console.println("Mechanic updated");
    }

}
