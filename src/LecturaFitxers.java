import java.io.FileReader;
import java.io.FileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class LecturaFitxers {

    //Inicialitzem fitxers json
    Gson gson = new Gson();
    JsonReader json1 = new JsonReader(new FileReader("resources/balls.json"));
    JsonReader json2 = new JsonReader(new FileReader("resources/poke.json"));
    //JsonReader json3 = new JsonReader(new FileReader("resources/legends.json"));

    Ball[] balls = gson.fromJson(json1, Ball[].class);
    Pokemon[] poke = gson.fromJson(json2, Pokemon[].class);
    //Legend legend = gson.fromJson(json3, Legend.class);


}
