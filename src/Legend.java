/** Clase amb la logica del programa
 * @author Josep Lluis
 * @author Josep Selga
 * @version 1.5
 * @since 1.0
 */

public class Legend {
        private long id;
        private String kind;
        private String gymName;
        private double longitude;
        private double latitude;

        public Legend() {

        }

        public Legend(long id, String kind, String gymName, double x, double y) {
                this.id = id;
                this.kind = kind;
                this.gymName = gymName;
                this.longitude = longitude;
                this.latitude = latitude;
        }

        //Getters
        public long getId() {
                return id;
        }
        public String getKind() {
                return kind;
        }
        public String getGymName() {
                return gymName;
        }
        public double getLongitude() {
                return longitude;
        }
        public double getLatitude() {
                return latitude;
        }

        //Setters
        public void setId(long id) {
                this.id = id;
        }
        public void setKind(String kind) {
                this.kind = kind;
        }
        public void setGymName(String gymName) {
                this.gymName = gymName;
        }
        public void setLongitude(double longitude) {
                this.longitude = longitude;
        }
        public void setLatitude(double latitude) {
                this.latitude = latitude;
        }
}
