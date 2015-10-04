package jpac.express.client.ui.custom;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class CenteredLayout extends HLayout {

	private VStack vStack;
	private HLayout hLayout;

	public CenteredLayout(int width) {
		super();
		configWidgets(width);
	}
	
	public CenteredLayout() {
		super();
		configWidgets();
	}

	private void configWidgets() {
		setWidth100();
		setHeight100();
		setAlign(Alignment.CENTER);

		vStack = new VStack();
		vStack.setAlign(VerticalAlignment.CENTER);

		hLayout = new HLayout();
		hLayout.setWidth100();
		hLayout.setAlign(Alignment.CENTER);

		hLayout.addMember(vStack);
		addMember(hLayout);
	}
	
	private void configWidgets(int width) {
		setWidth100();
		setHeight100();
		setAlign(Alignment.CENTER);

		vStack = new VStack();
		vStack.setWidth(width);
		vStack.setAlign(VerticalAlignment.CENTER);

		hLayout = new HLayout();
		hLayout.setWidth100();
		hLayout.setAlign(Alignment.CENTER);

		hLayout.addMember(vStack);
		addMember(hLayout);
	}

	public void setItemWidth(int width) {
		vStack.setWidth(width);
	}

	public void add(HLayout widget) {
		widget.setWidth100();
		widget.setAlign(Alignment.CENTER);
		vStack.addMember(widget);
	}

	public void drawWidgets() {
		draw();
	}

	public void add(VStack widget) {
		widget.setWidth100();
		widget.setAlign(Alignment.CENTER);
		vStack.addMember(widget);
	}
}
