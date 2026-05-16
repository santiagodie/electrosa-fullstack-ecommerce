package es.unirioja.paw.repository;

import es.unirioja.paw.jpa.ArticuloEntity;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticuloRepository extends JpaRepository<ArticuloEntity, String> {

    @Query("SELECT a FROM ArticuloEntity a WHERE " +
           "(:fabricante IS NULL OR a.fabricante = :fabricante) AND " +
           "(:tipo IS NULL OR a.tipo = :tipo) AND " +
           "(:precioMin IS NULL OR a.pvp >= :precioMin) AND " +
           "(:precioMax IS NULL OR a.pvp <= :precioMax)")
    Page<ArticuloEntity> findWithFilters(
            @Param("fabricante") String fabricante,
            @Param("tipo") String tipo,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable);

    @Query("SELECT DISTINCT a.fabricante FROM ArticuloEntity a ORDER BY a.fabricante")
    List<String> findDistinctFabricantes();

    @Query("SELECT DISTINCT a.tipo FROM ArticuloEntity a ORDER BY a.tipo")
    List<String> findDistinctTipos();
}
