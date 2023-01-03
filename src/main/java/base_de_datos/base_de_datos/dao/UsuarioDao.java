package base_de_datos.base_de_datos.dao;

import java.util.List;

import base_de_datos.base_de_datos.models.Usuario;

public interface UsuarioDao {
    List<Usuario> getUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    boolean verificarEmailPassword(Usuario usuario);
}
