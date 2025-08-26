/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.repository;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioCondominioRepository extends JpaRepository<UsuarioCondominio, Long> {
    @Query("SELECT uc.moradias FROM UsuarioCondominio uc WHERE uc.id = :idUsuarioCondominio")
    List<Moradia> findMoradiasPorId(Long idUsuarioCondominio);
}
