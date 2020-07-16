package archiduchess.microservice_chess_stats.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import archiduchess.microservice_chess_stats.beans.LeaderBean;
import archiduchess.microservice_chess_stats.beans.OnlineGameBean;
import archiduchess.microservice_chess_stats.beans.UserBean;
import archiduchess.microservice_chess_stats.modele.Color;
import archiduchess.microservice_chess_stats.modele.MoveStat;
import archiduchess.microservice_chess_stats.proxies.MicroserviceLeaderProxy;
import archiduchess.microservice_chess_stats.proxies.MicroserviceOnlineGameProxy;
import archiduchess.microservice_chess_stats.proxies.MicroserviceUserProxy;
import archiduchess.microservice_chess_stats.repositories.MoveStatRepository;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@Controller
@RequestMapping(path = "/archiduchess")
public class MoveStatController {

	@Autowired
	private MicroserviceOnlineGameProxy gameProxy;

	@Autowired
	private MicroserviceLeaderProxy leaderProxy;
	
	@Autowired
	private MicroserviceUserProxy userProxy;

	@Autowired
	private MoveStatRepository moveStatRepo;

	@ApiOperation(value = "Enregistre toutes les stats en base.")
	@RequestMapping("/moveStats")
	public void createMoveStats() {

		

		List<LeaderBean> leaders = leaderProxy.getAllLeaders();
		List<UserBean> users = userProxy.getAllUsers();
		 
		

		for (LeaderBean leader : leaders) {
			moveStatsByPlayer(leader.getUsername());		
		}
		
		for (UserBean user : users) {
			moveStatsByPlayer(user.getUsername());		
		}

	}

	private void moveStatsByPlayer(String username) {
		
		MoveStat moveStat = new MoveStat();
		moveStat.setUser(username);
		
		moveStat.setColor(Color.White);
		List<OnlineGameBean> games = gameProxy.getWhiteGamesByUser(username);
		getAndSaveMoves(games, moveStat); 
		
		moveStat.setColor(Color.Black);
		games = gameProxy.getBlackGamesByUser(username);
		getAndSaveMoves(games, moveStat); 
	}
	
	
	private void getAndSaveMoves(List<OnlineGameBean> games, MoveStat moveStat) {
		for (OnlineGameBean game : games) {

			moveStat.setGameId(game.getId());

			double score = evalScore(game.getResultat(), moveStat.getColor());
			moveStat.setScore(score);

			List<String> fens = gameProxy.getFensById(game.getId());

			for (String fen : fens) {
				moveStat.setFen(fen);
				moveStat.setNextMove(gameProxy.getNextMoveByFenAndId(game.getId(), fen));
				
				moveStatRepo.save(moveStat);
			}
		}
	}

	
	private double evalScore(String resultat, Color color) {
		double score = 0;
		if (resultat.equals("1/2-1/2"))
			score = 0.5;
		if (color == Color.White && resultat.equals("1-0"))
			score = 1;
		if (color == Color.Black && resultat.equals("0-1"))
			score = 1;
		return score;

	}

}