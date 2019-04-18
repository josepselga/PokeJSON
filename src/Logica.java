/** Classe amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Logica {

    private static final int EARTH_RADIUS;

    static {
        EARTH_RADIUS = 6371;
    }

    public Logica() {
    }

    //Funcio que converteix el nom d'un pokemon a id numeric
    private long nameToID(String name, Pokemon[] poke) {
        long id = -1;

        for (int i = 0; i < poke.length; i++){
            if (poke[i].getNom().equals(name)){
                id = poke[i].getId();
            }
        }
        return id;
    }

    //Funcio que converteix l'id d'un pokemon al seu nom
    private String idToName(Long id, Pokemon[] poke){
        String name;
        String nameCap = null;
        for (int i = 0; i < poke.length; i++){
            if (poke[i].getId().equals(id)){
                name = poke[i].getNom();
                nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }
        return nameCap;
    }

    //Funcio que ens diu si el pokemon es mitic o no
    public boolean itsMythic (ArrayList<Mythical> mythicals, long id) {

        boolean flag = false;

        for (int i = 0; i < mythicals.size(); i++) {

            if (id == mythicals.get(i).getId()) {
                flag = true;
            }
        }

        return flag;
    }

    //Funcio que ens diu si el pokemon es llegendari o no
    public boolean itsLegend (ArrayList<Legend> legends, long id) {

        boolean flag = false;

        for (int i = 0; i < legends.size(); i++) {

            if (id == legends.get(i).getId()) {
                flag = true;
            }
        }

        return flag;
    }

    //Proces que duu a terme la captura d'un Pokemon
    public void initiateCapture(Jugador jugador, Pokemon[] poke, long id, Ball[] balls, ArrayList<Mythical> mythicals){

        int j = 0;
        int intents = 5;
        String choosedPokeball = " ";
        String finePokeball = " ";

        for (int i = 0; i < poke.length; i++) {

            if (poke[i].getId().equals(id)) {
                j = i;
            }
        }
        String name = idToName(id, poke);
        System.out.println ("Un "+name+" salvatge aparegué!");

        while (checkPokeballs(jugador) && intents > 0) {

            choosedPokeball = comprovaChoosedPokeball(jugador, intents);                        //Quina pokeball vols utilitzar?

            updatePokeballs (jugador, choosedPokeball);

            if (huntedPokemon(choosedPokeball, balls, j, poke)){
                updateHuntedNumber(jugador, id);
                System.out.println("El pokémon " + poke[j].getNom() + " ha estat capturat!");
                checkMissions (jugador, poke, mythicals);
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

    public String tellPokeballName (Jugador jugador, String choosedPokeball) {

        String pokeball = " ";

        for (int i = 0; i < jugador.getNomBalls().length; i++) {

            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)) {
                pokeball = jugador.getNomBalls()[i];
            }
        }
        return pokeball;
    }

    public void updateHuntedNumber (Jugador jugador, Long id){

        for (int i = 0; i < jugador.getIdHunted().length; i++){

            if (jugador.getIdHunted()[i].equals(id)){
                jugador.updateHunted(i);
            }
        }
    }

    public boolean huntedPokemon(String choosedPokeball, Ball[] balls, int j, Pokemon[] poke){

        double randomValue = 0;
        int probMyBall = 0;
        int probMyPokemon = 0;
        double probCapture = 0;

        Random random = new Random();
        //producció d'un long aleatòri entre 0 i 1.
        randomValue = random.nextDouble();

        for (int i = 0; i < balls.length; i++){

            if (balls[i].getName().equalsIgnoreCase(choosedPokeball)){
                probMyBall = balls[i].getCapture_rate();
            }
        }
        probMyPokemon = poke[j].getCaptureRate();
        probCapture = (probMyBall/(double)256) + (probMyPokemon/(double)2048);
        return (probCapture > randomValue);
    }

    public void updatePokeballs (Jugador jugador, String choosedPokeball){

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)){
                jugador.restaBall(i);
            }
        }



        }

    //comprovo que queden pokeballs de les que ha triat el usuari per llançar
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

    //comprobem si existeix una pokeball
    public boolean correctPokeballName (Jugador jugador, String choosedPokeball) {
        boolean ok = false;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNomBalls()[i].equalsIgnoreCase(choosedPokeball)) {
                ok = true;
            }
        }
        return ok;
    }

    //Funcio per saber si el jugador disposa de pokeballs
    public boolean checkPokeballs (Jugador jugador) {

        boolean pokeballs = false;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNumBalls()[i] > 0) {
                pokeballs = true;
            }
        }
        return pokeballs;
    }

    //Funcio que calcula el total de pokeballs que disposa el jugador
    private int totalPokeballs (Jugador jugador){
        int total = 0;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {
            total += jugador.getNumBalls()[i];
        }
        return total;
    }

    //Funcio que demana un pokemon al usuari i retorna l'id
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
        }catch(NumberFormatException e){            //Si ha introduit el nom del pokemon, ho pasem a id
            id = nameToID(input.toLowerCase(), poke);
            return id;
        }
    }

    //Funcio que comprova si existeix pokemon
    public boolean existeixPokemon(Long id , Pokemon[] poke){
        for(int i = 0 ; i < poke.length ; i++){
            if(poke[i].getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    //Funcio que ens diu si cal mostrar una missio o no (si esta començada)
    private boolean showMission(Jugador jugador, ArrayList<Mythical> mythicals, int i){
        //boolean completed = false;
        boolean started = false;

        //Comprobem que la missio estigui començada
        for (int j = 0 ; j < mythicals.get(i).getTarget().size() ; j++){

            long idl = mythicals.get(i).getTarget().get(j);
            int idi = (int) idl;

            if (jugador.getNumHunted()[idi-1] > 0){
                started = true;
                /*if (jugador.getNumHunted()[idi-1] == mythicals.get(i).getQuantity().get(j)){
                    completed = true;
                }*/
            }

            /*if (completed && jugador.getNumHunted()[idi-1] >= mythicals.get(i).getQuantity().get(j)){
                completed = true;
            }else{
                completed = false;
            }*/

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

    //Funcio que comproba si hi ha missions començades
    private boolean hihaMissions(Jugador jugador, ArrayList<Mythical> mythicals){

        for (int i = 0 ; i < mythicals.size() ; i++){
            if (showMission(jugador, mythicals, i)){
                return true;
            }
        }
        return false;
    }

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

    private boolean huntedMythicPokemon() {
        double randomValue = 0;
        double probCapture = 1;

        Random random = new Random();
        //producció d'un long aleatòri entre 0 i 1.
        randomValue = random.nextDouble();
        return (probCapture > randomValue);
    }

    // OPCIO 1: afegir monedes al jugador
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

    // OPCIO 2: comprar Pokeballs
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

    //OPCIO 3: consultar inventari del jugador
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

    //OPCIO 4: buscar Pokemon salvatge
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

    //OPCIO 5: fer raid per buscar llegendaris
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

    public void initiateLegendCapture (Jugador jugador, Pokemon[] poke, long id, Ball[] balls) {

        int j = 0;
        int intents = 5;
        String choosedPokeball = " ";
        String finePokeball = " ";

        for (int i = 0; i < poke.length; i++) {

            if (poke[i].getId().equals(id)) {
                j = i;
            }
        }

        while (checkPokeballs(jugador) && intents > 0) {

            choosedPokeball = comprovaChoosedPokeball(jugador, intents);

            updatePokeballs (jugador, choosedPokeball);

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

    //Càlcul de la distància entre dos punts mitjançant la Fórmula de Haversine
    public double distance(double startLat, double startLong, double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    //OPCIO 6: veure missions en progress
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

