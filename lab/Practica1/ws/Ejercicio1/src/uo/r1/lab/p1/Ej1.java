package uo.r1.lab.p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ej1 {
	
	//Oracle
		private static final String oracleUrl="jdbc:oracle:thin:@156.35.94.98:1521:desa19";
		private static final int oraclePort=1521;
		private static final String oracleUser="UO293697";
		private static final String oraclePss="jorgeBs04";
		
	//Hsqldb
		private static final String hsqldbUrl= "jdbc:hsqldb:hsql://localhost" ;
		private static final String hsqldbUser= "sa";
		private static final String hsqldbPss= "";

	public static void main(String[] args) {
		statement();
		System.out.println();
		preparedStatement();

	}

	private static void preparedStatement() {
		Connection con = null;
		PreparedStatement st = null;
		long tiini, tifin;
		String inicio = "update tinvoices set amount = amount + ?  where number = ? ";//id en oracle number en hsqldb
		try {
			con = DriverManager.getConnection(hsqldbUrl, hsqldbUser, hsqldbPss);
			st = con.prepareStatement(inicio);
			
			
			tiini = System.currentTimeMillis();
			for (int i=0;i<=1000;i++) {
				//st.setString(2, String.valueOf(i));//number es int
				st.setInt(2, i);
				st.setDouble(1, i);
				st.executeUpdate();
			}
			
			tifin = System.currentTimeMillis();
			System.out.print("Tiempo [Prepared]= "+ (tifin-tiini));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null ) try{st.close();} catch (SQLException e) {e.printStackTrace();}
			if(con != null ) try{con.close();} catch (SQLException e) {e.printStackTrace();}
		}
	}

	private static void statement() {
		Connection con = null;
		Statement st = null;
		String inicio = "update tinvoices set amount = amount + ";
		String finals = " where number = ";
		
		long tiini, tifin;
		
		try {
			con = DriverManager.getConnection(hsqldbUrl, hsqldbUser, hsqldbPss);
			st = con.createStatement();
			
			tiini = System.currentTimeMillis();
			for (int i=0;i<=1000;i++) {
				st.executeUpdate(inicio+i+finals+i);
			}
			
			tifin = System.currentTimeMillis();
			
			System.out.print("Tiempo [Statement]= "+ (tifin-tiini));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null ) try{st.close();} catch (SQLException e) {e.printStackTrace();}
			if(con != null ) try{con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		
		
		
	}
	
	
	

}
