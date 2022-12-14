import java.sql.*;
import java.util.HashSet;

public class DBManagement{
    public static void main(String[] args) {
        try{
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "sadomazo911");

            getProduct(connection);
            getUserById(connection, 7);
            updateProductName(connection, 3, "Macbook");
            updateAddress(connection,7, "France, Paris 102-90");
//            updateAddress(connection,7, "USA, New York 101-23");

            getRank(connection, 177);
            getUsers(connection, false);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getProduct(Connection connection){
        String getProductsQuery = "SELECT * FROM test.Product";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getProductsQuery);

            resultSet.next();
            System.out.println(resultSet.getString(2));
            System.out.println(" "+resultSet.getString(3));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getUserById(Connection connection, int id){
        String getProductsQuery = "SELECT * FROM test.User WHERE id = "+id+";";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getProductsQuery);

            resultSet.next();
            System.out.println(resultSet.getString(2));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateProductName(Connection connection, int id, String productName) {
        String updateProductQuery = "UPDATE test.Product SET Name = ? WHERE id = ?";

        try{
            PreparedStatement pStatement = connection.prepareStatement(updateProductQuery);
            pStatement.setString(1, productName);
            pStatement.setInt(2, id);

            pStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateAddress(Connection connection, int id, String newAddress){
        String foundAddress = "SELECT Address_id FROM test.User WHERE id = "+id+";";
        int address_id = -1;
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(foundAddress);

            resultSet.next();
            address_id = resultSet.getInt(1);

        }catch (Exception e){
            e.printStackTrace();
        }

        if(address_id != -1) {
            String updateUserAddress = "UPDATE test.Address SET Full_Address = ? WHERE id = "+address_id+";";

            try{
                PreparedStatement pStatement = connection.prepareStatement(updateUserAddress);
                pStatement.setString(1, newAddress);

                pStatement.executeUpdate();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void getRank (Connection connection, double height) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall("{? = call test.getAllCustomerRankByHeight(?)}");
        callableStatement.registerOutParameter(1, Types.VARCHAR);
        callableStatement.setDouble(2, height);

        ResultSet rs = callableStatement.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString(1));
        }
        callableStatement.close();
    }

    public static void getUsers(Connection connection, boolean asc) throws SQLException{
        PreparedStatement pStatement = connection.prepareStatement("{call test.getUsersOrder(?)}");
        pStatement.setBoolean(1, asc);

        ResultSet rs = pStatement.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString(1));
        }
        pStatement.close();

    }

}
