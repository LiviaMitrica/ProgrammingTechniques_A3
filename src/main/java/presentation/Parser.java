/**
 * This is the class used for reading the input file and process the commands given as input in the text file
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */
package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public Parser() {
    }

    /**
     * method used for opening the file and reading the content line by line
     * at each line the first string is checked to see if the user wants to insert, delete, order or receive a report and according to the first string
     * one of the other methods is called
     *
     * content has been separated using split, delimitators are ",: "
     * @param file String containing the name of the file
     */
    public void parse(String file) {
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String input = myReader.nextLine();
                String[] string = input.split("[,: ]+");

                try {
                    if(string[0].toLowerCase().equals("insert"))
                        insert_command(string);
                    if(string[0].toLowerCase().equals("report"))
                        report_command(string);
                    if(string[0].toLowerCase().equals("delete"))
                        delete_command(string);
                    if(string[0].toLowerCase().equals("order"))
                        order_command(string);
                }
                catch(Exception e) {
                    System.out.println("An error occurred. You have introduced incorrect data.");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Cannot find file.");
        }
    }

    /**
     * method used to insert an object into the table
     * check the second string of the array to see if it is product or client, create the corresponding object
     * create a new object of BLL corresponding to the entry you want to insert and call insert method
     * in case the operation cannot be performed, throw an exception
     * @param string array of strings storing the content of the current line
     */
    private void insert_command(String[] string){
        if(string[1].equals("client")){
            String name, address;
            try {
                name = string[2]+" "+string[3];
                address = string[4];
                Client client = new Client(name, address);
                ClientBLL clientBLL = new ClientBLL();
                int id = clientBLL.insert(client).getId_client();
                client.setId_client(id);
            } catch (Exception e) {
                System.out.println("An error occurred. Cannot process client information.");
            }
        }
        else
        if(string[1].toLowerCase().equals("product")){
            String name;
            int stock;
            float price;
            try {
                name = string[2];
                stock = Integer.parseInt(string[3]);
                price = Float.parseFloat(string[4]);
                Product product = new Product( name, stock, price);
                ProductBLL productBLL = new ProductBLL();
                productBLL.insertP(product);
            } catch (Exception e) {
                //e.printStackTrace();
                //System.out.println("An error occurred. Cannot insert product information.");
            }
        }
    }

    /**
     * method used to delete an object into the table
     * check the second string of the array to see if it is product or client, create the corresponding object
     * create a new object of BLL corresponding to the entry you want to delete and call insert method
     * in case the operation cannot be performed, throw an exception
     * @param string array of strings storing the content of the current line
     */
    private void delete_command(String[] string){
        String name;
        try {
            if(string[1].toLowerCase().equals("client")){
                name = string[2]+" "+string[3];
                ClientBLL clientBLL = new ClientBLL();
                int id = clientBLL.deleteByName(name);
            }
            if(string[1].toLowerCase().equals("product")){
                name = string[2];
                System.out.println(name);
                ProductBLL productBLL = new ProductBLL();
                int id = productBLL.deleteByName(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred. Cannot delete information.");
        }
    }

    /**
     * method used to generate a report for one of the models of the application
     * check the second string of the array to see if it is product, order or client
     * create a new object of BLL corresponding to the report you want to obtain
     * in case the operation cannot be performed, throw an exception
     * @param string array of strings storing the content of the current line
     */
    private void report_command(String[] string){
        try{
            String field = string[1];
            if(field.equals("client")){
                ClientBLL clientBLL = new ClientBLL();
                clientBLL.genearteReport();
            }

            if(field.equals("product")){
                ProductBLL productBLL = new ProductBLL();
                productBLL.genearteReport();
            }

            if(field.equals("order")){
                OrderBLL orderBLL = new OrderBLL();
                orderBLL.genearteReport();
            }

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Cannot generate report.");
        }
    }

    /**
     * method used to insert an order into the table
     * retrieve the informetion of the order, name of product, name of client, quantity and create a new object of type Order
     * create a new object OrderBLL and call newOrder method
     * in case the operation cannot be performed, throw an exception
     * @param string array of strings storing the content of the current line
     * @throws FileNotFoundException in case it cannot print to file
     */
    private void order_command(String[] string) throws FileNotFoundException {
        String name_client, name_product;
        int quantity;
        name_client = string[1]+" "+string[2];
        name_product = string[3];
        quantity = Integer.parseInt(string[4]);
        Order order = new Order(name_client, name_product, quantity, 0);
        try {
            OrderBLL orderBLL = new OrderBLL();
            orderBLL.newOrder(order);
        } catch (Exception e) {
            ReportGenerator rG = new ReportGenerator("error",111);
            rG.writeInPDF("Cannot process the following order:\n\n"+ order.toString()+"\nInexistent product or client.");
        }
    }
}
