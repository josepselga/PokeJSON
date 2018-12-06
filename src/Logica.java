import java.util.Scanner;

public class Logica {

    private Pokemon[] pokemons;

    public void afegeixMonedes(){

        int monedes = -1;
        float preu = 0;
        char confirmacio;

        Jugador jugador = new Jugador();
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
            preu = monedes * 0.005
        }

        if (monedes >= 10000){
            preu = monedes * 0.0025;
        }

        System.out.println ("El preu total es de" + preu + "€. Confirma la compra? (Y/N)");
        confirmacio = entrada.nextInt();

        if (confirmacio == 'y'){
            Jugador.monedes =+ monedes;   //S'ha de utilitzar un setter de la classe jugador (no podem accedir a la variable monedes)
        }

    }
}
