/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */
 
package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Usuario extends EntidadeBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 5405172041950251807L;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150)
    private String sobreNome;

    @Column(length = 11)
    private String cpf;

    @Column(length = 11)
    private String rg;

    @Column(name = "telefone_1", length = 11)
    private String telefone1;

    @Column(name = "telefone_2", length = 11)
    private String telefone2;

    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao situacao;

    @Override
    public String toString() {
        return String.format("Usuario id:%d %s %s %s %s %s %s %s", getId(), nome, sobreNome, cpf, rg, telefone1, telefone2, situacao.getDescricao());
    }
}
