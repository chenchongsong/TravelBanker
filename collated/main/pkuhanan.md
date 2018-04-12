# pkuhanan
###### /java/seedu/address/logic/commands/BiggestDebtorCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;

/**
 * Finds the person that owes the most money
 */
public class BiggestDebtorCommand extends Command {
    public static final String COMMAND_WORD = "biggest-debtor";
    public static final String COMMAND_SHORTCUT = "bd";
    public static final String MESSAGE_SUCCESS = "Biggest debtor found: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person that owes the most money ";

    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = Index.fromZeroBased(0);
        Double highestDebt = 0.0;

        for (int i = 0; i < lastShownList.size(); i++) {
            Person person = lastShownList.get
                    (i);
            if (person.getMoney().balance > highestDebt) {
                index = Index.fromZeroBased(i);
                highestDebt = person.getMoney().balance;
            }
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(MESSAGE_SUCCESS + Integer.toString(index.getOneBased()));
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setMoney(Money money) {
            this.money = money;
        }

        public Optional<Money> getMoney() {
            return Optional.ofNullable(money);
        }
```
###### /java/seedu/address/logic/commands/RemindCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;


/**
 * Helps the user send a reminder by email to the contact
 */
public class RemindCommand extends Command {
    public static final String COMMAND_WORD = "remind";
    public static final String COMMAND_SHORTCUT = "rm";
    public static final String MESSAGE_SUCCESS = "Generated email for contact: ";
    public static final String MESSAGE_FAILURE = " does not owe you any money";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person that owes the most money ";

    private final Index index;
    private Person person;

    /**
     * @param index of the person in the filtered person list to remind
     */
    public RemindCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        person = lastShownList.get(index.getZeroBased());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        if (person.getMoney().balance > 0) {
            try {
                Desktop desktop;
                if (Desktop.isDesktopSupported()
                        && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                    String uri = "mailto:" + person.getEmail()
                            + "?subject=Reminder:%20Please%20pay%20me%20back&body=Please%20pay%20me%20back:%20$"
                            + person.getMoney();
                    URI mailto = new URI(uri);
                    desktop.mail(mailto);
                } else {
                    throw new AssertionError("No email client configured");
                }
                return new CommandResult(MESSAGE_SUCCESS + person.getName() + "<" + person.getEmail() + ">");
            } catch (Exception e) {
                throw new AssertionError("Problem sending email");
            }
        } else {
            return new CommandResult(person.getName() + MESSAGE_FAILURE);
        }
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case MaxCommand.COMMAND_WORD:
            return new MaxCommand();

        case MaxCommand.COMMAND_SHORTCUT:
            return new MaxCommand();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case SettleCommand.COMMAND_WORD:
            return new SettleCommandParser().parse(arguments);

        case SettleCommand.COMMAND_SHORTCUT:
            return new SettleCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);
        case RemindCommand.COMMAND_SHORTCUT:
            return new RemindCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String money} into an {@code Money}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code money} is invalid.
     */
    public static Money parseMoney(String money) throws IllegalValueException {
        requireNonNull(money);
        String trimmedMoney = money.trim();
        if (!Money.isValidMoney(trimmedMoney)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Money(trimmedMoney);
    }

    /**
     * Parses a {@code Optional<String> money} into an {@code Optional<money>} if {@code money} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Money> parseMoney(Optional<String> money) throws IllegalValueException {
        requireNonNull(money);
        return money.isPresent() ? Optional.of(parseMoney(money.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/RemindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemindCommand object
 */
public class RemindCommandParser implements Parser<RemindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SettleCommand
     * and returns a SettleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemindCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemindCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/model/money/Money.java
``` java
package seedu.address.model.money;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Money Balance in the TravelBanker.
 * Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS = "Money values should be numbers";
    public static final String MONEY_VALIDATION_REGEX = "-?\\d+(\\.\\d+)?(E-?\\d+)?";

    public final double balance;
    public final String value;

    /**
     * Constructs a {@code Money}.
     *
     * @param balance A valid money balance.
     */
    public Money(String balance) {
        requireNonNull(balance);
        checkArgument(isValidMoney(balance), MESSAGE_MONEY_CONSTRAINTS);
        this.balance = Double.parseDouble(balance);
        this.value = balance;
    }

    @Override
    public String toString() {
        return value;
    }

    public Double toDouble() {
        return balance;
    }

    /**
     * Returns true if a given string is a valid money balance.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }

```
###### /java/seedu/address/model/money/Money.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && this.value.equals(((Money) other).value)); // state check
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    public Money getMoney() {
        return money;
    }
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
    @XmlElement
    private String balance;
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        if (!Money.isValidMoney(this.balance)) {
            throw new IllegalValueException(Money.MESSAGE_MONEY_CONSTRAINTS);
        }
        final Money balance = new Money(this.balance);
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    @FXML
    private Label money;
```
