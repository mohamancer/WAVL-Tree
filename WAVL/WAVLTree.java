package WAVL;
import java.util.Arrays;
/*
* Username : Ayoubatrash , ID : 318476611
* Amer1 , ID : 208644963
*/

public class WAVLTree {
	
	 final WAVLNode ext_leaf=new WAVLNode();
	 WAVLNode root=null;
	 int[] arr0_1= {0,1};
	 int[] arr1_0= {1,0};
	 int[] arr2_0= {2,0};
	 int[] arr0_2= {0,2};
	 int[] arr1_1= {1,1};
	 int[] arr1_2= {1,2};
	 int[] arr2_1= {2,1};
	 int[] arr2_2= {2,2}; 
	 int[] arr2_3= {2,3};
	 int[] arr3_2= {3,2};
	 int[] arr3_1= {3,1};
	 int[] arr1_3= {1,3};

	
	
	public class WAVLNode {
		 int key;
		String value;
		WAVLNode left;
		WAVLNode right;
		WAVLNode parent;
		int rank;
		int size;
		
		/**
		 * 
		 * Build an External Node
		 */
		public WAVLNode() {
			this.size=0;
			this.rank=-1;
		}
		/**
		 * 
		 * @param left
		 * @param parent
		 * @param right
		 * @param k
		 * @param v
		 * A WAVLNode Constructor to construct a specific node
		 */
		public WAVLNode(WAVLNode left,WAVLNode parent ,WAVLNode right, int k,String v) {
			if (left==null) this.left=ext_leaf;
			else this.left=left;
			if (right==null) this.right=ext_leaf;
			else this.right=right;
			this.parent=parent;
			key=k;
			value=v;
			this.rank=1+Math.max(this.left.rank,this.right.rank);
			setSubtreeSize();
			
			
		}
		/**
		 * returns the key of the node or -1 if it is external
		 * 
		 */
		 public int getKey()
	     {
	             
	             return this.key;
	     }
		 /**
			 * returns the value of the node of null of it is external
			 * 
			 */
	     public String getValue()
	     {
	    	 if (this.getRank()==-1) return null;
	         return this.value;
	     }
	     /**
			 * returns the left child of the node or ext_leaf if it is a leaf
			 * 
			 */
	     public WAVLNode getLeft()
	     {
	             if (this.left==ext_leaf) return ext_leaf;
	             return this.left;
	     }
	     /**
			 * returns the right child of the node or ext_leaf if it is a leaf
			 * 
			 */
	     public WAVLNode getRight()
	     {
	    	 if (this.right==ext_leaf) return ext_leaf;
	         return this.right;
	     }
	     /**
			 * returns true if the node is not external
			 * 
			 */
	     public boolean isInnerNode()
	     {
	             if (this.getRank()!=-1) return true;
	             return false;
	     }
	     /**
			 * returns the size of the subtree under the node
			 * or 0 if it external
			 */
	     public int getSubtreeSize()
	     {
	             if (this.getRank()==-1) return 0;
	             return (1+this.getLeft().size+this.getRight().size);
	     }
	     /**
			 * resets the size depending on the size of the children
			 * 
			 */
	     private void setSubtreeSize() {
	    	 if (this.getRank()==-1) this.size= 0;
	 		this.size=1+this.left.size+this.right.size;
	 		
	 	}
	     /**
			 * returns true if the node is a leaf
			 */
		public boolean isLeaf() {
			if (this.getRight().getRank()==-1 && this.getLeft().getRank()==-1) return true;
			return false;
		}
		/**
		 * returns true of the node is unary
		 */
		public boolean isUnary() {
			if (this.right.getRank()==-1 ||this.left.getRank()==-1) return true;
			return false;
		}
		/**
		 * returns the rank of the node
		 */
		public int getRank() {
			return this.rank;
		}
	}


	  /**
	   * public boolean empty()
	   *
	   * returns true if and only if the tree is empty
	   *
	   */
	  public boolean empty() {
	    if (this.getRoot()==null) return true;
	    return false;
	  }

	 /**
	   * public String search(int k)
	   *
	   * returns the info of an item with key k if it exists in the tree
	   * otherwise, returns null
	   */
	  public String search(int k)
	  {
		  
		  WAVLNode current=Position(k);
		  if (current==null) return null;
		  if (current.getKey()==k) return current.getValue();
		  else return null;
		  
	  }
	  

