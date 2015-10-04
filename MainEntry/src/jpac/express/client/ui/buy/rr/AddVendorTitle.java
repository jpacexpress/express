package jpac.express.client.ui.buy.rr;

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
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddVendorTitle extends CustomWindow {

	private static final String name = Constants.RR_ADD_VENDORTITLE_NAME;
	private VStack mainStack;
	private HLayout customerTitleLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm titleForm;

	private TextItem titleName;
	private TextItem titleID;

	private IButton submit;
	private IButton cancel;

	public AddVendorTitle() {
		super(Constants.RR_ADD_VENDORTITLEID, name, 600, 100, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}
	public void showAddCustomerTitleName(){
		titleForm.clearValues();
		titleForm.setCanEdit(true);
		centerInPage();
		show();
		titleForm.focusInItem(Constants.VENDOR_TITLE_NAME);
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
				record.setAttribute(Constants.RR_VENDOR_TITLE_RECORD, titleForm.getValuesAsRecord());				
				//Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_VENDOR_TITLE_RECORD), titleForm.getValuesAsRecord(), titleForm.getValuesAsRecord().getAttributes());				
				VendorTitleList.getInstance().addVendorTitleToListAsRecord(record);
				hide();
			}
		});
		addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)){
					Record record = new Record();
					record.setAttribute(Constants.RR_VENDOR_TITLE_RECORD, titleForm.getValuesAsRecord());									
					VendorTitleList.getInstance().addVendorTitleToListAsRecord(record);
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		titleForm = new DynamicForm();
		//titleForm.setDataSource(DataSource.get("vendorTitleName"));
		//titleForm.setAutoFetchData(true);
		
		titleForm.setTitleWidth(44);
		titleForm.setWidth("95%");
		
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		
		titleID = new TextItem();
		titleID.setName(Constants.VENDOR_TITLEID);
		titleID.setTitle("");
		titleID.setShowTitle(false);
		
		titleName = new TextItem();
		titleName.setName(Constants.VENDOR_TITLE_NAME);
		titleName.setTitle("");
		titleName.setShowTitle(false);
		titleName.setWidth(500);
		titleForm.setTitleSuffix("");
		titleForm.setHeight(25);
		titleForm.setFields(autoId,titleID, titleName);
		titleForm.hideItem(Constants.VENDOR_TITLEID);
		titleForm.hideItem(Constants.IDS);

		customerTitleLayout.addMember(titleForm);
		mainStack.addMembers(customerTitleLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
