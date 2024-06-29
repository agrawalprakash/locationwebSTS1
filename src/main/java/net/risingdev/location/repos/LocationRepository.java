package net.risingdev.location.repos;

import org.springframework.data.repository.CrudRepository;

import net.risingdev.location.entities.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>{

}
