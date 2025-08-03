/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.record;

import br.edu.infnet.krossbyapi.domain.entity.Endereco;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoCondominio;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CondominioRecord(
        Long id,
        @NotBlank
        String nomeCondominio,
        @Enumerated(EnumType.ORDINAL)
        EnumTipoCondominio tipoCondominio,
        @NotNull
        int totalUnidades,
        @Size(max = 15)
        String cnpj,
        @Size(max = 11)
        String telefoneContato1,
        @Size(max = 11)
        String telefoneContato2,
        Endereco endereco,
        @Size(max = 100)
        String nomeSindico,
        @Size(max = 11)
        String telefoneSindico) {

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder("\n");
                sb.append("Id: ").append(id).append("\n");
                sb.append("Nome Condominio: ").append(nomeCondominio).append("\n");
                sb.append("Tipo Condominio: ").append(tipoCondominio.getTipoDeCondominio()).append("\n");
                sb.append("Total de Unidades: ").append(totalUnidades).append("\n");
                sb.append("Cnpj: ").append(cnpj).append("\n");
                sb.append("Telefone de Contato 1: ").append(telefoneContato1).append("\n");
                sb.append("Telefone de Contato 2: ").append(telefoneContato2).append("\n");
                sb.append("Endereço: ").append(endereco);
                sb.append("Nome do Sindico: ").append(nomeSindico).append("\n");
                sb.append("Telefone do Sindico: ").append(telefoneSindico).append("\n");
                return sb.toString();
        }
}

