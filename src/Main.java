public class Main {

    public static void main(String[] args) {
        World world = new World("data/airport-codes_no_comma.csv");
        Aeroport test = world.findNearest(2.316,48.866);
        Aeroport test2 = world.findByCode("CDG");
        System.out.println(test);
        System.out.println(test2);




    }
}
