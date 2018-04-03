package com.rimidev.backing;

import com.rimidev.maxbook.controller.ClientJpaController;
import com.rimidev.maxbook.entities.Book;
import com.rimidev.maxbook.entities.Client;
import com.rimidev.maxbook.entities.Invoice;
import com.rimidev.maxbook.entities.InvoiceDetails;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * Backing Bean for the downloads to help manage data.
 * 
 * @author Philippe Langlois-Pedroso, 1542705
 */
@Named
@RequestScoped
public class DownloadsBackingBean implements Serializable {

    @Inject
    private ClientJpaController clientController;

    private static final Logger logger = Logger.getLogger(DownloadsBackingBean.class.getName());

    private List<Book> allDownloads;
    private List<Book> model = null;
    private List<Integer> pages = null;
    private HttpSession session = null;
    private Client client = null;

    /**
     * Return a list of all owned books of a client
     * 
     * @param clientId
     * @return 
     */
    public List<Book> getAllOwnedBooks(int clientId) {
        if (this.model == null) {
            logger.info("DownloadsBackingBean -> model was null");
            Client c = clientController.findClient(clientId);
            List<Invoice> invoices = c.getInvoiceList().stream().filter( i -> i.getRemovalStatus() == false).collect(Collectors.toList());
            
            logger.info("Number of Associated Invoices: " +invoices.size());
            List<InvoiceDetails> details = new ArrayList();
            List<Book> books = new ArrayList();
            for(Invoice i : invoices){
               
                details.addAll(i.getInvoiceDetailsList().stream().filter(d -> d.getRemovalStatus() == false).collect(Collectors.toList()));
                logger.info("Number of : " +invoices.size());
            }
            for (InvoiceDetails d : details) {
                logger.info(d.getIsbn().getIsbn());
                books.add(d.getIsbn());
            }
            this.model = books;
        }
        return this.model;
    }

    /**
     * If the model for the downloads page is null, instantiate it. Populates
     * the model for display.
     * 
     * @return 
     */
    public List<Book> getDownloadsModel() {
        logger.info("DownloadsBackingBean -> getModel");
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        client = (Client) session.getAttribute("current_user");

        if (client == null) {
            logger.info("Client is not logged in: ");
            this.model = new ArrayList();
        } else {
            logger.info("Client is logged in: " + Integer.toString(client.getId()));
            this.model = getAllOwnedBooks(client.getId());
        }
        logger.info("Number of Downloads: " + Integer.toString(this.model.size()));
        return model;
    }

    /**
     * User will download a pdf of a book within the resources. Every download link
     * will download the same book.
     * 
     * @throws IOException 
     */
    public void getDownload() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        String filename = "Book.pdf";
        File file = new File("C:\\dev\\projects\\MaxBook\\src\\main\\webapp\\resources\\downloads\\defaultBook.pdf");
        ec.responseReset();
        ec.setResponseContentType("application/octet-stream");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        OutputStream out = ec.getResponseOutputStream();
        try {
            FileInputStream input = new FileInputStream(file);  
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = input.read(buffer)) != -1) {
                out.write(buffer);
                out.flush();
            }
            FacesContext.getCurrentInstance().getResponseComplete();
        } catch (IOException err) {
            err.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }

        fc.responseComplete();
    }
}