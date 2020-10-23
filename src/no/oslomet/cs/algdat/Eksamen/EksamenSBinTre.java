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
     * @param verdi - verdi som skal legges inn i treet
     * @return - returnerer true om alt går som det skal,
     * false om verdi lik null.
     */
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> currentNodeWeStandOn = rot;
        Node<T> nextNode = null;
        int compareValue = 0;

        //Så lenge noden vi er på har en verdi, sjekker vi om verdien vi skal sette
        //inn i treet er større eller mindre enn node-verdien vi er på.
        //Er den større, går vi til høyre barnenode
        //Er den mindre, går vi til venstre barnenode
        //Dersom venstre eller høyre barnenode ikke har verdi, går vi ut av
        //while-løkken
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
        //Her opprettes det en ny node i treet
        currentNodeWeStandOn = new Node<>(verdi, nextNode);
        if(nextNode == null) rot = currentNodeWeStandOn;
        else if(compareValue < 0) nextNode.venstre = currentNodeWeStandOn;
        else nextNode.hooyre = currentNodeWeStandOn;

        antall++;
        endringer++;
        return true;
    }

    /**
     * Oppgave 2 (Ferdig kodet)
     * @param verdi - Verdi skal sjekkes om ligger i treet eller ikke
     * @return - returnerer false om verdi ikke finnes eller er lik null,
     * true ellers
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
     * Oppgave 2 (Ferdig kodet)
     * @return - returnerer antall
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
     * Oppgave 2 (Ferdig kodet)
     * @return - returnerer true om antall er lik 0,
     * false ellers
     */
    public boolean tom() {
        return antall == 0;
    }

    /**
     * Oppgave 2
     * @param verdi - verdi som skal sjekkes hvor mange ganger
     *              forekommer i treet.
     * @return - returnerer antall forekomster av verdi i treet
     * Kilde: https://www.cs.hioa.no/~ulfu/appolonius/kap5/2/fasit526.html
     * (Fikk litt hjelp fra kompendium)
     */
    public int antall(T verdi) {
        Node<T> nodeViErPaa = rot;
        int antallForekomster = 0;
        int compareAnswer;

        //Så lenge noden i treet har en verdi, sjekker vi verdien opp mot den
        while(nodeViErPaa != null) {
            compareAnswer = comp.compare(verdi, nodeViErPaa.verdi);

            //Er verdien mindre enn nodens verdi, går vi til venstre barnenode
            if (compareAnswer < 0) nodeViErPaa = nodeViErPaa.venstre;

            //Hvis verdien er lik, legges det til i telleren +1 og
            //vi flytter oss til høyre barnenode
            else{
                if(compareAnswer == 0) antallForekomster++; nodeViErPaa = nodeViErPaa.hooyre;
            }
        }
        return antallForekomster;
    }

    /**
     * Oppgave 3
     * @param p - p er rot
     * @param <T> - generisk datatype
     * @return - skal returnere første node i postorden
     * Inspirasjon programkode 5.1.7 h) : https://www.cs.hioa.no/~ulfu/appolonius/kap5/1/kap51.html#5.1.7
     */
    private static <T> Node<T> foorstePostorden(Node<T> p) {
        while (true) {
            //hvis det finnes et venstrebarn, gå til venstre
            if (p.venstre != null) p = p.venstre;

            //hvis ikke venstre, sjekk om det finnes et høyrebarn, gå til høyre
            else if (p.hooyre != null) p = p.hooyre;

            //hvis ingen av delene, returner noden (den første helt til venstre)
            else return p;
        }
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
  /*      Integer[] a = {4,7,2,9,4,10,8,1,4,6};
        EksamenSBinTre<Integer> treOppg1 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi: a) treOppg1.leggInn(verdi);
        System.out.println();
        System.out.println(treOppg1.antall());*/

        //Oppgave 2
        Integer[] oppg2List = {1,2,3,1,2,3};
        EksamenSBinTre<Integer> treOppg2 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi : oppg2List) treOppg2.leggInn(verdi);
        System.out.println(treOppg2.antall(1));

        //Oppgave 3
        System.out.println(foorstePostorden(treOppg2.rot));

    }


} // ObligSBinTre
