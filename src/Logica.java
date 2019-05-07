import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Classe amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Logica {

    private static final int EARTH_RADIUS = 6371;

    //Constructor
    public Logica() {
    }

    /**
     * Funcio per consultar l'ID corresponent al nom d'un Pokemon
     * @param name String del nom del Pokemon
     * @param poke Array de pokemons on es fa la consulta del ID
     * @return long que representa l'id del pokemon consultat
     */
    private long nameToID(String name, Pokemon[] poke) {
        long id = -1;

        //Busquem el nom del pokemon
        for (int i = 0; i < poke.length; i++){
            if (poke[i].getNom().equals(name)){
                //obtenim l'id corresponent al nom
                id = poke[i].getId();
            }
        }
        return id;
    }

    /**
     * Funcio per consultar el nom corresponent al ID d'un Pokemon
     * @param id long que representa l'id del pokemon del que volem el nom
     * @param poke Array de pokemons on es fa la consulta del nom
     * @return String del nom del Pokemon
     */
    private String idToName(Long id, Pokemon[] poke){
        String name;
        String nameCap = null;

        //Busquem l'ID del pokemon
        for (int i = 0; i < poke.length; i++){
            if (poke[i].getId().equals(id)){
                //obteim el nom corresponent al id
                name = poke[i].getNom();
                nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }
        return nameCap;
    }

    /**
     * Funcio que ens diu si el pokemon es mitic o no
     * @param mythicals Array on trobem els pokemons mythics
     * @param id long que representa l'ID del pokemon que volem saber si es mythic
     * @return boolean que ens dira si el pokemon es mythic o no
     */
    public boolean itsMythic (ArrayList<Mythical> mythicals, long id) {
        boolean mythic = false;

        //Busquem el nom del pokemon a la llista de mítics
        for (int i = 0; i < mythicals.size(); i++) {
            if (id == mythicals.get(i).getId()) {
                mythic = true;
            }
        }
        return mythic;
    }

    /**
     * Funcio que ens diu si el pokemon es llegendari o no
     * @param legends Array on trobem els pokemons llegendaris
     * @param id long que representa l'ID del pokemon que volem saber si es llegendari
     * @return boolean que ens dira si el pokemon es llegendari o no
     */
    public boolean itsLegend (ArrayList<Legend> legends, long id) {
        boolean legend = false;

        for (int i = 0; i < legends.size(); i++) {
            if (id == legends.get(i).getId()) {
                legend = true;
            }
        }
        return legend;
    }

    /**
     * Proces que duu a terme la captura d'un Pokemon
     * @param jugador informacio del jugador, necesaria per fer la captura
     * @param poke array on trobem tots els pokemons disponibles
     * @param id id del pokemon que volem capturar
     * @param balls array on trobem totes les pokeballs disponibles
     * @param mythicals array amb els pokemones especials
     */
    public void initiateCapture(Jugador jugador, Pokemon[] poke, long id, Ball[] balls, ArrayList<Mythical> mythicals){

        int j = 0;
        int intents = 5;
        String choosedPokeball = " ";
        String finePokeball = " ";

        //Busquem el ID al Array de Pokemons
        for (int i = 0; i < poke.length; i++) {

            if (poke[i].getId().equals(id)) {
                j = i;
            }
        }
        String name = idToName(id, poke);
        System.out.println ("Un "+name+" salvatge aparegué!");

        while (checkPokeballs(jugador) && intents > 0) {

            //Quina pokeball vols utilitzar?
            choosedPokeball = comprovaChoosedPokeball(jugador, intents);
            updatePokeballs (jugador, choosedPokeball);

            //Control de captura
            if (huntedPokemon(choosedPokeball, balls, j, poke)){
                updateHuntedNumber(jugador, id);
                System.out.println("El pokémon " + poke[j].getNom() + " ha estat capturat!");
                //Repàs de les missions
                checkMissions (jugador, poke, mythicals);
                return;
            }else{
                intents--;
                finePokeball = tellPokeballName(jugador, choosedPokeball);
                System.out.println("La " + finePokeball + " ha fallat!");
            }
        }

        //Control de boles restants
        if (!checkPokeballs(jugador)){
            System.out.println("No queden Pokéballs...");
        }else{
            System.out.println("El pokémon " + poke[j].getNom() + " ha escapat...");
        }

    }

    /**
     * Funcio que comprova la pokeball escollida
     * @param jugador informacio del jugador
     * @param intents intents disponibles per caçar el pokemon
     * @return String que retorna la pokeball escollida
     */
    private String comprovaChoosedPokeball(Jugador jugador, int intents) {
        String choosedPokeball;

        do {
            System.out.println("Queden " + totalPokeballs(jugador) + " Pokéballs i " + intents + "/5 intents. Quin tipus de Pokéball vol fer servir?");
            Scanner entrada = new Scanner(System.in);
            choosedPokeball = entrada.next();

            if (!correctPokeballName(jugador, choosedPokeball)){
                System.out.println("Aquesta Pokéball no existeix!");
            }else{
                if (!avaliablePokeballName(jugador, choosedPokeball)){
                    System.out.println("No disposes de "+choosedPokeball+"!");
                }
            }
        }while (!correctPokeballName(jugador, choosedPokeball) || !avaliablePokeballName(jugador, choosedPokeball));
        return choosedPokeball;
    }

    /**
     * Funcio que retorna el nom original de la pokeball
     * @param jugador informacio del jugador
     * @param choosedPokeball String amb el nom de la pokeball escollida pel jugador
     * @return String amb el nom original de la pokeball
     */
    public String tellPokeballName (Jugador jugador, String choosedPokeball) {

        String pokeball = " ";

        for (int i = 0; i < jugador.getNomBalls().length; i++) {

            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)) {
                pokeball = jugador.getNomBalls()[i];
            }
        }
        return pokeball;
    }

    /**
     * Funcio que actualitza el nombre de Pokemons capturats d'un tipus
     * @param jugador informacio del jugador
     * @param id Long amb el id del Pokemon capturat
     */
    public void updateHuntedNumber (Jugador jugador, Long id){

        for (int i = 0; i < jugador.getIdHunted().length; i++){

            if (jugador.getIdHunted()[i].equals(id)){
                //Actualització del nombre de Pokemons capturats amb id "id"
                jugador.updateHunted(i);
            }
        }
    }

    /**
     * Funcio que retorna un boleà que indica si s'ha capturat el Pokémon
     * @param choosedPokeball Pokeball escollida pel jugador
     * @param balls Array de Pokeballs amb la informació de la probabilitat d'encert
     * @param j Int de la posició del Pokémon a capturar en el Array de Pokemons
     * @param poke Array amb la informació de captura de tots els Pokemons
     * @return Boolean que indica si s'ha capturat el Pokémon amb èxit o no
     */
    public boolean huntedPokemon(String choosedPokeball, Ball[] balls, int j, Pokemon[] poke){

        double randomValue = 0;
        int probMyBall = 0;
        int probMyPokemon = 0;
        double probCapture = 0;

        Random random = new Random();
        //Producció d'un long aleatòri entre 0 i 1.
        randomValue = random.nextDouble();

        //Probabilitat de la Pokeball
        for (int i = 0; i < balls.length; i++){

            if (balls[i].getName().equalsIgnoreCase(choosedPokeball)){
                probMyBall = balls[i].getCapture_rate();
            }
        }
        //Probabilitat del Pokémon
        probMyPokemon = poke[j].getCaptureRate();
        probCapture = (probMyBall/(double)256) + (probMyPokemon/(double)2048);
        return (probCapture > randomValue);
    }

    /**
     * Funcio que actualitza el nombre de Pokeballs restants
     * @param jugador informacio del jugador
     * @param choosedPokeball String amb la Pokeball escollida pel jugador
     */
    public void updatePokeballs (Jugador jugador, String choosedPokeball){

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)){
                jugador.restaBall(i);
            }
        }
    }

    /**
     * Comprova que queden pokeballs de les que ha triat el usuari per llançar
     * @param jugador Informacio del jugador de la partida
     * @param choosedPokeball Nom de la pokeball que volem saber si en queden unitats
     * @return true o false depenent de si el jugador disposa d'unitats
     */
    public boolean avaliablePokeballName (Jugador jugador, String choosedPokeball){

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)) {

                if (jugador.getNumBalls()[i] > 0) {
                    return true;
                }

            }

        }

        return false;
    }

    /**
     * Comprobem si existeix una pokeball
     * @param jugador Informacio del jugador de la partida
     * @param choosedPokeball Nom de la pokeball que volem saber si existeix
     * @return true o false depenent de si existeix o no
     */
    public boolean correctPokeballName (Jugador jugador, String choosedPokeball) {
        boolean ok = false;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {
            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)) {
                ok = true;
            }
        }
        return ok;
    }

    /**
     * Funcio per saber si el jugador disposa de pokeballs
     * @param jugador Informacio del jugador de la partida
     * @return ens retorna true o false depenent de si el jugador te o no pokeballs
     */
    public boolean checkPokeballs (Jugador jugador) {
        boolean pokeballs = false;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNumBalls()[i] > 0) {
                pokeballs = true;
            }
        }
        return pokeballs;
    }

    /**
     * Funcio que calcula el total de pokeballs que disposa el jugador
     * @param jugador Informacio del jugador de la partida
     * @return int amb el nombre de pokeballs que te el jugador
     */
    private int totalPokeballs (Jugador jugador){
        int total = 0;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {
            total += jugador.getNumBalls()[i];
        }
        return total;
    }

    /**
     * Funcio que demana un pokemon al usuari i retorna l'id
     * @param poke Array amb tots els pokemons que existeixen
     * @param tipusConuslta String que ens diu si volem una consulta de informacio o de reserca d'un pokemon
     * @return long amb l'id del pokemon que ha introduit
     */
    public long demanaPokemon(Pokemon[] poke, String tipusConuslta){
        //Demanem Pokemon
        if(tipusConuslta.equals("info")){
            System.out.println("De quin Pokémon vols informació?");
        }
        if(tipusConuslta.equals("captura")){
            System.out.println("Quin Pokémon vol buscar?");
        }
        Scanner teclat = new Scanner (System.in);
        String input = teclat.next();
        long id;

        try{
            int num = Integer.parseInt(input);
            id = (long)num;
            return id;
        }catch(NumberFormatException e){
            //Si ha introduit el nom del pokemon, ho pasem a id
            id = nameToID(input.toLowerCase(), poke);
            return id;
        }
    }

    /**
     * Funcio que comprova si existeix pokemon
     * @param id ID del pokemon que volem saber si existeix
     * @param poke Array amb tots els pokemons que existeixen
     * @return boolean que ens diu si el pokemon existeix o no
     */
    public boolean existeixPokemon(Long id , Pokemon[] poke){
        for(int i = 0 ; i < poke.length ; i++){
            if(poke[i].getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * Funcio que ens diu si cal mostrar una missio o no (si esta començada)
     * @param jugador Informacio del jugador de la partida
     * @param mythicals Array amb tots els pokemons mythics que existeixen
     * @param i Index que representa el pokemon especial de la recerca
     * @return boolean que ens diu si es una missio començada i no completada
     */
    private boolean showMission(Jugador jugador, ArrayList<Mythical> mythicals, int i){
        //boolean completed = false;
        boolean started = false;

        //Comprobem que la missio estigui començada
        for (int j = 0 ; j < mythicals.get(i).getTarget().size() ; j++){

            long idl = mythicals.get(i).getTarget().get(j);
            int idi = (int) idl;

            if (jugador.getNumHunted()[idi-1] > 0){
                started = true;
            }
        }
        //Comprovar si la misio esta completa
        boolean completed = true;
        for(int j = 0; j < mythicals.get(i).getTarget().size(); j++) {

            long k = mythicals.get(i).getTarget().get(j);
            int l = (int) k;

            if (jugador.getNumHunted()[l - 1] < mythicals.get(i).getQuantity().get(j)) {
                completed = false;
            }
        }

        if (started && !completed){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Funcio que comproba si hi ha missions començades
     * @param jugador Informacio del jugador de la partida
     * @param mythicals Array amb tots els pokemons mythics que existeixen
     * @return boolean que ens diu si hi han missions començades
     */
    private boolean hihaMissions(Jugador jugador, ArrayList<Mythical> mythicals){

        for (int i = 0 ; i < mythicals.size() ; i++){
            if (showMission(jugador, mythicals, i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Funcio que comprova si s'ha completat alguna missio especial i en cas afirmatiu inicia la captura
     * @param jugador Informacio del jugador de la partida
     * @param poke Array amb tots els pokemons que existeixen
     * @param mythicals Array amb tots els pokemons mythics que existeixen
     */
    public void checkMissions(Jugador jugador, Pokemon[] poke, ArrayList<Mythical> mythicals){

        for(int i = 0; i < mythicals.size(); i++){
            boolean completed = true;

            for(int j = 0; j < mythicals.get(i).getTarget().size(); j++){

                long k = mythicals.get(i).getTarget().get(j);
                int l = (int)k;

                if(jugador.getNumHunted()[l-1] < mythicals.get(i).getQuantity().get(j)){
                    completed = false;
                }
            }

            if (completed){
                long j = mythicals.get(i).getId();
                int h = (int)j;

                if(jugador.getNumHunted()[h-1] == 0) {
                    String name = idToName(poke[h - 1].getId(), poke);
                    System.out.println("Missió de captura completada!");
                    System.out.println("Recerca especial completada: Se t'apareix el mític " + name);
                    initiateMythicCapture(jugador, poke, h);
                }
            }
        }
    }

    /**
     * Funcio que inicia la captura d'un Pokémon mític en cas que s'hagi completat una missió
     * @param jugador Informacio del jugador de la partida
     * @param poke Array amb tots els pokemons que existeixen
     * @param i Int que representa el pokémon mític que correspon a la missió completada
     */
    private void initiateMythicCapture(Jugador jugador, Pokemon[] poke, int i){
        int intents = 5;
        String choosedPokeball = " ";
        String finePokeball = " ";

        while(intents > 0){

            do {
                System.out.println("Queden "+ intents + "/5 intents. Quin tipus de Pokéball vol fer servir?");
                Scanner entrada = new Scanner(System.in);
                choosedPokeball = entrada.next();

                if (!correctPokeballName(jugador, choosedPokeball)) {
                    System.out.println("Aquesta Pokéball no existeix!");
                }
            }while (!correctPokeballName(jugador, choosedPokeball));

                if (huntedMythicPokemon()){
                    String name = idToName(poke[i-1].getId(), poke);
                    updateHuntedNumber(jugador, poke[i-1].getId());
                    System.out.println("El pokémon " + name + " ha estat capturat!");
                    return;
                }else{
                    intents--;
                    finePokeball = tellPokeballName(jugador, choosedPokeball);
                    System.out.println("La " + finePokeball + " ha fallat!");
                }

        }
    }

    /**
     * Funcio que comprova si s'ha capturat el Pokémon mític.
     * @return true or false depenent de si s'ha capturat el Pokémon o no
     */
    private boolean huntedMythicPokemon() {
        double randomValue = 0;
        //La funció de càlcul de la probabilitat de captura d'un Pokémon mític sempre és 1. Indiferentment de la Pokéball que esculli l'usuari
        double probCapture = 1;

        Random random = new Random();
        //producció d'un long aleatòri entre 0 i 1.
        randomValue = random.nextDouble();
        return (probCapture > randomValue);
    }

    /**
     * OPCIO 1: afegir monedes al jugador
     * @param jugador Informacio del jugador de la partida
     */
    public void afegeixMonedes(Jugador jugador) {

        double monedes = -1;
        double preu = 0;
        boolean flag = false;

        DecimalFormat df = new DecimalFormat("#0.00");

        do {
            try {
                Scanner entrada = new Scanner(System.in);
                System.out.println("Quantes monedes vols comprar?");
                monedes = entrada.nextInt();

                if (monedes < 0) {
                    System.out.println("Error! S'han d'introduïr valors estrictament positius!");
                    monedes = 0;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error! Introdueix un número!");
                monedes = 0;
            }
        } while (monedes <= 0);

        if (monedes < 250) {
            preu = monedes * 0.01;
        }

        if (monedes >= 250 && monedes < 500) {
            preu = monedes * 0.009;
        }

        if (monedes >= 500 && monedes < 1000) {
            preu = monedes * 0.007;
        }

        if (monedes >= 1000 && monedes < 10000) {
            preu = monedes * 0.005;
        }

        if (monedes >= 10000) {
            preu = monedes * 0.0025;
        }

        System.out.println("El preu total es de " + df.format(preu) + "€. Confirma la compra? (Y/N)");

        do {
            Scanner entrada = new Scanner(System.in);
            String confirmacio = entrada.next();

            if (confirmacio.equalsIgnoreCase("y")) {
                jugador.setMonedes(jugador.getMonedes() + (int) monedes);                                                    //S'ha de utilitzar un setter de la classe jugador (no podem accedir a la variable monedes)
                System.out.println("S'han afegit " + (int) monedes + " monedes al seu compte.");
                flag = true;
            } else {

                if (confirmacio.equalsIgnoreCase("n")) {
                    System.out.println("Compra cancel·lada.");
                    flag = true;
                } else {
                    System.out.println("Introdueixi un caràcter correcte!");
                    System.out.println("El preu total es de " + df.format(preu) + "€. Confirma la compra? (Y/N)");
                }
            }
        } while (!flag);
    }

    /**
     * OPCIO 2: Comprar Pokeballs
     * @param jugador Informacio del jugador de la partida
     * @param balls Array amb totes les pokeballs disponibles
     */
    public void compraObjectes(Jugador jugador, Ball[] balls) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Teniu " + jugador.getMonedes() + " monedes.");
        System.out.println("Pokéballs disponibles:");
        int i;
        for (i = 0; i < balls.length; i++) {
            String name = balls[i].getName();
            String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            System.out.println("   " + (char) (97 + i) + ")" + nameCap + ":    " + balls[i].getPrice() + " monedes");
        }
        System.out.println(" ");
        System.out.println("   " + (char) (97 + i) + ") Sortir sense comprar");
        System.out.println(" ");

        //Comprobar opcio correcte
        char opcio;
        do {
            System.out.println("Escull una opció:");
            opcio = entrada.next().charAt(0);
            if (opcio > (97 + i) || opcio < 97) {
                System.out.println("Introdueix una opcio valida.");
            }
        } while (opcio > (97 + i) || opcio < 97);

        if(opcio != (97+i)){
            System.out.println("Quantes unitats en vol comprar?");
            try{
                int unitats = entrada.nextInt();
                //Comprobar numero enter
                if(unitats > 0){
                    //Comprobar saldo suficient
                    int cost = unitats * balls[opcio - 97].getPrice();
                    if (cost <= jugador.getMonedes()) {
                        if (unitats == 1) {
                            System.out.println("S'ha afegit " + unitats + " " + balls[opcio - 97].getName() + " al seu compte a canvi de " + balls[opcio - 97].getPrice() * unitats + " monedes.");
                        } else {
                            System.out.println("S'han afegit " + unitats + " " + balls[opcio - 97].getName() + "s al seu compte a canvi de " + balls[opcio - 97].getPrice() * unitats + " monedes.");
                        }
                        //Afegir balls al jugador
                        jugador.getNumBalls()[opcio - 97] += unitats;
                        jugador.setMonedes(jugador.getMonedes() - cost);
                    } else {
                        System.out.println("Ho sentim, però no disposa de suficients monedes.");
                    }
                }else{
                    System.out.println("Error, has d'introduir un nombre positiu");
                }
            }catch (java.util.InputMismatchException e){
                System.out.println("Error, has d'introduir un nombre!");
            }
        }else{
            System.out.println("Sortint de la botiga...");
        }
    }

    /**
     * OPCIO 3: Consultar inventari del jugador
     * @param jugador Informacio del jugador de la partida
     */
    public void consultaInventari(Jugador jugador) {
        int j = 0;
        System.out.println("Inventari:");
        for (int i = 0; i < jugador.getNomBalls().length; i++) {
            String name = jugador.getNomBalls()[i];
            String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            if (jugador.getNumBalls()[i] != 0) {
                j++;
                if (jugador.getNumBalls()[i] > 1) {
                    System.out.println("   - " + jugador.getNumBalls()[i] + "x " + nameCap + "s");
                } else {
                    System.out.println("   - " + jugador.getNumBalls()[i] + "x " + nameCap);
                }
            }
        }
        if (j == 0) {
        System.out.println("No disposes d'inventari!");
        }
    }

    /**
     * OPCIO 4: Buscar Pokemon salvatge
     * @param poke Array amb tots els pokemons que existeixen
     * @param legends Array amb tots els pokemons llegendaris que existeixen
     * @param mythicals Array amb tots els pokemons mythics que existeixen
     * @param jugador Informacio del jugador de la partida
     * @param balls Array amb tots els tipus de pokeballs disponibles
     */
    public void buscaPokemonSalvatge(Pokemon[] poke, ArrayList<Legend> legends, ArrayList<Mythical> mythicals, Jugador jugador, Ball[] balls) {

        if (checkPokeballs (jugador)) {

            long id = demanaPokemon(poke, "captura");

            //Començem captura
            if (existeixPokemon(id, poke)){

                if (!itsLegend(legends, id) && !itsMythic(mythicals, id)) {
                    initiateCapture (jugador, poke, id, balls, mythicals);
                }else {
                    if (itsLegend(legends, id)) System.out.println("Ho sentim, però aquest Pokémon és llegendari i no apareix salvatge.");
                    if (itsMythic(mythicals, id)) System.out.println("Ho sentim, però aquest Pokémon és mític i no apareix salvatge.");
                }

            }else{
                System.out.println("Ho sentim, però aquest Pokémon no existeix.");
            }

        } else {
            System.out.println("Ho sentim, però no té Pokéballs disponibles, pel que no pot buscar Pokémons.");
            System.out.println("Pot adquirir Pokéballs a la botiga!");
        }
    }

    /**
     * OPCIO 5: Fer raid per buscar llegendaris
     * @param jugador Informacio del jugador de la partida
     * @param legends Array amb tots els pokemons llegendaris que existeixen
     * @param poke Array amb tots els pokemons que existeixen
     * @param balls Array amb tots els tipus de pokeballs disponibles
     */
    public void ferRaid (Jugador jugador, ArrayList<Legend> legends, Pokemon[] poke, Ball[] balls) {

        double latitudUser;
        double longitudUser;
        int flag = 0;
        long gimnasMesProper;
        String gimnasMesProperr = " ";

        if (checkPokeballs (jugador)) {
            do {
                System.out.println("Latitud actual?");

                try {
                    Scanner entrada = new Scanner(System.in);
                    latitudUser = entrada.nextDouble();
                    if (latitudUser < -90 || latitudUser > 90) {
                        System.out.println("Error! Introdueix una latitud vàlida! (entre -90 i 90)");
                        latitudUser = 0;
                    } else {
                        flag = 1;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Error! Introdueix un número real!");
                    latitudUser = 0;
                }
            } while (flag == 0);

            flag = 0;

            do {
                System.out.println("Longitud actual?");

                try {
                    Scanner entrada2 = new Scanner(System.in);
                    longitudUser = entrada2.nextDouble();
                    if (longitudUser < -180 || longitudUser > 180) {
                        System.out.println("Error! Introdueix una longitud vàlida! (entre -180 i 180)");
                        longitudUser = 0;
                    } else {
                        flag = 1;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Error! Introdueix un número real!");
                    longitudUser = 0;
                }
            } while (flag == 0);
            //Retorna el id (long) del gimnàs més proper al jugador
            gimnasMesProper = distanciaMinima(legends, latitudUser, longitudUser);
            //Recerca del nom del gimnàs.
            for (int i = 0; i < legends.size(); i++) {
                if (legends.get(i).getId() == gimnasMesProper) {
                    gimnasMesProperr = legends.get(i).getGymName();
                }
            }

            String boss = " ";
            //Recerca del nom del Boss del Gimnàs
            for (int i = 0; i < poke.length; i++) {
                if (poke[i].getId() == gimnasMesProper) {
                    boss = poke[i].getNom();
                }
            }
            System.out.println("Gimnàs més proper: " + gimnasMesProperr + ". Començant Raid...");
            System.out.println("El boss de Raid " + boss + " us repta!");

            initiateLegendCapture (jugador, poke, gimnasMesProper, balls);

        }else{
            System.out.println("Ho sentim, però no té Pokéballs disponibles, pel que no pot buscar Pokémons.");
            System.out.println("Pot adquirir Pokéballs a la botiga!");
        }
    }

    /**
     * Funció que inicia la captura d'un Pokémon llegendari
     * @param jugador Informacio del jugador de la partida
     * @param poke Array amb tots els pokemons que existeixen
     * @param id ID del Pokémon que volem capturar
     * @param balls Array amb tots els tipus de pokeballs disponibles
     */
    public void initiateLegendCapture (Jugador jugador, Pokemon[] poke, long id, Ball[] balls) {
        int j = 0;
        int intents = 5;
        String choosedPokeball;
        String finePokeball;

        //Extreiem el ID del Pokémon llegendari
        for (int i = 0; i < poke.length; i++) {
            if (poke[i].getId().equals(id)) {
                j = i;
            }
        }

        //Control de Pokéballs i d'intents
        while (checkPokeballs(jugador) && intents > 0) {

            choosedPokeball = comprovaChoosedPokeball(jugador, intents);
            updatePokeballs (jugador, choosedPokeball);

            //Control de captura
            if (huntedLegendPokemon(choosedPokeball, balls, j, poke)){

                updateHuntedNumber(jugador, id);
                System.out.println("El pokémon " + poke[j].getNom() + " ha estat capturat!");
                return;
            }else{
                intents--;
                finePokeball = tellPokeballName(jugador, choosedPokeball);
                System.out.println("La " + finePokeball + " ha fallat!");
            }
        }

        if (!checkPokeballs(jugador)){
            System.out.println("No queden Pokéballs...");
        }else{
            System.out.println("El pokémon " + poke[j].getNom() + " ha escapat...");
        }
    }

    /**
     * Funció de captura d'un Pokémon llegendari
     * @param choosedPokeball Nom de la Pokeball escollida
     * @param balls Array amb tots els tipus de pokeballs disponibles
     * @param j Int del Pokémon a capturar
     * @param poke Array amb tots els pokemons que existeixen
     * @return true or false depenent de si s'ha completat la captura
     */
    public boolean huntedLegendPokemon(String choosedPokeball, Ball[] balls, int j, Pokemon[] poke){
        double randomValue;
        int probMyBall = 0;
        int probMyPokemon;
        double probCapture;

        Random random = new Random();
        //producció d'un long aleatòri entre 0 i 1.
        randomValue = random.nextDouble();

        for (int i = 0; i < balls.length; i++){

            if (balls[i].getName().equalsIgnoreCase(choosedPokeball)){
                probMyBall = balls[i].getCapture_rate();
            }
        }
        probMyPokemon = poke[j].getCaptureRate();
        probCapture = (Math.pow(probMyBall, 1.5) + Math.pow(probMyPokemon, Math.PI)) / ((double)4096);

        return (probCapture > randomValue);
    }

    /**
     * Càlcul de la distància mínima entre la posició de l'usuari i el gimnàs més proper
     * @param legends Array amb la informació de la posició de tots els gimnasos
     * @param latitudUser Latitud introduïda per l'usuari
     * @param longitudUser Longitud introduïda per l'usuari
     * @return Long amb el ID del gimnàs més proper
     */
    public long distanciaMinima (ArrayList<Legend> legends, double latitudUser, double longitudUser){
        double distanciaMinima = 0;
        int flag = 0;
        double latitudGymMesProper = 0;
        double longitudGymMesProper = 0;
        long gimnasProper = 0;

        //Inicialització de la distància mínima. Càlcul entre la distància del Player i el primer gimnàs que es troba al ArrayList
        for (int i = 0; i < legends.size() || flag == 0; i++){

            if (legends.get(i).getKind().equals("legendary")){
                latitudGymMesProper = legends.get(i).getLatitude();
                longitudGymMesProper = legends.get(i).getLongitude();
                flag = 1;
                distanciaMinima = distance(latitudUser, longitudUser, latitudGymMesProper, longitudGymMesProper);
            }
        }
        //Càlcul de la distància mínima
        for (int i = 0; i < legends.size(); i++){
            double latitudGym;
            double longitudGym;

            if (legends.get(i).getKind().equals("legendary")){
                latitudGym = legends.get(i).getLatitude();
                longitudGym = legends.get(i).getLongitude();

                if (distance(latitudUser, longitudUser, latitudGym, longitudGym) < distanciaMinima){
                    latitudGymMesProper = latitudGym;
                    longitudGymMesProper = longitudGym;
                    distanciaMinima = distance(latitudUser, longitudUser, latitudGym, longitudGym);
                }
            }
        }
        //extreiem la posicio del gimnàs més proper
        for (int i = 0; i < legends.size(); i++){
            if (legends.get(i).getKind().equals("legendary")){
                if ((legends.get(i).getLatitude() == latitudGymMesProper) && (legends.get(i).getLongitude() == longitudGymMesProper)){
                    gimnasProper = legends.get(i).getId();
                }
            }
        }
        return gimnasProper;
    }

    /**
     * Càlcul de la distància entre dos punts mitjançant la Fórmula de Haversine
     * @param startLat latitud inicial
     * @param startLong longitud inicial
     * @param endLat latitud final
     * @param endLong longitud final
     * @return Double amb la distancia entre els dos punts
     */
    public double distance(double startLat, double startLong, double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    /**
     * Operació interna per al càlcul de la distància entre dos punts. Forma part del càlcul de la Fórmula Haversine
     * @param val Longitud
     * @return Double amb el valor de l'operació
     */
    public double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    /**
     * OPCIO 6: Veure missions en progress
     * @param jugador Informacio del jugador de la partida
     * @param mythicals Array amb tots els pokemons mythics que existeixen
     * @param poke Array amb tots els pokemons que existeixen
     */
    public void recerquesEspecials (Jugador jugador, ArrayList <Mythical> mythicals, Pokemon[] poke){
        if (hihaMissions(jugador, mythicals)){
            System.out.println("Recerques Especials:");
            System.out.println(" ");
            for (int i = 0 ; i < mythicals.size() ; i++){
                if (showMission(jugador, mythicals, i)){
                    String name = idToName(mythicals.get(i).getId(), poke);
                    System.out.println("    - " + mythicals.get(i).getResearchName() + " (" + name + "):");
                    for (int j = 0 ; j < mythicals.get(i).getTarget().size() ; j++){
                        long idl = mythicals.get(i).getTarget().get(j);
                        int idi = (int)idl;
                        float capturats = jugador.getNumHunted()[(idi)-1];
                        float progress = (capturats / (float)mythicals.get(i).getQuantity().get(j)) * 100;
                        System.out.println("        * Capturar  " + idToName(mythicals.get(i).getTarget().get(j), poke) + ": " + (int)capturats + "/" + mythicals.get(i).getQuantity().get(j) + " (" + (int)progress + "%)");
                    }
                    System.out.println(" ");
                }
            }
        }else{
            System.out.println("No hi han missions començades!");
        }
    }

}

