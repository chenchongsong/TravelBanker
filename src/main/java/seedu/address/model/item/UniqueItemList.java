package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of items that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 */
public class UniqueItemList implements Iterable<Item> {

    private final ObservableList<Item> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ItemList.
     */
    public UniqueItemList() {}

    /**
     * Creates a UniqueItemList using given items.
     * Enforces no nulls.
     */
    public UniqueItemList(Set<Item> items) {
        requireAllNonNull(items);
        internalList.addAll(items);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all items in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Item> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns all items in this list as a String.
     */
    public String toString() {
        assert CollectionUtil.elementsAreUnique(internalList);
        final StringBuilder builder = new StringBuilder();
        builder.append("Items: ");
        internalList.forEach(builder::append);
        return builder.toString();
    }

    /**
     * Replaces the Items in this list with those in the argument Item list.
     */
    public void setItems(Set<Item> items) {
        requireAllNonNull(items);
        internalList.setAll(items);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every item in the argument list exists in this object.
     */
    public void mergeFrom(UniqueItemList from) {
        final Set<Item> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(item -> !alreadyInside.contains(item))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Item as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Item to the list.
     *
     * @throws DuplicateItemException if the Item to add is a duplicate of an existing Item in the list.
     */
    public void add(Item toAdd) throws DuplicateItemException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Item> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Item> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(((UniqueItemList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueItemList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateItemException extends DuplicateDataException {
        protected DuplicateItemException() {
            super("Operation would result in duplicate items");
        }
    }

}
