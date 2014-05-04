package me.broswen.creepertracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CreeperTracker extends JavaPlugin implements Listener{
	public static CreeperTracker plugin;
	String logMessage;
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		CreeperTracker.plugin = this;
	}
	
	@Override
	public void onDisable(){
		
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		if(event.getItem() == null){
			return;
		}
		
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		
		if(event.getItem().getType() != Material.MONSTER_EGG){
			return;
		}
		
		if(event.getItem().getDurability() != 50){
			return;
		}
		
		logMessage = "[" + format.format(now) + "] " + player.getName() + " | " + block.getLocation().getX() + " | " + block.getLocation().getY() + " | " + block.getLocation().getZ();
		logToFile(logMessage, "logs");
		
	}
	
	public void logToFile(String message, String fileName){
		
		try{
			File dataFolder = plugin.getDataFolder();
			if(!dataFolder.exists()){
				dataFolder.mkdir();
			}
			
			File saveTo = new File(plugin.getDataFolder(), fileName);
			if(!saveTo.exists()){
				saveTo.createNewFile();
			}
			
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(message);
			pw.flush();
			pw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
