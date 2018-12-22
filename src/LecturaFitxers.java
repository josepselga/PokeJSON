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
        legend = new ArrayList<Legend>();
        mythical = new ArrayList<Mythical>();
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
        for(int i = 0 ; i < json3.size() ; i++){
            JSONObject superPoke = (JSONObject) json3.get(i);

            if(superPoke.get("kind").equals("legendary")){
                Legend auxLegend = new Legend();
                auxLegend.setId((long)superPoke.get("id"));
                auxLegend.setKind((String) superPoke.get("kind"));
                //Info gym
                JSONObject superGym = (JSONObject) superPoke.get("gym");
                auxLegend.setGymName((String)superGym.get("name"));
                JSONObject superLocation = (JSONObject) superGym.get("location");
                auxLegend.setLongitude((double)superLocation.get("longitude"));
                auxLegend.setLatitude((double)superLocation.get("latitude"));
                legend.add(auxLegend);
            }else{
                Mythical auxMythical = new Mythical();
                auxMythical.setId((long)superPoke.get("id"));
                auxMythical.setKind((String) superPoke.get("kind"));
                //info research
                JSONObject superResearch = (JSONObject) superPoke.get("special_research");
                auxMythical.setResearchName((String)superResearch.get("name"));
                JSONArray superQuest = (JSONArray) superResearch.get("quests");
                for(int j = 0 ; j < superQuest.size() ; j++){
                    JSONObject auxMission = (JSONObject) superQuest.get(j);
                    auxMythical.addTarget((Long) auxMission.get("target"));
                    auxMythical.addQuantity((Long) auxMission.get("quantity"));
                }
                mythical.add(auxMythical);
            }
        }
    }
}
