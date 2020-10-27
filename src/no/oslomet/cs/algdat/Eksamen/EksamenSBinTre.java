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
        if(p == null) return null;
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
                    //hvis barnet nederst til venstre har et høyrebarn,
                    //går vi nederst til dette barnet og da dette er neste
                    //post-orden
                    if(foreldreNode.hooyre != null){
                        while(foreldreNode.hooyre != null){
                            foreldreNode = foreldreNode.hooyre;
                        }
                        return foreldreNode;
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
     * @param oppgave - Oppgave som skal utføres
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
        ArrayDeque<Node> koo = new ArrayDeque<>();
        ArrayList<T> serialisertNodeListe = new ArrayList<>();

        //dersom rot er null, returner tom liste
        if(rot == null) return serialisertNodeListe;

        //legg til rot-noden
        koo.addLast(rot);
        antall++;
        endringer++;

        //så lenge køen ikke er tom
        while(!koo.isEmpty()){
            //Punkt 1: Ta ut første fra køen
            Node<T> midlertidig = koo.removeFirst();
            antall--;
            endringer++;

            //Punkt 2: Legg til midlertidig sine to barn til køen
            if(midlertidig.venstre != null){
                koo.addLast(midlertidig.venstre);
                antall++;
                endringer++;
            }

            if(midlertidig.hooyre != null){
                koo.addLast(midlertidig.hooyre);
                antall++;
                endringer--;
            }

            //Punkt 3: Legg til verdien i listen
            serialisertNodeListe.add(midlertidig.verdi);
            antall++;
            endringer++;
        }
        return serialisertNodeListe;
    }

    /**
     * Oppgave 5
     *
     * @param data - Listen med data som skal legges i nivåorden i treet
     * @param c - Av Comparator-interfacet med metoden compare().
     * @param <K> - Generisk datatype
     * @return - Returnerer et tre av klassen EksamenSBinTre
     */
    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> deserialisertTre = new EksamenSBinTre<>(c);
        Node<K> midlertidigNode;

        //Lager en for-each-løkke så vi kan gå
        //gjennom alle verdiene i arraylisten
        for(K verdi : data){

            //Oppretter en node med verdi fra listen
            Node<K> node = new Node<>(verdi, null, null, null);

            //Dersom rotnoden ikke er blitt fylt inn,
            //setter vi første verdi fra listen lik rot
            if(deserialisertTre.rot == null){
                deserialisertTre.rot = node;

                //oppdaterer endringer og antall
                deserialisertTre.antall++;
                deserialisertTre.endringer++;
            }else {

                //midlertidig node/teller
                midlertidigNode = deserialisertTre.rot;
                boolean sattInn = false;
                while (sattInn == false){
                    //sjekker om verdien i listen er større og/eller lik, eller mindre enn
                    //noden vi er på
                    int retning = c.compare(node.verdi, midlertidigNode.verdi);

                    //hvis den er større og/eller lik, går vi til høyre
                    if(retning >= 0) {

                        //dersom noden til høyre ikke har noen verdi,
                        //settes denne lik noden
                        if(midlertidigNode.hooyre == null){
                            midlertidigNode.hooyre = node; //sett inn her
                            node.forelder = midlertidigNode;
                            sattInn = true;

                            //legger til antall og endringer
                            deserialisertTre.antall++;
                            deserialisertTre.endringer++;
                        }else{
                            //høyre har barn, vi flytter oss til det høyre barnet
                            //og sjekker i neste iterasjon om vi skal til høyre eller venstre
                            midlertidigNode = midlertidigNode.hooyre;
                        }
                    }else{

                        //dersom noden til venstre ikke har noen verdi,
                        //settes denne lik noden
                        if(midlertidigNode.venstre == null){
                            midlertidigNode.venstre = node; //setter inn her
                            node.forelder = midlertidigNode;
                            sattInn = true;

                            //legger til antall og endringer
                            deserialisertTre.antall++;
                            deserialisertTre.endringer++;
                        }else{
                            //dersom det er et venstrebarn, flytter vi oss videre til venstre
                            //og sjekker i neste iterasjon om vi skal gå til høyre eller venstre
                            midlertidigNode = midlertidigNode.venstre;
                        }
                    }
                }
            }
        }
        return deserialisertTre;
    }

    /**
     * Oppgave 6
     */
    public void nullstill() {

    }

    /**
     * Oppgave 6
     *
     * @param verdi
     * @return
     */
    public boolean fjern(T verdi) {

        //treet har ingen nullverdier
        if(verdi == null) return false;

        //q skal være forelder til p
        Node<T> p = rot;
        Node<T> q = null;

        //så lenge roten ikke er null, går vi inn og
        //sammenlikner rot og verdi for å finne ut om
        //vi skal gå til venstre eller høyre
        while(p != null){
            //sammenlikner
            int compare = comp.compare(verdi, p.verdi);

            //hvis verdien er mindre, gå til venstre subtre
            if(compare < 0) {
                q = p;
                p = p.venstre;
                endringer++;
            }

            //hvis verdien er større, gå til høyre subtre
            else if(compare > 0) {
                q = p;
                p = p.hooyre;
                endringer++;
            }
            //hvis ingen av delene, gå ut av while
            else break;
        }

        //vi finner ikke verdien
        if(p == null) return false;


        //Tilfelle 1: p har ingen barnenoder
        if(p.venstre == null && p.hooyre == null){
            //verdien ligger i roten
            //setter rot lik null
            if(p == rot){
                rot = null;

                //legger til endringer
                antall--;
                endringer++;
                return true;
            }
            //hvis p er et venstrebarn, settes foreldrenoden
            //sin venstrepeker lik null
            if(p == q.venstre){
                q.venstre = null;
                p.forelder = null;

                //legger til endringer
                endringer++;
            }

            //dersom p er et høyrebarn, settes foreldrepekeren
            //sin høyrepeker lik null
            else{
                q.hooyre = null;
                p.forelder = null;

                //legger til endringer
                endringer++;
            }
        }

        //Tilfelle 2: p har enten et høyrebarn eller et venstrebarn
        else if(p.venstre == null || p.hooyre == null){

            //hvis venstrebarnet ikke er null, blir barnet satt til venstrebarnet
            //hvis høyrebarnet ikke er null, blir barnet satt til høyrebarnet
            Node<T> barn = p.venstre != null ? p.venstre : p.hooyre;

            //hvis noden vi er på er lik roten,
            //settes roten lik barnet
            if(p == rot) {
                rot = barn;

                //legger til endringer
                endringer++;
            }

            //hvis noden vi er på er det venstre barnet,
            //setter vi forelderens venstre-peker til venstre eller
            //høyrebarnet til noden vi er på
            else if (p == q.venstre) {
                q.venstre = barn;

                //legger til endringer
                endringer++;
            }

            //og omvendt, hvis noden vi er på er det høyre barnet
            //settes forelderens høyrepeker til det høyre eller venstrebarnet
            //til nodden vi er på
            else {
                q.hooyre = barn;

                //fjerner antall og legger til endringer
                endringer++;
            }
        }
        //Tilfelle 3: p har to barn
        else{

            //s er en kopi av p (noden vi har funnet)
            Node<T> s = p;

            //r blir høyrebarnet til p
            Node<T> r = p.hooyre;

            //så lenge det finnes et venstrebarn
            //fra noden vi står på sitt høyrebarn
            while(r.venstre != null){

                //s er forelder til r
                //vi forflytter oss nedover venstre subtre
                s = r;
                r = r.venstre;

                //legger til endringer
                endringer++;
            }

            //når vi har funnet noden vi skal fjerne sin
            //neste in-orden, setter vi noden som skal
            //fjernes lik neste node in-orden
            p.verdi = r.verdi;

            //legger til endringer
            endringer++;

            //hvis s ikke er lik p (noden vi skal fjerne)
            //settes noden vi byttet med p lik høyrebarnet sitt
            if(s != p) s.venstre = r.hooyre;

            //hvis s er lik p (noden vi skal fjerne)
            //settes s sitt høyrebarn (p sitt høyrebarn, som vi
            //i dette tilfellet byttet plass med noden vi skulle fjerne)
            //lik sitt eget høyrebarn (null)
            else s.hooyre = r.hooyre;
        }

        antall--;
        endringer++;
        return true;

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
*//*
        //Oppgave 0
        EksamenSBinTre<String> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(tre.antall());

        EksamenSBinTre<Integer> treInt = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treInt.antall());
        EksamenSBinTre<Character> treChar = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treChar.antall());
        EksamenSBinTre<Double> treDouble = new EksamenSBinTre<>(Comparator.naturalOrder());
        System.out.println(treDouble.antall());
