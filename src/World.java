import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    public List<Aeroport> aeroportList = new ArrayList<>();

    public World(String fileName) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();
            s = buf.readLine();
            while (s != null) {
                s = s.replaceAll("\"", "");
                //Enlev les guillemets qui separent les champs de donnees GPS.
                String fields[] = s.split(",");
                //pt d arret
                if (fields[1].equals("large_airport")) {
                    String codeIATA = fields[9];
                    String country = fields[5];
                    String nom = fields[2];
                    double longitude = Double.parseDouble(fields[11]);
                    double latitude = Double.parseDouble(fields[12]);

                    Aeroport aeroport = new Aeroport(nom, latitude, longitude, codeIATA,country);

                    aeroportList.add(aeroport);
                }

                s = buf.readLine();
            }
        } catch (Exception e) {
            System.out.println("Maybe the file isnt there ?");
            System.out.println(aeroportList.get(aeroportList.size() - 1));
            e.printStackTrace();
        }
    }

    public Aeroport findNearest(double latitude, double longitude) {
        Aeroport nearestAeroport = null;
        double minDistance = Double.MAX_VALUE; // Distance minimale initialisée à un maximum

        for (Aeroport aeroport : aeroportList) {
            double distance = calculateDistance(latitude, longitude, aeroport.getLatitude(), aeroport.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestAeroport = aeroport;
            }
        }

        return nearestAeroport; // Retourne l'aéroport le plus proche ou null si la liste est vide
    }

    // Méthode pour calculer la distance entre deux points géographiques
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance en kilomètres
    }




    public void afficherAeroports() {
        for (Aeroport aeroport : aeroportList) {
            System.out.println(aeroport);
        }

    }
    public Aeroport findByCode(String codeIATA) {
        for (Aeroport aeroport : aeroportList) {
            if (aeroport.getCodeIATA().equalsIgnoreCase(codeIATA)) {
                return aeroport;
            }
        }
        // Si aucun aéroport n'est trouvé
        return null;
    }

}
