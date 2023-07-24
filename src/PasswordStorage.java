//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Storage for the passwords as a binary search tree
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

public class PasswordStorage {

    protected PasswordNode root; //the root of this BST that contains passwords
    private int size; //how many passwords are in the BST
    private final Attribute COMPARISON_CRITERIA; //what password information to use to determine
    // order in the tree

    /**
     * Constructor that creates an empty BST and sets the comparison criteria.
     *
     * @param comparisonCriteria, the Attribute that will be used to determine order in the tree
     */
    public PasswordStorage(Attribute comparisonCriteria) {
        //TODO
        this.COMPARISON_CRITERIA = comparisonCriteria;
        this.size = 0;
//        this.root = new PasswordNode(new Password("", 0));
    }

    /**
     * Getter for this BST's criteria for determining order in the three
     *
     * @return the Attribute that is being used to make comparisons in the tree
     */
    public Attribute getComparisonCriteria() {
        return this.COMPARISON_CRITERIA; //TODO
    }

    /**
     * Getter for this BST's size.
     *
     * @return the size of this tree
     */
    public int size() {
        return this.size; //TODO
    }

    /**
     * Determines whether or not this tree is empty.
     *
     * @return true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0; //TODO
    }

    /**
     * Provides in-order String representation of this BST, with each Password on its own line. The
     * String representation ends with a newline character ('\n')
     *
     * @return this BST as a string
     */
    @Override
    public String toString() {
        return toStringHelper(this.root);
    }

    /**
     * Recursive method the uses an in-order traversal to create the string representation of
     * this tree.
     *
     * @param currentNode, the root of the current tree
     * @return the in-order String representation of the tree rooted at current node
     */
    private String toStringHelper(PasswordNode currentNode) {
        //THIS MUST BE IMPLEMENTED RECURSIVELY
        if (currentNode == null) {
            return "";
        }
        return toStringHelper(currentNode.getLeft()) + currentNode.getPassword() + "\n" +
                toStringHelper(currentNode.getRight());
    }

    /**
     * Determines whether or not this tree is actually a valid BST.
     *
     * @return true if it is a BST, false otherwise
     */
    public boolean isValidBST() {
        return isValidBSTHelper(this.root, Password.getMinPassword(), Password.getMaxPassword());
    }

    /**
     * Recurisvely determines if the the tree rooted at the current node is a valid BST. That is,
     * every value to the left of currentNode is "less than" the value in currentNode and every
     * value to the right of it is "greater than" it.
     *
     * @param currentNode, the root node of the current tree
     * @param lowerBound,  the smallest possible password
     * @param upperBound,  the largest possible password
     * @return true if the tree rooted at currentNode is a BST, false otherwise
     */
    private boolean isValidBSTHelper(PasswordNode currentNode, Password lowerBound,
                                     Password upperBound) {
        //MUST BE IMPLEMENTED RECURSIVELY
        boolean result = false;
        boolean leftResult = false;
        boolean rightResult = false;

        // BASE CASE 1: the tree rooted at currentNode is empty, which does not violate any BST
        // rules
        if (currentNode == null) {
            return true;
        }

        // BASE CASE 2: the current Password is outside of the upper OR lower bound for this
        // subtree, which is against the rules for a valid BST
        if (currentNode.getPassword().compareTo(lowerBound, COMPARISON_CRITERIA) < 0 ||
                currentNode.getPassword().compareTo(upperBound, COMPARISON_CRITERIA) > 0) {
            return false;
        }

        // If we do not have a base case situation, we must use recursion to verify currentNode's
        // child subtrees

        // RECURSIVE CASE 1: Check that the left subtree contains only Passwords greater than
        // lowerBound and less than currentNode's Password; return false if this property is NOT
        // satisfied
        PasswordNode leftNode = currentNode.getLeft();
        leftResult = isValidBSTHelper(leftNode, lowerBound, currentNode.getPassword());

        // RECURSIVE CASE 2: Check that the right subtree contains only Passwords greater than
        // currentNode's Password and less than upperBound; return false if this property is NOT
        // satisfied
        PasswordNode rightNode = currentNode.getRight();
        rightResult = isValidBSTHelper(rightNode, currentNode.getPassword(), upperBound);
        // COMBINE RECURSIVE CASE ANSWERS: this is a valid BST if and only if both case 1 and 2
        // are valid
        return leftResult && rightResult; // NOTE: this will return at the end of that stack's
        // recursion (ie: will return true if the currentNode (the current root which literally
        // can be any root in the binary tree) satisfies its condition for a binary search tree)
        // (it got its one)
    }

