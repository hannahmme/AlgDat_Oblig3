package no.oslomet.cs.algdat.Eksamen;


import java.sql.SQLOutput;
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

    private static final class Node<T> {     // en indre nodeklasse
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

        private Node(T verdi, Node<T> forelder) {  // konstruktoor

            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    /**
     * Oppgave 1
     *
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
        while (currentNodeWeStandOn != null) {
            nextNode = currentNodeWeStandOn;
            compareValue = comp.compare(verdi, currentNodeWeStandOn.verdi);
            if (compareValue < 0) {
                currentNodeWeStandOn = currentNodeWeStandOn.venstre;
            } else {
                currentNodeWeStandOn = currentNodeWeStandOn.hooyre;
            }
        }

        //ute av while
        //Her opprettes det en ny node i treet
        currentNodeWeStandOn = new Node<>(verdi, nextNode);
        if (nextNode == null) rot = currentNodeWeStandOn;
        else if (compareValue < 0) nextNode.venstre = currentNodeWeStandOn;
        else nextNode.hooyre = currentNodeWeStandOn;

        antall++;
        endringer++;
        return true;
    }

    /**
     * Oppgave 2 (Ferdig kodet)
     *
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
     *
     * @return - returnerer antall
     */
    public int antall() {
        return antall;
    }


    /**
     * Hjelpemetode for å debugge oppgave 3. Midlertidig
     *
     * @param rot - tar inn rot som er startpunktet for traversering
     * @param <T> - generisk datatype
     */
    public static <T> void printLevelOrder(Node<T> rot) {
        ArrayDeque<Node> queue = new ArrayDeque<>();

        //legg til rot-noden
        queue.addLast(rot);

        //så lenge køen ikke er tom
        while (!queue.isEmpty()) {
            //Punkt 1: Ta ut første fra køen
            Node current = queue.removeFirst();

            //Punkt 2: Legg til current sine to barn til køen
            if (current.venstre != null) {
                queue.addLast(current.venstre);
            }
            if (current.hooyre != null) {
                queue.addLast(current.hooyre);
            }

            //Punkt 3: Skriv ut
            System.out.print(current.verdi + " ");
        }
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
     *
     * @return - returnerer true om antall er lik 0,
     * false ellers
     */
    public boolean tom() {
        return antall == 0;
    }

    /**
     * Oppgave 2
     *
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
        while (nodeViErPaa != null) {
            compareAnswer = comp.compare(verdi, nodeViErPaa.verdi);

            //Er verdien mindre enn nodens verdi, går vi til venstre barnenode
            if (compareAnswer < 0) nodeViErPaa = nodeViErPaa.venstre;

                //Hvis verdien er lik, legges det til i telleren +1 og
                //vi flytter oss til høyre barnenode
            else {
                if (compareAnswer == 0) antallForekomster++;
                nodeViErPaa = nodeViErPaa.hooyre;
            }
        }
        return antallForekomster;
    }

    /**
     * Oppgave 3
     *
     * @param p   - p er rot
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
     *
     * @param p   - rot
     * @param <T> - generisk datatype
     * @return - returnerer neste noden i rekken etter første node i postorden
     * Inspirasjon hentet fra 5.1.7: https://www.cs.hioa.no/~ulfu/appolonius/kap5/1/kap51.html#kode.5.1.7.h
     */
    private static <T> Node<T> nestePostorden(Node<T> p) {

        //Sjekker om noden har en foreldrenode. Har den ikke det,
        //går den ikke inn i if-en og metoden returnerer null
        if (p.forelder != null) {
            Node<T> foreldreNode = p.forelder;

            //Hvis det finnes en foreldrenode og noden vi er på er et høyrebarn,
            //vil den neste i postOrden være foreldrenoden
            if (foreldreNode.hooyre == p) {
                return foreldreNode;
            }

            //Hvis noden vi er på er et venstrebarn og høyrebarn ikke finnes,
            //er foreldrenoden neste i postOrden
            else if (foreldreNode.hooyre == null && foreldreNode.venstre == p) {
                return foreldreNode;
            }

            //Hvis noden vi er på er et venstrebarn, og det finnes et høyrebarn
            //går vi inn og sjekker om dette høyrebarnet har flere barn
            else if (foreldreNode.hooyre != null && foreldreNode.venstre == p) {

                //flytter noden vi er på, til det høyre barnet
                foreldreNode = foreldreNode.hooyre;

                //hvis noden vi står på har venstrebarn, følger vi
                //alle venstre barn til det ikke er flere
                if(foreldreNode.venstre != null) {
                    while (foreldreNode.venstre != null) {
                        foreldreNode = foreldreNode.venstre;
                    }
                    //returnerer det nederste, venstre barnet
                    return foreldreNode;
                }

                //hvis noden vi er på har høyrebarn, følger vi
                //alle høyre barn til det ikke er flere
                else if(foreldreNode.hooyre != null){
                    while(foreldreNode.hooyre != null){
                        foreldreNode = foreldreNode.hooyre;
                    }
                    //returnerer det nederste, høyre barnet
                    return foreldreNode;
                }

                //hvis det kun er et høyre nodebarn som ikke har noen flere barn
                //er denne i seg selv den neste
                else return foreldreNode;
            }
        }
        //Returnerer null da p er rotnoden (siste i postOrden)
        return null;
    }


    /**
     * Oppgave 4
     *
     * @param oppgave - tar imot en oppgave som ber om
     *                å skrive ut treet i post orden
     */
    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> nodePostOrden = foorstePostorden(rot);
        while (nodePostOrden != null) {
            oppgave.utførOppgave(nodePostOrden.verdi);
            nodePostOrden = EksamenSBinTre.nestePostorden(nodePostOrden);
        }
    }

    /**
     * Oppgave 4
     *
     * @param oppgave
     */
    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }


    /**
     * Oppgave 4
     *
     * @param p - Vilkårlig node som man starter å traversere på
     * @param oppgave - Oppgave av interfacet Oppgave
     */
    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p == null){
            return;
        }
        postordenRecursive(p.venstre, oppgave);
        postordenRecursive(p.hooyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    /**
     * Oppgave 5
     *
     * @return
     */
    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 5
     *
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
     *
     * @param verdi
     * @return
     */
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennaa!");
    }

    /**
     * Oppgave 6
     *
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
        Integer[] oppg2List = {10};
        EksamenSBinTre<Integer> treOppg2 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : oppg2List) treOppg2.leggInn(verdi);
        System.out.println("Antall forekomster av tallet 1");
        System.out.println(treOppg2.antall(1));

        Integer[] oppg3List = {6, 14, 1, 8, 12, 3, 7, 9, 11, 13, 2, 5, 4};
        for (int verdi : oppg3List) treOppg2.leggInn(verdi);

        //Oppgave 3
        System.out.println("Første post-orden");
        System.out.println(foorstePostorden(treOppg2.rot));

        System.out.println("Neste-post-orden");
        System.out.println(nestePostorden(treOppg2.rot.venstre.hooyre.venstre));
        System.out.println();

        System.out.println("Treet skrevet ut i post-orden");
        System.out.println(treOppg2.toStringPostOrder());

        printLevelOrder(treOppg2.rot);

    }
} // ObligSBinTre

