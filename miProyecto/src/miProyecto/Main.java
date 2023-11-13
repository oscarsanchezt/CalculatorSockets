package miProyecto;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField fielResultado;
    private JButton btnNewButton;
    private JButton btnRestar;
    private JButton btnMultiplicar;
    private JButton btnDividir;
    private JLabel lblNewLabel;
    private JLabel lblSegundoNumero;
    private JSeparator separator;
    private JLabel labelResultado;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        setTitle("Calculadora Socket Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setBounds(82, 44, 122, 26);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(82, 104, 122, 26);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        btnNewButton = new JButton("Sumar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                enviarOperacion("Sumar");
            }
        });
        btnNewButton.setBounds(311, 19, 90, 26);
        contentPane.add(btnNewButton);

        btnRestar = new JButton("Restar");
        btnRestar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                enviarOperacion("Restar");
            }
        });
        btnRestar.setBounds(311, 57, 90, 26);
        contentPane.add(btnRestar);

        btnMultiplicar = new JButton("Multiplicar");
        btnMultiplicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                enviarOperacion("Multiplicar");
            }
        });
        btnMultiplicar.setBounds(311, 95, 90, 26);
        contentPane.add(btnMultiplicar);

        btnDividir = new JButton("Dividir");
        btnDividir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                enviarOperacion("Dividir");
            }
        });
        btnDividir.setBounds(311, 133, 90, 26);
        contentPane.add(btnDividir);

        lblNewLabel = new JLabel("Primer numero");
        lblNewLabel.setBounds(102, 25, 89, 14);
        contentPane.add(lblNewLabel);

        lblSegundoNumero = new JLabel("Segundo numero");
        lblSegundoNumero.setBounds(92, 82, 102, 14);
        contentPane.add(lblSegundoNumero);

        separator = new JSeparator();
        separator.setBounds(31, 177, 379, 8);
        contentPane.add(separator);

        fielResultado = new JTextField();
        fielResultado.setBounds(113, 210, 216, 26);
        contentPane.add(fielResultado);
        fielResultado.setColumns(10);

        labelResultado = new JLabel("Resultado");
        labelResultado.setBounds(187, 184, 90, 14);
        contentPane.add(labelResultado);
    }

    private void enviarOperacion(String operacion) {
        try {
            double num1 = Double.parseDouble(textField.getText());
            double num2 = Double.parseDouble(textField_1.getText());

            Socket socket = new Socket("localhost", 12345);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(operacion);
            dos.writeDouble(num1);
            dos.writeDouble(num2);

            double resultado = dis.readDouble();
            fielResultado.setText(Double.toString(resultado));

            socket.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

