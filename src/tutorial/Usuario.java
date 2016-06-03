package tutorial;

import java.util.Arrays;

public class Usuario {
	static long verificaLogin(String login, char[] pass) {
		if(login == null || login.trim().isEmpty() || login.trim()=="" ) return -1L;
		String password = new String(pass);
		long idJugador = satrapia.Jugador.existe(login, password);
		if (idJugador==-1) return -2L;
		return idJugador;
	}
	
	static int verificaAlta(String login, char[] pass, char[] pass2) {
		if (!(Arrays.equals(pass, pass2))) return -1;
		if(login == null || login.trim().isEmpty() || login.trim()=="" ) return -2;
				
		return 0;
	}
	
	static long creaUsuario(String login, char[] pass) {
		if (satrapia.Jugador.existe(login)!=-1) return -1; //Si existe no lo damos de alta :-)
		String password = new String(pass);
		return satrapia.Jugador.creaUsuario(login, password);		
	}
	
	static int nivelTutorial(long idJugador) {
		satrapia.Jugador j = new satrapia.Jugador(idJugador);
		return j._get_NivelTutorial();		
	}
}
