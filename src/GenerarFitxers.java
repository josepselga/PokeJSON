import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

public class GenerarFitxers {

    private JsonObject consultaAPI(String sURL) throws IOException {
        // Connect to the URL using java's native library
        URL url = null;
        URLConnection request = null;
        // Iniciem conexio amb la API
        url = new URL(sURL);
        request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser();       //from gson
        JsonElement root = null;                //Convert the input stream to a json element
        root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject objecte = root.getAsJsonObject(); //May be an array, may be an object.
        return objecte;
    }

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
            pokemonsCapturats = new FileOutputStream("pokemonsCapturats.html");
            result = new PrintStream(pokemonsCapturats);
            result.println("<!DOCTYPE html>" + "<html>" + "<head>" + "<title>Pokemons Capturats</title>" + "<style>" + "body {" + "background-color: white;" + "text-align: left;" + "color: black;" + "font-family: Arial;" + "}" + "</style>" + "</head>" + "<body>");
            for (int i = 0 ; i < jugador.getNumHunted().length ; i++){
                if(jugador.getNumHunted()[i] > 0){
                    totalCapturats += jugador.getNumHunted()[i];
                }
            }
            if(totalCapturats > 0){
                result.println("<h1>Pokémons capturats: " + totalCapturats + "</h1>");
                for(int i = 0 ; i < jugador.getNumHunted().length ; i++){
                    if(jugador.getNumHunted()[i] > 0){
                        pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon/" + jugador.getIdHunted()[i] + "/");
                        name = pokeInfo.get("name").getAsString();
                        String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
                        sprites = (JsonObject) pokeInfo.get("sprites");
                        image = sprites.get("front_shiny").getAsString();
                        numHunted = jugador.getNumHunted()[i];
                        result.println("<p><img src=" + image + " align=middle alt=IMG err/><b>  " + nameCap +"</b> x" + numHunted + "</p>");
                    }
                }

            }else{
                result.println("<h1>No has capturat cap pokemon</h1>");
            }
            result.println("<p><img src=http://i66.tinypic.com/cuvcg.jpg align=middle  alt=\"PokeJson\" style=\"width:90px\"></p></body>" + "<html>");
            System.out.println("Fitxer HTML generat");
            //Obrim fitxer HTML
            URI uri = new URI("pokemonsCapturats.html");
            uri.normalize();
            Desktop.getDesktop().browse(uri);
        } catch (IOException | URISyntaxException e) {
            System.out.println(" Ja pots obrir el fitxer HTML (pokemonsCapturats.html)");
        }
    }

    public void infoPokemon(Pokemon[] poke, Logica logic){
        String description = null;

        long id = logic.demanaPokemon(poke);
        System.out.println(id);
        if(logic.existeixPokemon(id, poke)){
            try {
                JsonObject pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon/" + id + "/");
                String name = pokeInfo.get("name").getAsString();
                String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
                id = pokeInfo.get("id").getAsLong();
                JsonObject sprites = (JsonObject) pokeInfo.get("sprites");
                String image = sprites.get("front_shiny").getAsString();
                long height = pokeInfo.get("height").getAsLong();
                long weight= pokeInfo.get("weight").getAsLong();
                long base_experience = pokeInfo.get("base_experience").getAsLong();

                //Llegim flavour text de pokemon-species
                pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon-species/" + id + "/");
                JsonArray flavor_text_entries = (JsonArray) pokeInfo.get("flavor_text_entries");
                for(int i = 0 ; i< flavor_text_entries.size() ; i++){
                    JsonObject flavor_text = (JsonObject) flavor_text_entries.get(i);
                    JsonObject language = (JsonObject) flavor_text.get("language");
                    if(language.get("name").getAsString().equals("en")){
                        description = flavor_text.get("flavor_text").getAsString();
                    }
                }

                //Generar fitxer html
                FileOutputStream informePokemon;
                PrintStream result;
                informePokemon = new FileOutputStream("infoPokemon.html");
                result = new PrintStream(informePokemon);
                result.println("<!DOCTYPE html>" + "<html>" + "<head>" + "<title>" + nameCap + "</title>" + "<style>" + "body {" + "background-color: white;" + "text-align: left;" + "color: black;" + "font-family: Arial;" + "}" + "</style>" + "</head>" + "<body>");
                result.println("<body>" + "<h1>" + nameCap + " (" + id + ")</h1>" + "<p><img src=" + image + " alt= IMG err style=\"width:300px\"></p>" + "<p>" + description + "</p>" + "<ul>\n" + "<li>" + (float)height/10 + " m</li>" + "<li>" + (float)weight/10 + " kg</li>" + "<li>" + base_experience + " xp</li>" + "</ul>");
                result.println("<p><img src=http://i66.tinypic.com/cuvcg.jpg align=middle  alt=\"PokeJson\" style=\"width:90px\"></p></body>" + "<html>");
                System.out.println("Fitxer HTML generat");
                //Obrim fitxer HTML si tenim windows
                URI uri = new URI("infoPokemon.html");
                uri.normalize();
                Desktop.getDesktop().browse(uri);

            } catch (IOException | URISyntaxException e) {
                System.out.println(" Ja pots obrir el fitxer HTML (infoPokemon.html)");
            }
        }else{
            System.out.println("Ho sentim, però aquest Pokémon no existeix (encara).");
        }
    }

}