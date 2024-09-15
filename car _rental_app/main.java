import java.util.*;

class car {
    private String carId;
    private String model;
    private String brand;
    private double pricePerDay;
    private boolean isAvailable;


    public car(String carId, String model, String brand, double pricePerDay) {

        this.carId = carId;
        this.model=model;
        this.brand=brand;
        this.pricePerDay=pricePerDay;
    this.isAvailable=true;
    }
    public String getCarId(){
        return carId;
    }
    public String getModel(){

        return model;
    }
    public String getBrand(){
        return brand;
    }
    public double totalPricePerDay(int rentalDays){
        return pricePerDay * rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
         isAvailable=true;
    }
}

class customer{
    private String name;
    private String cuid;


    public customer (String name,String cuid){
        this.name=name;
        this.cuid=cuid;

    }

    public String getName(){
        return name;
    }
    public String getCuid(){
        return cuid;
    }



}

class Rental{
    private car Car;
    private customer Customer;
    private int days;

    public Rental(car Car,customer Customer,int days){
        this.Car=Car;
        this.Customer=Customer;
        this.days=days;
    }

    public car getCar(){
        return Car;
    }
    public customer getCustomer(){
        return Customer;
    }
    public int getDays(){
        return days;
    }
}

class  carRentalSystem{
    private List<car>cars;
    private List<customer> customer;
    private List<Rental>  rentals;
    public carRentalSystem(){
        cars= new ArrayList<>();
        customer=new ArrayList<>();
        rentals=new ArrayList<>();
            }

    public void addCar(car Car){
        cars.add(Car);
    }
    public void addCustomer(customer Customer){
        customer.add(Customer);
    }
    public void rentCar(car Car,customer Customer,int days){
        if (Car.isAvailable()) {
            Car.rent();
            rentals.add(new Rental(Car,Customer,days));
        }else{
            System.out.println("Car is not available for rent.");
        }


    }
    public void returnCar(car Car){

        Rental rentalToRemove=null;
        for(Rental rental:rentals){
            if(rental.getCar()==Car){
                rentalToRemove=rental;
                break;
            }
            if (rentalToRemove !=null){
                rentals.remove(rentalToRemove);
                System.out.println("Car rented successsfully");
            }
            else{
                System.out.println("Car was not rented");
            }
        }
        Car.returnCar();

    }

    public void menu(){
        Scanner sc =new Scanner(System.in);
        while (true){
            System.out.println("===Car Rental System===");
            System.out.println("1.Rent a Car");
            System.out.println("2.Return a Car");
            System.out.println("3.Exit");
            System.out.println("Enter your choice");


            int choice=sc.nextInt();
            sc.nextLine();//consume Next Line

            if(choice==1){
                System.out.println("\n==Rent a Car==\n");
                System.out.print("Enter your Name");
                String customerName=sc.nextLine();

                System.out.println("\nAvailable Cars:");
                for (car Car:cars){
                    if(Car.isAvailable()){
                        System.out.println(Car.getCarId()+" - "+Car.getBrand()+" - "+Car.getModel());
                    }
                }
                System.out.print("\nEnter the carid you want to rent:");
                String carId=sc.nextLine();

                System.out.println("Enter the  number of days for rental:");
                int rentalDays=sc.nextInt();
                sc.nextLine();

                customer newCustomer=new customer(customerName,"cus"+(customer.size()+1));
                addCustomer(newCustomer);

                car selectedCar=null;
                for(car Car:cars){
                    if(Car.getCarId().equals(carId)&& Car.isAvailable()){
                        selectedCar=Car;
                        break;
                    }
                }

                if(selectedCar!=null){
                    double totalprice=selectedCar.totalPricePerDay(rentalDays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("customerId"+newCustomer.getCuid());
                    System.out.println("Customer Name"+newCustomer.getName());
                    System.out.println("Car:"+selectedCar.getBrand()+" - "+selectedCar.getModel() );
                    System.out.println("Rental Days:"+rentalDays);
                    System.out.printf("Total Price:$%.2f%n",totalprice);

                    System.out.print("\nConfirm rental (Y/N):");
                    String confirm=sc.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\n==Car rented successfully");
                    }else{
                        System.out.println("\nrental canceled ");
                    }
                }else {
                    System.out.println("\nInvalid car selection or car is not available for rent.");
                }


            }else if(choice==2){
                System.out.println("\n==Return a car==\n");
                System.out.println("Enter the  car Id you want to return;");
                String carId=sc.nextLine();

                car carToReturn=null;
                for (car Car:cars){
                    if(Car.getCarId().equals(carId)&&!Car.isAvailable()){
                        carToReturn=Car;
                        break;
                    }
                }

                if(carToReturn!=null){
                    customer Customer=null;
                    for(Rental rental:rentals){
                        if(rental.getCar()==carToReturn){
                            Customer=rental.getCustomer();
                            break;
                        }
                    }
                    if(Customer!=null){
                        returnCar(carToReturn);
                        System.out.println("Car return successfully by"+Customer.getName());
                    }else{
                        System.out.println("Car was not rented or information is missing");
                    }
                }
                else{
                    System.out.println("Invalid carId or car is not rented");
                }
            } else if (choice==3) {
               break;

            }else{
                System.out.println("Invalid choice .Please enter the valid option");
            }

        }
        System.out.println("Thank you for using car rental system!");
        sc.close();
    }
}




public class Main {
    public static void main(String[] args) {
carRentalSystem rentalSystem=new carRentalSystem();
car Car1= new car("C001","Camry", "Toyoto",60.0);
car Car2=new car("C002","Accord","Honda",70.0);
car Car3=new car("C003","z1","lxd",90.0);
rentalSystem.addCar(Car1);
rentalSystem.addCar(Car2);
rentalSystem.addCar(Car3);
rentalSystem.menu();
    }
}
