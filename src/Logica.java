/** Clase amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class Logica {

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

    //Funcio que ens diu si el pokemon es mistic o no
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

    //Proces que du a terme la captura d'un Pokemon
    public void initiateCapture (Jugador jugador, Pokemon[] poke, long id){

        int j = 0;
        int intents = 5;
        String choosedPokeball = " ";

        for (int i = 0; i < poke.length; i++) {

            if (poke[i].getId() == id) {
                i = j;
            }
        }
        System.out.println ("Un "+poke[j].getNom()+" salvatge aparegué!");

        while (checkPokeballs(jugador)) {

            while (intents > 0) {

                do {
                    System.out.println("Queden" + totalPokeballs(jugador) + " Pokéballs i " + intents + "/5 intents. Quin tipus de Pokéball vol fer servir?");
                    Scanner entrada = new Scanner(System.in);
                    choosedPokeball = entrada.next();
                }while (correctPokeballName(jugador, choosedPokeball));



            }
        }


    }

    //comprobem si existeix una pokeball
    public boolean correctPokeballName (Jugador jugador, String choosedPokeball) {
        boolean ok = false;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {

            if (jugador.getNomBalls()[i].equals(choosedPokeball)) {
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
    public long demanaPokemon(Pokemon[] poke){
        //Demanem Pokemon
        System.out.println("De quin Pokémon vols informació?");
        Scanner teclat = new Scanner (System.in);
        String input = teclat.next();
        Long id;

        try{
            int num = Integer.parseInt(input);
            id = (long)num;
            return id;
        }catch(NumberFormatException e){            //Si no es el nom del pokemon, ho pasem a id
            id = nameToID(input, poke);
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
        boolean completed = false;
        boolean started = false;
        //Comprobem que la missio estigui començada
        for(int j = 0 ; j < mythicals.get(i).getTarget().size() ; j++){
            long idl = mythicals.get(i).getTarget().get(j);
            int idi = (int) idl;
            if(jugador.getNumHunted()[idi-1] > 0){
                started = true;
                if(jugador.getNumHunted()[idi-1] == mythicals.get(i).getQuantity().get(j)){
                    completed = true;
                }
            }
            if(completed && jugador.getNumHunted()[idi-1] >= mythicals.get(i).getQuantity().get(j)){
                completed = true;
            }else{
                completed = false;
            }
        }
        if(started && !completed){
            return true;
        }else{
            return false;
        }
    }

    //Funcio que comproba si hi ha missions començades
    private boolean hihaMissions(Jugador jugador, ArrayList<Mythical> mythicals){

        for(int i = 0 ; i < mythicals.size() ; i++){
            if(showMission(jugador, mythicals, i)){
                return true;
            }
        }
        return false;
    }

    public void checkMissions (Jugador jugador, Pokemon[] poke, ArrayList<Mythical> mythicals){}

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
    }

    //OPCIO 3: consultar inventari del jugador
    public void consultaInventari(Jugador jugador) {
        System.out.println("Inventari:");
        for (int i = 0; i < jugador.getNomBalls().length; i++) {
            String name = jugador.getNomBalls()[i];
            String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            if (jugador.getNumBalls()[i] != 0) {
                if (jugador.getNumBalls()[i] > 1) {
                    System.out.println("   - " + jugador.getNumBalls()[i] + "x " + nameCap + "s");
                } else {
                    System.out.println("   - " + jugador.getNumBalls()[i] + "x " + nameCap);
                }
            } else {
                if (i == 0) {
                    System.out.println("No disposes d'inventari!");
                }
            }
        }
    }

    //OPCIO 4: buscar Pokemon salvatge
    public void buscaPokemonSalvatge(Pokemon[] poke, ArrayList<Legend> legends, ArrayList<Mythical> mythicals, Jugador jugador) {

        if (checkPokeballs (jugador)) {

            long id = demanaPokemon(poke);

            //Començem captura
            if (existeixPokemon(id, poke)){

                if (!itsLegend(legends, id) && !itsMythic(mythicals, id)) {
                    initiateCapture (jugador, poke, id);
                    checkMissions (jugador, poke, mythicals);

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
    public void ferRaid () {

    }

    //OPCIO 6: veure missions en progress
    public void recerquesEspecials (Jugador jugador, ArrayList <Mythical> mythicals, Pokemon[] poke){
        if(hihaMissions(jugador, mythicals)){
            System.out.println("Recerques Especials:");
            System.out.println(" ");
            for(int i = 0 ; i < mythicals.size() ; i++){
                if(showMission(jugador, mythicals, i)){
                    String name = idToName(mythicals.get(i).getId(), poke);
                    System.out.println("    - " + mythicals.get(i).getResearchName() + " (" + name + "):");
                    for(int j = 0 ; j < mythicals.get(i).getTarget().size() ; j++){
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

