/* **********************************************
 *
 * @Proposit: P1_josep.lp_josep.selga
 * @Autor/s: Josep Lluis __ Josep Selga
 * @Data creacio: 29/11/2018
 * @Data ultima modificacio:
 *
 *********************************************** */

//Llibreries


public final class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        Jugador jugador = new Jugador();                                                                                //S'inicialitza fora del bucle perque no s'actualitzin les monedes a 100
        //Aaaaaa
        do{
            menu.mostraMenu();
            menu.comprovaOpcio();

            switch (menu.getOpcio()){

                case 1:
                    Logica.afegeixMonedes();
                case 2:
                case 3:
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
    }
}
