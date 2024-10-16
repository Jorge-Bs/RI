package uo.ri.cws.application.ui.manager.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.ServiceFactory;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindAllMechanicsAction implements Action {

	
	
	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics \n");  
		
		MechanicCrudService me = Factories.service.forMechanicService();
		List<MechanicDto> lista = me.findAllMechanics();
		
		lista.forEach((MechanicDto m)->{
			Console.printf("\t%s %s %s %s %d\n",  
					m.id
					,  m.nif
					,  m.name
					,  m.surname
					, m.version
				);
		});
	}
}
