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
import java.util.Scanner;
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
                        sprites = (JsonObject) pokeInfo.get("sprites");
                        image = sprites.get("front_shiny").getAsString();
                        numHunted = jugador.getNumHunted()[i];
                        result.println("<p><img src=" + image + " align=middle alt=" + name + "/><b>  " + name +"</b> x" + jugador.getNumHunted()[i] + "</p>");
                    }
                }

            }else{
                result.println("<h1>No has capturat cap pokemon</h1>");
            }
            result.println("</body>" + "<html>");
            URI uri = new URI("pokemonsCapturats.html");
            uri.normalize();
            Desktop.getDesktop().browse(uri);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void infoPokemon(){
        JsonObject pokeInfo;
        String nomPokemon;
        long idPokemon;
        String description;
        long altura;
        long pes;
        long exp;

        //Demanem POkemon
        System.out.println("De quin Pokémon vols informació?");
        Scanner id = new Scanner (System.in);
        String nom = id.next();

        //Comprobar pokemon existeix

        try {
            pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon-species/" + nom + "/");
            pokeInfo = consultaAPI("https://pokeapi.co/api/v2/pokemon/" + nom + "/");

        //Llegim el JSON de la API
            //nomPokemon = pokeInfo.get("name").toString(); //Guardem info
            //idPokemon = pokeInfo.get("id").getAsInt();
            //description = ;
            altura = pokeInfo.get("height").getAsLong();
            //pes = pokeInfo.get("weight").getAsInt();
            //exp = pokeInfo.get("base_experience").getAsInt();
            //System.out.println(nomPokemon);
            //System.out.println(idPokemon);
            System.out.println(altura);
            //System.out.println(pes);
            //System.out.println(exp);



            //Generar fitxer html
            FileOutputStream informePokemon;
            PrintStream result;
            informePokemon = new FileOutputStream("infoPokemon.html");
            result = new PrintStream(informePokemon);
            result.println("hola");
            result.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}