package manuel.fun.arma3;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import manuel.fun.arma3.model.SourceRepo;
import manuel.fun.arma3.model.WorkshopResponse;




@Service
@ShellComponent
public class ModManagerService {
	@Autowired
	SourceRepo sourceRepo;
	@Value("folder.mods")
	String modsfolder;
	@Value("folder.keys")
	String keysfolder;
	@Value("steam.api.key")
	String apikey;
	RestTemplate restTemplate;
	ObjectMapper mapper;

	public ModManagerService() {
		restTemplate=new RestTemplate();
		 mapper=new ObjectMapper();

		//https://api.steampowered.com/ISteamRemoteStorage/GetPublishedFileDetails/v1/?key=2D38124D105E22E19C11CE93C1CED527&itemcount=1&publishedfileids[0]=12324
	}
	public WorkshopResponse getobjectinfo(Long id) {
		
		//WorkshopResponse response=restTemplate.postForObject("https://api.steampowered.com/ISteamRemoteStorage/GetPublishedFileDetails/v1/?key="+apikey+"&itemcount=1&publishedfileids[0]="+id.toString(),new HttpEntity<>(null),WorkshopResponse.class);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("key", apikey);
		map.add("itemcount","1");
		map.add("publishedfileids[0]", id.toString());
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map);
		ResponseEntity<WorkshopResponse> response_web=restTemplate.exchange("https://api.steampowered.com/ISteamRemoteStorage/GetPublishedFileDetails/v1/",HttpMethod.POST,WorkshopResponse.class);
		
		return response_web.getBody();
		
	}
	@ShellMethod("Object info")
	public void shellgetinfo(Long id) {
		System.out.println(getobjectinfo(id).toString());
	}

}
