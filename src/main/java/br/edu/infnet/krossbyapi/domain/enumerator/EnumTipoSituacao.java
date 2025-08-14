/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoSituacao {
    INATIVO("Inativo"),
    ATIVO("Ativo");


    private final String descricao;
    public static EnumTipoSituacao valueOfAtivo(final int ordinal) {
        return values()[ordinal];
    }
}
