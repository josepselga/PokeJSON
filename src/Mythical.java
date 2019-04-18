/** Clase objecte dels Pokémons Mítics.
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */
import java.util.ArrayList;

public class Mythical {

    private long id;
    private String kind;
    private String researchName;
    private ArrayList<Long> target;
    private ArrayList<Long> quantity;

    public Mythical() {
        target = new ArrayList<Long>();
        quantity = new ArrayList<Long>();
    }
    public Mythical(long id, String kind, String researchName, ArrayList<Long> target, ArrayList<Long> quantity) {
        this.id = id;
        this.kind = kind;
        this.researchName = researchName;
        this.target = target;
        this.quantity = quantity;
    }

    //Getters
    public long getId() {
        return id;
    }
    public String getKind() {
        return kind;
    }
    public String getResearchName() {
        return researchName;
    }
    public ArrayList<Long> getTarget() {
        return target;
    }
    public ArrayList<Long> getQuantity() {
        return quantity;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public void setResearchName(String researchName) {
        this.researchName = researchName;
    }
    public void setTarget(ArrayList<Long> target) {
        this.target = target;
    }
    public void setQuantity(ArrayList<Long> quantity) {
        this.quantity = quantity;
    }

    /**
     * Afegir un target al array de targets
     * @param target Long id del pokemon a afegir
     */
    public void addTarget(Long target){
        this.target.add(target);
    }

    /**
     * Afegir quantitat de pokeons a capturar
     * @param quantity Long quantitat de pokemons a inserir
     */
    public void addQuantity(Long quantity){
        this.quantity.add(quantity);
    }

}
