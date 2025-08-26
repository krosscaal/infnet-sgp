/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoMoradia;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.service.MoradiaService;
import br.edu.infnet.krossbyapi.service.UsuarioCondominioService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Logger;

import static br.edu.infnet.krossbyapi.util.GeralUtils.getTipoSituacao;

@Order(5)
@Component
public class MoradiaLoader implements ApplicationRunner {
    private final MoradiaService moradiaService;
    private final UsuarioCondominioService usuarioCondominioService;
    Logger logger = Logger.getLogger(MoradiaLoader.class.getName());

    public MoradiaLoader(MoradiaService moradiaService, UsuarioCondominioService usuarioCondominioService) {
        this.moradiaService = moradiaService;
        this.usuarioCondominioService = usuarioCondominioService;
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
                Long idUsuarioPropietario = Long.valueOf(campos[3]);
                Long idUsuarioMorador = Long.valueOf(campos[4]);
                UsuarioCondominio propietario = usuarioCondominioService.buscarPorId(idUsuarioPropietario);
                UsuarioCondominio morador = usuarioCondominioService.buscarPorId(idUsuarioMorador);

                Moradia moradia = new Moradia();
                moradia.setTipoMoradia(tipoMoradia);
                moradia.setNumeroUnidade(campos[1]);
                moradia.setSituacao(tipoSituacao);
                moradia.setPropietario(propietario);
                moradia.setMorador(morador);

                moradiaService.incluir(moradia);
                linha = lerArquivo.readLine();
            }
        }
        logger.info("lista Moradias carregadas com sucesso");
        moradiaService.listarTodos().forEach(moradia -> logger.info(moradia.toString()));

    }
}
