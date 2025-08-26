/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoMoradia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static br.edu.infnet.krossbyapi.util.MensagemCenter.DEVE_CONTER_SOMENTE_NUMEROS;
import static br.edu.infnet.krossbyapi.util.MensagemCenter.NAO_NULL;

@Getter
@Setter
@Entity
@Table(name = "tb_moradia")
public class Moradia extends EntidadeBase {

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.STRING)
    private EnumTipoMoradia tipoMoradia;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private UsuarioCondominio propietario;


    @OneToOne
    @JoinColumn(name = "morador_id", referencedColumnName = "id")
    private UsuarioCondominio morador;

    @NotNull(message = NAO_NULL)
    @Size(max = 4)
    @Pattern(regexp = "^\\d+$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(nullable = false, length = 4)
    private String numeroUnidade;

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao situacao;

    @Size(max = 150, message = "O tamanho deve ser menor ou igual a 150 caracteres.")
    @Column
    private String quadraTorreBloco;

    @Size(max = 150, message = "O tamanho deve ser menor ou igual a 150 caracteres.")
    @Column
    private String lote;

    @Override
    public String toString() {
        return String.format("Moradia id: %d, número unidade:%s %s", getId(), numeroUnidade, situacao.getDescricao());
    }
}
