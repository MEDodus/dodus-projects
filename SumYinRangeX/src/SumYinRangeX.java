import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class SumYinRangeX {
	
	// Nested class Node describes the tuple needed to represent a node in our binary tree
	public class Node {
		
		// Node is the tuple (key, val, sum, Node, Node)
		int key; 
		int val;
		int sum;
		Node left;
		Node right;
		
		public Node(int x, int y) {
			
			this.key = x;
			this.val = y;
			
			// Sum will get updated when we insert
			this.sum = y;
			
			// Initially a new node will have no children references
			this.left = null;
			this.right = null;
		}
	}
	
	// Class variable for the root of the binary tree
	public Node root;
	
	// Constructor, sets root to null
	public SumYinRangeX() {
		this.root = null;
	}
	
	public void insert(int x, int y) {
		
		// Initialize root of tree if it is null
		if (root == null) {
			
			root = new Node(x, y);
			
		} else {
			
			/* If the root is not null,
			 * traverse the tree until
			 * insertion point is found
			 */
			
			Node copy = root;
			
			// Update sum rooted at copy
			copy.sum += y;
			
			boolean found = false;
			while (!found && (copy.left != null || copy.right != null)) {
				
				if (x <= copy.key && copy.left != null) {
					copy = copy.left;
					
					// Update sum rooted at y
					copy.sum += y;
				} else if (x > copy.key && copy.right != null) {
					copy = copy.right;
					
					// Update sum rooted at y
					copy.sum += y;
				} else { // Found the parent of insertion node
					found = true;
				}	
			}
			
			// Insert node as left or right child
			if (x <= copy.key) {
				copy.left = new Node(x, y);
			} else {
				copy.right = new Node(x, y);
			}
		}
	}
	
	// For personal verification that tree is being represented correctly
	public void inorderTraversal(Node curr) {
		if (curr != null) {
			inorderTraversal(curr.left);
			System.out.print(curr.key + " ");
			inorderTraversal(curr.right);
		}
	}
	
	// For personal verification that tree is being represented correctly
	public void preorderTraversal(Node curr) {
		if (curr != null) {
			System.out.print(curr.key + " ");
			preorderTraversal(curr.left);
			preorderTraversal(curr.right);
		}
	}
	
	// For personal verification that tree is being represented correctly
	public void preorderSum(Node curr) {
		if (curr != null) {
			System.out.print(curr.sum + " ");
			preorderSum(curr.left);
			preorderSum(curr.right);
		}
	}
	
	public int sumYinRangeX(int min, int max) {
		
		Node copy = root;
		
		// Search for first node in range of [min, max]
		while (copy != null && (copy.key < min || copy.key > max)) {
			if (copy.key < min) {
				copy = copy.right;
			} else if (copy.key > max) {
				copy = copy.left;
			}
		}
		
		// Calculate it's sumYinRangeX value
		if (copy != null) {
			return copy.val + treeCountGE(copy.left, min) + treeCountLE(copy.right, max);
		}
		return 0;
	}
	
	public static int treeCountLE(Node node, int k) {
		int sum = 0;
		while (node != null) {
			if (node.key <= k) {
				sum += node.val;
				if (node.left != null) {
					sum += node.left.sum;
				}
				node = node.right;
			} else {
				node = node.left;
			}
		}
		return sum;
	}
	
	public static int treeCountGE(Node node, int k) {
		int sum = 0;
		while (node != null) {
			if (node.key >= k) {
				sum += node.val;
				if (node.right != null) {
					sum += node.right.sum;
				}
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return sum;
	}
	
	// Grader will probably not need to use this
	public void clear() {
		root = null;
	}
	
	public static void main(String[] args) {
		
		
		// Instantiate tree representation
		SumYinRangeX tree = new SumYinRangeX();

	    try {
	    	
	    	// Get command line argument input for data file and range file
	        File input = new File(args[0]);
	        File range = new File(args[1]);
	        
	        
	        Scanner in = new Scanner(input);
	        
	        // Insert each data point into the tree
	        while (in.hasNextLine()) {
	          String[] line = in.nextLine().split("\\s+");
	          tree.insert(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
	        }
	        
	        Scanner rd = new Scanner(range);
	        
	        // Sum all values in range from range point provided and print the result
	        while (rd.hasNextLine()) {
	        	String[] line = rd.nextLine().split("\\s+");
	        	int res = tree.sumYinRangeX(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
	        	System.out.println(line[0] + " " + line[1] + " " + res);
	        }
	        
	        // Close Scanner variables
	        rd.close();
	        in.close();
	        
	      } catch (FileNotFoundException e) {
	    	  
	        System.out.println("File not found. Make sure to type the relative path if the test file is in the same directory or a ");
	        System.out.println("subdirectory from the .class file. If not, use absolute the absolute path to the file when typing in your arguments.");
	        e.printStackTrace();
	        
	      }
	}
}
