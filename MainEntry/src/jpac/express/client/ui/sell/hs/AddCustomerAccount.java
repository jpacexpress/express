package jpac.express.client.ui.sell.hs;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddCustomerAccount extends CustomWindow {

	private static final String name = "เพิ่มรหัสบัญชี";
	private VStack mainStack;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private HLayout customerAccountLayout;

	private AddCustomerAccountControl addCustomerAccountControl;

	private DynamicForm customerAccountForm;

	private IButton submit;
	private IButton cancel;

	public AddCustomerAccount() {
		super("AddCustomerAccount",name, 500, 270, true);
		addCustomerAccountControl = new AddCustomerAccountControl();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddCustomerAccount(Object object) {
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(450);
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
				Record.copyAttributesInto(record.getAttributeAsRecord("customer"), customerAccountForm.getValuesAsRecord(), customerAccountForm.getValuesAsRecord().getAttributes());
				CustomerAccountList.addCustomerAccountToListAsRecord(record);
				hide();
			}
		});
		
		controlLayout.setHeight(40);
		controlLayout.setMargin(3);
		controlLayout.addMembers(submit, cancel);

		customerAccountLayout = new HLayout();

		customerAccountForm = new DynamicForm();
		customerAccountForm.setItemLayout(FormLayoutType.TABLE);
		customerAccountForm.setNumCols(4);
		customerAccountForm.setWidth100();
		customerAccountForm.setTitleWidth(100);
		customerAccountForm.setTitleSuffix("");

		TextItem customerAccountID = new TextItem();
		customerAccountID.setName("customerAccountId");
		customerAccountID.setTitle("เลขที่บัญชี");
		customerAccountID.setColSpan(3);
		TextItem customerAccountNameTH = new TextItem();
		customerAccountNameTH.setName("customerAccountNameTH");
		customerAccountNameTH.setTitle("ชื่อไทย");
		customerAccountNameTH.setWidth("*");
		customerAccountNameTH.setColSpan(3);
		TextItem customerAccountNameEN = new TextItem();
		customerAccountNameEN.setName("customerAccountNameEN");
		customerAccountNameEN.setTitle("ชื่ออังกฤษ");
		customerAccountNameEN.setWidth("*");
		customerAccountNameEN.setColSpan(3);

		PickerIcon customerAccountControlSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				addCustomerAccountControl.showAddCustomerAccountControl(null);
			}
		});

		TextItem customerAccountControl = new TextItem();
		customerAccountControl.setName("customerAccountControl");
		customerAccountControl.setTitle("บัญชีคุม");
		customerAccountControl.setIcons(customerAccountControlSearch);
		
		TextItem customerAccountLevel = new TextItem();
		customerAccountLevel.setName("customerAccountLevel");
		customerAccountLevel.setTitle("ระดับบัญชี");
		customerAccountLevel.setWidth(30);
		
		SelectItem customerAccountGroup = new SelectItem();
		customerAccountGroup.setName("customerAccountGroup");
		customerAccountGroup.setTitle("หมวดบัญชี");
		customerAccountGroup.setColSpan(3);
		LinkedHashMap<String, String> customerAccountGroupValueMap = new LinkedHashMap<String, String> ();
		customerAccountGroupValueMap.put ("1", "1 - สินทรัพย์");
		customerAccountGroupValueMap.put ("2", "2 - หนี้สิน");
		customerAccountGroupValueMap.put ("3", "3 - ส่วนของผู้ถือหุ้น");
		customerAccountGroupValueMap.put ("4", "4 - รายได้");
		customerAccountGroupValueMap.put ("5", "5 - ค่าใช้จ่าย");
	    customerAccountGroup.setValueMap(customerAccountGroupValueMap);
		
		
		SelectItem customerAccountType = new SelectItem();
		customerAccountType.setName("customerAccountType");
		customerAccountType.setTitle("ประเภท");
		LinkedHashMap<String, String> customerAccountTypeValueMap = new LinkedHashMap<String, String> ();
		customerAccountTypeValueMap.put ("0", "0 - บัญชีย่อย");
		customerAccountTypeValueMap.put ("1", "1 - บัญชีคุม");
		customerAccountType.setValueMap(customerAccountTypeValueMap);
		customerAccountType.setColSpan(3);
		
		TextItem customerAccountIsSplitDepartment = new TextItem();
		customerAccountIsSplitDepartment.setName("customerAccountIsSplitDepartment");
		customerAccountIsSplitDepartment.setTitle("แยกแผนก");
		customerAccountIsSplitDepartment.setWidth(30);

		customerAccountForm.setFields(customerAccountID, customerAccountNameTH, customerAccountNameEN, customerAccountControl, customerAccountLevel, customerAccountGroup, customerAccountType, customerAccountIsSplitDepartment);

		customerAccountForm.setHeight(50);
		customerAccountLayout.addMember(customerAccountForm);
		customerAccountLayout.setHeight(50);
		customerAccountLayout.setMargin(3);

		mainStack.addMembers(customerAccountLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
