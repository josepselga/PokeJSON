/** Conte tot lo relacionat amb els menus del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

import java.util.Scanner;

public class Menu {

    //Atributs de la clase
    private int opcio;

    //Metodes de la classe
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

    public boolean comprovaOpcio (){
        Scanner entrada = new Scanner (System.in);

        try {
            this.opcio = entrada.nextInt();
            if (opcio < 1 || opcio > 9){
                System.out.println("Error! Opció incorrecta!");
                this.opcio = 0;
                return false;
            }else{
                return true;
            }
        }catch (java.util.InputMismatchException e) {
            System.out.println("Error! Introdueix un número!");
            this.opcio = 0;
            return false;
        }
    }
}
