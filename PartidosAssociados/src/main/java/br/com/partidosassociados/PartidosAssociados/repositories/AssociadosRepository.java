package br.com.partidosassociados.PartidosAssociados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;

public interface AssociadosRepository extends JpaRepository<Associados, Long> {

	List<Associados> findByCargoPolitico(CargoPolitico cargoPolitico);

}
