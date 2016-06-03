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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class PanelTutorial extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	Image imagen = new ImageIcon("imagenes/fondoMain.jpg").getImage();
	
	CardLayout padre;
	Tutorial framePadre;
	
	public void run() { while (true) {repaint();}}
		
	public PanelTutorial(CardLayout p, Tutorial fp) {
		super();
		
		padre=p;		
		framePadre = fp;
		
		initUI();
		
	} 
	
	@Override
    public void paintComponent(Graphics g){
		super.paintComponent(g);
        g.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), this);
    }
	
	public void anterior() {
		padre.previous(this.getParent());		
	}
	
	private void createLayout(JComponent... arg) {
		int FILAS = 5;
		int COLUMNAS = 5;
        //ImagePanel panelMain = new ImagePanel(new ImageIcon("imagenes/fondoMain.jpg").getImage());  
        JPanel panelMain = new JPanel();
        //panelMain.setBorder(BorderFactory.createTitledBorder(""));
        panelMain.setOpaque(false);
                
        GridLayout gl = new GridLayout(3,1,5,5);
        panelMain.setLayout(gl);
        
        //JPanel panelSuperior = new JPanel();//panelSuperior.setBorder(BorderFactory.createBevelBorder(0));
        //panelSuperior.setOpaque(false);
        //panelSuperior.add(arg[0]);        
        
        JPanel panelMedio = new JPanel();//panelMedio.setBorder(BorderFactory.createBevelBorder(1));
        panelMedio.setOpaque(false);
        GridBagLayout gridBag = new GridBagLayout ();        
        GridBagConstraints restricciones = new GridBagConstraints ();
        restricciones.insets = new Insets(3,3,3,3);
        
        restricciones.anchor = GridBagConstraints.CENTER;
                
        Font f = new Font("Serif", Font.BOLD + Font.ITALIC, 24);
        JTextArea textoExplicativo = new JTextArea("\nA continuación vamos a progresar en la construcción de tu imperio.\n");        
        textoExplicativo.setOpaque(false);
        textoExplicativo.setWrapStyleWord(true);        
        textoExplicativo.setFont(f);textoExplicativo.setForeground(Color.BLACK);
        
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = 0; // El área de texto empieza en la fila cero
        restricciones.gridwidth = COLUMNAS; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        //restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        gridBag.setConstraints(textoExplicativo, restricciones);
        panelMedio.add(textoExplicativo);
        
        ArrayList<JButton> listaBotones = new ArrayList<JButton>();
        f = new Font("Courier", Font.BOLD, 24);
        //restricciones.anchor = GridBagConstraints.CENTER;
        for(int i=0;i<FILAS;i++) {
        	for(int j=0;j<COLUMNAS;j++) {        		
        		restricciones.gridx = i+1; // El área de texto empieza en la columna cero.
                restricciones.gridy = j+1; // El área de texto empieza en la fila cero
                restricciones.gridwidth = 1; // El área de texto ocupa dos columnas.
                restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
                restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
                
                JButton botonTarea = new JButton("TAREA");
                botonTarea.setFont(f);
                
                botonTarea.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                    	//JOptionPane.showMessageDialog(null, "ATRAS");
                    	anterior();
                    }
                });
                
                listaBotones.add(botonTarea);
                
                gridBag.setConstraints(botonTarea, restricciones);
                panelMedio.add(botonTarea);
        	}
        }                 
        
        f = new Font("Serif", Font.ITALIC, 18);
        JTextArea textoBoton = new JTextArea("\nSelecciona un botón para realizar la tarea.\nEsta es la segunda línea CAÑÓN");
        textoBoton.setOpaque(false);
        textoBoton.setWrapStyleWord(true);
        textoBoton.setFont(f);textoBoton.setForeground(Color.BLUE);
        restricciones.gridx = 0; // El área de texto empieza en la columna cero.
        restricciones.gridy = COLUMNAS+1; // El área de texto empieza en la fila cero
        restricciones.gridwidth = COLUMNAS; // El área de texto ocupa dos columnas.
        restricciones.gridheight = 1; // El área de texto ocupa 2 filas.
        //restricciones.weighty = 1; // La fila 0 debe estirarse, le ponemos un 1.0
        gridBag.setConstraints(textoBoton, restricciones);
        panelMedio.add(textoBoton);
        
        panelMedio.setLayout(gridBag);
        
        JPanel panelInferior = new JPanel();//panelInferior.setBorder(BorderFactory.createBevelBorder(1));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(new EmptyBorder(100, 0, 0, 0));
        panelInferior.add(arg[1]);
                
        //panelMain.add(panelSuperior);
        panelMain.add(panelMedio);
        panelMain.add(panelInferior);
        
        this.add(panelMain);        
    }
	
	private void initUI() {
        
        Font f = new Font("Serif", Font.BOLD + Font.ITALIC, 72);
        JLabel label_titulo = new JLabel("SATRAPÍA");
        label_titulo.setFont(f);label_titulo.setForeground(new Color(244,45,77));
        
        f = new Font("Courier", Font.BOLD, 24);
        JButton quitButton = new JButton("ATRAS");
        quitButton.setFont(f);
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//JOptionPane.showMessageDialog(null, "ATRAS");
            	anterior();
            }
        });

        createLayout(label_titulo,quitButton);
    }	
}
