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
    //private ArrayList<Ball> ball = new ArrayList<>();

    //Constructor
    public Jugador(Ball[] ball) {
        this.monedes = 1000;
        //this.huntedPokemon = huntedPokemon;
        nomBalls = new String[ball.length];
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
/*public ArrayList<Ball> getBall() {
        return ball;
    }*/
    //Setters
    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }
}
