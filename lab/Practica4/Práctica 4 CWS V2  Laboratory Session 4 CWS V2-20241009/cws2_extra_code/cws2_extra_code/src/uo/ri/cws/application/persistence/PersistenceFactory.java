package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;

public class PersistenceFactory {

    public MechanicGateway forMechanic() {
        return new MechanicGatewayImpl();
    }

    public InvoiceGateway forInvoice() {
        return new InvoiceGatewayImpl();
    }

    public WorkOrderGateway forWorkOrder() {
        return new WorkOrderGatewayImpl();
    }

//    public ClientGateway forClient() {
//        return new ClientGatewayImpl();
//    }
//
//    public VehicleGateway forVehicle() {
//        return new VehicleGatewayImpl();
//    }
//
//    public InterventionGateway forIntervention() {
//        return new InterventionGatewayImpl();
//    }
}
