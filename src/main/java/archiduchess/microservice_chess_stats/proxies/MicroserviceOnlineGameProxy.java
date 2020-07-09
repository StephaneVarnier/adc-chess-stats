package archiduchess.microservice_chess_stats.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import archiduchess.microservice_chess_stats.beans.OnlineGameBean;

@FeignClient(name="microservice-onlineGame")
public interface MicroserviceOnlineGameProxy {

		@GetMapping(path="/archiduchess/onlineGames/{id}")
		public OnlineGameBean getGameById(@PathVariable String id) ;
		
		@GetMapping(path="/archiduchess/onlineGames/{id}/fens")
		public List<String> getFensById(@PathVariable String id);
		
		@GetMapping(path="/archiduchess/onlineGames/{id}/sans")
		public List<String> getSansById(@PathVariable String id);
		
		@GetMapping(path="/archiduchess/onlineGames/user/{user}/fen/{fen}")
		public List<OnlineGameBean> getGamesByUserAndFen(@PathVariable String user, @PathVariable String fen);
		
		@GetMapping(path="/archiduchessonlineGames/nextMoves/user/{user}/fen/{fen}")
		public List<String> getNextMovesByUserAndFen(@PathVariable String user, @PathVariable String fen);
		
		@GetMapping(path="/archiduchess/onlineGames/user/{user}")
		public List<OnlineGameBean> getGamesByUser(@PathVariable String user) ;

		@GetMapping(path="/archiduchess/onlineGames/nextMove/id/{id}/fen/{fen}")
		public String getNextMoveByFenAndId(@PathVariable String id, @PathVariable String fen);
		
}
