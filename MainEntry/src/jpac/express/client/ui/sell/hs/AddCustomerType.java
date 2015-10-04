package jpac.express.client.ui.sell.hs;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
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

public class AddCustomerType extends CustomWindow {

	private VStack mainStack;
	private HLayout customerTypeIdLayout;
	private HLayout customerTypeDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm customerTypeIdForm;
	private DynamicForm customerTypeDetailsForm;

	private TextItem customerTypeIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem customerTypeShortNameTH;
	private TextItem customerTypeDetailsTH;
	private TextItem customerTypeShortNameEN;
	private TextItem customerTypeDetailsEN;
	private TextItem customerTypeId;

	private IButton submit;
	private IButton cancel;

	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddCustomerType() {
		super(Constants.SELL_HS_ADDCUSTOMERTYPE_ID,Constants.SELL_HS_ADDCUSTOMERTYPE_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddCustomerType() {
		customerTypeIdForm.clearValues();
		customerTypeIdForm.setCanEdit(true);
		customerTypeDetailsForm.clearValues();
		centerInPage();
		show();
	}
	
	public void showAddCustomerType(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			customerTypeIdForm.setValues(record.toMap());
			customerTypeIdForm.setCanEdit(false);
			customerTypeDetailsForm.editSelectedData(CustomerTypeList.getInstance().getCustomerTypeList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		customerTypeIdLayout = new HLayout();
		customerTypeIdLayout.setHeight(25);
		customerTypeDetailsLayout = new HLayout();
		customerTypeDetailsLayout.setHeight(25);
		customerTypeDetailsLayout.setBorder("1px solid white");
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
				customerTypeDetailsForm.getField(Constants.CUSTOMERTYPE_CUSTOMERTYPEID).setValue(customerTypeIdForm.getField(Constants.CUSTOMERTYPE_CUSTOMERTYPEID).getValue());
				Record record = new Record();
				record.setAttribute("customer", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("customer"), customerTypeDetailsForm.getValuesAsRecord(), customerTypeDetailsForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					customerTypeDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDCUSTOMERTYPE_ID);
				} else {
					CustomerTypeList.addCystomerTypeToListAsRecord(record,action);
				}
				
				hide();
			}
		});
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					customerTypeDetailsForm.getField(Constants.CUSTOMERTYPE_CUSTOMERTYPEID).setValue(customerTypeIdForm.getField(Constants.CUSTOMERTYPE_CUSTOMERTYPEID).getValue());
					Record record = new Record();
					record.setAttribute("customer", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("customer"), customerTypeDetailsForm.getValuesAsRecord(), customerTypeDetailsForm.getValuesAsRecord().getAttributes());
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						customerTypeDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDCUSTOMERTYPE_ID);
					} else {
						CustomerTypeList.addCystomerTypeToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		customerTypeIdForm = new DynamicForm();
		customerTypeIdForm.setTitleWidth(44);
		customerTypeIdForm.setWidth("95%");
		customerTypeDetailsForm = new DynamicForm();
		customerTypeDetailsForm.setDataSource(DataSource.get("customerType"));
		customerTypeDetailsForm.setWidth("95%");
		customerTypeDetailsForm.setMargin(5);

		customerTypeIdTxt = new TextItem();
		customerTypeIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		customerTypeIdTxt.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEID);
		customerTypeIdTxt.setWidth(100);
		customerTypeIdForm.setItemLayout(FormLayoutType.TABLE);
		customerTypeIdForm.setTitleSuffix("");
		customerTypeIdForm.setHeight(25);
		customerTypeIdForm.setNumCols(4);
		customerTypeIdForm.setFields(customerTypeIdTxt);

		shortNameDescription = new StaticTextItem("");
		shortNameDescription.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescription.setAlign(Alignment.CENTER);

		detailsDescription = new StaticTextItem("");
		detailsDescription.setAlign(Alignment.CENTER);
		detailsDescription.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescription.setShowTitle(false);
		detailsDescription.setColSpan(2);
		detailsDescription.setWidth(300);

		customerTypeShortNameTH = new TextItem();
		customerTypeShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		customerTypeShortNameTH.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_TH);
		customerTypeDetailsTH = new TextItem();
		customerTypeDetailsTH.setShowTitle(false);
		customerTypeDetailsTH.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_TH);
		customerTypeDetailsTH.setWidth(300);
		customerTypeDetailsTH.setColSpan(2);
		customerTypeShortNameEN = new TextItem();
		customerTypeShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		customerTypeShortNameEN.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_EN);
		customerTypeDetailsEN = new TextItem();
		customerTypeDetailsEN.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_EN);
		customerTypeDetailsEN.setShowTitle(false);
		customerTypeDetailsEN.setWidth(300);
		customerTypeDetailsEN.setColSpan(2);
		customerTypeId = new TextItem();
		customerTypeId.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEID);
		customerTypeDetailsForm.setItemLayout(FormLayoutType.TABLE);
		customerTypeDetailsForm.setTitleSuffix("");
		customerTypeDetailsForm.setHeight(25);
		customerTypeDetailsForm.setNumCols(4);
		customerTypeDetailsForm.setFields(customerTypeId, shortNameDescription, detailsDescription, customerTypeShortNameTH, customerTypeDetailsTH, customerTypeShortNameEN, customerTypeDetailsEN);
		customerTypeDetailsForm.hideItem(Constants.CUSTOMERTYPE_CUSTOMERTYPEID);
		
		customerTypeIdLayout.addMember(customerTypeIdForm);
		customerTypeDetailsLayout.addMember(customerTypeDetailsForm);
		mainStack.addMembers(customerTypeIdLayout, customerTypeDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
