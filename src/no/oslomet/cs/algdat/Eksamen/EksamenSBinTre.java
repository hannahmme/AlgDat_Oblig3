package no.oslomet.cs.algdat.Eksamen;

import java.util.*;

public class EksamenSBinTre<T> {

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    private static final class Node<T> {     // en indre nodeklasse
        private T verdi;                    // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;           // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder) {  // konstruktør

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

        //Starter i roten
        Node<T> teller = rot;

        //er forelder til teller-noden
        Node<T> foreldreNode = null;

        int compareValue = 0;
        while (teller != null) {
            foreldreNode = teller;
            compareValue = comp.compare(verdi, teller.verdi);

            //flytter oss til venstre om verdien er mindre enn 0
            if (compareValue < 0) {
                teller = teller.venstre;

                //er verdien større eller lik, flytter vi oss til høyre
            } else {
                teller = teller.høyre;
            }
        }

        //når vi finner en node som ikke har en verdi, opprettes det
        //en ny node med verdien og foreldrenoden
            teller = new Node<>(verdi, foreldreNode);

        //hvis foreldrenoden er null, vil det si at treet ikke har noen rot
        if (foreldreNode == null) rot = teller;

        else if (compareValue < 0) foreldreNode.venstre = teller;
        else foreldreNode.høyre = teller;

        antall++;
        endringer++;
        return true;
    }

    /**
     * Oppgave 2 (Ferdig kodet fra før)
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
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    /**
     * Oppgave 2 (Ferdig kodet fra før)
     *
     * @return - returnerer antall
     */
    public int antall() {
        return antall;
    }


