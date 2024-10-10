package uo.r1.lab.p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ej2 {
	
	//Hsqldb
			private static final String hsqldbUrl= "jdbc:hsqldb:hsql://localhost" ;
			private static final String hsqldbUser= "sa";
			private static final String hsqldbPss= "";

	public static void main(String[] args) {
		bucleConexion();
		System.out.println();
		bucleOperacion();
	}
	
	private static void bucleConexion() {
		Connection con = null;
		Statement st = null;
		long tiini, tifin;
		String querry = "Select count(*) from tvehicles";
		
		tiini = System.currentTimeMillis();
		
		for(int i=0;i<=100;i++) {
			try {
				con = DriverManager.getConnection(hsqldbUrl, hsqldbUser, hsqldbPss);
				st = con.createStatement();
				
				
				
				st.execute(querry);
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(st != null ) try{st.close();} catch (SQLException e) {e.printStackTrace();}
				if(con != null ) try{con.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		tifin = System.currentTimeMillis();
		System.out.print("Tiempo [BucleConexion]= "+ (tifin-tiini));
		
	}
	
	private static void bucleOperacion() {
		Connection con = null;
		Statement st = null;
		long tiini, tifin;
		String querry = "Select count(*) from tvehicles";
		try {
			con = DriverManager.getConnection(hsqldbUrl, hsqldbUser, hsqldbPss);
			st = con.createStatement();
			
			
			tiini = System.currentTimeMillis();
			for (int i=0;i<=100;i++) {
				st.execute(querry);
			}
			
			tifin = System.currentTimeMillis();
			System.out.print("Tiempo [BucleOperacion]= "+ (tifin-tiini));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null ) try{st.close();} catch (SQLException e) {e.printStackTrace();}
			if(con != null ) try{con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}

}
