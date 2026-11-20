package com.taller.dao;

import com.taller.modelo.Item;
import com.taller.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public List<Item> listarInventario(String busqueda, String tipoFiltro, String ordenId) {
        List<Item> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario_taller WHERE 1=1 ";

        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql += " AND descripcion LIKE ? ";
        }
        if (tipoFiltro != null && !tipoFiltro.trim().isEmpty() && !tipoFiltro.equals("Todos")) {
            sql += " AND tipo_item = ? ";
        }

        if ("menor".equals(ordenId)) {
            sql += " ORDER BY id_item ASC";
        } else {
            sql += " ORDER BY id_item DESC"; // Por defecto de mayor a menor
        }

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int paramIndex = 1;
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + busqueda.trim() + "%");
            }
            if (tipoFiltro != null && !tipoFiltro.trim().isEmpty() && !tipoFiltro.equals("Todos")) {
                ps.setString(paramIndex++, tipoFiltro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setIdItem(rs.getInt("id_item"));
                    item.setTipoItem(rs.getString("tipo_item"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setCantidadStock(rs.getInt("cantidad_stock"));
                    item.setPrecio(rs.getDouble("precio"));
                    lista.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al filtrar el inventario: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarItem(Item item) {
        String sql = "INSERT INTO inventario_taller (tipo_item, descripcion, cantidad_stock, precio) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getTipoItem());
            ps.setString(2, item.getDescripcion());
            ps.setInt(3, item.getCantidadStock());
            ps.setDouble(4, item.getPrecio());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar item: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarItem(int idItem) {
        String sql = "DELETE FROM inventario_taller WHERE id_item = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idItem);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar item: " + e.getMessage());
            return false;
        }
    }

    public Item obtenerItemPorId(int idItem) {
        Item item = null;
        String sql = "SELECT * FROM inventario_taller WHERE id_item = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idItem);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new Item();
                    item.setIdItem(rs.getInt("id_item"));
                    item.setTipoItem(rs.getString("tipo_item"));
                    item.setDescripcion(rs.getString("descripcion"));
                    item.setCantidadStock(rs.getInt("cantidad_stock"));
                    item.setPrecio(rs.getDouble("precio"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar item: " + e.getMessage());
        }
        return item;
    }

    public boolean actualizarItem(Item item) {
        String sql = "UPDATE inventario_taller SET tipo_item=?, descripcion=?, cantidad_stock=?, precio=? WHERE id_item=?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getTipoItem());
            ps.setString(2, item.getDescripcion());
            ps.setInt(3, item.getCantidadStock());
            ps.setDouble(4, item.getPrecio());
            ps.setInt(5, item.getIdItem());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar item: " + e.getMessage());
            return false;
        }
    }
}