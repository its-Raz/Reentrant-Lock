public class PathFromRoot {
    /**
     * checks if there is a path that begins at the root of the tree for a string that is given
     * when the method is called it checks if the string length equal to 0, if so, its return true,
     * otherwise and if the input node is not null, and the first char equal to the first char of the input string
     * it calls recursively to the method with the string from char number one.
     * example: if the string is "Apple", and there is 'A' in the input node, the method will call itself again
     * with the string "pple".
     * @param root - the checked node
     * @param str - the string to check if a path does exist to create it
     * @return true if there is a path to the input string, otherwise false
     */
    public static boolean doesPathExist(BinNode<Character> root, String str) {
        if(str.length() == 0) { return true;}
        else if (root!=null) {
            if(root.getData() == str.charAt(0))
            {
                return doesPathExist(root.getLeft(),str.substring(1))
                        || doesPathExist(root.getRight(),str.substring(1));
            }
        }
        return false;
    }
}