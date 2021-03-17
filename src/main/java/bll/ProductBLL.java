/**
 * This class is used for implementing specific product methods
 *
 * @author Mitrica Livia Maria
 * @group 30424
 *
 * @param validators list of validators to ensure correct data
 * @param productDAO used for inserting or updating product in database
 * @param report_no used for counting generated reports
 *
 */
package bll;

import bll.validator.ProductPriceValidator;
import bll.validator.ProductStockValidator;
import bll.validator.Validator;
import databaseAccess.AbstractDAO;
import databaseAccess.ProductDAO;
import model.Product;
import presentation.ReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class ProductBLL extends AbstractBLL<Product> {

    private List<Validator<Product>> validators;
    private AbstractDAO productDAO;
    private static int report_no =1;

    /**
     * constructor using super method
     * adds to the validators list the existing validators
     * creates a data access object
     */
    public ProductBLL() {
        super(Product.class, new ProductDAO());
        validators = new ArrayList<>();
        validators.add(new ProductPriceValidator());
        validators.add(new ProductStockValidator());

        productDAO = new ProductDAO();
    }

    /**
     * this method is used to insert an object in the database
     * validate stock and price
     * search by name the product
     * if product exists in the database we need to update the stock, otherwise a newly created product is inserted
     * @param product object we want to insert
     * @return inserted product
     */
    public Product insertP(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        ProductDAO productDAO = new ProductDAO();
        Product productInDB = null;

        productInDB = productDAO.findByName(product.getName_product());

        //update product because it already exists in the table
        if( productInDB != null) {
            productInDB.setQuantity( productInDB.getQuantity()+product.getQuantity());
            return productDAO.updateById(productInDB.getId_product(), productInDB);
        }

        // otherwise we must insert new product
        return productDAO.insert(product);
    }

    /**
     * this method is used for generating reports, by creating a new object of BLL and calling the method findAll()
     * results are saved in a list and then the corresponding method is called from reportGenerator class
     * number of generated reports is incremented
     * @throws Exception if report cannot genereated
     */
    public void genearteReport() throws Exception {
        ProductBLL productBLL= new ProductBLL();
        List<Product> products = new ArrayList<>();
        products = productBLL.findAll();
        ReportGenerator reportGenerator = new ReportGenerator("reportProducts", report_no);
        report_no++;
        reportGenerator.writeProductsTablePdf(products);
    }
}
