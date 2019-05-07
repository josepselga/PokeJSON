import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Classe generadora de fitxers html
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class GenerarFitxers {

    /**
     * conexio amb la API de Pokemon per obtenir les dades necesaries
     * @param sURL String amb la url de la api a conectar-se
     * @return JsonObject que conte la info que hem consultat de la api
     * @throws IOException
     */
    private JsonObject consultaAPI(String sURL) throws IOException {
        URL url = null;
        URLConnection request = null;

        // Iniciem conexio amb la API
        url = new URL(sURL);
        request = url.openConnection();
        request.connect();

        // Convertim objecte json
        JsonParser jp = new JsonParser();
        JsonElement root = null;
        root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject objecte = root.getAsJsonObject();
        return objecte;
    }

    /**
     * Genera un fitxer html amb la llista de pokemons capturats pel jugador
     * @param jugador informacio del jugador on trobem els pokemons capturats
     */
    public void informeCapturats(Jugador jugador){
        FileOutputStream pokemonsCapturats;
        PrintStream result;
        JsonObject pokeInfo;
        JsonObject sprites;
        int totalCapturats = 0;
        String name;
        String image;
        int numHunted;

        try {
            //Inicialitzem fitxer HTML
            pokemonsCapturats = new FileOutputStream("pokemonsCapturats.html");
            result = new PrintStream(pokemonsCapturats);
            result.println("<!DOCTYPE html>" + "<html>" + "<head>" + "<title>Pokemons Capturats</title>" + "<style>" + "body {" + "background-color: white;" + "text-align: left;" + "color: black;" + "font-family: Arial;" + "}" + "</style>" + "</head>" + "<body>");

            //Calculem pokemons capturats
            for (int i = 0 ; i < jugador.getNumHunted().length ; i++){
                if(jugador.getNumHunted()[i] > 0){
                    totalCapturats += jugador.getNumHunted()[i];
                }
            }

            //Si em capturat pokemons obtenim info de la API i la posem al fitxer HTML
            if(totalCapturats > 0){
                result.println("<h1>Pokémons capturats: " + totalCapturats + "</h1>");
                for(int i = 0 ; i < jugador.getNumHunted().length ; i++){
                    if(jugador.getNumHunted()[i] > 0){
                        //Connectem amb la pagina de la API que conte la info del pokemon
                        pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon/" + jugador.getIdHunted()[i] + "/");
                        name = pokeInfo.get("name").getAsString();
                        String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
                        sprites = (JsonObject) pokeInfo.get("sprites");
                        image = sprites.get("front_shiny").getAsString();
                        numHunted = jugador.getNumHunted()[i];

                        //Introduim la info de la API al fitxer HTML
                        result.println("<p><img src=" + image + " align=middle alt=IMG err/><b>  " + nameCap +"</b> x" + numHunted + "</p>");
                    }
                }

             //Si no hem capturat cap pokemon ho diem al fitxer HTML
            }else{
                result.println("<h1>No has capturat cap pokemon</h1>");
            }
            result.println("<p><img src=http://i66.tinypic.com/cuvcg.jpg align=middle  alt=\"PokeJson\" style=\"width:90px\"></p></body>" + "<html>");
            System.out.println("Fitxer HTML generat");

            //Obrim fitxer HTML
            try {
                URI uri = new URI("pokemonsCapturats.html");
                uri.normalize();
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                System.out.println(" Ja pots obrir el fitxer HTML (pokemonsCapturats.html)");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un fitxer html amb la informacio detallada d'un pokemon
     * @param poke Array amb tots els pokemons existents
     * @param logic Clase logica per utilitzar la funcio de demanar pokemon
     */
    public void infoPokemon(Pokemon[] poke, Logica logic){
        String description = null;

        long id = logic.demanaPokemon(poke, "info");
        if(logic.existeixPokemon(id, poke)){
            try {
                //Connectem amb la API per extreure la info del pokemon desitjat
                JsonObject pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon/" + id + "/");
                String name = pokeInfo.get("name").getAsString();
                String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
                id = pokeInfo.get("id").getAsLong();
                JsonObject sprites = (JsonObject) pokeInfo.get("sprites");
                String image = sprites.get("front_shiny").getAsString();
                long height = pokeInfo.get("height").getAsLong();
                long weight= pokeInfo.get("weight").getAsLong();
                long base_experience = pokeInfo.get("base_experience").getAsLong();

                //Connectem API per llegir flavour text de pokemon-species
                pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon-species/" + id + "/");
                JsonArray flavor_text_entries = (JsonArray) pokeInfo.get("flavor_text_entries");
                for(int i = 0 ; i< flavor_text_entries.size() ; i++){
                    JsonObject flavor_text = (JsonObject) flavor_text_entries.get(i);
                    JsonObject language = (JsonObject) flavor_text.get("language");
                    if(language.get("name").getAsString().equals("en")){
                        description = flavor_text.get("flavor_text").getAsString();
                    }
                }

                //Generar fitxer html amb lka info extreta de la API
                FileOutputStream informePokemon;
                PrintStream result;
                informePokemon = new FileOutputStream("infoPokemon.html");
                result = new PrintStream(informePokemon);
                result.println("<!DOCTYPE html>" + "<html>" + "<head>" + "<title>" + nameCap + "</title>" + "<style>" + "body {" + "background-color: white;" + "text-align: left;" + "color: black;" + "font-family: Arial;" + "}" + "</style>" + "</head>" + "<body>");
                result.println("<body>" + "<h1>" + nameCap + " (" + id + ")</h1>" + "<p><img src=" + image + " alt= IMG err style=\"width:300px\"></p>" + "<p>" + description + "</p>" + "<ul>\n" + "<li>" + (float)height/10 + " m</li>" + "<li>" + (float)weight/10 + " kg</li>" + "<li>" + base_experience + " xp</li>" + "</ul>");
                result.println("<p><img src=http://i66.tinypic.com/cuvcg.jpg align=middle  alt=\"PokeJson\" style=\"width:90px\"></p></body>" + "<html>");
                System.out.println("Fitxer HTML generat");

                //Intentem obrir fitxer HTML
                try {
                    URI uri = new URI("infoPokemon.html");
                    uri.normalize();
                    Desktop.getDesktop().browse(uri);
                } catch (IOException e) {
                    System.out.println(" Ja pots obrir el fitxer HTML (infoPokemon.html)");
                }

            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("Ho sentim, però aquest Pokémon no existeix (encara).");
        }
    }

}