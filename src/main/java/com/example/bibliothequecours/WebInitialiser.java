package com.example.bibliothequecours;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class WebInitialiser extends SpringBootServletInitializer {
@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BibliothequecoursApplication.class);
	}
}
