package archiduchess.microservice_chess_stats.repositories;

import org.springframework.data.repository.CrudRepository;

import archiduchess.microservice_chess_stats.modele.NextMoveByFen;

public interface NextMoveByFenRepository 
			extends CrudRepository<NextMoveByFen, Integer> {
	
	

}
