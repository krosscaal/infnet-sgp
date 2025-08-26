/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static br.edu.infnet.krossbyapi.util.MensagemCenter.*;

@Getter
@Setter
@Entity
@Table(name = "tb_correspondencia")
public class Correspondencia extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "moradia_da_entrega",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_correspondencia_id_moradia"))
    private Moradia moradiaEntrega;

    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Size(max = 150, message = "O tamanho deve ser menor ou igual a 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String nomeDestinatario;

    @Size(min = 10, max = 11, message = "deve ser entre 10 e 11 caracteres numéricos")
    @Pattern(regexp = "^\\d{10,11}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(length = 11)
    private String telefoneDestinatario;

    @Size(max = 100, message = "O tamanho deve ser menor ou igual a 100 caracteres.")
    @Email(message = FORMATO_E_MAIL_INCORRETO)
    @Column(length = 100)
    private String emailDestinatario;

    @Column(length = 30)
    private String codigoIdentificadorDaEntrega;

    @NotNull(message = NAO_NULL)
    @CreationTimestamp
    private LocalDateTime dataRecepcao;

    private LocalDateTime dataEntregaDestinatario;

    @NotNull(message = NAO_NULL)
    @ManyToOne
    @JoinColumn(name = "usuario_recepcao", nullable = false)
    private UsuarioSistema usuarioRecepcao;

    @ManyToOne
    @JoinColumn(name = "usuario_entrega")
    private UsuarioSistema usuarioEntrega;

    @Size(max = 150, message = "O tamanho deve ser menor ou igual a 150 caracteres.")
    @Column(name = "nome_morador_recepcao", length = 150)
    private String nomeMoradorRecepcao;

    @Override
    public String toString() {
        return String.format("Correspondencia id:%d moradia entrega:%s destinatario:%s", getId(), moradiaEntrega.getNumeroUnidade(), nomeDestinatario);
    }
}
