package test;

import java.sql.*;

public class Test {
	
	public static void conecta() {
		String cc = "jdbc:postgresql://172.16.0.72/Produccion?user=postgres&password=vimvsp";
		try {
			Class.forName("org.postgresql.Driver");
			Connection conexion = DriverManager.getConnection(cc);
			Statement comando = conexion.createStatement();
			String sql ="SELECT Codigo,Nombre FROM Articulos WHERE (Alta='S' or Alta IS NULL) AND Tipo IN ('P') ORDER BY Codigo LIMIT 5";
			ResultSet resultado = comando.executeQuery(sql);
			while(resultado.next()) {
				String n = resultado.getString("Codigo");
				String a = resultado.getString("Nombre");
				System.out.println(n + " " + a);
			}
			resultado.close();
			comando.close();
			conexion.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("Inicio -----");
		Test.conecta();
	}

}
