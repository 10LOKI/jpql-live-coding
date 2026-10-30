package ma.youcode.gestion_commandes.repositories;
import ma.youcode.gestion_commandes.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import ma.youcode.gestion_commandes.projections.MontantExtremes;
import java.util.List;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByQuantiteGreaterThan(Integer quantite);
    List<Commande> findByQuantiteGreaterThanEqual(Integer quantite);
    List<Commande> findByPrixLessThan(BigDecimal prix);
    List<Commande> findByPrixLessThanEqual(BigDecimal prix);
    List<Commande> findByTotalTtcBetween(BigDecimal min, BigDecimal max);
    List<Commande> findByQuantiteGreaterThanAndPrixLessThan(
            Integer quantite, BigDecimal prix);

    List<Commande> findAllByOrderByTotalTtcDesc();
    List<Commande> findTop5ByOrderByTotalTtcDesc();
    Optional<Commande> findFirstByOrderByTotalTtcDesc();
    List<Commande> findTop3ByProduitContainingOrderByTotalTtcDesc(String produit);
    List<Commande> findByProduitContaining(String produit, Pageable pageable);

    @Query("SELECT c FROM Commande c WHERE c.produit IN :produits")
    List<Commande> findByProduitsIn(@Param("produits") List<String> produits);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Commande c SET c.tva = :nouvelleTva
        WHERE c.client.id = :clientId
        """)
    int updateTvaForClient(
        @Param("clientId") Long clientId,
        @Param("nouvelleTva") BigDecimal nouvelleTva);

    List<Commande> findByClient_Nom(String nom);
    List<Commande> findByClient_NomAndClient_Prenom(String nom , String prenom);
    List<Commande> findByClientAdresse(String adresse);

    // Exercices 11 et 13 : navigation et agregations.
    List<Commande> findByClient_NomAndTotalTtcGreaterThan(
        String nom, BigDecimal montant);
    
    @Query("""
        SELECT cmd FROM Commande cmd
        WHERE cmd.client.adresse LIKE CONCAT('%', :ville, '%')
        """)
    List<Commande> findByClientVille(@Param("ville") String ville);
    
    @Query("""
        SELECT COUNT(c) FROM Commande c
        WHERE c.produit LIKE CONCAT('%', :produit, '%')
        """)
    Long countProduitsContenant(@Param("produit") String produit);
    
    @Query("SELECT SUM(c.totalTtc) FROM Commande c")
    BigDecimal sommeTotalTtc();
    
    @Query("SELECT AVG(c.prix) FROM Commande c")
    Double prixMoyen();
    
    @Query("""
        SELECT MIN(c.totalTtc) AS minimum, MAX(c.totalTtc) AS maximum
        FROM Commande c
        """)
    MontantExtremes findMontantsExtremes();
    
}
