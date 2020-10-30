package binarytree.traversal;

import binarytree.Node;

import java.util.ArrayList;
import java.util.List;

abstract public class TraversalRecursive implements Traversal {

    protected List<Integer> traversalOutput;

    @Override
    public List<Integer> traverseTree(Node node) {
        traversalOutput = new ArrayList<>();

        traverseRecursively(node);

        return traversalOutput;
    }

    protected void traverseRecursively(Node node) {
        if (node != null) {
            traverseOrder(node);
        }
    }

    protected abstract void traverseOrder(Node node);

    protected void traverseLeftToLowerRecursively(Node node) {
        traverseRecursively(node.left);
    }

    protected void traverseRightToHigherRecursively(Node node) {
        traverseRecursively(node.right);
    }

    protected void visitNode(Node node) {
        traversalOutput.add(node.value);
    }
}