	  /**
	   * public int insert(int k, String i)
	   *
	   * inserts an item with key k and info i to the WAVL tree.
	   * the tree must remain valid (keep its invariants).
	   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
	   * returns -1 if an item with key k already exists in the tree.
	   */
	   public int insert(int k, String i) {
		   
	          if (this.search(k) != null) return -1; 
	          if (this.empty()) {
	        	  this.root=new WAVLNode(ext_leaf,null,ext_leaf,k,i); 
	        	  return 0 ;
	          }
	          WAVLNode parent=Position(k);
	          WAVLNode node=new WAVLNode(ext_leaf,parent,ext_leaf,k,i);
	          if (k<parent.key) parent.left=node;
	          else parent.right=node;
	          
	          if (parent!=null) {
	          parent.setSubtreeSize();
	          }
	          return I_Rebalance(node);
	          
	   }
	   
	   /**
		 * @param node
		 * Rebalances the tree starting from @param node after
		 * an insert is accomplished
		 * @return the number of rebalancing steps
		 */
	   
	   
	   // does the actual rebalancing 
	   private int I_Rebalance(WAVLNode node) {
		   
		if (this.root==node) {
			node.setSubtreeSize();
			return 0;
		}
		WAVLNode parent=node.parent;
		if (parent.rank-node.rank!=0) {
			reSetTreeSize(node);
			return 0;
		}
		int[] node_rank_arr= {parent.rank-parent.left.rank,parent.rank-parent.right.rank};
		if (Arrays.equals(node_rank_arr, arr0_1) ||Arrays.equals(node_rank_arr, arr1_0)) { //case 1: promote
			parent.rank+=1; //promote
			
			parent.setSubtreeSize();
			return 1+I_Rebalance(parent);
		}
		if (Arrays.equals(node_rank_arr, arr0_2)||Arrays.equals(node_rank_arr, arr2_0)) {//case 2: single rotate
			
			if (((node.rank-node.left.rank==1 && node.rank-node.right.rank==2 )&& (node.parent.left==node)) 
				|| ((node.rank-node.left.rank==2 && node.rank-node.right.rank==1 )&& (node.parent.right==node))) {
				
				
				if (Arrays.equals(node_rank_arr, arr0_2)) Rotate(node,"r");
				else Rotate(node,"l");
				reSetTreeSize(node);
				return 2;
			}else {// case 3:double rotate
				 if (Arrays.equals(node_rank_arr, arr0_2)) {
					 Rotate(node.right,"l");
					 Rotate(node.parent,"r");
					 
				 }else {
					 Rotate(node.left, "r");
					 Rotate(node.parent,"l");
				 }
				 node.parent.rank+=1;
				 reSetTreeSize(node);
				 return 5;
			}
		}
		
		return -1; //never reached
		 
	}
	   /**
		 * @param node
		 * @param s
		 * Rotates the given node left of right depending on the string s
		 */
	

	public void Rotate(WAVLNode node, String s) {
		
		if (s.equals("r")) {
			WAVLNode parent=node.parent;
			WAVLNode grandparent=parent.parent;
			WAVLNode x=node;
			WAVLNode z=x.parent;
			WAVLNode a=x.left;
			WAVLNode b=x.right;
			if (z==root) {
				x.parent=null;
				this.root=x;
				}
			x.right=z;
			b.parent=z;
			z.left=b;
			//x.right.left=b;
			z.parent=x;
			x.parent=grandparent;
			
			
			if (grandparent!=null){
				if (grandparent.left==z) {
					grandparent.left=x;
				}
				else {
					grandparent.right=x;
				}
			}
			// demote z
			z.rank-=1;
			
			reSetTreeSize(z);
			
		}
		else {
			WAVLNode parent=node.parent;
			WAVLNode grandparent=parent.parent;
			WAVLNode z=node;
			WAVLNode x=z.parent;
			WAVLNode b=z.left;
			WAVLNode y=z.right;
		
			
			
			
			
			
				if (x==root) {
					z.parent=null;
					this.root=z;
				}
				z.left=x;
				b.parent=x;
				x.right=b;
				//z.left.right=b;
				x.parent=z;
				z.parent=grandparent;
				
			
			
			if (grandparent!=null){
				if (grandparent.left==x) {
					grandparent.left=z;
				}
				else {
					grandparent.right=z;
				}
			}
			// demote x
			x.rank-=1;
			
			reSetTreeSize(x);
		}
		
	}

