package no.oslomet.cs.algdat.Eksamen;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

public class EksamenSBinTre<T> {
    
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

    /**
     * Oppgave 1
     * @param verdi
     * @return
     */
    public boolean leggInn(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
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
        EksamenSBinTre<String> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(tre.antall());

        EksamenSBinTre<Integer> treInt = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treInt.antall());
        EksamenSBinTre<Character> treChar = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treChar.antall());
        EksamenSBinTre<Double> treDouble = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treDouble.antall());

    }


} // ObligSBinTre
