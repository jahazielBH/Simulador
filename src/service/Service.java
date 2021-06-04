/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import db.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Reporte;
import server.SimuladorServer;

/**
 *
 * @author jahaziel
 */
public class Service {
    ConexionDB con;
    ResultSet rs;
    PreparedStatement ps;
    
    public void add(Reporte reporte) {
        String query = "INSERT INTO reporte (fecha,hora,imagen,ip,movimiento,nombre_sensor) VALUES (?,?,?,?,?,?)";
        try {
            con = ConexionDB.getInstace();
            ps = con.getConnection().prepareStatement(query);
            ps.setString(1, reporte.getFecha());
            ps.setString(2, reporte.getHora());
            ps.setString(3, reporte.getImagen());
            ps.setString(4, String.valueOf(reporte.getIp()));
            ps.setString(5, reporte.getMovimiento());
            ps.setString(6, reporte.getNombreSensor());
            ps.execute();
        } catch (SQLException e) {
            Logger.getLogger(SimuladorServer.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(SimuladorServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
