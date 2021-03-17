/**
 * This is the class validating product price
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */

package bll.validator;

import model.Product;

public class ProductPriceValidator implements Validator<Product> {
    /**
     * method which compoares the price to 0 and in a case of a negativa value or 0 throws an exception
     * @param product this represents the product for which we want to test the price attribute
     */

    public void validate(Product product) {
        if(product.getPrice()<=0)
            throw new IllegalArgumentException("The price must be a strictly positive number.");
    }
}
