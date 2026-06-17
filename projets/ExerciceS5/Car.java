public class Car{
    private String plateNumber, manufacture, color;
    private int speed;

    //constructeur int plaque d'immatriculation
    public Car (String plateNumber){
        this.plateNumber = plateNumber;
    }

    //constructeur int plaque d'immatriculation et la couleur
    public Car (String plateNumber, String color){
        this.plateNumber = plateNumber;
        this.color = color;
    }
     //constructeur int plaque d'immatriculation, la couleur, et le fabriquant
    public Car (String plateNumber, String color , String manufacture){
        this.plateNumber = plateNumber;
        this.color = color;
        this.manufacture = manufacture;
        }

        //constructeur int plaque d'immatriculation, la couleur, le fabriquant et la vitesse
        public Car (String plateNumber, String color , String manufacture , int speed){
            this.plateNumber = plateNumber;
            this.color = color;
            this.manufacture = manufacture;
            this.speed = speed;
}

//constructeur int plaque d'immatriculation et le fabriquant
public Car (String plateNumber , int speed){
    this.plateNumber = plateNumber;
    this.speed = speed;
}

//constructeur initialise la vitesse
public Car ( int speed){
    this.speed = speed;
}
public void accelerate(){
    this.speed++;
}

public void brake(){
    if(this.speed>0)
    this.speed--;
}

public void setspeed(int speed){
    this.speed = speed;
}
public String toString(){
    return "Ma voiture" + this.manufacture + "de couleur" + this.color +" avec la plaque" + this.plateNumber + "et de vitesse " + this.speed;
}
}