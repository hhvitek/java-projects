package binarytree;

import binarytree.traversal.*;

import java.util.List;

public class BinaryTree {

    private Node root;

    public void addValue(int value) {
        if (root == null) {
            root = new Node(value);
        } else {
            insertValueRecursively(root, value);
        }
    }

    private void insertValueRecursively(Node node, int value) {
        if (node.value >= value) {
            if (node.left == null) {
                node.left = new Node(value);
            } else {
                insertValueRecursively(node.left, value);
            }
        } else {
            if (node.right == null) {
                node.right = new Node(value);
            } else {
                insertValueRecursively(node.right, value);
            }
        }
    }

    public List<Integer> traverseInOrderRecursive() {
        return traverse(TraversalFactory.TRAVERSALS.IN_ORDER);
    }

    private List<Integer> traverse(TraversalFactory.TRAVERSALS traversals) {
        Traversal traversal = TraversalFactory.getTraversal(traversals);

        return traversal.traverseTree(root);
    }

    public List<Integer> traversePostOrderRecursive() {
        return traverse(TraversalFactory.TRAVERSALS.POST_ORDER);
    }

    public List<Integer> traversePreOrderRecursive() {
        return traverse(TraversalFactory.TRAVERSALS.PRE_ORDER);
    }

    public List<Integer> traverseInOrderIterative() {
        return traverse(TraversalFactory.TRAVERSALS.IN_ORDER_ITER);
    }

    public List<Integer> traversePreOrderIterative() {
        return traverse(TraversalFactory.TRAVERSALS.PRE_ORDER_ITER);
    }

    public List<Integer> traversePostOrderIterative() {
        return traverse(TraversalFactory.TRAVERSALS.POST_ORDER_ITER);
    }
}
