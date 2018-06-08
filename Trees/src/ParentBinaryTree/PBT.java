package ParentBinaryTree;

/**
 * Parent Binary Tree
 * @param <T> - the generic type of the tree
 */
public class PBT<T> {

    private PBTNode<T> root;

    /**
     * This method creates a parent-binary tree. The PBT does not accept ducplicate values
     */
    public PBT() {
        this.root = null;
    }

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
            return this.root.subSize;
        return 0;
    }

    /**
     * Add the value to the current PBT. PBT does not contain duplicate values
     * @param value - the value to be added to the tree
     */
    public void add(T value) {
        if (this.root == null) {
            root = new PBTNode<>(value);
        } else if (!contains(value)) {
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
        if (PBTNode.value.equals(value)) {
            return true;
        } else {
            return containHelper(PBTNode.right, value) || containHelper(PBTNode.left, value);
        }
    }

    private void resetSubSize(PBTNode<T> node) {
        while (node != null) {
            if (node.right != null && node.left != null)
                node.subSize = node.right.subSize + node.left.subSize + 1;
            else if (node.right == null)
                node.subSize = node.left.subSize + 1;
            else if (node.left == null)
                node.subSize = node.right.subSize + 1;
            else
                node.subSize = 1;
            node = node.parent;
        }
    }

    private void resetSubSize(PBTNode<T> node, int count) {
        while (node != null) {
            node.subSize += count;
            node = node.parent;
        }
    }

    private void addHelper(PBTNode<T> node, PBTNode<T> add) {
        if (node.left == null) {
            add.parent = node;
            node.left = add;
            resetSubSize(node);
        } else if (node.right == null) {
            add.parent = node;
            node.right = add;
            resetSubSize(node);
        } else if (node.left.subSize <= node.right.subSize) {
            addHelper(node.left, add);
        } else {
            addHelper(node.right, add);
        }
    }

    /**
     * Remove the value from the tree
     * @param value - the value to be removed
     */
    public void remove(T value) {
        if (this.root != null) {
            if (this.root.value.equals(value)) {
                if (this.root.right == null && this.root.left == null) {
                    this.root = null;
                } else if (this.root.left == null) {
                    this.root = this.root.right;
                } else if (this.root.right == null) {
                    this.root = this.root.left;
                } else {
                    PBTNode<T> temp = this.root.right;
                    this.root = this.root.left;
                    this.root.parent = (null);
                    this.root.subSize += (temp.subSize);
                    addHelper(this.root, temp);
                }
            } else
                removeHelper(this.root, value);
        }
    }

    private void removeHelper(PBTNode<T> node, T value) {
        if (node.right != null && node.right.value.equals(value)) {
            PBTNode<T> right = node.right.right;
            PBTNode<T> left = node.right.left;
            if (right == null && left == null) {
                node.right = (null);
                resetSubSize(node);
            } else {
                if (right == null) {
                    left.parent = node;
                    node.right = left;
                    resetSubSize(node);
                } else if (left == null) {
                    right.parent = node;
                    node.right = right;
                    resetSubSize(node);
                } else {
                    addHelper(left, right);
                    node.right = left;
                    resetSubSize(node.right);
                }
            }
        } else if (node.left != null && node.left.value.equals(value)) {
            PBTNode<T> right = node.left.right;
            PBTNode<T> left = node.left.left;
            if (right == null && left == null) {
                node.right = (null);
                resetSubSize(node);
            } else {
                if (right == null) {
                    left.parent = node;
                    node.left = left;
                    resetSubSize(node);
                } else if (left == null) {
                    right.parent = node;
                    node.left = right;
                    resetSubSize(node);
                } else {
                    addHelper(left, right);
                    node.left = left;
                    resetSubSize(node.left);
                }
            }
        } else {
            if (node.right != null)
                removeHelper(node.right, value);
            if (node.left != null)
                removeHelper(node.left, value);
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
        if (PBTNode.left != null && PBTNode.right != null)
            System.out.println("Left: " + PBTNode.left.value + ", Right: " + PBTNode.right.value);
        else if (PBTNode.left == null && PBTNode.right != null)
            System.out.println("Left: Null" + ", Right: " + PBTNode.right.value);
        else
            System.out.println("Left: " + PBTNode.left.value + ", Right: Null");
    }

    class PBTNode<T> {
        T value;
        PBTNode<T> parent;
        PBTNode<T> left;
        PBTNode<T> right;
        int subSize;

        public PBTNode(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
            this.subSize = 1;
        }

        @Override
        public String toString() {
            return "Value: " + this.value + ", size: " + this.subSize;
        }
    }
}
