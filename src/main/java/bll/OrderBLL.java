/**
 * This class is used for implementing specific order methods
 *
 * @author Mitrica Livia Maria
 * @group 30424
 *
 */
package bll;

import databaseAccess.OrderDAO;

import model.Client;
import model.Order;
import model.Product;
import presentation.ReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class OrderBLL extends AbstractBLL<Order>  {

    private static int report_no =1;

    /**
     * default constructor
     */
    public OrderBLL() {
        super(Order.class, new OrderDAO());
    }

    /**
     * this method is used for creating a new order
     * first retrieve client and product id and then
     * if both client and product are in the database check the quantity requested to be smaller or equal than the available stock
     * if yes, insert the newly created order, update stock quantity and generate corresponding bill
     * otherwise print a message saying insufficient stock in the pdf
     * @param order received order that needs to be processed
     * @return inserted order
     * @throws Exception in case the order cannot be inserted
     */
    public Order newOrder(Order order) throws Exception {
        ClientBLL clientBLL = new ClientBLL();
        Client currentClient = clientBLL.findByName(order.getName_client());

        ProductBLL productBLL = new ProductBLL();
        Product currentDBProduct = productBLL.findByName(order.getName_Product());//get the product from database

        if(currentDBProduct!=null && currentClient!=null) {

            if( currentDBProduct.getQuantity() >= order.getQuantity() ) {
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.insert(order);//add the order in the database
                currentDBProduct.setQuantity(currentDBProduct.getQuantity() - order.getQuantity());//set product new quantity
                productBLL.updateById(currentDBProduct.getId_product(), currentDBProduct);//update product new quantity

                ReportGenerator file = new ReportGenerator("bill", order.getId_order());//file.writeInPDF(order.toString());
                List<Order> orders = new ArrayList<Order>();
                orders.add(order);
                file.writeOrderTablePdf(orders);
            }
            else{
                ReportGenerator file = new ReportGenerator("bill", 110);
                file.writeInPDF("Cannot process the following order:\n\n"+ order.toString()+"\nInsufficient stock.");
            }
        }
        return order;
    }

    /**
     * this method is used for generating reports, by creating a new object of BLL and calling the method findAll()
     * results are saved in a list and then the corresponding method is called from reportGenerator class
     * @throws Exception if report cannot genereated
     */
    public void genearteReport() throws Exception {
        OrderBLL orderBLL= new OrderBLL();
        List<Order> orders = new ArrayList<>();
        orders = orderBLL.findAll();
        ReportGenerator reportGenerator = new ReportGenerator("reportOrders", report_no);
        report_no++;

        reportGenerator.writeOrderTablePdf(orders);
    }
}
