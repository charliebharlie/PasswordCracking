//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Tester for the Password, PasswordStorage, PasswordNode
// Course:   CS 300 Spring 2023
//
// Author:   Charlie Liu
// Email:    cliu637@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  Piazza Posts
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

public class PasswordCrackingTester {

    /**
     * Validates the constructor and accessor methods of PasswordStorage, specifically the
     * getComparisonCriteria(), size(), and isEmpty() methods, as well as accessing the
     * protected data field root.
     * <p>
     * Be sure to try making multiple PasswordStorage objects with different Attributes.
     *
     * @return true if the basic accessor methods work as expected, false otherwise
     */
    public static boolean testBasicPasswordStorageMethods() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
            // (1) getComparisonCriteria() test
            Attribute actualAttribute = bst.getComparisonCriteria();
            Attribute expectedAttribute = Attribute.OCCURRENCE;
            if (!actualAttribute.equals(expectedAttribute)) {
                return false;
            }

            // (2) size() test
            int actualSize = bst.size();
            int expectedSize = 0;
            if (actualSize != expectedSize) {
                System.out.println("this fails");
                return false;
            }

            // (3) isEmpty() test
            boolean isActualEmpty = bst.isEmpty();
            boolean isExpectedEmpty = true;
            if (isActualEmpty != isExpectedEmpty) {
                System.out.println("this fails");
                return false;
            }

            // (4) root test
            PasswordNode expectedRoot = null;
            PasswordNode actualRoot = bst.root;
            if (expectedRoot != actualRoot) {
                System.out.println(actualRoot);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true; // TODO
    }

