// Hlutir af tagi V12String eru pokar gilda af tagi E.
public class V12String implements Bag<String>
{
    private AVLTreeNode<String> tree;
    private int size;
    // Fastayrðing gagna:
    //    HÉR VANTAR
    // s.s. fastayrðirng fyrir tree og size, loforð um hvað það má uppfylla, ástamd gagnannar þegar
    // komið er inn í aðgerðu og út úr henni
    // tala um poka, hluturinn er poki sem inniheldur einhver gili, abstract gildi
    // í þessu tilviki er einhver concrete útfærsla á þessu tilviki
    // það er það sem fastayrðing gagna gerir


    // Notkun: int h = height(e);
    // Fyrir:  t er AVL tré.
    // Eftir:  h er hæð trésins.
    private static int height( AVLTreeNode<?> t )
    {
        if( t==null ) return 0;
        return t.height;
    }

    // Notkun: int h = height(left,right);
    // Fyrir:  left og right eru AVL tré.
    // Eftir:  h er hæð trés sem hefur rót sem hefur left sem vinstra
    //         undirtré og right sem hægra undirtré.
    private static int height( AVLTreeNode<?> left, AVLTreeNode<?> right )
    {
        int lh = height(left);
        int rh = height(right);
        if( lh>rh )
            return 1+lh;
        else
            return 1+rh;
    }

    // Notkun: t2 = insert(t1,x);
    // Fyrir:  t1 er AVL tré, x er ekki null.
    // Eftir:  t2 er AVl tré sem inniheldur alla hnúta úr t1 og einn
    //         hnút í viðbót sem hefur gildið x.
    private static AVLTreeNode<String> insert( AVLTreeNode<String> t, String x )
    {
        if( t==null )
        {
            t = new AVLTreeNode<String>();
            t.height = 1;
            t.val = x;
            return t;
        }
        int c=x.compareTo(t.val);
        if( c<0 )
        {
            t.left = insert(t.left,x);
            if( height(t.left) > height(t.right)+1 )
            {
                if( height(t.left.left) > height(t.left.right) )
                    t = upL(t);
                else
                    t = upLR(t);
            }
            else
                t.height = height(t.left,t.right);
        }
        else
        {
            t.right = insert(t.right,x);
            if( height(t.right) > height(t.left)+1 )
            {
                if( height(t.right.right) > height(t.right.left) )
                    t = upR(t);
                else
                    t = upRL(t);
            }
            else
                t.height = height(t.left,t.right);
        }
        return t;
    }

    // Notkun: t2 = delete(t1,x);
    // Fyrir:  t1 er AVL tré, x er ekki null.
    // Eftir:  t2 er AVL tré sem inniheldur alla hnúta úr t1,
    //         nema hvað ef einhver hnútur t1 innihélt x þá
    //         hefur einn slíkur hnútur verið fjarlægður.
    private static AVLTreeNode<String> delete( AVLTreeNode<String> t, String x )
    {
        if( t==null ) return  null;
        int c = x.compareTo(t.val);
        if( c<0 )
        {
            t.left = delete(t.left,x);
            if( height(t.left) < height(t.right)-1 )
            {
                if( height(t.right.left) > height(t.right.right) )
                    t = upRL(t);
                else
                    t = upR(t);
            }
            else
                t.height = height(t.left,t.right);
            return t;
        }
        if( c>0 )
        {
            t.right = delete(t.right,x);
            if( height(t.right) < height(t.left)-1 )
            {
                if( height(t.left.left) > height(t.left.right) )
                    t = upL(t);
                else
                    t = upLR(t);
            }
            else
                t.height = height(t.left,t.right);
            return t;
        }
        if( t.left==null ) return t.right;
        if( t.right==null ) return t.left;
        if( t.left.height > t.right.height )
        {
            String m = max(t.left);
            t.left = delete(t.left,m);
            t.val = m;
        }
        else
        {
            String m = min(t.right);
            t.right = delete(t.right,m);
            t.val = m;
        }
        t.height = height(t.left,t.right);
        return t;
    }

    // Notkun: t2 = search(t1,x);
    // Fyrir:  t1 er AVL tré, x er ekki null.
    // Eftir:  t2 vísar á hnút í t1 sem inniheldur gildið x, ef slíkur
    //         hnútur er tiul.  Annars er t1 null.
    private static AVLTreeNode<String> search( AVLTreeNode<String> t, String x )
    {
        if( t==null ) return null;
        int c = x.compareTo(t.val);
        if( c==0 ) return t;
        if( c<0 )
            return search(t.left,x);
        else
            return search(t.right,x);
    }

