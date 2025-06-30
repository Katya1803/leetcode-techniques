# Tree Algorithms - CS Fundamentals

## Core Concepts and Mathematical Foundation

### Tree Definition
**Tree:** A connected acyclic graph with n nodes and n-1 edges, where any two nodes are connected by exactly one path.

**Formal properties:**
- **Connected:** Path exists between any two nodes
- **Acyclic:** No cycles exist
- **Minimal connected:** Removing any edge disconnects the tree
- **Maximal acyclic:** Adding any edge creates a cycle

### Tree Terminology
```
        A (root)
       / \
      B   C (siblings)
     /|   |\
    D E   F G (leaves)
   /
  H
```

**Key terms:**
- **Root:** Top node (A)
- **Parent:** Node with children (A is parent of B, C)
- **Child:** Node with parent (B, C are children of A)
- **Leaf:** Node with no children (E, F, G, H)
- **Internal node:** Node with at least one child (A, B, C, D)
- **Siblings:** Nodes with same parent (B, C are siblings)
- **Ancestor:** Node on path from root (A is ancestor of H)
- **Descendant:** Node in subtree (H is descendant of A)

### Tree Properties
**Height:** Maximum distance from root to any leaf
**Depth of node:** Distance from root to that node
**Level:** All nodes at same depth
**Degree:** Number of children of a node

**Mathematical relationships:**
- **Nodes at level i:** At most 2^i in binary tree
- **Total nodes in binary tree of height h:** At most 2^(h+1) - 1
- **Minimum height for n nodes:** ⌈log₂(n+1)⌉ - 1
- **Maximum height for n nodes:** n - 1 (degenerate tree)

## Binary Trees

### Binary Tree Definition
**Binary Tree:** Each node has at most two children, typically called left and right child.

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```

### Binary Tree Types

#### 1. Full Binary Tree
**Definition:** Every internal node has exactly two children

**Properties:**
- If n = number of internal nodes, then total nodes = 2n + 1
- Number of leaves = n + 1

#### 2. Complete Binary Tree
**Definition:** All levels filled except possibly the last, which is filled left to right

**Properties:**
- Height = ⌊log₂(n)⌋
- Can be efficiently represented as array
- Used in binary heaps

#### 3. Perfect Binary Tree
**Definition:** All internal nodes have two children and all leaves at same level

**Properties:**
- Height h has exactly 2^(h+1) - 1 nodes
- 2^h leaves at level h

#### 4. Balanced Binary Tree
**Definition:** Height difference between left and right subtrees is at most 1 for every node

**Height guarantee:** O(log n) for n nodes

## Tree Traversal Algorithms

### Depth-First Search (DFS) Traversals

#### 1. Inorder Traversal (Left-Root-Right)
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    inorderHelper(root, result);
    return result;
}

private void inorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    inorderHelper(node.left, result);   // Left
    result.add(node.val);               // Root
    inorderHelper(node.right, result);  // Right
}
```

**Iterative version:**
```java
public List<Integer> inorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    
    while (current != null || !stack.isEmpty()) {
        // Go to leftmost node
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        
        // Process current node
        current = stack.pop();
        result.add(current.val);
        
        // Move to right subtree
        current = current.right;
    }
    
    return result;
}
```

**Key insight:** For BST, inorder traversal gives sorted sequence

#### 2. Preorder Traversal (Root-Left-Right)
```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorderHelper(root, result);
    return result;
}

private void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);               // Root
    preorderHelper(node.left, result);  // Left
    preorderHelper(node.right, result); // Right
}
```

**Applications:**
- Tree serialization
- Expression tree evaluation (prefix notation)
- Creating copy of tree

#### 3. Postorder Traversal (Left-Right-Root)
```java
public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    postorderHelper(root, result);
    return result;
}

private void postorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    postorderHelper(node.left, result);  // Left
    postorderHelper(node.right, result); // Right
    result.add(node.val);                // Root
}
```

