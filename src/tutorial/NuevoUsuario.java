package tutorial;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class NuevoUsuario extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField  textFieldLogin;
	JPasswordField textFieldPass, textFieldPass2;
	JButton enterButton;
	
	CardLayout padre;
	
	public NuevoUsuario(CardLayout p) {
		super();
		
		padre=p; initUI();
		
		this.addComponentListener( new ComponentAdapter() {
	        @Override
	        public void componentShown( ComponentEvent e ) {
	            textFieldLogin.requestFocusInWindow();
	        }
	    });
	} 
	
	public void run() { while (true) {repaint();}}
	
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
	    	textFieldPass2.requestFocus(); 
	    }
	};
	
	Action enterEnPass2 = new AbstractAction()
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
	
	private void createLayout(JComponent... arg) {        
        ImagePanel panelMain = new ImagePanel(new ImageIcon("imagenes/fondoMain.jpg").getImage());
        //panelMain.setPreferredSize(new Dimension(1024,800)); No funciona        
        panelMain.setBorder(BorderFactory.createTitledBorder(""));
        
        
        
        GridLayout gl = new GridLayout(3,1,5,5);
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
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        gridBag.setConstraints(arg[7], restricciones);
        panelMedio.add(arg[7]);
        
        restricciones.gridx = 2; // El área de texto empieza en la columna cero.
        restricciones.gridy = 2; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
       
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final  
        gridBag.setConstraints(arg[8], restricciones);
        panelMedio.add(arg[8]);
           
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 3; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 2; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        restricciones.anchor = GridBagConstraints.CENTER;
        
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final        
        gridBag.setConstraints(arg[5], restricciones);
        panelMedio.add(arg[5]);
                
        panelMedio.setLayout(gridBag);
        
        JPanel panelInferior = new JPanel();//panelInferior.setBorder(BorderFactory.createBevelBorder(1));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(new EmptyBorder(100, 0, 0, 0));
        panelInferior.add(arg[2]);
                
        panelMain.add(panelSuperior);
        panelMain.add(panelMedio);
        panelMain.add(panelInferior);
        
        this.add(panelMain);
        
        //panelPrincipal.add(panelMain);
        
        //this.setContentPane(panelMain);
        
        //this.setContentPane(panelPrincipal);
    }
	
	public void anterior() {
		padre.previous(this.getParent());
	}
	
	private void verificaAlta() {
		String login;
		char[] pass, pass2;
		
		login=textFieldLogin.getText();
		pass=textFieldPass.getPassword();
		pass2=textFieldPass2.getPassword();
		int resultado=Usuario.verificaAlta(login.trim(), pass, pass2);
		if (resultado==-1) {
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden o están en blanco");
			textFieldPass.setText("");textFieldPass2.setText("");
			textFieldPass.requestFocusInWindow();
		}
		else if (resultado==-2) {
			JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar en blanco");
			textFieldLogin.setText("");textFieldPass.setText("");textFieldPass2.setText("");
			textFieldLogin.requestFocusInWindow();
		}
		else if (resultado==-3) {
			JOptionPane.showMessageDialog(null, "Las contraseña no puede estar en blanco");
			textFieldPass.setText("");textFieldPass2.setText("");
			textFieldPass.requestFocusInWindow();
		}
		else {
			Usuario.creaUsuario(login,pass);			
			JOptionPane.showMessageDialog(null, "Usuario creado ");
		}
	}

	private void initUI() {
        
        //setTitle("Satrapía: El tutorial");
        //setSize(1024, 768);
        //setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Font f = new Font("Serif", Font.BOLD + Font.ITALIC, 72);
        JLabel label_titulo = new JLabel("SATRAPÍA");
        label_titulo.setFont(f);label_titulo.setForeground(new Color(244,45,77));
                
        JLabel label_login = new JLabel("NUEVO USUARIO:");        
        f = new Font("Serif", Font.BOLD, 32);
        label_login.setFont(f);
        
        JLabel label_pass = new JLabel("CLAVE:");
        label_pass.setFont(f);
       
        JLabel label_pass2 = new JLabel("REPITA CLAVE:");
        label_pass2.setFont(f);
                
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
        
        textFieldPass2 = new JPasswordField(20);        
        textFieldPass2.setFont(f);
        textFieldPass2.setBorder(BorderFactory.createCompoundBorder(textFieldPass2.getBorder(),BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        textFieldPass2.addActionListener( enterEnPass2 );
        label_pass2.setLabelFor(textFieldPass2);
        
        f = new Font("Courier", Font.BOLD, 24);
        
        enterButton = new JButton("CREAR USUARIO");
        enterButton.setBackground(new Color(0,144,0));enterButton.setForeground(Color.WHITE);
        enterButton.setFont(f);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	verificaAlta();
            }
        });
        enterButton.getInputMap().put(KeyStroke.getKeyStroke("pressed ENTER"),"enterEnEnter");
        enterButton.getActionMap().put("enterEnEnter", new AbstractAction("enterEnEnter") {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent evt) {
				verificaAlta();
            }
          });
        
        JButton quitButton = new JButton("ATRAS");
        quitButton.setFont(f);
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//JOptionPane.showMessageDialog(null, "ATRAS");
            	anterior();
            }
        });

        createLayout(label_titulo,textFieldLogin,quitButton,label_login,label_pass,enterButton,textFieldPass,label_pass2,textFieldPass2);
    }
}
