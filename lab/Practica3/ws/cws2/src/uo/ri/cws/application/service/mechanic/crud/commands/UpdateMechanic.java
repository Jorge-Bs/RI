package uo.ri.cws.application.service.mechanic.crud.commands;


import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void> {
//	private static String SQL_UPDATE = 
//			"update TMechanics " +
//				"set name = ?, surname = ?, version = version+1 " +
//				"where id = ?";
//		private static final String URL = "jdbc:hsqldb:hsql://localhost";
//		private static final String USER = "sa";
//		private static final String PASSWORD = "";
//		private Connection c = null;
		
		private MechanicGateway mg = Factories.persistence.forMechanic();
		private MechanicDto m = new MechanicDto();
		
		public UpdateMechanic(MechanicDto dto) {
			ArgumentChecks.isNotBlank(dto.name, "nombre invalido" );
			ArgumentChecks.isNotBlank(dto.id, "apellido invalido" );
			ArgumentChecks.isNotBlank(dto.surname, "id invalido" );
			
			m.name=dto.name;
			m.surname=dto.surname;
			m.id=dto.id;
		}



		public Void execute() {
			mg.update(DtoAssembler.toRecord(m));
			return null;
		}
}
