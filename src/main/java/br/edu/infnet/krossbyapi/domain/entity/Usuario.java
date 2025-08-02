/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */
 
package br.edu.infnet.krossbyapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 5405172041950251807L;

    @Column(nullable = false, length = 150)
    private String nomeCompleto;

    @Column(length = 11)
    private String cpf;

    @Column(length = 11)
    private String rg;

    @Column(name = "telefone_1", length = 11)
    private String telefone1;

    @Column(name = "telefone_2", length = 11)
    private String telefone2;
}
