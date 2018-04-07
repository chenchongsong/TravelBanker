# Articho28
###### /java/seedu/address/commons/events/ui/ShowMapRequestEvent.java
``` java
/**
 * An event requesting to view a map showing the nearest ATM.
 */
public class ShowMapRequestEvent extends BaseEvent {

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/BalanceCommand.java
``` java
/**
 * Handles the balance command.
 */
public class BalanceCommand extends Command {

    public static final String COMMAND_WORD = "balance";
    public static final String COMMAND_SHORTCUT = "b";
    public static final String MESSAGE_SUCCESS = "Shown balance.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Displays your overall balance";
    private static double calculatedBalance;
    private static DecimalFormat twoDecimalPlaces = new DecimalFormat("0.00");

    public static DecimalFormat getFormatTwoDecimalPlaces() {
        return twoDecimalPlaces;
    }

    public static double getCalculatedBalance() {
        return calculatedBalance;
    }

    @Override
    public CommandResult execute() {

        calculatedBalance = getBalanceFromTravelBanker();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + "Your balance is "
                + twoDecimalPlaces.format(calculatedBalance) + ".");
    }

    public double getBalanceFromTravelBanker() {
        double accumulator = 0.00;

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Person person : lastShownList) {
            double currentPersonBalance = person.getMoney().balance;
            accumulator = accumulator + currentPersonBalance;
        }

        return accumulator;
    }
}
```
###### /java/seedu/address/logic/commands/MapCommand.java
``` java
/**
 * Shows a map and searchs for the nearest ATM.
 */

public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_SHORTCUT = "mp";
    public static final String MESSAGE_SUCCESS = "Map Shown";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMapRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
```
###### /java/seedu/address/logic/commands/MinCommand.java
``` java
/**
 * Finds the person to which you owe the most money
 */
public class MinCommand extends Command {

    public static final String COMMAND_WORD = "min";
    public static final String COMMAND_SHORTCUT = "mn";
    public static final String MESSAGE_SUCCESS_FOUND = "The contact to which you owe the most money is: ";
    public static final String MESSAGE_SUCCESS_NO_RESULT = "Good news! You don't owe any money.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person to which you owe the most money ";
    private CommandResult result;

    public CommandResult getResult() {
        return result;
    }

    public void setResult(CommandResult result) {
        this.result = result;
    }
    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = Index.fromZeroBased(0);
        double lowestDebt = 0.0;

        for (int i = 0; i < lastShownList.size(); i++) {
            Person person = lastShownList.get(i);
            if (person.getMoney().balance < lowestDebt) {
                index = Index.fromZeroBased(i);
                lowestDebt = person.getMoney().balance;
            }
            if (lowestDebt == 0.0) {
                result = new CommandResult(MESSAGE_SUCCESS_NO_RESULT);
            } else {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
                result = new CommandResult(MESSAGE_SUCCESS_FOUND + lastShownList.get(index.getZeroBased()).getName());
            }
        }
        return result;
    }
}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public void loadAtmSearchPage() {
        loadPage(ATM_SEARCH_PAGE_URL);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void loadPersonAddress(Person person) {
        loadPage (ADDRESS_SEARCH_PAGE_URL + person.getAddress().value);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleShowMapRequestEvent(ShowMapRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadAtmSearchPage();
    }

}
```
