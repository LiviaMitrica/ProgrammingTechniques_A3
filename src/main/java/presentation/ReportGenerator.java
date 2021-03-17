/**
 * This is the class used for reading the input file and writing the pdf files
 *
 * @author Mitrica Livia Maria
 * @group 30424
 */
package presentation;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import model.Client;
import model.Order;
import model.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    private String name;

    /**
     * constructor for the file generator
     * builds a string out of the 2 parameters and adds pdf extension
     * @param type String which can have the values bill or report
     * @param no integer which tells the number of the report
     */
    public ReportGenerator(String type, int no) {
        this.name = type+no+".pdf";
    }

    /**
     * method used for writing in a pdf file a string given as a parameter
     * create a new document and try to add a new paragraph
     * @param string text to be printed in the pdf
     * @throws FileNotFoundException in case it cannot create a pdf file
     */
    public void writeInPDF(String string) throws FileNotFoundException {
        PdfDocument writer = new PdfDocument(new PdfWriter(name));
        Document document = new Document(writer, PageSize.A4.rotate());
        try {
            document.add(new Paragraph(string));
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot print pdf.");
        }
    }

    /**
     * method used for printing a table of clients in a pdf
     * create the table header with the fields of the class client
     * insert each client in the list in the table
     * align table to center
     * @param list represents the list of clients to be inserted in the table
     * @throws Exception in case document cannot be created
     */
    public void writeClientsTablePdf(List<Client> list) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(name));
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        float[] columnWidths = new float[]{1,7.5f,7.5f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths) );
        Cell cell = createHeader("Clients' report", 3);
        table.addHeaderCell(cell);

        for (int i = 0; i < 3; i++) {
            Cell[] headerFooter = new Cell[]{
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("#")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Full name")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Address"))
            };

            for (Cell hfCell : headerFooter)
                if (i == 0)
                    table.addHeaderCell(hfCell);
        }

        for (int i=0; i<list.size(); i++) {
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(list .get(i).getId_client()))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(list .get(i).getName_client())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(list .get(i).getAddress())));
        }

        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(table);
        doc.close();
    }

    /**
     * method used for printing a table of products in a pdf
     * create the table header with the fields of the class product
     * insert each product in the list in the table
     * align table to center
     * @param list this represents the list of products
     * @throws Exception in case document cannot be created
     */
    public void writeProductsTablePdf(List<Product> list) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(name));
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        float[] columnWidths = new float[]{1,7.5f,5f, 5f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths) );
        Cell cell = createHeader("Products' report", 4);
        table.addHeaderCell(cell);

        for (int i = 0; i < 4; i++) {
            Cell[] headerFooter = new Cell[]{
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("#")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Product name")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Available stock")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Price"))
            };
            for (Cell hfCell : headerFooter)
                if (i == 0)
                    table.addHeaderCell(hfCell);
        }

        for (int i=0; i<list.size(); i++) {
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(i+1))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(list.get(i).getName_product())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(list.get(i).getQuantity()))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(list.get(i).getPrice()))));
        }

        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(table);
        doc.close();
    }

    /**
     * method used for printing a table of orders in a pdf
     * create the table header with the fields of the class order
     * insert each order in the list in the table
     * align table to center
     * @param order list of orders we want to display in the table
     * @throws Exception in case document cannot be created
     */
    public void writeOrderTablePdf(List<Order> order) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(name));
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        float[] columnWidths = new float[]{1, 1, 7.5f, 7.5f, 5f, 5f, 5f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths) );

        Cell cell = new Cell();
        if(order.size()==1)
            cell = createHeader("Order "+order.get(0).getId_order(), 7);
        else
            cell = createHeader("Report Orders", 7);
        table.addHeaderCell(cell);

        for (int i = 0; i < 7; i++) {
            Cell[] headerFooter = new Cell[]{
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("#")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Order no")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Client name")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Product name")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Quantity")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Price")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Total"))
            };
            for (Cell hfCell : headerFooter)
                if (i == 0)
                    table.addHeaderCell(hfCell);
        }

        for( int i=0; i<order.size(); i++) {
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(i + 1))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(order.get(i).getId_order()))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(order.get(i).getName_client())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(order.get(i).getName_Product())));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(order.get(i).getQuantity()))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(order.get(i).getPrice_product()))));
            table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(order.get(i).getTotal()))));

        }
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(table);
        doc.close();
    }

    /**
     * method used for generting a header cell with a specified title on a specific number of columns
     * @param title represents the title of the report
     * @param colspan tells on how many columns should the header cell spread
     * @return Cell containing the header
     * @throws IOException in case it cannot genertae header cell
     */
    private Cell createHeader (String title, int colspan) throws IOException {
        PdfFont f = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Cell cell = new Cell(1, colspan)
                .add((IBlockElement) new Paragraph(title))
                .setFont(f)
                .setFontSize(13)
                .setFontColor(DeviceGray.WHITE)
                .setBackgroundColor(DeviceGray.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
        return cell;
    }
}
