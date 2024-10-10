package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;


public class FindAllMechanics {
	private static final String SQL = "select id, nif, name, surname, version from TMechanics";
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	public List<MechanicDto> execute() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MechanicDto> meca = new ArrayList<>();

		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pst = c.prepareStatement(SQL);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				MechanicDto m = new MechanicDto();
				m.id=  rs.getString(1);
				m.nif = rs.getString(2) ;
				m.name = rs.getString(3);
				m.surname= rs.getString(4);
				m.version = rs.getLong(5);
				meca.add(m);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		return meca;
	}
}
