package manuel.fun.arma3;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import javax.persistence.Entity;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.hibernate.internal.util.config.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import manuel.fun.arma3.model.Publishedfiledetail;
import manuel.fun.arma3.model.SourceEntry;
import manuel.fun.arma3.model.SourceRepo;
import manuel.fun.arma3.model.TaskStatus;
import manuel.fun.arma3.model.UpdateTask;
import manuel.fun.arma3.model.WorkshopResponse;

@Service
@ShellComponent
@Log
public class ModManagerService {
	@Autowired
	SourceRepo sourceRepo;
	@Value("${folder.mods}")
	String modsfolder;
	@Value("${folder.keys}")
	String keysfolder;
	@Value("${steam.api.key}")
	String apikey;
	RestTemplate restTemplate;
	ObjectMapper mapper;
	@Value("${steam.cmd.app}")
	String steamcmdapp;
	@Value("${folder.steam.cmd}")
	String foldersteamcmd;
	@Value("${steam.cmd.user}")
	String user;
	@Value("${steam.cmd.password}")
	String password;
	UpdateTask currenttask;
	ArrayList<UpdateTask> upgradelist = null;

	ExecutorService thread_pool;

	public ModManagerService(@Value("${steam.cmd.app}") String steamcmdapp,
			@Value("${folder.steam.cmd}") String foldersteamcmd, @Value("${steam.cmd.user}") String user,
			@Value("${steam.cmd.password}") String password) throws IOException, InterruptedException {

		restTemplate = new RestTemplate();
		mapper = new ObjectMapper();
		thread_pool = Executors.newCachedThreadPool();

		File steamcmd = new File(steamcmdapp);
		if (!steamcmd.exists()) {
			throw new FileNotFoundException("Steamcmd not found");
		} else {
			log.info("Login check");
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command(steamcmdapp, "+login " + user + " " + password, "+basedir " + foldersteamcmd,
					"+quit");
			Process steam_process = processBuilder.start();

			Steamrenderer renderer = new Steamrenderer(steam_process.getInputStream(), System.out, true);
			thread_pool.submit(renderer);
			thread_pool.submit(new Steamrenderer(steam_process.getErrorStream(), System.err, false));
			steam_process.waitFor();
			String steamlog = renderer.toString();
			if (steamlog.contains("Logged in OK")) {
				log.info("Credentials OK");
			} else if (steamlog.contains("Login Failure")) {
				log.log(Level.SEVERE, "LOGIN FAILED");
				throw new ConfigurationException("Wrong Steam Credentials");
			}

		}
		// https://api.steampowered.com/ISteamRemoteStorage/GetPublishedFileDetails/v1/?key=2D38124D105E22E19C11CE93C1CED527&itemcount=1&publishedfileids[0]=12324
	}

