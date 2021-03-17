/**
 * This class is used for doing order specific data access
 * It extends AbstractDAO
 *
 * @author Mitrica Livia Maria
 * @group 30424
 *
 * @param connection used for connecting to the database
 */
package databaseAccess;

import bll.ClientBLL;
import bll.ProductBLL;
import connection.DBConnection;
import model.Client;
import model.Order;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<Order> {

    private Connection connection = null;

    /**
     * override method, because the information is split in two dable and Class order does not map table Order
     * select from table 'order' the ids and the clients' and products' ids
     * retrieve information about client and product and then select quantity from the table order_details
     * create a new order with this information and add it to the list
     * @return list of orders found in the table
     * @throws SQLException in case query cannot be executed
     */
    @Override
    public List<Order> findAll() throws SQLException {
        List<Order> listOrders = new ArrayList<>();
        connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `order`");
        ResultSet rs = statement.executeQuery();
        try {
            while (rs.next()){
                Order order = new Order();
                ClientBLL clientBLL = new ClientBLL();
                Client client = clientBLL.findById(rs.getInt("id_client"));
                ProductBLL productBLL = new ProductBLL();
                Product product = productBLL.findById(rs.getInt("id_product"));

                order.setId_order(rs.getInt("id_order"));
                order.setName_client(client.getName_client());
                order.setName_product(product.getName_product());
                order.setPrice_product(product.getPrice());
                order.setTotal(rs.getFloat("total"));

                PreparedStatement statement_ = connection.prepareStatement("SELECT quantity FROM `order_details` WHERE id_order="+order.getId_order());
                ResultSet RS = statement_.executeQuery();

                if(RS.next())
                    order.setQuantity(RS.getInt("quantity"));

                listOrders.add(order);
            }
            return listOrders;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection);
        }
        return null;
    }

    /**
     * search in both tables, 'order' and 'order_details' to obtain all the information about the order having the specified id
     * @param id integer representing the value for which we want to obtain table entry
     * @return order having the requested id
     */
    @Override
    public Order findById(int id) {
        Order order = new Order(null, null, 0, 0);
        order.setId_order(id);

        try {
            if (connection.isClosed()) connection = DBConnection.getConnection();
            String query = "SELECT * FROM order WHERE id_order=" + id;

            ResultSet rs = modify(query);
            while (rs.next()) {
                int idCustomer = rs.getInt("id_client");
                int idProduct = rs.getInt("id_product");
                order.setName_client(new ClientDAO().findById(idCustomer).getName_client());
                order.setName_product(new ProductDAO().findById(idProduct).getName_product());
            }

            rs = modify("SELECT * FROM order_details where id_order=" + id);
            rs.next();
            order.setQuantity(rs.getInt("quantity"));
            return order;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * insert a new order in the table
     * retrieve client and product ids and insert them 'order' table
     * retrieve the id of the current order and insert the quantity in the table 'order_details'
     * @param order new order which needs to be inserted
     * @return inserted order
     */
    @Override
    public Order insert(Order order) {
        try {
            Connection connection = null;
            ResultSet rs = null;
            ClientBLL clientBLL = new ClientBLL();
            Client currentClient = clientBLL.findByName(order.getName_client());

            ProductBLL productBLL = new ProductBLL();
            Product currentProduct = productBLL.findByName(order.getName_Product());
            float total = currentProduct.getPrice()*order.getQuantity();

            String query1 = "INSERT INTO `order` (id_client, id_product, total) VALUES ('"+currentClient.getId_client()+"', '"+currentProduct.getId_product()+"', '"+total+"')";
            rs = modify(query1);

            if(rs.next()) {

                order.setId_order(rs.getInt(1));
                order.setTotal(total);
                order.setPrice_product(currentProduct.getPrice());
                String query2 = "INSERT INTO `order_details` (id_order, quantity) VALUES ('" + order.getId_order() + "', '" + order.getQuantity() + "');";
                modifyTable(query2);

            }

        } catch (Exception e) {
            System.out.println("Cannot insert order.\n");
            e.printStackTrace();
        }
        return order;
    }

    /**
     * this method is used to execute the execute queries
     * @param query String conataining the query we want to perform
     * @return ResultSet of the query which has been executed
     */
    private ResultSet modify(String query){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(query, statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
        } catch (SQLException e) {
            System.out.println("cannot perform operation.");
            e.printStackTrace();
        }

        return rs;
    }
}
