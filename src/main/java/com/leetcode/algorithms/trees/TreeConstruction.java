package com.leetcode.algorithms.trees;

import com.leetcode.utils.TreeNode;
import java.util.*;

/**
 * Binary Tree Construction Algorithms
 * Build trees from various inputs: arrays, traversals, special conditions
 *
 * Construction patterns:
 * 1. From traversal sequences
 * 2. From special arrays
 * 3. Tree modifications and cloning
 * 4. Serialization and deserialization
 */
public class TreeConstruction {

    // ==================== CONSTRUCTION FROM TRAVERSALS ====================

    /**
     * Build tree from preorder and inorder traversal
     * Time: O(n), Space: O(n)
     */
    public TreeNode buildTreeFromPreorderInorder(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildTreeHelper(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, inorderMap);
    }

    private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd,
                                     int[] inorder, int inStart, int inEnd,
                                     Map<Integer, Integer> inorderMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // Root is first element in preorder
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // Find root position in inorder
        int rootIndex = inorderMap.get(rootVal);
        int leftSize = rootIndex - inStart;

        // Build left and right subtrees
        root.left = buildTreeHelper(preorder, preStart + 1, preStart + leftSize,
                inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildTreeHelper(preorder, preStart + leftSize + 1, preEnd,
                inorder, rootIndex + 1, inEnd, inorderMap);

        return root;
    }

    /**
     * Build tree from postorder and inorder traversal
     * Time: O(n), Space: O(n)
     */
    public TreeNode buildTreeFromPostorderInorder(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildPostorderHelper(postorder, 0, postorder.length - 1,
                inorder, 0, inorder.length - 1, inorderMap);
    }

