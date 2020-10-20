package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktoor
    {
        rot = null;
        antall = 0;
        comp = c;
    }
    
    private static final class Node<T>{     // en indre nodeklasse
        private T verdi;                    // nodens verdi
        private Node<T> venstre, hooyre;    // venstre og hooyre barn
        private Node<T> forelder;           // forelder

        // konstruktoor
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            hooyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder){  // konstruktoor
       
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    /**
     * Oppgave 1
     * @param verdi
     * @return
     */
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> currentNodeWeStandOn = rot;
        Node<T> nextNode = null;
        int compareValue = 0;

        while(currentNodeWeStandOn != null){
            nextNode = currentNodeWeStandOn;
            compareValue = comp.compare(verdi, currentNodeWeStandOn.verdi);
            if(compareValue < 0){
                currentNodeWeStandOn = currentNodeWeStandOn.venstre;
            }else{
                currentNodeWeStandOn = currentNodeWeStandOn.hooyre;
            }
        }

        //ute av while
        currentNodeWeStandOn = new Node<T>(verdi, nextNode);
        if(nextNode == null) rot = currentNodeWeStandOn;
        else if(compareValue < 0) nextNode.venstre = currentNodeWeStandOn;
        else nextNode.hooyre = currentNodeWeStandOn;

        System.out.print(currentNodeWeStandOn.verdi + " ");

        antall++;
        return true;

    }

    /**
     * Oppgave 2
     * @param verdi
     * @return
     */
    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.hooyre;
            else return true;
        }

        return false;
    }

    /**
     * Oppgave 2
     * @return
     */
    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = foorstePostorden(rot); // gaar til den foorste i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    /**
     * Oppgave 2
     * @return
     */
    public boolean tom() {
        return antall == 0;
    }

    /**
     * Oppgave 2
     * @param verdi
     * @return
     */
    public int antall(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 3
     * @param p
     * @param <T>
     * @return
     */
    private static <T> Node<T> foorstePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 3
     * @param p
     * @param <T>
     * @return
     */
    private static <T> Node<T> nestePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 4
     * @param oppgave
     */
    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 4
     * @param oppgave
     */
    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }


    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 5
     * @return
     */
    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 5
     * @param data
     * @param c
     * @param <K>
     * @return
     */
    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 6
     */
    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 6
     * @param verdi
     * @return
     */
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 6
     * @param verdi
     * @return
     */
    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }


    public static void main(String[] args) {

/*
        //Oppgave 0
        EksamenSBinTre<String> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(tre.antall());

        EksamenSBinTre<Integer> treInt = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treInt.antall());
        EksamenSBinTre<Character> treChar = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treChar.antall());
        EksamenSBinTre<Double> treDouble = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treDouble.antall());
*/


        //Oppgave 1
        Integer[] a = {4,7,2,9,4,10,8,1,4,6};
        EksamenSBinTre<Integer> treOppg1 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi: a) treOppg1.leggInn(verdi);
        System.out.println();
        System.out.println(treOppg1.antall());
    }


} // ObligSBinTre
