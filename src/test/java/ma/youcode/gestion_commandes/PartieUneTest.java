package ma.youcode.gestion_commandes;

import ma.youcode.gestion_commandes.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PartieUneTest {
    @Autowired ClientRepository clients;
    @Autowired CommandeRepository commandes;

    @Test void donneesInitiales() {
        assertThat(clients.count()).isEqualTo(5);
        assertThat(commandes.count()).isEqualTo(10);
    }

    @Test void recherchesClientsEtProjections() {
        assertThat(clients.findByNom("Bennani")).hasSize(1);
        assertThat(clients.findByAdresseContainingIgnoreCase("rabat")).hasSize(1);
        assertThat(clients.existsByNomAndPrenom("Bennani", "Youssef")).isTrue();
        assertThat(clients.rechercherParVille("Casablanca")).hasSize(2);
        assertThat(clients.findProjectionByVille("Rabat").get(0).getNom())
            .isEqualTo("El Amrani");
    }

    @Test void montantsTriEtRequetesJpql() {
        assertThat(commandes.findByTotalTtcBetween(new BigDecimal("1000"),
            new BigDecimal("3000"))).hasSize(2);
        assertThat(commandes.findFirstByOrderByTotalTtcDesc()).isPresent();
        assertThat(commandes.findFirstByOrderByTotalTtcDesc().orElseThrow().getTotalTtc())
            .isEqualByComparingTo("2159.98");
        assertThat(commandes.findByProduitsIn(List.of("Hub USB"))).hasSize(1);
    }

    @Test void miseAJourAdresseSansModifierLesDonneesDesAutresTests() {
        assertThat(clients.updateAdresse(1L, "Adresse test")).isEqualTo(1);
        assertThat(clients.findById(1L).orElseThrow().getAdresse()).isEqualTo("Adresse test");
    }

    @Test void miseAJourTvaEtSuppressionSansResultat() {
        assertThat(commandes.updateTvaForClient(1L, new BigDecimal("10.00"))).isEqualTo(3);
        assertThat(clients.deleteByNomQuery("Client inexistant")).isZero();
    }
}
