/**
 * This is the class validating product quantity
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */
package bll.validator;

import model.Product;

public class ProductStockValidator implements Validator<Product> {
    /**
     * method which compares the price to 0 and in a case of a negative value or 0 throws an exception
     * @param product this represents the product for which we want to test the quantity attribute
     */
    public void validate(Product product) {
        if(product.getQuantity()<=0)
            throw new IllegalArgumentException("The stock must be a strictly positive integer.");
    }
}
