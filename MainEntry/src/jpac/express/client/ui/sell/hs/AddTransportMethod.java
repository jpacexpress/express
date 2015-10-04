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

public class AddTransportMethod extends CustomWindow {

	private VStack mainStack;
	private HLayout transportMethodIdLayout;
	private HLayout transportMethodDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm transportMethodIdForm;
	private DynamicForm transportMethodDetailsForm;

	private TextItem transportMethodIdTxt;
	private StaticTextItem shortNameDescriptionTxt;
	private StaticTextItem detailsDescriptionTxt;
	private TextItem transportMethodShortNameTH;
	private TextItem transportMethodDetailsTH;
	private TextItem transportMethodShortNameEN;
	private TextItem transportMethodDetailsEN;
	private TextItem transportMethodId;

	private IButton submit;
	private IButton cancel;

	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddTransportMethod() {
		super(Constants.SELL_HS_ADDTRANSPORTMETHOD_ID,Constants.SELL_HS_ADDTRANSPORTMETHOD_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddTransportMethod(Record record,String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			transportMethodIdForm.setValues(record.toMap());
			transportMethodIdForm.setCanEdit(false);
			transportMethodDetailsForm.editSelectedData(TransportMethodList.getInstance().getTransportMethodList());
		}
		centerInPage();
		show();
	}
	
	public void showAddTransportMethod() {
		transportMethodIdForm.clearValues();
		transportMethodDetailsForm.clearValues();
		transportMethodIdForm.setCanEdit(true);
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		transportMethodIdLayout = new HLayout();
		transportMethodIdLayout.setHeight(25);
		transportMethodDetailsLayout = new HLayout();
		transportMethodDetailsLayout.setHeight(25);
		transportMethodDetailsLayout.setBorder("1px solid white");
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
				action = Constants.RECORD_ACTION_NULL;
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				transportMethodDetailsForm.getField(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID).setValue(transportMethodIdForm.getField(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID).getValue());
				Record record = new Record();
				record.setAttribute("customer", new Record());
				record.getAttributeAsRecord("customer").setAttribute("transportMethod", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod"), transportMethodDetailsForm.getValuesAsRecord(), transportMethodDetailsForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					transportMethodDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDTRANSPORTMETHOD_ID);
				} else {
					TransportMethodList.addTransportMethodToListAsRecord(record,action);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					transportMethodDetailsForm.getField(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID).setValue(transportMethodIdForm.getField(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID).getValue());
					Record record = new Record();
					record.setAttribute("customer", new Record());
					record.getAttributeAsRecord("customer").setAttribute("transportMethod", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod"), transportMethodDetailsForm.getValuesAsRecord(), transportMethodDetailsForm.getValuesAsRecord().getAttributes());

					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						transportMethodDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDTRANSPORTMETHOD_ID);
					} else {
						TransportMethodList.addTransportMethodToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		transportMethodIdForm = new DynamicForm();
		transportMethodIdForm.setTitleWidth(44);
		transportMethodIdForm.setWidth("95%");
		transportMethodDetailsForm = new DynamicForm();
		transportMethodDetailsForm.setDataSource(DataSource.get("transportMethod"));
		transportMethodDetailsForm.setWidth("95%");
		transportMethodDetailsForm.setMargin(5);

		transportMethodIdTxt = new TextItem();
		transportMethodIdTxt.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID);
		transportMethodIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		transportMethodIdTxt.setWidth(100);
		transportMethodIdForm.setItemLayout(FormLayoutType.TABLE);
		transportMethodIdForm.setTitleSuffix("");
		transportMethodIdForm.setHeight(25);
		transportMethodIdForm.setNumCols(4);
		transportMethodIdForm.setFields(transportMethodIdTxt);

		shortNameDescriptionTxt = new StaticTextItem("");
		shortNameDescriptionTxt.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescriptionTxt.setAlign(Alignment.CENTER);

		detailsDescriptionTxt = new StaticTextItem("");
		detailsDescriptionTxt.setAlign(Alignment.CENTER);
		detailsDescriptionTxt.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescriptionTxt.setShowTitle(false);
		detailsDescriptionTxt.setColSpan(2);
		detailsDescriptionTxt.setWidth(300);

		transportMethodShortNameTH = new TextItem();
		transportMethodShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		transportMethodShortNameTH.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODSHORTNAME_TH);

		transportMethodDetailsTH = new TextItem();
		transportMethodDetailsTH.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODDETAILS_TH);
		transportMethodDetailsTH.setShowTitle(false);
		transportMethodDetailsTH.setWidth(300);
		transportMethodDetailsTH.setColSpan(2);

		transportMethodShortNameEN = new TextItem();
		transportMethodShortNameEN.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODSHORTNAME_EN);
		transportMethodShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		transportMethodDetailsEN = new TextItem();
		transportMethodDetailsEN.setShowTitle(false);
		transportMethodDetailsEN.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODDETAILS_EN);
		transportMethodDetailsEN.setWidth(300);
		transportMethodDetailsEN.setColSpan(2);
		transportMethodDetailsForm.setItemLayout(FormLayoutType.TABLE);
		transportMethodDetailsForm.setTitleSuffix("");
		transportMethodDetailsForm.setHeight(25);
		transportMethodDetailsForm.setNumCols(4);
		transportMethodId = new TextItem();
		transportMethodId.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID);
		transportMethodDetailsForm.setFields(transportMethodId, shortNameDescriptionTxt, detailsDescriptionTxt, transportMethodShortNameTH, transportMethodDetailsTH, transportMethodShortNameEN, transportMethodDetailsEN);
		transportMethodDetailsForm.hideItem(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID);
		
		transportMethodIdLayout.addMember(transportMethodIdForm);
		transportMethodDetailsLayout.addMember(transportMethodDetailsForm);
		mainStack.addMembers(transportMethodIdLayout, transportMethodDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
