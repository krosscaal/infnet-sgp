/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {
    public static final AtomicLong usuarioId = new AtomicLong(100);

    public void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new UsuarioException("Nome do Usuário é obrigatorio!");
        }
        if (usuario.getSobreNome() == null || usuario.getSobreNome().trim().isEmpty()) {
            throw new UsuarioException("SobreNome do Usuário é obrigatorio!");
        }
    }
}
