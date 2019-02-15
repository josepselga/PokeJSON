import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/** Clase main PokeJson
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */


public final class Main {
    public static void main(String[] args) {

        try{
            Menu menu = new Menu();
            LecturaFitxers json = new LecturaFitxers();
            json.llegeixFitxers();
            Jugador jugador = new Jugador(json.getPoke(), json.getBalls());
            Logica logic = new Logica();
            GenerarFitxers fitxers = new GenerarFitxers();

            System.out.println ("Benvingut a PokéJSON, aconsegueix-los tots!");

            do{

                do{
                    menu.mostraMenu();
                }while (!menu.comprovaOpcio());

                switch (menu.getOpcio()){
                    case 1:
                        logic.afegeixMonedes(jugador);
                        break;
                    case 2:
                        logic.compraObjectes(jugador, json.getBalls());
                        break;
                    case 3:
                        logic.consultaInventari(jugador);
                        break;
                    case 4:
                        logic.buscaPokemonSalvatge(json.getPoke(),json.getLegend(), json.getMythical(), jugador, json.getBalls());
                        break;
                    case 5:
                       logic.ferRaid(jugador, json.getLegend());
                        break;
                    case 6:
                        logic.recerquesEspecials(jugador, json.getMythical(), json.getPoke());
                        break;
                    case 7:
                        fitxers.informeCapturats(jugador);
                        break;
                    case 8:
                        fitxers.infoPokemon(json.getPoke(), logic);
                        break;
                    case 9:
                        System.out.println("Adeu! Ens veiem aviat :)");
                        break;
                }

            }while (menu.getOpcio() != 9);

        }catch (FileNotFoundException e) {
            System.err.println("Error al intentar obrir fitxer");
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
