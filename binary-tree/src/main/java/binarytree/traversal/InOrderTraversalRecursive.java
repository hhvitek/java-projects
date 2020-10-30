package binarytree.traversal;

import binarytree.Node;

public class InOrderTraversalRecursive extends TraversalRecursive {

    @Override
    protected void traverseOrder(Node node) {
        traverseLeftToLowerRecursively(node);
        visitNode(node);
        traverseRightToHigherRecursively(node);
    }
}
