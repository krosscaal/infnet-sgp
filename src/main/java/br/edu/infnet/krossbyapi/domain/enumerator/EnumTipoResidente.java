/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoResidente {
    PROPIETARIO("Propietario"),
    MORADOR("Morador");

    private final String usuarioResidente;
    public static EnumTipoResidente valueOfResidente(final int ordinal) {
        return EnumTipoResidente.values()[ordinal];
    }
}
