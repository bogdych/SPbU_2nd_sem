public abstract class Car {
    public String model;
    public String manufacturerCountry;
    public int maxSpeed;
    public int weight;
    public int productionYear;

    public void info() {
        System.out.println("Model:\t\t\t\t\t" + this.model);
        System.out.println("Manufacturer country :\t" + this.manufacturerCountry);
        System.out.println("Maximum speed :\t\t\t" + this.maxSpeed);
        System.out.println("Weight:\t\t\t\t\t" + this.weight);
        System.out.println("Production year:\t\t" + this.productionYear);
    }
}
