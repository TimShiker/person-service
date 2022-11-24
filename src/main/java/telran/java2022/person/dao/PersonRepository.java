package telran.java2022.person.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import telran.java2022.person.model.ICityPopulation;
import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	Stream<Person> findByNameIgnoreCase(String name);
	
	@Query(value = "SELECT * FROM PERSONS WHERE DATEDIFF(hour,BIRTH_DATE,NOW())/8766 > :minAge and DATEDIFF(hour,BIRTH_DATE,NOW())/8766 < :maxAge", nativeQuery = true)
	Stream<Person> findByAge(@Param("minAge") Integer minAge, @Param("maxAge")Integer maxAge);
	
	Stream<Person> findByAddress_CityIgnoreCase(String city);
	
	@Query(value = "SELECT p.city AS city, COUNT(p.name) AS population "
			  + "FROM PERSONS AS p GROUP BY  p.city", nativeQuery = true)
	Stream<ICityPopulation> countCityPopulation();
}
