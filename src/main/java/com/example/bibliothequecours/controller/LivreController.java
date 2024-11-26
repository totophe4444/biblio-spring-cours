package com.example.bibliothequecours.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.bibliothequecours.entity.Livre;
import com.example.bibliothequecours.service.LivreServiceItf;


@Controller
public class LivreController {
	@Autowired
	private  LivreServiceItf  livreService;
	
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
	
}