	/**
	 * @param k
	 * @return the node with key k if it exists in the tree 
	 * otherwise return the position to insert
	 */

	public WAVLNode Position(int k) {
		   {
				  WAVLNode current=this.getRoot();
				  
				  if (current==null) return null;
				  
				  while (current.getKey()!=k){
					  if (current.getKey()==k) return current;
					  if (current.getKey()<k ) {
						  if (current.getRight().rank!=-1) current=current.getRight();
						  else return current;
					  }
					  else {
						  if (current.getLeft().rank!=-1) current=current.getLeft();
						  else return current;
					  }
				  }
				return current;
			  }
	   }
		
	   /**
	   * public int delete(int k)
	   *
	   * deletes an item with key k from the binary tree, if it is there;
	   * the tree must remain valid (keep its invariants).
	   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	   * returns -1 if an item with key k was not found in the tree.
	   */
		
	   public int delete(int k)
	   {
		    WAVLNode node=Position(k);
		    if (node==null) {
		    	return 0;
		    }
		    if (node.getKey()!=k) return -1;
		    if (this.size()==1) {
		    	this.root=null;
		    	return 0;
		    }
		    if (node.isLeaf()) {
		    	return deleteLeaf(node);
		    }else if (node.isUnary()) {
		    	return deleteUnary(node);
		    }else {
		    	return deleteBinary(node);
		    }
	   }

	
	   

	   /**
		 * @param node
		 * deletes a given binary node from the tree by replacing it with its successor
		 * and then deleting the replaced node as a leaf or an unary node 
		 */

	   private int deleteBinary(WAVLNode node) {
		   WAVLNode suc=Successor(node);
		   Replace(node,suc);
		   reSetTreeSize(node);
		   if (node.isLeaf()) return deleteLeaf(node);
		   else return deleteUnary(node);
		  
	}

