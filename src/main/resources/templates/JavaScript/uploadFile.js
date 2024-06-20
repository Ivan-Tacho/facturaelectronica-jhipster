$(document).ready(function() {
  $('#form').submit(function(event) {
    event.preventDefault(); // Evitar que el formulario se envíe de forma predeterminada

    var formData = new FormData(); // Crear un objeto FormData para enviar el archivo

    // Obtener el archivo seleccionado
    var fileInput = $('#file')[0].files[0];
    formData.append('archivo', fileInput);

    // Crear un objeto JSON con el nombre y contenido del archivo
    var archivoJSON = {
      nombre: fileInput.name,
      contenido: fileInput
    };

    // Convertir el objeto JSON a una cadena JSON
    var archivoJSONString = JSON.stringify(archivoJSON);

    // Enviar el archivo como un JSON utilizando AJAX
    $.ajax({
      url: 'localhost:8080/uploadFile', // Especifica la URL del servidor donde quieres enviar el archivo
      type: 'POST',
      data: archivoJSONString,
      contentType: 'application/json',
      success: function(response) {
        // Manejar la respuesta del servidor
        console.log('Archivo enviado exitosamente');
        console.log(response);
      },
      error: function(xhr, status, error) {
        // Manejar errores
        console.error('Error al enviar el archivo:', error);
      }
    });
  });
});
