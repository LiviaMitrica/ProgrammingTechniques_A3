/**
 * This is the Product class containing getters, setters
 * @param id_product represents product id
 * @param name_product name of the product
 * @param quantity represents the stock available
 * @param price price per unit of the product
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */

package model;

public class Product {
    private int id_product ;
    private String name_product;
    private int quantity;
    private float price;

    public Product(){

    }

    /**
     * constructor for product, taking the following parameters
     * @param name represents product name
     * @param stock represents stock to be added
     * @param price represents product price
     */
    public Product( String name, int stock, float price) {
        this.id_product = -1;
        this.name_product = name;
        this.quantity = stock;
        this.price = price;
    }

    /**
     * Gets the product id
     * @return integer representing product id
     */
    public int getId_product() {
        return id_product;
    }

    /**
     * Sets the id of the product
     * @param id_product setting the id of the product
     */
    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
    /**
     * Gets product name
     * @return string representing product name
     */
    public String getName_product() {
        return name_product;
    }

    /**
     * Sets product name
     * @param name_product string representing product name
     */
    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    /**
     * Gets product avaialble quantity
     * @return integer representing product available stock
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Sets stock of the product
     * @param quantity representing product quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Gets product price
     * @return floating number representing price per unit of the product
     */
    public float getPrice() {
        return price;
    }
    /**
     * Sets price of the product
     * @param price floating number representing the price of the product
     */
    public void setPrice(float price) {
        this.price = price;
    }

}
