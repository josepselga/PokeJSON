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
    public long nameToID(String name, Pokemon[] poke) {
        long ID = -1;

        for (int i = 0; i < poke.length; i++){

            if (poke[i].getNom().equals(name)){
                ID = poke[i].getId();
            }
        }
        return ID;
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

    private int totalPokeballs (Jugador jugador){
        int total = 0;

        for (int i = 0; i < jugador.getNumBalls().length; i++) {
            total += jugador.getNumBalls()[i];
        }
        return total;
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
            System.out.println("   " + (char) (97 + i) + ")" + balls[i].getName() + ":    " + balls[i].getPrice() + " monedes");
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
        int unitats = entrada.nextInt();
        //Comprobar numero enter

        //Comprobar saldo suficient
        int cost = unitats * balls[opcio - 97].getPrice();
        if (cost <= jugador.getMonedes()) {
            if (unitats == 1) {
                System.out.println("S'ha afegit " + unitats + " " + balls[opcio - 97].getName() + " al seu compte a canvi de " + balls[opcio - 97].getPrice() + " monedes.");
            } else {
                System.out.println("S'han afegit " + unitats + " " + balls[opcio - 97].getName() + "s al seu compte a canvi de " + balls[opcio - 97].getPrice() + " monedes.");
            }
            //Afegir balls al jugador
            jugador.getNumBalls()[opcio - 97] += unitats;
            jugador.setMonedes(jugador.getMonedes() - cost);
        } else {
            System.out.println("Ho sentim, però no disposa de suficients monedes.");
        }
    }

    //OPCIO 3: consultar inventari del jugador
    public void consultaInventari(Jugador jugador) {
        System.out.println("Inventari:");
        for (int i = 0; i < jugador.getNomBalls().length; i++) {
            if (jugador.getNumBalls()[i] != 0) {
                if (jugador.getNumBalls()[i] > 1) {
                    System.out.println("   - " + jugador.getNumBalls()[i] + "x " + jugador.getNomBalls()[i] + "s");
                } else {
                    System.out.println("   - " + jugador.getNumBalls()[i] + "x " + jugador.getNomBalls()[i]);
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

        boolean trobat = true;

        if (checkPokeballs (jugador)) {

            Scanner entrada = new Scanner(System.in);
            boolean idReaded = true;                                                                                    //Flag pe saber si ens han introduït un int o un String
            System.out.println ("Quin Pokémon vol buscar?");
            long id = 0;
            String nom = " ";

            try {                                                                                                       //Ens poden introduir un ID int o un nom String.
                id = entrada.nextLong();
            }catch (java.util.InputMismatchException e) {
               nom = entrada.next();
               id = nameToID(nom, poke);
            }
            //Comprovar si es num o string

            //Si es String pasar a id

            //Comprovar si existeix pokemon
            for (int i = 0; i < poke.length; i++) {

                if (poke[i].getId() == id){
                    trobat = true;
                    id = poke[i].getId();

                }
            }
            //Començem captura
            if (trobat){

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
    public void recerquesEspecials (Jugador jugador, ArrayList < Mythical > mythicals){
        System.out.println("Recerques Especials:");


    }
}

