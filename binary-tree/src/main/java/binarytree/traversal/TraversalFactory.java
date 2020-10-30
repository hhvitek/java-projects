package binarytree.traversal;

public class TraversalFactory {

    public enum TRAVERSALS {
        IN_ORDER,
        PRE_ORDER,
        POST_ORDER,
        IN_ORDER_ITER,
        PRE_ORDER_ITER,
        POST_ORDER_ITER
    }

    public static Traversal getTraversal(TRAVERSALS traversal) {
        switch (traversal) {
            case IN_ORDER:
                return new InOrderTraversalRecursive();
            case PRE_ORDER:
                return new PreOrderTraversalRecursive();
            case POST_ORDER:
                return new PostOrderTraversalRecursive();
            case IN_ORDER_ITER:
                return new InOrderTraversalIterative();
            case PRE_ORDER_ITER:
                return new PreOrderTraversalIterative();
            case POST_ORDER_ITER:
                return new PostOrderTraversalIterative();
            default:
                throw new RuntimeException("Unknown Traversal: " + traversal);
        }
    }
}
