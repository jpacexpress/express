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

public class AddPaymentTypes extends CustomWindow {

	private static final String name = Constants.RR_ADD_PAYMENT_TYPE_LIST_NAME;
	private VStack mainStack;
	private HLayout paymentLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm paymentForm;

	private TextItem paymentName;
	private TextItem paymentCode;
	private IButton submit;
	private IButton cancel;

	public AddPaymentTypes() {
		super(Constants.RR_ADD_PAYMENT_TYPE_LISTID, name, 600, 100, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();	
	}

	public void showAddPayment() {
		paymentForm.clearValues();
		paymentForm.setCanEdit(true);
		centerInPage();
		show();
		paymentForm.focusInItem(Constants.RR_PAYMENT_TYPE_NAME);
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		paymentLayout = new HLayout();
		paymentLayout.setHeight(25);
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
				paymentForm.clearValues();
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//paymentForm.getField("paymentText").setValue(paymentForm.getField("paymentText").getValue());
				Record record = new Record();
				record.setAttribute(Constants.RR_PAYMENT_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_PAYMENT_RECORD), paymentForm.getValuesAsRecord(), paymentForm.getValuesAsRecord().getAttributes());
				PaymentTypesList.addPaymentToListAsRecord(record);
				paymentForm.clearValues();
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					//paymentForm.getField(Constants.RR_REMARK_RECORD).setValue(paymentForm.getField(Constants.RR_REMARK_RECORD).getValue());
					Record record = new Record();
					record.setAttribute(Constants.RR_PAYMENT_RECORD, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_PAYMENT_RECORD), paymentForm.getValuesAsRecord(), paymentForm.getValuesAsRecord().getAttributes());
					PaymentTypesList.addPaymentToListAsRecord(record);
					paymentForm.clearValues();
					hide();
				}
			}
		});
		controlLayout.addMembers(submit, cancel);

		paymentForm = new DynamicForm();
		paymentForm.setTitleWidth(44);
		paymentForm.setWidth("95%");
		
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		
		paymentCode = new TextItem("");
		paymentCode.setName(Constants.RR_PAYMENT_TYPEID);
		paymentCode.setShowTitle(false);
		
		paymentName = new TextItem("");
		paymentName.setName(Constants.RR_PAYMENT_TYPE_NAME);
		paymentName.setShowTitle(false);
		paymentName.setWidth(500);
		paymentForm.setTitleSuffix("");
		paymentForm.setHeight(25);
		paymentForm.setFields(autoId,paymentCode, paymentName);
		paymentForm.hideItem(Constants.RR_PAYMENT_TYPEID);
		paymentForm.hideItem(Constants.IDS);
		
		paymentLayout.addMember(paymentForm);
		mainStack.addMembers(paymentLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
