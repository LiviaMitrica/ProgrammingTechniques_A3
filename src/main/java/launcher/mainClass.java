/**
 * This is the application launcher
 * @param args[0] represents the input file
 * @param parser this is the parameter used for reading the input file
 *
 * The Order management program implements an application that can insert and delete clients, products, orders in a data base and
 * generates pdf reports for clients, orders, products
 * Commands will be processed from a text file given as command line argument
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */

package launcher;

import presentation.Parser;

public class mainClass {
    public static void main(String[] args) {
        Parser parser = new Parser();
        if(args.length==1){
            parser.parse(args[0]);
        }

    }
}
