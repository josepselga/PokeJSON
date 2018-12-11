/** Clase amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Ball {

    //Atributs de la clase
    private String name;
    private int capture_rate;
    private int price;

    //Metodes de la clase

    public Ball(String name, int capture_rate, int price) {
        this.name = name;
        this.capture_rate = capture_rate;
        this.price = price;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getCapture_rate() {
        return capture_rate;
    }
    public int getPrice() {
        return price;
    }
    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
