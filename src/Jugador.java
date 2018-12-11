/** Representa el jugador de la partida
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Jugador {

    //Atributs de la clase
    private int monedes;
    private int[] huntedPokemon;

    //Metodes de la clase
    //Getters
    public int getMonedes() {
        return monedes;
    }

    public Jugador() {
        this.monedes = 1000;
    }

    /** Sets the player coins.
     * @param monedes
     */
    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }
}
