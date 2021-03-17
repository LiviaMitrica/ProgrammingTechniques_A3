/**
 * This is the abstract class designed to access the database for an object
 * The name of the table we want to operate with is considered to be the name of the class
 *
 * @author Mitrica Livia Maria
 * @group 30424
 * @param LOGGER this object is used for logging information about the application
 * @param type this object is used for retrieving the class of the object we are working with
 */
package databaseAccess;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.DBConnection;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    /**
     * constructor with no parameters which initializes the type with the actual class of the object
     */
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * method used for creating a select query, the table is considered to be the class of the object
     * @param field this is used in SQL statement to filter the results according to a specified column
     * @return String containg the select query
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + "=?");
        return sb.toString();
    }

    /**
     * method used to retrieve all entries in a table
     * @return a list of objects which has been retrieved from the table, considering that an object's fields map 1-1 with table columns
     * @throws SQLException if query cannot be performed
     */
    public List<T> findAll() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = new StringBuilder().append("SELECT * FROM ").append(type.getSimpleName()).toString();
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            DBConnection.close(resultSet);
            DBConnection.close(statement);
            DBConnection.close(connection);
        }
        return null;
    }

    /**
     * find a table entry according to its id
     * @param id integer representing the value for which we want to obtain table entry
     * @return object with the given id in the table or null in case it does not exist
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id_"+type.getSimpleName());
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            DBConnection.close(resultSet);
            DBConnection.close(statement);
            DBConnection.close(connection);
        }
        return null;
    }

    /**
     * find a table entry according to its name
     * @param name Striing representing the value for which we want to obtain table entry
     * @return object with the given name in the table or null in case it does not exist
     */
    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name_"+ type.getSimpleName().toLowerCase());
        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            //if(resultSet.next())
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            System.out.println("error finding by name");
            e.printStackTrace();
        } finally {
            DBConnection.close(resultSet);
            DBConnection.close(statement);
            DBConnection.close(connection);
        }
        return null;
    }

    /**
     *this method is used to generate a list of objects corresponding to the resultSet retried from a query
     * @param resultSet set which contains table entries for which we want to obtain objects of class T
     * @return a list of objects T corresponding to the resultSet
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.getConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IntrospectionException | SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *this method is used to insert an object in a table
     * @param t object we want to insert
     * @return object inserted
     */
    public T insert(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(type.getSimpleName().toLowerCase());
        query.append(" (");

        for ( Field f: t.getClass().getDeclaredFields()){
            if(f.getName().equals("id_"+type.getSimpleName().toLowerCase()))
                continue;
            query.append(f.getName());
            query.append(",");

        }

        query.deleteCharAt(query.length()-1);
        query.append(") VALUES (");

        for( Field f: t.getClass().getDeclaredFields()){
            if (f.getName().equals("id_"+type.getSimpleName().toLowerCase()))
                continue;
            query.append("'");
            f.setAccessible(true);
            try {
                query.append(f.get(t).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            query.append("',");
        }

        query.deleteCharAt(query.length()-1);
        query.append(")");

        modifyTable(query.toString());
        return t;
    }

    /**
     *this method is used to update an entry in the table which has the id given as parameter
     * @param id integer representing the value for which we want to update values
     * @param t new object that will replace the one having the specified id
     * @return id of the updated entry
     */
    public T updateById(int id, T t) {

        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(type.getSimpleName().toLowerCase());
        query.append(" SET ");

        for ( Field f: t.getClass().getDeclaredFields()){
            if( f.getName().equals("id_"+t.getClass().getSimpleName().toLowerCase()) )
                continue;
            query.append(f.getName());
            query.append("='");
            f.setAccessible(true);

            try {
                query.append(f.get(t).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            query.append("',");
        }

        query.deleteCharAt(query.length()-1);
        query.append(" WHERE id_");
        query.append(t.getClass().getSimpleName().toLowerCase());
        query.append("=");
        query.append(id);
        query.append(";");

        modifyTable(query.toString());
        return t;
    }

    /**
     *this method is used to delete a table entry having the name equal to the given String
     * @param name String for which we want to delete the entry in the table
     * @return id of the deleted entry
     */
    public int deleteByName (String name){
        String query= new String();
        query = "DELETE FROM "+type.getSimpleName()+" WHERE name_"+type.getSimpleName().toLowerCase()+"='"+name+"'";
        return modifyTable(query.toString());
    }

    /**
     * this method is used to execute an update in a table based on a given query
     * @param query string containing the query to be executed
     * @return id of the entry which has been inserted/deleted/updated
     */
    protected int modifyTable(String query){
        Connection connection = null;
        PreparedStatement statement = null;
        int insertedID=-1;

        try {
            connection = DBConnection.getConnection();
            statement = connection.prepareStatement(query, statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next())
                insertedID = rs.getInt(1);

        } catch (SQLException e) {
            //System.out.println("cannot perform operation.");
            e.printStackTrace();
        } finally {
            DBConnection.close(statement);
            DBConnection.close(connection);
        }
        return insertedID;
    }
}
