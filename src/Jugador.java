/** Representa el jugador de la partida
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Jugador {

    //Atributs de la clase
    private int monedes;
    private Long[] idHunted;
    private int[] numHunted;
    private String[] nomBalls;
    private int[] numBalls;

    //Constructor
    public Jugador(Pokemon poke[], Ball[] ball) {
        monedes = 1000;
        idHunted = new Long[poke.length];
        for(int i = 0 ; i < poke.length ; i++){
           idHunted[i] = poke[i].getId();
        }
        numHunted = new int [poke.length];
        nomBalls = new String[ball.length];
        for(int i = 0 ; i < ball.length ; i++){
            nomBalls[i] = ball[i].getName();
        }
        numBalls = new int[ball.length];
        numHunted[131] = 1;
        numHunted[0] = 5;
        numHunted[3] = 5;
        numHunted[6] = 2;
        numHunted[132] = 5;
        numHunted[181] = 1;
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
    public Long[] getIdHunted() {
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
