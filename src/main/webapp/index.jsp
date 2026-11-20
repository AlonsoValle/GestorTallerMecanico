<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.taller.dao.UsuarioDAO" %>
<%
    UsuarioDAO daoCheck = new UsuarioDAO();
    if (!daoCheck.existenUsuarios()) {
        response.sendRedirect("setup_admin.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
        .login-container { background: white; padding: 35px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); text-align: center; width: 320px; border-top: 5px solid #2196F3; }
        .login-container h2 { color: #333; margin-bottom: 25px; }
        input[type="text"], input[type="password"] { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; margin-top: 15px; font-size: 16px; }
        button:hover { background-color: #1976D2; }
        .error { color: red; margin-top: 15px; font-size: 14px; font-weight: bold; }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Gestor de Taller Mecánico</h2>
    <form action="LoginServlet" method="POST">
        <input type="text" name="username" placeholder="Usuario" required>
        <input type="password" name="password" placeholder="Contraseña" required>
        <button type="submit">Iniciar Sesión</button>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
    </form>
</div>
</body>
</html>