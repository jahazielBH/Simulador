/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.InetAddress;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author jahaziel
 */
public class Reporte {
    
    private Long id;
    private InetAddress ip;
    private String nombreSensor;
    private String movimiento;
    private String imagen;
    private String fecha;
    private String hora;

    public Reporte() {
    }

    public Reporte(InetAddress ip, String nombreSensor, String movimiento, String imagen, String fecha, String hora) {
        this.ip = ip;
        this.nombreSensor = nombreSensor;
        this.movimiento = movimiento;
        this.imagen = imagen;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getNombreSensor() {
        return nombreSensor;
    }

    public void setNombreSensor(String nombreSensor) {
        this.nombreSensor = nombreSensor;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
}
