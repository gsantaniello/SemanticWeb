package com.unisa.techadvisor.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.unisa.techadvisor.client.TechAdvisorService;
import com.unisa.techadvisor.shared.Constants;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;
import com.unisa.techadvisor.shared.ResultSearch;
import com.unisa.techadvisor.shared.SearchDetails;
import com.unisa.techadvisor.shared.User;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TechAdvisorServiceImpl extends RemoteServiceServlet implements TechAdvisorService {


	private static final String MOBILE = "mobile";
	private static final String WEB = "web";
	private static Logger logger = Logger.getLogger("global");


	private Ontology ontology;

	public void setOntology(){
		this.ontology = new InferenceClass();
		try {
			KnowledgeBased.loadKnowledge();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private ArrayList<String> getLanguageFromWikiMiner(String argument) throws UnsupportedEncodingException{
		
		String lang = URLEncoder.encode(argument, "UTF-8");
		String urltext = "http://wikipedia-miner.cms.waikato.ac.nz/services/wikify?source="+lang+"&responseFormat=json";

		try {
			URL url;
			url = new URL(urltext);
			HttpURLConnection newUrl = (HttpURLConnection)url.openConnection();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(newUrl.getInputStream());
			int numb = rootNode.path("detectedTopics").size();
			ArrayList<String> language= new ArrayList<String>();
			for (int i=0;i<numb;i++){
				JsonNode linguaggi = rootNode.path("detectedTopics").get(i).get("title");
				if (linguaggi.textValue().equals("Logical conjunction") || linguaggi.textValue().equals("Programming language")){
					continue;
				}
				String ar [] = linguaggi.textValue().split(" ");
				language.add(ar[0]);
			}
			return language;
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	@Override
	public String signUp(User user) throws UnsupportedEncodingException {
		System.out.println("****************************************************************");
		System.out.println("signIn prima");
		System.out.println("\n\nEmail "+ user.getEmail());
		System.out.println("Name "+ user.getName());
		System.out.println("Password "+ user.getPassword());
		System.out.println("Language "+ user.getLanguage().get(0));
		ArrayList<String> language = getLanguageFromWikiMiner(user.getLanguage().get(0));

		user.setLanguage(language);
		String result = ontology.signUp(user);
		if (result.equals(Constants.REGISTRAZIONE_COMPLETATA)){
			storeUserInSession(user);
		}

		System.out.println("sigIn: "+ result);
		System.out.println("****************************************************************");
		return result;
	}

	@Override
	public String logIn(User user) {
		System.out.println("****************************************************************");
		System.out.println("logIn prima");
		System.out.println("\n\nEmail "+ user.getEmail());
		System.out.println("Name "+ user.getName());
		System.out.println("Password "+ user.getPassword());
		System.out.println("Language "+ user.getLanguage()+"\n\n");

		String result = ontology.logIn(user);
		if (result.equals(Constants.LOGIN_COMPLETATA)){
			storeUserInSession(user);
		}
		System.out.println("logIn: "+ result);
		System.out.println("****************************************************************");

		return result;
	}

	@Override
	/**
	 * Metodo che restituisce la lista dei linguaggi di programmazione preferiti dell'utente 
	 */
	public ArrayList<String> getLanguage(){
		User usr = getUserAlreadyFromSession();
		if (usr!=null){
			ArrayList<String> result = ontology.getLanguage(usr.getEmail());
			return result;
		}else {
			return null;
		}
	}

	@Override
	public String setResult(String argument) {
		// TODO Auto-generated method stub
		try {
			String arg = URLEncoder.encode(argument, "UTF-8");
			String urltext = "http://wikipedia-miner.cms.waikato.ac.nz/services/wikify?source="+arg+"&minProbability=0.2&responseFormat=json";
			URL url;
			url = new URL(urltext);
			HttpURLConnection newUrl = (HttpURLConnection)url.openConnection();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(newUrl.getInputStream());
			int numb = rootNode.path("detectedTopics").size();
			SearchDetails searchDetails = new SearchDetails();
			for (int i=0;i<numb;i++){
				JsonNode res = rootNode.path("detectedTopics").get(i).get("title");
				if (res.asText().equals(Constants.THE_MUSIC) || res.asText().equals(Constants.MUSIC)){
					searchDetails.setTopic(Constants.MUSIC);
				}
				else if(res.asText().equals(Constants.ELECTRONIC_COMMERCE)){
					searchDetails.setTopic("E-Commerce");
				}
				else if(res.asText().equals(Constants.SEMANTIC_WEB)){
					searchDetails.setTopic("Semantic Web");
				}
				else if (res.asText().equals(Constants.ANDROID)){
					searchDetails.setSubCategory(Constants.ANDROID);
					searchDetails.setCategory(MOBILE);
				}
				else if (res.asText().equals(Constants.IOS)||res.asText().equals(Constants.IOS_APPLE)){
					searchDetails.setSubCategory(Constants.IOS_APPLE);
					searchDetails.setCategory(MOBILE);
				}
				else if (res.asText().equals(Constants.WINDOWS_PHONE)){
					searchDetails.setSubCategory(Constants.WINDOWS_PHONE);
					searchDetails.setCategory(MOBILE);
				}
				else if (res.asText().equals(Constants.WEBSITE)){
					searchDetails.setSubCategory(Constants.WEBSITE);
					searchDetails.setCategory(WEB);
				}
				else if (res.asText().equals(Constants.WEB_APPLICATION)){
					searchDetails.setSubCategory("Web Application");
					searchDetails.setCategory(WEB);
				}
			}
			if (searchDetails.getTopic().equals("")){
				return Constants.NO_MATCH;
			}if (!searchDetails.getTopic().equals("") && !searchDetails.getSubCategory().equals("")){
				/**
				 * ho capito che ha settato il topic e che è un'applicaizione categoria WEB/MOBILE
				 */
				String ris = callSearch(searchDetails);
				return ris;
			}else {
				/**
				 * ha inserito solo il topic devo caricare la seconda pagina e chiamare il metodo searchInference 
				 * per recuperare il risultato parziale
				 */
				callSearch(searchDetails);
				storeSearchDetailsInSession(searchDetails);
				return Constants.SET_TOPIC;
			}

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo che chiama la ricerca finale
	 * @param searchDetails
	 * @return NO_MATCH se non c'è stato alcun match oppure RESULT_SEARCH se è stato trovato
	 * qalche ide framework o tool
	 */
	private String callSearch(SearchDetails searchDetails) {
		User usr = getUserAlreadyFromSession();
		String email = usr.getEmail();
		String topic = searchDetails.getTopic();
		String category = searchDetails.getCategory();
		String subcategory = searchDetails.getSubCategory();
		ArrayList<GenericKnowledgeClass> result = ontology.searchInference(email, topic, category, subcategory);
		if(result.isEmpty()){
			logger.severe("[Inference failed] Elemento non presente");
			searchDetails.setStatus(false);
			storeSearchDetailsInSession(searchDetails);
			return Constants.NO_MATCH;
		}else{
			for(GenericKnowledgeClass tmp: result){
				//Una syso per riga
				System.out.println("Type: "+tmp.getType());
				if (!tmp.getType().equalsIgnoreCase("IDE"))
					System.out.println("Topic: "+tmp.getTopic());
				System.out.println("Name: "+tmp.getName());
				System.out.println("Url: "+tmp.getUrl());
				System.out.println("Language: "+tmp.getLanguage());
				System.out.println("Description: "+tmp.getDescription());
				System.out.println("Category: "+tmp.getCategory());
				System.out.println("Sub Category: "+tmp.getSubcategory());
				if(!(tmp.getOs()=="" || tmp.getOs()=="-"))
					System.out.println("Os: "+tmp.getOs());
				System.out.println("\n");
			}
		}
		ResultSearch res = new ResultSearch(result);
		storeSearchResultInSession(res);
		return Constants.RESULT_SEARCH;
	}

	private void storeUserInSession(User user){
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute("user", user);
	}

	private void storeSearchResultInSession(ResultSearch result){
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute("result", result);
	}

	private void storeSearchDetailsInSession(SearchDetails details){
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute("details", details);
	}

	private SearchDetails getDetailsAlreadyFromSession(){
		SearchDetails sd = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute("details");
		if (userObj != null && userObj instanceof SearchDetails)
		{
			sd = (SearchDetails) userObj;
		}
		return sd;
	}

	@Override
	public User loginFromSessionServer() {
		return getUserAlreadyFromSession();
	}

	private User getUserAlreadyFromSession()
	{
		User user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute("user");
		if (userObj != null && userObj instanceof User)
		{
			user = (User) userObj;
		}
		return user;
	}


	@Override
	/**
	 * Metodo che permette di inserire un nuovo linguaggio di programmazione ad un utente
	 */
	public void insertLanguage(String lang_list) {
		// TODO Auto-generated method stub
		User user = getUserAlreadyFromSession();
		ArrayList<String> language;
		try {
			language = getLanguageFromWikiMiner(lang_list);
			if (language.size()>0){
				user.setLanguage(language);
				ontology.insertLanguage(user, language);
			}else{
				logger.severe("linguaggio non risconosciuto");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Metodo che permette di rimuovere un dato linguaggio di programmazione ad un utente
	 */
	public void removeLanguage(ArrayList<String> lang_list) {
		// TODO Auto-generated method stub
		User user = getUserAlreadyFromSession();
		ontology.removeLanguage(user, lang_list);
	}

	@Override
	public String setCategory(String category) {
		// TODO Auto-generated method stub
		SearchDetails searchDetails = getDetailsAlreadyFromSession();
		searchDetails.setCategory(category);
		storeSearchDetailsInSession(searchDetails);
		String result = callSearch(searchDetails);
		return result;
	}

	@Override
	public String setSubCategory(String type) {
		// TODO Auto-generated method stub
		SearchDetails sd = getDetailsAlreadyFromSession();
		sd.setSubCategory(type);
		storeSearchDetailsInSession(sd);
		String ris = callSearch(sd);
		return ris;
	}


	@Override
	public void logout() {
		// TODO Auto-generated method stub
		deleteUserFromSession();
	}

	private void deleteUserFromSession() {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute("user");
		session.removeAttribute("details");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		User usr = getUserAlreadyFromSession();
		String mail = usr.getEmail();
		return ontology.getName(mail);
	}

	@Override
	public ArrayList<String> getLanguages(String languages) {
		// TODO Auto-generated method stub
		ArrayList<String> lngs = new ArrayList<String>();
		try {
			lngs = getLanguageFromWikiMiner(languages);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lngs.size()>0){
			return lngs;
		}else{
			return null;
		}

	}

	@Override
	public ResultSearch resultFromSessionServer() {
		return getResultAlreadyFromSession();
	}

	/**
	 * metodo che mi consente di prelevare il result memorizzato in sessione
	 * è di tipo ResultSearch, la cui classe instanzia un ArrayList<GenericKnowledgeClass>
	 * @return ResultSearch
	 */
	private ResultSearch getResultAlreadyFromSession(){
		ResultSearch result = new ResultSearch();
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object resultObj = session.getAttribute("result");
		if (resultObj != null && result instanceof ResultSearch)
		{
			result = (ResultSearch) resultObj;
		}
		return result;
	}

	@Override
	/**
	 * restituisce al presenter lo stato della ricerca
	 * @return false se la ricerca è terminata prematuramente,
	 * 		   true altrimenti
	 */
	public boolean getDetailsFromSession() {
		// TODO Auto-generated method stub
		SearchDetails searchDetails = getDetailsAlreadyFromSession();
		boolean state = searchDetails.isStatus();
		return state;
	}
}
