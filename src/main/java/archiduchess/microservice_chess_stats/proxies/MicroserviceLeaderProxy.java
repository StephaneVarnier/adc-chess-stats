package archiduchess.microservice_chess_stats.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import archiduchess.microservice_chess_stats.beans.LeaderBean;

@FeignClient(name="microservice-leaderboard", url="${URL_LEADERBOARD}")
public interface MicroserviceLeaderProxy {

		@GetMapping(path="/archiduchess/leaders")
		public List<LeaderBean> getAllLeaders();
}
