package archiduchess.microservice_chess_stats.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import archiduchess.microservice_chess_stats.modele.MoveStat;


public interface MoveStatRepository extends MongoRepository<MoveStat,  String> {
	
	
}
