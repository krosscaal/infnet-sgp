/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoResidente;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.service.UsuarioCondominioService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static br.edu.infnet.krossbyapi.util.GeralUtils.criarUsuario;
import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoResidente;
import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoSituacao;

@Component
public class UsuarioCondominioLoader implements ApplicationRunner {
    private final UsuarioCondominioService usuarioCondominioService;
    Logger log = Logger.getLogger(UsuarioCondominioLoader.class.getName());

    public UsuarioCondominioLoader(UsuarioCondominioService usuarioCondominioService) {
        this.usuarioCondominioService = usuarioCondominioService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("usuario-condominio.txt");
        try (BufferedReader lerArquivo = new BufferedReader(arquivo)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");

                Usuario usuario = criarUsuario(campos);

                EnumTipoResidente residente = getTipoResidente(campos[6]);
                EnumTipoSituacao situacao = getTipoSituacao(campos[8]);

                UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
                usuarioCondominio.setUsuario(usuario);
                usuarioCondominio.setTipoResidente(residente);
                usuarioCondominio.setEmail(!campos[7].equalsIgnoreCase("null") ? campos[7] : null);
                usuarioCondominio.setSituacao(situacao);

                usuarioCondominioService.incluir(usuarioCondominio);
                usuarioCondominioService.incluirMap(usuarioCondominio);

                linha = lerArquivo.readLine();
            }
        }
        log.info("lista UsuarioCondomino do Map");
        usuarioCondominioService.buscarTodosMap().forEach(usuarioCondominio -> log.info(usuarioCondominio.toString()));

    }
}
