package com.taller.dao;
import com.taller.modelo.Usuario;
import com.taller.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UsuarioDAO {
    public static String validarPoliticaPassword(String username, String password) {
        if (password == null || password.length() < 5) {
            return "La contraseña debe tener al menos 5 caracteres.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "La contraseña debe incluir al menos una letra mayúscula.";
        }
        if (!password.matches(".*[0-9].*")) {
            return "La contraseña debe incluir al menos un número.";
        }
        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            return "La contraseña debe incluir al menos un carácter especial.";
        }
        if (username != null && !username.trim().isEmpty() && password.toLowerCase().contains(username.trim().toLowerCase())) {
            return "La contraseña no puede ser igual ni contener el nombre de usuario.";
        }
        return null;
    }
    public boolean existenUsuarios() {
        String sql = "SELECT COUNT(*) FROM usuarios";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuarios: " + e.getMessage());
        }
        return false;
    }
    public Usuario validarUsuario(String username, String password) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setRequiereCambio(rs.getBoolean("requiere_cambio"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }
        return usuario;
    }
    public boolean registrarUsuario(Usuario u, boolean requiereCambio) {
        String sql = "INSERT INTO usuarios (username, password, rol, requiere_cambio) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRol());
            ps.setInt(4, requiereCambio ? 1 : 0);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
    public boolean actualizarPassword(int idUsuario, String nuevaPassword) {
        String sql = "UPDATE usuarios SET password = ?, requiere_cambio = 0 WHERE id_usuario = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevaPassword);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar contraseña: " + e.getMessage());
            return false;
        }
    }
}