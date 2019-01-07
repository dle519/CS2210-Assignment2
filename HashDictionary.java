import java.util.Arrays;

/**
 * Creates a HashDictionary using separate chaining
 * 
 * @author David Le
 *
 */
public class HashDictionary implements DictionaryADT {
	Node hashTable[];
	private int size;
	private boolean NOT_FOUND = false;
	Node EMPTY = new Node(new Configuration("EMPTY", 0));

	/**
	 * Creates a hashtable and fills it with an empty node
	 * @param size - size of the hashtable
	 */
	public HashDictionary(int size) {
		this.size = size;
		hashTable = new Node[this.size];
		// Fill hashtable with empty nodes
		Arrays.fill(hashTable, EMPTY);
	}

	@Override
	public int put(Configuration data) throws DictionaryException {
		int key = hashFunction(data.getStringConfiguration());
		Node current = hashTable[key]; // Current node
		Node newNode = new Node(data); // Node to be added
		// To find a location to insert the new node
		while (!NOT_FOUND) {
			// If node is already in the dictionary
			if (current != null) {
				if (data.getStringConfiguration().equals(current.getElement().getStringConfiguration())) {
					throw new DictionaryException("Dictionary already contains this entry!");
				}
			}
			if (current.equals(EMPTY)) {
				hashTable[key] = newNode;
				break;
			} else if (current.getNext() == null) {
				current.setNext(newNode);
				break;
			}
			// Get next node until an empty one is found
			current = current.getNext();
		}
		return 0;
	}

	@Override
	public void remove(String config) throws DictionaryException {
		int key = hashFunction(config); // Location within the dictionary
		Node current = hashTable[key]; // Current node
		while (!NOT_FOUND) {
			Node next = current.getNext(); // Next node
			// If the current node is the one desired to be removed
			if (current.equals(EMPTY) || current == null) {
				throw new DictionaryException("This configuration is not in this dictionary!");
			}
			else if (current.getElement().getStringConfiguration().equals(config)) {
				hashTable[key] = next;
				break;
				// If the next node is the one desired to be removed
			} 
			else if (next != null && next.getElement().getStringConfiguration().equals(config)) {
				current.setNext(next.getNext());
				break;
			} 
			else if (current.getNext() == null){
				break;
			}
			current = current.getNext();
		}

	}

	@Override
	public int getScore(String config) {
		int result = -1;
		int key = hashFunction(config); // Key for location in dictionary
		Node current = hashTable[key];
		while (!NOT_FOUND) {
			// If its not in the list
			if (current == null) {
				break;
			}
			// If the current node has the configuration desired
			else if (config.equals(current.getElement().getStringConfiguration())) {
				result = current.getElement().getScore();
				break;
			} else {
				current = current.getNext();
			}

		}

		return result;
	}

	/**
	 * Produces they key location in the dictionary for each entry
	 * 
	 * @param config - configuration of the board
	 * @return They location in the dictionary
	 */
	private int hashFunction(String config) {
		int length = config.length();
		int result = (int) config.charAt(length - 1);
		for (int i = config.length() - 2; i <= 0; i--) {
			result = (result + (int) config.charAt(i) % this.size);
		}

		return result;

	}

	public static void main(String[] args) {
		Configuration w = new Configuration("abc", 5);
		HashDictionary test = new HashDictionary(10);
		char c = 'a';
		System.out.println((int) c);
	}

}
