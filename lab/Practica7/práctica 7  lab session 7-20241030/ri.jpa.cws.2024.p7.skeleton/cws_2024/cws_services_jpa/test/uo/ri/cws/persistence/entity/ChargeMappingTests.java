package uo.ri.cws.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.persistence.util.UnitOfWork;

class ChargeMappingTests {

	private UnitOfWork unitOfWork;
	private EntityManagerFactory factory;
	private Client client;
	private Voucher voucher;
	private Invoice invoice;
	private Charge charge;

	@BeforeEach
	void setUp() {
		factory = Persistence.createEntityManagerFactory("carworkshop");
		unitOfWork = UnitOfWork.over( factory );

		client = new Client("nif", "nombre", "apellidos");
		Address address = new Address("street", "city", "zipcode");
		client.setAddress(address);

		voucher = new Voucher("voucher-code", "voucher-description", 100);
		Associations.Hold.link(voucher, client);

		invoice = new Invoice( 1001L );
		charge = new Charge( invoice, voucher, invoice.getAmount() );

		unitOfWork.persist(client, voucher, invoice, charge);
	}

	@AfterEach
	void tearDown() {
		unitOfWork.remove( client, voucher, invoice, charge );
		factory.close();
	}

	/**
	 * All fields of charge are persisted properly
	 */
	@Test
	void testAllFieldsPersisted() {
		Charge restored = unitOfWork.findById( Charge.class, charge.getId());

		assertEquals( charge.getId(), restored.getId() );
		assertEquals( charge.getInvoice(), restored.getInvoice() );
		assertEquals( charge.getPaymentMean(), restored.getPaymentMean() );
		assertEquals( charge.getAmount(), restored.getAmount(), 0.001 );
	}

	/**
	 * There cannot be two charges for the same invoice and payment mean
	 */
	@Test
	void testRepeated() {
		Charge repeated = new Charge(
				charge.getInvoice(),
				charge.getPaymentMean(),
				0.0
			);

		assertThrows(PersistenceException.class, () -> {
			unitOfWork.persist( repeated );
		});
	}

}