    /**
     * Returns the password that matches the criteria of the provided key.
     * Ex. if the COMPARISON CRITERIA is OCCURRENCE and the key has an occurrence of 10
     * it will return the password stored in the tree with occurrence of 10
     *
     * @param key, the password that contains the information for the password we are searching for
     * @return the Password that matches the search criteria, if it does not exist in the tree it
     * this will be null
     */
    public Password lookup(Password key) {
        return lookupHelper(key, root);
    }

    /**
     * Recursive helper method to find the matching password in this BST
     *
     * @param key,         password containing the information we are searching for
     * @param currentNode, the node that is the current root of the tree
     * @return the Password that matches the search criteria, if it does not exist in the tree it
     * this will be null
     */
    private Password lookupHelper(Password key, PasswordNode currentNode) {
        //THIS MUST BE IMPLEMENTED RECURSIVELY
        Password foundKey = null;

        // BASE CASE 1: there is no key to be found in the currentNode after traversing through
        // the tree's root
        if (currentNode == null) {
            return null;
        }

        // BASE CASE 2: the currentNode is equal to the key, return that current node
        if (currentNode.getPassword().compareTo(key, COMPARISON_CRITERIA) == 0) {
            return currentNode.getPassword();
        }

        // RECURSIVE CASE 1: Check that if the left subtree contains the key; return null if
        // there is NO key to be found in the left subtree
        if (currentNode.getPassword().compareTo(key, COMPARISON_CRITERIA) > 0) {
            return lookupHelper(key, currentNode.getLeft());
        }

        // RECURSIVE CASE 2: Check that if the right subtree contains the key; return null if
        // there is NO key to be found in the right subtree
        return lookupHelper(key, currentNode.getRight()); //TODO
    }

    /**
     * Returns the best (max) password in this BST
     *
     * @return the best password in this BST
     * @throws NoSuchElementException if the BST is empty
     */
    public Password getBestPassword() throws NoSuchElementException {
//        You can assume that when calling best or worst on the tree it is already a BST
        if (isEmpty()) {
            throw new NoSuchElementException("BST is empty");
        }
        PasswordNode temp = root;
        while (temp.getRight() != null) {
            temp = temp.getRight();
        }
//        if(root.getPassword().compareTo(root.getLeft().getPassword(), COMPARISON_CRITERIA) < 0){
//
//        }
        return temp.getPassword(); //TODO
    }

    /**
     * Returns the worst password in this BST
     *
     * @return the worst password in this BST
     * @throws NoSuchElementException if the BST is empty
     */
    public Password getWorstPassword() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("BST is empty");
        }

        PasswordNode temp = root;
        while (temp.getLeft() != null) {
            temp = temp.getLeft();
        }
//        if(root.getPassword().compareTo(root.getLeft().getPassword(), COMPARISON_CRITERIA) < 0){
//
//        }
        return temp.getPassword(); //TODO
    }

    /**
     * Adds the Password to this BST.
     *
     * @param toAdd, the password to be added to the tree
     * @throws IllegalArgumentException if the password object is already in the tree
     */
    public void addPassword(Password toAdd) throws IllegalArgumentException {
        // if the lookup returns something other than null, that means it found a password with
        // the same comparison attribute, and thus a password object that is already in the tree
        if (lookup(toAdd) != null) {
            throw new IllegalArgumentException("Matching password object is already in the tree");
        }
        PasswordNode node = new PasswordNode(toAdd);
        addPasswordHelper(toAdd, node);
        size++;
        //TODO
    }

    /**
     * Recursive helper that traverses the tree and adds the password where it belongs
     *
     * @param toAdd,       the password to add to the tree
     * @param currentNode, the node that is the current root of the (sub)tree
     * @return true if it was successfully added, false otherwise
     */
    private boolean addPasswordHelper(Password toAdd, PasswordNode currentNode) {
        //THIS MUST BE IMPLEMENTED RECURSIVELY
        boolean addedResult = false;
        PasswordNode node = new PasswordNode(toAdd);
        // BASE CASE 1: there is no node at the currentNode, which is fine to add a new node at
        if (currentNode == null) {
//            currentNode = new PasswordNode(toAdd);
            return true;
        }

//        // BASE CASE 2: the currentNode is equal to the key, return that current node
//        if(toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA)){
//            return false;
//        }

        // RECURSIVE CASE 1: Check that if the left subtree contains the key; return null if
        // there is NO key to be found in the left subtree
        if (currentNode.getPassword().compareTo(toAdd, COMPARISON_CRITERIA) > 0) {
            addedResult = addPasswordHelper(toAdd, currentNode.getLeft()); // this is a condition
            // to test you can add a node to the left side of the currentNode (essentially
            // finding a leaf node)
            if (addedResult) {
                currentNode.setLeft(node);
            }
        }

        // RECURSIVE CASE 2: Check that if the right subtree contains the key; return null if
        // there is NO key to be found in the right subtree
        else if (currentNode.getPassword().compareTo(toAdd, COMPARISON_CRITERIA) < 0) {
            // comparison is need to ensure that the left side of the currentNode is empty and is
            // less than the currentNode in order to add the new node
            boolean rightAddedResult = addPasswordHelper(toAdd, currentNode.getRight());
            if (rightAddedResult) {
                currentNode.setRight(node);
            }
        }

        return false; //TODO
    }

    /**
     * Removes the matching password from the tree
     *
     * @param toRemove, the password to be removed from the tree
     * @throws NoSuchElementException if the password is not in the tree
     */
    public void removePassword(Password toRemove) throws NoSuchElementException {
        if (lookup(toRemove) == null) {
            throw new NoSuchElementException("Password is not in the tree");
        }

        root = removePasswordHelper(toRemove, this.root);
        size--;
        //TODO
    }

    /**
     * Recursive helper method to that removes the password from this BST.
     *
     * @param toRemove,    the password to be removed from the tree
     * @param currentNode, the root of the tree we are removing from
     * @return the PasswordNode representing the NEW root of this subtree now that toRemove
     * has been removed. This may still be currentNode, or it may have changed!
     */
    private PasswordNode removePasswordHelper(Password toRemove, PasswordNode currentNode) {
        //TODO: MUST BE IMPLEMENTED RECURSIVELY
        PasswordNode node = new PasswordNode(toRemove);

        //BASE CASE: current tree is empty
        if (currentNode == null) {
            return currentNode;
        }

        //RECURSIVE CASE: toRemove is in the left subtree, continue searching

        // if the node's password to remove is less than the root's password, traverse down the
        // left subtree
        if (toRemove.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) < 0) {
            // reach the leaf node's left child and remove the
            currentNode.setLeft(removePasswordHelper(toRemove, currentNode.getLeft()));
        }

