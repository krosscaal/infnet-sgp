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
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import static br.edu.infnet.krossbyapi.util.MensagemCenter.*;

@Getter
@Setter
@Entity
@Table(name = "tb_condominio")
public class Condominio extends EntidadeBase {
    @Size(min = 5, max = 150, message = "O tamanho deve ser entre 5 e 150 caracteres.")
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Column(nullable = false, length = 150)
    private String nomeCondominio;

    @NotNull(message = NAO_NULL)
    @Enumerated(EnumType.ORDINAL)
    private EnumTipoCondominio tipoCondominio;

    @NotNull(message = NAO_NULL)
    @Column(nullable = false)
    private int totalUnidades;

    @Size(min = 15, max = 15, message = "deve conter 15 digitos numéricos")
    @Pattern(regexp = "^\\d{15}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(length = 15)
    private String cnpj;

    @Size(min = 10, max = 11, message = "deve conter entre 10 e 11 caracteres numéricos")
    @Pattern(regexp = "^\\d{10,11}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(name = "telefone_contato_1", length = 11)
    private String telefoneContato1;

    @Size(min = 10, max = 11, message = "deve conter entre 10 e 11 caracteres numéricos")
    @Pattern(regexp = "^\\d{10,11}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(name = "telefone_contato_2", length = 11)
    private String telefoneContato2;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;

    @Size(min = 4, max = 200, message = "O tamanho deve ser entre 4 e 200 caracteres.")
    @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
    @Column(length = 200)
    private String nomeSindico;

    @Size(min = 10, max = 11, message = "deve conter entre 10 e 11 caracteres numéricos")
    @Pattern(regexp = "^\\d{10,11}$", message = DEVE_CONTER_SOMENTE_NUMEROS)
    @Column(length = 11)
    private String telefoneSindico;

    @NotNull(message = NAO_NULL)
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
