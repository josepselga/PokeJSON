/** Clase amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

import java.util.Scanner;

public class Logica {

    //Atributs de la classe
    private Pokemon[] pokemons;
    Jugador jugador = new Jugador();

    //Metodes de la clase
    public void afegeixMonedes(){

        double monedes = -1;
        double preu = 0;

        System.out.println("Quantes monedes vols comprar?");
        Scanner entrada = new Scanner (System.in);

        do {
            try {
                monedes = entrada.nextInt();
                if (monedes < 0) {
                    System.out.println("Error! S'han d'introduïr valors estrictament positius!");
                    monedes = 0;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error! Introdueix un número!");
                monedes = 0;
            }
        }while (monedes <= 0);

        if (monedes < 250){
            preu = monedes * 0.01;
        }

        if (monedes >= 250 && monedes < 500){
            preu = monedes * 0.009;
        }

        if (monedes >= 500 && monedes < 1000){
            preu = monedes * 0.007;
        }

        if (monedes >= 1000 && monedes < 10000){
            preu = monedes * 0.005;
        }

        if (monedes >= 10000){
            preu = monedes * 0.0025;
        }

        System.out.println ("El preu total es de " + preu + "€. Confirma la compra? (Y/N)");
        char confirmacio = entrada.next().charAt(0);

        if (confirmacio == 'y'){
            jugador.setMonedes(jugador.getMonedes() + (int)monedes);   //S'ha de utilitzar un setter de la classe jugador (no podem accedir a la variable monedes)
            System.out.println ("S'han afegit " + (int)monedes + " monedes al seu compte.");
        }else{
            System.out.println ("Compra cancel·lada.");
        }

    }

    public void compraObjectes(){
        Scanner entrada = new Scanner (System.in);

        System.out.println ("Teniu " + jugador.getMonedes() + " monedes.");
        System.out.println ("Pokéballs disponibles:");
        for(int i = 0; i < 5; i++){

        }
        char opcio = entrada.next().charAt(0);
        System.out.println ("Escull una opció:");

        //Comprobar opcio correcte

        System.out.println ("Quantes unitats en vol comprar?");
        int unitats = entrada.nextInt();

        //Comprobar unitats

        if(){
            System.out.println ("S'han afegit " + unitats + tipus + " al seu compte a canvi de " + preu + " monedes.");
        }else{
            System.out.println ("Ho sentim, però no disposa de suficients monedes.");
        }
    }

    public void consultaInventari(){

    }

}