    // Notkun: t2 = upL(t1);
    // Fyrir:  t1 er tré eins og vinstra tréð í myndinni að neðan.
    //         Allir hnútar í trénu uppfylla tvíleitarskilyrði og
    //         allir hnútar nema e.t.v. rótin uppfylla AVL skilyrði.
    //         Ef AVL skilyrðið er ekki uppfyllt þá er það vegna
    //         þess að vinstra undirtré vinstra undirtrés er of hátt.
    // Eftir:  t2 vísar á AVL tré sem er eins og hægri myndin sýnir.
    //            y             x
    //           / \           / \
    //          x   C   ===>  A   y
    //         / \               / \
    //        A   B             B   C
    private static AVLTreeNode<String> upL( AVLTreeNode<String> t )
    {
        AVLTreeNode<String> x,y,B;
        y = t;
        x = y.left;
        B = x.right;
        x.right = y;
        y.left = B;
        y.height = height(B,y.right);
        x.height = height(x.left,y);
        return x;
    }

    // Notkun: t2 = upR(t1);
    // Fyrir:  t1 er tré eins og vinstra tréð í myndinni að neðan.
    //         Allir hnútar í trénu uppfylla tvíleitarskilyrði og
    //         allir hnútar nema e.t.v. rótin uppfylla AVL skilyrði.
    //         Ef AVL skilyrðið er ekki uppfyllt þá er það vegna
    //         þess að hægra undirtré hægra undirtrés er of hátt.
    // Eftir:  t2 vísar á AVL tré sem er eins og hægri myndin sýnir.
    //          x                y
    //         / \              / \
    //        A   y    ===>    x   C
    //           / \          / \
    //          B   C        A   B
    private static AVLTreeNode<String> upR( AVLTreeNode<String> t )
    {
        AVLTreeNode<String> x,y,B;
        x = t;
        y = x.right;
        B = y.left;
        y.left = x;
        x.right = B;
        x.height = height(x.left,B);
        y.height = height(x,y.right);
        return y;
    }

    // Notkun: t2 = upLR(t1);
    // Fyrir:  t1 er tré eins og vinstra tréð í myndinni að neðan.
    //         Allir hnútar í trénu uppfylla tvíleitarskilyrði og
    //         allir hnútar nema e.t.v. rótin uppfylla AVL skilyrði.
    //         Ef AVL skilyrðið er ekki uppfyllt þá er það vegna
    //         þess að hægra undirtré vinstra undirtrés er of hátt.
    // Eftir:  t2 vísar á AVL tré sem er eins og hægri myndin sýnir.
    //           z                        y
    //          / \                      / \
    //         x   D                    /   \
    //        / \           ===>       x     z
    //       A   y                    / \   / \
    //          / \                  A   B C   D
    //         B   C
    private static AVLTreeNode<String> upLR( AVLTreeNode<String> t )
    {
        t.left = upR(t.left);
        return upL(t);
    }

    // Notkun: t2 = upRL(t1);
    // Fyrir:  t1 er tré eins og vinstra tréð í myndinni að neðan.
    //         Allir hnútar í trénu uppfylla tvíleitarskilyrði og
    //         allir hnútar nema e.t.v. rótin uppfylla AVL skilyrði.
    //         Ef AVL skilyrðið er ekki uppfyllt þá er það vegna
    //         þess að vinstra undirtré hægra undirtrés er of hátt.
    // Eftir:  t2 vísar á AVL tré sem er eins og hægri myndin sýnir.
    //           x                        y
    //          / \                      / \
    //         A   z                    /   \
    //            / \       ===>       x     z
    //           y   D                / \   / \
    //          / \                  A   B C   D
    //         B   C
    private static AVLTreeNode<String> upRL( AVLTreeNode<String> t )
    {
        t.right = upL(t.right);
        return upR(t);
    }

    // Notkun: String m = max(t);
    // Fyrir:  t er AVL tré, ekki tómt.
    // Eftir:  m er stærsta gildi í t.
    private static String max( AVLTreeNode<String> t )
    {
        while( t.right!=null )
            // t vísar á hnút á hægra jaðri trésins
            // sem upphaflega viðfangið vísaði á
            t = t.right;
        return t.val;
    }

