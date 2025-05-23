package uo.ri.cws.application.service.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.util.sql.AddInvoiceSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindInvoiceByIdSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindInvoiceByNumberSqlUnitOfWork;

public class InvoiceUtil {

	private InvoiceDto dto = createDefaultInvoiceRecord();

	public InvoiceDto get() {
		return dto;
	}
	
	private LocalDate randomDate() {
		LocalDate dateBefore = LocalDate.parse("2020-01-01");
		LocalDate dateAfter = LocalDate.now();
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		LocalDate randomDate = dateBefore.plusDays(
			        ThreadLocalRandom.current().nextLong(noOfDaysBetween +1));
		return randomDate;
	}

	private InvoiceDto createDefaultInvoiceRecord() {
		InvoiceDto res = new InvoiceDto();

		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.date = randomDate();
		res.state = "NOT_YET_PAID";
		res.amount = new Random().nextDouble();
		res.vat = 21.0;
		return res;
	}
	
	public InvoiceUtil withState(String state) {
		dto.state =  state;
		return this;
	}
	
	public InvoiceUtil withAmount(double amount) {
		dto.amount = amount * (1 + dto.vat/100);
		return this;
	}
	
	public InvoiceUtil withDate(String date) {
		dto.date = LocalDate.parse(date);
		return this;
	}

	public InvoiceUtil withVat(double arg) {
		dto.vat = arg;
		return this;
	}

	public InvoiceUtil withNumber(long arg) {
		dto.number = arg;
		return this;
	}

	public InvoiceUtil register() {
		new AddInvoiceSqlUnitOfWork(dto).execute();
		return this;
	}
	
	public InvoiceUtil loadById(String id) {
		dto = new FindInvoiceByIdSqlUnitOfWork(id).execute().get();
		return this;
	}

	public InvoiceUtil loadByNumber(String number) {
		dto = new FindInvoiceByNumberSqlUnitOfWork(number).execute().get();
		return this;
	}
}
