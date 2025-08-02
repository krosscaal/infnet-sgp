/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumAtivo;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoResidente;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario_condominio")
public class UsuarioCondominio extends EntidadeBase {

    @Embedded
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EnumTipoResidente tipoResidente;

    @Column(length = 80)
    private String email;

    @Enumerated(EnumType.STRING)
    private EnumAtivo ativo;

    @OneToOne(mappedBy = "morador")
    private Moradia moradia;

}
