package uo.ri.cws.application.service;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.orders.create.OrdersServiceImpl;
import uo.ri.cws.application.service.spare.providers.crud.ProvidersCrudServiceImpl;
import uo.ri.cws.application.service.spare.supplies.crud.SuppliesCrudServiceImpl;

public class ServiceFactory {

    public MechanicCrudService forMechanicService() {
        return new MechanicCrudServiceImpl();
    }

    public InvoicingService forInvoicingService() {
        return new InvoicingServiceImpl();
    }

//    public static WorkOrderService forWorkOrderService() {
//        return new WorkOrderServiceImpl();
//    }
//
//    public static VehicleTypeService forVehicleTypeService() {
//        return new VehicleTypeServiceImpl();
//    }
//
//    public static VehicleService forVehicleService() {
//        return new VehicleServiceImpl();
//    }
    
    public OrdersService forOrdersService() {
        return new OrdersServiceImpl();
    }
    
    public ProvidersCrudService forProvidersService() {
        return new ProvidersCrudServiceImpl();
    }

    public SuppliesCrudService forSuppliesCrudService() {
        return new SuppliesCrudServiceImpl();
    }
    

}
