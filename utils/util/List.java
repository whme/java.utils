package utils.util;

import utils.exceptions.NotInLoopException;
import utils.exceptions.AlreadyInLoopException;

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

    private ListElement<T> currentElement;

    private int length;

    private boolean isInLoop;
    private boolean isLastLoopIteration;

    public List() {
        this.firstElement = null;
        this.lastElement = null;
        this.length = 0;
        this.isInLoop = false;
        this.isLastLoopIteration = false;
    }

    @SuppressWarnings("unchecked")
    public List(T... elements) {
        this();
        for (T element : elements) {
            this.add(element);
        }
    }

    public List(List<T> list) {
        this();
        for (T element: list) {
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

    @SuppressWarnings("unchecked")
    public void add(T... elements) {
        for (T element: elements) {
            this.add(element);
        }
    }

    /**
     * Return the element at {@code index} from the list.
     * @param index Index of the to be returned element.
     * @return Element at the given index.
     * @throws IndexOutOfBoundsException if the index
     * is out of bounds.
     */
    public T get(int index) {
        return this._get(index).element;
    }

    /**
     * Helper function to get an element by index.
     * @param index Index of the element.
     * @return Element of list at the given index.
     */
    private ListElement<T> _get(int index) {
        if (index == 0) {
            return this.firstElement;
        }
        if (index == this.length || index == -1) {
            return this.lastElement;
        }
        boolean findFromBack;
        if (index < 0) {
            // A negative index was provided; we make it positive
            // and remove the offset of one. As -1 points to index 0.
            index = (-1 * index) - 1;

            // We only trigger the search from the back
            // if the index is between
            // -this.length/2 < index < 0
            // In other words if the now positive value of index points
            // to an element in the back half of the list.
            findFromBack = index < this.length - index;
        } else {
            // If a positive index was provided we trigger the search
            // from the front, unless the index points to
            // an element in the back half of the list.
            findFromBack = index > this.length - index;
        }
        if (findFromBack) {
            if (index > (this.length - index)) {
                    index = this.length - index - 1;
            }
            return this.findFromBack(index);
        }
        return this.findFromFront(index);
    }

    /**
     * Find element by index starting from the first element.
     * @param index Index of the element.
     * @return Element of list at the given index.
     */
    private ListElement<T> findFromFront(int index) {
        int steps = 0;
        ListElement<T> result;
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

    /**
     * Find element by index starting from the last element.
     * @param index Index of the element.
     * @return Element of list at the given index.
     */
    private ListElement<T> findFromBack(int index) {
        int steps = 0;
        ListElement<T> result;
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

    /**
     * Return the first element of the list for which
     * the given {@code compareFunction} evaluates to
     * {@code true}.
     * @param compareFunction Compare function to be used.
     * @return The element from the list.
     */
    public T get(Function<T, Boolean> compareFunction) {
        ListElement<T> currentElement = this.firstElement;
        while(currentElement != null) {
            if (compareFunction.apply(currentElement.element)) {
                return currentElement.element;
            }
            currentElement = currentElement.nextElement;
        }
        return null;
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
        this.length += 1;
    }

    /**
     * Remove element at {@code index} from the list.
     * @param index Index of the element to be removed.
     * @throws IndexOutOfBoundsException if the index
     * is out of bounds.
     */
    public void remove(int index) {
        this._remove(this._get(index));
    }

    /**
     * Remove the first occurence of the given Element
     * from the list.
     * @param element Element to remove.
     */
    public void remove(T element) {
        ListElement<T> currentElement = this.firstElement;
        while(currentElement != null) {
            if (currentElement.element.equals(element)) {
                this._remove(currentElement);
                return;
            }
            currentElement = currentElement.nextElement;
        }
    }

    /**
     * Remove the first element of the list for which
     * the given {@code compareFunction} evaluates to
     * {@code true}.
     * @param compareFunction Compare function to use.
     */
    public void remove(Function<T, Boolean> compareFunction) {
        ListElement<T> currentElement = this.firstElement;
        while(currentElement != null) {
            if (compareFunction.apply(currentElement.element)) {
                this._remove(currentElement);
                return;
            }
            currentElement = currentElement.nextElement;
        }
    }

    private void _remove(ListElement<T> ListElement) {
        this.length--;
        if (ListElement.equals(this.firstElement)) {
            this.firstElement = this.firstElement.nextElement;
            this.firstElement.previousElement = null;
            return;
        }
        if (ListElement.equals(this.lastElement)) {
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
            if (currentElement.element.equals(element)) {
                return true;
            }
            currentElement = currentElement.nextElement;
        }
        return false;
    }

    /**
     * Check if an element is contained within the list
     * by specifying the compare function on the element to be used.
     * @param compareFunction Compare function to be used.
     * @return Whether the element is present within the list or not.
     */
    public boolean contains(Function<T, Boolean> compareFunction) {
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
    @SuppressWarnings("unchecked")
    public List<T> clone() {
        List<T> newList = null;
        try {
            newList = (List<T>) super.clone();
        } catch (CloneNotSupportedException ignore) {
            newList = new List<>(this);
        }
        return newList;
    }

    /**
     * Initialize for loop iteration.
     * @return The first element of the list.
     */
    public T initForLoop() {
        if (this.isInLoop) {
            throw new AlreadyInLoopException("Loop has already been initialized.");
        }
        this.isInLoop = true;
        this.currentElement = this.firstElement;
        return this.currentElement.element;
    }

    /**
     * Whether there is a next element in the list
     * based on the current loop iteration.
     * @return boolean
     */
    public boolean hasNext() {
        if (!this.isInLoop) {
            throw new NotInLoopException("List has not been initialized for loop");
        }
        boolean hasNext;
        try {
            hasNext = this.currentElement.hasNext();
        } catch (NullPointerException ignore) {
            this.isInLoop = false;
            this.isLastLoopIteration = false;
            return false;
        }
        if (this.isLastLoopIteration) {
            return true;
        }
        return hasNext;
    }

    /**
     * Return the next element of the list
     * based on the current loop iteration.
     * @return Next element.
     */
    public T next() {
        if (!this.isInLoop) {
            throw new NotInLoopException("List has not been initialized for loop");
        }
        this.currentElement = this.currentElement.nextElement;
        T result;
        try {
            result = this.currentElement.element;
        } catch (NullPointerException ignore) {
            return null;
        }
        if (!this.currentElement.hasNext()) {
            this.isLastLoopIteration = true;
        }
        return result;
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

        public boolean hasNext() {
            return this.nextElement != null;
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