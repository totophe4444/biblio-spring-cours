package com.example.bibliothequecours.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.service.LivreServiceItf;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
/**
* Controleur livre. Point d'entré des requêtes client.
 * @author Tophe
 */

@Controller
public class LivreController {
	/**
	 * injection du composant livreService
	 */
	@Autowired
	private  LivreServiceItf  livreService;

	@Autowired
	private ServletContext context;   

	//@Value("${dir.images}")
	private String imageDir = "WEB-INF/classes/static/images/";
	/**
	 * route /accueil
	 * Appel la page statique d'accueil
	 * 
	 */
	@RequestMapping("/accueil")
	public  String  accueil()  {
		System.out.println("==== /accueils ====");
		return  "accueil";
	}
	/**
	 * route /afficher-livres
	 * Appel la page catalogue en lui fournissant la liste
	 * des livres afin de les afficher
	 * @param model
	 * contient la liste des livres 
	 * constituant le catalogue afin de les afficher
	 * @return catalogue
	 * appel la page catalogue pour afficher les livres
	 */
	@RequestMapping("/afficher-livres")
	public String administrer(Model model) {
		System.out.println("==== /afficher-livres ====");
		List<Livre> livreList = livreService.getAllLivre();
		System.out.println("LivreList: " + livreList);
		model.addAttribute("livreList", livreList);
		return "catalogue";
	}
	@RequestMapping("/creer-livre")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  String  creerLivre(Model  model)  {
		System.out.println("==== /creer-livre ====");
		model.addAttribute("titre",  "Créer  livre");
		return  "creer-livre";
	}
	@RequestMapping("/creer-livre-validation")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String creerValidationLivre(String titre, int nb, int nbPages, String descr, MultipartFile image) throws Exception {
		System.out.println("==== /creer-livre-validation ====");
		System.out.println("/creer-livre-validation titre=" + titre + " nb=" + nb + " nbPages=" + nbPages + " descr=" + descr);
		System.out.println("image=" + image);
		if(!image.isEmpty()) {
			String couverture = image.getOriginalFilename();
			System.out.println("couverture=" + couverture);
			String absolutePath = context.getRealPath(imageDir) + couverture;
			System.out.println("creerValidationLivre t- absolutePath=" + absolutePath);
			try {
				image.transferTo(new File(absolutePath));
			} catch (IllegalStateException | IOException e) {
				throw new Exception("Impossible d'enregistrer l'image sur le serveur.");
			}
			livreService.creerLivre(new Livre(titre, nb, nbPages, couverture, descr));
		}
		return "redirect:/afficher-livres";
	}
	@RequestMapping("/afficher-livre/{id}")
	public  String  afficherLivre(@PathVariable  Long  id,  Model  model)  {
		System.out.println("====  /afficher-livre  ====");
		System.out.println("id="  +  id);
		Livre  livre  =  livreService.getLivreById(id);
		System.out.println("Livre="  +  livre);
		model.addAttribute("livre",  livre);
		model.addAttribute("titre",  livre.getTitre());
		return  "detail";
	}
    @RequestMapping("/reserver/{id}")
    @PreAuthorize("hasRole('ROLE_ABONNE')")
    public String emprunterLivre(@PathVariable Long id, Model model, HttpServletRequest request) {
    	System.out.println("==== /reserver/" + id + " ====");
    	List<Long> livreEmprunterListId = (List<Long>) request.getSession().getAttribute("livreEmprunterListId");
    	if(livreEmprunterListId == null) {
    		livreEmprunterListId = new ArrayList<>();
    	}
    	// si la liste ne contient pas déjà le livre
    	if(!livreEmprunterListId.contains(id)) {
    		livreEmprunterListId.add(id);
    		livreService.decrementerNbExemplaireLivre(id);
    	}
    	request.getSession().setAttribute("livreEmprunterListId", livreEmprunterListId);
    	System.out.println("livreEmprunterListId=" + livreEmprunterListId);
    	return "redirect:/afficher-panier";
    }
    @RequestMapping("/afficher-panier")
    @PreAuthorize("hasRole('ROLE_ABONNE')")
	public String afficherPanier(Model model, HttpServletRequest request) {
		System.out.println("==== /afficher-panier ====");
		List<Long> livreEmprunterListId = (List<Long>) request.getSession().getAttribute("livreEmprunterListId");
		System.out.println("livreEmprunterListId=" + livreEmprunterListId);
		if(livreEmprunterListId != null) {
			List<Livre> livreEmprunterList = livreService.getLivreEmprunterListParLivreIdList(livreEmprunterListId);
			model.addAttribute("livreEmprunterList", livreEmprunterList);
		}
		else System.out.println("Pas de livre emprunté");
		model.addAttribute("titre", "Réservation livre");
		return "panier";
	}
    @RequestMapping("/supprimer-panier/{id}")
    @PreAuthorize("hasRole('ROLE_ABONNE')")
    public String supprimerLivrePanier(@PathVariable Long id, Model model, HttpServletRequest request) {
    	System.out.println("==== /supprimer-panier ====");
    	List<Long> livreEmprunterListId = (List<Long>) request.getSession().getAttribute("livreEmprunterListId");
    	System.out.println("livreEmprunterListId=" + livreEmprunterListId);
    	livreEmprunterListId.remove(id);
    	livreService.incrementerNbExemplaireLivre(id);
    	request.getSession().setAttribute("livreEmprunterListId", livreEmprunterListId);
    	System.out.println("livreEmprunterListId=" + livreEmprunterListId);
    	return "redirect:/afficher-panier";
    }

}
