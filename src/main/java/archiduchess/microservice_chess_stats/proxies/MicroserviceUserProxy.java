package archiduchess.microservice_chess_stats.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import archiduchess.microservice_chess_stats.beans.UserBean;


@FeignClient(name="microservice-user", url="${URL_USERS}")
public interface MicroserviceUserProxy {

	@GetMapping(path="archiduchess/users")
	public List<UserBean> getAllUsers() ;
	
}