	   /**
		 * @param node
		 * @param suc
		 * replaces the node with its successor by changing the indicators 
		 */
	private void Replace(WAVLNode node, WAVLNode suc) {
		WAVLNode tmp=this.new WAVLNode(node.left,node.parent,node.right,node.key,node.value);//key and value are not important here
		tmp.rank=node.rank;
		tmp.size=node.size;
		if (root==node) root=suc;
		if (suc.parent!=node) node.parent=suc.parent;
		else node.parent=suc;
		node.right=suc.right;
		node.left=ext_leaf;
		node.rank=suc.rank;
		node.size=suc.size;
		if (suc.parent!=node) {
			if (suc.parent.left==suc) suc.parent.left=node;
			else suc.parent.right=node;
		}
		suc.rank=tmp.rank;
		suc.size=tmp.size;
		suc.parent=tmp.parent;
		suc.left=tmp.left;
		if(tmp.right!=suc) suc.right=tmp.right;
		else suc.right=node;
		if (tmp.parent!=null) {
			if (tmp.parent.left==node) tmp.parent.left=suc;
			else tmp.parent.right=suc;
		}
		if (tmp.getLeft().getRank()!=-1) tmp.left.parent=suc;
		if (tmp.getRight()!=suc) tmp.right.parent=suc;
		
	}
	/**
	 * @param node
	 * deletes the given unary node and 
	 * @return number of rebalancing steps
	 */
	private int deleteUnary(WAVLNode node) {
		if (node.right.getRank()==-1) {
			if (node==root) root=node.left;
			node.left.parent=node.parent;
			if (node.parent!=null) {
			if (node.parent.left==node) node.parent.left=node.left;
			else node.parent.right=node.left;
			}
			reSetTreeSize(node.parent);
			return D_Rebalance(node.parent);
		}else {
			if (node==root) root=node.right;
			node.right.parent=node.parent;
			if (node.parent!=null) {
			if (node.parent.left==node ) node.parent.left=node.right;
			else node.parent.right=node.right;
			}
			reSetTreeSize(node.parent);
			return D_Rebalance(node.parent); 
		}
	}
	/**
	 * @param node
	 * deletes the given leaf node and 
	 * @return number of rebalancing steps
	 */
	private int deleteLeaf(WAVLNode node) {
		if (node!=root) {
			if (node.parent.left==node) node.parent.left=ext_leaf;
			else node.parent.right=ext_leaf;
			if (node.parent.isLeaf()) {
				node.parent.rank-=1;
				reSetTreeSize(node.parent);
				return 1+D_Rebalance(node.parent.parent);
			}
			reSetTreeSize(node.parent);
			return D_Rebalance(node.parent);
		}else {
			root=null;
			return 0;
		}
	}
	/**
	 * @param node
	 * Rebalances the tree starting from node after a delete is accomplished
	 * @return the number of rebalancing steps that occurred
	 */
	private int D_Rebalance(WAVLNode node) {
		if (node==null) { 
			return 0; 
		}
		int[] node_rank_arr= {node.rank-node.left.rank,node.rank-node.right.rank};
		if (Arrays.equals(node_rank_arr, arr3_2)||Arrays.equals(node_rank_arr, arr2_3)) {//case 1: Demote
			node.rank-=1;
			return 1+D_Rebalance(node.parent);
		}else if (Arrays.equals(node_rank_arr, arr3_1)) {//cases 2,3,4
			WAVLNode RightChild=node.right;
			int[] RightChild_arr= {RightChild.rank-RightChild.left.rank,RightChild.rank-RightChild.right.rank};
			if (Arrays.equals(RightChild_arr, arr2_2)) {// case 2: double Demote
				node.rank-=1;
				RightChild.rank-=1;
				return 2+D_Rebalance(node.parent);
			}else if (Arrays.equals(RightChild_arr, arr1_1)||Arrays.equals(RightChild_arr, arr2_1)) {// case 3: Rotate 
				RightChild.rank+=1;
				Rotate(RightChild,"l");
				if (node.isLeaf()) {
					node.rank-=1;
					reSetTreeSize(node.parent);
					return 4;
				}
				reSetTreeSize(node);
				return 3;
				
			}else if (Arrays.equals(RightChild_arr, arr1_2)) {//case 4: double rotate
				node.rank-=1;
				RightChild.left.rank+=2;
				Rotate(RightChild.left,"r");
				Rotate(node.right,"l");
				reSetTreeSize(node);
				return 5;
			}
		}else if (Arrays.equals(node_rank_arr, arr1_3)) {//cases 2,3,4 symmetric
			WAVLNode LeftChild=node.left;
			int[] LeftChild_arr= {LeftChild.rank-LeftChild.left.rank,LeftChild.rank-LeftChild.right.rank};
			if (Arrays.equals(LeftChild_arr, arr2_2)) {// case 2: double Demote symmetric
				node.rank-=1;
				LeftChild.rank-=1;
				return 2+D_Rebalance(node.parent);
			}else if (Arrays.equals(LeftChild_arr, arr1_1)||Arrays.equals(LeftChild_arr, arr1_2)) {// case 3: Rotate symmetric
				LeftChild.rank+=1;
				Rotate(LeftChild,"r");
				if (node.isLeaf()) {
					node.rank-=1;
					reSetTreeSize(node);
					return 4;
					
				}
				reSetTreeSize(node);
				return 3;
				
			}else if (Arrays.equals(LeftChild_arr, arr2_1)) {//case 4: double rotate symmetric
				node.rank-=1;
				LeftChild.right.rank+=2;
				Rotate(LeftChild.right,"l");
				Rotate(node.left,"r");
				reSetTreeSize(node);
				return 5;
			}
			
		}
		reSetTreeSize(node);
		return 0;
		
	}

	/**
	    * public String min()
	    *
	    * Returns the info of the item with the smallest key in the tree,
	    * or null if the tree is empty
	    */
	   public String min()
	   {
	           if (this.empty()==true) return null;
	           WAVLNode current=this.getRoot();
	           while (current.getLeft().getRank()!=-1) {
	        	   current =current.getLeft();
	        	   
	           }
	           return current.getValue();
	   }

	   /**
	    * public String max()
	    *
	    * Returns the info of the item with the largest key in the tree,
	    * or null if the tree is empty
	    */
	   public String max()
	   {
		   if (this.empty()==true) return null;
           WAVLNode current=this.getRoot();
           
           while (current.getRight().getRank()!=-1) {
        	   
        	   current =current.getRight();
           }
           
           return current.getValue();
	   }

