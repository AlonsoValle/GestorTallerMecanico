package com.taller.dao;

import com.taller.modelo.Bitacora;
import com.taller.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BitacoraDAO {

    public boolean registrarMovimiento(String usuario, String accion, String detalle) {
        String sql = "INSERT INTO bitacora (usuario, accion, detalle) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, accion);
            ps.setString(3, detalle);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar en bitácora: " + e.getMessage());
            return false;
        }
    }

    // Listar todos los movimientos (del más reciente al más antiguo)
    public List<Bitacora> listarMovimientos() {
        List<Bitacora> lista = new ArrayList<>();
        String sql = "SELECT * FROM bitacora ORDER BY id_movimiento DESC";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Bitacora b = new Bitacora();
                b.setIdMovimiento(rs.getInt("id_movimiento"));
                b.setUsuario(rs.getString("usuario"));
                b.setAccion(rs.getString("accion"));
                b.setDetalle(rs.getString("detalle"));
                b.setFecha(rs.getTimestamp("fecha"));
                lista.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar bitácora: " + e.getMessage());
        }
        return lista;
    }
}