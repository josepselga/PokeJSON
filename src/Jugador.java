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

    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }
}
