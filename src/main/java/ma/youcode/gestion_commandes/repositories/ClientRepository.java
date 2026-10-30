package ma.youcode.gestion_commandes.repositories;
import ma.youcode.gestion_commandes.entities.Client;
import ma.youcode.gestion_commandes.projections.ClientProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ma.youcode.gestion_commandes.dto.ClientStatDTO;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNom(String nom);
    List<Client> findByNomAndPrenom(String nom, String prenom);
    List<Client> findByNomOrPrenom(String nom, String prenom);

    List<Client> findByAdresseContaining(String ville);
    List<Client> findByAdresseContainingIgnoreCase(String ville);
    List<Client> findByNomStartingWith(String debut);
    List<Client> findByNomEndingWith(String fin);

    List<Client> findByNomOrderByPrenomAsc(String nom);

    long countByNom(String nom);
    long countByAdresseContaining(String ville);
    boolean existsByNom(String nom);
    boolean existsByNomAndPrenom(String nom, String prenom);

    @Query("""
        SELECT c FROM Client c
        WHERE LOWER(c.adresse) LIKE LOWER(CONCAT('%', :ville, '%'))
        """)
    List<Client> rechercherParVille(@Param("ville") String ville);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.adresse = :adresse WHERE c.id = :id")
    int updateAdresse(@Param("id") Long id, @Param("adresse") String adresse);

    @Modifying
    @Transactional
    @Query("DELETE FROM Client c WHERE c.nom = :nom")
    int deleteByNomQuery(@Param("nom") String nom);

    @Query("SELECT c.nom, c.prenom FROM Client c")
    List<Object[]> findNomsPrenoms();

    @Query("""
        SELECT c.nom AS nom, c.prenom AS prenom, c.adresse AS adresse
        FROM Client c
        WHERE c.adresse LIKE CONCAT('%', :ville, '%')
        """)
    List<ClientProjection> findProjectionByVille(@Param("ville") String ville);


    // Exercices 12, 14 et 15 : jointures, groupes et DTO.
    @Query("""
        SELECT DISTINCT c
        FROM Client c
        JOIN c.commandes cmd
        WHERE cmd.totalTtc > :montant
        """)
    List<Client> findClientsAvecCommandesSuperieuresA(
        @Param("montant") BigDecimal montant);
    
    @Query("""
        SELECT c, cmd
        FROM Client c
        LEFT JOIN c.commandes cmd
        """)
    List<Object[]> findAllClientsWithCommandes();
    
    @Query("""
        SELECT DISTINCT c FROM Client c
        LEFT JOIN FETCH c.commandes
        """)
    List<Client> findAllAvecCommandes();
    
    @Query("""
        SELECT c.nom, c.prenom, COUNT(cmd), SUM(cmd.totalTtc)
        FROM Client c
        LEFT JOIN c.commandes cmd
        GROUP BY c.id, c.nom, c.prenom
        ORDER BY SUM(cmd.totalTtc) DESC
        """)
    List<Object[]> statistiquesParClient();
    
    @Query("""
        SELECT c.nom, c.prenom, SUM(cmd.totalTtc)
        FROM Client c
        JOIN c.commandes cmd
        GROUP BY c.id, c.nom, c.prenom
        HAVING SUM(cmd.totalTtc) > :montant
        ORDER BY SUM(cmd.totalTtc) DESC
        """)
    List<Object[]> clientsDepensantPlusQue(@Param("montant") BigDecimal montant);
    
    @Query("""
        SELECT NEW ma.youcode.gestion_commandes.dto.ClientStatDTO(
            c.nom, c.prenom, COUNT(cmd), SUM(cmd.totalTtc)
        )
        FROM Client c
        LEFT JOIN c.commandes cmd
        GROUP BY c.id, c.nom, c.prenom
        ORDER BY SUM(cmd.totalTtc) DESC
        """)
    List<ClientStatDTO> getClientStats();
    
    @Query(
        value = """
            SELECT NEW ma.youcode.gestion_commandes.dto.ClientStatDTO(
                c.nom, c.prenom, COUNT(cmd), SUM(cmd.totalTtc)
            )
            FROM Client c
            LEFT JOIN c.commandes cmd
            GROUP BY c.id, c.nom, c.prenom
            ORDER BY SUM(cmd.totalTtc) DESC
            """,
        countQuery = "SELECT COUNT(c) FROM Client c"
    )
    Page<ClientStatDTO> getClientStats(Pageable pageable);
    
}
