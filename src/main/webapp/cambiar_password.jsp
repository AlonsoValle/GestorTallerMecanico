<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.taller.modelo.Usuario" %>
<%
    Usuario tempUser = (Usuario) session.getAttribute("tempUsuario");
    if (tempUser == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Cambio de Contraseña - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
        .login-container { background: white; padding: 35px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); text-align: center; width: 350px; border-top: 5px solid #FF5722; }
        .login-container h2 { color: #333; margin-bottom: 10px; }
        .login-container p { color: #666; font-size: 14px; margin-bottom: 15px; line-height: 1.4; }
        input[type="password"] { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .rules { font-size: 12px; color: #555; background: #f9f9f9; padding: 10px; border-radius: 4px; border-left: 3px solid #FF5722; margin-bottom: 15px; text-align: left; }
        button { width: 100%; padding: 12px; background-color: #FF5722; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; margin-top: 10px; font-size: 16px; }
        button:hover { background-color: #E64A19; }
        .error { color: #d32f2f; background: #ffebee; padding: 10px; border-radius: 4px; margin-top: 15px; font-size: 13px; font-weight: bold; }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Atención, <%= tempUser.getUsername() %></h2>
    <p>Por políticas de seguridad, debes cambiar tu contraseña temporal antes de ingresar al sistema.</p>
    <form action="CambiarPasswordServlet" method="POST">
        <input type="password" name="nuevaPassword" placeholder="Escribe tu nueva contraseña" required>
        <div class="rules">
            Requisitos: Mínimo 5 caracteres, una mayúscula, un número, un carácter especial y no coincidir con tu usuario.
        </div>
        <button type="submit">Actualizar e Ingresar</button>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
    </form>
</div>
</body>
</html>