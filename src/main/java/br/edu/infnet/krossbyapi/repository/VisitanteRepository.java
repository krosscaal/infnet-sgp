/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.repository;

import br.edu.infnet.krossbyapi.domain.entity.Visitante;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
    @Query("SELECT v FROM Visitante v WHERE v.usuarioVisitante.cpf = :cpf AND v.tipoAcesso = :tipoAcesso")
    List<Visitante> findByCpf(String cpf, EnumTipoAcesso tipoAcesso);

    List<Visitante> findAllByIngressoBetween(LocalDateTime ingressoInicio, LocalDateTime ingressoFim);
}
