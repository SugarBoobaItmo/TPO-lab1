package com.MomsDeveloper.task2;

public class RBtree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    static class Node {
        int data;

        Node left;
        Node right;
        Node parent;

        boolean color;

        public Node(int data) {
            this.data = data;
        }
    }

    private static class NilNode extends Node {
        public NilNode() {
            super(0);
            this.color = BLACK;
        }
    }

    // Operations
    public Node searchNode(int data) {
        Node current = root;
        while (current != null) {
            if (data == current.data) {
                return current;
            } else if (data < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public void insertNode(int data) {
        Node current = root;
        Node parent = null;

        // Find the parent of the new node
        while (current != null) {
            parent = current;
            if (data < current.data) {
                current = current.left;
            } else if (data > current.data) {
                current = current.right;
            } else {
                throw new IllegalArgumentException("Tree already contains a node with data " + data);
            }
        }

        Node newNode = new Node(data);
        newNode.color = RED;

        // Insert the new node
        if (parent == null) {
            root = newNode;
            root.color = BLACK;
        } else if (data < parent.data) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.parent = parent;
        insertFixup(newNode);
    }

    public void deleteNode(int data) {
        Node node = root;

        if (node == null) {
            throw new IllegalArgumentException("Cannot delete from an empty tree.");
        }

        // find the node to delete
        while (node != null && node.data != data) {
            if (data < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        // node not found
        if (node == null)
            return;

        Node movedUpNode;
        boolean deletedNodeColor;

        if (node.left == null || node.right == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        } else {
            Node inOrderSuccessor = findMinimum(node.right);
            node.data = inOrderSuccessor.data;
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        if (deletedNodeColor == BLACK) {
            deleteFixup(movedUpNode);

            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null);
            }
        }
    }

    public boolean isValidRBTree() {
        boolean checkColors = checkRBProperties(root);
        boolean checkHeights = checkBlackHeight(root) != -1;

        return checkColors && checkHeights;
    }

    // Fixup methods
    private void insertFixup(Node node) {
        Node parent = node.parent;

        // Case 1
        if (parent == null || parent.color == BLACK) {
            return;
        }

        Node grandparent = parent.parent;

        // Case 2
        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }

        Node uncle = getUncle(parent);

        // Case 3
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;
            insertFixup(grandparent);
            return;
        } else if (parent == grandparent.left) {
            // Case 4a
            if (node == parent.right) {
                rotateLeft(parent);
                parent = node;
            }
            // Case 5a
            rotateRight(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        } else {
            // Case 4b
            if (node == parent.left) {
                rotateRight(parent);
                parent = node;
            }
            // Case 5b
            rotateLeft(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }

    }

    private void deleteFixup(Node node) {
        // Case 1
        if (node == root)
            return;

        Node sibling = getSibling(node);

        // Case 2
        if (sibling.color == RED) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        // Cases 3 & 4
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = RED;

            // Case 3
            if (node.parent.color == RED)
                node.parent.color = BLACK;
            // Case 4
            else
                deleteFixup(node.parent);
        }
        // Cases 5 && 6
        else
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);

    }

    // Helper methods

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    private Node deleteNodeWithZeroOrOneChild(Node node) {
        // Node has ONLY a left child
        if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left);
            return node.left; // moved-up node
        }

        // Node has ONLY a right child
        else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right);
            return node.right; // moved-up node
        }

        // Node has no children
        else {
            Node newChild = node.color == BLACK ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }

    private Node findMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node getUncle(Node parent) {
        Node grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    private Node getSibling(Node node) {
        Node parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }
    }

    private boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private void handleRedSibling(Node node, Node sibling) {
        // Recolor...
        sibling.color = BLACK;
        node.parent.color = RED;

        // ...and rotate
        if (node == node.parent.left) {
            rotateLeft(node.parent);
        } else {
            rotateRight(node.parent);
        }
    }

    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        boolean isLeftChild = node == node.parent.left;

        // Case 5
        if (isLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!isLeftChild && isBlack(sibling.left)) {
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        // Case 6
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (isLeftChild) {
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }

    private boolean checkRBProperties(Node node) {
        if (node == null) {
            return true;
        }

        if (node.color == RED && (isRed(node.left) || isRed(node.right))) {
            return false;
        }

        return checkRBProperties(node.left) && checkRBProperties(node.right);
    }

    private int checkBlackHeight(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = checkBlackHeight(node.left);
        int rightHeight = checkBlackHeight(node.right);

        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return -1;
        }

        return isBlack(node) ? leftHeight + 1 : leftHeight;
    }
}
