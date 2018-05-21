package fr.proxibanque.proxibanquesi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.proxibanque.proxibanquesi.dao.ClientDAO;
import fr.proxibanque.proxibanquesi.dao.ConseillerDAO;
import fr.proxibanque.proxibanquesi.dao.CompteDAO;
import fr.proxibanque.proxibanquesi.exceptions.ServiceException;
import fr.proxibanque.proxibanquesi.model.CarteBancaire;
import fr.proxibanque.proxibanquesi.model.Client;
import fr.proxibanque.proxibanquesi.model.Compte;
import fr.proxibanque.proxibanquesi.model.CompteCourant;
import fr.proxibanque.proxibanquesi.model.Conseiller;
import fr.proxibanque.proxibanquesi.model.Gerant;
import fr.proxibanque.proxibanquesi.model.CompteEpargne;

@Service("service")
public class ProxiBanqueServiceImp implements GestionClientService, GestionCompteService, GestionConseillerService {

	// *** ATTRIBUTS ***

	@Autowired
	@Qualifier("clientDAO")
	ClientDAO clientDao;

	@Autowired
	ConseillerDAO conseillerDao;

	@Autowired
	@Qualifier("compteDAO")
	CompteDAO compteDAO;

	// *** GETTERS et SETTERS ***

	/**
	 * Permet d'attribuer une DAO client au service afin de réaliser les tests
	 * Mockito.
	 * 
	 * @param clientDao
	 */
	public void setClientDao(ClientDAO clientDao) {
		this.clientDao = clientDao;
	}

	// *** GESTION CLIENTS ***

	@Override
	public void creerClient(Client client) throws ServiceException {
		if (!clientEstValide(client)) {
			throw new ServiceException("Client invalide !");
		} else {
			clientDao.save(client);
		}
	}

	private boolean clientEstValide(Client client) {
		if (client == null) {
			return false;
		}
		String nom = client.getNom();
		String prenom = client.getPrenom();
		// Teste si les nom et prénom sont null, vides, ou ne contiennent que des espaces
		if (nom == null || nom.trim().isEmpty()) {
			return false;
		} else if (prenom == null || prenom.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public Client obtenirClient(long idClient) {
		return clientDao.findOne(idClient);
	}

	@Override
	public List<Client> obtenirTousClients() {
		return clientDao.findAll();
	};

	@Override
	public void modifierClient(Client client) throws ServiceException {
		if (client.getIdClient() == 0) {
			throw new ServiceException("Le client modifié doit avoir un ID.");
		} else {
			clientDao.save(client);
		}
	}

	@Override
	public void supprimerClient(long idClient) throws ServiceException {
		Client clientCandidat = this.obtenirClient(idClient);
		if (clientCandidat == null) {
			throw new ServiceException("Ce client n'existe pas !");
		} else {
			clientDao.delete(idClient);
		}
	}

	// *** GESTION CONSEILLERS ***

	@Override
	public void creerConseiller(Conseiller conseiller) {
		conseillerDao.save(conseiller);
	}

	@Override
	public void AttribuerCompteEpargneClient(long idClient, CompteEpargne compteEpargne) {
		// TODO Auto-generated method stub
		Client client = obtenirClient(idClient);
		compteEpargne.setNumeroCompte(genererNumero());
		compteEpargne.setDateOuverture(today());
		client.setCompteEpargne(compteEpargne);
		clientDao.save(client);

	}

	@Override
	public void AttribuerCompteCourantClient(long idClient, CompteCourant compteCourant) {
		// TODO Auto-generated method stub
		Client client = obtenirClient(idClient);
		compteCourant.setNumeroCompte(genererNumero());
		compteCourant.setDateOuverture(today());
		client.setCompteCourant(compteCourant);
		clientDao.save(client);

	}

	private long genererNumero() {
		long randomNumber = (long) (Math.random() * 1_000_000_000);
		return randomNumber;
	}

	private String today() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdfDate.format(date);
	}

	@Override
	public List<Compte> AfficherListeCompteClient(long idClient) {
		Client client = obtenirClient(idClient);
		List<Compte> listeCompte = new ArrayList<>();
		listeCompte.add(client.getCompteCourant());
		listeCompte.add(client.getCompteEpargne());
		return listeCompte;
	}

	@Override
	public void ModifierCompteEpargneClient(long idClient, CompteEpargne compteEpargneModif) {
		Client client = obtenirClient(idClient);
		client.setCompteEpargne(compteEpargneModif);
		clientDao.save(client);

	}

	@Override
	public void ModifierCompteCourantClient(long idClient, CompteCourant compteCourantModif) {
		Client client = obtenirClient(idClient);
		client.setCompteCourant(compteCourantModif);
		clientDao.save(client);

	}

	@Override
	public Compte AfficherCompteNumero(long numCompte) {
		return compteDAO.findOne(numCompte);
	}

}
