import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
public class Interface extends Application {
    private double lastMouseY = 0;
    public World w= new World("data/airport-codes_no_comma.csv");
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello world");
        Earth earth = new Earth();

        Scene ihm = new Scene(earth, 600, 400, true);
        primaryStage.setScene(ihm);
        primaryStage.show();

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        ihm.setCamera(camera);

        ihm.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println("Clicked on : ("+ event.getSceneX()+ ", "+ event.getSceneY()+")");
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                //camera.getTransforms().add(...); // A vous de completer
            }
        });
        ihm.addEventHandler(MouseEvent.ANY, event -> {

            if (event.getButton()== MouseButton.SECONDARY && event.getEventType()==MouseEvent.MOUSE_CLICKED) {
                double currentMouseY = event.getSceneY();
                double deltaY = currentMouseY - lastMouseY;

                // Appliquer la transformation de zoom en fonction du sens
                Translate translate = new Translate(0, 0, deltaY * 0.5);
                camera.getTransforms().add(translate);
                lastMouseY = currentMouseY; // Mettre à jour la position précédente
            }
        });
        ihm.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null) {
                    // Récupération du point d'intersection
                    Point2D intersectionPoint = pickResult.getIntersectedTexCoord();

                    if (intersectionPoint != null) {
                        // Rayon de la sphère (correspondant à la Terre)
                        double radius = 300;

                        // Récupération des coordonnées X, Y, Z du point d'intersection
                        double x = intersectionPoint.getX();
                        double y = intersectionPoint.getY();

                        // Conversion en latitude et longitude
                        double latitude = 180*(0.5-y);
                        double longitude = 360*(x-0.5);

                        // Affichage des coordonnées dans la console
                        System.out.println("Longitude: "+longitude+", Latitude: "+latitude);

                        Aeroport closestAeroport = w.findNearest(latitude,longitude);
                        if(closestAeroport != null) {
                            System.out.println("Aéroport le plus proche: " + closestAeroport + "\n\n");
                            //earth.displayRedSphere(closestAeroport);
                        }
                    }
                }

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}