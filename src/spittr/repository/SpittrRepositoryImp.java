package spittr.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import spittr.domain.Spittle;

@Repository
public class SpittrRepositoryImp implements SpittrRepository {

	@Override
	public List<Spittle> findSpittles(int number) {
		Spittle one = new Spittle();
		one.setId(100L);
		one.setMessage("one message");
		one.setTime(new Date());
		one.setLatitude(3.9);
		Spittle two = new Spittle();
		two.setId(200L);
		two.setMessage("two message");
		two.setTime(new Date());
		two.setLatitude(7.9);
		List<Spittle> spittles = new ArrayList<>();
		spittles.add(one);
		spittles.add(two);
		return spittles;
	}
}