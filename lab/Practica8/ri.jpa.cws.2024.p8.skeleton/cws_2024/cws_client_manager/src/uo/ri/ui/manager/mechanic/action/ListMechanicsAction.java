package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicsAction implements Action {

	@Override
	public void execute() throws BusinessException {

		MechanicCrudService as = Factories.service.forMechanicCrudService();
		List<MechanicDto> mechanics = as.findAllMechanics();

		Console.println("\nList of mechanics\n");
		mechanics.forEach( m ->
			Printer.printMechanic( m )
		);

	}
}
