package ma.youcode.gestion_commandes;

import ma.youcode.gestion_commandes.entities.Client;
import ma.youcode.gestion_commandes.repositories.ClientRepository;
import ma.youcode.gestion_commandes.repositories.CommandeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PartieDeuxTest {
    @Autowired ClientRepository clients;
    @Autowired CommandeRepository commandes;

    @Test void navigationEtFiltresSurLeClient() {
        assertThat(commandes.findByClient_Nom("Bennani")).hasSize(3);
        assertThat(commandes.findByClient_NomAndClient_Prenom("Bennani", "Youssef")).hasSize(3);
        assertThat(commandes.findByClient_NomAndTotalTtcGreaterThan("Bennani", new BigDecimal("1000"))).hasSize(1);
        assertThat(commandes.findByClientVille("Rabat")).hasSize(2);
        assertThat(commandes.findByClientAdresse("12 avenue Hassan II, 20000 Casablanca")).hasSize(4);
    }

    @Test void calculsGlobaux() {
        assertThat(commandes.countProduitsContenant("USB")).isEqualTo(1);
        assertThat(commandes.sommeTotalTtc()).isEqualByComparingTo("5021.61");
        assertThat(commandes.prixMoyen()).isCloseTo(190.49, org.assertj.core.data.Offset.offset(0.0001));
        assertThat(commandes.findMontantsExtremes().getMinimum()).isEqualByComparingTo("89.96");
        assertThat(commandes.findMontantsExtremes().getMaximum()).isEqualByComparingTo("2159.98");
    }

    @Test void groupesEtDtoPagines() {
        assertThat(clients.findClientsAvecCommandesSuperieuresA(new BigDecimal("1000")))
            .extracting(Client::getNom).containsExactlyInAnyOrder("Bennani", "El Amrani");
        assertThat(clients.statistiquesParClient()).hasSize(5);
        assertThat(clients.clientsDepensantPlusQue(new BigDecimal("1000"))).hasSize(2);
        var page = clients.getClientStats(PageRequest.of(0, 2));
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().get(0).totalAchats()).isEqualByComparingTo("2459.80");
    }

    @Test void clientSansCommandeEtHomonymeRestentDesGroupesDistincts() {
        Client nouveau = new Client();
        nouveau.setNom("Bennani");
        nouveau.setPrenom("Youssef");
        clients.saveAndFlush(nouveau);
        assertThat(clients.findAllClientsWithCommandes())
            .anySatisfy(ligne -> {
                assertThat(((Client) ligne[0]).getId()).isEqualTo(nouveau.getId());
                assertThat(ligne[1]).isNull();
            });
        assertThat(clients.findAllAvecCommandes())
            .filteredOn(c -> c.getId().equals(nouveau.getId()))
            .allSatisfy(c -> assertThat(c.getCommandes()).isEmpty()).hasSize(1);
        assertThat(clients.statistiquesParClient()).hasSize(6);
        assertThat(clients.getClientStats())
            .filteredOn(dto -> dto.nombreCommandes() == 0L)
            .singleElement().satisfies(dto -> assertThat(dto.totalAchats()).isEqualByComparingTo("0"));
        assertThat(clients.getClientStats(PageRequest.of(0, 2)).getTotalElements()).isEqualTo(6);
    }
}
