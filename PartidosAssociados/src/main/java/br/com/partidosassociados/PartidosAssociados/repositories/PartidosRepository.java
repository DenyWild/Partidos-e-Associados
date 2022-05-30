package br.com.partidosassociados.PartidosAssociados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;

public interface PartidosRepository extends JpaRepository<Partidos, Long> {

	@Query(value = "SELECT id, nome, ideologia, sigla, data_de_fundacao FROM Partidos", nativeQuery = true)
	List<Partidos> listarPartidos();

	List<Partidos> findByIdeologia(Ideologia ideologia);

}
