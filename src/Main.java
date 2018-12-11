/** Clase main PokeJson
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

//Llibreries
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public final class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        Jugador jugador = new Jugador();    /**S'inicialitza fora del bucle perque no s'actualitzin les monedes a 100*/
        Logica logic = new Logica();

        try{

            //Inicialitzem fitxers json
            Gson gson = new Gson();
            JsonReader json1 = new JsonReader(new FileReader("resources/balls.json"));
            JsonReader json2 = new JsonReader(new FileReader("resources/legends.json"));
            JsonReader json3 = new JsonReader(new FileReader("resources/poke.json"));

            //Ball ball = gson.fromJson(json1, Ball.class);
            //Legend legend = gson.fromJson(json2, Legend.class);
            //Pokemon poke = gson.fromJson(json3, Pokemon.class);

            do{
                menu.mostraMenu();
                menu.comprovaOpcio();

                switch (menu.getOpcio()){
                    case 1:
                        logic.afegeixMonedes();
                        break;
                    case 2:
                        logic.compraObjectes();
                        break;
                    case 3:
                        logic.consultaInventari();
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        System.out.println("Adeu! Ens veiem aviat :)");
                        break;
                }

            }while (menu.getOpcio() != 9);

        }catch (FileNotFoundException e) {
            System.err.println("Error al intentar obrir fitxer");
        }
    }
}
