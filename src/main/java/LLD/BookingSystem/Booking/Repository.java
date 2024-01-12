package LLD.BookingSystem.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String insertBooking = "INSERT INTO booking (user_id, status, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
    private String updateBooking = "UPDATE booking SET status = ?, updatedAt = ? WHERE id = ?";
    private String getBookingById = "SELECT id,status,createdAt,updatedAt from booking where id = ?";
    public Repository(){
    }
    public int addBooking(Connection connection,Booking booking){
        int rowsImpacted = -1;
        try (PreparedStatement statement = connection.prepareStatement(insertBooking)){
            statement.setInt(1,booking.getUserId());
            statement.setString(2,booking.getStatus().toString());
            statement.setTimestamp(3,new Timestamp(booking.getCreatedTime().getMillis()));
            statement.setTimestamp(4,new Timestamp(booking.getUpdatedTime().getMillis()));
            rowsImpacted = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowsImpacted;
    }

    public int updateBooking(Connection connection,Booking booking){
        int rowsUpdated = -1;
        try (PreparedStatement statement = connection.prepareStatement(updateBooking)){
            statement.setString(1,booking.getStatus().toString());
            statement.setTimestamp(2,new Timestamp(booking.getUpdatedTime().getMillis()));
            statement.setInt(3,booking.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowsUpdated;
    }

    public Booking getBooking(Connection connection,int bookingId){
        Booking booking = new Booking();
        try (PreparedStatement statement = connection.prepareStatement(getBookingById)){
            statement.setInt(1,bookingId);
            try (ResultSet resultSet = statement.executeQuery()){
                List<Booking> bookingList = parseBooking(resultSet);
                if (!bookingList.isEmpty()) booking = bookingList.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return booking;
    }
    private List<Booking> parseBooking(ResultSet resultSet){
        List<Booking> list = new ArrayList<>();
        try {
            while (resultSet.next()){
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setUserId(resultSet.getInt("user_id"));
                booking.setStatus(resultSet.getString("status"));
                list.add(booking);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
