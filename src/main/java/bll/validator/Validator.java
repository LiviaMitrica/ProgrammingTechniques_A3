/**
 * Interface used for product attributes validation
 */

package bll.validator;

public interface Validator<T> {

    public void validate(T t);
}
