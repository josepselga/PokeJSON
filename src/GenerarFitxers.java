import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class GenerarFitxers {

    public void informeCapturats(){
        FileOutputStream pokemonsCapturats;
        String text = "texto";
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

    }

}
