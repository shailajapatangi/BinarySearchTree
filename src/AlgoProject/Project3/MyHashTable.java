package AlgoProject.Project3;

//Linear Probing Hash table class

//
//CONSTRUCTION: an approximate initial size or default of 101
//
//******************PUBLIC OPERATIONS*********************
//bool insert( x )       --> Insert x
//bool remove( x )       --> Remove x
//bool contains( x )     --> Return true if x is present
//void makeEmpty( )      --> Remove all items

/**
 * Probing table implementation of hash tables. Note that all "matching" is
 * based on the equals method.
 * 
 * @author Mark Allen Weiss, Edit: Shailaja
 */
public class MyHashTable<AnyType> {
	/**
	 * Construct the hash table.
	 */
	public MyHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	/**
	 * Construct the hash table.
	 * 
	 * @param size the approximate initial size.
	 */
	public MyHashTable(int size) {
		allocateArray(size);
		doClear();
	}

	/**
	 * Insert into the hash table. If the item is already present, do nothing.
	 * 
	 * @param x the item to insert.
	 */
	public boolean insert(AnyType x, String prefix) {
		// Insert x as active
		int currentPos = findPos(x, prefix);
		if (isActive(currentPos, prefix)) {
			if (array[currentPos] != null && prefix.equals("word") && array[currentPos].prefix.equals("prefix")) {
				array[currentPos].prefix = "word";
				// return false;
			}
			return false;
		} else if (array[currentPos] != null && array[currentPos].isActive && prefix.equals("word")
				&& array[currentPos].prefix.equals("prefix")) {
			array[currentPos].prefix = "word";
			return false;
		}

		if (array[currentPos] == null)
			++occupied;
		array[currentPos] = new HashEntry<>(x, true, prefix);
		theSize++;

		// Rehash; see Section 5.5
		if (occupied > array.length / 2)
			rehash(prefix);

		return true;
	}

	/**
	 * Expand the hash table.
	 * 
	 * @param prefix
	 */
	private void rehash(String prefix) {
		HashEntry<AnyType>[] oldArray = array;

		// Create a new double-sized, empty table
		allocateArray(2 * oldArray.length);
		occupied = 0;
		theSize = 0;

		// Copy table over
		for (HashEntry<AnyType> entry : oldArray)
			if (entry != null && entry.isActive)
				insert(entry.element, prefix);
	}

	/**
	 * Method that performs quadratic probing resolution.
	 * 
	 * @param x      the item to search for.
	 * @param prefix
	 * @return the position where the search terminates.
	 */
	private int findPos(AnyType x, String prefix) {
		int offset = 1;
		int currentPos = myhash(x);

		if (prefix.equals("word")) {
			while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
				currentPos += offset; // Compute ith probe
				// offset += 2;
				if (currentPos >= array.length)
					currentPos -= array.length;
			}

		} else if (prefix.equals("prefix")) {
			while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
				currentPos += offset; // Compute ith probe
				// offset += 2;
				if (currentPos >= array.length)
					currentPos -= array.length;
			}

		}
		return currentPos;
	}

	/**
	 * Remove from the hash table.
	 * 
	 * @param x the item to remove.
	 * @return true if item removed
	 */
	public boolean remove(AnyType x, String prefix) {
		int currentPos = findPos(x, prefix);
		if (isActive(currentPos, prefix)) {
			array[currentPos].isActive = false;
			theSize--;
			return true;
		} else
			return false;
	}

	/**
	 * Get current size.
	 * 
	 * @return the size.
	 */
	public int size() {
		return theSize;
	}

	/**
	 * Get length of internal table.
	 * 
	 * @return the size.
	 */
	public int capacity() {
		return array.length;
	}

	/**
	 * Find an item in the hash table.
	 * 
	 * @param x the item to search for.
	 * @return the matching item.
	 */
	public boolean contains(AnyType x, String prefix) {
		int currentPos = findPos(x, prefix);
		return isActive(currentPos, prefix);
	}

//	/**
//	 * Returns the value associated with the specified key.
//	 * 
//	 * @param key the key
//	 * @return the value associated with {@code key}; {@code null} if no such value
//	 * @throws IllegalArgumentException if {@code key} is {@code null}
//	 */
//	public AnyType get(AnyType key) {
//		if (key == null)
//			throw new IllegalArgumentException("argument to get() is null");
//		for (int i = myhash(key); array[i] != null; i = (i + 1) % array.length)
//			if (array[i].element.equals(key))
//				return array[i].element;
//		return null;
//	}

	/**
	 * Return true if currentPos exists and is active.
	 * 
	 * @param currentPos the result of a call to findPos.
	 * @param prefix
	 * @return true if currentPos is active.
	 */
	private boolean isActive(int currentPos, String prefix) {

		return array[currentPos] != null && array[currentPos].isActive && array[currentPos].prefix.equals(prefix);
		// return array[currentPos] != null && array[currentPos].isActive;
	}

	/**
	 * Make the hash table logically empty.
	 */
	public void makeEmpty() {
		doClear();
	}

	private void doClear() {
		occupied = 0;
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}

	private int myhash(AnyType x) {
		int hashVal = x.hashCode();

		hashVal %= array.length;
		if (hashVal < 0)
			hashVal += array.length;

		return hashVal;
	}

	private static class HashEntry<AnyType> {
		public AnyType element; // the element
		public boolean isActive; // false if marked deleted
		public String prefix = "word";

		public HashEntry(AnyType e) {
			this(e, true, "word");
		}

		public HashEntry(AnyType e, boolean i, String pre) {
			element = e;
			isActive = i;
			prefix = pre;
		}
	}

	private static final int DEFAULT_TABLE_SIZE = 101;

	private HashEntry<AnyType>[] array; // The array of elements
	private int occupied; // The number of occupied cells
	private int theSize; // Current size

	/**
	 * Internal method to allocate array.
	 * 
	 * @param arraySize the size of the array.
	 */
	private void allocateArray(int arraySize) {
		array = new HashEntry[nextPrime(arraySize)];
	}

	/**
	 * Internal method to find a prime number at least as large as n.
	 * 
	 * @param n the starting number (must be positive).
	 * @return a prime number larger than or equal to n.
	 */
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		for (; !isPrime(n); n += 2)
			;

		return n;
	}

	/**
	 * Internal method to test if a number is prime. Not an efficient algorithm.
	 * 
	 * @param n the number to test.
	 * @return the result of the test.
	 */
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;

		if (n == 1 || n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}

}