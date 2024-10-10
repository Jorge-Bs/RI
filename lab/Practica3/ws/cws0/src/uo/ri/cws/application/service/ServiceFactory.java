package uo.ri.cws.application.service;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;

public class ServiceFactory {

    public static MechanicCrudService forMechanicService() {
	return new MechanicCrudServiceImpl();
    }

    public static InvoicingService forInvoicingService() {
	return new InvoicingServiceImpl();
    }

//    public static WorkOrderService forWorkOrderService() {
//	return new WorkOrderServiceImpl();
//
//    }
//
//    public static VehicleTypeService forVehicleTypeService() {
//	return new VehicleTypeServiceImpl();
//    }
//
//    public static VehicleService forVehicleService() {
//	return new VehicleServiceImpl();
//    }

}
