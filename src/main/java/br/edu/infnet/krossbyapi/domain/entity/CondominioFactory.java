/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.domain.record.CondominioRecord;
import br.edu.infnet.krossbyapi.util.GeralUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CondominioFactory {

    private static final String SOMENTE_NUMEROS = "aceita somente números!!";
    private static final String SOMENTE_LETRAS = "aceita somente letras!!";

    public static Condominio criarCondominio(CondominioRecord condominioRecord) {
        validarCondominio(condominioRecord);
        Endereco endereco = toUpperCaseEndereco(condominioRecord.endereco());

        Condominio condominioEntity = new Condominio();
        condominioEntity.setNomeCondominio(condominioRecord.nomeCondominio().toUpperCase(Locale.ROOT));
        condominioEntity.setTipoCondominio(condominioRecord.tipoCondominio());
        condominioEntity.setTotalUnidades(condominioRecord.totalUnidades());
        condominioEntity.setCnpj(condominioRecord.cnpj());
        condominioEntity.setTelefoneContato1(condominioRecord.telefoneContato1());
        condominioEntity.setTelefoneContato2(condominioRecord.telefoneContato2());
        condominioEntity.setEndereco(endereco);
        condominioEntity.setNomeSindico(condominioRecord.nomeSindico().toUpperCase(Locale.ROOT));
        condominioEntity.setTelefoneSindico(condominioRecord.telefoneSindico());
        condominioEntity.setSituacao(EnumTipoSituacao.ATIVO);

        return condominioEntity;
    }

    private static Endereco toUpperCaseEndereco(Endereco endereco) {
        endereco.setLogradouro(endereco.getLogradouro() != null ? endereco.getLogradouro().toUpperCase(Locale.ROOT) : null);
        endereco.setComplemento(endereco.getComplemento() != null? endereco.getComplemento().toUpperCase(Locale.ROOT) : null);
        endereco.setBairro(endereco.getBairro() != null ? endereco.getBairro().toUpperCase(Locale.ROOT) : null);
        endereco.setCidade(endereco.getCidade() != null ? endereco.getCidade().toUpperCase(Locale.ROOT) : null);
        endereco.setEstado(endereco.getEstado() != null ? endereco.getEstado().toUpperCase(Locale.ROOT) : null);
        endereco.setUf(endereco.getUf() != null ? endereco.getUf().toUpperCase(Locale.ROOT) : null);
        return endereco;
    }

    private static void validarCondominio(CondominioRecord condominioRecord) {
        if (condominioRecord == null) {
            throw new BusinessException("CondominioRecord não pode ser nulo");
        }
        if (condominioRecord.nomeCondominio() == null || condominioRecord.nomeCondominio().trim().isEmpty()) {
            throw new BusinessException("Nome do condomínio é obrigatório");
        }
        if (condominioRecord.tipoCondominio() == null) {
            throw new BusinessException("Tipo do condomínio é obrigatório");
        }
        if (condominioRecord.totalUnidades() <= 0) {
            throw new BusinessException("Total de unidades deve ser maior que zero");
        }
        if ((condominioRecord.telefoneContato1() != null && !condominioRecord.telefoneContato1().trim().isEmpty()) && !GeralUtils.contemNumeros(condominioRecord.telefoneContato1())) {
            throw new BusinessException(String.format("Telefone Contato 1 %s", SOMENTE_NUMEROS));
        }
        if ((condominioRecord.telefoneContato2() != null && !condominioRecord.telefoneContato2().trim().isEmpty()) && !GeralUtils.contemNumeros(condominioRecord.telefoneContato2())) {
            throw new BusinessException(String.format("Telefone Contato 2 %s", SOMENTE_NUMEROS));
        }
        if ((condominioRecord.telefoneSindico() != null && !condominioRecord.telefoneSindico().trim().isEmpty()) && !GeralUtils.contemNumeros(condominioRecord.telefoneSindico())) {
            throw new BusinessException(String.format("Telefone Sindico %s", SOMENTE_NUMEROS));
        }
        if ((condominioRecord.cnpj() != null && !condominioRecord.cnpj().trim().isEmpty()) && !GeralUtils.contemNumeros(condominioRecord.cnpj())) {
            throw new BusinessException(String.format("Cnpj %s", SOMENTE_NUMEROS));
        }

        verificarEndereco(condominioRecord.endereco());
    }

    private static void verificarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new BusinessException("Endereço não pode ser nulo");
        }
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) {
            throw new BusinessException("Logradouro do Condomínio é obrigatorio!!!");
        }
        if (!GeralUtils.contemNumeros(endereco.getNumero())) {
            throw new BusinessException(String.format("Número do Condomínio é obrigatorio, %s", SOMENTE_NUMEROS));
        }
        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new BusinessException("Cidade do Condomínio é obrigatorio!!!");
        }
        if (!GeralUtils.contemNumeros(endereco.getCep())) {
            throw new BusinessException(String.format("CEP do Condomínio é obrigatorio, %s", SOMENTE_NUMEROS));
        }
        if (!GeralUtils.contemLetras(endereco.getEstado())) {
            throw new BusinessException(String.format("Estado do Condomínio é obrigatorio, %s", SOMENTE_LETRAS));
        }
        if (!GeralUtils.contemLetras(endereco.getUf())) {
            throw new BusinessException(String.format("UF do Condomínio é obrigatorio, %s",  SOMENTE_LETRAS));
        }
    }
}
