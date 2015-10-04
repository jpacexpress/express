package jpac.express.client.ui.buy.rr;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

//import jpac.express.client.datasource.VendorTypesDS;
import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddVendorType extends CustomWindow {

	private static final String name = Constants.RR_ADD_VENDOR_TYPE_NAME;
	private VStack mainStack;
	private HLayout vendorTypeIdLayout;
	private HLayout vendorTypeDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm vendorTypeIdForm;
	private DynamicForm vendorTypeDetailsForm;

	private TextItem vendorTypeIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem vendorTypeShortNameTH;
	private TextItem vendorTypeDetailsTH;
	private TextItem vendorTypeShortNameEN;
	private TextItem vendorTypeDetailsEN;
	private TextItem vendorTypeId;

	private IButton submit;
	private IButton cancel;

	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddVendorType() {
		super(Constants.RR_ADD_VENDOR_TYPEID,name, 600, 200, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddVendorType() {
		vendorTypeIdForm.clearValues();
		vendorTypeIdForm.setCanEdit(true);
		vendorTypeDetailsForm.clearValues();
		centerInPage();
		show();
		vendorTypeIdForm.focusInItem(Constants.VENDOR_TYPEID);
	}
	
	public void showAddVendorType(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			vendorTypeDetailsForm.setValues(record.toMap());
			vendorTypeIdForm.setCanEdit(false);
			vendorTypeDetailsForm.editSelectedData(VendorTypeList.getInstance().getVendorTypeList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		vendorTypeIdLayout = new HLayout();
		vendorTypeIdLayout.setHeight(25);
		vendorTypeDetailsLayout = new HLayout();
		vendorTypeDetailsLayout.setHeight(25);
		vendorTypeDetailsLayout.setBorder(Constants.BORDER_WHITE_THIN);
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
				vendorTypeDetailsForm.getField(Constants.VENDOR_TYPEID).setValue(vendorTypeIdForm.getField(Constants.VENDOR_TYPEID).getValue());
				Record record = new Record();
				record.setAttribute(Constants.RR_VENDOR_TYPE_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_VENDOR_TYPE_RECORD), vendorTypeDetailsForm.getValuesAsRecord(), vendorTypeDetailsForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					vendorTypeDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
				} else {
					VendorTypeList.addVendorTypeToListAsRecord(record,action);
				}
				
				hide();
			}
		});
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					vendorTypeDetailsForm.getField(Constants.VENDOR_TYPEID).setValue(vendorTypeIdForm.getField(Constants.VENDOR_TYPEID).getValue());
					Record record = new Record();
					record.setAttribute(Constants.RR_VENDOR_TYPE_RECORD, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_VENDOR_TYPE_RECORD), vendorTypeDetailsForm.getValuesAsRecord(), vendorTypeDetailsForm.getValuesAsRecord().getAttributes());
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						vendorTypeDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
					} else {
						VendorTypeList.addVendorTypeToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		vendorTypeIdForm = new DynamicForm();
		vendorTypeIdForm.setTitleWidth(44);
		vendorTypeIdForm.setWidth("95%");
		vendorTypeDetailsForm = new DynamicForm();
		//vendorTypeDetailsForm.setDataSource(VendorTypesDS.getInstance());
		vendorTypeDetailsForm.setWidth("95%");
		vendorTypeDetailsForm.setMargin(5);

		vendorTypeIdTxt = new TextItem();
		vendorTypeIdTxt.setName(Constants.VENDOR_TYPEID);
		vendorTypeIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		vendorTypeIdTxt.setWidth(100);
		vendorTypeIdForm.setItemLayout(FormLayoutType.TABLE);
		vendorTypeIdForm.setTitleSuffix("");
		vendorTypeIdForm.setHeight(25);
		vendorTypeIdForm.setNumCols(4);
		vendorTypeIdForm.setFields(vendorTypeIdTxt);

		shortNameDescription = new StaticTextItem(Constants.SHORTNAME_TITLE);
		shortNameDescription.setTitle(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescription.setTitleOrientation(TitleOrientation.TOP);
		shortNameDescription.setAlign(Alignment.CENTER);
		shortNameDescription.setColSpan(2);

		detailsDescription = new StaticTextItem(Constants.FULLNAME_TITLE);
		detailsDescription.setTitle(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescription.setAlign(Alignment.CENTER);
		detailsDescription.setTitleOrientation(TitleOrientation.TOP);		
		//detailsDescription.setShowTitle(false);
		detailsDescription.setColSpan(2);
		detailsDescription.setWidth(300);

		vendorTypeShortNameTH = new TextItem();
		vendorTypeShortNameTH.setName(Constants.VENDOR_TYPE_SHORTNAME_TH);
		vendorTypeShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		vendorTypeDetailsTH = new TextItem();
		vendorTypeDetailsTH.setShowTitle(false);
		vendorTypeDetailsTH.setName(Constants.VENDOR_TYPE_FULLNAME_TH);
		vendorTypeDetailsTH.setWidth(300);
		vendorTypeDetailsTH.setColSpan(2);
		vendorTypeShortNameEN = new TextItem();
		vendorTypeShortNameEN.setName(Constants.VENDOR_TYPE_SHORTNAME_EN);
		vendorTypeShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		vendorTypeDetailsEN = new TextItem();
		vendorTypeDetailsEN.setName(Constants.VENDOR_TYPE_FULLNAME_EN);
		vendorTypeDetailsEN.setShowTitle(false);
		vendorTypeDetailsEN.setWidth(300);
		vendorTypeDetailsEN.setColSpan(2);
		vendorTypeId = new TextItem();
		vendorTypeId.setName(Constants.VENDOR_TYPEID);
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		vendorTypeDetailsForm.setItemLayout(FormLayoutType.TABLE);
		vendorTypeDetailsForm.setTitleSuffix("");
		vendorTypeDetailsForm.setHeight(25);
		vendorTypeDetailsForm.setNumCols(4);
		vendorTypeDetailsForm.setFields(autoId,vendorTypeId, shortNameDescription, detailsDescription, vendorTypeShortNameTH, vendorTypeDetailsTH, vendorTypeShortNameEN, vendorTypeDetailsEN);
		vendorTypeDetailsForm.hideItem(Constants.VENDOR_TYPEID);
		vendorTypeDetailsForm.hideItem(Constants.IDS);
		
		vendorTypeIdLayout.addMember(vendorTypeIdForm);
		vendorTypeDetailsLayout.addMember(vendorTypeDetailsForm);
		mainStack.addMembers(vendorTypeIdLayout, vendorTypeDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
