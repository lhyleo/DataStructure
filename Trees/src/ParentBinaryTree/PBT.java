package ParentBinaryTree;

/**
 * Parent Binary Tree
 * @param <T> - the generic type of the tree
 */
public class PBT<T> {

    private PBTNode<T> root;

    /**
     * This method creates a parent-binary tree. The PBT does not accept ducplicate values
     * @param value -the value to be store as root
     */
    public PBT(T value) {

        this.root = new PBTNode<>(value);
    }

    /**
     * Size of the tree
     * @return size of the tree
     */
    public int size() {
        if (this.root != null)
            return this.root.getSubSize();
        return 0;
    }

    /**
     * Add the value to the current PBT. PBT does not contain duplicate values
     * @param value - the value to be added to the tree
     */
    public void add(T value) {
        if (this.root == null) {
            root = new PBTNode<>(value);
        }
        if (!contains(value)) {
            PBTNode<T> val = new PBTNode<>(value);
            addHelper(this.root, val);
        }
    }

    /**
     * Return true if the PBT contains the given value.
     * @param value - The value to check if the PBT has contained it
     * @return True if the PBT contains the given value. False otherwise
     */
    public boolean contains(T value) {
        return containHelper(this.root, value);
    }

    private boolean containHelper(PBTNode<T> PBTNode, T value) {
        if (PBTNode == null) {
            return false;
        }
        if (PBTNode.getValue().equals(value)) {
            return true;
        } else {
            return containHelper(PBTNode.getRight(), value) || containHelper(PBTNode.getLeft(), value);
        }
    }

    private void addHelper(PBTNode<T> PBTNode, PBTNode<T> value) {
        if (PBTNode.getLeft() == null) {
            value.setParent(PBTNode);
            PBTNode.setLeft(value);
            while (PBTNode != null) {
                PBTNode.setSubSize(PBTNode.getSubSize() + 1);
                PBTNode = PBTNode.getParent();
            }
        } else if (PBTNode.getRight() == null) {
            value.setParent(PBTNode);
            PBTNode.setRight(value);
            while (PBTNode != null) {
                PBTNode.setSubSize(PBTNode.getSubSize() + 1);
                PBTNode = PBTNode.getParent();
            }
        } else if (PBTNode.getLeft().getSubSize() <= PBTNode.getRight().getSubSize()) {
            if (PBTNode.getLeft() == null) {
                value.setParent(PBTNode);
                PBTNode.setLeft(value);
                while (PBTNode.getParent() != null) {
                    PBTNode = PBTNode.getParent();
                    PBTNode.setSubSize(PBTNode.getSubSize() + 1);
                }
            } else
                addHelper(PBTNode.getRight(), value);
        } else {
            if (PBTNode.getRight() == null) {
                value.setParent(PBTNode);
                PBTNode.setRight(value);
                while (PBTNode.getParent() != null) {
                    PBTNode = PBTNode.getParent();
                    PBTNode.setSubSize(PBTNode.getSubSize() + 1);
                }
            }
        }
    }

    /**
     * Remove the value from the tree
     * @param value - the value to be removed
     */
    public void remove(T value) {
        if (this.root != null) {
            if (this.root.getValue().equals(value)) {
                if (this.root.getRight() == null && this.root.getLeft() == null) {
                    this.root = null;
                } else if (this.root.getLeft() == null) {
                    this.root = this.root.getRight();
                } else if (this.root.getRight() == null) {
                    this.root = this.root.getLeft();
                } else {
                    PBTNode<T> temp = this.root.getRight();
                    this.root = this.root.getLeft();
                    this.root.setParent(null);
                    this.root.setSubSize(temp.getSubSize());
                    addHelper(this.root, temp);
                }
            } else
                removeHelper(this.root, value);
        }
    }

    private void removeSet(PBTNode<T> right, PBTNode<T> left, PBTNode<T> PBTNode) {
        if (right == null) {
            left.setParent(PBTNode);
            PBTNode.setRight(left);
            resetSubSize(PBTNode, 1);
        } else if (left == null) {
            right.setParent(PBTNode);
            PBTNode.setRight(right);
            resetSubSize(PBTNode, 1);
        } else {
            left.setParent(PBTNode);
            addHelper(left, right);
            PBTNode.setRight(left);
            resetSubSize(PBTNode, 2);
        }

    }

    private void resetSubSize(PBTNode<T> PBTNode, int count) {
        while (PBTNode != null) {
            PBTNode.setSubSize(PBTNode.getSubSize() - count);
            PBTNode = PBTNode.getParent();
        }
    }

    private void removeHelper(PBTNode<T> PBTNode, T value) {
        if (PBTNode.getRight() != null && PBTNode.getRight().getValue().equals(value)) {
            PBTNode<T> right = PBTNode.getRight().getRight();
            PBTNode<T> left = PBTNode.getRight().getLeft();
            if (right == null && left == null) {
                PBTNode.setRight(null);
                resetSubSize(PBTNode, 1);
            } else {
                removeSet(right, left, PBTNode);
            }
        } else if (PBTNode.getLeft() != null && PBTNode.getLeft().getValue().equals(value)) {
            PBTNode<T> right = PBTNode.getLeft().getRight();
            PBTNode<T> left = PBTNode.getLeft().getLeft();
            if (right == null && left == null) {
                PBTNode.setLeft(null);
                resetSubSize(PBTNode, 1);
            } else {
                removeSet(right, left, PBTNode);
            }
        } else {
            if (PBTNode.getRight() != null)
                removeHelper(PBTNode.getRight(), value);
            if (PBTNode.getLeft() != null)
                removeHelper(PBTNode.getLeft(), value);
        }
    }

    @Override
    public String toString() {
        return this.root.toString();
    }

    /**
     * Prints a PBTNode's Sibling
     * @param PBTNode - the reference of a node
     */
    public void printSibling(PBTNode<T> PBTNode) {
        if (PBTNode.getLeft() != null && PBTNode.getRight() != null)
            System.out.println("Left: " + PBTNode.getLeft().getValue() + ", Right: " + PBTNode.getRight().getValue());
        else if (PBTNode.getLeft() == null && PBTNode.getRight() != null)
            System.out.println("Left: Null" + ", Right: " + PBTNode.getRight().getValue());
        else
            System.out.println("Left: " + PBTNode.getLeft().getValue() + ", Right: Null");
    }
}
