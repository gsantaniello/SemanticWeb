/**
 * 
 */
package com.unisa.techadvisor.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.unisa.techadvisor.shared.Constants;
import com.unisa.techadvisor.shared.FieldVerifier;
import com.unisa.techadvisor.shared.User;

/**
 * @author giuseppe
 *
 */
public final class LoginViewImpl extends Composite implements LoginView  {


	private static final Binder binder = GWT.create(Binder.class);

	@UiTemplate("LoginView.ui.xml")
	interface Binder extends UiBinder<Widget, LoginViewImpl> {
	}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */


	public LoginViewImpl() {
		initWidget(binder.createAndBindUi(this));
		activeLoginDiv();
	}

	@UiField Anchor loginlink;
	@UiHandler("loginlink")
	void clickLoginLink(ClickEvent e){
		activeLoginDiv();
	}

	/**
	 * metodo che attiva la schermata di login
	 */
	private void activeLoginDiv() {
		loginlink.getElement().setAttribute("class", "active");
		signupForm.setAttribute("style", "display: none;");
		signuplink.getElement().removeAttribute("class");
		loginForm.setAttribute("style", "display: block;");
		loginlink.getElement().setAttribute("class", "active");
	}

	@UiField DivElement load;
	
	@UiField Anchor signuplink;
	@UiHandler("signuplink")
	void clickSignUpLink(ClickEvent e){
		activeSignUpDiv();
	}

	/**
	 * metodo che attiva la schermata di registrazione
	 */
	private void activeSignUpDiv() {
		loginForm.setAttribute("style", "display: none;");
		loginlink.getElement().removeAttribute("class");
		signupForm.setAttribute("style", "display: block;");
		signuplink.getElement().setAttribute("class", "active");
	}
	@UiField DivElement loginForm;
	@UiField DivElement signupForm;
	@UiField InputElement email_login;
	@UiField InputElement password_login;
	@UiField SpanElement msgError;
	@UiField SpanElement msgEmpty;
	@UiField Button login;

	/**
	 * prelevo campi per il signUp
	 */
	@UiField InputElement name;
	@UiField InputElement email_sign;
	@UiField InputElement password_sign;
	@UiField InputElement confirm_pass;
	@UiField TextAreaElement languages;
	@UiField SpanElement msgEmptySignUp;

	private Presenter presenter;

	@UiHandler("login")
	void checkLogin(ClickEvent e) {
		String usr = email_login.getValue();
		String pswd = password_login.getValue();
		if (presenter != null)
			if (!usr.equals("") && !pswd.equals("")){
				//Window.alert("chiamo on loginbutton presenter");
				presenter.onLoginButtonClicked(usr,pswd);
				email_login.setValue("");
			    password_login.setValue("");
				load.setAttribute("style", "display:block; margin-left: 50%;");
			}
			else {
				msgEmpty.setAttribute("style", "display: block;");
				msgError.setAttribute("style", "display: none;");
			}
	}

	@UiHandler("signup")
	void registration(ClickEvent e) {
		String nameUser = name.getValue();
		String emailUser = email_sign.getValue();
		String passUser = password_sign.getValue();
		String confirmUser = confirm_pass.getValue();
		String langUser = languages.getValue();
		if(presenter!= null){
			if(!nameUser.equals("")  && !emailUser.equals("") && !passUser.equals("")  && !confirmUser.equals("")){
				if(FieldVerifier.isValidPassword(passUser, confirmUser)){
					if(FieldVerifier.isValidEmail(emailUser)){
						ArrayList<String> linguaggi = new ArrayList<String>();
						linguaggi.add(langUser);
						User user = new User(nameUser,emailUser,passUser,linguaggi);
						presenter.onSignUpButtonClicked(user);	
						load.setAttribute("style", "display:block; margin-left: 50%;");
					}else{
						email_sign.setAttribute("class", "error");
					}		
				}else {
					password_sign.setAttribute("class", "error");
					confirm_pass.setAttribute("class", "error");
				}
			}else {
				msgEmptySignUp.setAttribute("style", "display: block;");
			}
		}
	}


	
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		activeLoginDiv();
		load.setAttribute("style", "display:none");
	}

	@Override
	protected void onUnload() {
		// TODO Auto-generated method stub
		resetInputText();
	    resetErrDiv();	
	}

	/**
	 * Metodo che reimposta i div della pagina !!
	 */
	private void resetErrDiv() {
		msgEmpty.setAttribute("style", "display: none;");
		msgError.setAttribute("style", "display: none;");
		msgEmptySignUp.setAttribute("style", "display: none;");
		
		email_sign.setAttribute("class", "");
		password_sign.setAttribute("class", "");
		confirm_pass.setAttribute("class", "");
	}

	/**
	 * Reimposta i campi di testo
	 */
	private void resetInputText() {
		email_sign.setValue("");
	    password_sign.setValue("");
	    languages.setValue("");
	    name.setValue("");
	    confirm_pass.setValue("");
	    email_login.setValue("");
	    password_login.setValue("");
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return email_login.getValue();
	}


	public String getPassword() {
		// TODO Auto-generated method stub
		return password_login.getValue();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void setErrMsgLogin() {
		// TODO Auto-generated method stub
		msgEmpty.setAttribute("style", "display: none");
		msgError.setAttribute("style", "display: block;");
		load.setAttribute("style", "display: none;");
	}


	@Override
	public void setErrMsgSignUp(String errType) {
		// TODO Auto-generated method stub
		if (errType.equals(Constants.EMAIL_PRESENTE)){
			email_sign.setAttribute("class", "error");
		}
		load.setAttribute("style", "display: none;");
	}

}
