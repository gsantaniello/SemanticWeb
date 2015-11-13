package com.unisa.techadvisor.server;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.hp.hpl.jena.ontology.HasValueRestriction;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.DOAP;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.unisa.techadvisor.shared.Constants;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;
import com.unisa.techadvisor.shared.User;
import com.unisa.techadvisor.shared.UserVCARD;

public class InferenceClass implements Ontology {
	private static final String KNOWLEDGEBASE_RDF = "knowledgebase.rdf";
	private static final String USERS_RDF = "users.rdf";
	public static final String NS = "http://www.techadvisor.it/";
	private static Logger logger = Logger.getLogger("global");
	private ArrayList<String> languages = new ArrayList<String>();
	private final String langAbbrevation = "en";

	public static ArrayList<GenericKnowledgeClass> getInference(String email, String topic){
		return null;
	}



	public InferenceClass() {
		// TODO Auto-generated constructor stub
		Thesaurus thesaurus = new Thesaurus();
		thesaurus.loadThesaurus();
	}



	@Override
	public ArrayList<String> getLanguage(String email){
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream in = FileManager.get().open(USERS_RDF);
		base.read(in, NS);
		Resource user = base.getResource(NS + email);
		StmtIterator stiter = user.listProperties(UserVCARD.LANGUAGE);
		ArrayList<String> nodes = new ArrayList<String>();
		while (stiter.hasNext()) {
			Statement statement = stiter.next();
			nodes.add(statement.getString());
		}

		return nodes;
	}

	@Override
	public ArrayList<GenericKnowledgeClass> searchInference(String email, String str_topic, String str_category, String str_subcategory) {
		/**
		 * verrà utilizzata per prelevare il linguaggio 
		 */

		Model modelOfCategories = null;
		Resource resCat = null, resSubcat = null;
		str_topic = str_topic.toLowerCase();
		str_category = str_category.toLowerCase();
		str_subcategory = str_subcategory.toLowerCase();
		this.languages = getLanguage(email);


		if (str_category.equals("web")) {
			modelOfCategories = ModelFactory.createDefaultModel();
			InputStream in = FileManager.get().open("thesauri/web.rdf");
			modelOfCategories.read(in, uri_thes, "RDF/XML");
			resCat = getResourceFromTDB(modelOfCategories, str_category);
			resSubcat = getResourceFromTDB(modelOfCategories, str_subcategory);
		} else if (str_category.equals("mobile")) {
			modelOfCategories = ModelFactory.createDefaultModel();
			InputStream in = FileManager.get().open("thesauri/mobile.rdf");
			modelOfCategories.read(in, uri_thes, "RDF/XML");
			resCat = getResourceFromTDB(modelOfCategories, str_category);
			resSubcat = getResourceFromTDB(modelOfCategories, str_subcategory);
		}
		else	logger.info("Skip without category");


		Model modelOfTopics = ModelFactory.createDefaultModel();
		modelOfTopics.setNsPrefix("skos", SKOS.NS);
		modelOfTopics.setNsPrefix("ta", uri_cons);
		String partOfURI = str_topic.replaceAll("\\s", "");
		Resource resTopic = modelOfTopics.createResource(uri_cons + partOfURI, SKOS.Concept);

		OntModel knowBase = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream in = FileManager.get().open(KNOWLEDGEBASE_RDF);
		knowBase.read(in, uri, "RDF/XML");
		Property catProp = null;

		// verify whether a given sub-category was specified
		if (resSubcat != null) {
			catProp = knowBase.getProperty(DOAP.NS, "sub_category");
			return inferByTopicAndCategory(modelOfCategories, knowBase, resTopic, catProp, resSubcat);
		}

		// verify whether a given category was specified
		else if (resCat != null) {
			logger.info("Skip without sub-category");
			catProp = knowBase.getProperty(DOAP.NS, "category");

			return inferByTopicAndCategory(modelOfCategories, knowBase, resTopic, catProp, resCat);
		}
		else {
			logger.info("Show all frameworks and toolkits relative to topic and ides");

			return inferByTopic(modelOfCategories, knowBase, resTopic);
		}

	}


	private ArrayList<GenericKnowledgeClass> inferByTopic(Model modelOfCategories, OntModel knowBase, Resource res) {
		OntProperty uf = knowBase.getOntProperty(uri + "usedFor");
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF, knowBase);
		Resource resIDE = knowBase.getResource(uri + "IDE");
		ExtendedIterator<Individual> ideiter = knowBase.listIndividuals(resIDE);
		ArrayList<GenericKnowledgeClass> result = getResults(modelOfCategories, ideiter);
		HasValueRestriction hvr = inf.createHasValueRestriction(null, uf, res);
		ExtendedIterator<? extends OntResource> hvriter = hvr.listInstances();
		result.addAll(getResults(modelOfCategories, hvriter));

