/**
 * Clase amb la info d'un pokemon
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Pokemon {

    //Atributs de la clase
    private Long id;
    private String name;
    private int capture_rate;

    //Constructor
    public Pokemon(Long id, String nom, int capture_rate) {
        this.id = id;
        this.name = nom;
        this.capture_rate = capture_rate;
    }

    //Getters
    public Long getId() {
        return id;
    }
    public String getNom() {
        return name;
    }
    public int getCaptureRate() {
        return capture_rate;
    }

}
