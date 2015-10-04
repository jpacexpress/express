package jpac.express.client;

import jpac.express.client.service.LoginService;
import jpac.express.client.service.LoginServiceAsync;
import jpac.express.client.service.MessageService;
import jpac.express.client.service.MessageServiceAsync;
import jpac.express.client.ui.Login;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MainEntry implements EntryPoint {

	public static final VLayout mainLayout = new VLayout();
	public static final MessageServiceAsync messageService = GWT.create(MessageService.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	public void onModuleLoad() {
		RootPanel.get().getElement().getStyle().setMargin(0, Unit.PX);
		RootPanel.get().getElement().getStyle().setPadding(0, Unit.PX);
		//RootPanel.get().getElement().getStyle().setBackgroundColor("BurlyWood");
	
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.addChild(Login.getInstance());
		RootPanel.get().add(mainLayout.asWidget());
		
		loginService.isLogin(new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Boolean result) {
				if(result == true) {
					MainEntry.mainLayout.removeChild(Login.getInstance());
					MainEntry.mainLayout.addChild(Application.getInstance());
					Application.instance.showApplication();
				} else {
				}
			}
		});

		/*
		Element body = Document.get().getBody();
		Element loading = Document.get().getElementById("loading");
		if(body.isOrHasChild(loading)){
			loading.getStyle().setDisplay(Display.NONE);
		}
		*/
		
	}

	
}