**Applications:**
- Tree deletion (delete children before parent)
- Calculating size/height of subtrees
- Expression tree evaluation (postfix notation)

### Breadth-First Search (BFS) Traversal

#### Level-Order Traversal
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> currentLevel = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            currentLevel.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(currentLevel);
    }
    
    return result;
}
```

**Applications:**
- Print tree level by level
- Find minimum depth
- Serialize tree by levels

## Binary Search Trees (BST)

### BST Property (Invariant)
**Definition:** For every node in BST:
- All nodes in left subtree have values less than node's value
- All nodes in right subtree have values greater than node's value
- Both left and right subtrees are also BSTs

**Mathematical representation:**
```
For node with value v:
∀x ∈ left_subtree: x.val < v
∀y ∈ right_subtree: y.val > v
```

### BST Operations

#### 1. Search
```java
public TreeNode search(TreeNode root, int val) {
    if (root == null || root.val == val) {
        return root;
    }
    
    if (val < root.val) {
        return search(root.left, val);
    } else {
        return search(root.right, val);
    }
}
```

**Time complexity:** O(h) where h = height of tree
**Best case:** O(log n) for balanced tree
**Worst case:** O(n) for skewed tree

#### 2. Insertion
```java
public TreeNode insert(TreeNode root, int val) {
    if (root == null) {
        return new TreeNode(val);
    }
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else if (val > root.val) {
        root.right = insert(root.right, val);
    }
    // If val == root.val, do nothing (no duplicates)
    
    return root;
}
```

#### 3. Deletion (Most Complex)
```java
public TreeNode delete(TreeNode root, int val) {
    if (root == null) return null;
    
    if (val < root.val) {
        root.left = delete(root.left, val);
    } else if (val > root.val) {
        root.right = delete(root.right, val);
    } else {
        // Node to delete found
        
        // Case 1: Leaf node
        if (root.left == null && root.right == null) {
            return null;
        }
        
        // Case 2: One child
        if (root.left == null) {
            return root.right;
        }
        if (root.right == null) {
            return root.left;
        }
        
        // Case 3: Two children
        // Find inorder successor (smallest in right subtree)
        TreeNode successor = findMin(root.right);
        root.val = successor.val;
        root.right = delete(root.right, successor.val);
    }
    
    return root;
}

private TreeNode findMin(TreeNode node) {
    while (node.left != null) {
        node = node.left;
    }
    return node;
}
```

**Deletion cases analysis:**
1. **Leaf node:** Simply remove
2. **One child:** Replace with child
3. **Two children:** Replace with inorder successor (or predecessor)

### BST Validation
```java
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    
    if (node.val <= min || node.val >= max) {
        return false;
    }
    
    return validate(node.left, min, node.val) &&
           validate(node.right, node.val, max);
}
```

**Key insight:** Use bounds instead of just comparing with parent

## Advanced Tree Algorithms

### 1. Lowest Common Ancestor (LCA)

#### For BST (Optimized)
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    
    // Both nodes in left subtree
    if (p.val < root.val && q.val < root.val) {
        return lowestCommonAncestor(root.left, p, q);
    }
    
    // Both nodes in right subtree
    if (p.val > root.val && q.val > root.val) {
        return lowestCommonAncestor(root.right, p, q);
    }
    
    // Split case: one in left, one in right (or one is root)
    return root;
}
```

#### For General Binary Tree
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) {
        return root;
    }
    
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    if (left != null && right != null) {
        return root;  // Split case
    }
    
    return left != null ? left : right;  // Return non-null child
}
```

### 2. Tree Diameter
**Definition:** Longest path between any two nodes

```java
public int diameterOfBinaryTree(TreeNode root) {
    int[] maxDiameter = {0};
    calculateHeight(root, maxDiameter);
    return maxDiameter[0];
}

