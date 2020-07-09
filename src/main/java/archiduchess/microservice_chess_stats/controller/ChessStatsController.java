package archiduchess.microservice_chess_stats.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import archiduchess.microservice_chess_stats.beans.LeaderBean;
import archiduchess.microservice_chess_stats.beans.OnlineGameBean;
import archiduchess.microservice_chess_stats.modele.NextMoveByFen;
import archiduchess.microservice_chess_stats.proxies.MicroserviceLeaderProxy;
import archiduchess.microservice_chess_stats.proxies.MicroserviceOnlineGameProxy;
import archiduchess.microservice_chess_stats.repositories.NextMoveByFenRepository;

@Controller
public class ChessStatsController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MicroserviceLeaderProxy mLeaderProxy;
	
	@Autowired
	MicroserviceOnlineGameProxy mOnlineGameProxy;
	
	@Autowired
	NextMoveByFenRepository nextMoveByFenRepo;
	
	
	@Scheduled(fixedRate = 3600000)
	public void PutStatsInDB() {
		
		List<LeaderBean> leaders = mLeaderProxy.getAllLeaders(); 
		
		for (LeaderBean leader : leaders) {
			List<OnlineGameBean> leaderGames = 
					mOnlineGameProxy.getGamesByUser(leader.getUsername());
			
			for (OnlineGameBean game : leaderGames) {
				List<String> leaderGameFens = 
						mOnlineGameProxy.getFensById(game.getId());
						
				for (String fen : leaderGameFens) {
					NextMoveByFen move = new NextMoveByFen() ; 
					move.setFen(fen); 
					move.setSan(mOnlineGameProxy
							.getNextMoveByFenAndId(fen, game.getId()));
					
					nextMoveByFenRepo.save(move);
					
				}
				
				
			}
		}
		
		// ---> Créer l'api Rest
	}
	
}
