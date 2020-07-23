package archiduchess.microservice_chess_stats.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import archiduchess.microservice_chess_stats.beans.LeaderBean;
import archiduchess.microservice_chess_stats.beans.OnlineGameBean;
import archiduchess.microservice_chess_stats.beans.UserBean;
import archiduchess.microservice_chess_stats.modele.Color;
import archiduchess.microservice_chess_stats.modele.FenStat;
import archiduchess.microservice_chess_stats.modele.MoveStat;
import archiduchess.microservice_chess_stats.proxies.MicroserviceLeaderProxy;
import archiduchess.microservice_chess_stats.proxies.MicroserviceOnlineGameProxy;
import archiduchess.microservice_chess_stats.proxies.MicroserviceUserProxy;
import archiduchess.microservice_chess_stats.repositories.MoveStatRepository;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
@Controller
@RequestMapping(path = "/archiduchess")
public class MoveStatController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MicroserviceOnlineGameProxy gameProxy;

	@Autowired
	private MicroserviceLeaderProxy leaderProxy;
	
	@Autowired
	private MicroserviceUserProxy userProxy;

	@Autowired
	private MoveStatRepository moveStatRepo;
	

	@ApiOperation(value = "Recherche les stats pour un joueur et une partie donnés")
	@GetMapping("/stats/user/{user}/gameId/{gameId}")
	public @ResponseBody List<FenStat> getStatsByUserAndGameId(@PathVariable String user, @PathVariable String gameId) 
	{
		List<FenStat> fenStats = new ArrayList<>(); 
		List<MoveStat> userGameIdStats = moveStatRepo.findByGameIdAndUser(gameId, user);
		
		for (MoveStat stat : userGameIdStats) {
			String fen = stat.getFen();
			
			List<MoveStat> userFenStats = moveStatRepo.findByUserAndFen(user, fen);
			int playedGames = userFenStats.size();
			
			double victories = userFenStats
					.stream()
					.filter(e -> e.getScore() == 1)
					.collect(Collectors.toList())
					.size();
			
			
			double draws = userFenStats
					.stream()
					.filter(e -> e.getScore() == 0.5)
					.collect(Collectors.toList())
					.size();
			
			double efficiency = (victories+draws/2) / playedGames;
			fenStats.add(new FenStat(user, fen, playedGames, efficiency));
		}
		return fenStats;
	}
	
	
	@ApiOperation(value = "Enregistre toutes les stats en base.")
	@RequestMapping("/moveStats")
	@Scheduled(cron="* /10 * * * *") //every hour 
	public @ResponseBody String createMoveStats() {

		List<LeaderBean> leaders = leaderProxy.getAllLeaders();
		List<UserBean> users = userProxy.getAllUsers();
		
		for (LeaderBean leader : leaders) {
			log.info("starting with "+leader.getUsername()+"....");
			moveStatsByPlayer(leader.getUsername());
			log.info(leader.getUsername()+" done");
		}
		
		for (UserBean user : users) {
			log.info("starting with "+user.getUsername()+"....");
			moveStatsByPlayer(user.getUsername());	
			log.info(user.getUsername()+" done");
		}
		return ("moves data stored in database");
	}

	private void moveStatsByPlayer(String username) {
		
		List<OnlineGameBean> games = gameProxy.getWhiteGamesByUser(username);
		getAndSaveMoves(games, username, Color.White); 
		
				
		games = gameProxy.getBlackGamesByUser(username);
		getAndSaveMoves(games, username, Color.Black); 
	}
	
	
	private void getAndSaveMoves(List<OnlineGameBean> games, String username, Color color) {
		List<MoveStat> moveStats = new ArrayList<>();
		
		for (OnlineGameBean game : games) {
			if (moveStatRepo.findByGameIdAndColor(game.getId(), color).size() > 0) break;
			
			double score = evalScore(game.getResultat(), color);
			
			List<String> fens = gameProxy.getFensById(game.getId());
			List<String> sans = gameProxy.getSansById(game.getId());

			for (int i=0;i<fens.size();i++) {
				
				MoveStat moveStat = new MoveStat();
				moveStat.setGameId(game.getId());
				moveStat.setColor(color);
				moveStat.setUser(username);
				moveStat.setScore(score);
				
				try{
					moveStat.setFen(fens.get(i));
				}
				catch (Exception e) {}
				
				try{
					moveStat.setNextMove(sans.get(i));
				}
				catch (Exception IndexOutOfBoundsException) {}
				
				moveStats.add(moveStat);
				
			}
		}
		
		moveStatRepo.saveAll(moveStats);
		log.info(""+moveStats.size() + " " + color+" games saved");
		
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