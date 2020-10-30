package binarytree.traversal;

import binarytree.Node;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

abstract public class TraversalIterative implements Traversal {

    protected Deque<Node> traversalStack;

    protected List<Integer> traversalOutput;

    @Override
    public List<Integer> traverseTree(Node node) {
        traversalOutput = new ArrayList<>();

        traverseIteratively(node);

        return traversalOutput;
    }

    protected abstract void traverseIteratively(Node node);

    protected void visitCurrentNode() {
        Node currentNode = traversalStack.peek();
        traversalOutput.add(currentNode.value);
    }
}
