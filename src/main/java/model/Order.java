/**
 * This is the Order class containing getters, setters and toString method
 * @param id_order represents order id
 * @param name_client represents name of the client making the order
 * @param name_product name of the product that is ordered
 * @param price_product price of the ordered product
 * @param quantity quantity requested by client
 * @param total sum client will have to pay
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */
package model;

public class Order {
    private int id_order;
    private String name_client;
    private String name_product;
    private float price_product;
    private int quantity;
    private float total;

    public Order(){

    }
    /**
     * Constructor, creates order with the following parameters
     * @param name_client name of the client placing the order
     * @param name_product name of the product that is ordered
     * @param quantity quantity requested by client
     * @param total sum client will have to pay
     */
    public Order(String name_client, String name_product, int quantity, float total) {
        this.name_client = name_client;
        this.name_product = name_product;
        this.quantity = quantity;
        this.total = total;
    }
    /**
     * Gets the order id
     * @return integer representing order number
     */
    public int getId_order() {
        return id_order;
    }
    /**
     * Sets the id of the order
     * @param id_order setting the id of the order
     */
    public void setId_order(int id_order) {
        this.id_order = id_order;
    }
    /**
     * Gets the client name
     * @return string representing client name
     */
    public String getName_client() {
        return name_client;
    }
    /**
     * Sets the name of the client
     * @param name_client setting the name of the client
     */
    public void setName_client(String name_client) {
        this.name_client = name_client;
    }
    /**
     * Gets the product name
     * @return string representing product name
     */
    public String getName_Product() {
        return name_product;
    }
    /**
     * Sets the name of teh product
     * @param name_product setting the name of the product
     */
    public void setName_product(String name_product) {
        this.name_product = name_product;
    }
    /**
     * Gets the product quantity requested by client
     * @return integer representing quantity requested
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Sets the quantity of the product
     * @param quantity setting the quantity requested by client
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Gets the order total
     * @return float representing order total
     */
    public float getTotal() {
        return total;
    }
    /**
     * Sets the total of the client
     * @param total setting the total value client needs to pay for the order
     */
    public void setTotal(float total) {
        this.total = total;
    }
    /**
     * Gets the order price
     * @return float representing unit product price
     */
    public float getPrice_product() {
        return price_product;
    }
    /**
     * Sets the price of the client
     * @param price_product setting the price of teh product
     */
    public void setPrice_product(float price_product) {
        this.price_product = price_product;
    }
    /**
     * method used for printing datails about an order when it cannot be processed, due to insufficient stock or invalid client
     * @return string containing name of the client, name of the product, quantity
     */
    @Override
    public String toString() {
        return  "name client=" + name_client + '\n' +
                "name product= " + name_product + '\n' +
                "quantity= " + quantity + '\n' ;
    }
}
