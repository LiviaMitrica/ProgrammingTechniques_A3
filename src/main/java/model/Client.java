/**
 * This is the Client class containing getters, setters
 *
 * @author Mitrica Livia Maria
 * @group 30424
 *
 * @param id_client represents client id
 * @param name_client represents client name
 * @param address represents client address
 */

package model;

public class Client {

    private int id_client;
    private String name_client;
    private String address;

    public Client(){

    }

    /** Constructor
     * Creates a client with the specified name and address
     * @param name represents client name
     * @param address represents client address
     */
    public Client( String name, String address) {
        this.name_client = name;
        this.address = address;
    }
    /**
     * Gets the client id
     * @return integer representing client id
     */
    public int getId_client() {
        return id_client;
    }
    /**
     * Sets the id of the client
     * @param id_client setting the id of the client
     */
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    /**
     * Gets the client name
     * @return String representing client name
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
     * Gets the client address
     * @return string representing client address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the address of the client
     * @param address setting the name of the client
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
