import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private int opcio;

    public Menu(){
        opcio = 0;
    }

    public int getOpcio() {
        return opcio;
    }

    public void mostraMenu(){
        System.out.println ("\nBenvingut a PokéJSON, aconsegueix-los tots!\n\t1. Afegir monedes\n\t2. Comprar objectes\n\t" +
                "3. Consultar inventari\n\t4. Buscar Pokémon salvatge\n\t5. Fer Raid\n\t6. Recerques especials actuals\n\t" +
                "7. Informe de capturats\n\t8. Informació detallada\n\t9. Sortir\n\nSeleccioni una opció: ");
    }

    public void comprovaOpcio (){
        Scanner entrada = new Scanner (System.in);
        int opcio = 0;

        try {
            opcio = entrada.nextInt();
            if (opcio < 1 || opcio > 9){
                System.out.println("Error! Opció incorrecta!");
                opcio = 0;
            }
        }catch (java.util.InputMismatchException e) {
            System.out.println("Error! Introdueix un número!");
            opcio = 0;
        }
    }
}
