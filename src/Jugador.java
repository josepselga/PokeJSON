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
    private ArrayList<Ball> ball = new ArrayList<>();

    //Constructor

    public Jugador(Ball[] ball) {
        this.monedes = 1000;
        //this.huntedPokemon = huntedPokemon;
        this.ball.add(ball[0]);
        this.ball.get(0).setPrice(3);
    }

    //Metodes de la clase
    //Getters
    public int getMonedes() {
        return monedes;
    }

    public ArrayList<Ball> getBall() {
        return ball;
    }
    //Setters
    /** Sets the player coins.
     * @param monedes
     */
    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }
}
