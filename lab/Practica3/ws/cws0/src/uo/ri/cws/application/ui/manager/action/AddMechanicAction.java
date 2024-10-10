package uo.ri.cws.application.ui.manager.action;


import uo.ri.cws.application.service.ServiceFactory;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {

	
	
	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String nif = Console.readString("nif"); 
		String name = Console.readString("Name"); 
		String surname = Console.readString("Surname");
		
		MechanicDto m = new MechanicDto();
		
		m.nif=nif;
		m.name=name;
		m.surname=surname;
		
		MechanicCrudService service = ServiceFactory.forMechanicService();
		service.addMechanic(m);
		
		
		
		// Print result
		Console.println("Mechanic added");
	}

}
