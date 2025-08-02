/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.domain.entity;

import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoCondominio;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_condominio")
public class Condominio extends EntidadeBase{
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

    @Embedded
    private Endereco endereco;

    @Column(length = 200)
    private String nomeSindico;
    @Column(length = 11)
    private String telefoneSindico;

    protected Condominio() {}

    @Override
    public String toString() {
        return "Condominio{" +
                "nomeCondominio='" + nomeCondominio + '\'' +
                ", tipoCondominio=" + tipoCondominio +
                ", totalUnidades=" + totalUnidades +
                ", cnpj='" + cnpj + '\'' +
                ", telefoneContato1='" + telefoneContato1 + '\'' +
                ", telefoneContato2='" + telefoneContato2 + '\'' +
                ", endereco=" + endereco +
                ", nomeSindico='" + nomeSindico + '\'' +
                ", telefoneSindico='" + telefoneSindico + '\'' +
                '}';
    }
}