    private TreeNode buildPostorderHelper(int[] postorder, int postStart, int postEnd,
                                          int[] inorder, int inStart, int inEnd,
                                          Map<Integer, Integer> inorderMap) {
        if (postStart > postEnd || inStart > inEnd) {
            return null;
        }

        // Root is last element in postorder
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        // Find root position in inorder
        int rootIndex = inorderMap.get(rootVal);
        int leftSize = rootIndex - inStart;

        // Build left and right subtrees
        root.left = buildPostorderHelper(postorder, postStart, postStart + leftSize - 1,
                inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildPostorderHelper(postorder, postStart + leftSize, postEnd - 1,
                inorder, rootIndex + 1, inEnd, inorderMap);

        return root;
    }

    /**
     * Build tree from preorder traversal (with null markers)
     * Time: O(n), Space: O(n)
     */
    public TreeNode buildTreeFromPreorder(String[] preorder) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(preorder));
        return buildFromPreorderHelper(queue);
    }

    private TreeNode buildFromPreorderHelper(Queue<String> queue) {
        String val = queue.poll();
        if (val.equals("null")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = buildFromPreorderHelper(queue);
        root.right = buildFromPreorderHelper(queue);

        return root;
    }

    // ==================== CONSTRUCTION FROM ARRAYS ====================

    /**
     * Build complete binary tree from array (level-order)
     * Time: O(n), Space: O(n)
     */
    public TreeNode buildTreeFromArray(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode current = queue.poll();

            // Left child
            if (i < arr.length && arr[i] != null) {
                current.left = new TreeNode(arr[i]);
                queue.offer(current.left);
            }
            i++;

            // Right child
            if (i < arr.length && arr[i] != null) {
                current.right = new TreeNode(arr[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    /**
     * Convert tree to array representation (level-order)
     * Time: O(n), Space: O(n)
     */
    public List<Integer> treeToArray(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            if (current != null) {
                result.add(current.val);
                queue.offer(current.left);
                queue.offer(current.right);
            } else {
                result.add(null);
            }
        }

        // Remove trailing nulls
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    /**
     * Build Maximum Binary Tree from array
     * The root is the maximum element, left subtree from left part, right from right part
     * Time: O(n²) worst case, O(n log n) average, Space: O(n)
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return buildMaxTreeHelper(nums, 0, nums.length - 1);
    }

    private TreeNode buildMaxTreeHelper(int[] nums, int start, int end) {
        if (start > end) return null;

        // Find maximum element and its index
        int maxIndex = start;
        for (int i = start + 1; i <= end; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = buildMaxTreeHelper(nums, start, maxIndex - 1);
        root.right = buildMaxTreeHelper(nums, maxIndex + 1, end);

        return root;
    }

    // ==================== TREE MODIFICATION ====================

    /**
     * Clone/Copy a binary tree
     * Time: O(n), Space: O(h)
     */
    public TreeNode cloneTree(TreeNode root) {
        if (root == null) return null;

        TreeNode cloned = new TreeNode(root.val);
        cloned.left = cloneTree(root.left);
        cloned.right = cloneTree(root.right);

        return cloned;
    }

    /**
     * Mirror/Invert a binary tree
     * Time: O(n), Space: O(h)
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        // Swap left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // Recursively invert subtrees
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    /**
     * Flatten binary tree to linked list (preorder)
     * Time: O(n), Space: O(1)
     */
    public void flatten(TreeNode root) {
        if (root == null) return;

        TreeNode current = root;
        while (current != null) {
            if (current.left != null) {
                // Find rightmost node in left subtree
                TreeNode rightmost = current.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }

                // Connect rightmost to current's right
                rightmost.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }

    /**
     * Connect nodes at same level (Perfect Binary Tree)
     * Time: O(n), Space: O(1)
     */
    public TreeNode connect(TreeNode root) {
        if (root == null) return root;

        TreeNode leftmost = root;

        while (leftmost.left != null) {
            TreeNode head = leftmost;

            while (head != null) {
                // Connect children of current node
                head.left.next = head.right;

                // Connect to next node's left child
                if (head.next != null) {
                    head.right.next = head.next.left;
                }

                head = head.next;
            }

            leftmost = leftmost.left;
        }

        return root;
    }

    // ==================== SERIALIZATION ====================

    /**
     * Serialize tree to string (preorder with nulls)
     * Time: O(n), Space: O(n)
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }

        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    /**
     * Deserialize string to tree
     * Time: O(n), Space: O(n)
     */
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(queue);
    }

    private TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        if (val.equals("null")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = deserializeHelper(queue);
        root.right = deserializeHelper(queue);

        return root;
    }

    // ==================== SPECIAL CONSTRUCTIONS ====================

    /**
     * Build tree with minimum height from sorted array
     * Time: O(n), Space: O(log n)
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayHelper(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayHelper(int[] nums, int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayHelper(nums, left, mid - 1);
        root.right = sortedArrayHelper(nums, mid + 1, right);

        return root;
    }

    /**
     * Convert sorted linked list to balanced BST
     * Time: O(n), Space: O(log n)
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        // Find middle using slow-fast pointers
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Disconnect left part
        if (prev != null) {
            prev.next = null;
        }

        TreeNode root = new TreeNode(slow.val);
        root.left = sortedListToBST(head == slow ? null : head);
        root.right = sortedListToBST(slow.next);

        return root;
    }

    /**
     * Build tree from parent array representation
     * parent[i] = parent of node i, -1 for root
     * Time: O(n), Space: O(n)
     */
    public TreeNode buildTreeFromParentArray(int[] parent) {
        if (parent.length == 0) return null;

        TreeNode[] nodes = new TreeNode[parent.length];
        TreeNode root = null;

        // Create all nodes
        for (int i = 0; i < parent.length; i++) {
            nodes[i] = new TreeNode(i);
        }

        // Build tree structure
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == -1) {
                root = nodes[i];
            } else {
                TreeNode parentNode = nodes[parent[i]];
                if (parentNode.left == null) {
                    parentNode.left = nodes[i];
                } else {
                    parentNode.right = nodes[i];
                }
            }
        }

        return root;
    }

    /**
     * All possible full binary trees with n nodes
     * Time: Catalan number O(4^n / n^(3/2)), Space: O(4^n / n^(3/2))
     */
    public List<TreeNode> allPossibleFBT(int n) {
        List<TreeNode> result = new ArrayList<>();
        if (n % 2 == 0) return result; // Full binary trees need odd number of nodes

        if (n == 1) {
            result.add(new TreeNode(0));
            return result;
        }

        // Try all possible left subtree sizes
        for (int leftNodes = 1; leftNodes < n; leftNodes += 2) {
            int rightNodes = n - 1 - leftNodes;

            List<TreeNode> leftTrees = allPossibleFBT(leftNodes);
            List<TreeNode> rightTrees = allPossibleFBT(rightNodes);

            // Combine all possible left and right subtrees
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(0);
                    root.left = left;
                    root.right = right;
                    result.add(root);
                }
            }
        }

        return result;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Check if two trees are identical
     * Time: O(n), Space: O(h)
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * Check if tree is symmetric
     * Time: O(n), Space: O(h)
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val != right.val) return false;

        return isSymmetricHelper(left.left, right.right) &&
                isSymmetricHelper(left.right, right.left);
    }

    // Helper class for linked list (referenced in sortedListToBST)
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}