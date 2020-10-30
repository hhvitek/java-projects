package binarytree.traversal;

import binarytree.Node;

import java.util.ArrayDeque;
import java.util.Deque;

public class PostOrderTraversalIterative extends TraversalIterative {

    private Deque<Boolean> traversalStackFromLeft;

    @Override
    protected void traverseIteratively(Node node) {
        traversalStack = new ArrayDeque<>();
        traversalStackFromLeft = new ArrayDeque<>();

        traverseToLeftMostLowest(node);

        while(!traversalStack.isEmpty()) {
            boolean fromLeft = traversalStackFromLeft.pop();

            if (fromLeft) {
                traversalStackFromLeft.push(false);
                Node currentNode = traversalStack.peek();
                traverseToLeftMostLowest(currentNode.right);
            } else {
                visitCurrentNode();
                traversalStack.pop();
            }
        }

    }


    /**
     * go to the left-most node and add all nodes on the way
     */
    private void traverseToLeftMostLowest(Node node) {
        while (node != null) {
            traversalStack.push(node);
            traversalStackFromLeft.push(true);
            node = node.left;
        }
    }
}
