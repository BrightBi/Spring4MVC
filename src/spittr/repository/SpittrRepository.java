package spittr.repository;

import java.util.List;

import spittr.domain.Spittle;

public interface SpittrRepository {

	List<Spittle> findSpittles (int number);
}
