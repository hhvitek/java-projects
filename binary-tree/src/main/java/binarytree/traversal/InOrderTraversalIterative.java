package binarytree.traversal;

import binarytree.Node;

import java.util.*;

public class InOrderTraversalIterative extends TraversalIterative {

    @Override
    protected void traverseIteratively(Node node) {
        traversalStack = new ArrayDeque<>();

        traverseToLeftMostLowest(node);

        while (!traversalStack.isEmpty()) {

            visitCurrentNode();

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
            node = node.left;
        }
    }


}