    // Notkun: String m = min(t);
    // Fyrir:  t er AVL tré, ekki tómt.
    // Eftir:  m er minnsta gildi í t.
    private static String min( AVLTreeNode<String> t )
    {
        while( t.left!=null )
            // t vísar á hnút á vinstra jaðri trésins
            // sem upphaflega viðfangið vísaði á
            t = t.left;
        return t.val;
    }

    // Notkun: int count = fillInOrder(tree,a,pos);
    // Fyrir:  tree er AVL tré sem inniheldur n hnúta fyrir eitthvert
    //         n>=0.  a[pos..pos+n-1] er svæði í a.
    // Eftir:  Búið er að afrita gildin úr trénu í svæðið a[pos..pos+n-1]
    //         í vaxandi röð (inorder röð).  count er n.
    private static int fillInOrder( AVLTreeNode<String> tree, String[] a, int pos )
    {
        // HÉR VANTAR
        return 5;
    }

    // Notkun: V12String p = new V12String();
    // Eftir:  p vísar á nýjan tóman poka gilda af tagi E.
    public V12String()
    {
        //hér vantar
        size = 0;
        tree = null;
    }

    public void add( String x )
    {
        if (x != null) {
            insert(tree, x);
        }
        else System.out.println("virkar ekki");
    }


    public void remove( String x )
    {
        if(x != null) {
            delete(tree, x);
        }
        else System.out.println("virkar ekki");
    }

    public boolean contains( String x )
    {
        if(search(tree,x)== null) {return false; }
        else 
           return true;  
    }

    public int size()
    {
        return size;
    }

    public String min()
    {
        return min(tree);
    }

    public String max()
    {
        // HÉR VANTAR
        return max(tree);
    }

    // Hlutir af tagi MyIterator eru flakkarar yfir poka gilda af tagi E.
    private static class MyIterator implements java.util.Iterator<String>
    {
        // HÉR VANTAR TILVIKSBREYTUR
        // frumstilla tilviksbreytur hér, t.d. s = null
        private AVLTreeNode<String> current;


        private String s = null;

       //= "abc";  s= "abc";


        // Fastayrðing gagna:
        //    HÉR VANTAR

        // láta þetta vera
        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }

        // Notkun: boolean b = it.hasNext();
        // Eftir:  b er satt þþaa a.m.k. eitt gildi sé eftir í flakkinu.
        public boolean hasNext()
        {
            // ef current er til, þá er til næsta stak 
            return current!= null;
        } 

        // Notkun: String x = it.next();
        // Fyrir:  A.m.k. eitt gildi er eftir í flakkinu.
        // Eftir:  x vísar á næsta gildi í flakkinu og það (næsta gildið) hefur verið
        //         fjarlægt úr flakkinu.  Þetta var minnsta gildið sem
	    //         eftir var í flakkinu.
        public String next()
        {
            // HÉR VANTAR

            try {
                 if (!hasNext()) {
                     throw new NoSuchElementException();
                 }
            }

            catch (NoSuchElementException ex){
                System.out.println("þetta er ekki að virka -_-");
            }
            

            return "next";
        }
    }

    public java.util.Iterator<String> iterator()
    {
        MyIterator it = new MyIterator();
        // HÉR VANTAR
        String x = it.next();
        boolean k = it.hasNext();
        if (k) {
            System.out.println("blafafafa");
        }


        // gefa tilviksbreytunum gildi hér
        return it;
    }

    // Notkun: main(args);
    // Eftir:  Búið er að skrifa strengina í args í stafrófsröð á
    //         aðalúttak.
    public static void main( String[] args )
    {
        // p er tómur spoki af strengjum
        V12String p = new V12String();
        for( int i=0 ; i!=args.length ; i++ )
       // for( int i=0 ; i==2 ; i++ )
        {
            // p inniheldur strengina args[0..i-1]
            p.add(args[i]);
        }
     


        for( String s: p )
        {
            // Búið er að skrifa núll eða fleiri af strengjunum af
            // skipanalínunni sem eru fremstir í stafrófsröð.  Hinir
            // eru eftir í flakkinu gegnum p.
            System.out.println(s);
        }     
    }
}
