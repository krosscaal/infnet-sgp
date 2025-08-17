/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Correspondencia;
import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.service.CorrespondenciaService;
import br.edu.infnet.krossbyapi.service.MoradiaService;
import br.edu.infnet.krossbyapi.service.UsuarioSistemaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

@Order(7)
@Component
public class CorrespondenciaLoader implements ApplicationRunner {
    private final CorrespondenciaService correspondenciaService;
    private final UsuarioSistemaService usuarioSistemaService;
    private final MoradiaService moradiaService;
    Logger log = Logger.getLogger(CorrespondenciaLoader.class.getName());

    public CorrespondenciaLoader(CorrespondenciaService correspondenciaService, UsuarioSistemaService usuarioSistemaService, MoradiaService moradiaService) {
        this.correspondenciaService = correspondenciaService;
        this.usuarioSistemaService = usuarioSistemaService;
        this.moradiaService = moradiaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("correspondencia.txt");

        try (BufferedReader lerArquivo = new BufferedReader(arquivo)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");
                Long idMoradia = Long.valueOf(campos[0]);
                Moradia moradia = moradiaService.buscarPorIdMap(idMoradia);
                Correspondencia correspondencia = new Correspondencia();
                correspondencia.setNomeDestinatario(campos[1]);
                correspondencia.setTelefoneDestinatario(campos[2]);
                correspondencia.setUsuarioRecepcao(usuarioSistemaService.buscarPorIdMap(2L));
                correspondencia.setMoradiaEntrega(moradia);

                correspondenciaService.incluir(correspondencia);
                correspondenciaService.incluirMap(correspondencia);
                linha = lerArquivo.readLine();
            }
        }
        log.info("lista Correspondencia do Map");
        correspondenciaService.buscarTodosMap().forEach(correspondencia -> log.info(correspondencia.toString()));
    }
}
