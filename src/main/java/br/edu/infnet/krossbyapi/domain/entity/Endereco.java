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
public class Endereco implements Serializable {
    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    @Column(nullable = false, length = 150)
    private String logradouro;
    @Column(nullable = false, length = 6)
    private String numero;
    @Column(length = 150)
    private String complemento;
    @Column(length = 150)
    private String bairro;
    @Column(length = 150)
    private String cidade;
    @Column(length = 8)
    private String cep;
    @Column(length = 30)
    private String estado;
    @Column(length = 8)
    private String uf;
}
