/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoUsuarioSistema {
    OPERADOR("Operador"),
    ADMIN("Administrador/Síndico");

    private final String descricao;
}
