/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import util.Base64Image;

/**
 *
 * @author jahaziel
 */
public class SimuladorCliente {
    private static final int _PUERTO = 1234;
    private static final String _IP = "25.85.157.68";
    

    public static void main(String args[]) {

        InetAddress ipServidor = null;
        try {
            ipServidor = InetAddress.getByName(_IP);
        } catch (UnknownHostException uhe) {
            System.err.println("Host no encontrado : " + uhe);
            System.exit(-1);
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre del sensor: ");
        String nombreSensor = sc.next();
        
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        
        Base64Image base64 = new Base64Image();

        while (true) {

            Socket socketCliente = null;
            DataInputStream datosRecepcion = null;
            DataOutputStream datosEnvio = null;

            try {
                // Simulamos obtencion de variables
                
                double movimiento = Math.round((Math.random() * 40 + 16) * 100.0) / 100.0;
                
                String imagePath = "/home/jahaziel/NetBeansProjects/Simulador/src/image/Ratero.jpg";
                String img = "photo.jpg";
                // Establecemos los valores para el paquete segun el protocolo
                char tipo = 's';// s para string
                String data = "$#Nom|" + nombreSensor + "#Fec|" + fecha.format(new Date()) + "#Hr|" + hora.format(new Date()) + "#Mov|" + movimiento + "#Img|" + base64.encoder(imagePath) + "#$";
                byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);

                // Creamos el socket
                socketCliente = new Socket(ipServidor, _PUERTO);

                // Extraemos los streams de entrada y salida
                datosRecepcion = new DataInputStream(socketCliente.getInputStream());
                datosEnvio = new DataOutputStream(socketCliente.getOutputStream());

                // Lo escribimos datos en el flujo segun el protocolo
                datosEnvio.writeChar(tipo);
                datosEnvio.writeInt(dataInBytes.length);
                datosEnvio.write(dataInBytes);

                // Leemos el resultado final
                String resultado = datosRecepcion.readUTF();
                
                Thread.sleep(5000);

                // Indicamos en pantalla
                System.out.println("Solicitud = " + data + "\tResultado = " + resultado);

                // y cerramos los streams y el socket
                datosRecepcion.close();
                datosEnvio.close();

            } catch (Exception e) {
                System.err.println("Se ha producido la excepcion : " + e);
            }

            
            try {
                if (socketCliente != null)
                    socketCliente.close();
            } catch (IOException ioe) {
                System.err.println("Error al cerrar el socket : " + ioe);
            }
        }
    }
}
