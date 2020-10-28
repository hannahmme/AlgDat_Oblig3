# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

Jeg har brukt git til å dokumentere arbeidet mitt under denne eksamenen.
Jeg har 51 commits totalt, og jeg har skrevet en passende beskjed som beskriver
hver commit. 

#Oppgavebeskrivelse:

* Oppgave 1: 	
        Løste ved å implementere kode fra kilde Programkode 5.2.3 a som det står i
		oppgaveteksten. Vi starter med å opprette en node som blir en teller og en node
		som blir foreldre-noden til telleren. Verdien vi får inn i metoden blir sammenliknet
		med node-verdien vi er på og er den mindre flyttes noden til venstre barn. Er den større, flyttes telleren til 
		høyre barn. Når det ikke er flere barn, er teller null og vi går ut av while-løkken. 
		Her blir det avgjort om rot (foreldrenode) er null, da settes rot lik telleren. 
		Har vi en rotnode med verdi, skal den nye noden settes lik enten høyre eller venstre barn i treet. 

* Oppgave 2: 	
        Oppretter node som starter i roten som blir en teller "nodeViErPå" og en teller for 
		antall forekomster. Lager en while-løkke som itererer så lenge nodeViErPå ikke lik null.
 		For hver iterasjon flytter vi oss binært i treet, ved å bruke compare()-metoden. Hvis compare() 
		returnerer -1 er verdien vi sammenlikner med nodenViErPå.verdi mindre, er den 0 har vi funnet 
		verdien i treet og multipliserer antall-telleren med 1. Er returverdien 1 går vi til høyre i treet. 
		Når vi er ute av while-løkken returnerer den antallet forekomster.

* Oppgave 3: 	Implementerer førstePostorden(Node<T> p) ved å se på kode 5.1.7 h) i kompendiet. Benytter while-løkke 
		til å iterere til neste venstre eller høyre barn. Dersom p har et venstrebarn, så skal vi flytte oss 
		så langt ned til venstre som mulig. Deretter når p er kommet helt til venstre i venstre subtre, sjekker
		den om p har noen høyrebarn. Hvis ja, flytter p seg så langt til høyre i høyre subtre som mulig.
		Til slutt returneres p som er den første noden i treet post orden.

* Oppgave 3: 	
        Implementerer nestePostorden(Node<T> p) ved å lese meg opp på premissene for å finne neste post-orden i 
		kompendiet på 5.1.7 rett over figur 5.1.7 e). Legger inn sjekker for om noden vi får inn ikke er rotnoden og har 
		en forelder ikke lik null. Har den en foreldrenode, så går den videre og sjekker for alle de ulike tilfellene
		for hva p kan være. Dersom noen av tilfellene er sanne, går den inn og sjekker videre p sine nodebarn (dersom 
		den har det). Metoden returnerer null dersom p er rotnode, da rot er siste node vi besøker i post-orden rekkefølge. 
		Rot sin neste blir da naturlig null.

* Oppgave 4: 	
        Løser oppgaven postorden(Oppgave<? super T> oppgave) ved å benytte hjelpemetodene førstePostorden(Node<T> p) og nestePostorden(Node<T> p).
		Oppretter en teller "nodePostOrden" som starter på første post-orden. Deretter går det inn i en while-løkke
		som itererer så lenge nodePostOrden/telleren ikke er null. For hver iterasjon utføres en oppgave som sendes inn 
		i metoden. Denne oppgaven tar med seg telleren sin verdi og skriver ut dette i post-orden rekkefølge. 
		nodePostOrden oppdateres til neste node post-orden rekkefølge nederst i while-løkken.

* Oppgave 4: 	
        Løser postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) ved å gjøre denne til en rekursiv metode som 
		først sjekker om p-verdien vi får inn ikke er lik null. Er den ikke det, går den videre inn og flytter p så langt 
		til venstre det er venstrebarn og deretter så langt til høyre det er høyrebarn. Til slutt kalles utførOppgave og 
		sender inn p.verdi etter at de rekursive kallene har landet på noden post orden. 

* Oppgave 5: 	
        Implementerer serialize() ved å opprette en kø av ArrayDeque<Node>. Jeg lager også en ArrayList<T> som skal eventuelt fylles
		opp og returneres av metoden. Dersom roten i treet er lik null, returneres listen med en gang. Da vil denne være tom. 
		Dersom dette ikke er tilfellet, går metoden videre til å legge til roten bakerst med .addLast()-metoden. Så lenge køen ikke er tom,
		tar vi ut første element i køen og har det i en midlertidig variabel. Dersom denne noden vi tok ut av køen har noen barnenoder, legger 
		vi inn dem i køen. Til slutt legger vi til den midlertidige variabelen i ArrayList-en vår. 

* Oppgave 5: 	
        Løser det ved å opprette et nytt tre av EksamenSBinTre<K>. Jeg oppretter også en node som blir en midlertidig 
        node for rot senere i metoden. Med en for-each-løkke kjører metoden gjennom alle verdiene i ArrayListen vi som 
        metoden har fått inn. Det opprettes enda en ny node, denne som skal legges inn i treet igjen. Dersom treet ikke 
        har noen rot, legges denne nye noden som rot. Ellers skal denne nye noden enten stå som høyre eller venstre barn til
        roten. Dette avgjøres med metoden .compare(). Videre sjekker metoden om venstre eller høyrebarn er null. Hvis ja,
        så settes noden som ny barnenode for disse. Hvis ikke så settes den som ny barne-barne-node for dem igjen. 

* Oppgave 6: 	
        Implementerer metoden fjern(T verdi) ved å se på programkode 5.2.8 d). Legger inn ekstra kode for tilfellet 1, altså at noden vi har funnet
		og skal fjerne, ikke har noen barnenoder. Sjekker deretter om tilfellet er at noden er rot, så settes denne med en gang lik null. Hvis ikke,
		settes venstre eller høyrepekeren som peker på denne lik null. Videre skriver inn kode for tilfellene 2 og 3. Altså om noden vi har funnet 
		har et høyre eller et venstre barn, eller om den har to barn. 

* Oppgave 6: 	
        Løser oppgaven ved å implementere metoden fjernAlle(T verdi) ved å opprette en hjelpevariabel 
        "antallNoderFjernet". Sjekker om verdien vi får inn er lik null. Hvis ja, returner antallNoderFjernet. 
        Hvis treet er tomt, returnerer metoden tom() true. Da returneres 0. Hvis ingen av tilfellene over, 
        så går metoden inn i en while-løkke som kaller på metoden fjern(). Så lenge den returnerer true, itererer 
		while-løkken. For hver iterasjon legges det til 1 til variabelen antallNoderFjernet. 
		Til slutt returneres antallNoderFjernet. 

* Oppgave 6: 	Løser nullstill() ved å først sjekke om treet er tomt. Hvis ja, returner ut av metoden. Hvis rot ikke er lik null, så utføres oppgaven.
		Da går den inn i en while-løkke med en teller som itererer gjennom hver node og nullstiller høyre og venstre-barn, og setter sin egen verdi
		lik null. Når vi er ute av while-løkken står vi på roten. Da setter vi denne lik null.
		
