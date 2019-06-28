import java.util.ArrayList;

public class Node
{
	ArrayList<Pair> pairs = new ArrayList<Pair>();
	Node parent = null;
	
	public Node()
	{
		initiatePairArray();
	}
	private void initiatePairArray()
	{
		for(int i = 0; i < 5; i++)
		{
			Pair newPair = new Pair(0);
			pairs.add(i, newPair);
		}
	}
	public Node findInsertionNode(int findKey)
	{
		if(isLeaf())
			return this;

		for(int i = 0; i<pairs.size(); i++ )
		{
			if(findKey < pairs.get(i).maxKey)
				return pairs.get(i).children.findInsertionNode(findKey);	
		}
		return pairs.get(4).children.findInsertionNode(findKey);
	}

	public void insertOrSplit(Pair insertPair)
	{
		if(!isFull())
		{
			insertIntoNode(insertPair);
		}
		else
		{
			if(parent == null)
			{
				Pair promotedPair;
				Pair rightMost = new Pair(0);
				parent = new Node();
				
				rightMost = this.pairs.get(4);
				this.pairs.set(4, insertPair);
				this.pairs.add(rightMost);
				this.sort();
				promotedPair = this.pairs.get(2);
				
				Node leftRightMost = promotedPair.children;
				this.pairs.remove(2);
				//setting the appropriate children to its correct child.
				promotedPair.children = new Node();
				promotedPair.children.pairs.set(2, this.pairs.get(0));
				promotedPair.children.pairs.set(3, this.pairs.get(1));
				
				//reseting the pair
				this.pairs.set(0, new Pair(0));
				this.pairs.set(1, new Pair(0));
				
				promotedPair.children.parent = parent;
				parent.pairs.set(3, promotedPair);
				
				
				//leftchild's right most
				parent.pairs.get(3).children.pairs.set(4, new Pair(0));
				parent.pairs.get(3).children.pairs.get(4).children = new Node();
				parent.pairs.get(3).children.pairs.get(4).children = leftRightMost;
				if(leftRightMost != null)
					parent.pairs.get(3).children.pairs.get(4).children.parent = promotedPair.children;
				
				
				//right most
				parent.pairs.set(4, new Pair(0));
				parent.pairs.get(4).children = new Node();
				parent.pairs.get(4).children= this;
				
				Runner.root = parent;
			}
			else
			{
				Pair promotedPair;
				Node leftRightMost = null;
				Pair temp = this.pairs.get(4);
				this.pairs.set(4, insertPair);
				this.pairs.add(temp);
				this.sort();
				
				if(this.pairs.get(2).children != null)
				{
					leftRightMost = this.pairs.get(2).children;
				}
					

				promotedPair = this.pairs.get(2);
				this.pairs.remove(2);
				promotedPair.children = new Node();
				
				promotedPair.children.pairs.set(2, this.pairs.get(0));
				promotedPair.children.pairs.set(3, this.pairs.get(1));
				
				
				this.pairs.set(0, new Pair(0));
				this.pairs.set(1, new Pair(0));
				if(leftRightMost != null)
				{
					leftRightMost.parent = promotedPair.children;
					promotedPair.children.pairs.get(4).children = new Node();
					promotedPair.children.pairs.get(4).children = leftRightMost;
					promotedPair.children.parent = this.parent;
					
					for(Pair p: promotedPair.children.pairs)
					{
						if(p.maxKey != 0 && p.children != null)
						{
							p.children.parent = promotedPair.children;
						}
					}
					
					
				} 
				else if(this.parent != null)
					promotedPair.children.parent = this.parent;
				
				
			parent.insertOrSplit(promotedPair);
				
				
			}
		}
	}
	private void insertIntoNode(Pair newPair)
	{
		int count = 0;
		for(Pair pair: pairs)
		{
			if(pair.maxKey == 0 && pair.children == null)
			{
				pairs.set(count,newPair);
				sort(); 
				break;
			} 
			count++;
		}
	}
	private void sort()
	{
		int min;
		for (int i = 0; i < pairs.size()-2; i++) {
			min = i;
			for (int j = i + 1; j< pairs.size()- 1; j++)
			{
				if (pairs.get(j).maxKey < pairs.get(min).maxKey)
				{
					min = j;
				}
			}
			Pair temp = pairs.get(i);
			pairs.set(i,pairs.get(min));
			pairs.set(min,temp);
		}
	}
	private boolean isLeaf()
	{
		for(Pair pair: pairs)
		{
			if(pair.children != null)
			{
				return false;
			}
		}
		return true;
	}
	private boolean isFull()
	{
		for(int i = 0; i < pairs.size()-1;i++)
		{
			if(pairs.get(i).maxKey == 0)
				return false;
		}
		return true;
	}
}
