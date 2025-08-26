/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoAcesso;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static br.edu.infnet.krossbyapi.util.MensagemCenter.NAO_NULL;

@Getter
@Setter
@Entity
@Table(name = "tb_visitante")
public class Visitante extends EntidadeBase {

    @NotNull(message = NAO_NULL)
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuarioVisitante;

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.ORDINAL)
    private EnumTipoAcesso tipoAcesso;

    @Size(max = 15, message = "O tamanho deve ser menor ou igual a 15 caracteres.")
    @Column(length = 15)
    private String cartaoAcesso;

    @NotNull(message = NAO_NULL)
    @ManyToOne
    @JoinColumn(name = "moradia_id_destino", referencedColumnName = "id")
    private Moradia moradiaDestinoVisitante;

    @NotNull(message = NAO_NULL)
    @ManyToOne
    @JoinColumn(name = "autorizado_por_usuario_id", referencedColumnName = "id")
    private UsuarioCondominio usuarioAutorizacao;

    @Size(max = 150, message = "O tamanho deve ser menor ou igual a 150 caracteres.")
    @Column(length = 150)
    private String observacao;

    @Override
    public String toString() {
        return String.format("Visitante id:%d %s %s %s %s %s %s ",
                getId(), getUsuarioVisitante().toString(), tipoAcesso.getDescricao(), cartaoAcesso, moradiaDestinoVisitante.getNumeroUnidade(), usuarioAutorizacao.getId(), observacao);
    }
}
