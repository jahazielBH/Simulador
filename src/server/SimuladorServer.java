/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import model.Reporte;
import service.Service;

/**
 *
 * @author jahaziel
 */
public class SimuladorServer {

    private static final String _IP = "25.85.157.68";
    private static final int _PUERTO = 1234;
    private static final int _BACKLOG = 50;

    public static void main(String args[]) throws UnknownHostException {

        InetAddress ip = InetAddress.getByName(_IP);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        Reporte reporte;
        Service service = new Service();

        try {
            System.out.println("IP de LocalHost = " + InetAddress.getLocalHost().toString());
            System.out.println("\nEscuhando en : ");
            System.out.println("IP Host = " + ip.getHostAddress());
            System.out.println("Puerto = " + _PUERTO);
        } catch (UnknownHostException uhe) {
            System.err.println("No puedo saber la direccion IP local : " + uhe);
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(_PUERTO, _BACKLOG, ip);
        } catch (IOException ioe) {
            System.err.println("Error al abrir el socket de servidor : " + ioe);
            System.exit(-1);
        }

        while (true) {
            try {
                Socket socketPeticion = serverSocket.accept();

                DataInputStream datosEntrada = new DataInputStream(
                        new BufferedInputStream(socketPeticion.getInputStream()));
                DataOutputStream datosSalida = new DataOutputStream(socketPeticion.getOutputStream());

                int puertoRemitente = socketPeticion.getPort();

                InetAddress ipRemitente = socketPeticion.getInetAddress();

                char tipoDato = datosEntrada.readChar();

                int longitud = datosEntrada.readInt();

                if (tipoDato == 's') {
                    byte[] bytesDatos = new byte[longitud];

                    boolean finData = false;

                    StringBuilder dataEnMensaje = new StringBuilder(longitud);

                    int totalBytesLeidos = 0;

                    while (!finData) {
                        int bytesActualesLeidos = datosEntrada.read(bytesDatos);

                        totalBytesLeidos = bytesActualesLeidos + totalBytesLeidos;

                        if (totalBytesLeidos <= longitud) {
                            dataEnMensaje.append(new String(bytesDatos, 0, bytesActualesLeidos, StandardCharsets.UTF_8));
                        } else {
                            dataEnMensaje.append(new String(bytesDatos, 0, longitud - totalBytesLeidos + bytesActualesLeidos, StandardCharsets.UTF_8));
                        }

                        if (dataEnMensaje.length() >= longitud) {
                            finData = true;
                        }
                    }

                    String nombreSensor = dataEnMensaje.toString().split("\\|")[1].split("\\#")[0];
                    String fecha = dataEnMensaje.toString().split("\\|")[2].split("\\#")[0];
                    String hora = dataEnMensaje.toString().split("\\|")[3].split("\\#")[0];
                    String movimiento = dataEnMensaje.toString().split("\\|")[4].split("\\#")[0];
                    String imagen = dataEnMensaje.toString().split("\\|")[5].split("\\#")[0];

                    reporte = new Reporte();
                    
                    reporte.setIp(ipRemitente);
                    reporte.setNombreSensor(nombreSensor);
                    reporte.setMovimiento(movimiento);
                    reporte.setImagen(imagen);
                    reporte.setFecha(fecha);
                    reporte.setHora(hora);
                    
                    service.add(reporte);
                    
                    System.out.println(
                            "Cliente = " + ipRemitente + ":" + puertoRemitente
                            + "\tEntrada = " + dataEnMensaje.toString()
                            + "\tSalida = " + "OK");
                }
                datosSalida.writeUTF("OK");
                datosEntrada.close();
                datosSalida.close();
                socketPeticion.close();

            } catch (Exception e) {
                System.out.println("Se ha producido la excepcion : " + e);
            }
        }
    }
}