private int calculateHeight(TreeNode node, int[] maxDiameter) {
    if (node == null) return 0;
    
    int leftHeight = calculateHeight(node.left, maxDiameter);
    int rightHeight = calculateHeight(node.right, maxDiameter);
    
    // Update diameter: path through current node
    maxDiameter[0] = Math.max(maxDiameter[0], leftHeight + rightHeight);
    
    // Return height of current subtree
    return Math.max(leftHeight, rightHeight) + 1;
}
```

**Key insight:** Diameter either passes through root or lies entirely in one subtree

### 3. Tree Serialization and Deserialization
```java
public String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    buildString(root, sb);
    return sb.toString();
}

private void buildString(TreeNode node, StringBuilder sb) {
    if (node == null) {
        sb.append("null,");
    } else {
        sb.append(node.val).append(",");
        buildString(node.left, sb);
        buildString(node.right, sb);
    }
}

public TreeNode deserialize(String data) {
    Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
    return buildTree(nodes);
}

private TreeNode buildTree(Queue<String> nodes) {
    String val = nodes.poll();
    if ("null".equals(val)) {
        return null;
    } else {
        TreeNode node = new TreeNode(Integer.valueOf(val));
        node.left = buildTree(nodes);
        node.right = buildTree(nodes);
        return node;
    }
}
```

## Balanced Trees Theory

### AVL Trees
**Definition:** Self-balancing BST where height difference between left and right subtrees is at most 1 for every node.

**Balance factor:** height(right) - height(left) ∈ {-1, 0, 1}

**Rotations for rebalancing:**
```
Left Rotation (Right-Heavy):
    y               x
   / \             / \
  x   C     →     A   y
 / \                 / \
A   B               B   C

Right Rotation (Left-Heavy):
  x                 y
 / \               / \
y   C      →      A   x
/ \                   / \
A  B                 B   C
```

**Height guarantee:** For n nodes, height ≤ 1.44 log₂(n + 2) - 0.328

### Red-Black Trees
**Properties:**
1. Every node is either red or black
2. Root is black
3. All leaves (NIL nodes) are black
4. Red nodes cannot have red children
5. All paths from node to descendant leaves contain same number of black nodes

**Height guarantee:** For n nodes, height ≤ 2 log₂(n + 1)

**Applications:** Used in Java TreeMap, C++ std::map

## Tree-Based Problem Patterns

### 1. Path Sum Problems
```java
// Path sum equals target
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    
    if (root.left == null && root.right == null) {
        return root.val == targetSum;
    }
    
    int remaining = targetSum - root.val;
    return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
}

// Count all paths with sum
public int pathSumCount(TreeNode root, int targetSum) {
    if (root == null) return 0;
    
    return pathFromNode(root, targetSum) +
           pathSumCount(root.left, targetSum) +
           pathSumCount(root.right, targetSum);
}

private int pathFromNode(TreeNode node, long targetSum) {
    if (node == null) return 0;
    
    int count = 0;
    if (node.val == targetSum) count++;
    
    count += pathFromNode(node.left, targetSum - node.val);
    count += pathFromNode(node.right, targetSum - node.val);
    
    return count;
}
```

### 2. Tree Construction from Traversals
```java
// Build tree from preorder and inorder
public TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        inorderMap.put(inorder[i], i);
    }
    
    return build(preorder, 0, preorder.length - 1,
                 inorder, 0, inorder.length - 1, inorderMap);
}

