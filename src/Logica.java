/** Clase amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

import java.util.Scanner;

public class Logica {

    //Atributs de la classe

    //Metodes de la clase
    public void afegeixMonedes(Jugador jugador){

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

    public void compraObjectes(Jugador jugador, Ball balls[]){
        Scanner entrada = new Scanner (System.in);

        System.out.println ("Teniu " + jugador.getMonedes() + " monedes.");
        System.out.println ("Pokéballs disponibles:");
        int i;
        for(i = 0; i < balls.length; i++){
            System.out.println ("   " + (char)(97+i) + ")" + balls[i].getName() + ":    " + balls[i].getPrice() + " monedes");
        }
        System.out.println (" ");

        //Comprobar opcio correcte
        char opcio;
        do{
            System.out.println ("Escull una opció:");
            opcio = entrada.next().charAt(0);
            if(opcio > (97+i) || opcio < 97){
                System.out.println ("Introdueix una opcio valida.");
            }
        }while(opcio > (97+i) || opcio < 97);

        System.out.println ("Quantes unitats en vol comprar?");
        int unitats = entrada.nextInt();
        //Comprobar numero enter

        //Comprobar saldo suficient
        int cost = unitats * balls[opcio-97].getPrice();
        if(cost <= jugador.getMonedes()){
            if(unitats == 1){
                System.out.println ("S'ha afegit " + unitats + " " + balls[opcio-97].getName() + " al seu compte a canvi de " + balls[opcio-97].getPrice() + " monedes.");
            }else{
                System.out.println ("S'han afegit " + unitats + " " + balls[opcio-97].getName() + "s al seu compte a canvi de " + balls[opcio-97].getPrice() + " monedes.");
            }
            //Afegir balls al jugador
            jugador.setMonedes(jugador.getMonedes()-cost);
        }else{
            System.out.println ("Ho sentim, però no disposa de suficients monedes.");
        }
    }

    public void consultaInventari(Jugador jugador){
        System.out.println ("Inventari:");
        for(int i = 0 ; i < jugador.getBall().size() ; i++){
            System.out.println ("   - " + jugador.getBall().get(i).getPrice() + "x " + jugador.getBall().get(i).getName());
        }
    }
}
