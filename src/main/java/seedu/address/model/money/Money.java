package seedu.address.model.money;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS = "Money values should be numbers";
    public static final String MONEY_VALIDATION_REGEX = "^(?!0\\.00)\\d{1,3}(,\\d{3})*(\\.\\d\\d)?$";

    public final Double balance;

    /**
     * Constructs a {@code Money}.
     *
     * @param balance A valid money balance.
     */
    public Money(String balance) {
        requireNonNull(balance);
        checkArgument(isValidMoney(balance), MESSAGE_MONEY_CONSTRAINTS);
        this.balance = Double.parseDouble(balance);;
    }

    /**
     * Returns true if a given string is a valid money balance.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }
}
