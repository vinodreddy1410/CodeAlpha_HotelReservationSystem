# Hotel Reservation System - User Guide

## ✅ Task 4: Hotel Reservation System - Complete

A comprehensive Java-based hotel management system with GUI for searching, booking, and managing hotel rooms.

## Features Implemented

### ✓ Room Categorization
- **Standard Rooms**: Basic amenities (WiFi, TV, AC) - $100-110/night
- **Deluxe Rooms**: Premium amenities with city view (WiFi, Smart TV, AC, Mini-bar) - $200-220/night  
- **Suites**: Luxury amenities with premium view (WiFi, Smart TV, AC, Mini-bar, Jacuzzi, Living Area) - $350-400/night

### ✓ Search & Booking
- Search by room category, dates, and number of guests
- Real-time availability checking
- Date range validation
- Capacity-based filtering

### ✓ Reservation Management
- Create new bookings with guest information
- View booking details
- Cancel reservations
- Automatic booking ID generation
- Status tracking (Pending, Confirmed, Cancelled, Completed)

### ✓ Payment Simulation
- Interactive payment dialog
- Multiple payment methods (Credit Card, Debit Card, PayPal)
- Detailed booking summary
- Price calculation (nights × room price)
- Confirmation system

### ✓ Database/File I/O
- Persistent storage using Java Serialization
- Automatic save on all operations
- Files: `rooms.dat` and `bookings.dat`
- Data preservation across sessions

### ✓ OOP Design
- Room class with encapsulation
- Booking class with business logic
- HotelManager for centralized management
- Enum for categories and status
- Clean separation of concerns

## How to Run

```powershell
# Compile
javac HotelReservationSystem.java

# Run
java HotelReservationSystem
```

## User Interface

The application has 3 main tabs:

### 1. 🔍 Search & Book
- Select room category (or leave empty for all categories)
- Choose check-in and check-out dates using date spinners
- Set number of guests
- Click "Search Available Rooms" to see results
- Select a room and click "Book Selected Room"
- Enter guest information (name, email, phone)
- Complete payment simulation

### 2. 📋 My Bookings
- View all reservations in a table
- See booking details (ID, room, dates, guests, total, status)
- **View Details**: See complete booking information
- **Cancel Booking**: Cancel a reservation (frees up the room)
- **Refresh**: Reload bookings from file

### 3. 🏨 Room Management
- Dashboard with statistics:
  - Total Rooms
  - Available Rooms
  - Booked Rooms  
  - Total Revenue
- **Add New Room**: Create new rooms with custom settings
- **Reset All Data**: Clear all bookings and reset system

## Sample Rooms

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

## Technical Details

### Classes

**HotelReservationSystem** (Main GUI)
- JFrame-based interface with tabbed panels
- Search, booking, and management views
- Event handling for all user actions

**Room**
- Properties: roomNumber, category, pricePerNight, maxCapacity, available, amenities
- Serializable for file storage
- Automatic amenities assignment based on category

**Booking**
- Properties: bookingId, room, guest info, dates, status, totalAmount
- Auto-generated unique booking IDs
- Calculates total amount and number of nights

**HotelManager**
- Centralized business logic
- Room and booking management
- File I/O operations (load/save)
- Search and availability checking
- Statistics calculation

**RoomCategory** (Enum)
- STANDARD, DELUXE, SUITE
- Display names and descriptions

**BookingStatus** (Enum)
- PENDING, CONFIRMED, CANCELLED, COMPLETED

### Data Persistence

- **rooms.dat**: Stores all room objects
- **bookings.dat**: Stores all booking objects
- Uses Java Serialization (ObjectOutputStream/ObjectInputStream)
- Automatic save on create, update, delete operations
- Automatic load on application startup

### Search Logic

The search algorithm:
1. Filters by room capacity (>= requested guests)
2. Filters by category (if specified)
3. Checks date availability:
   - No overlapping bookings
   - Excludes cancelled bookings
4. Returns only available rooms

### Payment Flow

1. User selects room and clicks "Book"
2. System creates pending booking
3. Payment dialog shows:
   - Complete booking details
   - Total calculation
   - Payment method selection
4. On confirm:
   - Booking status → CONFIRMED
   - Room marked as booked
   - Data saved to file
5. On cancel:
   - Booking removed
   - Room remains available

## Features

✅ Modern GUI with color-coded elements  
✅ Date pickers with validation  
✅ Real-time availability checking  
✅ Complete CRUD operations  
✅ File-based persistence  
✅ Revenue tracking  
✅ Responsive table views  
✅ Error handling and validation  
✅ Hover effects on buttons  
✅ Clean OOP architecture  

## Color Scheme

- **Primary Blue** (#2980B9): Headers, search button
- **Success Green** (#27AE60): Confirm, available status
- **Danger Red** (#E74C3C): Cancel, delete actions  
- **Orange** (#E67E22): Booked status
- **Purple** (#8E44AD): Revenue display

## Data Files

The application creates two files in the same directory:
- `rooms.dat` - All room information
- `bookings.dat` - All booking records

To start fresh, simply delete these files and restart the application.

## Requirements

- Java 8 or higher
- No external dependencies (uses only Java Swing)
- Windows/Mac/Linux compatible

## Error Handling

The system handles:
- Invalid date selections
- Room unavailability
- Missing guest information
- Conflicting reservations
- File I/O errors
- Null value checks

## Future Enhancements (Optional)

- PDF receipt generation
- Email notifications
- Advanced search filters
- Room photo gallery
- Customer history
- Discount codes
- Multi-user support
- Database migration (MySQL, PostgreSQL)

---

**Developed for Code Alpha Internship - Task 4**
