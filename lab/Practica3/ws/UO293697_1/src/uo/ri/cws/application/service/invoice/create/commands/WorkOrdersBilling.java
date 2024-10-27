package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Round;

public class WorkOrdersBilling implements Command<InvoiceDto> {

    private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private InvoiceGateway ing = Factories.persistence.forInvoice();
    private List<String> workOrderIds = new ArrayList<>();

    public WorkOrdersBilling(List<String> lista) {
        ArgumentChecks.isNotNull(lista,
            "la lista de ordenes no puede " + "se nula");
        ArgumentChecks.isTrue(lista.size() != 0,
            "la lista no puede estar vacia");
        lista.forEach((String id) -> {
            ArgumentChecks.isNotBlank(id, "El id es invalido");
            ArgumentChecks.isNotNull(id, "El id es invalido");
            this.workOrderIds.add(id);
        });
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
        InvoiceDto dto = new InvoiceDto();
        try {

            if (!checkWorkOrdersExist(workOrderIds)) {
                throw new BusinessException("Workorder does not exist");
            }
            if (!checkWorkOrdersFinished(workOrderIds)) {
                throw new BusinessException("Workorder is not finished yet");
            }

            long numberInvoice = generateInvoiceNumber();
            LocalDate dateInvoice = LocalDate.now();
            double amount = calculateTotalInvoice(workOrderIds);
            double vat = vatPercentage(amount, dateInvoice);
            double total = amount * (1 + vat / 100);
            total = Round.twoCents(total);

            String idInvoice = createInvoice(numberInvoice, dateInvoice,
                total - amount, total);

            update(idInvoice);

            dto.number = numberInvoice;
            dto.date = dateInvoice;
            dto.amount = total;
            dto.vat = (total - amount);
            dto.id = idInvoice;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dto;
    }

    /**
     * Establece a cada workorder el id de la invoice actual y la pone en estado
     * invoice
     * 
     * @param idInvoice
     */
    private void update(String idInvoice) {

        for (String id : workOrderIds) {
            WorkOrderRecord rc = wog.findById(id).get();

            rc.invoiceId = idInvoice;
            rc.state = "INVOICED";

            wog.update(rc);
        }

    }

    /*
     * checks whether every work order exist
     */
    private boolean checkWorkOrdersExist(List<String> workOrderIDS) {

        for (String id : workOrderIDS) {
            try {
                wog.findById(id).get();
            } catch (NoSuchElementException e) {
                return false;
            }
        }
        return true;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private boolean checkWorkOrdersFinished(List<String> workOrderIDS)
        throws SQLException, BusinessException {

        for (String id : workOrderIDS) {
            WorkOrderRecord rs = wog.findById(id).get();

            String state = rs.state;
            if (!"FINISHED".equalsIgnoreCase(state)) {
                return false;
            }
        }
        return true;
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private Long generateInvoiceNumber() throws SQLException {

        return ing.getNextInvoiceNumber();

    }

    /*
     * Compute total amount of the invoice (as the total of individual work
     * orders' amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDS)
        throws BusinessException, SQLException {

        double totalInvoice = 0.0;
        for (String workOrderID : workOrderIDS) {
            totalInvoice += getWorkOrderTotal(workOrderID);
        }
        return totalInvoice;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private Double getWorkOrderTotal(String workOrderID)
        throws SQLException, BusinessException {

        WorkOrderRecord rc;

        try {
            rc = wog.findById(workOrderID).get();
        } catch (NoSuchElementException e) {
            throw new BusinessException(
                "Workorder " + workOrderID + " doesn't exist");
        }
        return rc.amount;
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
        return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0
            : 18.0;

    }

    /*
     * Creates the invoice in the database; returns the id
     */
    private String createInvoice(long numberInvoice, LocalDate dateInvoice,
        double vat, double total) {
        String idInvoice = UUID.randomUUID().toString();
        InvoiceRecord rc = new InvoiceRecord();

        rc.id = idInvoice;
        rc.amount = total;
        rc.vat = vat;
        rc.number = numberInvoice;
        rc.date = dateInvoice;
        rc.state = "NOT_YET_PAID";
        rc.version = 1L;

        ing.add(rc);

        return idInvoice;

    }
}
