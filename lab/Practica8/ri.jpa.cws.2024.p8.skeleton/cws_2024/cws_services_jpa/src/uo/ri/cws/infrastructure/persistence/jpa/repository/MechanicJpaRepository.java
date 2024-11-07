package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository
	 extends BaseJpaRepository<Mechanic>
		implements MechanicRepository {

	@Override
	public Optional<Mechanic> findById(String id) {
		EntityManager em = Jpa.getManager();
		return Optional.ofNullable(em.find(Mechanic.class, id));
	}

	@Override
	public Optional<Mechanic> findByNif(String nif) {
		return Jpa.getManager().
				createNamedQuery("Mechanic.findByNif",Mechanic.class).
				setParameter(1, nif).getResultStream().
				findFirst();
	}

}
