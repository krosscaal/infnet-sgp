/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoAcesso;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_visitante")
public class Visitante extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuarioVisitante;

    @Enumerated(EnumType.ORDINAL)
    private EnumTipoAcesso tipoAcesso;

    @Column(length = 15)
    private String cartaoAcesso;

    @ManyToOne
    @JoinColumn(name = "moradia_id_destino", referencedColumnName = "id")
    private Moradia moradiaDestinoVisitante;

    @ManyToOne
    @JoinColumn(name = "autorizado_por_usuario_id", referencedColumnName = "id")
    private UsuarioCondominio usuarioAutorizacao;

    @Column(length = 150)
    private String observacao;

    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao situacao;

    @Override
    public String toString() {
        return String.format("Visitante id:%d %s %s %s %s %s %s %s",
                getId(), getUsuarioVisitante().toString(), tipoAcesso.getDescricao(), cartaoAcesso, moradiaDestinoVisitante.getNumeroUnidade(), usuarioAutorizacao.getId(), observacao, situacao.getDescricao());
    }
}
