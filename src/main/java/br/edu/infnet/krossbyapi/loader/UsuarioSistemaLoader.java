/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioSistema;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoUsuarioSistema;
import br.edu.infnet.krossbyapi.service.UsuarioService;
import br.edu.infnet.krossbyapi.service.UsuarioSistemaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoSituacao;
import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoUsuariosistema;

@Order(4)
@Component
public class UsuarioSistemaLoader implements ApplicationRunner {

    private final UsuarioSistemaService usuarioSistemaService;
    private final UsuarioService usuarioService;
    Logger log = Logger.getLogger(getClass().getName());

    public UsuarioSistemaLoader(UsuarioSistemaService usuarioSistemaService, UsuarioService usuarioService) {
        this.usuarioSistemaService = usuarioSistemaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("usuario-sistema.txt");
        try (BufferedReader lerArquivo = new BufferedReader(arquivo)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");

                Long idUsuario = Long.valueOf(campos[0]);
                Usuario usuario = usuarioService.buscarPorId(idUsuario);

                EnumTipoUsuarioSistema usuariosistema = getTipoUsuariosistema(campos[1]);
                EnumTipoSituacao situacao = getTipoSituacao(campos[5]);

                UsuarioSistema usuarioSistema = new UsuarioSistema();
                usuarioSistema.setUsuario(usuario);
                usuarioSistema.setTipoUsuarioSistema(usuariosistema);
                usuarioSistema.setEmail(!campos[2].equalsIgnoreCase("null") ? campos[2] : null);
                usuarioSistema.setSenha(!campos[3].equalsIgnoreCase("null") ? campos[3] : null);
                usuarioSistema.setPassword(!campos[4].equalsIgnoreCase("null") ? campos[4] : null);
                usuarioSistema.setSituacao(situacao);

                usuarioSistemaService.incluir(usuarioSistema);
                linha = lerArquivo.readLine();
            }
        }
        log.info("lista UsuarioSistema carregada com sucesso");
        usuarioSistemaService.listarTodos().forEach(usuarioSistema -> log.info(usuarioSistema.toString()));
    }

}
