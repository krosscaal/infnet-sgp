/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoAcesso;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_acesso_condominio")
public class AcessoCondominio extends EntidadeBase{

    @Embedded
    private Usuario usuario;

    @Enumerated(EnumType.ORDINAL)
    private EnumTipoAcesso tipoAcesso;

    @Column(length = 15)
    private String cartaoAcesso;

    @OneToOne
    @JoinColumn(name = "moradia_id", referencedColumnName = "id")
    private Moradia moradia;

    @OneToOne
    @JoinColumn(name = "autorizado_por_id", referencedColumnName = "id")
    private UsuarioCondominio autorizado;

    @Column(length = 150)
    private String observacao;


}
