/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.entity.Visitante;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoAcesso;
import br.edu.infnet.krossbyapi.service.VisitanteService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static br.edu.infnet.krossbyapi.util.GeralUtils.criarUsuario;
import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoSituacao;

@Component
public class VisitanteLoader implements ApplicationRunner {
    private final VisitanteService visitanteService;
    Logger log = Logger.getLogger(VisitanteLoader.class.getName());

    public VisitanteLoader(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivoVisitante = new FileReader("visitante.txt");

        try (BufferedReader lerArquivo = new BufferedReader(arquivoVisitante)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");
                Usuario usuario = criarUsuario(campos);
                int ordinalTipoAcesso = Integer.parseInt(campos[6]);
                EnumTipoAcesso tipoAcesso = EnumTipoAcesso.getByCodigo(ordinalTipoAcesso);
                Visitante visitante = criarVistante(campos);
                visitante.setUsuarioVisitante(usuario);
                visitante.setTipoAcesso(tipoAcesso);

                visitanteService.incluir(visitante);
                visitanteService.incluirMap(visitante);

                linha = lerArquivo.readLine();
            }
        }
        log.info("lista Visitante do Map");
        visitanteService.buscarTodosMap().forEach(visitante -> log.info(visitante.toString()));
    }

    private Visitante criarVistante(String[] campos) {
        Long idMoradia = Long.valueOf(campos[8]);
        Long idUsuarioAutorizado = Long.valueOf(campos[9]);

        Moradia moradia = new Moradia();
        moradia.setId(idMoradia);

        UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
        usuarioCondominio.setId(idUsuarioAutorizado);

        Visitante visitante = new Visitante();
        visitante.setCartaoAcesso(campos[7]);
        visitante.setMoradiaDestinoVisitante(moradia);
        visitante.setUsuarioAutorizacao(usuarioCondominio);
        visitante.setObservacao(campos[10]);
        visitante.setSituacao(getTipoSituacao(campos[11]));
        return visitante;
    }
}
