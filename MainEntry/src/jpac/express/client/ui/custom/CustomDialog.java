package jpac.express.client.ui.custom;

import jpac.express.shared.Constants;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class CustomDialog extends Window {

	private static CustomDialog instance;
	
	private final HLayout msgHLayout = new HLayout();
	private final VStack msgVLayout = new VStack();
	private final Label msgLabel = new Label();
	private final IButton msgButton = new IButton(Constants.BUTTON_TITLE_SUBMIT);

	private final HLayout msgLabelLayout = new HLayout();
	private final HLayout msgButtonLayout = new HLayout();

	public CustomDialog() {
		super();
		msgLabelLayout.setWidth100();
		msgLabelLayout.setAlign(Alignment.CENTER);
		msgLabelLayout.setHeight(20);
		msgLabelLayout.setPadding(10);
		msgButtonLayout.setWidth100();
		msgButtonLayout.setAlign(Alignment.CENTER);
		msgButtonLayout.setHeight(20);

		msgLabel.setHeight(20);
		msgLabel.setWidth100();

		msgLabel.setAlign(Alignment.CENTER);
		msgLabelLayout.addMember(msgLabel);
		msgButtonLayout.addMember(msgButton);

		msgVLayout.setWidth(300);
		msgVLayout.setHeight(80);
		msgVLayout.setAlign(VerticalAlignment.CENTER);
		msgVLayout.addMembers(msgLabelLayout, msgButtonLayout);

		msgHLayout.setWidth100();
		msgHLayout.setHeight(80);
		msgHLayout.setAlign(Alignment.CENTER);
		msgHLayout.addMember(msgVLayout);

		setWidth(360);
		setHeight(115);
		setTitle("");
		setShowMinimizeButton(false);
		setShowCloseButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setAlign(Alignment.CENTER);
		centerInPage();
		addItem(msgHLayout);

		msgButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				hide();
			}
		});
		hide();
	}

	public void getWaitLoadingDialog() {
		//msgButtonLayout.removeMember(msgButton);
		setHeight(85);
		msgVLayout.setHeight(40);
		msgVLayout.removeMember(msgButtonLayout);
		Say("รอซักครู่");
		show();
	}
	
	public static CustomDialog getInstance() {
		if (instance == null) {
			instance = new CustomDialog();
		}
		return instance;
	}
	
	public void Say(String msg) {
		msgLabel.setContents("<span style='font-size: 12px !important;'>" + msg + "</span>");
		this.show();
	}

	public void Say(String msg, String color) {
		msgLabel.setContents("<span style='font-size: 12px !important;color: " + color + ";'>" + msg + "</span>");
		this.show();
	}

}
