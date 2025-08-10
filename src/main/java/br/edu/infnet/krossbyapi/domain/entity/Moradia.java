/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoMoradia;
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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_moradia")
public class Moradia extends EntidadeBase {

    @Enumerated(EnumType.STRING)
    private EnumTipoMoradia tipoMoradia;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "propietario_id")
    private UsuarioCondominio propietario;

    @OneToOne()
    @JoinColumn(name = "morador_id", referencedColumnName = "id")
    private UsuarioCondominio morador;

    @Column(nullable = false, length = 4)
    private String numeroUnidade;

    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao ativo;

}
