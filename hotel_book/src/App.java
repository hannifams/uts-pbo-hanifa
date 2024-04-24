import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Accommodation {
    protected int roomNumber;
    protected String type;
    protected double pricePerNight;
    protected boolean isAvailable;

    public Accommodation(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Room extends Accommodation {
    public Room(int roomNumber, String type, double pricePerNight) {
        super(roomNumber, type, pricePerNight);
    }
}

class HotelRoom extends Room {
    private String hotelName;

    public HotelRoom(String hotelName, int roomNumber, String type, double pricePerNight) {
        super(roomNumber, type, pricePerNight);
        this.hotelName = hotelName;
    }

    public String getHotelName() {
        return hotelName;
    }
}

class Hotel {
    protected String name;
    protected String location;
    protected List<HotelRoom> rooms;

    public Hotel(String name, String location) {
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<HotelRoom> getRooms() {
        return rooms;
    }

    public void addRoom(HotelRoom room) {
        rooms.add(room);
    }
}

class Booking {
    private String guestName;
    private Hotel hotel;
    private Room room;
    private int numOfNights;

    public Booking(String guestName, Hotel hotel, Room room, int numOfNights) {
        this.guestName = guestName;
        this.hotel = hotel;
        this.room = room;
        this.numOfNights = numOfNights;
    }

    public String getGuestName() {
        return guestName;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumOfNights() {
        return numOfNights;
    }
}

class HotelBookingSystem {
    List<Hotel> hotels;
    private List<Booking> bookings;

    public HotelBookingSystem() {
        this.hotels = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public List<Hotel> searchHotels(String location, int maxPricePerNight) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.getLocation().equalsIgnoreCase(location)) {
                for (HotelRoom room : hotel.getRooms()) {
                    if (room.getPricePerNight() <= maxPricePerNight && room.isAvailable()) {
                        result.add(hotel);
                        break;
                    }
                }
            }
        }
        return result;
    }

    public boolean bookRoom(Hotel hotel, Room room, String guestName, int numOfNights) {
        if (room.isAvailable()) {
            room.setAvailable(false);
            Booking booking = new Booking(guestName, hotel, room, numOfNights);
            bookings.add(booking); // Add booking to the list of bookings
            System.out.println("Booking successful! Guest: " + guestName + ", Hotel: " + hotel.getName() +
                    ", Room: " + room.getRoomNumber() + ", Number of Nights: " + numOfNights);
            return true;
        } else {
            System.out.println("Room is not available.");
            return false;
        }
    }

    public void displayBookedRooms() {
        System.out.println("Booked Rooms:");
        for (Booking booking : bookings) {
            System.out.println("Guest: " + booking.getGuestName() + ", Hotel: " + booking.getHotel().getName() +
                    ", Room: " + booking.getRoom().getRoomNumber() + ", Number of Nights: " + booking.getNumOfNights());
        }
    }

    public void displayAllHotels() {
        System.out.println("All Hotels:");
        for (Hotel hotel : hotels) {
            System.out.println("Hotel: " + hotel.getName() + ", Location: " + hotel.getLocation());
            System.out.println("Rooms:");
            for (HotelRoom room : hotel.getRooms()) {
                System.out.println("Room Number: " + room.getRoomNumber() +
                        ", Type: " + room.getType() +
                        ", Price per Night: $" + room.getPricePerNight());
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelBookingSystem bookingSystem = new HotelBookingSystem();
        
        Hotel hilton = new Hotel("Hilton", "New York");
        hilton.addRoom(new HotelRoom("Hilton", 101, "Single", 150));
        hilton.addRoom(new HotelRoom("Hilton", 102, "Double", 200));
        bookingSystem.addHotel(hilton);

        Hotel marriott = new Hotel("Marriott", "Los Angeles");
        marriott.addRoom(new HotelRoom("Marriott", 201, "Single", 120));
        marriott.addRoom(new HotelRoom("Marriott", 202, "Double", 180));
        bookingSystem.addHotel(marriott);

        boolean running = true;
        while (running) {
            System.out.println("1. View All Hotels");
            System.out.println("2. Search Hotels");
            System.out.println("3. Book Room");
            System.out.println("4. Display Booked Rooms");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    bookingSystem.displayAllHotels();
                    break;
                case 2:
                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter maximum price per night: ");
                    int maxPricePerNight = scanner.nextInt();
                    scanner.nextLine(); 
                    List<Hotel> availableHotels = bookingSystem.searchHotels(location, maxPricePerNight);
                    for (Hotel hotel : availableHotels) {
                        System.out.println("Hotel: " + hotel.getName() + ", Location: " + hotel.getLocation());
                        System.out.println("Rooms:");
                        for (HotelRoom room : hotel.getRooms()) {
                            System.out.println("Room Number: " + room.getRoomNumber() +
                                    ", Type: " + room.getType() +
                                    ", Price per Night: $" + room.getPricePerNight());
                        }
                    }
                    if (availableHotels.isEmpty()) {
                        System.out.println("No available hotels matching the criteria.");
                    }
                    break;
                case 3:
                    System.out.print("Enter hotel name: ");
                    String hotelName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter guest name: ");
                    scanner.nextLine();
                    String guestName = scanner.nextLine();
                    System.out.print("Enter number of nights: ");
                    int numOfNights = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    Hotel bookedHotel = null;
                    Room bookedRoom = null;
                    for (Hotel hotel : bookingSystem.hotels) {
                        if (hotel.getName().equalsIgnoreCase(hotelName)) {
                            for (HotelRoom room : hotel.getRooms()) {
                                if (room.getRoomNumber() == roomNumber) {
                                    bookedHotel = hotel;
                                    bookedRoom = room;
                                    break;
                                }
                            }
                        }
                        if (bookedHotel != null && bookedRoom != null) {
                            break;
                        }
                    }
                    if (bookedHotel != null && bookedRoom != null) {
                        bookingSystem.bookRoom(bookedHotel, bookedRoom, guestName, numOfNights);
                    } else {
                        System.out.println("Invalid hotel name or room number.");
                    }
                    break;
                case 4:
                    bookingSystem.displayBookedRooms();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        scanner.close();
    }
}
