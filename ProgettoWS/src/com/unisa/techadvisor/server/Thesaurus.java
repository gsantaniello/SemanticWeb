package com.unisa.techadvisor.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Thesaurus {


	public static final Model model = ModelFactory.createDefaultModel();
	public static final String uri_cons = "http://www.thesaurus.ta.it/concepts#";
	public static final String uri = "http://www.thesaurus.ta.it/";
	
	public void loadThesaurus(){
		model.setNsPrefix("skos", SKOS.getURI());
		model.setNsPrefix("ta", uri_cons);
		Thesaurus t = new Thesaurus();
		t.createWeb();
		model.removeAll();
		t.createMobile();
		model.removeAll();
		t.createTopics();
	}
	
	private void createTopics() {
		Resource scheme = model.createResource(uri + "TopicsThesaurus",SKOS.ConceptScheme);

		Resource music = model.createResource(uri_cons + "music", SKOS.Concept);
		music.addProperty(SKOS.prefLabel, "music", "en");
		music.addProperty(SKOS.prefLabel, "musica", "it");
		music.addProperty(SKOS.inScheme, scheme);

		Resource ecommerce = model.createResource(uri_cons + "e-commerce", SKOS.Concept);
		ecommerce.addProperty(SKOS.altLabel, "ecommerce", "en");
		ecommerce.addProperty(SKOS.altLabel, "e commerce", "en");
		ecommerce.addProperty(SKOS.prefLabel, "e-commerce", "en");
		ecommerce.addProperty(SKOS.prefLabel, "e-commerce", "it");
		ecommerce.addProperty(SKOS.inScheme, scheme);

		Resource semanticweb = model.createResource(uri_cons + "semanticweb", SKOS.Concept);
		semanticweb.addProperty(SKOS.prefLabel, "semantic web", "en");
		semanticweb.addProperty(SKOS.prefLabel, "web semantico", "it");
		semanticweb.addProperty(SKOS.inScheme, scheme);

		try {
			model.write(new FileOutputStream("thesauri/topics.rdf"), "RDF/XML");
			model.write(new FileOutputStream("thesauri/topics.ttl"), "TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createMobile() {
		Resource scheme = model.createResource(uri + "MobileThesaurus",
				SKOS.ConceptScheme);

		Resource ios = model.createResource(uri_cons + "ios", SKOS.Concept);
		ios.addProperty(SKOS.altLabel, "iphone os", "en");
		ios.addProperty(SKOS.prefLabel, "ios", "en");
		ios.addProperty(SKOS.prefLabel, "ios", "it");
		ios.addProperty(SKOS.inScheme, scheme);

		Resource wp = model.createResource(uri_cons + "windowsphone",
				SKOS.Concept);
		wp.addProperty(SKOS.altLabel, "wp", "en");
		wp.addProperty(SKOS.prefLabel, "windows phone", "en");
		wp.addProperty(SKOS.prefLabel, "windows phone", "it");
		wp.addProperty(SKOS.inScheme, scheme);

		Resource android = model.createResource(uri_cons + "android",
				SKOS.Concept);
		android.addProperty(SKOS.prefLabel, "android", "en");
		android.addProperty(SKOS.prefLabel, "android", "it");
		android.addProperty(SKOS.inScheme, scheme);

		Resource mobile = model.createResource(uri_cons + "mobile",
				SKOS.Concept);
		mobile.addProperty(SKOS.prefLabel, "mobile", "en");
		mobile.addProperty(SKOS.altLabel, "mobile app", "en");
		mobile.addProperty(SKOS.altLabel, "mobile application", "en");
		mobile.addProperty(SKOS.altLabel, "applicazione mobile", "it");

		mobile.addProperty(SKOS.narrower, android);
		mobile.addProperty(SKOS.narrower, ios);
		mobile.addProperty(SKOS.narrower, wp);
		android.addProperty(SKOS.broader, mobile);
		ios.addProperty(SKOS.broader, mobile);
		wp.addProperty(SKOS.broader, mobile);

		scheme.addProperty(SKOS.hasTopConcept, mobile);
		mobile.addProperty(SKOS.topConceptOf, scheme);

		try {
			model.write(new FileOutputStream("thesauri/mobile.rdf"), "RDF/XML");
			model.write(new FileOutputStream("thesauri/mobile.ttl"), "TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createWeb() {
		Resource scheme = model.createResource(uri + "WebThesaurus",
				SKOS.ConceptScheme);

		Resource web = model.createResource(uri_cons + "web", SKOS.Concept);
		web.addProperty(SKOS.prefLabel, "web", "en");
		web.addProperty(SKOS.prefLabel, "web", "it");
		web.addProperty(SKOS.altLabel, "world wide web", "en");
		web.addProperty(SKOS.altLabel, "www", "en");

		Resource website = model.createResource(uri_cons + "website",
				SKOS.Concept);
		website.addProperty(SKOS.prefLabel, "site", "en");
		website.addProperty(SKOS.prefLabel, "sito web", "it");
		website.addProperty(SKOS.altLabel, "website", "en");
		website.addProperty(SKOS.altLabel, "web site", "en");
		website.addProperty(SKOS.altLabel, "web page", "en");
		website.addProperty(SKOS.altLabel, "webpage", "en");
		website.addProperty(SKOS.altLabel, "pagina web", "it");
		website.addProperty(SKOS.inScheme, scheme);

		Resource webapp = model.createResource(uri_cons + "webapplication",
				SKOS.Concept);
		webapp.addProperty(SKOS.prefLabel, "web application", "en");
		webapp.addProperty(SKOS.prefLabel, "applicazione web", "it");
		webapp.addProperty(SKOS.altLabel, "web app", "en");
		webapp.addProperty(SKOS.altLabel, "webapp", "en");
		webapp.addProperty(SKOS.altLabel, "web app", "it");
		webapp.addProperty(SKOS.altLabel, "webapp", "it");
		webapp.addProperty(SKOS.inScheme, scheme);

		web.addProperty(SKOS.narrower, website);
		web.addProperty(SKOS.narrower, webapp);
		website.addProperty(SKOS.broader, web);
		webapp.addProperty(SKOS.broader, web);

		scheme.addProperty(SKOS.hasTopConcept, web);
		web.addProperty(SKOS.topConceptOf, scheme);

		try {
			model.write(new FileOutputStream("thesauri/web.rdf"), "RDF/XML");
			model.write(new FileOutputStream("thesauri/web.ttl"), "TURTLE");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
