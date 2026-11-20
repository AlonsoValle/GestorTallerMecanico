<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.taller.dao.UsuarioDAO" %>
<%
    UsuarioDAO daoCheck = new UsuarioDAO();
    if (daoCheck.existenUsuarios()) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Configuración Inicial - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
        .setup-container { background: white; padding: 35px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 360px; border-top: 5px solid #4CAF50; }
        h2 { color: #333; text-align: center; margin-bottom: 10px; }
        p { color: #666; font-size: 13px; text-align: center; margin-bottom: 20px; }
        label { font-weight: bold; color: #555; display: block; margin-top: 10px; }
        input { width: 100%; padding: 10px; margin: 8px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .rules { font-size: 12px; color: #555; background: #f9f9f9; padding: 10px; border-radius: 4px; border-left: 3px solid #4CAF50; margin-bottom: 15px; }
        button { width: 100%; padding: 12px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; font-size: 15px; }
        button:hover { background-color: #45a049; }
        .error { color: #d32f2f; background: #ffebee; padding: 10px; border-radius: 4px; margin-top: 15px; font-size: 13px; font-weight: bold; text-align: center; }
    </style>
</head>
<body>
<div class="setup-container">
    <h2>Configuración Inicial</h2>
    <p>No existen cuentas registradas. Crea el primer perfil de <strong>Administrador</strong> para comenzar.</p>
    <form action="SetupAdminServlet" method="POST">
        <label for="username">Usuario Administrador:</label>
        <input type="text" id="username" name="username" value="<%= request.getAttribute("usernamePrevio") != null ? request.getAttribute("usernamePrevio") : "" %>" placeholder="Ej. Alonso" required>
        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" placeholder="Contraseña segura" required>
        <div class="rules">
            Requisitos: Mínimo 5 caracteres, al menos una mayúscula, un número, un carácter especial y no coincidir con el usuario.
        </div>
        <button type="submit">Crear Administrador</button>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
    </form>
</div>
</body>
</html>