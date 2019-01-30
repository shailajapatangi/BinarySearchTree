package AlgoProject.Project2;

import java.util.LinkedList;
import java.util.Queue;

//BinarySearchTree class
//
//CONSTRUCTION: with no initializer
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x
//boolean contains( x )  --> Return true if x is present
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//void printTree( )      --> Print tree in sorted order
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	private int binaryCount = 0;
	private boolean equalsTree = true;

	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x the item to insert.
	 */
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax() {
		if (isEmpty())
			throw new UnderflowException();
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x the item to search for.
	 * @return true if not found.
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return new BinaryNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			; // Duplicate; do nothing
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x is item to search for.
	 * @param t the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return false;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true; // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private void printTree(BinaryNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	/**
	 * Gets the node count of binary tree
	 * 
	 * @return integer
	 */
	public int getNodeCount() {
		return nodeCount(root);
	}

	private int nodeCount(BinaryNode<AnyType> node) {
		if (node == null) {
			return binaryCount;
		}
		if (node != null) {
			binaryCount++;
			nodeCount(node.left);
			nodeCount(node.right);
		}
		return binaryCount;

	}

	/**
	 * Checks if the tree is full
	 * 
	 * @return boolean
	 */
	public boolean isFull() {
		return isBinaryTreeFull(root);
	}

	private boolean isBinaryTreeFull(BinaryNode<AnyType> node) {
		if (node == null) {
			return true;
		}

		if (node.left == null && node.right == null) {
			return true;
		}
		if (node.left != null && node.right != null) {
			return (isBinaryTreeFull(node.left) && isBinaryTreeFull(node.right));
		}

		return false;
	}

	/**
	 * Compares the structure of two trees
	 * 
	 * @param tree1
	 * @param tree2
	 * @return boolean
	 */
	public boolean compareStructure(BinarySearchTree<AnyType> tree1) {
		if (tree1 == null) {
			return false;
		}
		if (root == null && tree1.getRoot() == null) {
			return true;
		}
		if (root != null && tree1 != null) {
			return compareTreeStructure(root, tree1.getRoot());

		}
		return false;
	}

	private boolean compareTreeStructure(BinaryNode<AnyType> binaryNode, BinaryNode<AnyType> binaryNode2) {
		if (binaryNode == null && binaryNode2 == null) {
			return true;
		}
		if (binaryNode != null && binaryNode2 != null) {
			if (binaryNode.left != null && binaryNode2.left != null) {
				compareTreeStructure(binaryNode.left, binaryNode2.left);
			} else if ((binaryNode.left != null && binaryNode2.left == null)
					|| (binaryNode.left == null && binaryNode2.left != null)) {
				return false;
			}
			if (binaryNode.right != null && binaryNode2.right != null) {
				compareTreeStructure(binaryNode.right, binaryNode2.right);
			} else if ((binaryNode.right != null && binaryNode2.right == null)
					|| (binaryNode.right == null && binaryNode2.right != null)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if passed tree is equal to this tree
	 * 
	 * @param tree1
	 * @return boolean
	 */
	public boolean equals(BinarySearchTree<AnyType> tree1) {
		equalsTree = true;
		return isEqual(root, tree1.getRoot());

	}

	private boolean isEqual(BinaryNode<AnyType> treeRoot, BinaryNode<AnyType> tree1) {
		if (treeRoot == null && tree1 == null) {
			equalsTree = equalsTree && true;
		}
		if (treeRoot != null && tree1 != null) {
			if (treeRoot.element == tree1.element) {
				isEqual(treeRoot.left, tree1.left);
				isEqual(treeRoot.right, tree1.right);

			} else {
				equalsTree = equalsTree && false;
			}
		}
		return equalsTree;
	}

	/**
	 * Gets a copy of original tree
	 * 
	 * @return
	 */
	public BinarySearchTree<AnyType> copy() {
		BinaryNode<AnyType> newNode = copyTree(root);
		BinarySearchTree<AnyType> searchTree = new BinarySearchTree<>();
		searchTree.setRoot(newNode);
		return searchTree;
	}

	private BinaryNode<AnyType> copyTree(BinaryNode<AnyType> tree) {
		BinaryNode<AnyType> newNode = null;
		if (tree != null) {
			newNode = new BinaryNode<AnyType>(tree.element, null, null);
			newNode.left = copyTree(tree.left);
			newNode.right = copyTree(tree.right);
		}
		return newNode;
	}

	/**
	 * Gets the mirror of original tree
	 * 
	 * @return mirrorTree {@link BinarySearchTree}
	 */
	public BinarySearchTree<AnyType> mirror() {
		if (root == null) {
			return null;
		}
		getMirrorTree(root);
		return this;
	}

	private BinaryNode<AnyType> getMirrorTree(BinaryNode<AnyType> node) {
		if (node == null)
			return node;

		/* dFor subtrees */
		BinaryNode<AnyType> left = getMirrorTree(node.left);
		BinaryNode<AnyType> right = getMirrorTree(node.right);

		BinaryNode<AnyType> tempNode = node.left;
		node.left = right;
		node.right = tempNode;

		return node;
	}

	/**
	 * Checks if the tree is mirror of the passed tree
	 * 
	 * @param tree1
	 * @return boolean
	 */
	public boolean isMirror(BinarySearchTree<AnyType> tree1) {
		return isMirrorTree(root, tree1.getRoot());
	}

	private boolean isMirrorTree(BinaryNode<AnyType> node1, BinaryNode<AnyType> node2) {
		if (node1 == null && node2 == null) {
			return true;
		}
		if (node1 != null && node2 != null && node1.element == node2.element)
			return (isMirrorTree(node1.left, node2.right) && isMirrorTree(node1.right, node2.left));
		return false;
	}

	/**
	 * Performs level by level printing of the tree
	 */
	public void printTreeLevel() {
		if (root == null) {
			return;
		}
		Queue<BinaryNode<AnyType>> queue1 = new LinkedList<>();
		Queue<BinaryNode<AnyType>> queue2 = new LinkedList<>();
		queue1.add(root);
		BinaryNode<AnyType> node;
		while (!queue1.isEmpty() || !queue2.isEmpty()) {
			while (!queue1.isEmpty()) {
				node = queue1.poll();
				System.out.println(node.element + "  ");
				if (node.left != null) {
					queue2.add(node.left);
				}
				if (node.right != null) {
					queue2.add(node.right);
				}
			}
			while (!queue2.isEmpty()) {
				node = queue2.poll();
				System.out.println(node.element + "  ");
				if (node.left != null) {
					queue1.add(node.left);
				}
				if (node.right != null) {
					queue1.add(node.right);
				}
			}
		}
		System.out.println();
	}

	/**
	 * Rotates left around the given node.
	 */
	protected void rotateLeft(int value) {
		BinaryNode<AnyType> n = getNode(root, value);
		if (n.getRight() == null) {
			return;
		}
		BinaryNode<AnyType> oldRight = n.getRight();
		n.setRight(oldRight.getLeft());
		BinaryNode<AnyType> parent = n.getParent(root, n);
		if (parent == null) {
			root = oldRight;
		} else if (parent == n) {
			parent.setLeft(oldRight);
		} else {
			parent.setRight(oldRight);
		}
		oldRight.setLeft(n);
	}

	private BinaryNode<AnyType> getNode(BinaryNode<AnyType> cur, int val) {

		BinaryNode<AnyType> result = null;
		if (cur.left != null)
			result = getNode(cur.left, val);

		if (!(cur.element instanceof Integer)) {
			return null;
		}
		if ((Integer) cur.element == val)
			return cur;
		if (result == null && cur.right != null)
			result = getNode(cur.right, val);

		return result;

	}

	/**
	 * Rotates right around the given node.
	 */
	protected void rotateRight(int value) {
		BinaryNode<AnyType> n = getNode(root, value);
		if (n.getLeft() == null) {
			return;
		}
		BinaryNode<AnyType> oldLeft = n.getLeft();
		n.setLeft(oldLeft.right);
		BinaryNode<AnyType> parent = n.getParent(root, n);
		if (parent == null) {
			root = oldLeft;
		} else if (parent.getLeft() == n) {
			parent.setLeft(oldLeft);
		} else {
			parent.setRight(oldLeft);
		}
		oldLeft.setRight(n);
	}

	/**
	 * Internal method to compute height of a subtree.
	 * 
	 * @param t the node that roots the subtree.
	 */
	private int height(BinaryNode<AnyType> t) {
		if (t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType> {
		// Constructors
		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}

		AnyType element; // The data in the node
		BinaryNode<AnyType> left; // Left child
		BinaryNode<AnyType> right; // Right child

		public BinaryNode<AnyType> getLeft() {
			return left;
		}

		public void setLeft(BinaryNode<AnyType> left) {
			this.left = left;
		}

		public BinaryNode<AnyType> getRight() {
			return right;
		}

		public void setRight(BinaryNode<AnyType> right) {
			this.right = right;
		}

		public BinaryNode<AnyType> getParent(BinaryNode<AnyType> root, BinaryNode<AnyType> node) {
			if (root == null || node == null) {
				return null;
			} else if ((root.right != null && root.right.element == node.element)
					|| (root.left != null && root.left.element == node.element)) {
				return root;
			} else {
				BinaryNode<AnyType> found = getParent(root.right, node);
				if (found == null) {
					found = getParent(root.left, node);
				}
				return found;
			}
		}
	}

	/** The tree root. */
	private BinaryNode<AnyType> root;

	public BinaryNode<AnyType> getRoot() {
		return root;
	}

	public void setRoot(BinaryNode<AnyType> root) {
		this.root = root;
	}

// Test program
	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		final int NUMS = 10;
		final int GAP = 2;

		System.out.println("Checking... (no more output means success)");

		for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
			t.insert(i);

		for (int i = 1; i < NUMS; i += 2)
			t.remove(i);

		if (NUMS < 40)
			t.printTree();
		if (t.findMin() != 2 || t.findMax() != NUMS - 2)
			System.out.println("FindMin or FindMax error!");

		for (int i = 2; i < NUMS; i += 2)
			if (!t.contains(i))
				System.out.println("Find error1!");

		int nodecount = t.getNodeCount();
		System.out.println("Number of nodes in the tree = " + nodecount);

		boolean fullTree = t.isFull();
		System.out.println(fullTree == true ? "Tree is full" : "Tree is not full");

		BinarySearchTree<Integer> tree1 = new BinarySearchTree<>();
		BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();

		for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
			tree1.insert(i);
		int tree2Gap = 3;
		int tree2Nums = 20;
		for (int i = tree2Gap; i != 0; i = (i + tree2Gap) % tree2Nums)
			tree2.insert(i);

		boolean sameStructure = t.compareStructure(tree2);
		System.out.println(sameStructure == true ? "Same structure" : "Not same structure");

		boolean isEqual = t.equals(tree2);
		System.out.println(isEqual == true ? "Trees are equal" : "Trees are not equal");

		BinarySearchTree<Integer> copiedTree = t.copy();
		System.out.println(copiedTree.equals(t) ? "Correct Copy" : "Incorrect Copy");
		System.out.println(copiedTree);

		t.mirror();
		boolean isMirrorTree = t.isMirror(tree1);
		System.out.println(isMirrorTree == true ? "Mirror tree" : "Not a Mirror tree");

		t.printTreeLevel();
		t.rotateRight(6);
		t.printTreeLevel();
		t.mirror();
		t.rotateLeft(4);
		t.printTreeLevel();
		for (int i = 1; i < NUMS; i += 2) {
			if (t.contains(i))
				System.out.println("Find error2!");
		}
	}

}
