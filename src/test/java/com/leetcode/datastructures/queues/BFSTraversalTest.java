package com.leetcode.datastructures.queues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BFSTraversalTest {

    private BFSTraversal bfsTraversal;

    @BeforeEach
    void setUp() {
        bfsTraversal = new BFSTraversal();
    }

    // Helper method to create binary tree for testing
    private BFSTraversal.TreeNode createStandardTree() {
        // Tree structure:    3
        //                   / \
        //                  9   20
        //                     /  \
        //                    15   7
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(3);
        root.left = new BFSTraversal.TreeNode(9);
        root.right = new BFSTraversal.TreeNode(20);
        root.right.left = new BFSTraversal.TreeNode(15);
        root.right.right = new BFSTraversal.TreeNode(7);
        return root;
    }

    private BFSTraversal.TreeNode createComplexTree() {
        // Tree structure:        1
        //                       / \
        //                      2   3
        //                     / \   \
        //                    4   5   6
        //                   /
        //                  7
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);
        root.left = new BFSTraversal.TreeNode(2);
        root.right = new BFSTraversal.TreeNode(3);
        root.left.left = new BFSTraversal.TreeNode(4);
        root.left.right = new BFSTraversal.TreeNode(5);
        root.right.right = new BFSTraversal.TreeNode(6);
        root.left.left.left = new BFSTraversal.TreeNode(7);
        return root;
    }

    // ==================== LEVEL ORDER TRAVERSAL TESTS ====================

    @Test
    @DisplayName("Level Order Traversal - Standard Tree")
    void testLevelOrderStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        List<List<Integer>> result = bfsTraversal.levelOrder(root);

        // Then
        assertEquals(3, result.size());
        assertEquals(Arrays.asList(3), result.get(0));
        assertEquals(Arrays.asList(9, 20), result.get(1));
        assertEquals(Arrays.asList(15, 7), result.get(2));
    }

    @Test
    @DisplayName("Level Order Traversal - Empty Tree")
    void testLevelOrderEmptyTree() {
        // When
        List<List<Integer>> result = bfsTraversal.levelOrder(null);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Level Order Traversal - Single Node")
    void testLevelOrderSingleNode() {
        // Given
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(42);

        // When
        List<List<Integer>> result = bfsTraversal.levelOrder(root);

        // Then
        assertEquals(1, result.size());
        assertEquals(Arrays.asList(42), result.get(0));
    }

    @Test
    @DisplayName("Level Order Bottom - Standard Tree")
    void testLevelOrderBottomStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        List<List<Integer>> result = bfsTraversal.levelOrderBottom(root);

        // Then
        assertEquals(3, result.size());
        assertEquals(Arrays.asList(15, 7), result.get(0));
        assertEquals(Arrays.asList(9, 20), result.get(1));
        assertEquals(Arrays.asList(3), result.get(2));
    }

    @Test
    @DisplayName("Zigzag Level Order - Standard Tree")
    void testZigzagLevelOrderStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        List<List<Integer>> result = bfsTraversal.zigzagLevelOrder(root);

        // Then
        assertEquals(3, result.size());
        assertEquals(Arrays.asList(3), result.get(0));       // Left to right
        assertEquals(Arrays.asList(20, 9), result.get(1));   // Right to left
        assertEquals(Arrays.asList(15, 7), result.get(2));   // Left to right
    }

    @Test
    @DisplayName("Zigzag Level Order - Complex Tree")
    void testZigzagLevelOrderComplexTree() {
        // Given
        BFSTraversal.TreeNode root = createComplexTree();

        // When
        List<List<Integer>> result = bfsTraversal.zigzagLevelOrder(root);

        // Then
        assertEquals(4, result.size());
        assertEquals(Arrays.asList(1), result.get(0));       // Left to right
        assertEquals(Arrays.asList(3, 2), result.get(1));    // Right to left
        assertEquals(Arrays.asList(4, 5, 6), result.get(2)); // Left to right
        assertEquals(Arrays.asList(7), result.get(3));       // Right to left
    }

    // ==================== TREE VIEW TESTS ====================

    @Test
    @DisplayName("Right Side View - Standard Tree")
    void testRightSideViewStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        List<Integer> result = bfsTraversal.rightSideView(root);

        // Then
        assertEquals(Arrays.asList(3, 20, 7), result);
    }

    @Test
    @DisplayName("Right Side View - Left Skewed Tree")
    void testRightSideViewLeftSkewed() {
        // Given - Tree: 1 -> 2 -> 3 (all left children)
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);
        root.left = new BFSTraversal.TreeNode(2);
        root.left.left = new BFSTraversal.TreeNode(3);

        // When
        List<Integer> result = bfsTraversal.rightSideView(root);

        // Then
        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    @DisplayName("Right Side View - Empty Tree")
    void testRightSideViewEmptyTree() {
        // When
        List<Integer> result = bfsTraversal.rightSideView(null);

        // Then
        assertTrue(result.isEmpty());
    }

    // ==================== TREE STATISTICS TESTS ====================

    @Test
    @DisplayName("Average of Levels - Standard Tree")
    void testAverageOfLevelsStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        List<Double> result = bfsTraversal.averageOfLevels(root);

        // Then
        assertEquals(3, result.size());
        assertEquals(3.0, result.get(0), 0.001);
        assertEquals(14.5, result.get(1), 0.001); // (9 + 20) / 2
        assertEquals(11.0, result.get(2), 0.001); // (15 + 7) / 2
    }

    @Test
    @DisplayName("Average of Levels - Single Values")
    void testAverageOfLevelsSingleValues() {
        // Given - Tree with single node per level
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);
        root.left = new BFSTraversal.TreeNode(2);
        root.left.left = new BFSTraversal.TreeNode(3);

        // When
        List<Double> result = bfsTraversal.averageOfLevels(root);

        // Then
        assertEquals(3, result.size());
        assertEquals(1.0, result.get(0), 0.001);
        assertEquals(2.0, result.get(1), 0.001);
        assertEquals(3.0, result.get(2), 0.001);
    }

    @Test
    @DisplayName("Minimum Depth - Standard Tree")
    void testMinDepthStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        int result = bfsTraversal.minDepth(root);

        // Then
        assertEquals(2, result); // Path to leaf node 9
    }

    @Test
    @DisplayName("Minimum Depth - Balanced Tree")
    void testMinDepthBalancedTree() {
        // Given - Perfectly balanced tree
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);
        root.left = new BFSTraversal.TreeNode(2);
        root.right = new BFSTraversal.TreeNode(3);
        root.left.left = new BFSTraversal.TreeNode(4);
        root.left.right = new BFSTraversal.TreeNode(5);
        root.right.left = new BFSTraversal.TreeNode(6);
        root.right.right = new BFSTraversal.TreeNode(7);

        // When
        int result = bfsTraversal.minDepth(root);

        // Then
        assertEquals(3, result); // Any path to leaf
    }

    @Test
    @DisplayName("Maximum Depth - Standard Tree")
    void testMaxDepthStandardTree() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When
        int result = bfsTraversal.maxDepth(root);

        // Then
        assertEquals(3, result);
    }

    @Test
    @DisplayName("Depth Tests - Edge Cases")
    void testDepthEdgeCases() {
        // Null tree
        assertEquals(0, bfsTraversal.minDepth(null));
        assertEquals(0, bfsTraversal.maxDepth(null));

        // Single node
        BFSTraversal.TreeNode single = new BFSTraversal.TreeNode(1);
        assertEquals(1, bfsTraversal.minDepth(single));
        assertEquals(1, bfsTraversal.maxDepth(single));
    }

    // ==================== ROTTING ORANGES TESTS ====================

    @Test
    @DisplayName("Rotting Oranges - Standard Case")
    void testOrangesRottingStandardCase() {
        // Given
        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };

        // When
        int result = bfsTraversal.orangesRotting(grid);

        // Then
        assertEquals(4, result);
    }

    @Test
    @DisplayName("Rotting Oranges - Impossible Case")
    void testOrangesRottingImpossible() {
        // Given - Fresh orange isolated
        int[][] grid = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        };

        // When
        int result = bfsTraversal.orangesRotting(grid);

        // Then
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Rotting Oranges - No Fresh Oranges")
    void testOrangesRottingNoFresh() {
        // Given
        int[][] grid = {
                {2, 2, 2},
                {2, 2, 2},
                {2, 2, 2}
        };

        // When
        int result = bfsTraversal.orangesRotting(grid);

        // Then
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Rotting Oranges - All Fresh No Rotten")
    void testOrangesRottingAllFreshNoRotten() {
        // Given
        int[][] grid = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        // When
        int result = bfsTraversal.orangesRotting(grid);

        // Then
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Rotting Oranges - Single Cell Cases")
    void testOrangesRottingSingleCell() {
        assertEquals(0, bfsTraversal.orangesRotting(new int[][]{{0}})); // Empty
        assertEquals(0, bfsTraversal.orangesRotting(new int[][]{{2}})); // Rotten
        assertEquals(-1, bfsTraversal.orangesRotting(new int[][]{{1}})); // Fresh isolated
    }

    // ==================== WORD LADDER TESTS ====================

    @Test
    @DisplayName("Word Ladder - Standard Case")
    void testWordLadderStandardCase() {
        // Given
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

        // When
        int result = bfsTraversal.ladderLength(beginWord, endWord, wordList);

        // Then
        assertEquals(5, result); // hit -> hot -> dot -> dog -> cog
    }

    @Test
    @DisplayName("Word Ladder - No Path Exists")
    void testWordLadderNoPath() {
        // Given
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log");

        // When
        int result = bfsTraversal.ladderLength(beginWord, endWord, wordList);

        // Then
        assertEquals(0, result); // endWord not in wordList
    }

    @Test
    @DisplayName("Word Ladder - Direct Transformation")
    void testWordLadderDirectTransformation() {
        // Given
        String beginWord = "hit";
        String endWord = "hot";
        List<String> wordList = Arrays.asList("hot");

        // When
        int result = bfsTraversal.ladderLength(beginWord, endWord, wordList);

        // Then
        assertEquals(2, result); // hit -> hot
    }

    @Test
    @DisplayName("Word Ladder - Same Begin and End")
    void testWordLadderSameWord() {
        // Given
        String beginWord = "hit";
        String endWord = "hit";
        List<String> wordList = Arrays.asList("hit");

        // When
        int result = bfsTraversal.ladderLength(beginWord, endWord, wordList);

        // Then
        assertEquals(1, result);
    }

    // ==================== OPEN LOCK TESTS ====================

    @ParameterizedTest
    @MethodSource("provideOpenLockTestCases")
    @DisplayName("Open Lock - Multiple Scenarios")
    void testOpenLockMultipleScenarios(String[] deadends, String target, int expected) {
        int result = bfsTraversal.openLock(deadends, target);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideOpenLockTestCases() {
        return Stream.of(
                Arguments.of(new String[]{"0201", "0101", "0102", "1212", "2002"}, "0202", 6),
                Arguments.of(new String[]{"8888"}, "0009", 1),
                Arguments.of(new String[]{"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"}, "8888", -1),
                Arguments.of(new String[]{"0000"}, "8888", -1),
                Arguments.of(new String[]{}, "0001", 1),
                Arguments.of(new String[]{"0001"}, "0001", -1)
        );
    }

    // ==================== MATRIX UPDATE TESTS ====================

    @Test
    @DisplayName("01 Matrix - Simple Case")
    void testUpdateMatrixSimpleCase() {
        // Given
        int[][] mat = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        int[][] expected = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        // When
        int[][] result = bfsTraversal.updateMatrix(mat);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("01 Matrix - Complex Case")
    void testUpdateMatrixComplexCase() {
        // Given
        int[][] mat = {
                {0, 0, 0},
                {0, 1, 0},
                {1, 1, 1}
        };

        int[][] expected = {
                {0, 0, 0},
                {0, 1, 0},
                {1, 2, 1}
        };

        // When
        int[][] result = bfsTraversal.updateMatrix(mat);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("01 Matrix - All Zeros")
    void testUpdateMatrixAllZeros() {
        // Given
        int[][] mat = {
                {0, 0},
                {0, 0}
        };

        int[][] expected = {
                {0, 0},
                {0, 0}
        };

        // When
        int[][] result = bfsTraversal.updateMatrix(mat);

        // Then
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("01 Matrix - Single Cell")
    void testUpdateMatrixSingleCell() {
        // Given
        int[][] mat = {{0}};
        int[][] expected = {{0}};

        // When
        int[][] result = bfsTraversal.updateMatrix(mat);

        // Then
        assertArrayEquals(expected, result);
    }

    // ==================== WALLS AND GATES TESTS ====================

    @Test
    @DisplayName("Walls and Gates - Standard Case")
    void testWallsAndGatesStandardCase() {
        // Given
        int INF = Integer.MAX_VALUE;
        int[][] rooms = {
                {INF, -1, 0, INF},
                {INF, INF, INF, -1},
                {INF, -1, INF, -1},
                {0, -1, INF, INF}
        };

        int[][] expected = {
                {3, -1, 0, 1},
                {2, 2, 1, -1},
                {1, -1, 2, -1},
                {0, -1, 3, 4}
        };

        // When
        bfsTraversal.wallsAndGates(rooms);

        // Then
        assertArrayEquals(expected, rooms);
    }

    @Test
    @DisplayName("Walls and Gates - No Gates")
    void testWallsAndGatesNoGates() {
        // Given
        int INF = Integer.MAX_VALUE;
        int[][] rooms = {
                {INF, -1, INF},
                {INF, INF, -1},
                {INF, -1, INF}
        };

        int[][] expected = {
                {INF, -1, INF},
                {INF, INF, -1},
                {INF, -1, INF}
        };

        // When
        bfsTraversal.wallsAndGates(rooms);

        // Then
        assertArrayEquals(expected, rooms);
    }

    @Test
    @DisplayName("Walls and Gates - Edge Cases")
    void testWallsAndGatesEdgeCases() {
        // Empty grid
        assertDoesNotThrow(() -> bfsTraversal.wallsAndGates(new int[0][0]));
        assertDoesNotThrow(() -> bfsTraversal.wallsAndGates(null));

        // Single gate
        int[][] singleGate = {{0}};
        bfsTraversal.wallsAndGates(singleGate);
        assertEquals(0, singleGate[0][0]);
    }

    // ==================== PACIFIC ATLANTIC TESTS ====================

    @Test
    @DisplayName("Pacific Atlantic - Standard Case")
    void testPacificAtlanticStandardCase() {
        // Given
        int[][] heights = {
                {1, 2, 2, 3, 5},
                {3, 2, 3, 4, 4},
                {2, 4, 5, 3, 1},
                {6, 7, 1, 4, 5},
                {5, 1, 1, 2, 4}
        };

        // When
        List<List<Integer>> result = bfsTraversal.pacificAtlantic(heights);

        // Then
        assertFalse(result.isEmpty());
        assertTrue(result.contains(Arrays.asList(0, 4)));
        assertTrue(result.contains(Arrays.asList(1, 3)));
        assertTrue(result.contains(Arrays.asList(1, 4)));
        assertTrue(result.contains(Arrays.asList(2, 2)));
        assertTrue(result.contains(Arrays.asList(3, 0)));
        assertTrue(result.contains(Arrays.asList(3, 1)));
        assertTrue(result.contains(Arrays.asList(4, 0)));
    }

    @Test
    @DisplayName("Pacific Atlantic - Single Cell")
    void testPacificAtlanticSingleCell() {
        // Given
        int[][] heights = {{1}};

        // When
        List<List<Integer>> result = bfsTraversal.pacificAtlantic(heights);

        // Then
        assertEquals(1, result.size());
        assertTrue(result.contains(Arrays.asList(0, 0)));
    }

    @Test
    @DisplayName("Pacific Atlantic - Empty Grid")
    void testPacificAtlanticEmptyGrid() {
        // Given
        int[][] heights = {};

        // When
        List<List<Integer>> result = bfsTraversal.pacificAtlantic(heights);

        // Then
        assertTrue(result.isEmpty());
    }

    // ==================== SHORTEST BRIDGE TESTS ====================

    @Test
    @DisplayName("Shortest Bridge - Adjacent Islands")
    void testShortestBridgeAdjacent() {
        // Given
        int[][] grid = {
                {0, 1},
                {1, 0}
        };

        // When
        int result = bfsTraversal.shortestBridge(grid);

        // Then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Shortest Bridge - Separated Islands")
    void testShortestBridgeSeparated() {
        // Given
        int[][] grid = {
                {0, 1, 0},
                {0, 0, 0},
                {0, 0, 1}
        };

        // When
        int result = bfsTraversal.shortestBridge(grid);

        // Then
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Shortest Bridge - Complex Islands")
    void testShortestBridgeComplexIslands() {
        // Given
        int[][] grid = {
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };

        // When
        int result = bfsTraversal.shortestBridge(grid);

        // Then
        assertEquals(1, result); // Inner island to outer ring
    }

    // ==================== PERFORMANCE TESTS ====================

    @ParameterizedTest
    @MethodSource("providePerformanceTestData")
    @DisplayName("Performance Test - Large Inputs")
    void testPerformanceLargeInputs(String testType, Object input, String description) {
        // When
        long startTime = System.currentTimeMillis();

        switch (testType) {
            case "levelOrder":
                bfsTraversal.levelOrder((BFSTraversal.TreeNode) input);
                break;
            case "orangesRotting":
                bfsTraversal.orangesRotting((int[][]) input);
                break;
            case "updateMatrix":
                bfsTraversal.updateMatrix((int[][]) input);
                break;
        }

        long endTime = System.currentTimeMillis();

        // Then
        assertTrue(endTime - startTime < 200,
                description + " should complete within 200ms");
    }

    private static Stream<Arguments> providePerformanceTestData() {
        // Large grid (100x100)
        int[][] largeGrid = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                largeGrid[i][j] = (i + j) % 2;
            }
        }

        // Large tree (depth 15, ~32K nodes)
        BFSTraversal.TreeNode largeTree = createLargeTree(15);

        return Stream.of(
                Arguments.of("levelOrder", largeTree, "Large tree traversal"),
                Arguments.of("orangesRotting", largeGrid, "Large grid oranges"),
                Arguments.of("updateMatrix", largeGrid, "Large matrix update")
        );
    }

    private static BFSTraversal.TreeNode createLargeTree(int depth) {
        if (depth <= 0) return null;

        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);
        Queue<BFSTraversal.TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int val = 2;

        for (int level = 1; level < depth && !queue.isEmpty(); level++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                BFSTraversal.TreeNode node = queue.poll();
                if (val <= Math.pow(2, depth)) {
                    node.left = new BFSTraversal.TreeNode(val++);
                    node.right = new BFSTraversal.TreeNode(val++);
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
        }

        return root;
    }

    // ==================== INTEGRATION AND STRESS TESTS ====================

    @Test
    @DisplayName("BFS Properties Verification")
    void testBFSPropertiesVerification() {
        // Given
        BFSTraversal.TreeNode root = createComplexTree();

        // When
        List<List<Integer>> levelOrder = bfsTraversal.levelOrder(root);
        List<List<Integer>> zigzag = bfsTraversal.zigzagLevelOrder(root);
        List<Integer> rightView = bfsTraversal.rightSideView(root);

        // Then - Verify BFS properties
        assertEquals(levelOrder.size(), zigzag.size(),
                "Level order and zigzag should have same number of levels");
        assertEquals(levelOrder.size(), rightView.size(),
                "Right view should have one element per level");

        // Verify zigzag pattern
        for (int i = 0; i < levelOrder.size(); i++) {
            assertEquals(levelOrder.get(i).size(), zigzag.get(i).size(),
                    "Each level should have same number of nodes");

            if (i % 2 == 1) { // Odd levels should be reversed
                List<Integer> reversed = new ArrayList<>(levelOrder.get(i));
                Collections.reverse(reversed);
                assertEquals(reversed, zigzag.get(i));
            } else { // Even levels should be same
                assertEquals(levelOrder.get(i), zigzag.get(i));
            }
        }

        // Verify right view contains rightmost elements
        for (int i = 0; i < levelOrder.size(); i++) {
            List<Integer> level = levelOrder.get(i);
            assertEquals(level.get(level.size() - 1), rightView.get(i));
        }
    }

    @Test
    @DisplayName("Stress Test - Deep Tree Traversal")
    void testStressDeepTreeTraversal() {
        // Given - Deep left-skewed tree
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);
        BFSTraversal.TreeNode current = root;

        for (int i = 2; i <= 1000; i++) {
            current.left = new BFSTraversal.TreeNode(i);
            current = current.left;
        }

        // When
        long startTime = System.currentTimeMillis();
        int maxDepth = bfsTraversal.maxDepth(root);
        int minDepth = bfsTraversal.minDepth(root);
        List<Integer> rightView = bfsTraversal.rightSideView(root);
        List<List<Integer>> levelOrder = bfsTraversal.levelOrder(root);
        long endTime = System.currentTimeMillis();

        // Then
        assertEquals(1000, maxDepth);
        assertEquals(1000, minDepth);
        assertEquals(1000, rightView.size());
        assertEquals(1000, levelOrder.size());
        assertTrue(endTime - startTime < 100,
                "Deep tree operations should complete within 100ms");
    }

    @Test
    @DisplayName("Edge Cases Comprehensive Test")
    void testEdgeCasesComprehensive() {
        // Tree edge cases
        assertTrue(bfsTraversal.levelOrder(null).isEmpty());
        assertTrue(bfsTraversal.rightSideView(null).isEmpty());
        assertTrue(bfsTraversal.averageOfLevels(null).isEmpty());
        assertEquals(0, bfsTraversal.minDepth(null));
        assertEquals(0, bfsTraversal.maxDepth(null));

        // Grid edge cases
        assertEquals(0, bfsTraversal.orangesRotting(new int[0][0]));
        assertDoesNotThrow(() -> bfsTraversal.wallsAndGates(null));
        assertTrue(bfsTraversal.pacificAtlantic(new int[0][0]).isEmpty());

        // Word ladder edge cases
        assertEquals(0, bfsTraversal.ladderLength("hit", "cog", new ArrayList<>()));
        assertEquals(0, bfsTraversal.ladderLength("", "cog", Arrays.asList("cog")));

        // Open lock edge cases
        assertEquals(-1, bfsTraversal.openLock(new String[]{"0000"}, "0001"));
        assertEquals(0, bfsTraversal.openLock(new String[]{}, "0000"));
    }

    @Test
    @DisplayName("Multi-Source BFS Validation")
    void testMultiSourceBFSValidation() {
        // Test that multi-source BFS produces optimal results

        // Case 1: Multiple rotten oranges
        int[][] grid1 = {
                {2, 1, 1, 2},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {2, 1, 1, 2}
        };

        int result1 = bfsTraversal.orangesRotting(grid1);
        assertEquals(1, result1); // Should take only 1 minute due to multiple sources

        // Case 2: Single vs multiple gates
        int INF = Integer.MAX_VALUE;
        int[][] rooms1 = {{0, INF, INF, 0}}; // Two gates
        int[][] rooms2 = {{0, INF, INF, INF}}; // One gate

        bfsTraversal.wallsAndGates(rooms1);
        bfsTraversal.wallsAndGates(rooms2);

        // Multiple gates should produce better (smaller) distances
        assertTrue(rooms1[0][1] <= rooms2[0][1]);
        assertTrue(rooms1[0][2] <= rooms2[0][2]);
    }

    @Test
    @DisplayName("BFS vs DFS Behavior Verification")
    void testBFSvsDFSBehavior() {
        // Given - Tree where BFS and DFS would visit nodes in different orders
        BFSTraversal.TreeNode root = createComplexTree();

        // When
        List<List<Integer>> levelOrder = bfsTraversal.levelOrder(root);
        int minDepth = bfsTraversal.minDepth(root);

        // Then - Verify BFS characteristics
        // BFS visits level by level
        List<Integer> bfsOrder = new ArrayList<>();
        for (List<Integer> level : levelOrder) {
            bfsOrder.addAll(level);
        }

        // First few elements should be from top levels
        assertEquals(1, (int) bfsOrder.get(0)); // Root
        assertTrue(bfsOrder.subList(1, 3).containsAll(Arrays.asList(2, 3))); // Level 1

        // Min depth should find shortest path (BFS characteristic)
        assertEquals(3, minDepth); // Should find leaf nodes 5 or 6, not deeper node 7
    }

    @Test
    @DisplayName("Queue Size Management Test")
    void testQueueSizeManagement() {
        // Given - Wide tree to test queue size management
        BFSTraversal.TreeNode root = new BFSTraversal.TreeNode(1);

        // Create a tree with 8 leaf nodes at level 3
        root.left = new BFSTraversal.TreeNode(2);
        root.right = new BFSTraversal.TreeNode(3);

        root.left.left = new BFSTraversal.TreeNode(4);
        root.left.right = new BFSTraversal.TreeNode(5);
        root.right.left = new BFSTraversal.TreeNode(6);
        root.right.right = new BFSTraversal.TreeNode(7);

        root.left.left.left = new BFSTraversal.TreeNode(8);
        root.left.left.right = new BFSTraversal.TreeNode(9);
        root.left.right.left = new BFSTraversal.TreeNode(10);
        root.left.right.right = new BFSTraversal.TreeNode(11);
        root.right.left.left = new BFSTraversal.TreeNode(12);
        root.right.left.right = new BFSTraversal.TreeNode(13);
        root.right.right.left = new BFSTraversal.TreeNode(14);
        root.right.right.right = new BFSTraversal.TreeNode(15);

        // When
        List<List<Integer>> result = bfsTraversal.levelOrder(root);

        // Then - Verify all levels processed correctly
        assertEquals(4, result.size());
        assertEquals(1, result.get(0).size()); // Level 0: 1 node
        assertEquals(2, result.get(1).size()); // Level 1: 2 nodes
        assertEquals(4, result.get(2).size()); // Level 2: 4 nodes
        assertEquals(8, result.get(3).size()); // Level 3: 8 nodes

        // Verify correct order within each level
        assertEquals(Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15), result.get(3));
    }

    @Test
    @DisplayName("Memory Efficiency Test")
    void testMemoryEfficiency() {
        // Given - Tree designed to test memory usage patterns
        BFSTraversal.TreeNode root = createComplexTree();

        // When - Multiple operations that could cause memory leaks
        for (int i = 0; i < 100; i++) {
            bfsTraversal.levelOrder(root);
            bfsTraversal.rightSideView(root);
            bfsTraversal.minDepth(root);
            bfsTraversal.maxDepth(root);
        }

        // Then - Operations should complete without memory issues
        // This test primarily ensures no obvious memory leaks in queue management
        List<List<Integer>> finalResult = bfsTraversal.levelOrder(root);
        assertEquals(4, finalResult.size());
    }

    @Test
    @DisplayName("Concurrent Modification Safety")
    void testConcurrentModificationSafety() {
        // Test that BFS implementations handle queue operations safely
        BFSTraversal.TreeNode root = createStandardTree();

        // Multiple rapid calls should not cause concurrent modification issues
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 50; i++) {
                bfsTraversal.levelOrder(root);
                bfsTraversal.zigzagLevelOrder(root);
                bfsTraversal.rightSideView(root);
            }
        });
    }

    @Test
    @DisplayName("Data Integrity Test")
    void testDataIntegrity() {
        // Given
        BFSTraversal.TreeNode root = createStandardTree();

        // When - Multiple operations on same tree
        List<List<Integer>> levelOrder1 = bfsTraversal.levelOrder(root);
        List<Integer> rightView1 = bfsTraversal.rightSideView(root);
        List<List<Integer>> levelOrder2 = bfsTraversal.levelOrder(root);
        List<Integer> rightView2 = bfsTraversal.rightSideView(root);

        // Then - Results should be consistent
        assertEquals(levelOrder1, levelOrder2);
        assertEquals(rightView1, rightView2);

        // Tree structure should not be modified
        assertEquals(3, root.val);
        assertEquals(9, root.left.val);
        assertEquals(20, root.right.val);
    }

    @Test
    @DisplayName("Algorithm Correctness Verification")
    void testAlgorithmCorrectnessVerification() {
        // Test mathematical properties of BFS algorithms

        // Property 1: Min depth <= Max depth
        BFSTraversal.TreeNode tree = createComplexTree();
        int minDepth = bfsTraversal.minDepth(tree);
        int maxDepth = bfsTraversal.maxDepth(tree);
        assertTrue(minDepth <= maxDepth);

        // Property 2: Right view size == number of levels
        List<Integer> rightView = bfsTraversal.rightSideView(tree);
        List<List<Integer>> levels = bfsTraversal.levelOrder(tree);
        assertEquals(rightView.size(), levels.size());

        // Property 3: Level order total nodes == tree size
        int totalNodes = levels.stream().mapToInt(List::size).sum();
        assertEquals(7, totalNodes); // Complex tree has 7 nodes

        // Property 4: Average calculation correctness
        List<Double> averages = bfsTraversal.averageOfLevels(tree);
        for (int i = 0; i < levels.size(); i++) {
            double expectedAvg = levels.get(i).stream().mapToInt(Integer::intValue).average().orElse(0.0);
            assertEquals(expectedAvg, averages.get(i), 0.001);
        }
    }
}