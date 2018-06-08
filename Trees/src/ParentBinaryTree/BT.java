package ParentBinaryTree;

public class BT<T> extends PBT<T> {

    private PBTNode<T> root;

    public BT() {
        super();
    }

    public BT(T value) {
        this.root = new PBTNode<T>(value);
    }

    public int size() {
        if (this.root != null)
            return this.root.subSize;
        return 0;
    }

    @Override
    public void add(T value) {
        if (this.root == null) {
            root = new PBTNode<T>(value);
        } else if (!contains(value)) {
            PBTNode<T> val = new PBTNode<T>(value);
            addHelper(this.root, val);
        }
        updateSubSize(this.root);
    }

    private int updateSubSize(PBTNode<T> node) {
        if (node.left == null && node.right == null) {
            node.subSize = 1;
            return 1;
        } else if (node.left == null) {
            node.subSize = updateSubSize(node.right) + 1;
            return node.subSize;
        } else if (node.right == null) {
            node.subSize = updateSubSize(node.left) + 1;
            return node.subSize;
        } else {
            node.subSize = updateSubSize(node.left) + updateSubSize(node.right) + 1;
            return node.subSize;
        }
    }

    private void addHelper(PBTNode<T> node, PBTNode<T> add) {
        if (node.left == null) {
            node.left = add;
        } else if (node.right == null) {
            node.right = add;
        } else if (node.left.subSize <= node.right.subSize) {
            addHelper(node.left, add);
        } else {
            addHelper(node.right, add);
        }
    }

    @Override
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

    public void remove(T value) {
        if (this.root != null) {
            if (this.root.value.equals(value)) {
                if (this.root.subSize == 1) {
                    this.root = null;
                } else if (this.root.right == null){
                    this.root = this.root.left;
                } else if (this.root.left == null) {
                    this.root = this.root.right;
                } else {
                    PBTNode<T> temp = this.root.right;
                    this.root = this.root.left;
                    this.root.parent = (null);
                    this.root.subSize += (temp.subSize);
                    addHelper(this.root, temp);
                }
            } else {
                removeHelper(this.root, value);
                updateSubSize(this.root);
            }
        }
    }

    private void removeHelper(PBTNode<T> node, T value) {
        if (node.right != null && node.right.value.equals(value)) {
            PBTNode<T> right = node.right.right;
            PBTNode<T> left = node.right.left;
            if (right == null && left == null) {
                node.right = (null);
            } else {
                if (right == null) {
                    node.right = left;
                } else if (left == null) {
                    node.right = right;
                } else {
                    addHelper(left, right);
                    node.right = (left);
                }
            }
        } else if (node.left != null && node.left.value.equals(value)) {
            PBTNode<T> right = node.left.right;
            PBTNode<T> left = node.left.left;
            if (right == null && left == null) {
                node.left = (null);
            } else {
                if (right == null) {
                    node.left = left;
                } else if (left == null) {
                    node.left = right;
                } else {
                    addHelper(left, right);
                    node.left = (left);
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
    public void printSibling(PBTNode<T> node) {
        if (node.left != null && node.right != null)
            System.out.println("Left: " + node.left.value + ", Right: " + node.right.value);
        else if (node.left == null && node.right != null)
            System.out.println("Left: Null" + ", Right: " + node.right.value);
        else
            System.out.println("Left: " + node.left.value + ", Right: Null");
    }

    @Override
    public String toString() {
        return this.root.toString();
    }
}
