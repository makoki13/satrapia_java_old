package test;

import java.sql.*;
import postgresql.*;

public class Test {
	
	public static void conecta() {		
		String servidor = "172.16.0.72";
		String baseDeDatos = "Produccion";
		String usuario = "postgres";
		String password = "vimvsp";
		Connection conexion = Jdbc.conecta(servidor, baseDeDatos, usuario, password);
		try {
			String sql ="SELECT Codigo,Nombre FROM Articulos WHERE (Alta='S' or Alta IS NULL) AND Tipo IN ('P') ORDER BY Codigo LIMIT 5";
			ResultSet resultado = Jdbc.consulta(conexion, sql);
			while(resultado.next()) {
				String n = resultado.getString("Codigo");
				String a = resultado.getString("Nombre");
				System.out.println(n + " " + a);
			}
			resultado.close();			
						
			sql = "UPDATE articulos_200809 SET Nombre='VALENCIANA CONSUM 10 ptes. 700gr.' WHERE Codigo='01070'";
			Integer filas = Jdbc.ejecuta(conexion, sql);
			System.out.println("Modificadas "+filas.toString()+" filas");
			
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
