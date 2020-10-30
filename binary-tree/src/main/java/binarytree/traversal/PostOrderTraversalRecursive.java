package binarytree.traversal;

import binarytree.Node;

public class PostOrderTraversalRecursive extends TraversalRecursive {
    @Override
    protected void traverseOrder(Node node) {
        traverseLeftToLowerRecursively(node);
        traverseRightToHigherRecursively(node);
        visitNode(node);
    }
}
