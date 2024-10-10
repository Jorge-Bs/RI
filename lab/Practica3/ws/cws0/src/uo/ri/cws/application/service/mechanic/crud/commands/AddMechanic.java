package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;

public class AddMechanic {

	private static String SQL = "insert into TMechanics(id, nif, name, surname, version) values (?, ?, ?, ?, ?)";
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	private MechanicDto m;
	
	public AddMechanic(MechanicDto arg) {
		ArgumentChecks.isNotBlank(arg.nif, "Invalid nif");
		ArgumentChecks.isNotBlank(arg.name, "Invalid name");
		ArgumentChecks.isNotBlank(arg.surname, "Invalid surname");
		
		m = new MechanicDto();
		
		m.id = UUID.randomUUID().toString();
		
		m.nif=arg.nif;
		m.name=arg.name;
		m.surname=arg.surname;
		m.version = 1L;
	}
	
	public MechanicDto execute() {
		// Process
				Connection c = null;
				PreparedStatement pst = null;
				ResultSet rs = null;

				try {
					c = DriverManager.getConnection(URL, USER, PASSWORD);
					
					pst = c.prepareStatement(SQL);
					pst.setString(1, m.id);
					pst.setString(2, m.nif);
					pst.setString(3, m.name);
					pst.setString(4, m.surname);
					pst.setLong(5, m.version);
					
					pst.executeUpdate();
					
					return m;
					
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
