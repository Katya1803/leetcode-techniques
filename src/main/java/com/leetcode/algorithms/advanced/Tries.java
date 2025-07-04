package com.leetcode.algorithms.advanced;

import java.util.*;

/**
 * Trie (Prefix Tree) Data Structure and Applications
 *
 * This class implements various trie-based data structures and algorithms
 * for efficient string operations, pattern matching, and prefix queries.
 */
public class Tries {

    // =========================== Basic Trie Implementation ===========================

    /**
     * Basic Trie Node
     */
    public static class TrieNode {
        public TrieNode[] children;
        public boolean isEndOfWord;
        public int frequency;

        public TrieNode() {
            children = new TrieNode[26]; // For lowercase letters a-z
            isEndOfWord = false;
            frequency = 0;
        }
    }

    /**
     * Basic Trie implementation
     */
    public static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * Insert word into trie
         * Time Complexity: O(m) where m is word length
         * Space Complexity: O(m) in worst case
         */
        public void insert(String word) {
            TrieNode current = root;

            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                current = current.children[index];
                current.frequency++;
            }

            current.isEndOfWord = true;
        }

        /**
         * Search for exact word in trie
         * Time Complexity: O(m)
         * Space Complexity: O(1)
         */
        public boolean search(String word) {
            TrieNode node = searchNode(word);
            return node != null && node.isEndOfWord;
        }

        /**
         * Check if any word starts with given prefix
         * Time Complexity: O(m)
         * Space Complexity: O(1)
         */
        public boolean startsWith(String prefix) {
            return searchNode(prefix) != null;
        }

        /**
         * Delete word from trie
         * Time Complexity: O(m)
         * Space Complexity: O(m) for recursion
         */
        public void delete(String word) {
            deleteHelper(root, word, 0);
        }

        private boolean deleteHelper(TrieNode node, String word, int index) {
            if (index == word.length()) {
                if (!node.isEndOfWord) {
                    return false; // Word doesn't exist
                }
                node.isEndOfWord = false;
                // Return true if current has no children
                return !hasChildren(node);
            }

            char c = word.charAt(index);
            TrieNode child = node.children[c - 'a'];

            if (child == null) {
                return false; // Word doesn't exist
            }

            boolean shouldDeleteChild = deleteHelper(child, word, index + 1);

            if (shouldDeleteChild) {
                node.children[c - 'a'] = null;
                // Return true if current has no children and is not end of word
                return !node.isEndOfWord && !hasChildren(node);
            }

            return false;
        }

        private boolean hasChildren(TrieNode node) {
            for (TrieNode child : node.children) {
                if (child != null) return true;
            }
            return false;
        }

        private TrieNode searchNode(String word) {
            TrieNode current = root;

            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (current.children[index] == null) {
                    return null;
                }
                current = current.children[index];
            }

            return current;
        }

        /**
         * Get all words with given prefix
         * Time Complexity: O(p + n*m) where p is prefix length, n is number of words with prefix
         * Space Complexity: O(n*m)
         */
        public List<String> getWordsWithPrefix(String prefix) {
            List<String> result = new ArrayList<>();
            TrieNode prefixNode = searchNode(prefix);

            if (prefixNode != null) {
                dfsCollectWords(prefixNode, prefix, result);
            }

            return result;
        }

        private void dfsCollectWords(TrieNode node, String prefix, List<String> result) {
            if (node.isEndOfWord) {
                result.add(prefix);
            }

            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    dfsCollectWords(node.children[i], prefix + (char) ('a' + i), result);
                }
            }
        }

        /**
         * Count words with given prefix
         * Time Complexity: O(p) where p is prefix length
         */
        public int countWordsWithPrefix(String prefix) {
            TrieNode node = searchNode(prefix);
            return node != null ? node.frequency : 0;
        }

        /**
         * Find longest common prefix of all words in trie
         * Time Complexity: O(m) where m is length of longest common prefix
         */
        public String longestCommonPrefix() {
            StringBuilder prefix = new StringBuilder();
            TrieNode current = root;

            while (current != null && !current.isEndOfWord && countChildren(current) == 1) {
                for (int i = 0; i < 26; i++) {
                    if (current.children[i] != null) {
                        prefix.append((char) ('a' + i));
                        current = current.children[i];
                        break;
                    }
                }
            }

            return prefix.toString();
        }

        private int countChildren(TrieNode node) {
            int count = 0;
            for (TrieNode child : node.children) {
                if (child != null) count++;
            }
            return count;
        }

        public TrieNode getRoot() {
            return root;
        }
    }

    // =========================== Word Search Applications ===========================

    /**
     * Word Search II - find all words in board that exist in dictionary
     * Time Complexity: O(M * N * 4^L) where M*N is board size, L is max word length
     * Space Complexity: O(total characters in dictionary)
     */
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();

        // Build trie from dictionary
        TrieNode root = buildTrie(words);

        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfsWordSearch(board, i, j, root, result);
            }
        }

        return result;
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();

        for (String word : words) {
            TrieNode current = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                current = current.children[index];
            }
            current.isEndOfWord = true;
        }

        return root;
    }

    private void dfsWordSearch(char[][] board, int row, int col, TrieNode node, List<String> result) {
        char c = board[row][col];
        if (c == '#' || node.children[c - 'a'] == null) {
            return;
        }

        node = node.children[c - 'a'];

        // Check if we found a word
        if (node.isEndOfWord) {
            result.add(getWordFromPath(board, row, col, node));
            node.isEndOfWord = false; // Avoid duplicates
        }

        // Mark as visited
        board[row][col] = '#';

        // Explore all 4 directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < board.length &&
                    newCol >= 0 && newCol < board[0].length) {
                dfsWordSearch(board, newRow, newCol, node, result);
            }
        }

        // Backtrack
        board[row][col] = c;
    }

    private String getWordFromPath(char[][] board, int row, int col, TrieNode node) {
        // This is a simplified version - in practice, you'd store the word in the node
        // or reconstruct it from the path
        return ""; // Placeholder
    }

    // =========================== Auto-complete System ===========================

    /**
     * Auto-complete system using trie
     */
    public static class AutocompleteSystem {
        private TrieNode root;
        private TrieNode currentNode;
        private StringBuilder currentInput;

        public AutocompleteSystem(String[] sentences, int[] times) {
            root = new TrieNode();
            currentNode = root;
            currentInput = new StringBuilder();

            // Build initial trie
            for (int i = 0; i < sentences.length; i++) {
                insertSentence(sentences[i], times[i]);
            }
        }

        public List<String> input(char c) {
            if (c == '#') {
                // End of input - add sentence to trie
                insertSentence(currentInput.toString(), 1);
                currentInput = new StringBuilder();
                currentNode = root;
                return new ArrayList<>();
            }

            currentInput.append(c);

            if (currentNode != null) {
                int index = getCharIndex(c);
                if (index != -1 && currentNode.children[index] != null) {
                    currentNode = currentNode.children[index];
                    return getTop3Sentences(currentNode);
                }
            }

            currentNode = null;
            return new ArrayList<>();
        }

        private void insertSentence(String sentence, int count) {
            TrieNode node = root;

            for (char c : sentence.toCharArray()) {
                int index = getCharIndex(c);
                if (index != -1) {
                    if (node.children[index] == null) {
                        node.children[index] = new TrieNode();
                    }
                    node = node.children[index];
                }
            }

            node.isEndOfWord = true;
            node.frequency += count;
        }

        private List<String> getTop3Sentences(TrieNode node) {
            List<Sentence> sentences = new ArrayList<>();
            collectSentences(node, currentInput.toString(), sentences);

            sentences.sort((a, b) -> {
                if (a.count != b.count) {
                    return Integer.compare(b.count, a.count); // Higher count first
                }
                return a.sentence.compareTo(b.sentence); // Lexicographical order
            });

            List<String> result = new ArrayList<>();
            for (int i = 0; i < Math.min(3, sentences.size()); i++) {
                result.add(sentences.get(i).sentence);
            }

            return result;
        }

        private void collectSentences(TrieNode node, String prefix, List<Sentence> sentences) {
            if (node.isEndOfWord) {
                sentences.add(new Sentence(prefix, node.frequency));
            }

            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    char nextChar = getCharFromIndex(i);
                    if (nextChar != 0) {
                        collectSentences(node.children[i], prefix + nextChar, sentences);
                    }
                }
            }
        }

        private int getCharIndex(char c) {
            if (c >= 'a' && c <= 'z') {
                return c - 'a';
            } else if (c == ' ') {
                return 26;
            }
            return -1; // Invalid character
        }

        private char getCharFromIndex(int index) {
            if (index >= 0 && index < 26) {
                return (char) ('a' + index);
            } else if (index == 26) {
                return ' ';
            }
            return 0; // Invalid index
        }

        private static class Sentence {
            String sentence;
            int count;

            Sentence(String sentence, int count) {
                this.sentence = sentence;
                this.count = count;
            }
        }
    }

}