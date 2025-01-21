package com.example.bibliothequecours.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue
	private Long id;
	private String login;
	private String passwdHash;
	private String email;
	private String role;
	@OneToMany
	private List<Emprunt> emprunterLivreList;
	
	public Utilisateur() {}
	public Utilisateur(String login, String passwdHash, String email, String role) {
		this.login = login;
		this.passwdHash = passwdHash;
		this.email = email;
		this.role = role;
		emprunterLivreList = new ArrayList<Emprunt>();
	}
	@Override
	public String toString() {
		return "Utilisateur [login=" + login + ", id=" + id + ", passwdHash=" + passwdHash + ", email=" + email + ", role=" + role +"]";
	}
	public void emprunterLivre(Emprunt emprunt) {
		emprunterLivreList.add(emprunt);
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswdHash() {
		return passwdHash;
	}
	public void setPasswdHash(String passwdHash) {
		this.passwdHash = passwdHash;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Emprunt> getEmprunterLivreList() {
		return emprunterLivreList;
	}
	public void setEmprunterLivreList(List<Emprunt> emprunterLivreList) {
		this.emprunterLivreList = emprunterLivreList;
	}
}
