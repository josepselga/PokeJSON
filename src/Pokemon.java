/** Clase amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Pokemon {

    //Atributs de la clase
    private int id;
    private String nom;
    private int capture_rate;

    //Metodes de la clase
    public Pokemon(int id, String nom, int capture_rate) {
        this.id = id;
        this.nom = nom;
        this.capture_rate = capture_rate;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public int getCaptureRate() {
        return capture_rate;
    }

}
