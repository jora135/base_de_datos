package base_de_datos.base_de_datos.controller;

import org.springframework.web.bind.annotation.RestController;

import base_de_datos.base_de_datos.dao.UsuarioDao;
import base_de_datos.base_de_datos.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET) //mostrar datos
    public List<Usuario> getUsuarios() {
        return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE) //notacion para indicar cual es el URL que debe de ser para devolver prueba()
    public void eliminar(@PathVariable Long id){
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST) 
    public void registrarUsuario(@RequestBody Usuario usuario) {
        //argon2 es para guardar contraseñas de forma segura tipo hash
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/login", method = RequestMethod.POST) 
    public String login(@RequestBody Usuario usuario) {
        if(usuarioDao.verificarEmailPassword(usuario)){
            return "OK";
        }
        return "Fail";
    }
}
