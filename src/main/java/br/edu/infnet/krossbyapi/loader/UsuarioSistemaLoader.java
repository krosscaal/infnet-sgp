/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioSistema;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoUsuarioSistema;
import br.edu.infnet.krossbyapi.service.UsuarioSistemaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static br.edu.infnet.krossbyapi.util.GeralUtils.criarUsuario;
import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoSituacao;
import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoUsuariosistema;

@Component
public class UsuarioSistemaLoader implements CommandLineRunner {

    private final UsuarioSistemaService usuarioSistemaService;
    Logger log = Logger.getLogger(getClass().getName());

    public UsuarioSistemaLoader(UsuarioSistemaService usuarioSistemaService) {
        this.usuarioSistemaService = usuarioSistemaService;
    }

    @Override
    public void run(String... args) throws Exception {
        FileReader arquivo = new FileReader("usuario-sistema.txt");
        try (BufferedReader lerArquivo = new BufferedReader(arquivo)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");

                Usuario usuario = criarUsuario(campos);
                EnumTipoUsuarioSistema usuariosistema = getTipoUsuariosistema(campos[6]);
                EnumTipoSituacao situacao = getTipoSituacao(campos[10]);

                UsuarioSistema usuarioSistema = new UsuarioSistema();
                usuarioSistema.setUsuario(usuario);
                usuarioSistema.setTipoUsuarioSistema(usuariosistema);
                usuarioSistema.setEmail(!campos[7].equalsIgnoreCase("null") ? campos[7] : null);
                usuarioSistema.setSenha(!campos[8].equalsIgnoreCase("null") ? campos[8] : null);
                usuarioSistema.setPassword(!campos[9].equalsIgnoreCase("null") ? campos[9] : null);
                usuarioSistema.setSituacao(situacao);

                usuarioSistemaService.incluir(usuarioSistema);
                usuarioSistemaService.incluirMap(usuarioSistema);
                linha = lerArquivo.readLine();
            }
        }
        log.info("lista UsuarioSistema do Map");
        usuarioSistemaService.buscarTodosMap().forEach(usuarioSistema -> log.info(usuarioSistema.toString()));
    }

}