	private WorkshopResponse getobjectinfo(Long id) {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("key", apikey);
		map.add("itemcount", "1");
		map.add("publishedfileids[0]", id.toString());
		URI uri;
		try {
			uri = new URI("https://api.steampowered.com/ISteamRemoteStorage/GetPublishedFileDetails/v1/");
			RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)
					.accept(MediaType.APPLICATION_JSON).body(map);

			ResponseEntity<WorkshopResponse> response_web = restTemplate.exchange(request, WorkshopResponse.class);

			return response_web.getBody();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@ShellMethod("Retrives info about object from the workshop")
	public void getinfo(Long id) {
		WorkshopResponse response = getobjectinfo(id);
		if (response != null) {
			org.jline.utils.Log
					.info("\n#####################################################################################");
			for (Publishedfiledetail a : response.getResponse().getPublishedfiledetails()) {
				org.jline.utils.Log.info(a.toString());
			}
		} else {
			org.jline.utils.Log.info("No object with that ID");
		}
	}

	@ShellMethod("Returns all registerd sources")
	public void getsources() {
		org.jline.utils.Log.info(sourceRepo.count() + " sources registerd");

		for (SourceEntry entry : sourceRepo.findAll()) {
			org.jline.utils.Log.info(entry.toString());
		}
	}

	@ShellMethod("Adds a source")
	public void addsource(Long id, String name) {
		org.jline.utils.Log.debug("Retreaving source info");
		WorkshopResponse mod = getobjectinfo(id);
		if (mod.getResponse().getPublishedfiledetails().get(0).getConsumerAppId() == 107410) {
			SourceEntry toadd = SourceEntry.builder().workshopid(id).modname(name).keyfilename("non")
					.updatetime((long) 0).build();
			sourceRepo.save(toadd);
			log.info("Source added");

		} else {
			org.jline.utils.Log.error("That object is not an ARMA 3 Mod");
		}
	}

	@ShellMethod("Removes a source")
	public void removesource(Long id) {
		Optional<SourceEntry> entry = sourceRepo.findById(id);
		if (entry.isPresent()) {
			sourceRepo.delete(entry.get());
			log.info(entry.get().toString() + " removed");
		}

	}

	private UpdateTask updateitem(UpdateTask task) {
		log.info("Updating source");
		try {
			currenttask = task;
			SourceEntry entry = task.getSource();
			task.setStatus(TaskStatus.running);
			deletekey(entry);
			download(entry.getWorkshopid());
			File entryfolder = new File(foldersteamcmd + "steamapps" + File.separator + "workshop" + File.separator
					+ "content" + File.separator + "107410" + File.separator + entry.getWorkshopid() + File.separator);
			log.info("Fixing paths");
			fix_path(entryfolder);
			log.info("Adding key");
			addkey(entry);
			log.info("Linking Mod");
			Path link = new File(modsfolder + entry.getModname() + File.separator).toPath();
			if (Files.exists(link)) {
				Files.delete(link);
			}
			Files.createSymbolicLink(link, entryfolder.getAbsoluteFile().toPath());
			log.info("Updating DB");
			entry.setUpdatetime(getobjectinfo(entry.getWorkshopid()).getResponse().getPublishedfiledetails().get(0)
					.getTimeUpdated());
			sourceRepo.save(entry);
			log.info("Analising Result");
			if (task.getExceptions().size() == 0) {
				task.setStatus(TaskStatus.done);
				log.info("Done");
			} else {
				log.warning("Done with error");
				task.setStatus(TaskStatus.done_with_error);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			task.getExceptions().add(e);
			task.setStatus(TaskStatus.error);
			e.printStackTrace();

		}
		return task;

	}

	private void fix_path(File mod) {

		Arrays.stream(mod.listFiles()).forEach(file -> {
			try {
				log.fine("Renaming file " + file.getName());
				file.renameTo(new File(file.getCanonicalFile().toString().toLowerCase()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Arrays.stream(mod.listFiles()).filter(file -> file.isDirectory()).forEach(file -> {
			fix_path(file);
		});

	}

	private void deletekey(SourceEntry entry) {
		if (!entry.getKeyfilename().equals("non")) {
			log.info("Deleting key " + entry.getKeyfilename());
			File keyfolder = new File(keysfolder);
			File key = null;
			ArrayList<File> todelete = new ArrayList<>();
			for (File file : keyfolder.listFiles()) {
				if (file.getName().equals(entry.getKeyfilename())) {
					key = file;
					break;
				}
			}
			if (key != null) {
				key.delete();
			} else {
				log.warning("No keyfile to delete");
				currenttask.getExceptions().add(new FileNotFoundException("No keyfile to delete"));
			}
		}
	}

	private void addkey(SourceEntry entry) throws IOException {
		File entryfolder = new File(foldersteamcmd + "steamapps" + File.separator + "workshop" + File.separator
				+ "content" + File.separator + "107410" + File.separator + entry.getWorkshopid());
		if (entryfolder.exists() && entryfolder.isDirectory()) {
			File keyfile = findkey(entryfolder);
			if (keyfile != null) {
				entry.setKeyfilename(keyfile.getName());
				log.info("Keyfile found: " + keyfile.getName());
				Files.copy(keyfile.toPath(), new File(keysfolder + keyfile.getName()).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				log.info("Key copyed");
			} else {
				log.severe("NO KEY FOUND");
				throw new FileNotFoundException("No key file found");
			}
		} else {
			log.severe("MOD NOT FOUND");
			throw new FileNotFoundException("Mod not found");
		}
	}

	private File findkey(File folder) {
		File keyfile = null;
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				if (FilenameUtils.getExtension(file.getName()).equals("bikey")) {
					keyfile = file;
					break;
				}
			} else {
				keyfile = findkey(file);
				if (keyfile != null)
					break;
			}
		}
		return keyfile;
	}

	private void download(Long id) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		log.info("Downloading Object");
		processBuilder.command(steamcmdapp, "+login " + user + " " + password,
				"+workshop_download_item " + 107410 + " " + id, "+basedir " + foldersteamcmd, "+api_logging enabled",
				"+quit");

		Process steam_process = processBuilder.start();
		Steamrenderer sr = new Steamrenderer(steam_process.getInputStream(), System.out, true);
		thread_pool.submit(sr);
		thread_pool.submit(new Steamrenderer(steam_process.getErrorStream(), System.err, false));
		steam_process.waitFor();
		if (sr.toString().contains("Success. Downloaded")) {
			log.info("Download done");
		} else {
			log.warning("Download failed");
			throw new IOException("DOWNLOAD FAILED");
		}

	}

	@ShellMethod("Force steam to download an item")
	public void force_download(Long id, String modname) throws IOException {

		log.info("Forcing download of mod");
		SourceEntry forcedentry = SourceEntry.builder().modname(modname).workshopid(id).build();
		UpdateTask task = new UpdateTask(forcedentry);
		updateitem(task);
		log.info("Force done");

	}

	@ShellMethod("Searches for updates")
	public void search() {
		ArrayList<UpdateTask> toupdate = new ArrayList<>();
		log.info("Searching for updates");
		Iterable<SourceEntry> sourcelist = sourceRepo.findAll();
		for (SourceEntry sourceEntry : sourcelist) {
			Publishedfiledetail respons = getobjectinfo(sourceEntry.getWorkshopid()).getResponse()
					.getPublishedfiledetails().get(0);
			Long updatetime = respons.getTimeUpdated();
			if (updatetime > sourceEntry.getUpdatetime()) {
				log.info(
						"Update available for (" + sourceEntry.getWorkshopid() + "->" + sourceEntry.getModname() + ")");
				UpdateTask updateTask = new UpdateTask(sourceEntry);
				toupdate.add(updateTask);
			}
		}
		if (toupdate.size() > 0) {
			log.info("Updates ready to intall.\nType update to start.");
			upgradelist = toupdate;
		} else {
			log.info("No update available");
		}

	}

	@ShellMethod("Starts update")
	public void update() {
		
		if (upgradelist != null) {
			log.info("Staring update");
			for (UpdateTask updateTask : upgradelist) {
				log.info("\n\n\n");
				log.info("#######################################################");
				log.info("# Updating "+updateTask.getSource().getWorkshopid() + "->" + updateTask.getSource().getModname());
				log.info("#######################################################");
				updateitem(updateTask);
				log.info("#######################################################");
				if(updateTask.getStatus().equals(TaskStatus.done)) {
					log.info("Mod installed");
				}else if(updateTask.getStatus().equals(TaskStatus.done_with_error)) {
					log.warning("Mod installed but with errors");
					for (Exception exeption : updateTask.getExceptions()) {
						log.warning(exeption.getMessage());
					}
				}else if(updateTask.getStatus().equals(TaskStatus.error)) {
					log.severe("Mod installation failed");
					for (Exception exeption : updateTask.getExceptions()) {
						log.severe(exeption.getMessage());
					}
				}
				log.info("#######################################################");
				log.info("\n\n\n");
				log.info("=======================================================");
			}
			log.info("Update done");
		} else {
			log.info("No update list found.\nType 'search' to search for updates.");
		}
		
	}
	

}
