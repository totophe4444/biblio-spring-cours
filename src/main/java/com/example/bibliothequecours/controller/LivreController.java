package com.example.bibliothequecours.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.service.LivreServiceItf;

import jakarta.servlet.ServletContext;


@Controller
public class LivreController {
	@Autowired
	private  LivreServiceItf  livreService;
	
	@Autowired
	private ServletContext context;   

	@Value("${dir.images}")
	private String imageDir;

	@RequestMapping("/accueil")
	public  String  accueil()  {
		System.out.println("==== /accueils ====");
		return  "accueil";
	}
	@RequestMapping("/afficher-livres")
	public String administrer(Model model) {
		System.out.println("==== /afficher-livres ====");
		List<Livre> livreList = livreService.getAllLivre();
		System.out.println("LivreList: " + livreList);
		model.addAttribute("livreList", livreList);
		return "catalogue";
	}
	@RequestMapping("/creer-livre")
	public  String  creerLivre(Model  model)  {
		System.out.println("==== /creer-livre ====");
		model.addAttribute("titre",  "Créer  livre");
		return  "creer-livre";
	}
	@RequestMapping("/creer-livre-validation")
	public String creerValidationLivre(String titre, int nb, int nbPages, String descr, MultipartFile image) {
		System.out.println("==== /creer-livre-validation ====");
		System.out.println("/creer-livre-validation titre=" + titre + " nb=" + nb + " nbPages=" + nbPages + " descr=" + descr);
		System.out.println("image=" + image);
		if(!image.isEmpty()) {
			String couverture = image.getOriginalFilename();
			System.out.println("couverture=" + couverture);

			String absolutePath = context.getRealPath("resources/images");
			System.out.println("creerValidationLivre t- absolutePath=" + absolutePath);
			System.out.println(context.getRealPath("/"));
			String pathFile = imageDir + couverture;
			System.out.println("pathFile=" + pathFile);
			try {
				image.transferTo(new File(pathFile));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			livreService.creerLivre(new Livre(titre, nb, nbPages, couverture, descr));
		}
		return "redirect:/afficher-livres";
	}

}
