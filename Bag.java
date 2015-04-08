// Tilvik klasa sem uppfylla skilin Bag<E> eru pokar (bag, multiset)
// gilda af tagi E.
public interface Bag<E extends Comparable<? super E>> extends Iterable<E>
{
    // Notkun: s.add(x);
    // Fyrir:  x er ekki null.
    // Eftir:  Búið er að bæta x við s.
    public void add( E x );

    // Notkun: s.remove(x);
    // Fyrir:  x er ekki null.
    // Eftir:  Gildið x hefur verið fjarlægt úr s.
    public void remove( E x );

    // Notkun: boolean b = s.contains(x);
    // Fyrir:  x er ekki null.
    // Eftir:  b er satt þþaa s innihaldi a.m.k. eitt eintak af gildi
    //         sem er jafnt x.
    public boolean contains( E x );

    // Notkun: int n = s.size();
    // Eftir:  n er fjöldi gilda í s.  Ef mörg eintök sama gildis
    //         eru í s þá eru gildin að sama skapi margtalin í gildinu
    //         n.
    public int size();

    // Notkun: E x = s.max();
    // Fyrir:  s er ekki tómt.
    // Eftir:  x er stærsta gildi í s.
    public E max();

    // Notkun: E x = s.min();
    // Fyrir:  s er ekki tómt.
    // Eftir:  x er minnsta gildi í s.
    public E min();

    // Notkun: java.util.Iterator<E> it = p.iterator();
    // Eftir:  it er flakkari (iterator) sem flakkar í gegnum öll
    //         gildi í pokanum p í vaxandi röð, eins oft og hvert gildi
    //         kemur fyrir í p.  Flakkarinn er gildur (valid) þar
    //         til innihaldi p er næst breytt, til dæmis með því
    //         að nota aðgerðirnar add eða remove.
    public java.util.Iterator<E> iterator();
}
