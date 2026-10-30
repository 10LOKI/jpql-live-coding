package ma.youcode.gestion_commandes.dto;
import java.math.BigDecimal;

public record ClientStatDTO(
    String nomComplet,
    Long nombreCommandes,
    BigDecimal totalAchats
) {
    public ClientStatDTO(String nom, String prenom, Long nb, BigDecimal total) {
        this(nom + " " + prenom, nb,
             total == null ? BigDecimal.ZERO : total);
    }
}
