/** Representa el jugador de la partida
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */
import java.util.ArrayList;

public class Jugador {

    //Atributs de la clase
    private int monedes;
    private int[] huntedPokemon;
    private String[] nomBalls;
    private int[] numBalls;

    //Constructor
    public Jugador(Ball[] ball) {
        this.monedes = 1000;
        this.huntedPokemon = huntedPokemon;
        nomBalls = new String[ball.length];
        for(int i = 1 ; i < ball.length ; i++){
            nomBalls[i] = ball[i].getName();
        }
        numBalls = new int[ball.length];
        nomBalls[0] = ball[0].getName();
        numBalls[0] = 3;
    }

    //Metodes de la clase
    //Getters
    public int getMonedes() {
        return monedes;
    }

    public String[] getNomBalls() {
        return nomBalls;
    }

    public int[] getNumBalls() {
        return numBalls;
    }

    //Setters
    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }
}
