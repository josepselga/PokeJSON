import java.util.Scanner;

public class Logica {
    private Pokemon[] pokemons;

    public void afegeixMonedes(){

        int monedes;

        System.out.println("Quantes monedes vols comprar?");
        Scanner entrada = new Scanner (System.in);

        try {
            monedes = entrada.nextInt();
            if (monedes < 0){
                System.out.println("Error! No pots introduïr nombres negatius!");
                monedes = 0;
            }
        }catch (java.util.InputMismatchException e) {
            System.out.println("Error! Introdueix un número positiu!");
            monedes = 0;
        }



    }
}
