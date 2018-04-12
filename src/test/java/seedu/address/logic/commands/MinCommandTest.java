package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.money.Money;

public class MinCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executes_updatedBalanceAccordingly() throws Exception {
        model.getFilteredPersonList().get(0).setMoney(new Money("-100"));

        MinCommand minCommand = new MinCommand();
        minCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = minCommand.execute();

        assertEquals(commandResult.feedbackToUser,
                MinCommand.MESSAGE_SUCCESS_FOUND + model.getFilteredPersonList().get(0).getName());
    }
}
