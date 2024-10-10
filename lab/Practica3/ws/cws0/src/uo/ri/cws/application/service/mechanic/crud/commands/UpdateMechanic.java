package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic {
	private static String SQL_UPDATE = 
			"update TMechanics " +
				"set name = ?, surname = ?, version = version+1 " +
				"where id = ?";
		private static final String URL = "jdbc:hsqldb:hsql://localhost";
		private static final String USER = "sa";
		private static final String PASSWORD = "";
		private Connection c = null;
		
		private MechanicDto m = new MechanicDto();
		
		public UpdateMechanic(MechanicDto dto) {
			ArgumentChecks.isNotBlank(dto.name, "nombre invalido" );
			ArgumentChecks.isNotBlank(dto.id, "apellido invalido" );
			ArgumentChecks.isNotBlank(dto.surname, "id invalido" );
			
			m.name=dto.name;
			m.surname=dto.surname;
			m.id=dto.id;
		}



		public void execute() {
			PreparedStatement pst = null;
			ResultSet rs = null;

			try {
				c = DriverManager.getConnection(URL, USER, PASSWORD);
				pst = c.prepareStatement(SQL_UPDATE);
				pst.setString(1, m.name);
				pst.setString(2, m.surname);
				pst.setString(3, m.id);
				
				pst.executeUpdate();
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			finally {
				if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
				if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
				if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
			}
		}
}
