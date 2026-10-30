package ma.youcode.gestion_commandes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "commandes")
@Getter
@Setter
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produit;
    private Integer quantite;

    @Column(precision = 12, scale = 2)
    private BigDecimal prix;

    @Column(name = "total_ht", precision = 12, scale = 2)
    private BigDecimal totalHt;

    @Column(precision = 5, scale = 2)
    private BigDecimal tva;

    @Column(name = "total_ttc", precision = 12, scale = 2)
    private BigDecimal totalTtc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}
