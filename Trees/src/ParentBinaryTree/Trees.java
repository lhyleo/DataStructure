package ParentBinaryTree;

public class Trees {

    public static void main(String args[]) {
        PBT<Integer> tree = new PBT<>(0);
//        tree.add(2);
//        tree.add(3);
//        tree.add(2);
//        System.out.println(tree.contains(2));
//        System.out.println(tree.getSize());
//        tree.remove(2);
//        System.out.println(tree.contains(2));
        for (int i = 1; i < 20; i++) {
            tree.add(i);
        }
        System.out.println(tree.contains(1));
        tree.remove(4);
        tree.remove(5);
        tree.remove(8);
        tree.remove(19);
        System.out.println(tree.contains(20));
        for (int i = 0; i < 21; i++) {
            tree.remove(i);
        }
        tree.add(0);
        tree.add(1);
        tree.add(2);
        System.out.println(tree.size());

    }
}
