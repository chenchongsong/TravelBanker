package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item in the TravelBanker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Item {

    public static final String MESSAGE_ITEM_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String ITEM_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String itemName;

    /**
     * Constructs a {@code Item}.
     *
     * @param itemName A valid item name.
     */
    public Item(String itemName) {
        requireNonNull(itemName);
        checkArgument(isValidTagName(itemName), MESSAGE_ITEM_CONSTRAINTS);
        this.itemName = itemName;
    }

    /**
     * Returns true if a given string is a valid item name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(ITEM_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Item // instanceof handles nulls
                && this.itemName.equals(((Item) other).itemName)); // state check
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + itemName + ']';
    }

}
