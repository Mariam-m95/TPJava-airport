public class Aeroport {
        private String nom;
        private double latitude;
        private double longitude;
        private String codeIATA;
        private String country;

        public Aeroport(String nom, double latitude, double longitude, String codeIATA) {
            this.nom = nom;
            this.latitude = latitude;
            this.longitude = longitude;
            this.codeIATA = codeIATA;
            this.country = country;
        }


    public double getLatitude() {
            return latitude;
        }


        public double getLongitude() {
            return longitude;
        }


        public String getCodeIATA() {
            return codeIATA;
        }


        @Override
        public String toString() {
            return "L'aéroport de "+nom+", situé en "+country+", en "+latitude+", "+longitude+", et le code IATA est :"+codeIATA;
        }

    public double calculDistance(Aeroport a) {

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(a.getLatitude());
        double lon2 = Math.toRadians(a.getLongitude());

        // Différences entre les coordonnées
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        // Calcul selon la formule
        double distance = Math.pow(deltaLat, 2) + Math.pow(deltaLon * Math.cos((lat1 + lat2) / 2), 2);

        return distance;
    }
}



