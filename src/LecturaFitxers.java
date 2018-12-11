import java.io.FileReader;
import java.io.FileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class LecturaFitxers {

    //Atributs
    private Ball[] balls;
    private Pokemon[] poke;
    //private Legend legend;

    //Getters
    public Ball[] getBalls() {
        return balls;
    }
    public Pokemon[] getPoke() {
        return poke;
    }

    //Metodes
    public void llegeixFitxers(){
        try{
            //Inicialitzem fitxers json
            Gson gson = new Gson();
            JsonReader json1 = new JsonReader(new FileReader("resources/balls.json"));
            JsonReader json2 = new JsonReader(new FileReader("resources/poke.json"));
            //JsonReader json3 = new JsonReader(new FileReader("resources/legends.json"));

            balls = gson.fromJson(json1, Ball[].class);
            poke = gson.fromJson(json2, Pokemon[].class);
            //legend = gson.fromJson(json3, Legend.class);

        }catch(FileNotFoundException e){
            System.err.println("Error al intentar obrir fitxer");
        }
    }
}
