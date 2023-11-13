package miProyecto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                // Crear flujos de entrada/salida para la comunicación con el cliente
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                // Leer operación y operandos del cliente
                String operacion = dis.readUTF();
                double num1 = dis.readDouble();
                double num2 = dis.readDouble();

                // Calcular el resultado
                double resultado = calcular(operacion, num1, num2);

                // Enviar el resultado al cliente
                dos.writeDouble(resultado);

                // Cerrar conexiones
                socket.close();
                dis.close();
                dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calcular(String operacion, double num1, double num2) {
        double resultado = 0;
        switch (operacion) {
            case "Sumar":
                resultado = num1 + num2;
                break;
            case "Restar":
                resultado = num1 - num2;
                break;
            case "Multiplicar":
                resultado = num1 * num2;
                break;
            case "Dividir":
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    System.out.println("Error: División por cero.");
                }
                break;
        }
        return resultado;
    }
}
