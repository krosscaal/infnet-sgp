/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.enumerator;

import lombok.Getter;

@Getter
public enum EnumTipoAcesso {

    VISITANTE(0,"Visitante", "Convidado, Hospede"),
    FUNCIONARIO(1, "Funcionario", "Condominio"),
    FORNECEDOR(2, "Fornecedor", "Água, Gás, Supermercados"),
    PRESTADOR(3, "Prestador", "TV a Cabo, Telefonía/Internet, Energia Elétrica, Outros Serviços"),
    DIARISTA(4, "Diarista", "Diarista");

    private Integer codigo;
    private String descricao;
    private String opcoes;

    EnumTipoAcesso(Integer codigo, String descricao, String opcoes) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.opcoes = opcoes;
    }

    public static EnumTipoAcesso getByCodigo(Integer codigo) {
        for (EnumTipoAcesso tipoAcesso : EnumTipoAcesso.values()) {
            if (tipoAcesso.getCodigo().equals(codigo)) {
                return tipoAcesso;
            }
        }
        throw new IllegalArgumentException("Tipo de Acesso não encontrado para o codigo: " + codigo);
    }

}
