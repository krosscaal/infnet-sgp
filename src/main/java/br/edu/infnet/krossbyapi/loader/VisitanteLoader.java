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
import br.edu.infnet.krossbyapi.service.MoradiaService;
import br.edu.infnet.krossbyapi.service.UsuarioCondominioService;
import br.edu.infnet.krossbyapi.service.UsuarioService;
import br.edu.infnet.krossbyapi.service.VisitanteService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;



@Order(6)
@Component
public class VisitanteLoader implements ApplicationRunner {
    private final VisitanteService visitanteService;
    private final MoradiaService moradiaService;
    private final UsuarioCondominioService usuarioCondominioService;
    private final UsuarioService usuarioService;
    Logger log = Logger.getLogger(VisitanteLoader.class.getName());

    public VisitanteLoader(VisitanteService visitanteService, MoradiaService moradiaService, UsuarioCondominioService usuarioCondominioService, UsuarioService usuarioService) {
        this.visitanteService = visitanteService;
        this.moradiaService = moradiaService;
        this.usuarioCondominioService = usuarioCondominioService;
        this.usuarioService = usuarioService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivoVisitante = new FileReader("visitante.txt");

        try (BufferedReader lerArquivo = new BufferedReader(arquivoVisitante)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");

                Long idUsuario = Long.valueOf(campos[0]);
                Usuario usuario = usuarioService.buscarPorId(idUsuario);

                int ordinalTipoAcesso = Integer.parseInt(campos[1]);
                EnumTipoAcesso tipoAcesso = EnumTipoAcesso.getByCodigo(ordinalTipoAcesso);
                Visitante visitante = criarVistante(campos);
                visitante.setUsuarioVisitante(usuario);
                visitante.setTipoAcesso(tipoAcesso);

                visitanteService.incluir(visitante);

                linha = lerArquivo.readLine();
            }
        }
        log.info("lista Visitante carregada com sucesso");
        visitanteService.listarTodos().forEach(visitante -> log.info(visitante.toString()));
    }

    private Visitante criarVistante(String[] campos) {
        Long idMoradia = Long.valueOf(campos[3]);
        Long idUsuarioAutorizado = Long.valueOf(campos[4]);

        Moradia moradia = moradiaService.buscarPorId(idMoradia);
        UsuarioCondominio usuarioCondominio = usuarioCondominioService.buscarPorId(idUsuarioAutorizado);


        Visitante visitante = new Visitante();
        visitante.setCartaoAcesso(campos[2]);
        visitante.setMoradiaDestinoVisitante(moradia);
        visitante.setUsuarioAutorizacao(usuarioCondominio);
        visitante.setObservacao(campos[5]);
        return visitante;
    }
}
