public class PathFromRoot {
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
