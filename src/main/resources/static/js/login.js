/**
 * 
 */
var cta = document.querySelector(".cta");
var check = 0;

cta.addEventListener('click', function(){
    var text = document.getElementById("login-formulario");
    var loginText = document.getElementById("login-div");
    var textoInicial = document.getElementById("texto-iniciar");
    text.classList.toggle('hidden');
    loginText.classList.toggle('expand');
    
    if (!loginText.classList.contains('expand')) {
        textoInicial.classList.remove('hidden');
        textoInicial.classList.add('texto-iniciar');
        textoInicial.classList.add('expand');
    } else {
		textoInicial.classList.remove('texto-iniciar');
        textoInicial.classList.remove('expand');
        textoInicial.classList.add('hidden');
    }
    if(check == 0)
    {
        cta.innerHTML = "<i class=\"fa fa-chevron-up\"></i>";
        check++;
    }
    else
    {
        cta.innerHTML = "<i class=\"fa fa-chevron-down\"></i>";	
        check = 0;
    }
})

$(".toggle-password").click(function() {

  $(this).toggleClass("fa-eye fa-eye-slash");
  var id = $(this).attr('id').substring(0, $(this).attr('id').lastIndexOf("-"));
  var input = document.getElementById(id);
  if (input.type === "password") {
    input.type = "text";
  } else {
    input.type = "password";
  }
});

document.getElementById("login-boton-registrarse").addEventListener("click", function() {
    var inicioSesion = document.getElementById("inicio-sesion");
    var registro = document.getElementById("registro");
    var botonLogin = document.getElementById("registrarse-boton-login");
    var botonRegistrarse = document.getElementById("registrarse-boton-registrarse");
    
	registro.classList.remove('hidden');
    
	setTimeout(function() {
        inicioSesion.classList.add('move-left');
        registro.classList.remove('move-right');
    }, 50);
    
	inicioSesion.classList.add('hidden');
	
	botonLogin.classList.add('inactive-btn');
	botonLogin.classList.remove('active-btn');
	botonRegistrarse.classList.add('active-btn');
	botonRegistrarse.classList.remove('inactive-btn');
});

document.getElementById("registrarse-boton-login").addEventListener("click", function() {
    var inicioSesion = document.getElementById("inicio-sesion");
    var registro = document.getElementById("registro");
    var botonLogin = document.getElementById("login-boton-login");
    var botonRegistrarse = document.getElementById("login-boton-registrarse");
    
    inicioSesion.classList.remove('hidden');
	
	setTimeout(function() {
   		inicioSesion.classList.remove('move-left');
    	registro.classList.add('move-right');
    }, 50);
    
     registro.classList.add('hidden');
     
     botonLogin.classList.add('active-btn');
     botonLogin.classList.remove('inactive-btn');
     botonRegistrarse.classList.add('inactive-btn');
     botonRegistrarse.classList.remove('active-btn');
     
});

document.getElementById("registrarse-boton-registrarse").addEventListener("click",async function() {
	var usuario = document.getElementById("registro-usuario").value;
	var email = document.getElementById("registro-email").value;
	var password = document.getElementById("registro-password").value;
	var password2 = document.getElementById("registro-password2").value;
	
	if (usuario == "" || email == "" || password == "" || password2 == "") {
		openModal("Por favor, rellena todos los campos");
		return;
	}
	if (password != password2) {
		openModal("Las passwords no coinciden");
		return;
	}
	if (password.length < 8) {
		openModal("La password debe tener al menos 8 caracteres");
		return;
	}
	if(password.search(/[a-z]/) < 0 || password.search(/[A-Z]/) < 0 || password.search(/[0-9]/) < 0 
		|| password.search(/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/) < 0){
		openModal("La password debe tener al menos una letra mayúscula, una minúscula, un número y un caracter especial");
		return;
	}
	var pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!pattern.test(email)) {
		openModal("El email no es válido");
		return;
	}
	if (!(await existeUsuario(usuario))) {
		openModal("El usuario ya existe");
		return;
	}
	
	if (!(await existeEmail(email))) {
		openModal("El email no está disponible");
		return;
	}
	await registrarUsuario(usuario, email, password);
	openModal("Usuario registrado correctamente");
});

function registrarUsuario(usuario, email, password) {
    var datos = {
        usuario: usuario,
        email: email,
        password: password
    };

    // Realizar la solicitud POST a la API de manera síncrona
    return $.ajax({
        url: 'http://localhost:8080/api/usuarios/registro',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(datos),
        error: function(xhr) {
            console.error('Error al llamar a la API:', xhr.status, xhr.statusText);
        }
    });
}

document.getElementById("login-boton-login").addEventListener("click",async function() {
	var usuario = document.getElementById("input-user-login").value;
	var password = document.getElementById("input-pass-login").value;
	
	if (usuario == "" || password == "") {
		openModal("Por favor, rellena todos los campos");
		return;
	}

	existe = await existeUsuario(usuario);
	if (!existe) {
		openModal("El usuario no existe");
		return;
	}

	passwordCorrecto = await comprobarpassword(usuario, password);
	if (!passwordCorrecto) {
		openModal("Contraseña incorrecta. Inténtalo de nuevo");
		return;
	}
	
	var credentials = {
		usuario: usuario,
		password: password
	};


	window.location.href = "http://localhost:8080/home";
	
	$.ajax({
        type: 'POST',
        url: 'http://localhost:8080/login',
        contentType: 'application/json',
        data: JSON.stringify(credentials),
        success: function(data) {
			var a = "";
            /*aqui pondria algo en el navegador para saber que el usuario esta logeado*/
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Error:', textStatus, errorThrown);
            openModal("Ha ocurrido un error. Inténtalo de nuevo");
        }
    });
    
});
	

function openModal(mensaje) {
    var modal = document.getElementById("ventana-error");
    var modalMessage = document.getElementById("mensaje-error");
    modalMessage.textContent = mensaje;
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("ventana-error");
    modal.style.display = "none";
}

function existeUsuario(usuario) {
    return $.ajax({
        url: 'http://localhost:8080/api/usuarios/existe-usuario/' + usuario,
        type: 'GET'
    });
}

function comprobarpassword(usuario, password) {
	var datos = {
        usuario: usuario,
        password: password
    };
	
	return $.ajax({
		url: 'http://localhost:8080/api/usuarios/login',
		type: 'POST',
		contentType: 'application/json',
        data: JSON.stringify(datos)
	});
}

function existeEmail(email) {
    return $.ajax({
        url: 'http://localhost:8080/api/usuarios/existe-email/' + email,
        type: 'GET'
    });
}
