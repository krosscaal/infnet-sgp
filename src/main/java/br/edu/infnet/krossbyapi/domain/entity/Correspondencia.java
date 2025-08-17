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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Column(nullable = false, length = 150)
    private String nomeDestinatario;

    @Column(length = 12)
    private String telefoneDestinatario;

    @Column(length = 100)
    private String emailDestinatario;

    @Column(length = 30)
    private String codigoIdentificadorDaEntrega;

    @CreationTimestamp
    private LocalDateTime dataRecepcao;

    @CreationTimestamp
    private LocalDateTime dataEntregaDestinatario;

    @ManyToOne
    @JoinColumn(name = "usuario_recepcao", nullable = false)
    private UsuarioSistema usuarioRecepcao;

    @ManyToOne
    @JoinColumn(name = "usuario_entrega")
    private UsuarioSistema usuarioEntrega;

    @Column(name = "nome_morador_recepcao", length = 150)
    private String nomeMoradorRecepcao;

    @Override
    public String toString() {
        return String.format("Correspondencia id:%d moradia entrega:%s destinatario:%s", getId(), moradiaEntrega.getNumeroUnidade(), nomeDestinatario);
    }
}
