package jpac.express.client.ui.custom;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class CustomControlMenu extends ToolStrip {

	private String buttonSize = "40px";
	private int iconSize = 25;

	public CustomControlMenu() {
		super();
	}

	public ToolStripButton getButton(String id) {
		return (ToolStripButton) getMember(id);
	}
	
	public void newButton(String name, String iconName, com.smartgwt.client.widgets.events.ClickHandler clickHandler) {
		ToolStripButton newButton = new ToolStripButton();
		newButton.setTitle("");
		newButton.setSize(buttonSize, buttonSize);
		newButton.setID(name);
		newButton.setIcon(GWT.getHostPageBaseURL() + "images/controls/" + iconName +".jpg");
		newButton.setIconSize(iconSize);
		newButton.setBackgroundColor("#F0F0F0");
		newButton.setIconSpacing(0);
		newButton.setAlign(Alignment.CENTER);
		newButton.addClickHandler(clickHandler);
		//newButton.setShowTitle(false);
		addMember(newButton);
	}

	public void addSeparatorMenu() {
		this.addSeparator();
	}

	public void addButton(ToolStripButton btn) {
		btn.setSize(buttonSize, buttonSize);
		btn.setIconSize(iconSize);
		btn.setBackgroundColor("#F0F0F0");
		btn.setIconSpacing(0);
		btn.setAlign(Alignment.CENTER);
		this.addMember(btn);
	}

	public String getButtonSize() {
		return buttonSize;
	}

	public void setButtonSize(String buttonSize) {
		this.buttonSize = buttonSize;
	}

	public int getIconSize() {
		return iconSize;
	}

	public void setIconSize(int iconSize) {
		this.iconSize = iconSize;
	}
}
