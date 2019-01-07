/**
 * Node for Separate Chaining in HashDictionary
 * @author David Le
 *
 */
public class Node {
	private Node left, right;
	private Configuration element;
	
	public Node(){
		left = null;
		right = null;
		element = null;
	}
	
	/**
	 * Creates a node storing an element of type Configuration
	 * @param elem
	 */
	public Node(Configuration elem) {
		element = elem;
		next = null;
	}
	/**
	 * Returns the next connected node
	 * @return
	 */
	public Node getNext() {
		return next;
	}
	/**
	 * Returns the element stored
	 * @return
	 */
	public Configuration getElement() {
		return element;
	}
	
	/**
	 * Sets the next node to be the one desired
	 * @param node
	 */
	public void setNext(Node node) {
		next = node;
	}
}
