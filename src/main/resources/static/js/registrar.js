// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
    });

async function registrarUsuario(){
  
    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value;//se extrae el nombre de la caja del html
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.telefono = document.getElementById('txtTelefono').value;
    datos.password = document.getElementById('txtPassword').value;

    let repetirPassword = document.getElementById('txt2Password').value;

    if(repetirPassword!=datos.password){
        alert('La contraseña no coinside')
        return;
    }

    const request = await fetch('api/usuarios', {
        method: 'POST',
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    alert("La cuenta fue creada con exito");
    window.location.href = 'login.html'
}