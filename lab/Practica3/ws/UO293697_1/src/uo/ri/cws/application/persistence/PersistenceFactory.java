package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.impl.InterventionGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.impl.OrderGatewayImpl;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.impl.OrderLineGatewayImpl;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.impl.ProviderGatewayImpl;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.impl.SparePartGatewayImpl;
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
    public InterventionGateway forIntervention() {
        return new InterventionGatewayImpl();
    }
    
    public OrderGateway forOrder() {
        return new OrderGatewayImpl();
    }
    
    public ProviderGateway forProvider() {
        return new ProviderGatewayImpl();
    }
    
    public OrderLineGateway forOrderLine() {
        return new OrderLineGatewayImpl();
    }

    public SparePartGateway forSpareParts() {
        return new SparePartGatewayImpl();
    }
}
