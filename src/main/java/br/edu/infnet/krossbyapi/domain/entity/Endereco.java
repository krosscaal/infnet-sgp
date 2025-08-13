/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Endereco extends EntidadeBase implements Serializable {
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
    private String localidade;
    @Column(length = 8)
    private String cep;
    @Column(length = 30)
    private String estado;
    @Column(length = 8)
    private String uf;

    @Override
    public String toString() {
        StringBuilder sbEndereco = new StringBuilder();
        sbEndereco.append("Logradouro: ").append(logradouro).append("\n");
        sbEndereco.append("Numero: ").append(numero).append("\n");
        sbEndereco.append("Complemento: ").append(complemento).append("\n");
        sbEndereco.append("Bairro: ").append(bairro).append("\n");
        sbEndereco.append("Localidade: ").append(localidade).append("\n");
        sbEndereco.append("CEP: ").append(cep).append("\n");
        sbEndereco.append("Estado: ").append(estado).append("\n");
        sbEndereco.append("UF: ").append(uf).append("\n");
        return sbEndereco.toString();
    }
}
