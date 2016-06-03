package tutorial;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelUsuario extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton enterButton;
	
	CardLayout padre;
	satrapia.Jugador jugador;
	
	Image imagen = new ImageIcon("imagenes/fondoMain.jpg").getImage();
	
	public PanelUsuario(CardLayout p, long idJugador) {
		super();
		
		padre=p;
		jugador = new satrapia.Jugador(idJugador);
		
		initUI();
	} 
	
	public void run() { while (true) {repaint();}}
	
	@Override
    public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), this);
    }
			
	private void createLayout(JComponent... arg) {      
		 //ImagePanel panelMain = new ImagePanel(new ImageIcon("imagenes/fondoMain.jpg").getImage());  
        JPanel panelMain = new JPanel();
        //panelMain.setBorder(BorderFactory.createTitledBorder(""));
        panelMain.setOpaque(false);
        
        //ImagePanel panelMain = new ImagePanel(imagen);                
        //panelMain.setBorder(BorderFactory.createTitledBorder(""));
        //panelMain.setOpaque(false);
   
        
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
        
        restricciones.anchor = GridBagConstraints.CENTER;
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 0; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        gridBag.setConstraints(arg[2], restricciones);
        panelMedio.add(arg[2]);
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 1; // El área de texto empieza en la fila cero
        restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        
        gridBag.setConstraints(arg[3], restricciones);
        panelMedio.add(arg[3]);
                    
        panelMedio.setLayout(gridBag);
        
        JPanel panelInferior = new JPanel();//panelInferior.setBorder(BorderFactory.createBevelBorder(1));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(new EmptyBorder(100, 0, 0, 0));
        panelInferior.add(arg[1]);
                
        panelMain.add(panelSuperior);
        panelMain.add(panelMedio);
        panelMain.add(panelInferior);
        
        this.add(panelMain);
    }
	
	public void anterior() {
		padre.previous(this.getParent());
	}
	
	
	private void initUI() {        
        Font f = new Font("Serif", Font.BOLD + Font.ITALIC, 72);
        JLabel label_titulo = new JLabel("SATRAPÍA");
        label_titulo.setFont(f);label_titulo.setForeground(new Color(244,45,77));
                
        JLabel labelUsuario = new JLabel("Bienvenido "+jugador._get_Nombre());        
        f = new Font("Serif", Font.BOLD, 32);
        labelUsuario.setFont(f);
        
        JLabel labelNoticias = new JLabel("El juego todavía no está abierto. ¡Paciencia!");        
        f = new Font("Serif", Font.BOLD, 32);
        labelNoticias.setFont(f);
                
        f = new Font("Courier", Font.BOLD, 24);
                        
        JButton quitButton = new JButton("SALIR");
        quitButton.setFont(f);
        
        quitButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        createLayout(label_titulo,quitButton,labelUsuario,labelNoticias);
    }
}