    /**
     * Validates the Password class compareTo() method. Create at least two DIFFERENT
     * Password objects and compare them on each of the Attribute values. See the writeup
     * for details on how the various comparisons are expected to work.
     *
     * @return true if Password's compareTo() works as expected, false otherwise
     */
    public static boolean testPasswordCompareTo() {
        try {
            Password p1 = new Password("test@123", 500);
            Password p2 = new Password("iloveyou", 765);

            // OCCURRENCE test
            if (p1.compareTo(p2, Attribute.OCCURRENCE) >= 0) {
                System.out.println(p1.compareTo(p2, Attribute.OCCURRENCE) < 0);
                return false;
            }

            // STRENGTH test (remember that the longer a string is, the more likely it is to be
            // "stronger"
            if (p1.compareTo(p2, Attribute.STRENGTH_RATING) <= 0) {
                System.out.println(p1.compareTo(p2, Attribute.STRENGTH_RATING));
                return false;
            }

            // HASHED_PASSWORD test
            if (p1.compareTo(p2, Attribute.HASHED_PASSWORD) >= 0) {
                System.out.println(p1.compareTo(p2, Attribute.HASHED_PASSWORD) < 0);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true; // TODO
    }

    /**
     * Validates the incomplete methods in PasswordNode, specifically isLeafNode(),
     * numberOfChildren(), hasLeftChild() and hasRightChild(). Be sure to test all
     * possible configurations of a node in a binary tree!
     *
     * @return true if the status methods of PasswordNode work as expected, false otherwise
     */
    public static boolean testNodeStatusMethods() {
        try {
            Password p1 = new Password("test", 500);
            Password p2 = new Password("iloveyou", 765);
            Password p3 = new Password("qwerty", 250);

            PasswordNode leftNode = new PasswordNode(p2);
            PasswordNode rightNode = new PasswordNode(p3);

            // (1) has only the root
            {
                PasswordNode currentNode = new PasswordNode(p1, null, null);
                if (!currentNode.isLeafNode()) {
                    return false;
                }
            }
            // (2) has right and left nodes
            {
                PasswordNode currentNode = new PasswordNode(p1, leftNode, rightNode);
                int expectedNodes = 2;
                int actualNodes = currentNode.numberOfChildren();

                if (currentNode.isLeafNode() || !currentNode.hasLeftChild() ||
                        !currentNode.hasRightChild() ||
                        (expectedNodes != actualNodes)) {
                    System.out.println("this fails asdf asdf " + currentNode.isLeafNode());
                    return false;
                }
            }

            // (3) has only left node
            {
                PasswordNode currentNode = new PasswordNode(p1, leftNode, null);
                int expectedNodes = 1;
                int actualNodes = currentNode.numberOfChildren();

                if (currentNode.isLeafNode() || (!currentNode.hasLeftChild() ||
                        currentNode.hasRightChild()) || expectedNodes != actualNodes) {

                    return false;
                }
            }

            // (4) has only right node
            {
                PasswordNode currentNode = new PasswordNode(p1, null, rightNode);
                int expectedNodes = 1;
                int actualNodes = currentNode.numberOfChildren();

                if (currentNode.isLeafNode() || (currentNode.hasLeftChild() ||
                        !currentNode.hasRightChild()) || expectedNodes != actualNodes) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true; // TODO
    }

    /**
     * Validates the toString() method in PasswordStorage through edge case and valid cases
     *
     * @return true if the toString() method of PasswordStorage works as expected, false otherwise
     */
    // GIVE TO STUDENTS
    public static boolean testToString() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            // empty is empty string
            String expected = "";
            String actual = bst.toString();
            if (!actual.equals(expected)) {
                System.out.println("toString() does not return the proper value on an empty " +
                        "tree!");
                return false;
            }

            // size one only returns 1 thing
            Password p = new Password("1234567890", 15000);
            PasswordNode rootNode = new PasswordNode(p);

            bst.root = rootNode; // here I am manually building the tree by editing the root node
            // directly to be the node of my choosing

            expected = p.toString() + "\n";
            actual = bst.toString();
            if (!actual.equals(expected)) {
                System.out.println(actual);
                System.out.println("this fails");
                return false;
            }


            // big tree returns in-order traversal
            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);

            PasswordNode p4Node = new PasswordNode(p4);
            PasswordNode p3Node = new PasswordNode(p3);
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode; // set the root node to be p

            expected = p4.toString() + "\n" + p2.toString() + "\n" + p3.toString() + "\n" +
                    p.toString() + "\n" + p5.toString() + "\n" + p7.toString() + "\n" +
                    p6.toString() + "\n";
            actual = bst.toString();
//            System.out.println(actual.equals(expected));
            if (!actual.equals(expected)) {

                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Validates the isValid() method in PasswordStorage through edge cases, valid cases, and
     * invalid cases
     *
     * @return true if the isValid() method of PasswordStorage works as expected, false otherwise
     */
    // GIVE TO STUDENTS
    public static boolean testIsValidBST() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            // empty tree is a valid bst
            /*
             * String expected = ""; String actual = bst.toString();
             */
            if (!bst.isValidBST()) {
                System.out.println("isValidBST() says that an empty tree is not a valid BST!");
                return false;
            }

            // size one is a bst
            Password p = new Password("1234567890", 1000);
            PasswordNode rootNode = new PasswordNode(p);

            bst.root = rootNode; // here I am manually building the tree by editing the root node
            // directly to be the node of my choosing

            if (!bst.isValidBST()) {
                System.out.println("isValidBST() says that a tree of size 1 is not a valid BST!");
                return false;
            }

            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);

            // works on indentifying small obviously invalid tree
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p3Node = new PasswordNode(p3);
            rootNode = new PasswordNode(p, p7Node, p3Node);
            bst.root = rootNode;
            if (bst.isValidBST())
                return false;

            // tree with only one subtree being valid, other subtree has a violation a couple
            // layers deep

            PasswordNode p4Node = new PasswordNode(p4);
            p7Node = new PasswordNode(p7);
            p3Node = new PasswordNode(p3);
            PasswordNode p6Node = new PasswordNode(p6, null, p7Node);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            if (bst.isValidBST()) {
                System.out
                        .println("isValidBST() says that a tree with only one valid subtree is a" +
                                " valid bst");
                return false;
            }


            // works on valid large tree
            p4Node = new PasswordNode(p4);
            p3Node = new PasswordNode(p3);
            p7Node = new PasswordNode(p7);
            p6Node = new PasswordNode(p6, p7Node, null);
            p5Node = new PasswordNode(p5, null, p6Node);
            p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;
//            System.out.println("THis run: " + (bst.isValidBST()));
            if (!bst.isValidBST()) {
                System.out.println("this fails");
                return false;
            }

            PasswordNode one = new PasswordNode(p4);
            PasswordNode three = new PasswordNode(p3, one, null);
            PasswordNode two = new PasswordNode(p2, null, three);
            bst.root = two;

            if (bst.isValidBST()) {
                System.out.println("bad bst is valid");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // STUDENT TODO: add javadoc

    /**
     * Validates the lookup() method in PasswordStorage through edge case and valid cases
     *
     * @return true if the lookup() method of PasswordStorage work as expected, false otherwise
     */
    public static boolean testLookup() {
        try {
            Password p = new Password("1234567890", 1000);
            Password p2 = new Password("test", 500); // same occurrences
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);
            Password p8 = new Password("no_key", 3029); // invalid key
            Password p9 = new Password("copy_password", 500); // same occurrences

            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
            PasswordNode p4Node = new PasswordNode(p4);
            PasswordNode p3Node = new PasswordNode(p3);
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p9Node = new PasswordNode(p9);
            PasswordNode p6Node = new PasswordNode(p6, p7Node, p9Node);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            PasswordNode rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            // (1) valid test on the right subtree
            {
                Password expected = p6;
                Password actual = bst.lookup(p6);
                if (expected.compareTo(actual, Attribute.OCCURRENCE) != 0) {
                    System.out.println(actual);
                    return false;
                }
            }

            // (2) valid test on the left subtree
            {
                Password expected = p3;
                Password actual = bst.lookup(p3);
                if (expected.compareTo(actual, Attribute.OCCURRENCE) != 0) {
                    System.out.println(actual);
                    return false;
                }
            }

            // (3) invalid test
            {
                Password expected = null;
                Password actual = bst.lookup(p8);
                if (expected != (actual)) {
                    System.out.println(actual);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Validates the addPassword() method in PasswordStorage valid cases
     *
     * @return true if the addPassword() method of PasswordStorage work as expected, false
     * otherwise
     */
    // STUDENT TODO: add javadoc
    public static boolean testAddPassword() {
        try {
            Password p = new Password("1234567890", 1000);
            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090); // same occurrences
            Password p8 = new Password("no_key", 3029);
            Password p9 = new Password("copy_password", 2090); // same occurrences
            Password p0 = new Password("a@1", 100);

            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
            PasswordNode p4Node = new PasswordNode(p4);
            PasswordNode p3Node = new PasswordNode(p3);
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            PasswordNode rootNode = new PasswordNode(p, p2Node, p5Node);
//        bst.addPassword(p0);

            // (1) valid test with occurrences
            {
                bst.root = rootNode;
                bst.addPassword(p8);
                String expected = "qwerty(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]\n" +
                        "test(a94a8fe5ccb19ba61c4c0873d391e987982fbbd3): 500 [5.75]\n" +
                        "iloveyou(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]\n" +
                        "1234567890(01b307acba4f54f55aafc33bb06bbbf6ca803e9a): 1000 [11.75]\n" +
                        "admin(d033e22ae348aeb5660fc2140aec35850c4da997): 1002 [6.75]\n" +
                        "abc123(6367c48dd193d56ea7b0baad25b19455e529f5ee): 2090 [9.5]\n" +
                        "password(5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8): 2232 [9.75]\n" +
                        "no_key(7cbdb877a3c15af687476aeeaa472150154ca2fc): 3029 [9.5]\n";

                String actual = bst.toString();
                if (!bst.isValidBST() || !actual.equals(expected)) {
                    return false;
                }
            }

            // (2) valid test with strength rating
            {
                PasswordStorage strengthBST = new PasswordStorage(Attribute.STRENGTH_RATING);

                PasswordNode strengthPNode = new PasswordNode(p);
                PasswordNode strengthP2Node = new PasswordNode(p2);
                PasswordNode strengthP4Node = new PasswordNode(p4);
                PasswordNode strengthP3Node = new PasswordNode(p3, null, strengthPNode);

                PasswordNode strengthP5Node = new PasswordNode(p5, strengthP2Node, strengthP4Node);
                PasswordNode strengthRootNode = new PasswordNode(p7, strengthP5Node,
                        strengthP3Node);
                strengthBST.root = strengthRootNode;

                strengthBST.addPassword(p0);
                String expected = "test(a94a8fe5ccb19ba61c4c0873d391e987982fbbd3): 500 [5.75]\n" +
                        "admin(d033e22ae348aeb5660fc2140aec35850c4da997): 1002 [6.75]\n" +
                        "qwerty(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]\n" +
                        "a@1(371f5eae52c86d1843a304cb5f08973f8e9b41f4): 100 [8.25]\n" +
                        "abc123(6367c48dd193d56ea7b0baad25b19455e529f5ee): 2090 [9.5]\n" +
                        "iloveyou(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]\n" +
                        "1234567890(01b307acba4f54f55aafc33bb06bbbf6ca803e9a): 1000 [11.75]\n";
                String actual = strengthBST.toString();
                if (!strengthBST.isValidBST() || !expected.equals(actual)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true; // TODO
    }


    /**
     * Validates the removePassword() method in PasswordStorage valid cases
     *
     * @return true if the removePassword() method of PasswordStorage work as expected, false
     * otherwise
     */
    // STUDENT TODO: add javadoc
    public static boolean testRemovePassword() {
        try {
            Password p = new Password("1234567890", 1000);
            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090); // same occurrences
            Password p8 = new Password("no_key", 3029);
            Password p9 = new Password("copy_password", 2090); // same occurrences
            Password p0 = new Password("no_key", 100);
            // (1) valid test with occurrences
            {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                PasswordNode p4Node = new PasswordNode(p4);
                PasswordNode p3Node = new PasswordNode(p3);
                PasswordNode p7Node = new PasswordNode(p7);
                PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
                PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
                PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
                PasswordNode rootNode = new PasswordNode(p, p2Node, p5Node);
                bst.root = rootNode;

                // delete root
                bst.removePassword(p);
                String expected = "qwerty(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]\n" +
                        "test(a94a8fe5ccb19ba61c4c0873d391e987982fbbd3): 500 [5.75]\n" +
                        "iloveyou(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]\n" +
                        "admin(d033e22ae348aeb5660fc2140aec35850c4da997): 1002 [6.75]\n" +
                        "abc123(6367c48dd193d56ea7b0baad25b19455e529f5ee): 2090 [9.5]\n" +
                        "password(5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8): 2232 [9.75]\n";
                String actual = bst.toString();
                if (!bst.isValidBST() || !expected.equals(actual)) {
                    return false;
                }
            }

            // (2) valid test with occurrences
            {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                PasswordNode p4Node = new PasswordNode(p4);
                PasswordNode p3Node = new PasswordNode(p3);
                PasswordNode p7Node = new PasswordNode(p7);
                PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
                PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
                PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
                PasswordNode rootNode = new PasswordNode(p, p2Node, p5Node);
                bst.root = rootNode;

                // delete node
                bst.removePassword(p2);
                String expected = "qwerty(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]\n" +
                        "iloveyou(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]\n" +
                        "1234567890(01b307acba4f54f55aafc33bb06bbbf6ca803e9a): 1000 [11.75]\n" +
                        "admin(d033e22ae348aeb5660fc2140aec35850c4da997): 1002 [6.75]\n" +
                        "abc123(6367c48dd193d56ea7b0baad25b19455e529f5ee): 2090 [9.5]\n" +
                        "password(5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8): 2232 [9.75]\n";
                String actual = bst.toString();
                if (!bst.isValidBST() || !expected.equals(actual)) {
                    return false;
                }
            }

            // (3) valid test with occurrences
            {
                PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
                PasswordNode p4Node = new PasswordNode(p4);
                PasswordNode p3Node = new PasswordNode(p3);
                PasswordNode p7Node = new PasswordNode(p7);
                PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
                PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
                PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
                PasswordNode rootNode = new PasswordNode(p, p2Node, p5Node);
                bst.root = rootNode;

                // delete node
                bst.removePassword(p6);
                String expected = "qwerty(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]\n" +
                        "test(a94a8fe5ccb19ba61c4c0873d391e987982fbbd3): 500 [5.75]\n" +
                        "iloveyou(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]\n" +
                        "1234567890(01b307acba4f54f55aafc33bb06bbbf6ca803e9a): 1000 [11.75]\n" +
                        "admin(d033e22ae348aeb5660fc2140aec35850c4da997): 1002 [6.75]\n" +
                        "abc123(6367c48dd193d56ea7b0baad25b19455e529f5ee): 2090 [9.5]\n";
                String actual = bst.toString();
                if (!bst.isValidBST() || !expected.equals(actual)) {
                    System.out.println(actual);
                    return false;
                }
            }

            // (4) valid test with strength rating
            {
                PasswordStorage strengthBST = new PasswordStorage(Attribute.STRENGTH_RATING);

                PasswordNode strengthPNode = new PasswordNode(p);
                PasswordNode strengthP2Node = new PasswordNode(p2);
                PasswordNode strengthP4Node = new PasswordNode(p4);
                PasswordNode strengthP3Node = new PasswordNode(p3, null, strengthPNode);

                PasswordNode strengthP5Node = new PasswordNode(p5, strengthP2Node, strengthP4Node);
                PasswordNode strengthRootNode = new PasswordNode(p7, strengthP5Node,
                        strengthP3Node);
                strengthBST.root = strengthRootNode;

                strengthBST.removePassword(p2);

                String expected = "admin(d033e22ae348aeb5660fc2140aec35850c4da997): 1002 [6.75]\n" +
                        "qwerty(b1b3773a05c0ed0176787a4f1574ff0075f7521e): 250 [7.75]\n" +
                        "abc123(6367c48dd193d56ea7b0baad25b19455e529f5ee): 2090 [9.5]\n" +
                        "iloveyou(ee8d8728f435fd550f83852aabab5234ce1da528): 765 [9.75]\n" +
                        "1234567890(01b307acba4f54f55aafc33bb06bbbf6ca803e9a): 1000 [11.75]\n";
                String actual = strengthBST.toString();
                if (!strengthBST.isValidBST() || !expected.equals(actual)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true; // TODO
    }

    /**
     * Main method to run all the tests that test the methods of the Password, PasswordStorage,
     * and PasswordNode classes
     * @param args unused
     */
    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * Tests the methods of the Password, PasswordStorage, and PasswordNode classes
     * @return
     */
    public static boolean runAllTests() {
        boolean compareToPassed = testPasswordCompareTo();
        boolean nodeStatusPassed = testNodeStatusMethods();
        boolean basicMethodsPassed = testBasicPasswordStorageMethods();
        boolean toStringPassed = testToString();
        boolean isValidBSTPassed = testIsValidBST();
        boolean lookupPassed = testLookup();
        boolean addPasswordPassed = testAddPassword();
        boolean removePasswordPassed = testRemovePassword();

        System.out.println("Password compareTo: " + (compareToPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordNode Status Methods: " + (nodeStatusPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage Basic Methods: " + (basicMethodsPassed ? "PASS" :
                "FAIL"));
        System.out.println("PasswordStorage toString: " + (toStringPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage isValidBST: " + (isValidBSTPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage lookup: " + (lookupPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage addPassword: " + (addPasswordPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage removePassword: " + (removePasswordPassed ? "PASS" :
                "FAIL"));

        // AND ANY OTHER ADDITIONAL TEST METHODS YOU MAY WANT TO WRITE!

        return compareToPassed && nodeStatusPassed && basicMethodsPassed && toStringPassed
                && isValidBSTPassed && lookupPassed && addPasswordPassed && removePasswordPassed;
    }

}
