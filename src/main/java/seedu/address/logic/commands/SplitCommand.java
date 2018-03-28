package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Split a bill evenly among selected people.
 */
public class SplitCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "split";
    public static final String COMMAND_SHORTCUT = "sp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Split a bill evenly among specified people. "
            + "Parameters: INDEX1 INDEX2 ... "
            + "[" + PREFIX_MONEY + "MONEY] ";

    public static final String MESSAGE_SPLIT_BILL_SUCCESS = "Bill Split: ";

    private final ArrayList<Index> indices;

    private ArrayList<Person> peopleToEdit;
    private ArrayList<Person> editedPeople;

    /**
     * @param indices of people in the filtered person list to settle the bill
     */
    public SplitCommand(ArrayList<Index> indices, double bill) {
        requireNonNull(indices);
        this.indices = indices;
        peopleToEdit = new ArrayList<>();
        editedPeople = new ArrayList<>();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            for (int i=0; i<indices.size(); i++) {
                model.updatePerson(peopleToEdit.get(i), editedPeople.get(i));
            }
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target people cannot be duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target people cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        // EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(MESSAGE_SPLIT_BILL_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person person = lastShownList.get(index.getZeroBased());
            peopleToEdit.add(person);
            editedPeople.add(getSettledPerson(person));
        }
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * but with a 0 balance
     */
    private static Person getSettledPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Money money = new Money("0");
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, money, tags);
    }
}
