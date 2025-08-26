/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoResidente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static br.edu.infnet.krossbyapi.util.MensagemCenter.FORMATO_E_MAIL_INCORRETO;
import static br.edu.infnet.krossbyapi.util.MensagemCenter.NAO_NULL;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario_condominio")
public class UsuarioCondominio extends EntidadeBase {

    @NotNull(message = NAO_NULL)
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.STRING)
    private EnumTipoResidente tipoResidente;

    @Size(max = 80)
    @Email(message = FORMATO_E_MAIL_INCORRETO)
    @Column(length = 80)
    private String email;

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao situacao;

    @JsonIgnore
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Moradia> moradias = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("Usuario Condominio id:%d, %s %s %s %s",
                getId(), getUsuario().toString(), tipoResidente.getUsuarioResidente(), email, situacao.getDescricao());
    }
}
