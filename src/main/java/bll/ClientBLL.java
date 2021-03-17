/**
 * This method is used for implementing specific client methods
 *
 * @author Mitrica Livia Maria
 * @group 30424
 *
 * @param report_no used for identifying reports
 */
package bll;

import databaseAccess.ClientDAO;
import model.Client;
import presentation.ReportGenerator;

import java.util.ArrayList;
import java.util.List;

public class ClientBLL extends AbstractBLL<Client> {

    private static int report_no=1;

    /**
     * default contructor
     */
    public ClientBLL() {
        super(Client.class, new ClientDAO());
    }

    /**
     * this method is used for generating reports, by creating a new object of BLL and calling the method findAll()
     * results are saved in a list and then the corresponding method is called from reportGenerator class
     * number of generated reports is incremented
     * @throws Exception if report cannot genereated
     */
    public void genearteReport() throws Exception {
        ClientBLL clientBLL= new ClientBLL();
        List<Client> clients = new ArrayList<>();
        clients = clientBLL.findAll();
        ReportGenerator reportGenerator = new ReportGenerator("ReportClients", report_no);
        report_no ++;
        reportGenerator.writeClientsTablePdf(clients);
    }
}
