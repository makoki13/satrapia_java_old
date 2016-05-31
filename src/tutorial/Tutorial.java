package tutorial;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
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
        panelMedio.add(arg[1]);
        
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
        JTextField textField = new JTextField(20);
        label.setLabelFor(textField);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        createLayout(label,textField,quitButton);
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
