<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Inicio</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"rel="stylesheet"integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"crossorigin="anonymous">
<link rel="stylesheet"href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400..700&display=swap"rel="stylesheet">
<style>
body {
	background-color: #f3e5f5;
	color: #343a40;
	padding-top: 23px;
	font-family: "Dancing Script", cursive;
	font-weight: 400;
	font-style: normal;
}

.container {
	background-color: #fff;
	border-radius: 20px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	padding: 40px;
}

h1 {
	color: black;
	font-size: 2.50em;
	font-weight: bold;
}

h2 {
	background-color: #007bff;
	border-radius: 9px;
	margin-left: auto;
	margin-right: auto;
	padding: 10px 20px;
	color: #fff;
	max-width: fit-content;
	margin-bottom: 20px;
	font-size: 2em;
	font-weight: bold;
}

footer {
	background-color: #f3e5f5;
	color: black;
	padding: 10px 10px;
	border-radius: 5px;
	margin-left: auto;
	margin-right: auto;
	max-width: fit-content;
	margin-top: 20px;
	font-size: 1.50em;
}

.btn {
	font-size: 1.50em;
}
</style>
</head>
<body>
<header>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand fs-2 text-dark">Alke Wallet</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active fs-5 text-dark" aria-current="page" href='/home'>Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link fs-5 text-dark" href='/depositar'>Depositar</a>
        </li>
        <li class="nav-item">
          <a class="nav-link fs-5 text-dark" href='/retirar'>Retirar</a>
        </li>
        <li class="nav-item">
          <a class="nav-link fs-5 text-dark" href='/transferir'>Transferir a un tercero</a>
        </li>
        <li class="nav-item">
          <a class="nav-link fs-5 text-dark" href='/historial'>Ver Historial de Transacciones</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
</header>
	<div class="container text-center">
		<c:if test="${not empty alertaTitulo}">
			<script>
				const alertaTitulo = '${alertaTitulo}';
				const alertaMensaje = '${alertaMensaje}';
				const alertaTipo = '${alertaTipo}'.toLowerCase();

				Swal.fire({
					title : alertaTitulo,
					text : alertaMensaje,
					icon : alertaTipo,
					confirmButtonText : 'OK'
				});
			</script>
		</c:if>

		<img
			src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4riIGDL4H2FYaJIyqPSe_is4bTo0QeVNp4Q&s"
			alt="Imagen con bordes redondeados"
			style="border-radius: 10px; margin-right: 20px;">
		<h1>Alke Wallet</h1>
		<h1 class="text-center">
			¡Bienvenido,
			<c:out value="${usuario.nombre}" />
			!
		</h1>
		<h2 class="text-center">
			Saldo Actual:
			<c:out value="${usuario.saldo}" />
		</h2>

		<div class="text-center">
			<h1 class="text-center">Menú</h1>
		</div>

		<form action="menu-usuario" method="post">
			<div class="d-grid gap-2 col-6 mx-auto">
				<a href="<c:url value='/depositar'/>" class="btn btn-primary">Depositar</a>
				<a href="<c:url value='/retirar'/>" class="btn btn-primary">Retirar</a>
				<a href="<c:url value='/transferir'/>" class="btn btn-primary">Transferir a un tercero</a>
			</div>
		</form>

		<div class="text-center mt-4">
			<a href="<c:url value='/historial'/>" class="btn btn-primary">Ver Historial de Transacciones</a>
		</div>

		<br>

		<div class="text-center">
			<a href="<c:url value='/logout'/>" class="btn btn-secondary">Cerrar
				Sesión</a>
		</div>
	</div>

	<footer>
		<p>&copy; 2024 - Alke Wallet.</p>
	</footer>

</body>
</html>
