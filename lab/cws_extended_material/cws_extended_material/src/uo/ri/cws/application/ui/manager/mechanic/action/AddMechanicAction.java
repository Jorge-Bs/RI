package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        // Ask the user for data
        MechanicDto m = new MechanicDto();
        m.nif = Console.readString("Doi");
        m.name = Console.readString("Name");
        m.surname = Console.readString("Surname");

        // Invoke the service
        MechanicCrudService as = Factories.service.forMechanicService();
        m = as.addMechanic(m);

        // Show result
        Console.println("New mechanic added: " + m.id);
    }

}
