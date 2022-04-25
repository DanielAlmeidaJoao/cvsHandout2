import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class IntervalTree {

    private int len;
    private int count;
    private int [] tree;
    public IntervalTree(int n){
        this.tree = new int [2*n-1];
        len = n;
        count = 0;
    }

    /**
     *
     * @param parentIndex < len
     * @return
     */
    public int leftChild(int parentIndex){
        if(parentIndex>(len-1)){
            return 0;
        }
        assert(parentIndex >= 0 && parentIndex < len);
        return tree[2*parentIndex+1];
    }

    /**
     *
     * @param parentIndex < len
     * @return
     */
    public int rightChild(int parentIndex){
        if(parentIndex>(len-1)){
            return 0;
        }
        assert(parentIndex >= 0 && parentIndex < len);
        return tree[2*parentIndex+2];
    }

    /**
     *
     * @param childIndex > 0, is not root
     * @return
     */
    public int parent(int childIndex){
        assert (childIndex > 0 && childIndex < tree.length);
        return tree[(childIndex-1)/2];
    }
    /**
     * the i-th element of the sequence of the array
     * the first (n-1) correspond to the non leaves
     * @param index
     * @return
     */
    public int getElement(int index){
        assert(index>=0 && index<len);
        return tree[index+(len-1)];
    }
    /**
     * tree[i] == leftChild(i)+rightChild(i)
     * index of the sequence
     */
    public void update(int index, int value){
        assert (index>=0 && index<len);
        int treeIndex = index+(len-1);
        tree[0] += value;
        while (treeIndex >0){
            tree[treeIndex] += value;
            assert (tree[treeIndex]==leftChild(treeIndex)+rightChild(treeIndex));
            treeIndex = (treeIndex-1)/2;
        }
    }
    public int queryPredicate(int a, int b){
        a = a+(len-1);
        b = b+(len-1);
        int total = 0;
        while(a<b){
            total += tree[a];
            a++;
        }
        return total;
    }
    public int query(int a, int b){
        assert(a>b);
        int startA = a;
        int startB = b;
        int ra = a + len-1;
        int rb = b + len-1;
        int total = 0;
        while (ra!=rb){
            if(ra%2==0){
                total += tree[ra];
            }
            if(rb%2==0){
                total += tree[rb-1];
            }
            ra = ra/2;
            rb = (rb-1)/2;
        }
        assert (total == queryPredicate(startA,startB));
        return total;
    }

    public int[] getTree() {
        return tree;
    }

    public static void main(String [] args){
        System.out.println("STARTED j");
        /**
        IntervalTree it = new IntervalTree(8);
        it.update(0,10);
        it.update(1,3);
        it.update(2,5);
        it.update(3,1);

        it.update(4,8);
        it.update(5,4);
        it.update(6,2);
        it.update(7,7);
        System.out.println(Arrays.toString(it.getTree()));
        System.out.println("ENDED");
        System.out.println("QUERY RANGE "+it.query(1,7));
        System.out.println("QUERY RANGE PREDICATE "+it.queryPredicate(1,7));
        **/

        Random rd = new Random();
        int len = 1+rd.nextInt(10);
        System.out.println(len);
        int allSum = 0;
        IntervalTree it = new IntervalTree(len);
        for (int i = 0; i < len; i++) {
            int val = rd.nextInt(1000);
            it.update(i,val);
            allSum += val;
        }
        assert (it.getTree()[0]==allSum);
        testQuerySum(it,len);
        System.out.println("ENDED WITH SUCCESS! "+allSum);
    }

    public static void testQuerySum(IntervalTree it, int len){
        Random rd = new Random();
        for (int i = 0; i < 5; i++) {
            int a = rd.nextInt(len-1);
            int b = rd.nextInt(a+1,len);
            System.out.println(it.query(a,b));
        }
    }
}
