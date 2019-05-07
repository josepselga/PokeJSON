import java.util.Scanner;

/**
 *Conté tot el que està relacionat amb els menús del programa.
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Menu {

    //Atributs de la clase
    private int opcio;

    //Constructor
    public Menu(){
        opcio = 0;
    }

    //Getter
    public int getOpcio() {
        return opcio;
    }

    /**
     * Mostra per pantalla totes les opcions disponibles del PokeJson
     */
    public void mostraMenu(){
        System.out.println (" ");
        System.out.println ("   1. Afegir monedes ");
        System.out.println ("   2. Comprar objectes");
        System.out.println ("   3. Consultar inventari");
        System.out.println ("   4. Buscar Pokémon salvatge");
        System.out.println ("   5. Fer Raid");
        System.out.println ("   6. Recerques especials actuals");
        System.out.println ("   7. Informe de capturats");
        System.out.println ("   8. Informació detallada");
        System.out.println ("   9. Sortir");
        System.out.println (" ");
        System.out.println ("Seleccioni una opció: ");
    }

    /**
     * Llegeix la opcio seleccionada per l'usuari i comprova que la opcio
     * sigui correcte
     * @return boolea que indica si la opcio es correcte o no
     */
    public boolean comprovaOpcio (){
        Scanner entrada = new Scanner (System.in);

        try {
            opcio = entrada.nextInt();
            if (opcio < 1 || opcio > 9){
                System.out.println("Error! Introdueix un número entre 1 i 9!");
                opcio = 0;
                return false;
            }else{
                return true;
            }
        }catch (java.util.InputMismatchException e) {
            System.out.println("Error! Introdueix un número!");
            opcio = 0;
            return false;
        }
    }
}