		orderByLanguage(result);

		return result;
	}


	private ArrayList<GenericKnowledgeClass> inferByTopicAndCategory(Model modelOfCategories, OntModel knowBase, Resource res,
			Property catProp, Resource resCat) {
		OntProperty uf = knowBase.getOntProperty(uri + "usedFor");
		OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF, knowBase);
		Resource resIDE = knowBase.getResource(uri + "IDE");
		HasValueRestriction hvr1 = inf.createHasValueRestriction(null, catProp, resCat);
		HasValueRestriction hvr2 = inf.createHasValueRestriction(null, RDF.type, resIDE);
		RDFList members = inf.createList(new RDFNode[] {hvr1, hvr2});
		IntersectionClass ic = inf.createIntersectionClass(null, members);
		ExtendedIterator<? extends OntResource> ideiter = ic.listInstances();
		ArrayList<GenericKnowledgeClass> result = getResults(modelOfCategories, ideiter);
		HasValueRestriction hvr3 = inf.createHasValueRestriction(null, uf, res);
		members = inf.createList(new RDFNode[] {hvr1, hvr3});
		ic = inf.createIntersectionClass(null, members);
		ExtendedIterator<? extends OntResource> iciter = ic.listInstances();
		result.addAll(getResults(modelOfCategories, iciter));

		orderByLanguage(result);

		return result;
	}



	/**
	 * Ordina i risultati in base ai linguaggi preferiti dell'utente!
	 * @param result
	 */
	private void orderByLanguage(ArrayList<GenericKnowledgeClass> result) {
		for (String lang : languages) {
			for (int i = 0; i < result.size(); i++) {
				GenericKnowledgeClass gkc = result.get(i);
				if (gkc.getLanguage().toLowerCase().contains(lang.toLowerCase()))
					result.add(0, result.remove(i));
			}
		}
	}


	private ArrayList<GenericKnowledgeClass> getResults(Model model, ExtendedIterator<? extends OntResource> results) {
		ArrayList<GenericKnowledgeClass> result = new ArrayList<GenericKnowledgeClass>();
		Model modelOfCategories;
		if (model == null)	modelOfCategories = ModelFactory.createDefaultModel();
		else	modelOfCategories = model;

		while (results.hasNext()) {
			GenericKnowledgeClass knw = new GenericKnowledgeClass();
			OntResource ontResource = results.next();
			StmtIterator props = ontResource.listProperties();
			while (props.hasNext()) {
				Statement stmt = props.nextStatement();
				Property p = stmt.getPredicate();
				if (p.getNameSpace().equals(DOAP.NS)) {
					RDFNode node = stmt.getObject();
					if (node.isLiteral()){
						switch (p.getLocalName()) {
						case "os":
							knw.setOs(node.toString());
							break;
						case "programming-language":
							String langs = knw.getLanguage();
							langs += node.toString() + ", ";
							knw.setLanguage(langs);
							break;
						case "description":
							knw.setDescription(node.toString());
							break;
						case "name":
							knw.setName(node.toString());
							break;					
						}
					}
					else {
						switch (p.getLocalName()) {
						case "homepage":
							knw.setUrl(node.toString());
							break;
						case "category":
							Resource res = node.asResource();
							int index = res.getURI().indexOf('#') + 1;
							String cat = res.getURI().substring(index);
							if (cat.startsWith("web"))
								modelOfCategories.read(FileManager.get().open("thesauri/web.rdf"), "RDF/XML");
							else
								modelOfCategories.read(FileManager.get().open("thesauri/mobile.rdf"), "RDF/XML");
							NodeIterator iter = modelOfCategories.listObjectsOfProperty(res, SKOS.prefLabel);
							Literal catlit = null;
							while (iter.hasNext()) {
								RDFNode item = iter.nextNode();
								if (item.isLiteral() && item.asLiteral().getLanguage().equals(langAbbrevation)) {
									catlit = item.asLiteral();
									break;
								}
							}
							knw.setCategory(modelOfCategories.getProperty(node.asResource(), SKOS.prefLabel).getString());
							break;
						case "sub_category":
							res = node.asResource();
							index = res.getURI().indexOf('#') + 1;
							cat = res.getURI().substring(index);
							if (cat.startsWith("web"))
								modelOfCategories.read(FileManager.get().open("thesauri/web.rdf"), "RDF/XML");
							else
								modelOfCategories.read(FileManager.get().open("thesauri/mobile.rdf"), "RDF/XML");

							iter = modelOfCategories.listObjectsOfProperty(res, SKOS.prefLabel);
							catlit = null;
							while (iter.hasNext()) {
								RDFNode item = iter.nextNode();
								if (item.isLiteral() && item.asLiteral().getLanguage().equals(langAbbrevation)) {
									catlit = item.asLiteral();
									break;
								}
							}
							knw.setSubcategory(catlit.getString());
							break;
						case "logo":
							knw.setLogoLink(node.toString());
							break;
						}
					}
				}
				if (p.getNameSpace().equals(RDF.getURI())) {
					RDFNode node = stmt.getObject();
					Resource res = node.asResource();
					if (res.getNameSpace() != null && res.getNameSpace().equals(uri)){
						knw.setType(res.getLocalName());
					}
				}
				if (p.hasURI(uri + "usedFor")) {
					RDFNode node = stmt.getObject();
					Model topicBase = ModelFactory.createDefaultModel();
					topicBase.read(FileManager.get().open("thesauri/topics.rdf"), uri_thes);
					String str = topicBase.getProperty(node.asResource(), SKOS.prefLabel).getString();
					knw.setTopic(str);
				}
			}
			String langs = knw.getLanguage();
			knw.setLanguage(langs.substring(0, langs.length() - 2));
			result.add(knw);
		}


		return result;
	}

	private Resource getResourceFromTDB(Model model, String category) {

		Dataset dataset = TDBFactory.createDataset("DBforSearch");

		/* Write in storage as Named Graph */
		dataset.begin(ReadWrite.WRITE);
		String urigraph = uri_thes + "graph";
		dataset.addNamedModel(urigraph, model);
		dataset.commit();

		// get resource relative to preference label
		String queryString = "PREFIX skos: <" + SKOS.NS + ">"
				+ "SELECT ?s FROM <" + urigraph + ">"
				+ "WHERE { ?s skos:prefLabel \"" + category + "\"@" + langAbbrevation
				+ " }";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect();
		while (results.hasNext()) {
			QuerySolution querySolution = results.next();
			RDFNode sol = querySolution.get("s");
			return sol.asResource();
		}

		// get resource relative to alternative label
		queryString = "PREFIX skos: <" + SKOS.NS + ">"
				+ "SELECT ?s FROM <" + urigraph + ">"
				+ "WHERE { ?s skos:altLabel \"" + category + "\"@" + langAbbrevation
				+ " }";
		query = QueryFactory.create(queryString);
		qexec = QueryExecutionFactory.create(query, model);
		results = qexec.execSelect();
		while (results.hasNext()) {
			QuerySolution querySolution = results.next();
			RDFNode sol = querySolution.get("s");
			return sol.asResource();
		}

		return null;
	}

	public static final String uri_cons = "http://www.thesaurus.ta.it/concepts#";
	public static final String uri_thes = "http://www.thesaurus.ta.it/";
	public static final String uri = "http://www.techadvisor.it/";


	@Override

	public String signUp(User user) throws UnsupportedEncodingException {

		String directory = "MyDB";
		Dataset dataset = TDBFactory.createDataset(directory);
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream in = FileManager.get().open("users.rdf");

		if (in == null) {
			System.out.println("Il file Non esiste");
			dataset.begin(ReadWrite.WRITE);
			Model model = dataset.getNamedModel("http://www.techadvisor.it/graph01");
			String insertString = insertUsr(user);
			System.out.println("Execute insert action " + insertString);
			UpdateAction.parseExecute(insertString, model);
			dataset.commit();
			try {
				model.write(new PrintWriter("users.rdf"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dataset.end();
			model.close();
			return Constants.REGISTRAZIONE_COMPLETATA;
		}
		
		base.read(in, "RDF/XML");
		dataset.begin(ReadWrite.READ);
		Model model = dataset.getNamedModel("http://www.techadvisor.it/graph01");
		dataset.end();

		String queryString = "SELECT ?email FROM <http://www.techadvisor.it/graph01> "
		+ "WHERE {<http://www.techadvisor.it/"
		+ user.getEmail()
		+ ">  <http://www.w3.org/2001/vcard-rdf/3.0#EMAIL> ?email}";
		
		Query query = QueryFactory.create(queryString);
		try {
			QueryExecution qexec = QueryExecutionFactory.create(query, model);
			ResultSet results = qexec.execSelect();
			//   System.out.println("results.hasNext(): "+results.hasNext());
			if (!results.hasNext()) {
				// email non presente effettuo l'insert
				System.out.println("dentro if");
				dataset.begin(ReadWrite.WRITE);
				model = dataset.getNamedModel("http://www.techadvisor.it/graph01");
				String insertString = insertUsr(user);
				System.out.println("Execute insert action " + insertString);
				UpdateAction.parseExecute(insertString, model);
				dataset.commit();
				model.write(new PrintWriter("users.rdf"));
				dataset.end();
				model.close();
				return Constants.REGISTRAZIONE_COMPLETATA;

			} else {
				// email gia' esistente
				return Constants.REGISTRAZIONE_FALLITA +" " + Constants.EMAIL_PRESENTE;
			}
		}
		catch (Exception e) {
			System.out.println(" ERROR ");
			e.printStackTrace();
			return Constants.REGISTRAZIONE_FALLITA;

		}

	}




	private String insertUsr(User user){
		String lang = "";
		for(String x : user.getLanguage()){
			lang += "vCard:LANGUAGE \""+x+"\";";
		}
		String insertString = "PREFIX vCard:<http://www.w3.org/2001/vcard-rdf/3.0#>"
				+ " INSERT DATA "
				+ "{<http://www.techadvisor.it/"
				+ user.getEmail()
				+ "> vCard:FN \""
				+ user.getName()+ "\";"
				+ "vCard:PASSWORD \""
				+ user.getPassword() + "\";"
				+ "vCard:EMAIL \""
				+ user.getEmail() + "\";"
				+lang
				+ " } ";
		return insertString;
	}

	@Override
	public String logIn(User user) {
		String directory = "MyDB";
		Dataset dataset = TDBFactory.createDataset(directory);
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream in = FileManager.get().open(USERS_RDF);
		if (in == null) {
			System.out.println("Il file Non esiste");

			return Constants.ERRORE_RDF_VUOTO;
		}
		base.read(in, "RDF/XML");

		/* Scrittura nello storage come Named Graph */
		dataset.begin(ReadWrite.WRITE);
		dataset.addNamedModel("http://www.techadvisor.it/graph01", base);
		dataset.commit();
		dataset.end();

		/* Lettura dallo storage */
		dataset.begin(ReadWrite.READ);
		Model model = dataset.getNamedModel("http://www.techadvisor.it/graph01");
		dataset.end();

		System.out.println("*******************************************************");
		model.write(System.out);
		System.out.println("*******************************************************");
		String email = user.getEmail();
		String queryString = "SELECT ?email ?pass FROM <http://www.techadvisor.it/graph01> "
				+ "WHERE {<http://www.techadvisor.it/"
				+ email
				+ ">  <http://www.w3.org/2001/vcard-rdf/3.0#EMAIL> ?email."
				+ " <http://www.techadvisor.it/"
				+ email
				+ ">  <http://www.w3.org/2001/vcard-rdf/3.0#PASSWORD> ?pass}";
		Query query = QueryFactory.create(queryString);
		System.out.println("Query login"+query);
		try {
			QueryExecution qexec = QueryExecutionFactory.create(query, model);
			ResultSet results = qexec.execSelect();
			if (!results.hasNext()) {
				return Constants.LOGIN_FALLITA; // query fallita perch� l'email � sbagliata (non
				// presente nel file)
			} else {
				// email giusta e bisogna controllare la password inserita
				// dall'user
				QuerySolution soln = results.nextSolution();
				RDFNode objPass = soln.get("pass");
				System.out.println("Password controllata"+objPass);
				System.out.println("Password dell'user"+user.getPassword());
				if (!user.getPassword().equals(objPass.toString())) {
					return Constants.LOGIN_FALLITA;
				}
			}
		} catch (Exception e) {
			System.out.println("****** ERROR ******");
			e.printStackTrace();
		}
		return Constants.LOGIN_COMPLETATA;
	}



	@Override
	public void insertLanguage(User user, ArrayList<String> lang_list) {
		// TODO Auto-generated method stub
		Model model = ModelFactory.createDefaultModel();

		String directory = "MyDB";
		Dataset dataset = TDBFactory.createDataset(directory);
		InputStream in = FileManager.get().open(USERS_RDF);
		model.read(in, "RDF/XML");
		dataset.begin(ReadWrite.READ);
		Model TDBmodel = dataset.getNamedModel("http://www.techadvisor.it/graph01");
		dataset.end();

		String queryString = "SELECT ?email FROM <http://www.techadvisor.it/graph01> "
				+ "WHERE {<http://www.techadvisor.it/"
				+ user.getEmail()  //mettere l'email
				+ ">  <http://www.w3.org/2001/vcard-rdf/3.0#EMAIL> ?email}";



		Query query = QueryFactory.create(queryString);
		//		System.out.println(query);
		try {
			QueryExecution qexec = QueryExecutionFactory.create(query,TDBmodel);
			ResultSet results = qexec.execSelect();
			if (results.hasNext()) {
				// email non presente effettuo l'insert
				dataset.begin(ReadWrite.WRITE);
				dataset.addNamedModel("http://www.techadvisor.it/graph01", model);
				TDBmodel =dataset.getNamedModel("http://www.techadvisor.it/graph01") ;
				dataset.commit();

				String insertString = insertLng(user, lang_list);

				//				System.out.println("Execute insert action " + insertString);
				UpdateAction.parseExecute(insertString, TDBmodel);

				TDBmodel.write(new PrintWriter(USERS_RDF));
				dataset.end();
				logger.severe("insert "+insertString+" complete");

			} else {
				// email gi� esistente
				logger.severe("Failed insert language");
			}
		}
		catch (Exception e) {
			logger.severe("**** ERROR: Occurence problem in INSERT new languages");
			e.printStackTrace();
		}

	}

	private String insertLng(User user, ArrayList<String> lang_list){
		String lang = "";
		for(String x : lang_list){
			lang += "vCard:LANGUAGE \""+x+"\";";
		}
		String insertString = "PREFIX vCard:<http://www.w3.org/2001/vcard-rdf/3.0#>"
				+ " INSERT DATA "
				+ "{<http://www.techadvisor.it/"
				+ user.getEmail()+">"
				+lang 
				+ " } ";
		return insertString;
	}


	@Override
	public void removeLanguage(User user, ArrayList<String> lang_list) {
		// TODO Auto-generated method stub
		Model model = ModelFactory.createDefaultModel();

		String directory = "MyDB";
		Dataset dataset = TDBFactory.createDataset(directory);
		InputStream in = FileManager.get().open(USERS_RDF);
		model.read(in, "RDF/XML");
		dataset.begin(ReadWrite.READ);
		Model TDBmodel = dataset.getNamedModel("http://www.techadvisor.it/graph01");
		dataset.end();

		String queryString = "SELECT ?email FROM <http://www.techadvisor.it/graph01> "
				+ "WHERE {<http://www.techadvisor.it/"
				+ user.getEmail()  //mettere l'email
				+ ">  <http://www.w3.org/2001/vcard-rdf/3.0#EMAIL> ?email}";



		Query query = QueryFactory.create(queryString);
		//		System.out.println(query);
		try {
			QueryExecution qexec = QueryExecutionFactory.create(query,TDBmodel);
			ResultSet results = qexec.execSelect();
			if (results.hasNext()) {
				// email non presente effettuo l'insert
				dataset.begin(ReadWrite.WRITE);
				dataset.addNamedModel("http://www.techadvisor.it/graph01", model);
				TDBmodel =dataset.getNamedModel("http://www.techadvisor.it/graph01") ;
				dataset.commit();

				String insertString = removeLng(user, lang_list);

				//				System.out.println("Execute insert action " + insertString);
				UpdateAction.parseExecute(insertString, TDBmodel);

				TDBmodel.write(new PrintWriter(USERS_RDF));
				dataset.end();
				logger.severe("Complete");

			} else {
				// email gi� esistente
				logger.severe("Failed");
			}
		}
		catch (Exception e) {
			logger.severe("**** ERROR: Occurence problem in INSERT new languages");
			e.printStackTrace();
		}
	}

	private  String removeLng(User user, ArrayList<String> lang_list){
		String lang = "";
		for(String x : lang_list){
			lang += "vCard:LANGUAGE \""+x+"\";";
		}
		String insertString = "PREFIX vCard:<http://www.w3.org/2001/vcard-rdf/3.0#>"
				+ " DELETE DATA "
				+ "{<http://www.techadvisor.it/"
				+ user.getEmail()+">"
				+lang 
				+ " } ";
		return insertString;
	}

	@Override
	public String getName(String email) {
		// TODO Auto-generated method stub
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream in = FileManager.get().open(USERS_RDF);
		base.read(in, NS);
		Resource user = base.getResource(NS + email);
		StmtIterator stiter = user.listProperties(UserVCARD.FN);
		String node ="";
		while (stiter.hasNext()) {
			Statement statement = stiter.next();
			node+=statement.getString();
		}
		return node;
	}
}