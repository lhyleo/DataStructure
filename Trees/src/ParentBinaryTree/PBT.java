package ParentBinaryTree;

import java.time.Year;

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

    private void addHelper(PBTNode<T> PBTNode, PBTNode<T> value) {
        if (PBTNode.left == null) {
            value.parent = PBTNode;
            PBTNode.left = value;
            while (PBTNode != null) {
                PBTNode.subSize++;
                PBTNode = PBTNode.parent;
            }
        } else if (PBTNode.right == null) {
            value.parent = PBTNode;
            PBTNode.right = value;
            while (PBTNode != null) {
                PBTNode.subSize++;
                PBTNode = PBTNode.parent;
            }
        } else if (PBTNode.left.subSize <= PBTNode.right.subSize) {
            if (PBTNode.left == null) {
                value.parent = (PBTNode);
                PBTNode.left = value;
                while (PBTNode.parent != null) {
                    PBTNode = PBTNode.parent;
                    PBTNode.subSize++;
                }
            } else
                addHelper(PBTNode.left, value);
        } else {
            if (PBTNode.right == null) {
                value.parent = (PBTNode);
                PBTNode.right = (value);
                while (PBTNode.parent != null) {
                    PBTNode = PBTNode.parent;
                    PBTNode.subSize++;
                }
            } else {
                addHelper(PBTNode.right, value);
            }
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
                    this.root.subSize = (temp.subSize);
                    addHelper(this.root, temp);
                }
            } else
                removeHelper(this.root, value);
        }
    }

    private void removeSet(PBTNode<T> right, PBTNode<T> left, PBTNode<T> PBTNode) {
        if (right == null) {
            left.parent = (PBTNode);
            PBTNode.right = (left);
            resetSubSize(PBTNode, 1);
        } else if (left == null) {
            right.parent = (PBTNode);
            PBTNode.right = (right);
            resetSubSize(PBTNode, 1);
        } else {
            left.parent = (PBTNode);
            addHelper(left, right);
            PBTNode.right = (left);
            resetSubSize(PBTNode, 2);
        }
    }

    private void resetSubSize(PBTNode<T> PBTNode, int count) {
        while (PBTNode != null) {
            PBTNode.subSize -= count;
            PBTNode = PBTNode.parent;
        }
    }

    private void removeHelper(PBTNode<T> PBTNode, T value) {
        if (PBTNode.right != null && PBTNode.right.value.equals(value)) {
            PBTNode<T> right = PBTNode.right.right;
            PBTNode<T> left = PBTNode.right.left;
            if (right == null && left == null) {
                PBTNode.right = (null);
                resetSubSize(PBTNode, 1);
            } else {
                removeSet(right, left, PBTNode);
            }
        } else if (PBTNode.left != null && PBTNode.left.value.equals(value)) {
            PBTNode<T> right = PBTNode.left.right;
            PBTNode<T> left = PBTNode.left.left;
            if (right == null && left == null) {
                PBTNode.left = (null);
                resetSubSize(PBTNode, 1);
            } else {
                removeSet(right, left, PBTNode);
            }
        } else {
            if (PBTNode.right != null)
                removeHelper(PBTNode.right, value);
            if (PBTNode.left != null)
                removeHelper(PBTNode.left, value);
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
