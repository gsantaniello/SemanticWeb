package com.unisa.techadvisor.server;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.sparql.vocabulary.DOAP;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;

public class KnowledgeBased {

	private static final String KNOWLEDGEBASE_RDF = "knowledgebase.rdf";
	//	public static String DOAP_BASE = "http://usefulinc.com/ns/doap#";
	public static String MY_DOAP_BASE = "http://www.techadvisor.it/";
	public static final String uri_thes = "http://www.thesaurus.ta.it/";
	private static Logger logger = Logger.getLogger("global");
	private static String langAbbrevation = "en";

	public static void loadKnowledge() throws UnsupportedEncodingException{
		// DOAP model
		OntModel doapModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		doapModel.read("http://usefulinc.com/ns/doap");

		// Ontologic model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
		model.createOntology(MY_DOAP_BASE);

		// Extend DOAP model
		model.createOntology(MY_DOAP_BASE).addImport(new ResourceImpl("http://usefulinc.com/ns/doap"));

		// Model and DOAP prefix
		model.setNsPrefix("doap", DOAP.NS);
		model.setNsPrefix("ta", MY_DOAP_BASE);

		// create usedFor property
		OntProperty isUsedFor = model.createObjectProperty(MY_DOAP_BASE + "usedFor");
		isUsedFor.addLabel("isUsedFor", "en");
		isUsedFor.addComment("stress the use of a framework, IDE and toolkit relative to a given topic", "en");

		// create Framework class
		OntClass frameworkClass = model.createClass(MY_DOAP_BASE + "Framework");
		frameworkClass.setLabel("Framework", "en");
		isUsedFor.addRange(frameworkClass);

		// create IDE class
		OntClass ideClass = model.createClass(MY_DOAP_BASE + "IDE");
		ideClass.setLabel("IDE", "en");
		isUsedFor.addRange(ideClass);

		// create Toolkit class
		OntClass toolClass = model.createClass(MY_DOAP_BASE + "Toolkit");
		toolClass.setLabel("Toolkit", "en");
		isUsedFor.addRange(toolClass);

		//MUSIC FRAMEWORK
		String logoLink = "http://explodingart.com/jmusic/jMusic.gif";
		String name= "jMusic";
		String language = "Java";
		String url = "http://explodingart.com/jmusic/";
		String description = "As a library of classes for generating and manipulating music, jMusic provides a "
				+ "solid framework for musical composition in Java. jMusic supports music composition with its "
				+ "music data structure based upon note events, and methods for working with that musical data. "
				+ "jMusic can read and write MIDI files, its own .jm files, and audio files.";
		String topic = "music";
		String category = "mobile";
		String subcategory = "android";
		String type = "Framework";

		GenericKnowledgeClass knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);


