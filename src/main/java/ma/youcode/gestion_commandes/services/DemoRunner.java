package ma.youcode.gestion_commandes.services;
import ma.youcode.gestion_commandes.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.util.Arrays;
@Component
@RequiredArgsConstructor
public class DemoRunner implements CommandLineRunner {
    private final ClientRepository clients;
    private final CommandeRepository commandes;
    private void afficher(String nom, Object valeur) {
        System.out.println(nom + " : " + valeur);
    }
    @Override
    @Transactional(readOnly = true)
    public void run(String... args) {
        System.out.println("=== SECOURS : EXERCICES 11 A 15 ===");
        afficher("EX11 commandes Bennani", commandes.findByClient_Nom("Bennani").size());
        afficher("EX11 nom et prenom", commandes.findByClient_NomAndClient_Prenom("Bennani", "Youssef").size());
        afficher("EX11 Bennani TTC > 1000", commandes.findByClient_NomAndTotalTtcGreaterThan("Bennani", new BigDecimal("1000")).size());
        afficher("EX11 adresse exacte Casablanca", commandes.findByClientAdresse("12 avenue Hassan II, 20000 Casablanca").size());
        afficher("EX11 commandes Rabat", commandes.findByClientVille("Rabat").size());
        afficher("EX12 clients commande > 1000", clients.findClientsAvecCommandesSuperieuresA(new BigDecimal("1000")).size());
        afficher("EX12 lignes LEFT JOIN", clients.findAllClientsWithCommandes().size());
        afficher("EX12 clients avec FETCH", clients.findAllAvecCommandes().size());
        afficher("EX13 produits contenant USB", commandes.countProduitsContenant("USB"));
        afficher("EX13 somme TTC", commandes.sommeTotalTtc());
        afficher("EX13 prix moyen", commandes.prixMoyen());
        var extremes = commandes.findMontantsExtremes();
        afficher("EX13 minimum", extremes.getMinimum());
        afficher("EX13 maximum", extremes.getMaximum());
        clients.statistiquesParClient().forEach(l -> afficher("EX14 stats", Arrays.toString(l)));
        afficher("EX14 clients cumul > 1000", clients.clientsDepensantPlusQue(new BigDecimal("1000")).size());
        clients.getClientStats().forEach(s -> afficher("EX15 DTO", s));
        var page = clients.getClientStats(PageRequest.of(0, 2));
        afficher("EX15 elements page", page.getNumberOfElements());
        afficher("EX15 total clients", page.getTotalElements());
        afficher("EX15 total pages", page.getTotalPages());
    }
}
