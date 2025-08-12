/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoCondominio;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_condominio")
public class Condominio extends EntidadeBase {
    @Column(nullable = false, length = 150)
    private String nomeCondominio;

    @Enumerated(EnumType.ORDINAL)
    private EnumTipoCondominio tipoCondominio;

    @Column(nullable = false)
    private int totalUnidades;

    @Column(length = 15)
    private String cnpj;

    @Column(name = "telefone_contato_1", length = 11)
    private String telefoneContato1;

    @Column(name = "telefone_contato_2", length = 11)
    private String telefoneContato2;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;

    @Column(length = 200)
    private String nomeSindico;

    @Column(length = 11)
    private String telefoneSindico;

    @Enumerated(EnumType.ORDINAL)
    private EnumTipoSituacao situacao;

    protected Condominio() {}

    @Override
    public String toString() {
        StringBuilder sbCondominio = new StringBuilder("INFORMAÇÕES:\n");
        sbCondominio.append("id: ").append(getId()).append("\n");
        sbCondominio.append("Condominio: ").append(nomeCondominio).append("\n");
        sbCondominio.append("TipoCondominio: ").append(tipoCondominio.getTipoDeCondominio()).append("\n");
        sbCondominio.append("TotalUnidades: ").append(totalUnidades).append("\n");
        sbCondominio.append("Cnpj: ").append(cnpj).append("\n");
        sbCondominio.append("TelefoneContato1: ").append(telefoneContato1).append("\n");
        sbCondominio.append("TelefoneContato2: ").append(telefoneContato2).append("\n");
        sbCondominio.append("Situacão: ").append(situacao.getDescricao()).append("\n");
        sbCondominio.append("Endereco: ").append(endereco);
        sbCondominio.append("NomeSindico: ").append(nomeSindico).append("\n");
        sbCondominio.append("telefoneSindico: ").append(telefoneSindico).append("\n");
        return sbCondominio.toString();
    }
}
