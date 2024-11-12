package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class SparePartJpaRepository
		extends BaseJpaRepository<SparePart>
		implements SparePartRepository {

	@Override
	public Optional<SparePart> findByCode(String code) {
		return Jpa.getManager()
				.createNamedQuery("SparePart.findByCode", SparePart.class)
				.setParameter(1, code)
				.getResultStream()
				.findFirst();
	}

	@Override
	public List<SparePart> findUnderStockNotPending() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SparePart> findByDescription(String description) {
		// TODO Auto-generated method stub
		return null;
	}


}
