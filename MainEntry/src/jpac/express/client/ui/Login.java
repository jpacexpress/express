package jpac.express.client.ui;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.datasource.LoginDataSource;
import jpac.express.client.service.LoginService;
import jpac.express.client.service.LoginServiceAsync;
import jpac.express.client.ui.custom.CustomDialog;
import jpac.express.domain.Account;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Login extends VLayout {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	private final HLayout loginLayout = new HLayout();
	private final Window loginWindow = new Window();
	private final CustomDialog dialog = new CustomDialog();
	private final DynamicForm loginForm = new DynamicForm();
	private final TextItem username = new TextItem();
	private final PasswordItem password = new PasswordItem();
	private final ButtonItem loginButton = new ButtonItem("เข้าสู่ระบบ");
	private static Login instance;

	public Login() {
		super();
		setWidth100();
		setHeight100();

		loginWindow.setWidth(360);
		loginWindow.setHeight(125);
		loginWindow.setTitle("เข้าสู่ระบบ");
		loginWindow.setShowMinimizeButton(false);
		loginWindow.setShowCloseButton(false);
		loginWindow.setIsModal(true);
		loginWindow.setShowModalMask(true);
		loginWindow.setAlign(Alignment.CENTER);
		loginWindow.centerInPage();

		username.setName("accountUsername");
		username.setValue("guest");
		password.setName("accountPassword");
		password.setValue("guest");

		loginButton.setColSpan(2);
		loginButton.setAlign(Alignment.RIGHT);
		loginButton.addClickHandler(loginBtnClickHandler());

		loginForm.setDataSource(LoginDataSource.getInstance());
		loginForm.setItemLayout(FormLayoutType.TABLE);
		loginForm.setNumCols(2);
		loginForm.setTitleWidth(90);
		loginForm.setTitleAlign(Alignment.LEFT);
		loginForm.setTitleSuffix("");
		loginForm.setWidth(200);
		loginForm.setHeight(80);
		loginForm.setFields(username, password, loginButton);

		loginLayout.addMember(loginForm);
		loginLayout.setWidth100();
		loginLayout.setMargin(10);
		loginLayout.setAlign(Alignment.CENTER);

		loginWindow.addItem(loginLayout);
		addChild(loginWindow);
		
	}

	public static Login getInstance() {
		if(instance==null) {
			instance = new Login();
		}
		return instance;
	}
	
	private AsyncCallback<Boolean> loginCallBack() {
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				//SC.warn("ล้มเหลวในการเชื่อมต่อ");
				dialog.Say("ล้มเหลวในการเชื่อมต่อ "+caught.getLocalizedMessage());
				loginButton.enable();
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == true) {
					hide();
					MainEntry.mainLayout.removeChild(instance);
					MainEntry.mainLayout.addChild(Application.getInstance());
					Application.instance.showApplication();
				} else {
					//SC.warn("ไม่พบชื่อผู้ใช้งานและรหัสผ่าน");
					dialog.Say("ไม่พบชื่อผู้ใช้งานและรหัสผ่าน", "red");
					loginButton.enable();
				}
			}

		};
		return callback;
	}

	private ClickHandler loginBtnClickHandler() {
		ClickHandler click = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loginButton.disable();
				Account account = new Account();
				account.setAccountUsername(loginForm.getValueAsString("accountUsername").trim());
				account.setAccountPassword(loginForm.getValueAsString("accountPassword").trim());
				loginService.doLogin(account, loginCallBack());
			}
		};
		return click;
	}
}
