package archiduchess.microservice_chess_stats.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import archiduchess.microservice_chess_stats.modele.Color;
import archiduchess.microservice_chess_stats.modele.MoveStat;


public interface MoveStatRepository extends MongoRepository<MoveStat,  String> {

	List<MoveStat> findByGameId(String gameId);
	
	List<MoveStat> findByGameIdAndColor(String gameId, Color color);

	List<MoveStat> findByGameIdAndUser(String gameId, String user);

	List<MoveStat> findByUserAndFen(String user, String fen);
	
	
}
