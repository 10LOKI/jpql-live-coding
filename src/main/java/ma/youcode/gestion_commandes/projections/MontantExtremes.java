package ma.youcode.gestion_commandes.projections;
import java.math.BigDecimal;

public interface MontantExtremes {
    BigDecimal getMinimum();
    BigDecimal getMaximum();
}
