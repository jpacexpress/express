package jpac.express.client.ui.buy.rr;

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

public class AddPaymentConditions extends CustomWindow {

	private static final String name = Constants.ADD_REMARK_NAME;
	private VStack mainStack;
	private HLayout notesLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm pmcForm;

	private TextItem pmcon;
	private TextItem pmconId;

	private IButton submit;
	private IButton cancel;

	public AddPaymentConditions() {
		super(Constants.RR_ADD_PAYMENT_CON_LISTID, name, 600, 100, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();	
	}

	public void showAddPaymentConditions() {
		pmcForm.clearValues();
		pmcForm.setCanEdit(true);
		centerInPage();
		show();
		pmcForm.focusInItem(Constants.RR_PAYMENT_CON_NAME);
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		notesLayout = new HLayout();
		notesLayout.setHeight(25);
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
				pmcForm.clearValues();
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {			
				Record record = new Record();
				record.setAttribute(Constants.RR_PAYMENT_CON_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_PAYMENT_CON_RECORD), pmcForm.getValuesAsRecord(), pmcForm.getValuesAsRecord().getAttributes());
				PaymentConditionsList.addPaymentConditionsToListAsRecord(record);
				pmcForm.clearValues();
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {					
					Record record = new Record();
					record.setAttribute(Constants.RR_PAYMENT_CON_RECORD, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_PAYMENT_CON_RECORD), pmcForm.getValuesAsRecord(), pmcForm.getValuesAsRecord().getAttributes());
					PaymentConditionsList.addPaymentConditionsToListAsRecord(record);
					pmcForm.clearValues();
					hide();
				}
			}
		});
		controlLayout.addMembers(submit, cancel);

		pmcForm = new DynamicForm();
		pmcForm.setTitleWidth(44);
		pmcForm.setWidth("95%");
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		pmconId = new TextItem("");
		pmconId.setName(Constants.RR_PAYMENT_CONID);
		pmconId.setShowTitle(false);
		
		pmcon = new TextItem("");
		pmcon.setName(Constants.RR_PAYMENT_CON_NAME);
		pmcon.setShowTitle(false);
		pmcon.setWidth(500);
		pmcForm.setTitleSuffix("");
		pmcForm.setHeight(25);
		pmcForm.setFields(autoId,pmconId, pmcon);
		pmcForm.hideItem(Constants.RR_PAYMENT_CONID);
		pmcForm.hideItem(Constants.IDS);

		notesLayout.addMember(pmcForm);
		mainStack.addMembers(notesLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
