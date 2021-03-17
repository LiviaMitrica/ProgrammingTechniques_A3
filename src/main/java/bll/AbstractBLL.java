/**
 * This is the generic class used for implementing the logic level in the application.
 * The methods provided go directly to the data access level
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */
package bll;

import databaseAccess.AbstractDAO;
import databaseAccess.ProductDAO;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public class AbstractBLL<T> {

    protected Class<T> type;
    protected AbstractDAO<T> DAO;

    /**
     * constructor method
     * @param type the class of the object
     * @param DAO used for accessing the data access methods corresponding to this type object
     */
    public AbstractBLL(Class<T> type, AbstractDAO<T> DAO) {
        this.type = type;
        this.DAO = DAO;
    }

    /**
     * Gets the type of class
     * @return class type
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * this method searches for all entries in a table
     * @return list of objects containing all the entries in the table
     * @throws SQLException in case query cannot be executed
     */
    public List<T> findAll() throws SQLException {
        return DAO.findAll();
    }

    /**
     * this method deletes an entry having the value of column name equal to the given parameter
     * @param name string which tells what value to be looked for and deleted in the table
     * @return id of the deleted record
     */
    public int deleteByName(String name) {
        return DAO.deleteByName(name);
    }

    /**
     * this method inserts a new object in the table
     * @param t object to be inserted
     * @return inserted object
     */
    public T insert(T t) {
        return DAO.insert(t);
    }

    /**
     * this method updates the table record having the given id
     * @param id record identifier which we want to update
     * @param t object which will replace the old object
     * @return replaced object
     */
    public T updateById(int id, T t) {
        return DAO.updateById(id, t);
    }

    /**
     * this method finds the record having the id equal to the given parameter
     * @param id integer identifier for the table entry we want to retrieve
     * @return object found in database
     */
    public T findById(int id) {
        return DAO.findById(id);
    }

    /**
     * this method searches for an entry having the value of column name equal to the given parameter
     * @param name string containing the new looked for
     * @return object which has the given name
     */
    public T findByName(String name){
        return DAO.findByName(name);
    }
}
