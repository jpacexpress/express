package jpac.express.client.ui.buy.rr;

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
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddVendorAccount extends CustomWindow {

	private static final String name = Constants.RR_ADD_VENDOR_ACC_LIST_NAME;
	private VStack mainStack;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private HLayout vendorAccountLayout;

	private AddVendorAccountControl addVendorAccountControl;

	private DynamicForm vendorAccountForm;

	private IButton submit;
	private IButton cancel;

	public AddVendorAccount() {
		super(Constants.RR_ADD_VENDOR_ACC_LISTID , name, 500, 270, true);
		addVendorAccountControl = new AddVendorAccountControl();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddVendorAccount(Object object) {
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
				record.setAttribute(Constants.RR_VENDOR_ACC_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_VENDOR_ACC_RECORD), vendorAccountForm.getValuesAsRecord(), vendorAccountForm.getValuesAsRecord().getAttributes());
				VendorAccountList.addVendorAccountToListAsRecord(record);
				hide();
			}
		});
		
		controlLayout.setHeight(40);
		controlLayout.setMargin(3);
		controlLayout.addMembers(submit, cancel);

		vendorAccountLayout = new HLayout();

		vendorAccountForm = new DynamicForm();
		vendorAccountForm.setItemLayout(FormLayoutType.TABLE);
		vendorAccountForm.setNumCols(4);
		vendorAccountForm.setWidth100();
		vendorAccountForm.setTitleWidth(100);
		vendorAccountForm.setTitleSuffix("");
		
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		
		TextItem vendorAccountID = new TextItem();
		vendorAccountID.setName(Constants.VENDOR_ACCID);
		vendorAccountID.setTitle(Constants.ACCOUNT_ADD_NO);
		vendorAccountID.setColSpan(3);
		TextItem vendorAccountNameTH = new TextItem();
		vendorAccountNameTH.setName(Constants.VENDOR_ACC_NAME_TH);
		vendorAccountNameTH.setTitle(Constants.ACCOUNT_ADD_NAME_TH);
		vendorAccountNameTH.setWidth("*");
		vendorAccountNameTH.setColSpan(3);
		TextItem vendorAccountNameEN = new TextItem();
		vendorAccountNameEN.setName(Constants.VENDOR_ACC_NAME_EN);
		vendorAccountNameEN.setTitle(Constants.ACCOUNT_ADD_NAME_EN);
		vendorAccountNameEN.setWidth("*");
		vendorAccountNameEN.setColSpan(3);

		PickerIcon vendorAccountControlSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				addVendorAccountControl.showAddVendorAccountControl(null);
			}
		});

		TextItem vendorAccountControl = new TextItem();
		vendorAccountControl.setName(Constants.VENDOR_ACC_CONTROL);
		vendorAccountControl.setTitle(Constants.ACCOUNT_ADD_CONTROL);
		vendorAccountControl.setIcons(vendorAccountControlSearch);
		
		TextItem vendorAccountLevel = new TextItem();
		vendorAccountLevel.setName(Constants.VENDOR_ACC_LEVEL);
		vendorAccountLevel.setTitle(Constants.ACCOUNT_ADD_LEVEL);
		vendorAccountLevel.setWidth(30);
		
		SelectItem vendorAccountGroup = new SelectItem();
		vendorAccountGroup.setName(Constants.VENDOR_ACC_GROUP);
		vendorAccountGroup.setTitle(Constants.ACCOUNT_ADD_CATEGORY);
		vendorAccountGroup.setColSpan(3);
		LinkedHashMap<String, String> vendorAccountGroupValueMap = new LinkedHashMap<String, String> ();
		vendorAccountGroupValueMap.put (Constants.ACCOUNT_CATEGORY_VALUE_1, Constants.ACCOUNT_CATEGORY_1);
		vendorAccountGroupValueMap.put (Constants.ACCOUNT_CATEGORY_VALUE_2, Constants.ACCOUNT_CATEGORY_2);
		vendorAccountGroupValueMap.put (Constants.ACCOUNT_CATEGORY_VALUE_3, Constants.ACCOUNT_CATEGORY_3);
		vendorAccountGroupValueMap.put (Constants.ACCOUNT_CATEGORY_VALUE_4, Constants.ACCOUNT_CATEGORY_4);
		vendorAccountGroupValueMap.put (Constants.ACCOUNT_CATEGORY_VALUE_5, Constants.ACCOUNT_CATEGORY_5);
	    vendorAccountGroup.setValueMap(vendorAccountGroupValueMap);
		
		
		SelectItem vendorAccountType = new SelectItem();
		vendorAccountType.setName(Constants.VENDOR_ACC_TYPES);
		vendorAccountType.setTitle(Constants.ACCOUNT_ADD_TYPES);
		LinkedHashMap<String, String> vendorAccountTypeValueMap = new LinkedHashMap<String, String> ();
		vendorAccountTypeValueMap.put (Constants.ACCOUNT_TYPES_VALUE_0, Constants.ACCOUNT_TYPES_0);
		vendorAccountTypeValueMap.put (Constants.ACCOUNT_TYPES_VALUE_0, Constants.ACCOUNT_TYPES_1);
		vendorAccountType.setValueMap(vendorAccountTypeValueMap);
		vendorAccountType.setColSpan(3);
		
		TextItem vendorAccountIsSplitDepartment = new TextItem();
		vendorAccountIsSplitDepartment.setName(Constants.VENDOR_ACC_SEP);
		vendorAccountIsSplitDepartment.setTitle(Constants.ACCOUNT_ADD_SEP_DEP);
		vendorAccountIsSplitDepartment.setWidth(30);

		vendorAccountForm.setFields(autoId,vendorAccountID, vendorAccountNameTH, vendorAccountNameEN, vendorAccountControl, vendorAccountLevel, vendorAccountGroup, vendorAccountType, vendorAccountIsSplitDepartment);
		vendorAccountForm.hideItem(Constants.IDS);
		vendorAccountForm.setHeight(50);
		vendorAccountLayout.addMember(vendorAccountForm);
		vendorAccountLayout.setHeight(50);
		vendorAccountLayout.setMargin(3);

		mainStack.addMembers(vendorAccountLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
