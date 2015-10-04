package jpac.express.client.ui.startup.datatable;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

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

public class UnitsList extends CustomWindow {

	private String className = this.getClass().getSimpleName();

	private static UnitsList instance = null;

	private HLayout dataLayout;
	public final ListGrid unitsList;

	public UnitsList() {

		super(Constants.STARTUP_DATATABLE_UNITSLIST_ID, Constants.STARTUP_DATATABLE_UNITSLIST_TITLE, 1120, 600, false);

		unitsList = new ListGrid();
		unitsList.setDataSource(DataSource.get("units"));
		unitsList.setCanEdit(true);
		unitsList.setAutoSaveEdits(true);
		unitsList.setAutoFetchData(true);
		unitsList.setContextMenu(getUnitsListContextMenu());

		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					unitsList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					unitsList.startEditing(unitsList.getRecordIndex(unitsList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					unitsList.removeData(unitsList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATATABLE_UNITSLIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}

		});

		unitsList.focus();
	}

	public static UnitsList getInstance() {
		if (instance == null) {
			instance = new UnitsList();
		}
		return instance;
	}

	public void showUnitsList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();

		unitsList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getUnitsListContextMenu().showContextMenu();
			}
		});
		unitsList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getUnitsListContextMenu().showContextMenu();
			}
		});

		final ListGridField unitsId = new ListGridField();
		unitsId.setTitle(Constants.UNITS_UNITSID_TITLE);
		unitsId.setName(Constants.UNITS_UNITSID);
		unitsId.setWidth("10%");

		final ListGridField unitsShortNameTH = new ListGridField();
		unitsShortNameTH.setTitle(Constants.UNITS_UNITSSHORTNAME_TH_TITLE);
		unitsShortNameTH.setName(Constants.UNITS_UNITSSHORTNAME_TH);
		unitsShortNameTH.setWidth("10%");

		final ListGridField unitsShortNameEN = new ListGridField();
		unitsShortNameEN.setTitle(Constants.UNITS_UNITSSHORTNAME_EN_TITLE);
		unitsShortNameEN.setName(Constants.UNITS_UNITSSHORTNAME_EN);
		unitsShortNameEN.setWidth("10%");

		final ListGridField unitsFullNameTH = new ListGridField();
		unitsFullNameTH.setTitle(Constants.UNITS_UNITSFULLNAME_TH_TITLE);
		unitsFullNameTH.setName(Constants.UNITS_UNITSFULLNAME_TH);
		unitsFullNameTH.setWidth("40%");

		final ListGridField unitsFullNameEN = new ListGridField();
		unitsFullNameEN.setTitle(Constants.UNITS_UNITSFULLNAME_EN_TITLE);
		unitsFullNameEN.setName(Constants.UNITS_UNITSFULLNAME_EN);
		unitsFullNameEN.setWidth("20%");

		final ListGridField unitsRate = new ListGridField();
		unitsRate.setTitle(Constants.UNITS_UNITSRATE_TITLE);
		unitsRate.setName(Constants.UNITS_UNITSRATE);
		unitsRate.setWidth("10%");

		unitsList.addCellSavedHandler(new CellSavedHandler() {
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

				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_UNITSLIST_ID);
			}
		});
		unitsList.addEditorEnterHandler(new EditorEnterHandler() {
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
		unitsList.addEditorExitHandler(new EditorExitHandler() {
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

		unitsList.setFields(unitsId, unitsShortNameTH, unitsShortNameEN, unitsFullNameTH, unitsFullNameEN, unitsRate);
		dataLayout.addMember(unitsList);
		addDataSection(dataLayout);
	}

	protected Menu getUnitsListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				unitsList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				unitsList.startEditing(unitsList.getRecordIndex(unitsList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				unitsList.removeData(unitsList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_UNITSLIST_ID);
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

				unitsList.startEditingNew();
			}
		});

		getCustomControlMenu().newButton("edit" + className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (unitsList.getSelectedRecord() != null) {
					getCustomControlMenu().getButton("new" + className).disable();
					getCustomControlMenu().getButton("trash" + className).disable();
					getCustomControlMenu().getButton("save" + className).enable();
					getCustomControlMenu().getButton("gotofirst" + className).disable();
					getCustomControlMenu().getButton("gotoprevious" + className).disable();
					getCustomControlMenu().getButton("gotonext" + className).disable();
					getCustomControlMenu().getButton("gotolast" + className).disable();
					getCustomControlMenu().getButton("print" + className).disable();
					unitsList.startEditing(unitsList.getRecordIndex(unitsList.getSelectedRecord()));
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
							unitsList.removeData(unitsList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATATABLE_UNITSLIST_ID);
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
				unitsList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst" + className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (unitsList.getRecords().length >= 0) {
					unitsList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious" + className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (unitsList.getRecords().length >= 0) {
					if ((unitsList.getRecordIndex(unitsList.getSelectedRecord()) - 1) >= 0) {
						unitsList.selectSingleRecord((unitsList.getRecordIndex(unitsList.getSelectedRecord()) - 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext" + className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (unitsList.getRecords().length >= 0) {
					if ((unitsList.getRecordIndex(unitsList.getSelectedRecord()) + 1) <= (unitsList.getRecords().length - 1)) {
						unitsList.selectSingleRecord((unitsList.getRecordIndex(unitsList.getSelectedRecord()) + 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast" + className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (unitsList.getRecords().length >= 0) {
					unitsList.selectSingleRecord((unitsList.getRecords().length - 1));
				}
			}
		});

		getCustomControlMenu().newButton("print" + className, "print", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				unitsList.invalidateCache();
				VLayout printContainer = new VLayout();

				Label companyName = new Label();
				companyName.setContents("<span style='font-size: 16px !important;'>บริษัท</span>");
				Label title = new Label();
				title.setContents("<span style='font-size: 22px !important;'>รายการตารางข้อมูล</span>");
				Label dataTitle = new Label();
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : " + Constants.STARTUP_DATATABLE_UNITSLIST_TITLE + "</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
					@Override
					protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
						return "font-size: 16px !important;";
					}
				};
				printGrid.setFields(unitsList.getFields());
				printGrid.setData(unitsList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName, title, dataTitle, separator, printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});

	}

}
