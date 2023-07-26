public class Driver {
    public static void main(String args[]){
        PasswordStorage strengthBST = new PasswordStorage(Attribute.STRENGTH_RATING);
        Password p1 = new Password("password", 765);
        Password p2 = new Password("H1sw2zoVOstiS", 250);
        Password p3 = new Password("abc123", 2090);
        Password p4 = new Password("very_difficult_password", 123);

        PasswordNode strengthP3Node = new PasswordNode(p3, null, null);
        PasswordNode strengthP2Node = new PasswordNode(p2, null, null);
        PasswordNode strengthRootNode = new PasswordNode(p1, strengthP3Node,
                strengthP2Node);
        strengthBST.root = strengthRootNode;
        strengthBST.addPassword(p4);



        System.out.println(strengthBST);

//        Password p1 = new Password("password", 765);
//        PasswordStorage strengthBST = new PasswordStorage(Attribute.STRENGTH_RATING);
//        PasswordNode strengthRootNode = null;
//        strengthBST.root = strengthRootNode;
//        strengthBST.addPassword(p1);
//        System.out.println(strengthBST);
    }
}
