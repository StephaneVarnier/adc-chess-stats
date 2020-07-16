package archiduchess.microservice_chess_stats.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import archiduchess.microservice_chess_stats.beans.UserBean;


@FeignClient(name="microservice-user", url="localhost:9998")
public interface MicroserviceUserProxy {

	@GetMapping(path="archiduchess/users")
	public List<UserBean> getAllUsers() ;
	
}
