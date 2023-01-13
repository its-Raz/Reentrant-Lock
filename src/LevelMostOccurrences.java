import java.util.ArrayDeque;

public class LevelMostOccurrences {
    public static int getLevelWithMostOccurrences(BinNode<Integer> root, int num) {
        int maxAppearance = 0;
        int maxLevel=-1;
        int q1Level = 0;
       int counter = 0;
        ArrayDeque<ArrayDeque<BinNode>> arrayOfArrays = new ArrayDeque<>(2);
        ArrayDeque<BinNode> q1 = new ArrayDeque<>();
        ArrayDeque<BinNode> q2 = new ArrayDeque<>();
        arrayOfArrays.add(q1);
        arrayOfArrays.add(q2);
        q1.add(root);
        while(!q1.isEmpty())
        {

            for(BinNode node : q1)
            {
                if(node.getData().equals((Integer)num))
                {
                    counter++;
                }
                if(node.getLeft()!=null){q2.add(node.getLeft());}
                if(node.getRight()!= null){q2.add(node.getRight());}
            }
            if(counter > maxAppearance)
            {
                maxAppearance = counter;
                maxLevel = q1Level;
            }
            counter=0;
            arrayOfArrays.pollFirst();
            q1=q2;
            q1Level++;
            q2= new ArrayDeque<>();
            arrayOfArrays.add(q2);
        }
        return maxLevel;
    }
}