private TreeNode build(int[] preorder, int preStart, int preEnd,
                      int[] inorder, int inStart, int inEnd,
                      Map<Integer, Integer> inorderMap) {
    if (preStart > preEnd) return null;
    
    int rootVal = preorder[preStart];
    TreeNode root = new TreeNode(rootVal);
    
    int rootIndex = inorderMap.get(rootVal);
    int leftSize = rootIndex - inStart;
    
    root.left = build(preorder, preStart + 1, preStart + leftSize,
                     inorder, inStart, rootIndex - 1, inorderMap);
    root.right = build(preorder, preStart + leftSize + 1, preEnd,
                      inorder, rootIndex + 1, inEnd, inorderMap);
    
    return root;
}
```

### 3. Tree Flattening and Transformation
```java
// Flatten binary tree to linked list
public void flatten(TreeNode root) {
    if (root == null) return;
    
    // Flatten left and right subtrees
    flatten(root.left);
    flatten(root.right);
    
    // Store right subtree
    TreeNode rightSubtree = root.right;
    
    // Make left subtree the right subtree
    root.right = root.left;
    root.left = null;
    
    // Find the end of new right subtree and attach old right
    TreeNode current = root;
    while (current.right != null) {
        current = current.right;
    }
    current.right = rightSubtree;
}
```

## Complexity Analysis

### Time Complexity Summary
| Operation | BST (Average) | BST (Worst) | Balanced Tree |
|-----------|---------------|-------------|---------------|
| Search | O(log n) | O(n) | O(log n) |
| Insert | O(log n) | O(n) | O(log n) |
| Delete | O(log n) | O(n) | O(log n) |
| Traversal | O(n) | O(n) | O(n) |

### Space Complexity
**Recursive algorithms:** O(h) where h = height
- **Best case:** O(log n) for balanced trees
- **Worst case:** O(n) for skewed trees

**Iterative with explicit stack:** O(h) for stack space

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Forgetting base cases** in recursive functions
2. **Not handling null nodes** properly
3. **Confusing tree properties** (BST vs general binary tree)
4. **Stack overflow** with deep recursion on skewed trees
5. **Incorrect BST validation** (comparing only with parent)

### Edge Cases to Consider
1. **Empty tree:** root == null
2. **Single node:** Both children are null
3. **Skewed tree:** All nodes lean to one side
4. **Duplicate values:** How are they handled?
5. **Integer overflow:** In sum calculations

### Problem Recognition Patterns
**Tree problems often involve:**
- Recursive thinking (solve for subtrees)
- Multiple traversal strategies
- State passing down or up the tree
- Level-by-level processing (BFS)
- Path-related calculations

### Interview Problem-Solving Strategy
1. **Identify tree type:** Binary tree, BST, balanced, etc.
2. **Choose traversal method:** DFS (inorder/preorder/postorder) vs BFS
3. **Define base cases:** Null nodes, leaf nodes
4. **Determine information flow:** Top-down vs bottom-up
5. **Consider iterative alternatives:** For very deep trees

### Classic Problem Categories

**Tree construction:**
- Build tree from traversals
- Serialize/deserialize tree
- Clone tree with random pointers

**Tree validation:**
- Validate BST
- Check if balanced
- Symmetric tree

**Path problems:**
- Path sum variants
- Binary tree paths
- Diameter of tree

**Tree modification:**
- Flatten tree
- Invert tree
- Connect level order nodes

### Template for Tree Problems
```java
public ResultType treeAlgorithm(TreeNode root) {
    // Base case
    if (root == null) {
        return baseValue;
    }
    
    // Recursive calls
    ResultType leftResult = treeAlgorithm(root.left);
    ResultType rightResult = treeAlgorithm(root.right);
    
    // Process current node
    ResultType currentResult = processNode(root, leftResult, rightResult);
    
    return currentResult;
}
```

### Morris Traversal (Advanced)
**Concept:** Tree traversal with O(1) space using threading

```java
public List<Integer> inorderMorris(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    TreeNode current = root;
    
    while (current != null) {
        if (current.left == null) {
            result.add(current.val);
            current = current.right;
        } else {
            // Find inorder predecessor
            TreeNode predecessor = current.left;
            while (predecessor.right != null && predecessor.right != current) {
                predecessor = predecessor.right;
            }
            
            if (predecessor.right == null) {
                // Make thread
                predecessor.right = current;
                current = current.left;
            } else {
                // Remove thread
                predecessor.right = null;
                result.add(current.val);
                current = current.right;
            }
        }
    }
    
    return result;
}
```

**Applications:** When recursion depth is limited or stack space is constrained