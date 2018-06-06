package ParentBinaryTree;

/**
 * Parent Binary Tree node that use for PBT
 * PBTNode has a direct linkage to the immediate parent
 * @param <T>
 */
public class PBTNode<T> {

    private T value;
    private PBTNode parent;
    private PBTNode left;
    private PBTNode right;
    private int subSize;

    /**
     * Create a PBTNode
     * @param value - the Value of the node
     */
    public PBTNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.subSize = 1;
    }

    /**
     * Return the size of the subtree
     * @return the size of the subtree
     */
    public int getSubSize() {
        return subSize;
    }

    /**
     * Set the size of the subtree
     * @param subSize - the size to set
     */
    public void setSubSize(int subSize) {
        this.subSize = subSize;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public PBTNode getParent() {
        return parent;
    }

    public void setParent(PBTNode parent) {
        this.parent = parent;
    }

    public PBTNode getLeft() {
        return left;
    }

    public void setLeft(PBTNode left) {
        this.left = left;
    }

    public PBTNode getRight() {
        return right;
    }

    public void setRight(PBTNode right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }
        PBTNode<?> that = (PBTNode<?>) obj;
        return value.equals(that.value);
    }

    @Override
    public String toString() {
        return "Value: " + this.value + ", size: " + this.subSize;
    }
}
