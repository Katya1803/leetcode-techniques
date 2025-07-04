package com.leetcode.algorithms.trees;

import com.leetcode.utils.TreeNode;
import java.util.*;

/**
 * Binary Tree Traversal Algorithms
 * Covers all major traversal patterns: DFS (preorder, inorder, postorder) and BFS
 *
 * Time Complexity: O(n) for all traversals where n = number of nodes
 * Space Complexity: O(h) for recursive DFS, O(w) for BFS where h = height, w = max width
 */
public class TreeTraversal {

    // ==================== RECURSIVE DFS TRAVERSALS ====================

    /**
     * Preorder Traversal (Root -> Left -> Right)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;

        result.add(node.val);           // Process root
        preorderHelper(node.left, result);   // Traverse left subtree
        preorderHelper(node.right, result);  // Traverse right subtree
    }

    /**
     * Inorder Traversal (Left -> Root -> Right)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;

        inorderHelper(node.left, result);    // Traverse left subtree
        result.add(node.val);                // Process root
        inorderHelper(node.right, result);   // Traverse right subtree
    }

    /**
     * Postorder Traversal (Left -> Right -> Root)
     * Time: O(n), Space: O(h)
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;

        postorderHelper(node.left, result);  // Traverse left subtree
        postorderHelper(node.right, result); // Traverse right subtree
        result.add(node.val);                // Process root
    }

    // ==================== ITERATIVE DFS TRAVERSALS ====================

    /**
     * Iterative Preorder Traversal using Stack
     * Time: O(n), Space: O(h)
     */
    public List<Integer> preorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);

            // Push right first, then left (stack is LIFO)
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }

        return result;
    }

    /**
     * Iterative Inorder Traversal using Stack
     * Time: O(n), Space: O(h)
     */
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

            // Process node
            current = stack.pop();
            result.add(current.val);

            // Move to right subtree
            current = current.right;
        }

        return result;
    }

    /**
     * Iterative Postorder Traversal using Two Stacks
     * Time: O(n), Space: O(h)
     */
    public List<Integer> postorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();

        stack1.push(root);

        // First stack for traversal, second for result order
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);

            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }

        // Pop from second stack gives postorder
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }

        return result;
    }

    // ==================== BFS TRAVERSALS ====================

    /**
     * Level Order Traversal (BFS)
     * Time: O(n), Space: O(w) where w is maximum width
     */
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

    /**
     * Zigzag Level Order Traversal
     * Time: O(n), Space: O(w)
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    currentLevel.add(node.val);
                } else {
                    currentLevel.add(0, node.val); // Add to front for reverse order
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);
            leftToRight = !leftToRight;
        }

        return result;
    }

    /**
     * Right Side View of Binary Tree
     * Time: O(n), Space: O(w)
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                // Add the rightmost node of each level
                if (i == levelSize - 1) {
                    result.add(node.val);
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return result;
    }

    // ==================== TREE PROPERTIES ====================

    /**
     * Maximum Depth of Binary Tree
     * Time: O(n), Space: O(h)
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * Minimum Depth of Binary Tree
     * Time: O(n), Space: O(h)
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;

        int minLeft = root.left != null ? minDepth(root.left) : Integer.MAX_VALUE;
        int minRight = root.right != null ? minDepth(root.right) : Integer.MAX_VALUE;

        return 1 + Math.min(minLeft, minRight);
    }

    /**
     * Check if tree is balanced
     * Time: O(n), Space: O(h)
     */
    public boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private int checkBalance(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = checkBalance(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = checkBalance(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Path Sum - check if root-to-leaf path with target sum exists
     * Time: O(n), Space: O(h)
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        int remainingSum = targetSum - root.val;
        return hasPathSum(root.left, remainingSum) || hasPathSum(root.right, remainingSum);
    }

    /**
     * All Root-to-Leaf Paths
     * Time: O(n), Space: O(n*h)
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        findPaths(root, "", result);
        return result;
    }

    private void findPaths(TreeNode node, String path, List<String> result) {
        if (node == null) return;

        path += node.val;

        if (node.left == null && node.right == null) {
            result.add(path);
            return;
        }

        path += "->";
        findPaths(node.left, path, result);
        findPaths(node.right, path, result);
    }
}