    /**
     * (Ferdig kodet fra før)
     *
     * @return - returnerer en String av treet i postOrder-rekkefølge
     */
    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }
        return s.toString();
    }

    /**
     * Oppgave 2 (Ferdig kodet fra før)
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
        Node<T> nodeViErPå = rot;
        int antallForekomster = 0;
        int compareAnswer;

        //Så lenge noden i treet har en verdi, sjekker vi verdien opp mot den
        while (nodeViErPå != null) {
            compareAnswer = comp.compare(verdi, nodeViErPå.verdi);

            //Er verdien mindre enn nodens verdi, går vi til venstre barnenode
            if (compareAnswer < 0) nodeViErPå = nodeViErPå.venstre;

                //Hvis verdien er lik, legges det til i telleren +1 og
                //vi flytter oss til høyre barnenode
            else {
                if (compareAnswer == 0) antallForekomster++;
                nodeViErPå = nodeViErPå.høyre;
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
    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (true) {
            //hvis det finnes et venstrebarn, gå til venstre
            if (p.venstre != null) p = p.venstre;

                //hvis ikke venstre, sjekk om det finnes et høyrebarn, gå til høyre
            else if (p.høyre != null) p = p.høyre;

                //hvis ingen av delene, returner noden (den første helt til venstre)
            else return p;
        }
    }

    /**
     * Oppgave 3
     *
     * @param p  - rot
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
            if (foreldreNode.høyre == p) {
                return foreldreNode;
            }

            //Hvis noden vi er på er et venstrebarn og høyrebarn ikke finnes,
            //er foreldrenoden neste i postOrden
            else if (foreldreNode.høyre == null && foreldreNode.venstre == p) {
                return foreldreNode;
            }

            //Hvis noden vi er på er et venstrebarn, og det finnes et høyrebarn
            //går vi inn og sjekker om dette høyrebarnet har flere barn
            else if (foreldreNode.høyre != null && foreldreNode.venstre == p) {

                //flytter noden vi er på, til det høyre barnet
                foreldreNode = foreldreNode.høyre;

                //hvis noden vi står på har venstrebarn, følger vi
                //alle venstre barn til det ikke er flere
                if(foreldreNode.venstre != null) {
                    while (foreldreNode.venstre != null) {
                        foreldreNode = foreldreNode.venstre;
                    }
                    //hvis barnet nederst til venstre har et høyrebarn,
                    //går vi nederst til dette barnet og da dette er neste
                    //post-orden
                    if(foreldreNode.høyre != null){
                        while(foreldreNode.høyre != null){
                            foreldreNode = foreldreNode.høyre;
                        }
                        return foreldreNode;
                    }
                    //returnerer det nederste, venstre barnet
                    return foreldreNode;
                }

                //hvis noden vi er på har høyrebarn, følger vi
                //alle høyre barn til det ikke er flere
                else if(foreldreNode.høyre != null){
                    while(foreldreNode.høyre != null){
                        foreldreNode = foreldreNode.høyre;
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
        Node<T> nodePostOrden = førstePostorden(rot);
        while (nodePostOrden != null) {
            oppgave.utførOppgave(nodePostOrden.verdi);
            nodePostOrden = EksamenSBinTre.nestePostorden(nodePostOrden);
        }
    }

    /**
     * Oppgave 4 (Ferdig kodet fra før)
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
        postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    /**
     *
     * Oppgave 5
     *
     * @return - returnerer et array med et serialisert
     * tre i nivå orden
     */
    public ArrayList<T> serialize() {
        ArrayDeque<Node> kø = new ArrayDeque<>();
        ArrayList<T> serialisertNodeListe = new ArrayList<>();

        //dersom rot er null, returner tom liste
        if(rot == null) return serialisertNodeListe;

        //legg til rot-noden
        kø.addLast(rot);
        antall++;
        endringer++;

        //så lenge køen ikke er tom
        while(!kø.isEmpty()){
            //Punkt 1: Ta ut første fra køen
            Node<T> midlertidig = kø.removeFirst();
            antall--;
            endringer++;

            //Punkt 2: Legg til midlertidig sine to barn til køen
            if(midlertidig.venstre != null){
                kø.addLast(midlertidig.venstre);
                antall++;
                endringer++;
            }

            if(midlertidig.høyre != null){
                kø.addLast(midlertidig.høyre);
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
                        if(midlertidigNode.høyre == null){
                            midlertidigNode.høyre = node; //sett inn her
                            node.forelder = midlertidigNode;
                            sattInn = true;

                            //legger til antall og endringer
                            deserialisertTre.antall++;
                            deserialisertTre.endringer++;
                        }else{
                            //høyre har barn, vi flytter oss til det høyre barnet
                            //og sjekker i neste iterasjon om vi skal til høyre eller venstre
                            midlertidigNode = midlertidigNode.høyre;
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
        if(tom()){
            return;
        }
        if(rot != null) {
            //starter fra første postorden
            Node<T> nodeSomSkalFjernes = førstePostorden(rot);
            Node<T> neste ;

            while(nodeSomSkalFjernes != null){
                //lagrer neste post-orden før node-verdien
                //vi står på blir nullet ut
                neste = nestePostorden(nodeSomSkalFjernes);
                nodeSomSkalFjernes.verdi = null;
                nodeSomSkalFjernes.venstre = null;
                nodeSomSkalFjernes.høyre = null;

                //legger til antall og endringer
                antall--;
                endringer++;

                //flytter til neste postorden i treet som skal fjernes
                nodeSomSkalFjernes = neste;

            }
            //når nodeSomSkalFjernes == null, betyr det at vi
            //har travsert gjennom treet i postorden
            //og står på roten
            rot = null;
        }
    }

    /**
     * Oppgave 6
     *
     * @param verdi - verdi som skal fjernes i treet
     * @return - returnerer true om fjerningen er vellykket,
     *           false dersom verdien ikke finnes i treet eller
     *           mislykket fjerning.
     */
    public boolean fjern(T verdi) {

        //treet har ingen nullverdier
        if(verdi == null) return false;
        if(rot == null) return false;

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
                p = p.høyre;
                endringer++;
            }
            //hvis ingen av delene, gå ut av while
            else break;
        }

        //vi finner ikke verdien
        if(p == null) return false;


        //Tilfelle 1: p har ingen barnenoder
        if(p.venstre == null && p.høyre == null){
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
                q.høyre = null;
                p.forelder = null;

                //legger til endringer
                endringer++;
            }
        }

        //Tilfelle 2: p har enten et høyrebarn eller et venstrebarn
        else if(p.venstre == null || p.høyre == null){

            //hvis venstrebarnet ikke er null, blir barnet satt til venstrebarnet
            //hvis høyrebarnet ikke er null, blir barnet satt til høyrebarnet
            Node<T> barn = p.venstre != null ? p.venstre : p.høyre;

            //hvis noden vi er på er lik roten,
            //settes roten lik barnet
            if(p == rot) {
                rot = barn;
                barn.forelder = null;

                //legger til endringer
                endringer++;
            }

            //hvis noden vi er på er det venstre barnet,
            //setter vi forelderens venstre-peker til venstre eller
            //høyrebarnet til noden vi er på
            else if (p == q.venstre) {
                q.venstre = barn;
                barn.forelder = q;

                //legger til endringer
                endringer++;
            }

            //og omvendt, hvis noden vi er på er det høyre barnet
            //settes forelderens høyrepeker til det høyre eller venstrebarnet
            //til nodden vi er på
            else {
                q.høyre = barn;
                barn.forelder = q;

                //fjerner antall og legger til endringer
                endringer++;
            }
        }
        //Tilfelle 3: p har to barn
        else{

            //s er en kopi av p (noden vi har funnet)
            Node<T> s = p;

            //r blir høyrebarnet til p
            Node<T> r = p.høyre;

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
            if(s != p) {
                s.venstre = r.høyre;
            }

            //hvis s er lik p (noden vi skal fjerne)
            //settes s sitt høyrebarn (p sitt høyrebarn, som vi
            //i dette tilfellet byttet plass med noden vi skulle fjerne)
            //lik sitt eget høyrebarn (null)
            else {
                s.høyre = r.høyre;
            }
        }
        antall--;
        endringer++;
        return true;

    }

    /**
     * Oppgave 6
     *
     * @param verdi - verdi som skal fjernes i treet. Gjelder alle
     * objekter i treet med denne verdien
     * @return - returnerer antallet av verdien som ble fjernet
     * Inspirasjon fra kildekode: https://www.cs.hioa.no/~ulfu/appolonius/kap5/2/fasit528.html
     */
    public int fjernAlle(T verdi) {
        int antallNoderFjernet = 0;

        if (verdi == null) {
            return antallNoderFjernet;
        }

        //hvis treet er tomt, returner 0
        if(tom()){
            return 0;
        }

        //så lenge fjern-metoden returnerer true
        //altså at den vellykket finner og fjerner verdien,
        //forsetter den helt til den er false
        while(fjern(verdi)) {
            antallNoderFjernet++;
        }
        return antallNoderFjernet;
    }
} // ObligSBinTre

