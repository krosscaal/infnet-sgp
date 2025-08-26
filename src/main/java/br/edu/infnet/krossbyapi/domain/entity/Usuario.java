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
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

import static br.edu.infnet.krossbyapi.util.MensagemCenter.*;

@Getter
@Setter
@Entity
public class Usuario extends EntidadeBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 5405172041950251807L;

    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]*$", message = DEVE_CONTER_SOMENTE_LETRAS)
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]*$", message = DEVE_CONTER_SOMENTE_LETRAS)
    @Column(nullable = false, length = 150)
    private String sobreNome;

    @Size(min = 14, max = 14, message = "O tamanho deve ser igual a 14 caracteres.")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Deve ser em formato : XXX.XXX.XXX-XX")
    @Column(length = 14)
    private String cpf;
    
    @Size(max = 11, message = "O tamanho deve ser de 11 caracteres.")
    @Pattern(regexp = "^\\d{11}$", message = "RG deve conter apenas números")
    @Column(length = 11)
    private String rg;

    @Size(min = 10, max = 11, message = "Tamanho deve ser entre 10 e 11 caracteres.")
    @Pattern(regexp = "^\\d{10,11}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(name = "telefone_1", length = 11)
    private String telefone1;

    @Size(min = 10, max = 11 , message = "Tamanho deve ser entre 10 e 11 caracteres.")
    @Pattern(regexp = "^\\d{10,11}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(name = "telefone_2", length = 11)
    private String telefone2;

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.STRING)
    private EnumTipoSituacao situacao;

    @Override
    public String toString() {
        return String.format("Usuario id:%d %s %s %s %s %s %s %s", getId(), nome, sobreNome, cpf, rg, telefone1, telefone2, situacao.getDescricao());
    }
}
