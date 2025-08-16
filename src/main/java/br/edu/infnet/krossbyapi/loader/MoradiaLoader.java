/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoMoradia;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.service.MoradiaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoSituacao;

@Order(2)
@Component
public class MoradiaLoader implements ApplicationRunner {
    private final MoradiaService moradiaService;
    Logger logger = Logger.getLogger(MoradiaLoader.class.getName());

    public MoradiaLoader(MoradiaService moradiaService) {
        this.moradiaService = moradiaService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileReader arquivo = new FileReader("moradia.txt");
        try (BufferedReader lerArquivo = new BufferedReader(arquivo)) {
            String linha = lerArquivo.readLine();
            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");

                int ordinalMoradia = Integer.parseInt(campos[0]);
                EnumTipoMoradia tipoMoradia = EnumTipoMoradia.valueOfMoradia(ordinalMoradia);
                EnumTipoSituacao tipoSituacao = getTipoSituacao(campos[2]);

                Moradia moradia = new Moradia();
                moradia.setTipoMoradia(tipoMoradia);
                moradia.setNumeroUnidade(campos[1]);
                moradia.setSituacao(tipoSituacao);

                moradiaService.incluir(moradia);
                moradiaService.incluirMap(moradia);
                linha = lerArquivo.readLine();
            }
        }
        logger.info("lista Moradias do Map!");
        moradiaService.buscarTodosMap().forEach(moradia -> logger.info(moradia.toString()));

    }
}
