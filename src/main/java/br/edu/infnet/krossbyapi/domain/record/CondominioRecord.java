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
}
