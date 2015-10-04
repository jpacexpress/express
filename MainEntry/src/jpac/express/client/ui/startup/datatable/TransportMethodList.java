package jpac.express.client.ui.startup.datatable;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class TransportMethodList extends CustomWindow {

	private String className = this.getClass().getSimpleName();

	private static TransportMethodList instance = null;

	private HLayout dataLayout;
	public final ListGrid transportMethodList;

	public TransportMethodList() {
		super(Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_ID, Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_TITLE, 1120, 600, false);
		transportMethodList = new ListGrid();
		transportMethodList.setDataSource(DataSource.get("transportMethod"));
		transportMethodList.setCanEdit(true);
		transportMethodList.setAutoSaveEdits(true);
		transportMethodList.setAutoFetchData(true);
		transportMethodList.setContextMenu(getTransportMethodListContextMenu());

		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					transportMethodList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					transportMethodList.startEditing(transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					transportMethodList.removeData(transportMethodList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}

		});
		transportMethodList.focus();
	}

	public static TransportMethodList getInstance() {
		if (instance == null) {
			instance = new TransportMethodList();
		}
		return instance;
	}

	public void showTransportMethodList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();

		transportMethodList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getTransportMethodListContextMenu().showContextMenu();
			}
		});
		transportMethodList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getTransportMethodListContextMenu().showContextMenu();
			}
		});

		final ListGridField transportMethodId = new ListGridField();
		transportMethodId.setTitle(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID_TITLE);
		transportMethodId.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODID);
		transportMethodId.setWidth("10%");

		final ListGridField transportMethodShortNameTH = new ListGridField();
		transportMethodShortNameTH.setTitle(Constants.TRANSPORTMETHOD_TRANSPORTMETHODSHORTNAME_TH_TITLE);
		transportMethodShortNameTH.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODSHORTNAME_TH);
		transportMethodShortNameTH.setWidth("10%");

		final ListGridField transportMethodShortNameEN = new ListGridField();
		transportMethodShortNameEN.setTitle(Constants.TRANSPORTMETHOD_TRANSPORTMETHODSHORTNAME_EN_TITLE);
		transportMethodShortNameEN.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODSHORTNAME_EN);
		transportMethodShortNameEN.setWidth("10%");

		final ListGridField transportMethodDetailsTH = new ListGridField();
		transportMethodDetailsTH.setTitle(Constants.TRANSPORTMETHOD_TRANSPORTMETHODDETAILS_TH_TITLE);
		transportMethodDetailsTH.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODDETAILS_TH);
		transportMethodDetailsTH.setWidth("50%");

		final ListGridField transportMethodDetailsEN = new ListGridField();
		transportMethodDetailsEN.setTitle(Constants.TRANSPORTMETHOD_TRANSPORTMETHODDETAILS_EN_TITLE);
		transportMethodDetailsEN.setName(Constants.TRANSPORTMETHOD_TRANSPORTMETHODDETAILS_EN);
		transportMethodDetailsEN.setWidth("20%");

		transportMethodList.addCellSavedHandler(new CellSavedHandler() {
			@Override
			public void onCellSaved(CellSavedEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_ID);
			}
		});
		transportMethodList.addEditorEnterHandler(new EditorEnterHandler() {
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				getCustomControlMenu().getButton("new" + className).disable();
				getCustomControlMenu().getButton("trash" + className).disable();
				getCustomControlMenu().getButton("save" + className).disable();
				getCustomControlMenu().getButton("gotofirst" + className).disable();
				getCustomControlMenu().getButton("gotoprevious" + className).disable();
				getCustomControlMenu().getButton("gotonext" + className).disable();
				getCustomControlMenu().getButton("gotolast" + className).disable();
				getCustomControlMenu().getButton("print" + className).disable();
			}
		});
		transportMethodList.addEditorExitHandler(new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
			}
		});

		transportMethodList.setFields(transportMethodId, transportMethodShortNameTH, transportMethodShortNameEN, transportMethodDetailsTH, transportMethodDetailsEN);
		dataLayout.addMember(transportMethodList);
		addDataSection(dataLayout);
	}

	protected Menu getTransportMethodListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				transportMethodList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				transportMethodList.startEditing(transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				transportMethodList.removeData(transportMethodList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_ID);
			}
		});
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}

	private void initControlMenu() {

		getCustomControlMenu().newButton("new" + className, "new", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("edit" + className).disable();
				getCustomControlMenu().getButton("trash" + className).disable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).disable();
				getCustomControlMenu().getButton("gotoprevious" + className).disable();
				getCustomControlMenu().getButton("gotonext" + className).disable();
				getCustomControlMenu().getButton("gotolast" + className).disable();
				getCustomControlMenu().getButton("print" + className).disable();

				transportMethodList.startEditingNew();

			}
		});

		getCustomControlMenu().newButton("edit" + className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (transportMethodList.getSelectedRecord() != null) {
					getCustomControlMenu().getButton("new" + className).disable();
					getCustomControlMenu().getButton("trash" + className).disable();
					getCustomControlMenu().getButton("save" + className).enable();
					getCustomControlMenu().getButton("gotofirst" + className).disable();
					getCustomControlMenu().getButton("gotoprevious" + className).disable();
					getCustomControlMenu().getButton("gotonext" + className).disable();
					getCustomControlMenu().getButton("gotolast" + className).disable();
					getCustomControlMenu().getButton("print" + className).disable();
					transportMethodList.startEditing(transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()));
				}
			}
		});

		getCustomControlMenu().newButton("trash" + className, "trash", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new" + className).disable();
				getCustomControlMenu().getButton("edit" + className).disable();

				getCustomControlMenu().getButton("gotofirst" + className).disable();
				getCustomControlMenu().getButton("gotoprevious" + className).disable();
				getCustomControlMenu().getButton("gotonext" + className).disable();
				getCustomControlMenu().getButton("gotolast" + className).disable();
				getCustomControlMenu().getButton("print" + className).disable();

				SC.confirm(Constants.CONFIRM_ASK_DELETE, new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						getCustomControlMenu().getButton("new" + className).enable();
						getCustomControlMenu().getButton("edit" + className).enable();
						getCustomControlMenu().getButton("trash" + className).enable();

						getCustomControlMenu().getButton("save" + className).disable();
						getCustomControlMenu().getButton("gotofirst" + className).enable();
						getCustomControlMenu().getButton("gotoprevious" + className).enable();
						getCustomControlMenu().getButton("gotonext" + className).enable();
						getCustomControlMenu().getButton("gotolast" + className).enable();
						getCustomControlMenu().getButton("print" + className).enable();
						if (value) {
							transportMethodList.removeData(transportMethodList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_ID);
						}
					}

				});

			}
		});

		getCustomControlMenu().newButton("cancel" + className, "cancel", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
			}
		});

		getCustomControlMenu().newButton("save" + className, "save", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
				transportMethodList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst" + className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (transportMethodList.getRecords().length >= 0) {
					transportMethodList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious" + className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (transportMethodList.getRecords().length >= 0) {
					if ((transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()) - 1) >= 0) {
						transportMethodList.selectSingleRecord((transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()) - 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext" + className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (transportMethodList.getRecords().length >= 0) {
					if ((transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()) + 1) <= (transportMethodList.getRecords().length - 1)) {
						transportMethodList.selectSingleRecord((transportMethodList.getRecordIndex(transportMethodList.getSelectedRecord()) + 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast" + className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (transportMethodList.getRecords().length >= 0) {
					transportMethodList.selectSingleRecord((transportMethodList.getRecords().length - 1));
				}
			}
		});

		getCustomControlMenu().newButton("print" + className, "print", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				VLayout printContainer = new VLayout();

				Label companyName = new Label();
				companyName.setContents("<span style='font-size: 16px !important;'>บริษัท</span>");
				Label title = new Label();
				title.setContents("<span style='font-size: 22px !important;'>รายการตารางข้อมูล</span>");
				Label dataTitle = new Label();
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : " + Constants.STARTUP_DATATABLE_TRANSPORTMETHODLIST_TITLE + "</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
					@Override
					protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
						return "font-size: 16px !important;";
					}
				};
				printGrid.setFields(transportMethodList.getFields());
				printGrid.setData(transportMethodList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName, title, dataTitle, separator, printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});

	}

}
