import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * TASK 4: Hotel Reservation System
 * A comprehensive system to search, book, and manage hotel rooms with
 * room categorization, payment simulation, and file-based storage.
 */
public class HotelReservationSystem extends JFrame {
    private HotelManager hotelManager;
    private JTabbedPane tabbedPane;

    // Search Panel Components
    private JComboBox<RoomCategory> categoryComboBox;
    private JSpinner checkInDateSpinner;
    private JSpinner checkOutDateSpinner;
    private JSpinner guestsSpinner;
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;

    // Booking Panel Components
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;

    // Modern Color Palette
    private static final Color PRIMARY_COLOR = new Color(52, 73, 94); // Dark slate
    private static final Color ACCENT_COLOR = new Color(41, 128, 185); // Bright blue
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113); // Emerald green
    private static final Color DANGER_COLOR = new Color(231, 76, 60); // Red
    private static final Color WARNING_COLOR = new Color(241, 196, 15); // Yellow
    private static final Color INFO_COLOR = new Color(155, 89, 182); // Purple
    private static final Color LIGHT_BG = new Color(236, 240, 241); // Light gray
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(44, 62, 80);
    private static final Color TEXT_SECONDARY = new Color(127, 140, 141);

    public HotelReservationSystem() {
        hotelManager = new HotelManager();
        initializeGUI();
        loadRoomsToTable();
        loadBookingsToTable();
    }

    private void initializeGUI() {
        setTitle("🏨 Luxury Hotel Reservation System");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_BG);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(CARD_BG);
        tabbedPane.setForeground(TEXT_PRIMARY);
        tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add tabs
        tabbedPane.addTab("🔍 Search & Book", createSearchPanel());
        tabbedPane.addTab("📋 My Bookings", createBookingsPanel());
        tabbedPane.addTab("🏨 Room Management", createManagementPanel());

        add(tabbedPane);
    }

    private JPanel createSearchPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(LIGHT_BG);

        // Search criteria panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Search Rooms",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                PRIMARY_COLOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Room Category
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Room Category:"), gbc);
        gbc.gridx = 1;
        categoryComboBox = new JComboBox<>(RoomCategory.values());
        categoryComboBox.insertItemAt(null, 0);
        categoryComboBox.setSelectedIndex(0);
        categoryComboBox.setPreferredSize(new Dimension(250, 35));
        categoryComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryComboBox.setBackground(Color.WHITE);
        categoryComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(5, 10, 5, 10)));
        searchPanel.add(categoryComboBox, gbc);

        // Check-in Date
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel checkInLabel = new JLabel("📅 Check-in Date:");
        checkInLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkInLabel.setForeground(TEXT_PRIMARY);
        searchPanel.add(checkInLabel, gbc);
        gbc.gridx = 1;
        Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        SpinnerDateModel checkInModel = new SpinnerDateModel(tomorrow, new Date(), null, Calendar.DAY_OF_MONTH);
        checkInDateSpinner = new JSpinner(checkInModel);
        JSpinner.DateEditor checkInEditor = new JSpinner.DateEditor(checkInDateSpinner, "MMM dd, yyyy");
        checkInEditor.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 12));
        checkInDateSpinner.setEditor(checkInEditor);
        checkInDateSpinner.setPreferredSize(new Dimension(250, 35));
        checkInDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        searchPanel.add(checkInDateSpinner, gbc);

        // Check-out Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel checkOutLabel = new JLabel("📅 Check-out Date:");
        checkOutLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkOutLabel.setForeground(TEXT_PRIMARY);
        searchPanel.add(checkOutLabel, gbc);
        gbc.gridx = 1;
        Date dayAfterTomorrow = new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);
        SpinnerDateModel checkOutModel = new SpinnerDateModel(dayAfterTomorrow, new Date(), null,
                Calendar.DAY_OF_MONTH);
        checkOutDateSpinner = new JSpinner(checkOutModel);
        JSpinner.DateEditor checkOutEditor = new JSpinner.DateEditor(checkOutDateSpinner, "MMM dd, yyyy");
        checkOutDateSpinner.setEditor(checkOutEditor);
        checkOutDateSpinner.setPreferredSize(new Dimension(200, 30));
        searchPanel.add(checkOutDateSpinner, gbc);

        // Number of Guests
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel guestsLabel = new JLabel("👥 Number of Guests:");
        guestsLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        guestsLabel.setForeground(TEXT_PRIMARY);
        searchPanel.add(guestsLabel, gbc);
        gbc.gridx = 1;
        guestsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        guestsSpinner.setPreferredSize(new Dimension(250, 35));
        guestsSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ((JSpinner.DefaultEditor) guestsSpinner.getEditor()).getTextField()
                .setFont(new Font("Segoe UI", Font.PLAIN, 12));
        guestsSpinner.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        searchPanel.add(guestsSpinner, gbc);

        // Search Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton searchButton = createStyledButton("Search Available Rooms", PRIMARY_COLOR);
        searchButton.addActionListener(e -> searchRooms());
        searchPanel.add(searchButton, gbc);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Results table
        String[] columns = { "Room #", "Category", "Price/Night", "Capacity", "Amenities", "Status" };
        roomsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomsTable = new JTable(roomsTableModel);
        roomsTable.setRowHeight(35);
        roomsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roomsTable.setSelectionBackground(new Color(52, 152, 219, 50));
        roomsTable.setSelectionForeground(TEXT_PRIMARY);
        roomsTable.setGridColor(new Color(236, 240, 241));
        roomsTable.setShowGrid(true);
        roomsTable.setIntercellSpacing(new Dimension(1, 1));
        roomsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        roomsTable.getTableHeader().setBackground(PRIMARY_COLOR);
        roomsTable.getTableHeader().setForeground(Color.WHITE);
        roomsTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        roomsTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollPane = new JScrollPane(roomsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Book button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(LIGHT_BG);
        JButton bookButton = createStyledButton("✅ Book Selected Room", SUCCESS_COLOR);
        bookButton.setPreferredSize(new Dimension(220, 45));
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookButton.addActionListener(e -> bookSelectedRoom());
        buttonPanel.add(bookButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createBookingsPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(LIGHT_BG);

        // Title
        JLabel titleLabel = new JLabel("📋 My Reservations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Bookings table
        String[] columns = { "Booking ID", "Room #", "Category", "Guest Name", "Check-in", "Check-out", "Guests",
                "Total", "Status" };
        bookingsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(bookingsTableModel);
        bookingsTable.setRowHeight(35);
        bookingsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bookingsTable.setSelectionBackground(new Color(46, 204, 113, 50));
        bookingsTable.setSelectionForeground(TEXT_PRIMARY);
        bookingsTable.setGridColor(new Color(236, 240, 241));
        bookingsTable.setShowGrid(true);
        bookingsTable.setIntercellSpacing(new Dimension(1, 1));
        bookingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        bookingsTable.getTableHeader().setBackground(PRIMARY_COLOR);
        bookingsTable.getTableHeader().setForeground(Color.WHITE);
        bookingsTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        bookingsTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(LIGHT_BG);

        JButton viewDetailsButton = createStyledButton("👁️ View Details", ACCENT_COLOR);
        viewDetailsButton.setPreferredSize(new Dimension(160, 40));
        viewDetailsButton.addActionListener(e -> viewBookingDetails());
        buttonPanel.add(viewDetailsButton);

        JButton cancelButton = createStyledButton("❌ Cancel Booking", DANGER_COLOR);
        cancelButton.setPreferredSize(new Dimension(180, 40));
        cancelButton.addActionListener(e -> cancelBooking());
        buttonPanel.add(cancelButton);

        JButton refreshButton = createStyledButton("🔄 Refresh", INFO_COLOR);
        refreshButton.setPreferredSize(new Dimension(140, 40));
        refreshButton.addActionListener(e -> loadBookingsToTable());
        buttonPanel.add(refreshButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createManagementPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(LIGHT_BG);

        JLabel titleLabel = new JLabel("🏨 Room Management Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Statistics panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        statsPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        statsPanel.setBackground(LIGHT_BG);

        statsPanel.add(createStatCard("🏢 Total Rooms", String.valueOf(hotelManager.getTotalRooms()), ACCENT_COLOR));
        statsPanel.add(createStatCard("✅ Available", String.valueOf(hotelManager.getAvailableRooms()), SUCCESS_COLOR));
        statsPanel.add(createStatCard("📅 Booked", String.valueOf(hotelManager.getBookedRooms()), WARNING_COLOR));
        statsPanel.add(createStatCard("💰 Revenue", "$" + hotelManager.getTotalRevenue(), INFO_COLOR));

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(LIGHT_BG);

        JButton addRoomButton = createStyledButton("➕ Add New Room", SUCCESS_COLOR);
        addRoomButton.setPreferredSize(new Dimension(180, 45));
        addRoomButton.addActionListener(e -> addNewRoom());
        buttonPanel.add(addRoomButton);

        JButton resetDataButton = createStyledButton("🗑️ Reset All Data", DANGER_COLOR);
        resetDataButton.setPreferredSize(new Dimension(180, 45));
        resetDataButton.addActionListener(e -> resetAllData());
        buttonPanel.add(resetDataButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 3, true),
                new EmptyBorder(25, 25, 25, 25)));
        card.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_SECONDARY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 0),
                new EmptyBorder(8, 15, 8, 15)));

        // Enhanced hover effect with animation-like feel
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bgColor.darker(), 2),
                        new EmptyBorder(6, 13, 6, 13)));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bgColor.darker(), 0),
                        new EmptyBorder(8, 15, 8, 15)));
            }
        });

        return button;
    }

    private void loadRoomsToTable() {
        roomsTableModel.setRowCount(0);
        for (Room room : hotelManager.getAllRooms()) {
            roomsTableModel.addRow(new Object[] {
                    room.getRoomNumber(),
                    room.getCategory(),
                    "$" + room.getPricePerNight(),
                    room.getMaxCapacity() + " guests",
                    room.getAmenities(),
                    room.isAvailable() ? "Available" : "Booked"
            });
        }
    }

    private void searchRooms() {
        Date checkIn = (Date) checkInDateSpinner.getValue();
        Date checkOut = (Date) checkOutDateSpinner.getValue();

        if (checkIn == null || checkOut == null) {
            JOptionPane.showMessageDialog(this, "Please select check-in and check-out dates.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        RoomCategory category = (RoomCategory) categoryComboBox.getSelectedItem();
        int guests = (int) guestsSpinner.getValue();

        List<Room> availableRooms = hotelManager.searchRooms(category, guests, checkIn, checkOut);

        roomsTableModel.setRowCount(0);
        for (Room room : availableRooms) {
            roomsTableModel.addRow(new Object[] {
                    room.getRoomNumber(),
                    room.getCategory(),
                    "$" + room.getPricePerNight(),
                    room.getMaxCapacity() + " guests",
                    room.getAmenities(),
                    "Available"
            });
        }

        if (availableRooms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No rooms available for the selected criteria.", "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void bookSelectedRoom() {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to book.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date checkIn = (Date) checkInDateSpinner.getValue();
        Date checkOut = (Date) checkOutDateSpinner.getValue();

        if (checkIn == null || checkOut == null) {
            JOptionPane.showMessageDialog(this, "Please select check-in and check-out dates.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String roomNumber = roomsTable.getValueAt(selectedRow, 0).toString();
        int guests = (int) guestsSpinner.getValue();

        // Get guest information
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        Object[] message = {
                "Guest Name:", nameField,
                "Email:", emailField,
                "Phone:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Guest Information", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION || nameField.getText().trim().isEmpty()) {
            return;
        }

        String guestName = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        // Create booking
        Booking booking = hotelManager.createBooking(roomNumber, guestName, email, phone, checkIn, checkOut, guests);

        if (booking != null) {
            // Show payment dialog
            showPaymentDialog(booking);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create booking. Room may no longer be available.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showPaymentDialog(Booking booking) {
        JDialog paymentDialog = new JDialog(this, "Payment Simulation", true);
        paymentDialog.setSize(500, 600);
        paymentDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Booking details
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));

        detailsPanel.add(new JLabel("Booking ID:"));
        detailsPanel.add(new JLabel(booking.getBookingId()));

        detailsPanel.add(new JLabel("Room Number:"));
        detailsPanel.add(new JLabel(booking.getRoom().getRoomNumber()));

        detailsPanel.add(new JLabel("Room Category:"));
        detailsPanel.add(new JLabel(booking.getRoom().getCategory().toString()));

        detailsPanel.add(new JLabel("Guest Name:"));
        detailsPanel.add(new JLabel(booking.getGuestName()));

        detailsPanel.add(new JLabel("Check-in:"));
        detailsPanel.add(new JLabel(new SimpleDateFormat("MMM dd, yyyy").format(booking.getCheckInDate())));

        detailsPanel.add(new JLabel("Check-out:"));
        detailsPanel.add(new JLabel(new SimpleDateFormat("MMM dd, yyyy").format(booking.getCheckOutDate())));

        detailsPanel.add(new JLabel("Number of Nights:"));
        detailsPanel.add(new JLabel(String.valueOf(booking.getNumberOfNights())));

        detailsPanel.add(new JLabel("Price per Night:"));
        detailsPanel.add(new JLabel("$" + booking.getRoom().getPricePerNight()));

        detailsPanel.add(new JLabel("Total Amount:"));
        JLabel totalLabel = new JLabel("$" + booking.getTotalAmount());
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(SUCCESS_COLOR);
        detailsPanel.add(totalLabel);

        mainPanel.add(detailsPanel, BorderLayout.NORTH);

        // Payment method
        JPanel paymentPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment Method"));

        ButtonGroup paymentGroup = new ButtonGroup();
        JRadioButton creditCard = new JRadioButton("Credit Card", true);
        JRadioButton debitCard = new JRadioButton("Debit Card");
        JRadioButton paypal = new JRadioButton("PayPal");

        paymentGroup.add(creditCard);
        paymentGroup.add(debitCard);
        paymentGroup.add(paypal);

        paymentPanel.add(creditCard);
        paymentPanel.add(debitCard);
        paymentPanel.add(paypal);

        mainPanel.add(paymentPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton confirmButton = createStyledButton("Confirm Payment", SUCCESS_COLOR);
        confirmButton.addActionListener(e -> {
            booking.setStatus(BookingStatus.CONFIRMED);
            hotelManager.saveBookings();
            JOptionPane.showMessageDialog(paymentDialog,
                    "Payment Successful!\n\nBooking ID: " + booking.getBookingId() +
                            "\n\nA confirmation email has been sent to " + booking.getEmail(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            paymentDialog.dispose();
            loadBookingsToTable();
            loadRoomsToTable();
            tabbedPane.setSelectedIndex(1); // Switch to bookings tab
        });

        JButton cancelButton = createStyledButton("Cancel", DANGER_COLOR);
        cancelButton.addActionListener(e -> {
            hotelManager.cancelBooking(booking.getBookingId());
            paymentDialog.dispose();
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        paymentDialog.add(mainPanel);
        paymentDialog.setVisible(true);
    }

    private void loadBookingsToTable() {
        bookingsTableModel.setRowCount(0);
        for (Booking booking : hotelManager.getAllBookings()) {
            bookingsTableModel.addRow(new Object[] {
                    booking.getBookingId(),
                    booking.getRoom().getRoomNumber(),
                    booking.getRoom().getCategory(),
                    booking.getGuestName(),
                    new SimpleDateFormat("MMM dd, yyyy").format(booking.getCheckInDate()),
                    new SimpleDateFormat("MMM dd, yyyy").format(booking.getCheckOutDate()),
                    booking.getNumberOfGuests(),
                    "$" + booking.getTotalAmount(),
                    booking.getStatus()
            });
        }
    }

    private void viewBookingDetails() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to view.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookingId = bookingsTable.getValueAt(selectedRow, 0).toString();
        Booking booking = hotelManager.getBookingById(bookingId);

        if (booking != null) {
            StringBuilder details = new StringBuilder();
            details.append("BOOKING DETAILS\n");
            details.append("================\n\n");
            details.append("Booking ID: ").append(booking.getBookingId()).append("\n");
            details.append("Guest Name: ").append(booking.getGuestName()).append("\n");
            details.append("Email: ").append(booking.getEmail()).append("\n");
            details.append("Phone: ").append(booking.getPhone()).append("\n\n");
            details.append("Room Number: ").append(booking.getRoom().getRoomNumber()).append("\n");
            details.append("Category: ").append(booking.getRoom().getCategory()).append("\n");
            details.append("Amenities: ").append(booking.getRoom().getAmenities()).append("\n\n");
            details.append("Check-in: ").append(new SimpleDateFormat("MMM dd, yyyy").format(booking.getCheckInDate()))
                    .append("\n");
            details.append("Check-out: ").append(new SimpleDateFormat("MMM dd, yyyy").format(booking.getCheckOutDate()))
                    .append("\n");
            details.append("Number of Nights: ").append(booking.getNumberOfNights()).append("\n");
            details.append("Number of Guests: ").append(booking.getNumberOfGuests()).append("\n\n");
            details.append("Total Amount: $").append(booking.getTotalAmount()).append("\n");
            details.append("Status: ").append(booking.getStatus()).append("\n");

            JTextArea textArea = new JTextArea(details.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Booking Details",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cancelBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String bookingId = bookingsTable.getValueAt(selectedRow, 0).toString();
        String status = bookingsTable.getValueAt(selectedRow, 8).toString();

        if (status.equals("CANCELLED")) {
            JOptionPane.showMessageDialog(this, "This booking is already cancelled.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this booking?\nBooking ID: " + bookingId,
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (hotelManager.cancelBooking(bookingId)) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadBookingsToTable();
                loadRoomsToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addNewRoom() {
        JDialog dialog = new JDialog(this, "Add New Room", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField roomNumberField = new JTextField(10);
        JComboBox<RoomCategory> categoryBox = new JComboBox<>(RoomCategory.values());
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(100.0, 50.0, 1000.0, 10.0));
        JSpinner capacitySpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Room Number:"), gbc);
        gbc.gridx = 1;
        panel.add(roomNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        panel.add(categoryBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Price per Night:"), gbc);
        gbc.gridx = 1;
        panel.add(priceSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Max Capacity:"), gbc);
        gbc.gridx = 1;
        panel.add(capacitySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton addButton = createStyledButton("Add Room", SUCCESS_COLOR);
        addButton.addActionListener(e -> {
            String roomNumber = roomNumberField.getText().trim();
            if (roomNumber.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a room number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            RoomCategory category = (RoomCategory) categoryBox.getSelectedItem();
            double price = (double) priceSpinner.getValue();
            int capacity = (int) capacitySpinner.getValue();

            Room room = new Room(roomNumber, category, price, capacity);
            hotelManager.addRoom(room);

            JOptionPane.showMessageDialog(dialog, "Room added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            loadRoomsToTable();
        });
        panel.add(addButton, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void resetAllData() {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset all data?\nThis will delete all bookings and reset rooms.",
                "Confirm Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            hotelManager.resetData();
            loadRoomsToTable();
            loadBookingsToTable();
            JOptionPane.showMessageDialog(this, "All data has been reset.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            HotelReservationSystem system = new HotelReservationSystem();
            system.setVisible(true);
        });
    }
}

/**
 * Room Category Enumeration
 */
enum RoomCategory {
    STANDARD("Standard Room", "Basic amenities"),
    DELUXE("Deluxe Room", "Premium amenities, City view"),
    SUITE("Suite", "Luxury amenities, Separate living area, Premium view");

    private String displayName;
    private String description;

    RoomCategory(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

/**
 * Room Class
 */
class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roomNumber;
    private RoomCategory category;
    private double pricePerNight;
    private int maxCapacity;
    private boolean available;
    private String amenities;

    public Room(String roomNumber, RoomCategory category, double pricePerNight, int maxCapacity) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.maxCapacity = maxCapacity;
        this.available = true;
        this.amenities = generateAmenities(category);
    }

    private String generateAmenities(RoomCategory category) {
        switch (category) {
            case STANDARD:
                return "WiFi, TV, AC";
            case DELUXE:
                return "WiFi, Smart TV, AC, Mini-bar, City View";
            case SUITE:
                return "WiFi, Smart TV, AC, Mini-bar, Jacuzzi, Premium View, Living Area";
            default:
                return "Basic Amenities";
        }
    }

    // Getters and Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getAmenities() {
        return amenities;
    }
}

/**
 * Booking Status Enumeration
 */
enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED
}

/**
 * Booking Class
 */
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookingId;
    private Room room;
    private String guestName;
    private String email;
    private String phone;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfGuests;
    private BookingStatus status;
    private double totalAmount;
    private Date bookingDate;

    public Booking(Room room, String guestName, String email, String phone,
            Date checkInDate, Date checkOutDate, int numberOfGuests) {
        this.bookingId = generateBookingId();
        this.room = room;
        this.guestName = guestName;
        this.email = email;
        this.phone = phone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.status = BookingStatus.PENDING;
        this.bookingDate = new Date();
        this.totalAmount = calculateTotalAmount();
    }

    private String generateBookingId() {
        return "BK" + System.currentTimeMillis();
    }

    private double calculateTotalAmount() {
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        int nights = (int) (diffInMillies / (1000 * 60 * 60 * 24));
        return nights * room.getPricePerNight();
    }

    public int getNumberOfNights() {
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        return (int) (diffInMillies / (1000 * 60 * 60 * 24));
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getBookingDate() {
        return bookingDate;
    }
}

/**
 * Hotel Manager - Manages rooms and bookings with file I/O
 */
class HotelManager {
    private List<Room> rooms;
    private List<Booking> bookings;
    private static final String ROOMS_FILE = "rooms.dat";
    private static final String BOOKINGS_FILE = "bookings.dat";

    public HotelManager() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        loadData();

        // Initialize with sample rooms if empty
        if (rooms.isEmpty()) {
            initializeSampleRooms();
        }
    }

    private void initializeSampleRooms() {
        rooms.add(new Room("101", RoomCategory.STANDARD, 100.0, 2));
        rooms.add(new Room("102", RoomCategory.STANDARD, 100.0, 2));
        rooms.add(new Room("103", RoomCategory.STANDARD, 110.0, 3));
        rooms.add(new Room("201", RoomCategory.DELUXE, 200.0, 2));
        rooms.add(new Room("202", RoomCategory.DELUXE, 200.0, 2));
        rooms.add(new Room("203", RoomCategory.DELUXE, 220.0, 4));
        rooms.add(new Room("301", RoomCategory.SUITE, 350.0, 4));
        rooms.add(new Room("302", RoomCategory.SUITE, 400.0, 6));
        saveRooms();
    }

    public List<Room> searchRooms(RoomCategory category, int guests, Date checkIn, Date checkOut) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getMaxCapacity() >= guests) {
                if (category == null || room.getCategory() == category) {
                    if (isRoomAvailable(room, checkIn, checkOut)) {
                        availableRooms.add(room);
                    }
                }
            }
        }

        return availableRooms;
    }

    private boolean isRoomAvailable(Room room, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber().equals(room.getRoomNumber()) &&
                    booking.getStatus() != BookingStatus.CANCELLED) {
                // Check for date overlap
                if (!(checkOut.before(booking.getCheckInDate()) || checkIn.after(booking.getCheckOutDate()))) {
                    return false;
                }
            }
        }
        return room.isAvailable();
    }

    public Booking createBooking(String roomNumber, String guestName, String email,
            String phone, Date checkIn, Date checkOut, int guests) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null && isRoomAvailable(room, checkIn, checkOut)) {
            Booking booking = new Booking(room, guestName, email, phone, checkIn, checkOut, guests);
            bookings.add(booking);
            saveBookings();
            return booking;
        }
        return null;
    }

    public boolean cancelBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                booking.setStatus(BookingStatus.CANCELLED);
                booking.getRoom().setAvailable(true);
                saveBookings();
                return true;
            }
        }
        return false;
    }

    public Room getRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public Booking getBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        saveRooms();
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public int getTotalRooms() {
        return rooms.size();
    }

    public int getAvailableRooms() {
        int count = 0;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    public int getBookedRooms() {
        return getTotalRooms() - getAvailableRooms();
    }

    public double getTotalRevenue() {
        double revenue = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus() == BookingStatus.CONFIRMED ||
                    booking.getStatus() == BookingStatus.COMPLETED) {
                revenue += booking.getTotalAmount();
            }
        }
        return revenue;
    }

    public void resetData() {
        bookings.clear();
        for (Room room : rooms) {
            room.setAvailable(true);
        }
        saveRooms();
        saveBookings();
    }

    // File I/O Operations
    @SuppressWarnings("unchecked")
    private void loadData() {
        // Load rooms
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOMS_FILE))) {
            rooms = (List<Room>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, will be created
        } catch (Exception e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }

        // Load bookings
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {
            bookings = (List<Booking>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, will be created
        } catch (Exception e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
    }

    public void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOMS_FILE))) {
            oos.writeObject(rooms);
        } catch (Exception e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }

    public void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
        } catch (Exception e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }
}
