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
    private int[] idHunted;
    private int[] numHunted;
    private String[] nomBalls;
    private int[] numBalls;

    //Constructor
    public Jugador(Pokemon poke[], Ball[] ball) {
        monedes = 1000;
        idHunted = new int[poke.length];
        for(int i = 0 ; i < poke.length ; i++){
           idHunted[i] = poke[i].getId();
        }
        numHunted = new int [poke.length];
        nomBalls = new String[ball.length];
        for(int i = 0 ; i < ball.length ; i++){
            nomBalls[i] = ball[i].getName();
        }
        numBalls = new int[ball.length];
        numBalls[0] = 3;
        numHunted[3] = 2;
        numHunted[155] = 2;
        numHunted[22] = 2;
        numHunted[383] = 232;
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
    public int[] getIdHunted() {
        return idHunted;
    }
    public int[] getNumHunted() {
        return numHunted;
    }

    //Setters
    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }
}
