package com.leetcode.algorithms.trees;

import com.leetcode.utils.TreeNode;
import java.util.*;

/**
 * Binary Search Tree Operations
 * BST Property: For every node, left subtree < node < right subtree
 *
 * Time Complexity: O(h) for most operations where h = height
 * Best Case: O(log n) for balanced BST
 * Worst Case: O(n) for skewed BST
 */
public class BSTOperations {

    // ==================== BASIC BST OPERATIONS ====================

    /**
     * Search for a value in BST
     * Time: O(h), Space: O(1) iterative / O(h) recursive
     */
    public TreeNode search(TreeNode root, int val) {
        while (root != null && root.val != val) {
            root = (val < root.val) ? root.left : root.right;
        }
        return root;
    }

    /**
     * Recursive search in BST
     * Time: O(h), Space: O(h)
     */
    public TreeNode searchRecursive(TreeNode root, int val) {
        if (root == null || root.val == val) {
            return root;
        }

        if (val < root.val) {
            return searchRecursive(root.left, val);
        } else {
            return searchRecursive(root.right, val);
        }
    }

    /**
     * Insert a value into BST
     * Time: O(h), Space: O(h)
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }

    /**
     * Iterative insert into BST
     * Time: O(h), Space: O(1)
     */
    public TreeNode insertIterative(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        TreeNode current = root;
        while (true) {
            if (val < current.val) {
                if (current.left == null) {
                    current.left = new TreeNode(val);
                    break;
                } else {
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    current.right = new TreeNode(val);
                    break;
                } else {
                    current = current.right;
                }
            }
        }

        return root;
    }

    /**
     * Delete a node from BST
     * Time: O(h), Space: O(h)
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Node to be deleted found

            // Case 1: Node has no children (leaf node)
            if (root.left == null && root.right == null) {
                return null;
            }

            // Case 2: Node has one child
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }

            // Case 3: Node has two children
            // Find inorder successor (smallest node in right subtree)
            TreeNode successor = findMin(root.right);
            root.val = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }

        return root;
    }

    /**
     * Find minimum value node in BST
     * Time: O(h), Space: O(1)
     */
    public TreeNode findMin(TreeNode root) {
        while (root != null && root.left != null) {
            root = root.left;
        }
        return root;
    }

    /**
     * Find maximum value node in BST
     * Time: O(h), Space: O(1)
     */
    public TreeNode findMax(TreeNode root) {
        while (root != null && root.right != null) {
            root = root.right;
        }
        return root;
    }

    // ==================== BST VALIDATION ====================

    /**
     * Validate if a binary tree is a valid BST
     * Time: O(n), Space: O(h)
     */
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long minVal, long maxVal) {
        if (node == null) return true;

        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }

        return validate(node.left, minVal, node.val) &&
                validate(node.right, node.val, maxVal);
    }

    /**
     * Alternative BST validation using inorder traversal
     * Time: O(n), Space: O(h)
     */
    public boolean isValidBSTInorder(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);

        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) {
                return false;
            }
        }

        return true;
    }

    private void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }

    // ==================== BST CONSTRUCTION ====================

    /**
     * Convert sorted array to balanced BST
     * Time: O(n), Space: O(log n)
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return arrayToBSTHelper(nums, 0, nums.length - 1);
    }

    private TreeNode arrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = arrayToBSTHelper(nums, left, mid - 1);
        root.right = arrayToBSTHelper(nums, mid + 1, right);

        return root;
    }

    /**
     * Convert BST to sorted array
     * Time: O(n), Space: O(n)
     */
    public int[] bstToSortedArray(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result.stream().mapToInt(i -> i).toArray();
    }

    // ==================== BST QUERIES ====================

    /**
     * Find k-th smallest element in BST
     * Time: O(h + k), Space: O(h)
     */
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            k--;
            if (k == 0) {
                return current.val;
            }
            current = current.right;
        }

        return -1; // Should not reach here if k is valid
    }

    /**
     * Find k-th largest element in BST
     * Time: O(h + k), Space: O(h)
     */
    public int kthLargest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.right; // Go right for largest first
            }

            current = stack.pop();
            k--;
            if (k == 0) {
                return current.val;
            }
            current = current.left;
        }

        return -1;
    }

    /**
     * Find closest value to target in BST
     * Time: O(h), Space: O(1)
     */
    public int closestValue(TreeNode root, double target) {
        int closest = root.val;

        while (root != null) {
            if (Math.abs(root.val - target) < Math.abs(closest - target)) {
                closest = root.val;
            }

            if (target < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }

        return closest;
    }

    /**
     * Find range sum in BST (values between low and high inclusive)
     * Time: O(n), Space: O(h)
     */
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) return 0;

        int sum = 0;

        // Include current node if in range
        if (root.val >= low && root.val <= high) {
            sum += root.val;
        }

        // Recursively search left and right
        if (root.val > low) {
            sum += rangeSumBST(root.left, low, high);
        }
        if (root.val < high) {
            sum += rangeSumBST(root.right, low, high);
        }

        return sum;
    }

    // ==================== BST ADVANCED OPERATIONS ====================

    /**
     * Lowest Common Ancestor in BST
     * Time: O(h), Space: O(1)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                return root; // Found LCA
            }
        }
        return null;
    }

    /**
     * Convert BST to Greater Sum Tree
     * Each node's value becomes sum of all values greater than it
     * Time: O(n), Space: O(h)
     */
    public TreeNode bstToGst(TreeNode root) {
        int[] sum = {0}; // Use array to pass by reference
        reverseInorderTraversal(root, sum);
        return root;
    }

    private void reverseInorderTraversal(TreeNode node, int[] sum) {
        if (node == null) return;

        reverseInorderTraversal(node.right, sum); // Right first
        sum[0] += node.val;
        node.val = sum[0];
        reverseInorderTraversal(node.left, sum);  // Then left
    }

    /**
     * Balance a BST
     * Time: O(n), Space: O(n)
     */
    public TreeNode balanceBST(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);

        int[] arr = inorder.stream().mapToInt(i -> i).toArray();
        return sortedArrayToBST(arr);
    }

    /**
     * Trim BST to range [low, high]
     * Time: O(n), Space: O(h)
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;

        if (root.val < low) {
            return trimBST(root.right, low, high);
        }
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }

        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }

    // ==================== BST UTILITY METHODS ====================

    /**
     * Get BST height
     * Time: O(n), Space: O(h)
     */
    public int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /**
     * Count total nodes in BST
     * Time: O(n), Space: O(h)
     */
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * Check if BST is balanced (height difference <= 1)
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
}