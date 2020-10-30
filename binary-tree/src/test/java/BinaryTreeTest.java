import binarytree.BinaryTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BinaryTreeTest {

    private BinaryTree binaryTree = new BinaryTree();

    @BeforeEach
    private void init() {
        binaryTree.addValue(5);
        binaryTree.addValue(1);
        binaryTree.addValue(4);
        binaryTree.addValue(2);
        binaryTree.addValue(3);
        binaryTree.addValue(1);
    }

    @Test
    public void inOrderTraversalTest() {
        List<Integer> expected = List.of(1, 1, 2, 3, 4, 5);

        List<Integer> actual = binaryTree.traverseInOrderRecursive();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void inOrderIterativeTraversalTest() {
        List<Integer> expected = List.of(1, 1, 2, 3, 4, 5);

        List<Integer> actual = binaryTree.traverseInOrderIterative();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void postOrderTraversalTest() {
        List<Integer> expected = List.of(1, 3, 2, 4, 1, 5);

        List<Integer> actual = binaryTree.traversePostOrderRecursive();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void postOrderIterativeTraversalTest() {
        List<Integer> expected = List.of(1, 3, 2, 4, 1, 5);

        List<Integer> actual = binaryTree.traversePostOrderIterative();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void preOrderTraversalTest() {
        List<Integer> expected = List.of(5, 1, 1, 4, 2, 3);

        List<Integer> actual = binaryTree.traversePreOrderRecursive();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void preOrderIterativeTraversalTest() {
        List<Integer> expected = List.of(5, 1, 1, 4, 2, 3);

        List<Integer> actual = binaryTree.traversePreOrderIterative();

        Assertions.assertEquals(expected, actual);
    }

}
