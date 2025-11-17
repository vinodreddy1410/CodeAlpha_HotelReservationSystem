# 🏨 Hotel Reservation System

A comprehensive Java-based hotel management system with a modern GUI for searching, booking, and managing hotel rooms.

## 📋 Project Overview

This project is part of the **Code Alpha Internship Program - Task 4**. It's a complete hotel reservation system that allows users to search for available rooms, make bookings, manage reservations, and process payments through a simulation.

## ✨ Features

### 🔍 Search & Booking
- **Advanced Search**: Filter rooms by category, dates, and number of guests
- **Real-time Availability**: Check room availability for specific date ranges
- **Easy Booking Process**: Simple flow from search to payment
- **Guest Information Management**: Collect and store guest details

### 🏷️ Room Categorization
- **Standard Rooms** ($100-110/night)
  - WiFi, TV, AC
  - Capacity: 2-3 guests
  
- **Deluxe Rooms** ($200-220/night)
  - WiFi, Smart TV, AC, Mini-bar, City View
  - Capacity: 2-4 guests
  
- **Suites** ($350-400/night)
  - WiFi, Smart TV, AC, Mini-bar, Jacuzzi, Premium View, Living Area
  - Capacity: 4-6 guests

### 📅 Reservation Management
- View all bookings in organized table format
- Cancel reservations with automatic room availability update
- View detailed booking information
- Track booking status (Pending, Confirmed, Cancelled, Completed)

### 💳 Payment Simulation
- Interactive payment dialog
- Multiple payment methods (Credit Card, Debit Card, PayPal)
- Detailed booking summary with cost breakdown
- Instant confirmation system

### 💾 Data Persistence
- File-based storage using Java Serialization
- Automatic save/load functionality
- Persistent data across application sessions
- Files: `rooms.dat` and `bookings.dat`

### 🎨 Modern UI/UX
- Clean and intuitive interface
- Color-coded elements for easy navigation
- Responsive tables with sorting capabilities
- Icon-enhanced buttons and labels
- Professional color scheme

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line or terminal access

### Compilation & Execution

```bash
# Compile the program
javac HotelReservationSystem.java

# Run the application
java HotelReservationSystem
```

## 📸 Screenshots

### Search & Book Panel
Search for available rooms based on your criteria including dates, room category, and number of guests.

### My Bookings Panel
View and manage all your reservations with options to view details or cancel bookings.

### Room Management Dashboard
Admin panel showing statistics including total rooms, availability, bookings, and revenue.

## 🏗️ Technical Architecture

### Object-Oriented Design
- **HotelReservationSystem** (Main GUI)
- **Room** class with encapsulation
- **Booking** class with business logic
- **HotelManager** for centralized management
- **RoomCategory** enum
- **BookingStatus** enum

### Design Patterns
- MVC-inspired architecture
- Singleton pattern for hotel manager
- Observer pattern for UI updates
- Factory pattern for room creation

### Technologies
- **Java Swing**: GUI framework
- **Java I/O**: File-based persistence
- **Java Collections**: Data management
- **Java Date/Time**: Date handling

## 📊 Database Schema (File-based)

### rooms.dat
Stores Room objects with:
- Room number
- Category
- Price per night
- Maximum capacity
- Availability status
- Amenities list

### bookings.dat
Stores Booking objects with:
- Unique booking ID
- Room reference
- Guest information (name, email, phone)
- Check-in/check-out dates
- Number of guests
- Total amount
- Booking status

## 🎯 Key Functionalities

1. **Search Algorithm**
   - Filters by capacity (>= requested guests)
   - Filters by category (if specified)
   - Checks date availability (no overlapping bookings)
   - Returns only available rooms

2. **Booking Process**
   - Validates date selections
   - Collects guest information
   - Creates pending booking
   - Shows payment simulation
   - Confirms and saves booking

3. **Payment Simulation**
   - Displays complete booking details
   - Shows cost breakdown
   - Accepts payment method selection
   - Processes payment confirmation

4. **Cancellation Logic**
   - Updates booking status to CANCELLED
   - Marks room as available
   - Saves changes to file
   - Refreshes UI tables

## 📈 Statistics Dashboard

Track important metrics:
- **Total Rooms**: Count of all rooms in the system
- **Available Rooms**: Number of currently bookable rooms
- **Booked Rooms**: Number of occupied rooms
- **Total Revenue**: Sum of all confirmed bookings

## 🎨 Color Scheme

- **Primary** (#34495E): Dark slate for headers
- **Accent** (#2980B9): Bright blue for interactive elements
- **Success** (#2ECC71): Emerald green for positive actions
- **Danger** (#E74C3C): Red for delete/cancel actions
- **Warning** (#F1C40F): Yellow for booked status
- **Info** (#9B59B6): Purple for revenue and information
- **Light BG** (#ECF0F1): Clean gray background

## 🔄 Future Enhancements

- [ ] MySQL/PostgreSQL database integration
- [ ] PDF receipt generation
- [ ] Email notification system
- [ ] Advanced search filters (price range, amenities)
- [ ] Room photo gallery
- [ ] Customer review system
- [ ] Discount codes and promotions
- [ ] Multi-user authentication
- [ ] Admin dashboard with analytics
- [ ] Mobile-responsive web version

## 📝 Sample Data

The system comes pre-loaded with 8 rooms:

| Room # | Category | Price/Night | Capacity |
|--------|----------|-------------|----------|
| 101    | Standard | $100        | 2 guests |
| 102    | Standard | $100        | 2 guests |
| 103    | Standard | $110        | 3 guests |
| 201    | Deluxe   | $200        | 2 guests |
| 202    | Deluxe   | $200        | 2 guests |
| 203    | Deluxe   | $220        | 4 guests |
| 301    | Suite    | $350        | 4 guests |
| 302    | Suite    | $400        | 6 guests |

## 🐛 Error Handling

The system handles:
- Invalid date selections
- Room unavailability conflicts
- Missing guest information
- Conflicting reservations
- File I/O errors
- Null pointer exceptions

## 👨‍💻 Author

**Vinod**  
Code Alpha Internship Program  
Task 4: Hotel Reservation System

## 📄 License

This project is created for educational purposes as part of the Code Alpha Internship Program.

## 🙏 Acknowledgments

- Code Alpha for the internship opportunity
- Java Swing documentation
- Open source community for inspiration

---

**⭐ If you find this project useful, please consider giving it a star!**

For detailed documentation, see [HOTEL_SYSTEM_GUIDE.md](HOTEL_SYSTEM_GUIDE.md)
