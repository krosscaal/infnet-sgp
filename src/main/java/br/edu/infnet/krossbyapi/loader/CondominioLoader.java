/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.loader;

import br.edu.infnet.krossbyapi.domain.entity.Endereco;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoCondominio;
import br.edu.infnet.krossbyapi.domain.record.CondominioRecord;
import br.edu.infnet.krossbyapi.service.CondominioService;
import org.jboss.logging.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class CondominioLoader implements ApplicationRunner {

    private final CondominioService condominioService;
    Logger log = Logger.getLogger(getClass().getName());

    public CondominioLoader(CondominioService condominioService) {
        this.condominioService = condominioService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("condominio.txt");

        try (BufferedReader lerArquivo = new BufferedReader(arquivo)) {
            String linha = lerArquivo.readLine();

            String[] campos = null;

            while (linha != null) {
                campos = linha.split(";");

                Endereco endereco = new Endereco();
                endereco.setLogradouro(campos[6]);
                endereco.setNumero(campos[7]);
                endereco.setComplemento(campos[8]);
                endereco.setBairro(campos[9]);
                endereco.setCidade(campos[10]);
                endereco.setCep(campos[11]);
                endereco.setEstado(campos[12]);
                endereco.setUf(campos[13]);

                int ordinalTipoCondominio = Integer.parseInt(campos[1]);
                EnumTipoCondominio tipoCondominio = EnumTipoCondominio.valueOfTipoDeCondominio(ordinalTipoCondominio);

                int ordinalAtivo = Integer.parseInt(campos[16]);
                EnumTipoSituacao ativo = EnumTipoSituacao.valueOfAtivo(ordinalAtivo);


                CondominioRecord condominioRecord = new CondominioRecord(
                        null,
                        campos[0],
                        tipoCondominio,
                        Integer.valueOf(campos[2]),
                        !campos[3].equalsIgnoreCase("null")? campos[3]: null,
                        !campos[4].equalsIgnoreCase("null")? campos[4] : null,
                        campos[5],
                        endereco,
                        campos[14],
                        campos[15],
                        ativo);

                condominioService.incluir(condominioRecord);
                condominioService.incluirMap(condominioRecord);

                linha = lerArquivo.readLine();
            }

        }
        log.info("Lista Condominio do Banco de dados");
        condominioService.listarTodos().forEach(condominioRecord -> log.info(condominioRecord.toString()));

        log.info("Lista de Condominios do Map");
        condominioService.listarTodosMap().forEach(condominio -> log.info(condominio.toString()));


    }
}
