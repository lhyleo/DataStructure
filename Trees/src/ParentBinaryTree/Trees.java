package ParentBinaryTree;

public class Trees {

    public static void main(String args[]) {
//        PBT<Integer> tree = new PBT<>(0);
////        tree.add(2);
////        tree.add(3);
////        tree.add(2);
////        System.out.println(tree.contains(2));
////        System.out.println(tree.getSize());
////        tree.remove(2);
////        System.out.println(tree.contains(2));
//        for (int i = 1; i < 100; i++) {
//            tree.add(i);
//        }
//        System.out.println(tree.size());
//        System.out.println(tree.contains(1));
//        System.out.println(tree.contains(19));
//        for (int i = 0; i < 10; i++) {
//            tree.remove(i);
//        } System.out.println(tree.contains(12));
//        System.out.println(tree.size());
        BT<Integer> tree = new BT<>(0);
        for (int i = 1; i < 100; i++) {
            tree.add(i);
        }
        for (int i = 10; i < 30; i++) {
            tree.remove(i);
        }
//        tree.remove(8);
//        System.out.println(tree.contains(5));
        System.out.println(tree.size());

    }
}
