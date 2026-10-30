package ma.youcode.gestion_commandes.services;
import ma.youcode.gestion_commandes.entities.Client;
import ma.youcode.gestion_commandes.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import ma.youcode.gestion_commandes.dto.ClientStatDTO;
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    public List<Client> rechercherParNom(String nom) {
        return clientRepository.findByNom(nom);
    }
    public List<ClientStatDTO> getStatistiquesClients() {
        return clientRepository.getClientStats();
    }
}
