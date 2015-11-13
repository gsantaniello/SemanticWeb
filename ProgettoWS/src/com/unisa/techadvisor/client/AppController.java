package com.unisa.techadvisor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.event.HomeEvent;
import com.unisa.techadvisor.client.event.HomeEventHandler;
import com.unisa.techadvisor.client.event.LogoutEvent;
import com.unisa.techadvisor.client.event.LogoutEventHandler;
import com.unisa.techadvisor.client.event.ProfileEvent;
import com.unisa.techadvisor.client.event.ProfileEventHandler;
import com.unisa.techadvisor.client.event.ResultEvent;
import com.unisa.techadvisor.client.event.ResultEventHandler;
import com.unisa.techadvisor.client.event.Search2Event;
import com.unisa.techadvisor.client.event.Search2EventHandler;
import com.unisa.techadvisor.client.event.Search3_1Event;
import com.unisa.techadvisor.client.event.Search3_1EventHandler;
import com.unisa.techadvisor.client.event.Search3_2Event;
import com.unisa.techadvisor.client.event.Search3_2EventHandler;
import com.unisa.techadvisor.client.event.SearchEvent;
import com.unisa.techadvisor.client.event.SearchEventHandler;
import com.unisa.techadvisor.client.event.UpdateProfileEvent;
import com.unisa.techadvisor.client.event.UpdateProfileHandler;
import com.unisa.techadvisor.client.presenter.HomePresenter;
import com.unisa.techadvisor.client.presenter.ProfilePresenter;
import com.unisa.techadvisor.client.presenter.ResultPresenter;
import com.unisa.techadvisor.client.presenter.Search2Presenter;
import com.unisa.techadvisor.client.presenter.Search3_1Presenter;
import com.unisa.techadvisor.client.presenter.Search3_2Presenter;
import com.unisa.techadvisor.client.presenter.Search1Presenter;
import com.unisa.techadvisor.client.presenter.SidebarPresenter;
import com.unisa.techadvisor.client.presenter.LoginPresenter;
import com.unisa.techadvisor.client.presenter.Presenter;
import com.unisa.techadvisor.client.view.HomeViewImpl;
import com.unisa.techadvisor.client.view.ProfileViewImpl;
import com.unisa.techadvisor.client.view.ResultViewImpl;
import com.unisa.techadvisor.client.view.Search1ViewImpl;
import com.unisa.techadvisor.client.view.Search2ViewImpl;
import com.unisa.techadvisor.client.view.Search3_1ViewImpl;
import com.unisa.techadvisor.client.view.Search3_2ViewImpl;
import com.unisa.techadvisor.client.view.SidebarViewImpl;
import com.unisa.techadvisor.client.view.LoginViewImpl;
import com.unisa.techadvisor.shared.User;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private HasWidgets container;
	private LoginViewImpl loginView;
	private SidebarViewImpl sidebarView;
	private HomeViewImpl homeView;
	private Search1ViewImpl searchView;
	private ProfileViewImpl profileView;
	private Search2ViewImpl search2View;
	private Search3_1ViewImpl search3_1View;
	private Search3_2ViewImpl search3_2View;
	private ResultViewImpl resultView;
	private final HandlerManager eventBus;
	private final TechAdvisorServiceAsync rpcService;


	public AppController(TechAdvisorServiceAsync rpcService, HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {
		// TODO Auto-generated method stub
		History.addValueChangeHandler(this);

		eventBus.addHandler(HomeEvent.TYPE,
				new HomeEventHandler() {
			@Override
			public void onHome(HomeEvent event) {
				// TODO Auto-generated method stub
				doHomePage();
			}
		});


		eventBus.addHandler(SearchEvent.TYPE,
				new SearchEventHandler() {
			@Override
			public void onSearch(SearchEvent event) {
				// TODO Auto-generated method stub
				doSearchPage();
			}
		});

		eventBus.addHandler(ProfileEvent.TYPE,
				new ProfileEventHandler() {
			@Override
			public void onProfile(ProfileEvent event) {
				// TODO Auto-generated method stub
				doProfilePage();
			}
		});
		eventBus.addHandler(Search2Event.TYPE, new Search2EventHandler() {
			@Override
			public void onSearch2(Search2Event event) {
				// TODO Auto-generated method stub
				doSearch2Page();
			}
		});
		eventBus.addHandler(Search3_1Event.TYPE, new Search3_1EventHandler() {
			@Override
			public void onSearch3_1(Search3_1Event event) {
				// TODO Auto-generated method stub
				doSearch3_1Page();
			}
		});
		eventBus.addHandler(Search3_2Event.TYPE, new Search3_2EventHandler() {
			@Override
			public void onSearch3_2(Search3_2Event event) {
				// TODO Auto-generated method stub
				doSearch3_2Page();
			}
		});

		eventBus.addHandler(ResultEvent.TYPE, new ResultEventHandler() {

			@Override
			public void onResult(ResultEvent event) {
				// TODO Auto-generated method stub
				doResultPage();
			}
		});

		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {

			@Override
			public void onLogout(LogoutEvent event) {
				doLogout();
			}
		});
		
		eventBus.addHandler(UpdateProfileEvent.TYPE, new UpdateProfileHandler() {
			
			@Override
			public void onUpdateProfile(UpdateProfileEvent up) {
				// TODO Auto-generated method stub
				doUpProfilePage();
				
			}
		});
	}

	protected void doUpProfilePage() {
		// TODO Auto-generated method stub
		History.newItem("upProfile");
		
	}

	protected void doLogout() {
		// TODO Auto-generated method stub
		History.newItem("logout");
	}

	protected void doResultPage() {
		// TODO Auto-generated method stub
		History.newItem("result");
	}

	protected void doSearch3_2Page() {
		// TODO Auto-generated method stub
		History.newItem("search3_2");
	}

	protected void doSearch3_1Page() {
		// TODO Auto-generated method stub
		History.newItem("search3_1");
	}

	protected void doSearch2Page() {
		// TODO Auto-generated method stub
		History.newItem("search2");
	}

	protected void doProfilePage() {
		// TODO Auto-generated method stub
		History.newItem("profile");
	}

	protected void doSearchPage() {
		// TODO Auto-generated method stub
		History.newItem("search");
	}

	private void doHomePage() {
		// TODO Auto-generated method stub
		History.newItem("home");
	}

	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		this.container=container;
		String sessionID = Cookies.getCookie("sid");
		if (sessionID != null){
			History.newItem("home");
		}else {
			History.newItem("login");
		}
		/*
		if ("".equals(History.getToken())) {
		}
		else {
			History.fireCurrentHistoryState();
		}*/
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		String token = event.getValue();

		if (token != null) {
			if (token.equals("login")) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						//

						if (loginView == null) {
							loginView = new LoginViewImpl();

						}
						new LoginPresenter(rpcService, eventBus, loginView).go(container);
					}
				});
			}else if (token.equals("logout")) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						rpcService.logout(new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
								if (loginView == null) {
									loginView = new LoginViewImpl();

								}
								new LoginPresenter(rpcService, eventBus, loginView).go(container);
								History.newItem("login");
							}
						});
					}
				});
			}else if (token.equals("home")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(homeView == null){
										homeView = new HomeViewImpl();
									}
									buildPage();
									new HomePresenter(rpcService, eventBus, homeView).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
					}
				});
			}
			else if (token.equals("search")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(searchView == null){
										searchView = new Search1ViewImpl();
									}
									buildPage();
									new Search1Presenter(rpcService, eventBus, searchView).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
					}
				});
			}
			else if (token.equals("profile")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(profileView == null){
										profileView = new ProfileViewImpl();
									}
									buildPage();
									new ProfilePresenter(rpcService, eventBus, profileView,container).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
					}
				});
			}else if( token.equals("search2")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(search2View == null){
										search2View = new Search2ViewImpl();
									}

									buildPage();
									new Search2Presenter(rpcService, eventBus, search2View).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}else if (token.equals("search3_1")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(search3_1View == null){
										search3_1View = new Search3_1ViewImpl();
									}

									buildPage();
									new Search3_1Presenter(rpcService, eventBus, search3_1View).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}else if (token.equals("search3_2")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(search3_2View == null){
										search3_2View = new Search3_2ViewImpl();
									}
									buildPage();
									new Search3_2Presenter(rpcService, eventBus, search3_2View).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}else if (token.equals("result")){
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						// lazily initialize our views, and keep them around to be reused
						rpcService.loginFromSessionServer(new AsyncCallback<User>() {
							@Override
							public void onSuccess(User result) {
								// TODO Auto-generated method stub
								if (result!=null){
									if(resultView == null){
										resultView = new ResultViewImpl();
									}
									buildPage();
									new ResultPresenter(rpcService, eventBus, resultView).go(container);
								}else {
									if (loginView == null) {
										loginView = new LoginViewImpl();
									}
									new LoginPresenter(rpcService, eventBus, loginView).go(container);
								}
							}
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
							}
						});
					}
				});
			}
			else if (token.equals("upProfile")) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess() {
						if (profileView == null) {
							profileView = new ProfileViewImpl();
						}
						new ProfilePresenter(rpcService, eventBus, profileView,container).go(container);
						History.newItem("profile");
					}
				});
			}
			
		}
	}

	private HasWidgets buildPage(){
		container.clear();
		if (sidebarView == null)
			sidebarView = new SidebarViewImpl();
		new SidebarPresenter(rpcService, eventBus, sidebarView);
		container.add(sidebarView);
		//container.add(widget);

		return container;
	}
}
