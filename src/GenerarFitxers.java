import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GenerarFitxers {

    public void informeCapturats(){
        FileOutputStream pokemonsCapturats;
        PrintStream result;
        try {
            pokemonsCapturats = new FileOutputStream("pokemonsCapturats.html");
            result = new PrintStream(pokemonsCapturats);
            result.println("hola");
            //result.close();
        }catch (FileNotFoundException e) {

        }
    }

    public void infoPokemon(){
        String nomPokemon;
        int idPokemon;
        String description;
        int altura;
        int pes;
        int exp;
        //Demanem POkemon
        System.out.println("De quin Pokémon vols informació?");
        Scanner id = new Scanner (System.in);

        //Comprobar pokemon existeix

        //Accedir api //OPCIO 2
        // Connect to the URL using java's native library
        URL url = null;
        try {
            url = new URL("https://pokeapi.co/api/v2/pokemon/" + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection request = null;
        try {
            request = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            request.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = null; //Convert the input stream to a json element
        try {
            root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject pokeInfo = root.getAsJsonObject(); //May be an array, may be an object.

        //Llegir json
        nomPokemon = pokeInfo.get("name").toString(); //Guardem info
        idPokemon = pokeInfo.get("id").getAsInt();
        //description = ;
        altura = pokeInfo.get("height").getAsInt();
        pes = pokeInfo.get("weight").getAsInt();
        exp = pokeInfo.get("base_experience").getAsInt();
        System.out.println(nomPokemon);
        System.out.println(idPokemon);
        System.out.println(altura);
        System.out.println(pes);
        System.out.println(exp);


    /*//Generar fitxer html
    FileOutputStream infoPokemon;
    PrintStream result;
    informePokemon = new FileOutputStream("infoPokemon.html");
    result = new PrintStream(informePokemon);
    result.println("hola");
    //result.close();
    */

    }

}