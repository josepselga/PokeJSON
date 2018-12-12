/** Clase main PokeJson
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

//Llibreries



public final class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        LecturaFitxers json = new LecturaFitxers();
        json.llegeixFitxers();
        Jugador jugador = new Jugador(json.getBalls());
        Logica logic = new Logica();

        //try{
            do{
                menu.mostraMenu();
                menu.comprovaOpcio();

                switch (menu.getOpcio()){
                    case 1:
                        logic.afegeixMonedes(jugador);
                        break;
                    case 2:
                        logic.compraObjectes(jugador, json.getBalls());
                        break;
                    case 3:
                        logic.consultaInventari(jugador, json.getBalls());
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        System.out.println("Adeu! Ens veiem aviat :)");
                        break;
                }

            }while (menu.getOpcio() != 9);

       /*}catch (FileNotFoundException e) {
            System.err.println("Error al intentar obrir fitxer");
        }*/
    }
}
