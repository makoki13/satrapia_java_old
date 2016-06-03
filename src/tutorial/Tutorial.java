package tutorial;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import postgresql.Jdbc;
import satrapia.Mapa;

public class Tutorial extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel panelPrincipal;
	JTextField textFieldLogin;
	JPasswordField textFieldPass;
	JButton enterButton,nuevoButton;
	
	boolean creadoNuevo, creadoTutorial, panelJugador = false;

	public Tutorial() {

        initUI();
    }
	
	private void createLayout(JComponent... arg) {                
        panelPrincipal = new JPanel();
        CardLayout cl = new CardLayout();
        panelPrincipal.setLayout(cl);
        
        ImagePanel panelMain = new ImagePanel(new ImageIcon("imagenes/fondoMain.jpg").getImage());                
        panelMain.setBorder(BorderFactory.createTitledBorder(""));
        
        GridLayout gl = new GridLayout(3,4,1,1);
        panelMain.setLayout(gl);
        
        JPanel panelSuperior = new JPanel();//panelSuperior.setBorder(BorderFactory.createBevelBorder(0));
        panelSuperior.setOpaque(false);
        panelSuperior.add(arg[0]);        
        
        JPanel panelMedio = new JPanel();//panelMedio.setBorder(BorderFactory.createBevelBorder(1));
        panelMedio.setOpaque(false);
        //GridLayout glm = new GridLayout(3,2,5,5);
        GridBagLayout gridBag = new GridBagLayout ();        
        GridBagConstraints restricciones = new GridBagConstraints ();
        restricciones.insets = new Insets(3,3,3,3);
        
        
        restricciones.anchor = GridBagConstraints.EAST;
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 0; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        gridBag.setConstraints(arg[3], restricciones);
        panelMedio.add(arg[3]);
        
        restricciones.gridx = 1; // El área de texto empieza en la columna cero.
        restricciones.gridy = 0; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final        
        gridBag.setConstraints(arg[1], restricciones);
        panelMedio.add(arg[1]);
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 1; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        gridBag.setConstraints(arg[4], restricciones);
        panelMedio.add(arg[4]);
        
        restricciones.gridx = 1; // El área de texto empieza en la columna cero.
        restricciones.gridy = 1; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final        
        gridBag.setConstraints(arg[6], restricciones);
        panelMedio.add(arg[6]);
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 2; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 2; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        restricciones.anchor = GridBagConstraints.CENTER;
        
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final        
        gridBag.setConstraints(arg[5], restricciones);
        panelMedio.add(arg[5]);
        
        restricciones.anchor = GridBagConstraints.BELOW_BASELINE;
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 4; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 2; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 2; // El área de texto ocupa 2 filas.
                
        restricciones.insets = new Insets(1,1,1,1);
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final        
        gridBag.setConstraints(arg[7], restricciones);
        panelMedio.add(arg[7]);
                
        panelMedio.setLayout(gridBag);
        
        JPanel panelInferior = new JPanel();//panelInferior.setBorder(BorderFactory.createBevelBorder(1));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(new EmptyBorder(100, 0, 0, 0));
        panelInferior.add(arg[2]);
                
        panelMain.add(panelSuperior);
        panelMain.add(panelMedio);
        panelMain.add(panelInferior);
        
        panelPrincipal.add(panelMain);
        
        //this.setContentPane(panelMain);
        
        this.setContentPane(panelPrincipal);
    }
	
	Action enterEnLogin = new AbstractAction()
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
	    public void actionPerformed(ActionEvent e)
	    {
	    	textFieldPass.requestFocus(); 
	    }
	};
	
	Action enterEnPass = new AbstractAction()
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
	    public void actionPerformed(ActionEvent e)
	    {
	    	enterButton.requestFocus(); 
	    }
	};
	
	private void verificaUsuario() {
		String login;
		char[] pass;
		long idJugador;
		
		login=textFieldLogin.getText();
		pass=textFieldPass.getPassword();
		idJugador=Usuario.verificaLogin(login, pass);
		if (idJugador>0L) {
			int nivelTutorial = Usuario.nivelTutorial(idJugador);
			if (nivelTutorial==999) {
				CardLayout cl = (CardLayout)(panelPrincipal.getLayout());
				if (panelJugador==false) {
					PanelUsuario panelUsuario = new PanelUsuario(cl,idJugador);
					panelPrincipal.add(panelUsuario,"JUGADOR");
					panelJugador=true;
				}
				cl.show(panelPrincipal, "JUGADOR");
			}
			else {
				JOptionPane.showMessageDialog(null, "Panel Tutorial");
			}			
		}
		else if (idJugador==-1) {
			JOptionPane.showMessageDialog(null, "Debe de indicar un usuario");
			textFieldLogin.setText("");textFieldPass.setText("");
			textFieldLogin.requestFocusInWindow();
		}
		else {
			JOptionPane.showMessageDialog(null, "Usuario no existe o contraseña incorrecta");
			textFieldLogin.setText("");textFieldPass.setText("");
			textFieldLogin.requestFocusInWindow();
		}
	}
	
	public void muestraPanelTutorial(long idJugador) {
		//Pendiente ccrear panel tutorial y activarlo desde aqui.
		CardLayout cl = (CardLayout)(panelPrincipal.getLayout());
		if (creadoTutorial==false) {			
			PanelTutorial panelDelTutorial = new PanelTutorial(cl,this);
			panelPrincipal.add(panelDelTutorial,"TUTORIAL");
			creadoTutorial=true;
		}		
		cl.show(panelPrincipal, "TUTORIAL");
	}
	
	private void addUsuario() {
		CardLayout cl = (CardLayout)(panelPrincipal.getLayout());
		if (creadoNuevo==false) {
			NuevoUsuario panelNuevoUsuario = new NuevoUsuario(cl,this);
			panelPrincipal.add(panelNuevoUsuario,"NUEVO");
			creadoNuevo=true;
		}
		cl.show(panelPrincipal, "NUEVO");		
	}

    private void initUI() {
        
        setTitle("Satrapía: El tutorial");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Font f = new Font("Serif", Font.BOLD + Font.ITALIC, 72);
        JLabel label_titulo = new JLabel("SATRAPÍA");
        label_titulo.setFont(f);label_titulo.setForeground(new Color(244,45,77));
                
        JLabel label_login = new JLabel("USUARIO:");        
        f = new Font("Serif", Font.BOLD, 32);        
        label_login.setFont(f);
        
        JLabel label_pass = new JLabel("CLAVE:");
        label_pass.setFont(f);
                
        f = new Font("Serif", Font.ITALIC, 24);
        
        textFieldLogin = new JTextField(20);
        textFieldLogin.setFont(f);
        textFieldLogin.setBorder(BorderFactory.createCompoundBorder(textFieldLogin.getBorder(),BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        textFieldLogin.addActionListener( enterEnLogin );
        label_login.setLabelFor(textFieldLogin);
        
        textFieldPass = new JPasswordField(20);
        textFieldPass.setFont(f);        
        textFieldPass.setBorder(BorderFactory.createCompoundBorder(textFieldPass.getBorder(),BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        textFieldPass.addActionListener( enterEnPass );
        label_pass.setLabelFor(textFieldPass);
        
        f = new Font("Courier", Font.BOLD, 24);
        
        enterButton = new JButton("ENTRAR");
        enterButton.setBackground(new Color(0,144,0));enterButton.setForeground(Color.WHITE);
        enterButton.setFont(f);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	verificaUsuario();
            }
        });
        enterButton.getInputMap().put(KeyStroke.getKeyStroke("pressed ENTER"),"enterEnEnter");
        enterButton.getActionMap().put("enterEnEnter", new AbstractAction("enterEnEnter") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent evt) {
				verificaUsuario();
            }
          });
        
        nuevoButton = new JButton("NUEVO USUARIO");
        nuevoButton.setBackground(new Color(244,144,144));nuevoButton.setForeground(Color.BLACK);
        nuevoButton.setFont(f);
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//addUsuario(); descomentar despues de test
            	muestraPanelTutorial(14);
            }
        });
        nuevoButton.getInputMap().put(KeyStroke.getKeyStroke("pressed ENTER"),"enterEnEnter");
        nuevoButton.getActionMap().put("enterEnEnter", new AbstractAction("enterEnEnter") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent evt) {
				addUsuario();
            }
          });
        
        JButton quitButton = new JButton("SALIR");
        quitButton.setFont(f);
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        createLayout(label_titulo,textFieldLogin,quitButton,label_login,label_pass,enterButton,textFieldPass,nuevoButton);
    }

    public static void main(String[] args) {
    	
    	Jdbc oBD = new Jdbc();
    	
    	Mapa.carga(oBD);

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                Tutorial ex = new Tutorial();
                ex.setVisible(true);
            }
        });
    }
}

class ImagePanel extends JPanel {

	  private Image img;
	  private static final long serialVersionUID = 6841876236948317038L;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    //g.drawImage(img, 0, 0, null);
		  g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	  }

	}


