import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    private Sphere sph;
    private Rotate ry; // Rotation autour de l'axe Y


    public Earth() {
        // Créer une sphère représentant la Terre
        sph = new Sphere(300);

        // Appliquer une texture avec PhongMaterial
        PhongMaterial earthMaterial = new PhongMaterial();
        Image earthTexture = new Image("file:data/earth_lights_4800.png"); // Remplacez par le chemin de votre image
        earthMaterial.setDiffuseMap(earthTexture);
        sph.setMaterial(earthMaterial);

        // Ajouter une rotation autour de l'axe Y
        ry = new Rotate(0, Rotate.Y_AXIS);
        this.getTransforms().add(ry);

        // Ajouter l'AnimationTimer pour animer la rotation
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long currentTime) {
                if (lastTime > 0) {
                    double deltaTime = (currentTime - lastTime) / 1_000_000_000.0; // Convertir en secondes
                    double angleIncrement = deltaTime * 15; // Ajuster la vitesse de rotation (15° par seconde)
                    ry.setAngle(ry.getAngle() + angleIncrement);
                }
                lastTime = currentTime;
            }
        };
        animationTimer.start();

        // Ajouter la sphère au groupe
        this.getChildren().add(sph);
    }

    public Sphere createSphere(Aeroport a, Color c) {
        Sphere coloredSph = new Sphere();
        PhongMaterial phg2 = new PhongMaterial();
        phg2.setDiffuseColor(c);
        coloredSph.setMaterial(phg2);

        double latitude = Math.toRadians(a.getLatitude());
        double longitude = Math.toRadians(a.getLongitude());

        double radius = sph.getRadius();

        // Conversion latitude/longitude en coordonnées 3D
        double x = radius * Math.cos(latitude) * Math.cos(longitude);
        double y = radius * Math.sin(latitude);
        double z = radius * Math.cos(latitude) * Math.sin(longitude);

        coloredSph.setTranslateX(x);
        coloredSph.setTranslateY(-y); // Inversion Y pour correspondre à la convention graphique
        coloredSph.setTranslateZ(z);

        return coloredSph;
    }
    public void displayRedSphere(Aeroport a){
        Sphere redSph = new Sphere(5);
        redSph = createSphere(a,Color.RED);
        //redSph = redSph.set
        this.getChildren().add(redSph);
    }

    public void displayYellowSphere(World w){
        Sphere yellowSph = new Sphere(2);
        for(Aeroport aeroport : w.aeroportList){
            yellowSph = createSphere(aeroport, Color.YELLOW);
            this.getChildren().add(yellowSph);
        }
    }
}