//        boolean isLeftSubtree = currentNode.getPassword().compareTo(toRemove,
//                COMPARISON_CRITERIA) == 0 && removePasswordHelper(toRemove,
//                currentNode.getLeft()) == null;

        //RECURSIVE CASE: toRemove is in the right subtree, continue searching

        // if the node's password to remove is greater than the root's password, traverse down the
        // right subtree
        else if (toRemove.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) > 0) {
            // reach the leaf node's left child and remove the
            currentNode.setRight(removePasswordHelper(toRemove, currentNode.getRight()));
        }
//        boolean isRightSubtree = currentNode.getPassword().compareTo(toRemove,
//                COMPARISON_CRITERIA) == 0 && removePasswordHelper(toRemove,
//                currentNode.getRight()) == null;

        //otherwise we found the node to remove!

        else {
            //BASE CASE: current node has no children
            if (currentNode.isLeafNode()) {
                return null;
            }

            //BASE CASE(S): current node has one child (one for the left and right respectively)
            if (currentNode.getLeft() == null) {
                return currentNode.getRight();
            } else if (currentNode.getRight() == null) {
                return currentNode.getLeft();
            }
            //RECURSIVE CASE: currentNode has 2 children
            //1)Find the predecessor password [HINT: Write a private helper method!]
            Password predecessorPassword = findPredecessor(currentNode.getLeft());
            //2)Make new node for the predecessor password. It should have same left and right
            // subtree as the current node.
            PasswordNode predecessorNode = new PasswordNode(predecessorPassword);
            predecessorNode.setLeft(currentNode.getLeft());
            predecessorNode.setRight(currentNode.getRight());

//            System.out.println(predecessorNode.getLeft().getPassword());
//            System.out.println(predecessorNode.getRight().getPassword() + "\n");
            //3)Replace currentNode with the new predecessor node
            currentNode = predecessorNode;

//            System.out.println("current node: " + currentNode.getPassword());
            //4)Remove the (duplicate) predecessor from the current tree and update the left
            // subtree
            currentNode.setLeft(removePasswordHelper(currentNode.getPassword(), currentNode.getLeft()
            ));
        }
        return currentNode; //LEAVE THIS LINE AS IS
    }

    /**
     * Finds the predecessor through the left subtree
     *
     * @param currentNode the root of the tree we are removing from
     * @return the predecessor node
     */
    private Password findPredecessor(PasswordNode currentNode) {
        Password predecessorPassword = currentNode.getPassword();
        while (currentNode.getRight() != null) {
            predecessorPassword = currentNode.getRight().getPassword();
            currentNode = currentNode.getRight();
        }
//        System.out.println("This asdfasdf " +predecessorPassword);
        return predecessorPassword;
    }
}
