package tutorial;

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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Tutorial extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Tutorial() {

        initUI();
    }
	
	private void createLayout(JComponent... arg) {
                
        //JPanel panelMain = new JPanel();
        ImagePanel panelMain = new ImagePanel(new ImageIcon("fondo.png").getImage());
        
        panelMain.setBorder(BorderFactory.createTitledBorder("Entrada"));
        
        GridLayout gl = new GridLayout(3,1,5,5);
        panelMain.setLayout(gl);
        
        JPanel panelSuperior = new JPanel();//panelSuperior.setBorder(BorderFactory.createBevelBorder(0));
        panelSuperior.add(arg[0]);        
        
        JPanel panelMedio = new JPanel();//panelMedio.setBorder(BorderFactory.createBevelBorder(1));
        //GridLayout glm = new GridLayout(3,2,5,5);
        GridBagLayout gridBag = new GridBagLayout ();        
        GridBagConstraints restricciones = new GridBagConstraints ();
        restricciones.insets = new Insets(3,3,3,3);
        
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
        
        restricciones.gridwidth = GridBagConstraints.REMAINDER;  // Fila final        
        gridBag.setConstraints(arg[5], restricciones);
        panelMedio.add(arg[5]);
                
        panelMedio.setLayout(gridBag);
        
        JPanel panelInferior = new JPanel();//panelInferior.setBorder(BorderFactory.createBevelBorder(1));
        panelInferior.setBorder(new EmptyBorder(100, 0, 0, 0));
        panelInferior.add(arg[2]);
                
        panelMain.add(panelSuperior);
        panelMain.add(panelMedio);
        panelMain.add(panelInferior);
        
        this.setContentPane(panelMain);
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
        f = new Font("Serif", Font.PLAIN, 24);
        label_login.setFont(f);
        JLabel label_pass = new JLabel("CLAVE:");
        label_pass.setFont(f);
                
        f = new Font("Serif", Font.ITALIC, 24);
        
        JTextField textFieldLogin = new JTextField(20);
        textFieldLogin.setFont(f);
        textFieldLogin.setBorder(BorderFactory.createCompoundBorder(textFieldLogin.getBorder(),BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        label_login.setLabelFor(textFieldLogin);
        
        JTextField textFieldPass = new JTextField(20);
        textFieldPass.setFont(f);        
        textFieldPass.setBorder(BorderFactory.createCompoundBorder(textFieldPass.getBorder(),BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        label_pass.setLabelFor(textFieldPass);
        
        f = new Font("Courier", Font.BOLD, 24);
        
        JButton enterButton = new JButton("ENTRAR");
        enterButton.setBackground(new Color(0,144,0));enterButton.setForeground(Color.WHITE);
        enterButton.setFont(f);
        
        JButton quitButton = new JButton("SALIR");
        quitButton.setFont(f);
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        createLayout(label_titulo,textFieldLogin,quitButton,label_login,label_pass,enterButton,textFieldPass);
    }

    public static void main(String[] args) {

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
	    g.drawImage(img, 0, 0, null);
	  }

	}


