package uo.r1.lab.p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class Ej3 {
	
	//Hsqldb
	private static final String hsqldbUrl= "jdbc:hsqldb:hsql://localhost" ;
	private static final String hsqldbUser= "sa";
	private static final String hsqldbPss= "";

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		bucleDriver();
		pool();
	}
	
	private static DataSource iniciarPool() throws SQLException {
		DataSource ds_unpooled = DataSources.unpooledDataSource(hsqldbUrl, hsqldbUser, hsqldbPss);  
		Map overrides = new HashMap();  
		overrides.put("minPoolSize", 3); 
		overrides.put("maxPoolSize", 50); 
		overrides.put("initialPoolSize", 3);  
		return DataSources.pooledDataSource(ds_unpooled, overrides );  
	}
	
	private static void pool() throws SQLException {
		Connection con = null;
		Statement st = null;
		long tiini, tifin;
		String querry = "Select count(*) from tvehicles";
		

		DataSource pool = iniciarPool();
		
		tiini = System.currentTimeMillis();
		
		for(int i=0;i<=100;i++) {
			try {
				con = pool.getConnection();
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
		System.out.print("Tiempo [Pool]= "+ (tifin-tiini));
		
	}
	
	private static void bucleDriver() {
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
		System.out.print("Tiempo [Driver]= "+ (tifin-tiini));
		
	}

}