*//*


        //Oppgave 1
  *//*      Integer[] a = {4,7,2,9,4,10,8,1,4,6};
        EksamenSBinTre<Integer> treOppg1 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi: a) treOppg1.leggInn(verdi);
        System.out.println();
        System.out.println(treOppg1.antall());*//*

        //Oppgave 2
        Integer[] oppg2List = {11,24,1,2,4,5};
        EksamenSBinTre<Integer> treOppg2 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : oppg2List) treOppg2.leggInn(verdi);
        System.out.println("Antall forekomster av tallet 1");
        System.out.println(treOppg2.antall(1));

        //Integer[] oppg3List = {6, 14, 1, 8, 12, 3, 7, 9, 11, 13, 2, 5, 4};
        //for (int verdi : oppg3List) treOppg2.leggInn(verdi);

        //Oppgave 3
        System.out.println("Første post-orden");
        System.out.println(foorstePostorden(treOppg2.rot));

        System.out.println("Neste-post-orden");
        System.out.println(nestePostorden(treOppg2.rot.venstre));
        System.out.println();

        System.out.println("Treet skrevet ut i post-orden");
        //System.out.println(treOppg2.toStringPostOrder());

        //printLevelOrder(treOppg2.rot);*/


       // treOppg2.serialize();

        EksamenSBinTre<Integer> treOppg5 = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for(int verdi : a) treOppg5.leggInn(verdi);

        System.out.println();

        System.out.println("Serialisert på nivåorden: ");
        ArrayList<Integer> treOppg5Serialize = treOppg5.serialize();
        System.out.println(treOppg5Serialize);


        System.out.println("\n");
     /*   EksamenSBinTre<Integer> treOppg5Deserialize = EksamenSBinTre.deserialize(treOppg5Serialize, Comparator.naturalOrder());
        System.out.println("DEtte er den som ikke er forståelig:");
        System.out.println(treOppg5Deserialize);
*/
/*
        System.out.println("Print level order treOppg5Deserialize : ");
        printLevelOrder(treOppg5Deserialize.rot);*/

       // treOppg5.leggInn(8);

      //  System.out.println("\nEtter å ha lagt inn 8 nivåorden:");
        //System.out.println(treOppg5.serialize());

        //treOppg5.fjern(9);
        //System.out.println(treOppg5.toStringPostOrder());


        treOppg5.fjern(12);
        treOppg5.fjern(2);
        treOppg5.fjern(4);
        treOppg5.fjern(6);

        System.out.println("Etter å ha fjernet tallet 12, 2, 4, 6");
        System.out.println(treOppg5.serialize());

        System.out.println("Skriver ut tre i postOrden");
        System.out.println(treOppg5.toStringPostOrder());



    }
} // ObligSBinTre

