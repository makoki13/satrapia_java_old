package postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {
	static public Connection conecta(String servidor,String basededatos, String usuario, String password) {
		String cc = "jdbc:postgresql://"+servidor+"/"+basededatos+"?user="+usuario+"&password="+password;
		try {
			Class.forName("org.postgresql.Driver");
			Connection conexion = DriverManager.getConnection(cc);
			return conexion;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	static public ResultSet consulta(Connection conexion, String sql) {
		Statement comando = null;
		try {
			comando = conexion.createStatement();		
			ResultSet resultado = comando.executeQuery(sql);
			return resultado;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			try {
				comando.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	static public int ejecuta(Connection conexion, String sql) {
		Statement comando = null;
		try {
			comando = conexion.createStatement();		
			int filas = comando.executeUpdate(sql);
			return filas;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}		 
				
	}
}
