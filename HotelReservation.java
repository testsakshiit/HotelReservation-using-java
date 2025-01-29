import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HotelReservation 
{

    private static List<Room> availableRooms = new ArrayList<>();
    private static List<Room> bookedRooms = new ArrayList<>();
    private static double totalAmount = 0;

    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelTitle = new JLabel("Welcome to Hotel Reservation System", JLabel.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(labelTitle);
        panel.add(Box.createVerticalStrut(20));

        JLabel labelRoomType = new JLabel("Select Room Type:");
        panel.add(labelRoomType);
        String[] roomTypes = {"Single", "Double", "Suite"};
        JComboBox<String> roomTypeComboBox = new JComboBox<>(roomTypes);
        panel.add(roomTypeComboBox);
        panel.add(Box.createVerticalStrut(10));

        JLabel labelCheckIn = new JLabel("Enter Check-in Date (DD-MM-YYYY):");
        JTextField textCheckIn = new JTextField();
        JLabel labelCheckOut = new JLabel("Enter Check-out Date (DD-MM-YYYY):");
        JTextField textCheckOut = new JTextField();
        panel.add(labelCheckIn);
        panel.add(textCheckIn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(labelCheckOut);
        panel.add(textCheckOut);
        panel.add(Box.createVerticalStrut(20));

        JButton btnSearchRooms = new JButton("Search Available Rooms");
        panel.add(btnSearchRooms);
        DefaultListModel<String> roomListModel = new DefaultListModel<>();
        JList<String> roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(roomList);
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));

        JButton btnMakeReservation = new JButton("Make Reservation");
        panel.add(btnMakeReservation);
        panel.add(Box.createVerticalStrut(10));

        JLabel labelAmount = new JLabel("Total Amount: $0.00");
        panel.add(labelAmount);
        JLabel labelPaymentStatus = new JLabel("");
        panel.add(labelPaymentStatus);
        panel.add(Box.createVerticalStrut(20));

        btnSearchRooms.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String roomType = (String) roomTypeComboBox.getSelectedItem();
                String checkInDate = textCheckIn.getText();
                String checkOutDate = textCheckOut.getText();

                availableRooms.clear();
                roomListModel.clear(); 

                if (roomType.equals("Single")) 
                {
                    availableRooms.add(new Room("Single Room 101", 1000));
                    availableRooms.add(new Room("Single Room 102", 1000));
                } else if (roomType.equals("Double")) 
                {
                    availableRooms.add(new Room("Double Room 201", 1500));
                    availableRooms.add(new Room("Double Room 202", 1500));
                } else if (roomType.equals("Suite")) 
                {
                    availableRooms.add(new Room("Suite Room 301", 3000));
                    availableRooms.add(new Room("Suite Room 302", 3000));
                }

                for (Room room : availableRooms) 
                {
                    roomListModel.addElement(room.getName() + " - $" + room.getPrice() + " per night");
                }
            }
        });

        btnMakeReservation.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
             {
                String checkInDate = textCheckIn.getText();
                String checkOutDate = textCheckOut.getText();
                String selectedRoomName = roomList.getSelectedValue();

                if (selectedRoomName != null && !selectedRoomName.isEmpty()) 
                {
                    Room selectedRoom = null;
                    for (Room room : availableRooms) {
                        if (selectedRoomName.startsWith(room.getName())) {
                            selectedRoom = room;
                            break;
                        }
                    }

                    if (selectedRoom != null) 
                    {
                        availableRooms.remove(selectedRoom);
                        bookedRooms.add(selectedRoom);

                        totalAmount = selectedRoom.getPrice();

                        labelAmount.setText("Total Amount: $" + totalAmount);
                        labelPaymentStatus.setText("Payment Pending...");

                        JOptionPane.showMessageDialog(frame, "Reservation Successful!\nRoom: " + selectedRoom.getName() + "\nCheck-in: " + checkInDate + "\nCheck-out: " + checkOutDate);
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Please select a room first.");
                }
            }
        });

        labelPaymentStatus.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if (!bookedRooms.isEmpty()) 
                {
                    labelPaymentStatus.setText("Payment Successful!");
                    JOptionPane.showMessageDialog(frame, "Payment of $" + totalAmount + " completed.");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    static class Room 
    {
        private String name;
        private double price;

        public Room(String name, double price) 
        {
            this.name = name;
            this.price = price;
        }

        public String getName() 
        {
            return name;
        }

        public double getPrice() 
        {
            return price;
        }
    }
}
