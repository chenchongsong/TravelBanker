package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Sort all persons in address book in order.
 * Keywords will be given by user through arguments.
 * Both ascending and descending order is supported.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_SHORTCUT = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in ascendingly or descendingly, "
            + "ordering by the specified keywords.\n"
            + "Parameters: KEYWORD_PREFIX+ORDER ...\n"
            + "Example1: " + COMMAND_WORD + " n/desc\n"
            + "Example2: " + COMMAND_WORD + " m/asc";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";


    public SortCommand(String sortKey, String order) {

    }

//    public SortCommand(NameContainsKeywordsPredicate predicate) {
//        this.predicate = predicate;
//    }

    @Override
    public CommandResult execute() {
        model.sortUniquePersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
