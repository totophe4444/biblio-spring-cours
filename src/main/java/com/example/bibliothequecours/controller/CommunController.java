package com.example.bibliothequecours.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommunController {

	@RequestMapping("/")
	public  String  entree()  {
		System.out.println("==== / ====");
		return  "redirect:/accueil";
	}
	@RequestMapping("/mentions-legales")
	public String retournerEmprunt() {
		return "mentions-legales";
	}
	@RequestMapping("/cookies")
	public String cookies() {
		return "cookies";
	}
	@RequestMapping("/donnees-personnelles")
	public String donneesPersonnelles() {
		return "donnees-personnelles";
	}
}