		logoLink = "http://www.softsynth.com/images/softsynth_logo.png";
		name = "JSyn";
		language = "Java";
		url = "http://www.softsynth.com/jsyn/";
		description = "Real-time modular audio synthesis API for Java. JSyn has over 100 unit generators including "
				+ "band-limited oscillators, envelopes, filters, effects, etc. JSyn is open source on GitHub.";
		topic = "music";
		category = "mobile";
		subcategory = "android";
		type = "Framework";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);


		logoLink = "http://jmsldev.com/wp-content/uploads/2013/12/logoJMLS11.jpg";
		name = "JMSL - Java Music Specification Language";
		language = "Java";
		url = "http://www.algomusic.com/jmsl/index.html";
		description = "JMSL is a Java API for experiments in interactive performance, composition, and intelligent instrument "
				+ "design. With JMSL, the composer/programmer can create stand-alone musical applications or deploy applets "
				+ "on the web. JMSL supports JSyn, MidiShare, MidiPort, and JavaSound.";
		topic = "music";
		category = "mobile";
		subcategory = "android";
		type="Framework";				//Dovr‡ essere mappato con SKOS
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);

		//E-COMMERCE FRAMEWORK
		logoLink = "http://www.vipereg.com/wp-content/uploads/2012/09/logo-magento.png";
		name = "Magento";
		language = "PHP";
		url = "http://magento.com/";
		description = "Magento provides wide range of functionality and plugins to make your e-commerce site easily. "
				+ "Based on PHP, magento provides many functionalities like mobile templates , multiple store , "
				+ "payment gateway integration modules , easy layout changes etc .To create a sample magento app "
				+ "on openshift cloud server ";
		topic = "e-commerce";
		category = "web";
		subcategory = "web application";
		type="Framework";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);


		logoLink = "http://www.comune.sanpellegrinoterme.bg.it/wp-content/uploads/2014/03/wordpress.png";
		name = "WordPress";
		language = "-";
		url = "https://it.wordpress.com/";
		description = "Wordpress is also a very good tool to build your e-commerce site. It provides several nice "
				+ "user interfaces and templates.To start building your site , you can take help from this link "
				+ "(http://code.tutsplus.com/tutorials/how-to-build-an-e-commerce-website-using-wordpress-part-1--wp-23370)";
		topic = "e-commerce";
		category = "web";
		subcategory = "site";
		type="Tool";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);


		logoLink = "http://alliance.rice.edu/uploadedImages/Events/2013_IT_and_Web_Venture_Forum(1)/broadleaf.png";
		name = "BroadLeaf";
		language = "-";
		url = "http://www.broadleaknwommerce.org/";
		description = "BroadLeaf Commerce is an open source eCommerce platform built on java and spring framework. "
				+ "It is using all the popular java based technologies like hibernate , spring and solr.You can setup"
				+ " your own site , by this well explained tutorial (http://docs.broadleaknwommerce.org/current/Getting-Started.html).";
		topic = "e-commerce";
		category = "web";
		subcategory = "web application";
		type="Framework";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);



		//E-COMMERCE TOOL
		logoLink = "http://profprojects.com/images/cms/jadasite.png";
		name = "JadaSite";
		language = "Java";
		url = "http://www.jadasite.com/";
		description = "JadaSite is an open sourced content management and e-commerce system. Our philosophy is to ensure that JadaSite"
				+ " is feature-rich, easy to use and maintain, and does not require an I.T. Team for support and to make changes to the"
				+ " system. We use simple tools and frameworks to ensure future changes by other parties outside of our core development"
				+ " team can be done easily.";
		topic = "e-commerce";
		category = "web";
		subcategory = "web application";
		type="Tool";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, toolClass, knw);


		logoLink = "http://www.softslate.com/forums/templates/subSilver/images/logo_phpBB.gif";
		name = "SoftSlate";
		language = "Java";
		url = "http://www.jadasite.com/";
		description = "SoftSlate Commerce is a full-featured, high-performance, open-source Java shopping cart that powers dozens of "
				+ "ecommerce websites. SoftSlate Commerce is designed to be easily extended and customized. And if you need help building "
				+ "a solution, SoftSlate offers custom development and support services.";
		category = "web";
		subcategory = "web application";
		type="Tool";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, toolClass, knw);


		//IDE
		logoLink = "http://cdn1.raywenderlich.com/wp-content/uploads/2013/11/opening_studio_3.png";
		name = "Android Studio";
		language = "Java";
		url = "http://developer.android.com/tools/studio/index.html";
		description = "Android Studio is the official IDE for Android application development, based on IntelliJ IDEA.";
		category = "mobile";
		subcategory = "android";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);

		
		logoLink = "http://blog.javabit.net/wp-content/uploads/2009/11/gwt-logo.png";
		name = "Google Web Toolkit";
		language = "Java";
		url = "http://www.gwtproject.org/";
		description = "GWT is a development toolkit for building and optimizing complex browser-based applications. Its goal"
				+ " is to enable productive development of high-performance web applications without the developer having to "
				+ "be an expert in browser quirks, XMLHttpRequest, and JavaScript. GWT is used by many products at Google, "
				+ "including AdWords, AdSense, Flights, Hotel Finder, Offers, Wallet, Blogger. Itís opn source, completely free,"
				+ " and used by thousands of developers around the world.";
		category = "web";
		subcategory = "web application";
		type="Tool";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, toolClass, knw);

		logoLink = "https://upload.wikimedia.org/wikipedia/fr/d/da/Logo_xcode.png";
		name = "Xcode";
		language = "Objective-C";
		url = "https://developer.apple.com/xcode/ide/";
		description = "The Xcode IDE is at the center of the Apple development experience. Tightly integrated with the Cocoa and Cocoa"
				+ " Touch frameworks, Xcode is an incredibly productive environment for building amazing apps for Mac, iPhone, and iPad.";
		category = "mobile";
		subcategory = "ios";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);


		logoLink = "http://www.ilsoftware.it/public/shots/visualstudioexpress2012_0213.jpg";
		name = "Visual Studio Express 2012";
		language = "-";
		url = "https://msdn.microsoft.com/en-us/library/windows/apps/jj714071(v=vs.105).aspx";
		description = "Tool to build Windows Phone 8 apps. Itís free and includes the Windows Phone SDK 8.0, Blend for Visual Studio, and "
				+ "project templates. For more info, see Windows Phone 8 SDK tools."
				+ "https://msdn.microsoft.com/en-us/library/windows/apps/ff402523(v=vs.105).aspx";
		category = "mobile";
		subcategory = "windows phone";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);


		logoLink = "http://blogs.msdn.com/cfs-filesystemfile.ashx/__key/communityserver-blogs-components-weblogfiles/00-00-01-10-92-metablogapi/7853.VisualStudioLogo_5F00_49DD9B64.jpg";
		name = "Visual Web Developer";
		language = "-";
		url = "https://msdn.microsoft.com/en-us/library/ms178093(v=vs.80).aspx";
		description = "Visual Web Developer is basically a stripped down version of Visual Studio, with only the web development tools in place. "
				+ "It has the same great project management and database tools that VS has, only it costs $299 less. This app is aimed at beginners,"
				+ " So you can get starter kits along with it, and there's a great Beginning Developer Learning Center online.";
		category = "web";
		subcategory = "site";
		String os = "Windows";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);

		logoLink = "http://www.nadav-sharon.ch/images/informatica/phpDesigner.jpg";
		name = "phpDesigner";
		language = "-";
		url = "http://www.mpsoftware.dk/phpdesigner.php";
		description = "www.phpeditors.com gave phpDesigner a 5 star rating, saying it was a \"super fast PHP IDE with many features.\" To back this up,"
				+ " phpDesigner offers support for PHP debugging and profiling; It also supports all standard web languages, and offers TortoiseSVN "
				+ "support, and live error detection for PHP, HTML, and CSS. A code snippets library and built-in PHP manual for beginners only "
				+ "sweeten this pot.";
		category = "web";
		subcategory = "site";
		os="Windows";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);


		logoLink = "https://cdn.tutsplus.com/net/uploads/2013/07/msbuild-retina-preview.jpg";
		name = "Visual Studio";
		language = "-";
		url = "https://www.visualstudio.com/en-us";
		description = "Visual Studio is basically the industry standard for writing .NET code, but it's also good for web development. It's "
				+ "strong point is ASP .NET (obviously), but it's pretty good with traditional web languages, offering extensive \"IntelliSense\" "
				+ "(code completion) for HTML, CSS and JavaScript. There's no native PHP support, but there is a plug-in of sorts that can add "
				+ "it (Rumour has it that VS 2010 will support PHP). The code debugger is amazing if you're using ASP .NET, and now you can debug "
				+ "JavaScript as well. Visual Studio has so many more powerful features; it's a good product for anyone who uses ASP .NET.";
		category = "web";
		subcategory = "site";
		os="Windows";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);


		logoLink = "http://cdn.appstorm.net/mac.appstorm.net/authors/jacobwilson/coda-icon.jpg";
		name = "Coda";
		language = "-";
		url = "http://www.panic.com/coda/";
		description = "Coda may be the best IDE for the Mac, and it's one of the few IDEs created with the sole intent of web development. Its "
				+ "concept of sites is pretty neat, and you can remotely edit files on almost any server. With the ability to collaborate on "
				+ "file with anyone in the world, and a clips repository for frequently typed snippets, Coda really looks promising. By the time"
				+ " you have a Javascript console, Dom inspector, CSS editor, and built-in terminal, you're looking at an application that would"
				+ " make me consider switching to a Mac.";
		category = "web";
		subcategory = "site";
		os="Mac";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);


		logoLink = "http://static.tonyarnold.com/cssedit_icon-1306152105.png";
		name = "CSSEdit";
		language = "-";
		url = "http://macrabbit.com/espresso/";
		description = "An IDE for CSS? That's right, and an amazingly slick one at that. With built in validation, \"Milestones\" (Code versioning),"
				+ " and a really neat selector builder, writing CSS is a snap with CSSEdit. And then there are tools that let you inspect other "
				+ "websites to see how it's done. This tool is great for both CSS newbies and veterans.";
		category = "web";
		subcategory = "site";
		os="Mac";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);

		logoLink = "http://cdn1.download.html.it/wp-content/uploads/2013/02/Snap2648-150x130.jpg";
		name = "Bluefish";
		language = "-";
		url = "http://bluefish.openoffice.nl/index.html";
		description = "Bluefish aims to be a light and clean IDE for linux users. It offers project support, as well as the ability to access remote "
				+ "files on almost any server. It has very robust search and replace, code completion for HTML and XML, and a function reference "
				+ "browser for PHP, CSS, Python, and HTML.";
		category = "web";
		subcategory = "site";
		os="Linux";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);


		logoLink = "http://phpgranada.com/images/enlaces/netbeans.png";
		name = "Netbeans";
		language = "-";
		url = "https://netbeans.org/";
		description = "This open-source IDE is a sweet deal: whether you're developing in PHP, Ruby on Rails, JavaScript, or something else, you'll"
				+ " find rich editing features, as well as support for FTP and MySQL. At least with PHP, it offers light on-the-go debugging, "
				+ "alerting you to errors as you type. Netbeans also has a nice code navigator, and offers code completion and integrated "
				+ "documentation for frameworks like jQuery and Mootools.";
		category = "web";
		subcategory = "site";
		os="Windows/Mac/Linux";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);
		
		logoLink = "http://yohan.jasdid.com/wp-content/uploads/2014/06/Eclipse-luna.png";
		name = "Eclipse";
		language = "-";
		url = "https://eclipse.org/home/index.php";
		description = "Eclipse is an integrated development environment (IDE). "
				+ "It contains a base workspace and an extensible plug-in system "
				+ "for customizing the environment. Written mostly in Java, Eclipse "
				+ "can be used to develop applications. By means of various plug-ins, "
				+ "Eclipse may also be used to develop applications in other "
				+ "programming languages: Ada, ABAP, C, C++, COBOL, Fortran, Haskell, "
				+ "JavaScript, Lasso, Lua, Natural, Perl, PHP, Prolog, Python, R, "
				+ "Ruby (including Ruby on Rails framework), Scala, Clojure, Groovy, "
				+ "Scheme, and Erlang. "
				+ "It can also be used to develop packages for the software Mathematica. "
				+ "Development environments include the Eclipse Java development tools (JDT) "
				+ "for Java and Scala, Eclipse CDT for C/C++ and Eclipse PDT for PHP, "
				+ "among others.";
		topic = "Semantic Web";
		category = "Web";
		subcategory = "Web application";
		os = "Windows/Mac/Linux";
		type="IDE";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, ideClass, knw);

		//SEMANTIC WEB FRAMEWORK
		logoLink = "http://www.epimorphics.com/web/sites/all/documents/jena-logo-large.png";
		name = "Jena";
		language = "Java";
		url = "https://jena.apache.org/";
		description = "Jena, a Java RDF API and toolkit, is a Java framework to construct "
				+ "Semantic Web Applications. It provides a programmatic environment "
				+ "for RDF, RDFS and OWL, SPARQL, GRDDL, and includes a rule-based inference engine.";
		topic = "Semantic Web";
		category = "Web";
		subcategory = "Web application";
		os = "Windows/Linux/Mac";
		type= "Framework";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);

		logoLink = "http://www.w3.org/RDF/icons/rdf_w3c_icon.48";
		name = "Redland";
		language = "C, Python, Objective-C, PHP, Ruby, Perl";
		url = "http://librdf.org/";
		description = "The Redland RDF Application Framework is a set of free software libraries "
				+ "that provide support for RDF. "
				+ "It provides parser for RDF/XML, Turtle, N-triples, Atom, RSS; "
				+ "has a SPARQL and GRDDL implementation, and has language interfaces to "
				+ "Python, Obj-C, Perl, PHP and Ruby.";
		topic = "Semantic Web";
		category = "Web";
		subcategory = "Web application";
		os = "Windows/Linux/Mac";
		type= "Framework";
		knw = new GenericKnowledgeClass(logoLink, name, language.toLowerCase(), url, description, topic.toLowerCase(), category.toLowerCase(), subcategory.toLowerCase(), type.toLowerCase(), os.toLowerCase());
		createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);

		
		PrintStream output = null;
		try {
			output = new PrintStream(KNOWLEDGEBASE_RDF);
		} catch (FileNotFoundException e) {
			logger.severe("knowledgebase.rdf not found");
			e.printStackTrace();
		}

		model.write(output, "RDF/XML", MY_DOAP_BASE);
	}


	private static void createKnowledge(OntModel model,
			OntModel doapModel, OntProperty isUsedFor, OntClass clas, GenericKnowledgeClass fc) {

		// Individual Knowledge
		Individual proj = clas.createIndividual(MY_DOAP_BASE + fc.getName());
		Model base = ModelFactory.createDefaultModel();

		// set doap:url
		proj.setPropertyValue(DOAP.homepage, model.createResource(fc.getUrl()));
		
		// set doap:os
		proj.setPropertyValue(DOAP.os, model.createLiteral(fc.getOs()));

		// set doap:category		[Web or Mobile]
		InputStream in = FileManager.get().open("thesauri/" + fc.getCategory() + ".rdf");
		base.read(in, uri_thes, "RDF/XML");
		Resource value = getResourceFromTDB(base, fc.getCategory());
		proj.setPropertyValue(DOAP.category, value);

		// set doap:subcategory		[Web Application/Site o Android/iOS/WindowsPhone]
		value = getResourceFromTDB(base, fc.getSubcategory());
		proj.setPropertyValue(doapModel.createProperty(DOAP.NS, "sub_category"), value);

		// set the programming language(s) than can be used
		String[] langs = fc.getLanguage().split(", ");
		for (int i = 0; i < langs.length; i++)
			proj.addProperty(DOAP.programming_language, langs[i]);

		// set doap:description
		proj.setPropertyValue(DOAP.description, model.createLiteral(fc.getDescription()));

		// set doap:name
		proj.setPropertyValue(DOAP.name, model.createLiteral(fc.getName()));
		
		// set doap:logo
		proj.setPropertyValue(doapModel.createProperty(DOAP.NS, "logo"),
				model.createResource(fc.getLogoLink()));

		// add usedFor property only for frameworks and toolkits
		if (!fc.getType().equalsIgnoreCase("IDE")) {
			Model topicBase = ModelFactory.createDefaultModel();
			in = FileManager.get().open("thesauri/topics.rdf");
			topicBase.read(in, uri_thes, "RDF/XML");
			value = getResourceFromTDB(topicBase, fc.getTopic());
			proj.addProperty(isUsedFor, value);
		}
	}
	
	private static Resource getResourceFromTDB(Model model, String label) {
		Dataset dataset = TDBFactory.createDataset("DBforSearch");
		dataset.begin(ReadWrite.READ);
		dataset.commit();
		dataset.end();

		// get resource relative to preference label
		String queryString = "PREFIX skos: <" + SKOS.NS + ">"
				+ "SELECT ?s "
				+ "WHERE { ?s skos:prefLabel \"" + label + "\"@" + langAbbrevation
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
				+ "SELECT ?s "
				+ "WHERE { ?s skos:altLabel \"" + label + "\"@" + langAbbrevation
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


	public static void insertKnowledgeBase(GenericKnowledgeClass knw) throws IOException{
		// DOAP model
		OntModel doapModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
//		doapModel.read("mykb1.rdf");
		doapModel.read("http://usefulinc.com/ns/doap");

		// Ontologic model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
//		model.read("mykb1.rdf");
		model.createOntology(MY_DOAP_BASE);

		// Extend DOAP model
		model.createOntology(MY_DOAP_BASE).addImport(new ResourceImpl("http://usefulinc.com/ns/doap"));

		// Model and DOAP prefix
		model.setNsPrefix("doap", DOAP.NS);
		model.setNsPrefix("ta", MY_DOAP_BASE);

		// create usedFor property
		OntProperty isUsedFor = model.createObjectProperty(MY_DOAP_BASE + "usedFor");
		isUsedFor.addLabel("isUsedFor", "en");
		isUsedFor.addComment("stress the use of a framework, IDE and toolkit relative to a given topic", "en");
		
		if(knw.getType().equals("Framework")){
			// create Framework class
			OntClass frameworkClass = model.createClass(MY_DOAP_BASE + "Framework");
			frameworkClass.setLabel("Framework", "en");
			isUsedFor.addRange(frameworkClass);

			createKnowledge(model, doapModel, isUsedFor, frameworkClass, knw);
		}else if(knw.getType().equals("Tool")){
			// create Toolkit class
			OntClass toolClass = model.createClass(MY_DOAP_BASE + "Toolkit");
			toolClass.setLabel("Toolkit", "en");
			isUsedFor.addRange(toolClass);
			
			createKnowledge(model, doapModel, isUsedFor, toolClass, knw);
		}else if(knw.getType().equals("IDE")){
			// create IDE class
			OntClass ideClass = model.createClass(MY_DOAP_BASE + "IDE");
			ideClass.setLabel("IDE", "en");
			isUsedFor.addRange(ideClass);
			
			createKnowledge(model, doapModel, isUsedFor, ideClass, knw);
		}
		
		

		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(KNOWLEDGEBASE_RDF,false)));
		model.write(output, "RDF/XML", MY_DOAP_BASE);  // stampa in file
		logger.severe("Insert Knowledge "+ knw.getName());
	}
}
