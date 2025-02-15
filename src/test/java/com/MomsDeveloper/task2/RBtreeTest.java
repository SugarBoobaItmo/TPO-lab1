package com.MomsDeveloper.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class RBtreeTest {
    private RBtree tree;

    @BeforeEach
    void setup() {
        tree = new RBtree();
    }

    @Test
    @DisplayName("Test empty tree")
    void testEmptyTree() {
        assertNull(tree.searchNode(10), "Searching in an empty tree should return null");
    }

    @Test
    @DisplayName("Test deletion from empty tree")
    void testDeleteFromEmptyTree() {
        assertThrows(IllegalArgumentException.class, () -> tree.deleteNode(10), "Cannot delete from an empty tree");
    }

    @Test
    @DisplayName("Test insertion in empty tree")
    void testInsertionInEmptyTree() {
        tree.insertNode(10);
        RBtree.Node root = tree.searchNode(10);

        assertNotNull(root, "Root should not be null");
        assertEquals(10, root.data, "Root should have data 10");
        assertFalse(root.color, "Root should be black");
    }

    @Test
    @DisplayName("Test insertion without rotation")
    void testSimpleInsertionWithoutRotation() {
        tree.insertNode(10);
        tree.insertNode(20);

        RBtree.Node node20 = tree.searchNode(20);
        assertNotNull(node20, "Node with data 20 should not be null");
        assertEquals(20, node20.data, "Incorrect data");
        assertEquals(tree.searchNode(10), node20.parent, "Parent of node with 20 should be node with 10");
    }

    @Test
    @DisplayName("Test insertion with recoloring")
    void testInsertWithRecoloring() {
        tree.insertNode(10);
        tree.insertNode(5);
        tree.insertNode(15);
        tree.insertNode(1);
        
        RBtree.Node node5 = tree.searchNode(5);
        RBtree.Node node15 = tree.searchNode(15);
        assertFalse(node5.color, "Node with data 5 should be black after recoloring");
        assertFalse(node15.color, "Node with data 15 should be black after recoloring");
    }

    @Test
    @DisplayName("Test insertion with rotation")
    void testInsertWithRotation() {
        tree.insertNode(10);
        tree.insertNode(5);
        tree.insertNode(1); 

        RBtree.Node root = tree.searchNode(5);
        assertEquals(5, root.data, "Root should be 5 after right rotation");
        assertEquals(1, root.left.data, "Left child of 5 should be 1");
        assertEquals(10, root.right.data, "Right child of 5 should be 10");
        assertTrue(tree.isValidRBTree(), "Tree should be a valid red-black tree");
    }

    @Test
    @DisplayName("Test deletion of leaf node")
    void testDeleteLeafNode() {
        tree.insertNode(10);
        tree.insertNode(5);
        tree.deleteNode(5);

        assertNull(tree.searchNode(5), "Node with data 5 should be deleted");
    }

    @Test
    @DisplayName("Test deletion of node with one child")
    void testDeleteNodeWithOneChild() {
        tree.insertNode(10);
        tree.insertNode(5);
        tree.insertNode(1);

        tree.deleteNode(5);

        RBtree.Node node1 = tree.searchNode(1);
        assertNotNull(node1, "Node with 1 should exist");
        assertEquals(tree.searchNode(10), node1.parent, "Parent of 1 should be 10");

    }

    @Test
    @DisplayName("Test deletion of node with two children")
    void testDeleteNodeWithTwoChildren() {
        tree.insertNode(10);
        tree.insertNode(5);
        tree.insertNode(15);
        tree.insertNode(12);
        tree.insertNode(17);

        tree.deleteNode(10); 

        RBtree.Node newRoot = tree.searchNode(12);
        assertNotNull(newRoot, "Root should be 12 or 15");
    }


    @Test
    @DisplayName("Test duplicate insertion")
    void testDuplicateInsertion() {
        tree.insertNode(10);
        assertThrows(IllegalArgumentException.class, () -> tree.insertNode(10), "Tree already contains a node with data 10");
    }

}
