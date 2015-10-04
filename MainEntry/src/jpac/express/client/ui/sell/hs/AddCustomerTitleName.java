package jpac.express.client.ui.sell.hs;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddCustomerTitleName extends CustomWindow {

	private VStack mainStack;
	private HLayout customerTitleLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm titleForm;

	private TextItem titleName;

	private IButton submit;
	private IButton cancel;

	public AddCustomerTitleName() {
		super(Constants.SELL_HS_ADDCUSTOMERTITLENAME_ID,Constants.SELL_HS_ADDCUSTOMERTITLENAME_TITLE, 600, 100, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddCustomerTitleName() {
		titleForm.clearValues();
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		customerTitleLayout = new HLayout();
		customerTitleLayout.setHeight(25);
		controlLayout = new HLayout();
		controlLayout.setHeight(25);
		controlLayout.setMargin(5);
		controlLayout.setAlign(Alignment.CENTER);

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setHeight(30);
		submit.setMargin(5);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setHeight(30);
		cancel.setMargin(5);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("customer"), titleForm.getValuesAsRecord(), titleForm.getValuesAsRecord().getAttributes());
				
				CustomerTitleNameList.getInstance().addCustomerTitleNameToListAsRecord(record);
				hide();
			}
		});
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute("customer", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("customer"), titleForm.getValuesAsRecord(), titleForm.getValuesAsRecord().getAttributes());

					CustomerTitleNameList.getInstance().addCustomerTitleNameToListAsRecord(record);
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		titleForm = new DynamicForm();
		titleForm.setDataSource(DataSource.get("titleName"));
		titleForm.setAutoFetchData(true);
		
		titleForm.setTitleWidth(44);
		titleForm.setWidth("95%");

		titleName = new TextItem();
		titleName.setName(Constants.TITLENAME_CUSTOMERTITLENAME_TH);
		titleName.setTitle("");
		titleName.setShowTitle(false);
		titleName.setWidth(500);
		titleForm.setTitleSuffix("");
		titleForm.setHeight(25);
		titleForm.setFields(titleName);

		customerTitleLayout.addMember(titleForm);
		mainStack.addMembers(customerTitleLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
