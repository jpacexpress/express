package jpac.express.client.ui.custom;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class CustomDataTabSet extends TabSet {
	public CustomDataTabSet() {
		super();
		setTabBarPosition(Side.TOP);
		setTabBarAlign(Side.LEFT);
		setHeight("100%");
	}

	public void setCustomDataTabSetMinHeight(int height) {
		this.setMinHeight(height);
	}

	public void setCustomDataTabSetMaxHeight(int height) {
		this.setMaxHeight(height);
	}

	public Tab getCustomTab(String id) {
		return getTab(id);
	}

	public void newCustomTab(String id, String title) {
		Tab tab = new Tab(title);
		tab.setID(id);
		addTab(tab);
	}

	public void addCustomTab(Tab tab) {
		addTab(tab);
	}
}
