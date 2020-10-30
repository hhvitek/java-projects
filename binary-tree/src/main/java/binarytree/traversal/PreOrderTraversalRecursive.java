package binarytree.traversal;

import binarytree.Node;

public class PreOrderTraversalRecursive extends TraversalRecursive {
    @Override
    protected void traverseOrder(Node node) {
        visitNode(node);
        traverseLeftToLowerRecursively(node);
        traverseRightToHigherRecursively(node);
    }
}
