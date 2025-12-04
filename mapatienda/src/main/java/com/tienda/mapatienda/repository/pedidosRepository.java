package com.tienda.mapatienda.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tienda.mapatienda.model.pedido;

public interface pedidosRepository extends JpaRepository<pedido, Long> {

    //Buscar pedidos por ID de cliente
    List<pedido> findByClienteIdCliente(Long idCliente);  


    // 1. Método para borrar registros que cumplan ciertas condiciones 
    /**
     * Borra pedidos cuyo estado sea un valor específico.
     * Ejemplo: deleteByEstado("anulado")
     */
    @Modifying
    long deleteByEstado(String estado);

    // 2. Borrar registros que cumplan ciertas condiciones
    /**
     * Borra pedidos cuyo total sea inferior a un precio en especifico.
     * Ejemplo: deleteByTotalLessThan(10.00)
     */
    @Modifying
    long deleteByTotalLessThan(double total);

    List<pedido> findByFechaPedidoAndEstadoAndTotal(Date fecha, String estado, double total);

    // 3. Método de búsqueda con filtros
    /**
     * Busca pedidos que tengan un estado específico y un total mayor o igual a un monto dado.
     * Ejemplo: findByEstadoAndTotalGreaterThanEqual("ENTREGADO", 50.00)
     */
    List<pedido> findByEstadoAndTotalGreaterThanEqual(String estado, double total);

    // 4. Consulta personalizada con @Query (JPQL)
    /**
     * Busca todos los pedidos realizados antes de una fecha específica.
     * @param fecha Limite superior de la fecha de pedido.
     */
    @Query("SELECT p FROM pedido p WHERE p.fechaPedido < :fechaLimite")
    List<pedido> buscarPedidosAnterioresA(@Param("fechaLimite") Date fecha);
    
}
