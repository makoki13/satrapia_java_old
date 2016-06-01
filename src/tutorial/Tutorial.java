package tutorial;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Tutorial extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Tutorial() {

        initUI();
    }
	
	private void createLayout(JComponent... arg) {
                
        JPanel panelMain = new JPanel();
        panelMain.setBorder(BorderFactory.createTitledBorder("Entrada"));
        
        GridLayout gl = new GridLayout(3,1);
        panelMain.setLayout(gl);
        
        JPanel panelSuperior = new JPanel();panelSuperior.setBorder(BorderFactory.createBevelBorder(1));
        panelSuperior.add(arg[0]);        
        
        JPanel panelMedio = new JPanel();panelMedio.setBorder(BorderFactory.createBevelBorder(1));
        GridLayout glm = new GridLayout(3,2);
        panelMedio.setLayout(glm);
        panelMedio.add(arg[3]);panelMedio.add(arg[1]);
        panelMedio.add(arg[4]);panelMedio.add(arg[6]);
        panelMedio.add(arg[5]);
        
        JPanel panelInferior = new JPanel();panelInferior.setBorder(BorderFactory.createBevelBorder(1));
        panelInferior.add(arg[2]);
                
        panelMain.add(panelSuperior);
        panelMain.add(panelMedio);
        panelMain.add(panelInferior);
        
        this.setContentPane(panelMain);
        
        //this.add(arg[0]);
        //this.add(arg[1]);
        //this.add(arg[2]);
        
        
        /*
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup()
                
                .addGroup(gl.createSequentialGroup()
                		.addComponent(arg[1])
                		.addComponent(arg[2])
                		)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
        		.addComponent(arg[0])
        		.addGroup(gl.createParallelGroup()        				
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        		)
        );
        */
    }

    private void initUI() {
        
        setTitle("Satrapía: El tutorial");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JButton quitButton = new JButton("SALIR");
        JLabel label = new JLabel("SATRAPÍA");
        JLabel label_login = new JLabel("USUARIO:");
        JLabel label_pass = new JLabel("CLAVE:");
        JButton enterButton = new JButton("ENTRAR");
        JTextField textFieldLogin = new JTextField(20);
        JTextField textFieldPass = new JTextField(20);
        label_login.setLabelFor(textFieldLogin);
        label_pass.setLabelFor(textFieldPass);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        createLayout(label,textFieldLogin,quitButton,label_login,label_pass,enterButton,textFieldPass);
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

