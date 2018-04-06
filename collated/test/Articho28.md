# Articho28
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(BrowserPanel.ATM_SEARCH_PAGE_URL);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.ADDRESS_SEARCH_PAGE_URL
                +  BOB.getAddress().value.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
###### /java/seedu/address/logic/commands/MapCommandTest.java
``` java
public class MapCommandTest extends GuiUnitTest {

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;
    private MapCommand mapCommand;
    private Model model;
    private Model expectedModel;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        mapCommand = new MapCommand();
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void executes_mapCommandRecognized() {
        assertCommandSuccess(mapCommand, model, MapCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test public void executes_displaysMap() throws MalformedURLException {
        CommandResult commandResult = mapCommand.execute();

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(new URL(BrowserPanel.ATM_SEARCH_PAGE_URL), browserPanelHandle.getLoadedUrl());
    }


}
```
###### /java/seedu/address/logic/commands/BalanceCommandTest.java
``` java
public class BalanceCommandTest {

    private Model model;
    private Model expectedModel;
    private BalanceCommand balanceCommand;
    private double balance;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        balanceCommand = new BalanceCommand();
        balanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        balance = balanceCommand.getBalanceFromTravelBanker();
    }

    @Test
    public void executes_getsOverallBalanceSuccess() {
        assertCommandSuccess(balanceCommand, model,  BalanceCommand.MESSAGE_SUCCESS
                + "\n" + "Your balance is "
                + BalanceCommand.getFormatTwoDecimalPlaces().format(balance)
                + ".", expectedModel);
    }




}
```
