public class Driver {
    public static void main(String args[]){
        Password p = new Password("1234567890", 1000);
        Password p2 = new Password("test", 500); // same occurrences
        Password p3 = new Password("iloveyou", 765);
        Password p4 = new Password("qwerty", 250);
        Password p5 = new Password("admin", 1002);
        Password p6 = new Password("password", 2232);
        Password p7 = new Password("abc123", 2090);
        Password p9 = new Password("copy_password", 500); // same occurrences

        PasswordStorage bst = new PasswordStorage(Attribute.STRENGTH_RATING);
        PasswordNode p4Node = new PasswordNode(p4);
        PasswordNode p3Node = new PasswordNode(p3);
        PasswordNode p7Node = new PasswordNode(p7);
        PasswordNode p9Node = new PasswordNode(p9);
        PasswordNode p6Node = new PasswordNode(p6, p7Node, p9Node);
        PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
        PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
        PasswordNode rootNode = new PasswordNode(p, p2Node, p5Node);
        bst.root = rootNode;


        System.out.println(bst);
    }
}
