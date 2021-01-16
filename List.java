/**
 * INFO: In order to comply with policies restricting
 * the use of certain java libraries, such as java.util,
 * the following import statements can be removed or
 * commented. This will only prevent this class
 * to work with the enhanced {@code for} statement
 * (sometimes called the "for-each loop" statement).
 * All other functionalities will still be available.
 */
import java.util.Iterator;
import java.util.ListIterator;
/**
 * INFO: The below import enables the {@code contains}
 * function to accept a compare function as input
 * parameter.
 */
import java.util.function.Function;

/**
 * A custom implementation of a linked list.
 * Implemented as an iterable to allow use with
 * the enhanced {@code for} statement
 * (sometimes called the "for-each loop" statement).
 * Also implements negative indexes for easier use.
 * 
 * By design the list is sorted in the order in which
 * items have been added. Unless of course items are
 * inserted at specific indexes with the {@code insert}
 * function.
 *
 * @param <T> the type of elements stored in this list.
 *
 */
public class List<T> implements Iterable<T>, Cloneable {

    private ListElement<T> firstElement;
    private ListElement<T> lastElement;

    private int length;

    public List() {
        this.firstElement = null;
        this.lastElement = null;
        this.length = 0;
    }

    @SuppressWarnings("unchecked")
    public List(T... elements) {
        this();
        for (T element : elements) {
            this.add(element);
        }
    }

    /**
     * Get the length of the list.
     * @return Length of the list.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Add the element at the end of the list.
     * @param element Element to be added.
     */
    public void add(T element) {
        ListElement<T> newListElement = new ListElement<T>(element);
        if (this.firstElement == null) {
            this.firstElement = newListElement;
            this.lastElement = this.firstElement;
        } else {
            newListElement.previousElement = this.lastElement;
            this.lastElement.nextElement = newListElement;
            this.lastElement = newListElement;
        }
        this.length += 1;
    }

    /**
     * Return the element at {@code index} from the list.
     * @param index Index of the to be returned element
     * @return Element at the given index.
     * @throws IndexOutOfBoundsException if the index
     * is out of bounds.
     */
    public T get(int index) {
        return this._get(index).element;
    }

    private ListElement<T> _get(int index) {
        int steps = 0;
        ListElement<T> result;
        if (index < 0) {
            index = (-1 * index) - 1;
            result = this.lastElement;
            while (steps < index) {
                try {
                    result = result.previousElement;
                } catch (NullPointerException ignore) {
                    throw new IndexOutOfBoundsException();
                }
                steps++;
            }
            return result;
        }
        if (index > 0) {
            result = this.firstElement;
            while (steps < index) {
                try {
                    result = result.nextElement;
                } catch (NullPointerException ignore) {
                    throw new IndexOutOfBoundsException();
                }
                steps++;
            }
            return result;
        }
        return this.firstElement;
    }

    /**
     * Insert element at given index.
     * @param element Element to be inserted.
     * @param index Index at which to insert.
     * @throws IndexOutOfBoundsException if the index
     * is out of bounds.
     */
    public void insert(T element, int index) {
        ListElement<T> newElement = new ListElement<T>(element);
        ListElement<T> previousElement = this._get(index - 1);
        newElement.nextElement = previousElement.nextElement;
        newElement.previousElement = previousElement;
        previousElement.nextElement = newElement;
    }

    /**
     * Remove element at {@code index} from the list.
     * @param index Index of the element to be removed.
     * @throws IndexOutOfBoundsException if the index
     * is out of bounds.
     */
    public void remove(int index) {
        this._remove(index);
        this.length--;
    }

    private void _remove(int index) {
        ListElement<T> ListElement = this._get(index);
        if (ListElement == this.firstElement) {
            this.firstElement = this.firstElement.nextElement;
            this.firstElement.previousElement = null;
            return;
        }
        if (ListElement == this.lastElement) {
            this.lastElement = this.lastElement.previousElement;
            this.lastElement.nextElement = null;
            return;
        }
        ListElement.previousElement.nextElement = ListElement.nextElement;
        ListElement.nextElement.previousElement = ListElement.previousElement;

    }

    /**
     * Check if an element is contained within the list.
     * @param element The element to look for.
     * @return Whether the element is present within the list or not.
     */
    public boolean contains(T element) {
        ListElement<T> currentElement = this.firstElement;
        while(currentElement != null) {
            if (currentElement.element == element) {
                return true;
            }
            currentElement = currentElement.nextElement;
        }
        return false;
    }

    /**
     * Check if an element is contained within the list
     * and specify a compare function to be used.
     * @param element Element to be looked for.
     * @param compareFunction Compare function to be used.
     * @return Whether the element is present within the list or not.
     */
    public boolean contains(T element, Function<T, Boolean> compareFunction) {
        ListElement<T> currentElement = this.firstElement;
        while(currentElement != null) {
            if (compareFunction.apply(currentElement.element)) {
                return true;
            }
            currentElement = currentElement.nextElement;
        }
        return false;
    }

    @Override
    public String toString() {
        String result = "[";
        ListElement<T> currentElement = this.firstElement;
        while (currentElement != null) {
            result += currentElement.element.toString();
            currentElement = currentElement.nextElement;
            if (currentElement == null)
                break;
            result += ", ";
        }
        result += "]";
        return result;
    }

    /**
     * INFO: If, in order to comply with policies restricting
     * the use of certain java libraries, such as java.util,
     * the import statements in thif file have been removed or
     * commented, this is also to be done for the following code.
     * As mentioned above, this will only prevent this class
     * to work with the enhanced {@code for} statement
     * (sometimes called the "for-each loop" statement).
     * All other functionalities will still be available.
     */

    @SuppressWarnings("hiding")
    private class ListElement<T> {

        public ListElement<T> previousElement;
        public ListElement<T> nextElement;
        public T element;
    
        public ListElement(T element) {
            this.previousElement = null;
            this.element = element;
            this.nextElement = null;
        }
    
        @Override
        public String toString() {
            T previousElement;
            T nextElement;
            try {
                previousElement = this.previousElement.element;
            } catch (NullPointerException ignore) {
                previousElement = null;
            }
            try {
                nextElement = this.nextElement.element;
            } catch (NullPointerException ignore) {
                nextElement = null;
            }
            return String.format("%s <-- %s --> %s", previousElement, this.element, nextElement);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>(){
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < getLength();
            }

            @Override
            public T next() {
                return get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("not supported yet");
            }

            @Override
            public boolean hasPrevious() {
                int _index = index < 0 ? -1 * index : index;
                return _index < getLength();
            }

            @Override
            public T previous() {
                return get(index--);
            }

            @Override
            public int nextIndex() {
                return index+1;
            }

            @Override
            public int previousIndex() {
                return index-1;
            }

            @Override
            public void set(T e) {
                throw new UnsupportedOperationException("not supported yet");
            }

            @Override
            public void add(T e) {
                throw new UnsupportedOperationException("not supported yet");
            }
        };
    }
}