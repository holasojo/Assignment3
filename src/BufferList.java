/**
 * Source code example adapted from "A Practical Introduction to Data Structures
 * and Algorithm Analysis, 3rd Edition (Java)" by Clifford A. Shaffer Copyright
 * 2008-2011 by Clifford A. Shaffer
 *
 * Adapted ArrayList source code to fit the needs of a list of buffers.
 *
 * @author sshumway
 * @author sohyun
 * @version 3/23/2016
 * @param <E>
 *            parameter for the list
 */
class BufferList<E> {
    private int maxSize; // Maximum size of list
    private int listSize; // Current # of list items
    private int curr; // Position of current element
    private E[] listArray; // Array holding list elements

    /**
     * Constructor for the buffer list
     * 
     * @param max
     *            The number of buffers that can be held in the buffer pool.
     */
    @SuppressWarnings("unchecked")
    public BufferList(int max) {
        maxSize = max;
        listSize = 0;
        curr = 0;
        listArray = (E[]) new Object[max];
    }

    /**
     * Clears the list.
     */
    public void clear() {
        listSize = 0;
        curr = 0;
    }

    /**
     * Inserts an object into the list.
     * 
     * @param it
     *            Object to be inserted.
     */
    public void insert(E it) {
        if (listSize == maxSize) {
            moveToEnd();
            remove();
        }

        moveToStart();
        for (int i = listSize; i > curr; i--) {
            listArray[i] = listArray[i - 1];
        }

        moveToStart();
        listArray[curr] = it;
        listSize++;
    }

    /**
     * Moves buffer from position to the front of the list to be reused. Follows
     * LRU buffer pool principle.
     * 
     * @param itPos
     *            Position of buffer to promote to front.
     * @return The buffer that is moved.
     */
    public E promote(int itPos) {
        E toReturn = listArray[itPos];

        for (int i = itPos; i > 0; i--) {
            listArray[i] = listArray[i - 1];
        }

        moveToStart();
        listArray[curr] = toReturn;

        return toReturn;
    }

    /**
     * Append "it" to list
     * 
     * @param it
     *            item to append
     */
    public void append(E it) {
        listArray[listSize++] = it;
    }

    /**
     * Remove and return the rear element
     * 
     * @return the rear element
     */
    public E remove() {
        // No current element
        if ((curr < 0) || (curr >= listSize)) {
            return null;
        }
        // Copy the element
        E it = listArray[curr];
        for (int i = curr; i < listSize - 1; i++) {
            // Shift them down
            listArray[i] = listArray[i + 1];
        }
        // Decrement size
        listSize--;
        return it;
    }

    /**
     * Sets curr to front
     */
    public void moveToStart() {
        curr = 0;
    }

    /**
     * Moves current to last index in list.
     */
    public void moveToEnd() {
        curr = listSize - 1;
    }

    /**
     * Moves substracts from curr.
     */
    public void prev() {
        if (curr != 0) {
            curr--;
        }
    }

    /**
     * Adds one to curr.
     */
    public void next() {
        if (curr < listSize) {
            curr++;
        }
    }

    /**
     * gets the list length
     * 
     * @return List size
     */
    public int length() {
        return listSize;
    }

    /**
     * gets the current position
     * 
     * @return Current position
     */
    public int currPos() {
        return curr;
    }

    /**
     * Moves curr to desired position.
     * 
     * @param pos The position to move to.
     */
    public void moveToPos(int pos) {
        curr = pos;
    }

    /**
     * Gets the current value.
     * 
     * @return The current value(buffer)
     */
    public E getValue() {
        return listArray[curr];
    }

    /**
     * Generate a human-readable representation of this list's contents that
     * looks something like this: < 1 2 3 | 4 5 6 >. The vertical bar represents
     * the current location of the fence. This method uses toString() on the
     * individual elements.
     * 
     * @return The string representation of this list
     */
    public String toString() {
        // Save the current position of the list
        int oldPos = currPos();
        int length = length();
        StringBuffer out = new StringBuffer((length() + 1) * 4);

        moveToStart();
        out.append("< ");
        for (int i = 0; i < oldPos; i++) {
            out.append(getValue());
            out.append(" ");
            next();
        }
        out.append("| ");
        for (int i = oldPos; i < length; i++) {
            out.append(getValue());
            out.append(" ");
            next();
        }
        out.append(">");
        moveToPos(oldPos); // Reset the fence to its original position
        return out.toString();
    }
}
