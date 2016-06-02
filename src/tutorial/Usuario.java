package tutorial;

import java.util.Arrays;

public class Usuario {
	static boolean verificaLogin(String login, char[] pass) {
		return true;
	}
	
	static int verificaAlta(String login, char[] pass, char[] pass2) {
		if (!(Arrays.equals(pass, pass2))) return -1;
		if(login == null || login.trim().isEmpty() || login.trim()=="" ) return -2;
				
		return 0;
	}
	
	static int creaUsuario(String login, char[] pass) {
		//String password = new String(pass);
		return 0;
	}
}
