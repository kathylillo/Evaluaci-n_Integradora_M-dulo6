<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400..700&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <title>Login</title>
    <style >
    body {
    	font-family: "Dancing Script", cursive;
    	font-weight: 400;
    	font-style: normal;
    }
    h1 {
            color: black;
            font-size: 2.50em; 
            font-weight: bold; 
        }
        h2 {
            font-size: 2em; 
            font-weight: bold; 
        }
        
        .btn {
            font-size: 1.50em; 
        }
        label {
            font-weight: bold;
             font-size: 2em; 
        }
    </style>
</head>
<body>

    <section class="vh-100" style="background-color: #f3e5f5;">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card shadow-2-strong" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">
						 <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4riIGDL4H2FYaJIyqPSe_is4bTo0QeVNp4Q&s" alt="Alke Wallet Logo" style="border-radius: 10px; margin-bottom: 5px;">
                           <h1>Alke Wallet</h1>
                            <h2 class="mb-5">Iniciar sesión</h2>
                          
        						<p style="color: red;">${errorMessage}</p>
        						<c:if test="${param.error != null}">
    						<p style="color: red;">Nombre de usuario o contraseña incorrectos</p>

    
                                <div class="alert alert-danger" role="alert">
                                    ¡Error al iniciar sesión! Por favor, inténtalo de nuevo.
                                </div>
							</c:if>
                            <form action="<c:url value='/login' />" method="post">
                                <div data-mdb-input-init class="form-outline mb-4">
                                    <input type="text" name="username" id="typeEmailX-2" class="form-control form-control-lg" />
                                    <label class="form-label" for="typeEmailX-2"style="font-weight: bold;">Correo</label>
                                </div>

                                <div data-mdb-input-init class="form-outline mb-4">
                                    <input type="password" name="password" id="typePasswordX-2" class="form-control form-control-lg" />
                                    <label class="form-label" for="typePasswordX-2"style="font-weight: bold;">Clave</label>
                                </div>

                                <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-lg btn-block" type="submit">Acceder</button>
							<br>
							<a href="/registro"class="btn btn-link">Crea una cuenta</a>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>

