/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossbyapi.repository;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaRepository extends JpaRepository<Moradia, Long> {
}
