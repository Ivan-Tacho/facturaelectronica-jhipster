var cta = document.querySelector(".cta");
var check = 0;
let compañiaSeleccionada = '';

// Desplazamiento del header
cta.addEventListener('click', function(){
    var divEmpresas = document.getElementById("empresas");
    var divHeader = document.getElementById("header-div");
    var textoInicial = document.getElementById("texto-subir-factura");
    divEmpresas.classList.toggle('hidden');
    divHeader.classList.toggle('expand');
    
    if (!divHeader.classList.contains('expand')) {
        textoInicial.classList.remove('hidden');
        textoInicial.classList.add('texto-subir-factura');
        textoInicial.classList.add('expand');
    } else {
		textoInicial.classList.remove('texto-subir-factura');
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

// Evento al clickar en la imagen de la compañía
const elementosLogoCompañias = document.querySelectorAll('.logo-compañias');

elementosLogoCompañias.forEach(function(elemento) {
    elemento.addEventListener('click', function() {
		compañiaSeleccionada = elemento.parentNode.parentNode.id;
		compañiaSeleccionada = compañiaSeleccionada.substring(compañiaSeleccionada.indexOf('-') + 1);
		console.log(compañiaSeleccionada);
        document.getElementById('file-input').click();
    });
});

// Evento al seleccionar una factura
document.getElementById('file-input').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('display-image').src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
});