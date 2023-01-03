package base_de_datos.base_de_datos.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import base_de_datos.base_de_datos.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository// anotacion que tendra la funcionalidad de entrar al repositorio de la base de datos
@Transactional//forma en que tratara las consultas de sql / va a ser en fracmentos de transaccion
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();//obtener de la base de datos
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);//eliminar de la base de datos
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);//guardar en la base de datos
    }

    @Override
    public boolean  verificarEmailPassword(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> lista = entityManager.createQuery(query)
                                .setParameter("email",usuario.getEmail())
                                .getResultList();

        if(lista.isEmpty()){//es para controlar los campos vacios de las contraseñas
            return false;
        }

        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(passwordHashed, usuario.getPassword());//retorna booleano de comparativa de contraseñás

    }
    
}
