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
    @Override
    @Transactional(readOnly = true)
    public void run(String... args) {
        System.out.println("=== EXERCICES 11 A 15 ===");
        System.out.println("EX11 commandes Bennani : " + commandes.findByClient_Nom("Bennani").size());
        System.out.println("EX11 nom et prenom : " + commandes.findByClient_NomAndClient_Prenom("Bennani", "Youssef").size());
        System.out.println("EX11 Bennani TTC > 1000 : " + commandes.findByClient_NomAndTotalTtcGreaterThan("Bennani", new BigDecimal("1000")).size());
        System.out.println("EX11 adresse exacte Casablanca : " + commandes.findByClientAdresse("12 avenue Hassan II, 20000 Casablanca").size());
        System.out.println("EX11 commandes Rabat : " + commandes.findByClientVille("Rabat").size());
        System.out.println("EX12 clients commande > 1000 : " + clients.findClientsAvecCommandesSuperieuresA(new BigDecimal("1000")).size());
        System.out.println("EX12 lignes LEFT JOIN : " + clients.findAllClientsWithCommandes().size());
        System.out.println("EX12 clients avec FETCH : " + clients.findAllAvecCommandes().size());
        System.out.println("EX13 produits contenant USB : " + commandes.countProduitsContenant("USB"));
        System.out.println("EX13 somme TTC : " + commandes.sommeTotalTtc());
        System.out.println("EX13 prix moyen : " + commandes.prixMoyen());
        var extremes = commandes.findMontantsExtremes();
        System.out.println("EX13 minimum : " + extremes.getMinimum());
        System.out.println("EX13 maximum : " + extremes.getMaximum());
        clients.statistiquesParClient().forEach(l -> System.out.println("EX14 stats : " + Arrays.toString(l)));
        System.out.println("EX14 clients cumul > 1000 : " + clients.clientsDepensantPlusQue(new BigDecimal("1000")).size());
        clients.getClientStats().forEach(s -> System.out.println("EX15 DTO : " + s));
        var page = clients.getClientStats(PageRequest.of(0, 2));
        System.out.println("EX15 elements page : " + page.getNumberOfElements());
        System.out.println("EX15 total clients : " + page.getTotalElements());
        System.out.println("EX15 total pages : " + page.getTotalPages());
    }
}
