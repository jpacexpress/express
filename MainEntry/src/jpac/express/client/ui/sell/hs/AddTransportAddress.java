package jpac.express.client.ui.sell.hs;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.util.SC;
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

public class AddTransportAddress extends CustomWindow {

	private static final String name = "เพิ่มที่อยู่ที่จัดส่ง";
	private VStack mainStack;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private HLayout customerIdLayout;
	private HLayout transportAddressLayout;

	private DynamicForm customerIdForm;
	private DynamicForm transportAddressForm;

	private IButton submit;
	private IButton cancel;
	private String action = Constants.RECORD_ACTION_NULL;

	public AddTransportAddress() {
		super("AddTransportAddress",name, 550, 295, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		customerIdForm.disable();
		hide();
	}

	public void showAddTransportAddress(String action, String target, Record record) {
		this.action = action;
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			transportAddressForm.editSelectedData(TransportAddressList.getTransportAddressList());
		} else if(action.equals(Constants.RECORD_ACTION_ADD)) {
			transportAddressForm.clearValues();
		}
		if(target.equals(Constants.HS)&&HS.getCustomerForm().getField("customerIdentify").getValue()!=null&&!HS.getCustomerForm().getField("customerIdentify").getValue().equals("")) {
			customerIdForm.setValues(HS.getCustomerForm().getValues());
			transportAddressForm.setValue("customer",HS.getCustomerForm().getValueAsString("customerId"));
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();

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
				record.getAttributeAsRecord("customer").setAttribute("transportAddress", transportAddressForm.getValuesAsRecord());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					transportAddressForm.saveData();
				} else {
					TransportAddressList.addTransportAddressToListAsRecord(record);
				}
				action = Constants.RECORD_ACTION_NULL;
				
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute("customer", new Record());
					record.getAttributeAsRecord("customer").setAttribute("transportAddress", transportAddressForm.getValuesAsRecord());
					
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						transportAddressForm.saveData();
					} else {
						TransportAddressList.addTransportAddressToListAsRecord(record);
					}
					action = Constants.RECORD_ACTION_NULL;
		
					hide();
				}
			}
		});

		controlLayout.setHeight(40);
		controlLayout.setMargin(3);
		controlLayout.addMembers(submit, cancel);

		customerIdLayout = new HLayout();
		transportAddressLayout = new HLayout();

		customerIdForm = new DynamicForm();
		customerIdForm.setItemLayout(FormLayoutType.TABLE);
		customerIdForm.setNumCols(6);
		customerIdForm.setWidth100();
		customerIdForm.setTitleWidth(81);
		customerIdForm.setTitleSuffix("");
		TextItem customerId = new TextItem();
		customerId.setTitle("รหัสลูกค้า");
		customerId.setName("customerIdentify");
		customerId.setWidth(107);
		TextItem customerTitle = new TextItem();
		customerTitle.setTitle(Constants.TITLENAME_CUSTOMERTITLENAME_TH_TITLE);
		customerTitle.setName(Constants.TITLENAME_CUSTOMERTITLENAME_TH);
		customerTitle.setColSpan(3);
		TextItem customerName = new TextItem();
		customerName.setTitle("ชื่อ");
		customerName.setName("customerName");
		customerName.setTitleColSpan(3);
		customerName.setColSpan(3);
		customerName.setWidth("*");
		customerIdForm.setFields(customerId, customerTitle, customerName);

		transportAddressForm = new DynamicForm();
		transportAddressForm.setTitleWidth(50);
		transportAddressForm.setItemLayout(FormLayoutType.TABLE);
		transportAddressForm.setDataSource(DataSource.get("transportAddress"));
		transportAddressForm.setNumCols(4);
		transportAddressForm.setWidth100();
		transportAddressForm.setTitleWidth(70);
		transportAddressForm.setTitleSuffix("");

		TextItem transportTransportAddressId = new TextItem();
		transportTransportAddressId.setTitle("รหัสสถานที่ส่ง");
		transportTransportAddressId.setName("transportAddressId");
		transportTransportAddressId.setColSpan(4);

		TextItem transportAddressLine1 = new TextItem();
		transportAddressLine1.setTitle("ที่อยู่");
		transportAddressLine1.setName("transportAddressLine1");
		transportAddressLine1.setColSpan(4);
		transportAddressLine1.setWidth("*");

		TextItem transportAddressLine2 = new TextItem();
		transportAddressLine2.setTitle("");
		transportAddressLine2.setName("transportAddressLine2");
		transportAddressLine2.setColSpan(4);
		transportAddressLine2.setWidth("*");

		TextItem transportAddressLine3 = new TextItem();
		transportAddressLine3.setTitle("");
		transportAddressLine3.setName("transportAddressLine3");
		transportAddressLine3.setWidth("*");

		TextItem transportPostalCode = new TextItem();
		transportPostalCode.setTitle("รหัสไปรษณีย์");
		transportPostalCode.setName("transportPostalCode");
		transportPostalCode.setWidth("*");

		TextItem transportPhoneNumber = new TextItem();
		transportPhoneNumber.setTitle("โทรศัพท์");
		transportPhoneNumber.setName("transportPhoneNumber");
		transportPhoneNumber.setColSpan(4);
		transportPhoneNumber.setWidth("*");

		TextItem transportContactName = new TextItem();
		transportContactName.setTitle("ชื่อผู้ติดต่อ");
		transportContactName.setName("transportContactName");
		transportContactName.setColSpan(4);
		transportContactName.setWidth("*");
		transportAddressForm.setFields(transportTransportAddressId, transportAddressLine1, transportAddressLine2, transportAddressLine3, transportPostalCode, transportPhoneNumber, transportContactName);

		customerIdForm.setHeight(50);
		customerIdLayout.addMember(customerIdForm);
		customerIdLayout.setHeight(50);
		customerIdLayout.setMargin(3);

		transportAddressForm.setHeight(100);
		transportAddressLayout.setMargin(3);
		transportAddressLayout.addMember(transportAddressForm);
		transportAddressLayout.setBorder("1px solid white");

		mainStack.addMembers(customerIdLayout, transportAddressLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
