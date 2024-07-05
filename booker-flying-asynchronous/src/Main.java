import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BookerFlying bookerFlying = new BookerFlying();

        Thread reader1 = new Thread(new Reader(bookerFlying, 1));
        Thread w1 = new Thread(new Writer(bookerFlying, 2,"make"));
        Thread w2 = new Thread(new Writer(bookerFlying, 4,"cancel"));
        Thread w3 = new Thread(new Writer(bookerFlying, 5,"make"));
        Thread reader3 = new Thread(new Reader(bookerFlying, 1));


        reader3.start();
        reader3.join();

        w1.start();
        w2.start();
        w3.start();
        reader1.start();
        w1.join();
        w2.join();
        w3.join();
        reader1.join();

    }
}
class Reader implements Runnable{
    private BookerFlying bookerFlying;
    private int seatNumber;
    public Reader(BookerFlying bookerFlying, int seatNumber){
        this.bookerFlying = bookerFlying;
        this.seatNumber = seatNumber;
    }

    @Override
    public void run() {
        bookerFlying.queryReservation(seatNumber);
    }
}
class Writer implements  Runnable{
    private BookerFlying bookerFlying;
    private int seatNumber;
    private String mode;
    public Writer(BookerFlying bookerFlying, int seatNumber, String mode){
        this.bookerFlying = bookerFlying;
        this.seatNumber = seatNumber;
        this.mode = mode;
    }

    @Override
    public void run() {
        if (mode.equals("make")){
            bookerFlying.makeReservation(seatNumber);
        } else if (mode.equals("cancel")) {
            bookerFlying.cancelReservation(seatNumber);
        }
    }
}
class Seat{
    private int seatNumber;
    private boolean status;

    public Seat(int seatNumber, boolean status) {
        this.seatNumber = seatNumber;
        this.status = status;
    }
    void setSeatNumber(int seatNumber){
        this.seatNumber = seatNumber;
    }

    void setStatus(Boolean status){
        this.status = status;
    }
    int getSeatNumber(){
        return this.seatNumber;
    }
    boolean getStatus(){
        return this.status;
    }
}
class BookerFlying {
    List<Seat> seatsList ;


    public BookerFlying(){
        seatsList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            seatsList.add(new Seat(i,false));
        }
    }
    void makeReservation(int seatNumber){
        printLine();
        try {
            System.out.println("\n" + Thread.currentThread().threadId() + " tries to book the seat " + seatNumber);
            for (Seat seat: seatsList){
                if (seat.getSeatNumber() == seatNumber){
                    if (!seat.getStatus()){
                        seat.setStatus(true);
                        System.out.println(Thread.currentThread().threadId() + " booked seat number " + seatNumber + " successfully");
                    }else {
                        System.out.println(Thread.currentThread().threadId() + "could not booked seat number " + seatNumber + " since has been already booked");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("erros =>>" + e);
        }
    }

    void cancelReservation(int seatNumber){
        printLine();
        try {
            System.out.println("\n" + Thread.currentThread().threadId() + " tries to cancel the seat " + seatNumber);
            for(Seat seat : seatsList){
                if (seat.getSeatNumber() == seatNumber){
                    if (seat.getStatus()){
                        seat.setStatus(false);
                    }else {
                        System.out.println(Thread.currentThread().threadId() + " booked seat number " + seatNumber + " already free");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("erros =>>" + e);
        }
    }

    void queryReservation(int seatNumber){
        printLine();
        try {
            for (Seat r : seatsList) {
                if (r.getSeatNumber() == seatNumber) {
                    System.out.println("\nlooks for available seats. State of seats are :");
                    printALlSeatStatus(seatsList);
                }
            }
        }catch (Exception e){
            System.out.println("erros =>>" + e);
        }
    }
    void printALlSeatStatus(List<Seat> list){
        for (Seat seat : list){
            System.out.println(+Thread.currentThread().threadId() + " number of request to  seat Number " + seat.getSeatNumber() + " : " + seat.getStatus());
        }
    }
    void printLine(){
        System.out.println("*********************************************************************************");
    }
}