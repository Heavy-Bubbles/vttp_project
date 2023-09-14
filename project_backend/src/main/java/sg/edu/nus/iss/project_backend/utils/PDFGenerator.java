package sg.edu.nus.iss.project_backend.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.models.Invoice;
import sg.edu.nus.iss.project_backend.models.Services;

@Service
public class PDFGenerator {
    
    public InputStream generatePDF(Invoice invoice, List<Services> services, Customer customer) throws IOException{
        
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try{
            PdfWriter.getInstance(document, baos);
            document.open();
            
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(26);
            Paragraph title = new Paragraph("Invoice", fontTitle);
            title.setAlignment(Paragraph.TITLE);
            document.add(title);
            
            Font fontbody = FontFactory.getFont(FontFactory.HELVETICA);
            fontbody.setSize(12);
            Paragraph invoiceId = new Paragraph("Invoice ID: " + invoice.getId(), fontbody);
            invoiceId.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(invoiceId);
            
            Long timeStamp = invoice.getInvoiceDate();
            Date date = new Date(timeStamp);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = dateFormat.format(date);
            Paragraph invoiceDate = new Paragraph("Invoice Date: " + dateString, fontbody);
            invoiceDate.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(invoiceDate);

            String customerName = customer.getName();
            Paragraph name = new Paragraph("Billed to: " + customerName, fontbody);
            invoiceDate.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(name);

            Font fontthead = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontthead.setSize(18);
            Paragraph thead = new Paragraph("Services Provided:", fontthead);
            thead.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(thead);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100f);
            table.setWidths(new int[] {3,3,3,3});
            table.setSpacingBefore(5);
            
            
            PdfPCell cell = new PdfPCell();
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader.setSize(12);
            
            cell.setPhrase(new Phrase("Service ID:", fontHeader));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Service Name:", fontHeader));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Duration(mins):", fontHeader));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Price:", fontHeader));
            table.addCell(cell);
            
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            for (Services s : services){
                table.addCell(String.valueOf(s.getId()));
                table.addCell(s.getName());
                table.addCell(String.valueOf(s.getDurationInMinutes()));
                String formattedPrice = decimalFormat.format(s.getPrice());
                table.addCell("$" + formattedPrice);
            }
            document.add(table);

            String formattedPrice = decimalFormat.format(invoice.getAmountDue());
            Paragraph amountDue = new Paragraph("Amount Due: $" + formattedPrice, fontHeader);
            amountDue.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(amountDue);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        byte[] pdfBytes = baos.toByteArray();
        return new ByteArrayInputStream(pdfBytes);
    }
}
