import java.util.LinkedList;
import java.util.Queue;

public class Runner
{
	public static Node root = new Node();

	public static void main(String args[])
	{
		int[] data = { 1, 20, 4, 15, 3, 25, 7, 80, 2, 5, 70, 100, 6, 8, 16, 17, 18, 21, 52, 78, 152, 85, 13, 19, 153,
				154, 155, 35, 36, 22, 26, 30, 9, 14 };
		for (int i = 0; i < data.length; i++)
		{
			System.out.println("\n Inserting " + data[i] + ". . .");
			Pair insertPair = new Pair(data[i]);
			root.findInsertionNode(insertPair.maxKey).insertOrSplit(insertPair);
			printLevelOrder(root);
		}
	}

	/**
	 * Idea from
	 * https://www.geeksforgeeks.org/print-level-order-traversal-line-line/
	 */
	// Iterative method to do level order traversal line by line
	static void printLevelOrder(Node root) {
		Queue<Node> q = new LinkedList<Node>();
		Queue<String> level = new LinkedList<String>();

		q.add(root);

		while (!q.isEmpty()) {

			// nodeCount (queue size) indicates number of nodes
			// at current level.
			int nodeCount = q.size();
			if (nodeCount == 0)
				break;

			// Dequeue all nodes of current level and Enqueue all
			// nodes of next level
			while (nodeCount > 0) {
				Node node = q.peek();
				for (Pair p : node.pairs)
				{
					if (p.maxKey != 0)
					{
						level.add(p.maxKey + " ");

						if (p.children != null)
						{
							q.add(p.children);
						}
					} else if (p.children != null)
					{
						q.add(p.children);
					}

				}
				System.out.print(level);
				level.clear();
				q.remove();
				nodeCount--;
			}
			System.out.println();

		}

	}

}
