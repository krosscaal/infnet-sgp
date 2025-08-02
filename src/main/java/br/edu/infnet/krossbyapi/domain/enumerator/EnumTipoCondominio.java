/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoCondominio {
    HORIZONTAL("Horizontal"),
    VERTICAL("Vertical");

    private final String tipoDeCondominio;
}
