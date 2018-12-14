import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LecturaFitxers {

    //Atributs
    private Ball[] balls;
    private Pokemon[] poke;
    private ArrayList<Legend> legend;
    private ArrayList<Mythical> mythical;

    //Getters
    public Ball[] getBalls() {
        return balls;
    }
    public Pokemon[] getPoke() {
        return poke;
    }
    public ArrayList<Legend> getLegend() {
        return legend;
    }
    public ArrayList<Mythical> getMythical() {
        return mythical;
    }

    //Metodes
    public void llegeixFitxers() throws IOException, ParseException {
        legend = new ArrayList<>();
        mythical = new ArrayList<>();
        JSONParser parser = new JSONParser();


        //Inicialitzem fitxers json
        Gson gson = new Gson();
        JsonReader json1 = new JsonReader(new FileReader("resources/balls.json"));
        JsonReader json2 = new JsonReader(new FileReader("resources/poke.json"));
        Object obj = parser.parse(new FileReader("resources/legends.json"));
        JSONArray json3 = (JSONArray) obj;

        balls =  gson.fromJson(json1, Ball[].class);
        poke = gson.fromJson(json2, Pokemon[].class);

        //Llegir legends
        JSONObject superPoke;
        JSONObject superGym;
        JSONObject superLocation;
        JSONObject superResearch;
        JSONArray superQuest;
        Legend auxLegend = new Legend();
        Mythical auxMythical = new Mythical();

        for(int i = 0 ; i < json3.size() ; i++){
            superPoke = (JSONObject) json3.get(i);

            if(superPoke.get("kind").equals("legendary")){
                auxLegend.setId((long)superPoke.get("id"));
                auxLegend.setKind((String) superPoke.get("kind"));
                //Info gym
                superGym = (JSONObject) superPoke.get("gym");
                auxLegend.setGymName((String)superGym.get("name"));
                superLocation = (JSONObject) superGym.get("location");
                auxLegend.setLongitude((double)superLocation.get("longitude"));
                auxLegend.setLatitude((double)superLocation.get("latitude"));
                legend.add(auxLegend);
            }else{
                auxMythical.setId((long)superPoke.get("id"));
                auxMythical.setKind((String) superPoke.get("kind"));
                //info research
                superResearch = (JSONObject) superPoke.get("special_research");
                auxMythical.setResearchName((String)superResearch.get("name"));
                superQuest = (JSONArray) superResearch.get("quests");
                for(int j = 0 ; j < superQuest.size() ; j++){
                    auxMythical.addTarget((Long)superResearch.get("target"));
                    auxMythical.addQuantity((Long)superResearch.get("quantity"));
                }
                mythical.add(auxMythical);
            }
        }
    }
}
