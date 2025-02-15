package com.MomsDeveloper.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RBtreeTest {
    private RBtree tree;
    // private List<String> callLog;

    @BeforeEach
    void setup() {
        // tree = new RBtree() {
        //     @Override
        //     protected void log(String message) {
        //         callLog.add(message);
        //     }
        // }

        tree = new RBtree();
        // callLog = new ArrayList<>();
    }

    @Test
    void testRedBlackPropertiesAfterInsertions() {
        tree.insertNode(10);
        tree.insertNode(20);
        tree.insertNode(30);
        
        assertTrue(tree.isValidRBTree());
    }

    @Test
    void testSearch() {
        tree.insertNode(15);
        tree.insertNode(25);
        tree.insertNode(5);
        
        assertNotNull(tree.searchNode(15));
        assertNotNull(tree.searchNode(25));
        assertNull(tree.searchNode(100));
    }
}
