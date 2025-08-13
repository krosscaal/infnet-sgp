/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoResidente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario_condominio")
public class UsuarioCondominio extends EntidadeBase {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EnumTipoResidente tipoResidente;

    @Column(length = 80)
    private String email;

    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao ativo;

    @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY)
    private List<Moradia> moradias = new ArrayList<>();

}
