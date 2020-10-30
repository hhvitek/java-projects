package binarytree.traversal;

import binarytree.Node;

import java.util.ArrayDeque;

public class PreOrderTraversalIterative extends TraversalIterative {

    @Override
    protected void traverseIteratively(Node node) {
        traversalStack = new ArrayDeque<>();

        traverseToLeftMostLowest(node);

        while (!traversalStack.isEmpty()) {
            Node currentNode = traversalStack.pop();

            traverseToLeftMostLowest(currentNode.right);
        }

    }

    /**
     * go to the left-most node and add all nodes on the way
     */
    private void traverseToLeftMostLowest(Node node) {
        while (node != null) {
            traversalStack.push(node);
            visitCurrentNode();
            node = node.left;
        }
    }
}