	   /**
	   * public int[] keysToArray()
	   *
	   * Returns a sorted array which contains all keys in the tree,
	   * or an empty array if the tree is empty.
	   */
	   public int[] keysToArray()
	   {
		   int[] empty_arr= {};
		   
		   if (this.empty()==true) return empty_arr;
		   WAVLNode[] arr=activate_in_order();
		   int[] final_arr= new int[arr.length];
	       for (int i=0;i<this.getRoot().size;i++) {
	    	   final_arr[i]=arr[i].getKey();
	       }
	       return final_arr;

	   }

	   /**
	   * public String[] infoToArray()
	   *
	   * Returns an array which contains all info in the tree,
	   * sorted by their respective keys,
	   * or an empty array if the tree is empty.
	   */
	   public String[] infoToArray()
	   {
		   String[] empty_arr= {};
		   if (this.empty()==true) return empty_arr;
		   
		   WAVLNode[] arr=activate_in_order();
		   String[] final_arr= new String[arr.length];
	       for (int i=0;i<this.getRoot().size;i++) {
	    	   final_arr[i]=arr[i].getValue();
	       }
	       return final_arr;
		                     
	   }
	   
	   /**
	    * activates the in_order function on the root
	    * 
	    * @return the sorted array of nodes
	    */
	   public WAVLNode[] activate_in_order() {
		   
	        WAVLNode[] arr = new WAVLNode[this.size()]; 
	        int i=0;
	        in_order(this.getRoot(),i,arr);
	        return arr;
	   }
	   
	   /**
		 * 
		 * @param r
		 * @param i
		 * @param arr
		 * in order traversal of the tree, builds the array that the above function returns
		 * @return the index in the in_order traversal 
		 * @post arr is the on_order traversal of the tree until index i
		 */
	   public int in_order(WAVLNode r,int i,WAVLNode[] arr) {
		   if (r.getRank()==-1) {
			   return i;
		   }
		   
		   i=in_order(r.left,i,arr);
		   arr[i]=r;
		   i+=1;
		   i=in_order(r.right,i,arr);
		   return i;
		   
		   
	   }

	   /**
	    * public int size()
	    *
	    * Returns the number of nodes in the tree.
	    *
	    */
	   public int size()
	   {
	           if (this.getRoot()!=null) return this.getRoot().size;
	           return 0;
	   }
	   
	     /**
	    * public WAVLNode getRoot()
	    *
	    * Returns the root WAVL node, or null if the tree is empty
	    *
	    */
	   public WAVLNode getRoot()
	   {
		   if (this.root==null) return null;
	       return root;
	   }
	     /**
	    * public int select(int i)
	    *
	    * Returns the value of the i'th smallest key (return -1 if tree is empty)
	    * Example 1: select(1) returns the value of the node with minimal key 
	        * Example 2: select(size()) returns the value of the node with maximal key 
	        * Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor  
	    *
	    */   
	   public String select(int i)
	   {	
		   String[] info_arr=infoToArray();
		   if (i>info_arr.length || empty()) return null;
		   return info_arr[i-1];
	   }
	   
	   /**
		 * @param node
		 * @return the successor of node
		 */
	   public WAVLNode Successor(WAVLNode node) {
		   
		   if (node.getRight().getRank()!=-1) {
			   WAVLNode current=node.getRight();
			   while (current.left.getRank()!=-1) {
				   current=current.getLeft();
			   }
			   return current;
		   }else {
		  WAVLNode parent=node.parent;
		  while (parent!=null && parent.getRight()==node) {
			  node =parent;
			  parent=node.parent;
		  }
		  return parent;
		   }
	   }
	   /**
		 * @param node
		 * starts with node and resets its size depending on its children
		 * moves up the tree doing the same thing until it reaches the root
		 */
	   private void reSetTreeSize(WAVLNode node) {
		   if (node!=null) { 
	        WAVLNode currentNode = node;
	        while (currentNode.parent!=null) {
	            currentNode.setSubtreeSize();
	            currentNode = currentNode.parent;
	        }
	        currentNode.setSubtreeSize();
	    }
	   }
	   
}
