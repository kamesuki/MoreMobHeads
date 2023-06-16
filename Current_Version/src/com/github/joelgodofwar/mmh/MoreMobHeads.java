package com.github.joelgodofwar.mmh;
//1.14

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.block.TileState;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.joelgodofwar.mmh.handlers.EventHandler_1_16_R2;
import com.github.joelgodofwar.mmh.handlers.EventHandler_1_17_R1;
import com.github.joelgodofwar.mmh.handlers.EventHandler_1_19_R1;
import com.github.joelgodofwar.mmh.util.Ansi;
import com.github.joelgodofwar.mmh.util.CatHeads;
import com.github.joelgodofwar.mmh.util.HorseHeads;
import com.github.joelgodofwar.mmh.util.LlamaHeads;
import com.github.joelgodofwar.mmh.util.Metrics;
import com.github.joelgodofwar.mmh.util.MobHeads;
import com.github.joelgodofwar.mmh.util.MobHeads117;
import com.github.joelgodofwar.mmh.util.RabbitHeads;
import com.github.joelgodofwar.mmh.util.SheepHeads;
import com.github.joelgodofwar.mmh.util.StrUtils;
import com.github.joelgodofwar.mmh.util.UpdateChecker;
import com.github.joelgodofwar.mmh.util.VillagerHeads;
import com.github.joelgodofwar.mmh.util.YmlConfiguration;
import com.github.joelgodofwar.mmh.util.ZombieVillagerHeads;
import com.github.joelgodofwar.mmh.util.datatypes.JsonDataType;
import com.github.joelgodofwar.mmh.util.mob.NameTag;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import io.papermc.lib.PaperLib;
import io.papermc.lib.features.blockstatesnapshot.BlockStateSnapshotResult;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class MoreMobHeads extends JavaPlugin implements Listener{
	
	public final static Logger logger = Logger.getLogger("Minecraft");
	/** update checker variables */
	public String updateurl = "https://github.com/JoelGodOfwar/MoreMobHeads/raw/master/versioncheck/{vers}/version.txt";
	public String newVerMsg;
	public int updateVersion = 73997; // https://spigotmc.org/resources/73997
	boolean UpdateAvailable =  false;
	public String UColdVers;
	public String UCnewVers;
	public static boolean UpdateCheck;
	public String thisName = this.getName();
	public String thisVersion = this.getDescription().getVersion();
	/** end update checker variables */
	public static boolean debug = false;
	public static String daLang;
	//String updateURL = "https://github.com/JoelGodOfwar/MoreMobHeads/raw/master/versioncheck/1.14/version.txt";
	File langFile;
	public FileConfiguration lang;
	File langNameFile;
	public FileConfiguration langName;
	public File playerFile;
	public FileConfiguration playerHeads;
	File blockFile;
	File blockFile116;
	File blockFile1162;
	File blockFile117;
	File blockFile119;
	public FileConfiguration blockHeads  = new YamlConfiguration();
	public FileConfiguration blockHeads2  = new YamlConfiguration();
	public FileConfiguration blockHeads3  = new YamlConfiguration();
	public FileConfiguration blockHeads4  = new YamlConfiguration();
	public File customFile;
	public FileConfiguration traderCustom;
	File chanceFile;
	public YmlConfiguration chanceConfig;
	public YmlConfiguration oldchanceConfig;
	File mobnameFile;
	FileConfiguration mobname;
	double defpercent = 0.013;
	//static boolean showkiller;
	//static boolean showpluginname;
	public YmlConfiguration config = new YmlConfiguration();
	YamlConfiguration oldconfig = new YamlConfiguration();
	static PluginDescriptionFile pdfFile;
	static String datafolder;
	public String world_whitelist;
	public String world_blacklist;
	public String mob_whitelist;
	public String mob_blacklist;
	boolean colorful_console;
	boolean silent_console;
	public final NamespacedKey NAMETAG_KEY = new NamespacedKey(this, "name_tag");
	public final NamespacedKey SHIVERING_KEY = new NamespacedKey(this, "shivering_tag");
	File debugFile;
	Random random = new Random();
	
	@Override // TODO: onEnable
	public void onEnable(){
		UpdateCheck = getConfig().getBoolean("auto_update_check");
		//showkiller = getConfig().getBoolean("lore.show_killer", true);
		//showpluginname = getConfig().getBoolean("lore.show_plugin_name", true);
		debug = getConfig().getBoolean("debug", false);
		daLang = getConfig().getString("lang", "en_US");
		oldconfig = new YamlConfiguration();
		pdfFile = this.getDescription();
		datafolder = this.getDataFolder().toString();
		colorful_console = getConfig().getBoolean("console.colorful_console", true);
		silent_console = getConfig().getBoolean("console.silent_console", false);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		consoleLog(Ansi.GREEN + "**************************************" + Ansi.RESET);
		consoleLog(Ansi.YELLOW + pdfFile.getName() + " v" + pdfFile.getVersion() + Ansi.RESET + " Loading...");
		
		debugFile = new File(this.getDataFolder() + File.separator + "logs" + File.separator  + "mmh_debug.log");
		if(!debugFile.exists()){
            saveResource("logs" + File.separatorChar + "mmh_debug.log", true);
        }
		
		/** DEV check **/
		File jarfile = this.getFile().getAbsoluteFile();
		if(jarfile.toString().contains("-DEV")){
			debug = true;
			logDebug("Jar file contains -DEV, debug set to true");
			//log("jarfile contains dev, debug set to true.");
		}
		if(debug){logDebug("datafolder=" + getDataFolder());}
		langFile = new File(getDataFolder() + "" + File.separatorChar + "lang" + File.separatorChar, daLang + ".yml");//\
		if(debug){logDebug("langFilePath=" + langFile.getPath());}
		if(!langFile.exists()){																	// checks if the yaml does not exist
			langFile.getParentFile().mkdirs();									// creates the /plugins/<pluginName>/ directory if not found
			saveResource("lang" + File.separatorChar + "cs_CZ.yml", true);
			saveResource("lang" + File.separatorChar + "de_DE.yml", true);
			saveResource("lang" + File.separatorChar + "en_US.yml", true);
			saveResource("lang" + File.separatorChar + "es_MX.yml", true);
			saveResource("lang" + File.separatorChar + "fr_FR.yml", true);
			saveResource("lang" + File.separatorChar + "nl_NL.yml", true);
			saveResource("lang" + File.separatorChar + "pt_BR.yml", true);
			saveResource("lang" + File.separatorChar + "zh_CN.yml", true);
			log(Level.INFO, "lang file not found! copied cs_CZ.yml, de_DE.yml, en_US.yml, es_MX.yml, fr_FR.yml, nl_NL.yml, pt_BR.yml, and zh_CN.yml to " + getDataFolder() + "" + File.separatorChar + "lang");
			//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
		}
	consoleLog("Loading language file...");
		lang = new YamlConfiguration();
		try {
			lang.load(langFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		String checklangversion = lang.getString("langversion", "1.0.0");
		if(checklangversion != null){
			if(!checklangversion.equalsIgnoreCase("1.0.20")){
				saveResource("lang" + File.separatorChar + "cs_CZ.yml", true);
				saveResource("lang" + File.separatorChar + "de_DE.yml", true);
				saveResource("lang" + File.separatorChar + "en_US.yml", true);
				saveResource("lang" + File.separatorChar + "es_MX.yml", true);
				saveResource("lang" + File.separatorChar + "fr_FR.yml", true);
				saveResource("lang" + File.separatorChar + "nl_NL.yml", true);
				saveResource("lang" + File.separatorChar + "pt_BR.yml", true);
				saveResource("lang" + File.separatorChar + "zh_CN.yml", true);
				saveResource("lang" + File.separatorChar + "cs_CZ_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "de_DE_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "en_US_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "es_MX_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "fr_FR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "nl_NL_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "pt_BR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "zh_CN_mobnames.yml", true);
				log(Level.INFO, "Updating lang files! copied cs_CZ.yml, de_DE.yml, en_US.yml, es_MX.yml, fr_FR.yml, nl_NL.yml, pt_BR.yml, and zh_CN.yml to "
						+ getDataFolder() + "" + File.separatorChar + "lang");
			}
		}else{
			saveResource("lang" + File.separatorChar + "cs_CZ.yml", true);
			saveResource("lang" + File.separatorChar + "de_DE.yml", true);
			saveResource("lang" + File.separatorChar + "en_US.yml", true);
			saveResource("lang" + File.separatorChar + "es_MX.yml", true);
			saveResource("lang" + File.separatorChar + "fr_FR.yml", true);
			saveResource("lang" + File.separatorChar + "nl_NL.yml", true);
			saveResource("lang" + File.separatorChar + "pt_BR.yml", true);
			saveResource("lang" + File.separatorChar + "zh_CN.yml", true);
			log(Level.INFO, "Updating lang files! copied cs_CZ.yml, de_DE.yml, en_US.yml, es_MX.yml, fr_FR.yml, nl_NL.yml, pt_BR.yml, and zh_CN.yml to "
					+ getDataFolder() + "" + File.separatorChar + "lang");
		}
		
		
		
		/** Version Check */
		if( !(Double.parseDouble( getMCVersion().substring(0, 4) ) >= 1.14) ){
			// !getMCVersion().startsWith("1.14")&&!getMCVersion().startsWith("1.15")&&!getMCVersion().startsWith("1.16")&&!getMCVersion().startsWith("1.17")
			logger.info(Ansi.RED + "WARNING! *!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!" + Ansi.RESET);
			logger.info(Ansi.RED + "WARNING! " + lang.get("server_not_version") + Ansi.RESET);
			logger.info(Ansi.RED + "WARNING! " + this.getName() + " v" + this.getDescription().getVersion() + " disabling." + Ansi.RESET);
			logger.info(Ansi.RED + "WARNING! *!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!" + Ansi.RESET);
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		/**	Check for config */
		try{
			if(!getDataFolder().exists()){
				log(Level.INFO, "Data Folder doesn't exist");
				log(Level.INFO, "Creating Data Folder");
				getDataFolder().mkdirs();
				log(Level.INFO, "Data Folder Created at " + getDataFolder());
			}
			File	file = new File(getDataFolder(), "config.yml");
			if(debug){logDebug("" + file);}
			if(!file.exists()){
				log(Level.INFO, "config.yml not found, creating!");
				saveResource("config.yml", true);
				saveResource("chance_config.yml", true);
			}
		}catch(Exception e){
			stacktraceInfo();
			e.printStackTrace();
		}
	consoleLog("Loading config file...");
		try {
			oldconfig.load(new File(getDataFolder() + "" + File.separatorChar + "config.yml"));
		} catch (Exception e2) {
			logWarn("Could not load config.yml");
			stacktraceInfo();
			e2.printStackTrace();
		}
		String checkconfigversion = oldconfig.getString("version", "1.0.0");
		if(checkconfigversion != null){
			if(!checkconfigversion.equalsIgnoreCase("1.0.20")){
				boolean isOldFormat;
				try {
					copyFile_Java7(getDataFolder() + "" + File.separatorChar + "config.yml",getDataFolder() + "" + File.separatorChar + "old_config.yml");
				} catch (IOException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
				if(config.get("colorful_console", null) != null){
					isOldFormat = true;
				}else{
					isOldFormat = false;
				}
				saveResource("config.yml", true);
				
				try {
					config.load(new File(getDataFolder(), "config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("Could not load config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
				try {
					oldconfig.load(new File(getDataFolder(), "old_config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
				if(isOldFormat){
					config.set("auto_update_check", oldconfig.get("auto_update_check", true));
					config.set("debug", oldconfig.get("debug", false));
					config.set("lang", oldconfig.get("lang", "en_US"));
					config.set("console.colorful_console", oldconfig.get("colorful_console", true));//
					config.set("console.silent_console", oldconfig.get("silent_console", false));
					config.set("vanilla_heads.creepers", oldconfig.get("creeper_vanilla_heads", false));
					config.set("vanilla_heads.ender_dragon", oldconfig.get("ender_dragon_vanilla_heads", false));
					config.set("vanilla_heads.skeleton", oldconfig.get("skeleton_vanilla_heads", false));
					config.set("vanilla_heads.wither_skeleton", oldconfig.get("wither_skeleton_vanilla_heads", false));
					config.set("vanilla_heads.zombie", oldconfig.get("zombie_vanilla_heads", false));
					config.set("world.whitelist", oldconfig.get("world.whitelist", ""));
					config.set("world.blacklist", oldconfig.get("world.blacklist", ""));
					config.set("mob.whitelist", oldconfig.get("mob.whitelist", ""));
					config.set("mob.blacklist", oldconfig.get("mob.blacklist", ""));
					config.set("mob.nametag", oldconfig.get("mob.nametag", false));
					config.set("lore.show_killer", oldconfig.get("show_killer", true));
					config.set("lore.show_plugin_name", oldconfig.get("show_plugin_name", true));
					config.set("wandering_trades.custom_wandering_trader", oldconfig.get("custom_wandering_trader", true));
					config.set("wandering_trades.player_heads.enabled", oldconfig.get("wandering_trades.player_heads.enabled", true));
					config.set("wandering_trades.player_heads.min", oldconfig.get("wandering_trader_min_player_heads", 0));
					config.set("wandering_trades.player_heads.max", oldconfig.get("wandering_trader_max_player_heads", 5));
					config.set("wandering_trades.block_heads.enabled", oldconfig.get("wandering_trades.block_heads.enabled", true));
					config.set("wandering_trades.block_heads.pre_116.min", oldconfig.get("wandering_trader_min_block_heads", 0));
					config.set("wandering_trades.block_heads.pre_116.max", oldconfig.get("wandering_trader_max_block_heads", 5));
					config.set("wandering_trades.block_heads.is_116.min", oldconfig.get("wandering_trader_min_block_heads", 0));
					config.set("wandering_trades.block_heads.is_116.max", oldconfig.get("wandering_trader_max_block_heads", 5));
					config.set("wandering_trades.block_heads.is_117.min", oldconfig.get("wandering_trader_min_block_heads", 0));
					config.set("wandering_trades.block_heads.is_117.max", oldconfig.get("wandering_trader_max_block_heads", 5));
					
					config.set("wandering_trades.custom_trades.enabled", oldconfig.get("wandering_trader_custom_trades_enabled", false));
					config.set("wandering_trades.custom_trades.min", oldconfig.get("wandering_trader_min_custom_trades", 0));
					config.set("wandering_trades.custom_trades.max", oldconfig.get("wandering_trader_max_custom_trades", 5));
					config.set("apply_looting", oldconfig.get("apply_looting", true));
					config.set("whitelist.enforce", oldconfig.get("enforce_whitelist", true));
					config.set("whitelist.player_head_whitelist", oldconfig.get("player_head_whitelist", "names_go_here"));
					config.set("blacklist.enforce", oldconfig.get("enforce_blacklist", true));
					config.set("blacklist.player_head_blacklist", oldconfig.get("player_head_blacklist", "names_go_here"));
					config.set("event.piston_extend", oldconfig.get("piston_extend", true));
				}else{
					config.set("auto_update_check", oldconfig.get("auto_update_check", true));
					config.set("debug", oldconfig.get("debug", false));
					config.set("lang", oldconfig.get("lang", "en_US"));
					config.set("console.colorful_console", oldconfig.get("console.colorful_console", true));
					config.set("console.silent_console", oldconfig.get("console.silent_console", false));
					config.set("vanilla_heads.creepers", oldconfig.get("vanilla_heads.creepers", false));
					config.set("vanilla_heads.ender_dragon", oldconfig.get("vanilla_heads.ender_dragon", false));
					config.set("vanilla_heads.skeleton", oldconfig.get("vanilla_heads.skeleton", false));
					config.set("vanilla_heads.wither_skeleton", oldconfig.get("vanilla_heads.wither_skeleton", false));
					config.set("vanilla_heads.zombie", oldconfig.get("vanilla_heads.zombie", false));
					config.set("world.whitelist", oldconfig.get("world.whitelist", ""));
					config.set("world.blacklist", oldconfig.get("world.blacklist", ""));
					config.set("mob.whitelist", oldconfig.get("mob.whitelist", ""));
					config.set("mob.blacklist", oldconfig.get("mob.blacklist", ""));
					config.set("mob.nametag", oldconfig.get("mob.nametag", false));
					config.set("lore.show_killer", oldconfig.get("lore.show_killer", true));
					config.set("lore.show_plugin_name", oldconfig.get("lore.show_plugin_name", true));
					config.set("wandering_trades.custom_wandering_trader", oldconfig.get("wandering_trades.custom_wandering_trader", true));
					config.set("wandering_trades.player_heads.enabled", oldconfig.get("wandering_trades.player_heads.enabled", true));
					config.set("wandering_trades.player_heads.min", oldconfig.get("wandering_trades.player_heads.min", 0));
					config.set("wandering_trades.player_heads.max", oldconfig.get("wandering_trades.player_heads.max", 5));
					config.set("wandering_trades.block_heads.enabled", oldconfig.get("wandering_trades.block_heads.enabled", true));
					config.set("wandering_trades.block_heads.pre_116.min", oldconfig.get("wandering_trader_min_block_heads", 0));
					config.set("wandering_trades.block_heads.pre_116.max", oldconfig.get("wandering_trader_max_block_heads", 5));
					config.set("wandering_trades.block_heads.is_116.min", oldconfig.get("wandering_trader_min_block_heads", 0));
					config.set("wandering_trades.block_heads.is_116.max", oldconfig.get("wandering_trader_max_block_heads", 5));
					config.set("wandering_trades.block_heads.is_117.min", oldconfig.get("wandering_trader_min_block_heads", 0));
					config.set("wandering_trades.block_heads.is_117.max", oldconfig.get("wandering_trader_max_block_heads", 5));
					
					config.set("wandering_trades.custom_trades.enabled", oldconfig.get("wandering_trades.custom_trades.enabled", false));
					config.set("wandering_trades.custom_trades.min", oldconfig.get("wandering_trades.custom_trades.min", 0));
					config.set("wandering_trades.custom_trades.max", oldconfig.get("wandering_trades.custom_trades.max", 5));
					config.set("apply_looting", oldconfig.get("apply_looting", true));
					config.set("whitelist.enforce", oldconfig.get("whitelist.enforce", true));
					config.set("whitelist.player_head_whitelist", oldconfig.get("whitelist.player_head_whitelist", "names_go_here"));
					config.set("blacklist.enforce", oldconfig.get("enforce_blacklist", true));
					config.set("blacklist.player_head_blacklist", oldconfig.get("blacklist.player_head_blacklist", "names_go_here"));
					config.set("event.piston_extend", oldconfig.get("piston_extend", true));
				}
				//config.set("", oldconfig.get("", true));
				
				try {
					config.save(new File(getDataFolder(), "config.yml"));
				} catch (IOException e) {
					logWarn("Could not save old settings to config.yml");
					stacktraceInfo();
					e.printStackTrace();
				}
				saveResource("chance_config.yml", true);
				log(Level.INFO, "config.yml Updated! old config saved as old_config.yml");
				log(Level.INFO, "chance_config.yml saved.");
			}else{
				try {
					config.load(new File(getDataFolder(), "config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("Could not load config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			oldconfig = null;
		}
		/** end config check */
		
		if(getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
			/** Trader heads load */
			playerFile = new File(getDataFolder() + "" + File.separatorChar + "player_heads.yml");//\
			if(debug){logDebug("player_heads=" + playerFile.getPath());}
			if(!playerFile.exists()){																	// checks if the yaml does not exist
				saveResource("player_heads.yml", true);
				log(Level.INFO, "player_heads.yml not found! copied player_heads.yml to " + getDataFolder() + "");
				//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
			}
		consoleLog("Loading player_heads file...");
			playerHeads = new YamlConfiguration();
			try {
				playerHeads.load(playerFile);
			} catch (IOException | InvalidConfigurationException e) {
				stacktraceInfo();
				e.printStackTrace();
			}
			
			
			/** Custom Trades load */
			customFile = new File(getDataFolder() + "" + File.separatorChar + "custom_trades.yml");//\
			if(debug){logDebug("customFile=" + customFile.getPath());}
			if(!customFile.exists()){																	// checks if the yaml does not exist
				saveResource("custom_trades.yml", true);
				log(Level.INFO, "custom_trades.yml not found! copied custom_trades.yml to " + getDataFolder() + "");
				//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
			}
		consoleLog("Loading custom_trades file...");
			traderCustom = new YamlConfiguration();
			try {
				traderCustom.load(customFile);
			} catch (IOException | InvalidConfigurationException e) {
				stacktraceInfo();
				e.printStackTrace();
			}
		}
		
		/** chanceConfig load */
		chanceFile = new File(getDataFolder() + "" + File.separatorChar + "chance_config.yml");//\
		if(debug){logDebug("chanceFile=" + chanceFile.getPath());}
		if(!chanceFile.exists()){																	// checks if the yaml does not exist
			saveResource("chance_config.yml", true);
			log(Level.INFO, "chance_config.yml not found! copied chance_config.yml to " + getDataFolder() + "");
			//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
		}
	consoleLog("Loading chance_config file...");
		chanceConfig = new YmlConfiguration();
		oldchanceConfig = new YmlConfiguration();
		try {
			chanceConfig.load(chanceFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		/** chanceConfig update check */
		String checkchanceConfigversion = chanceConfig.getString("version", "1.0.0");
		if(checkchanceConfigversion != null){
			if(!checkchanceConfigversion.equalsIgnoreCase("1.0.20")){
				try {
					copyFile_Java7(getDataFolder() + "" + File.separatorChar + "chance_config.yml",getDataFolder() + "" + File.separatorChar + "old_chance_config.yml");
				} catch (IOException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
				
				saveResource("chance_config.yml", true);
				copyChance(getDataFolder() + "" + File.separatorChar + "old_chance_config.yml", chanceFile.getPath());
				log(Level.INFO, "chance_config.yml updated.");
			}
		}
		
		
		/** Mob names translation */
		langNameFile = new File(getDataFolder() + "" + File.separatorChar + "lang" + File.separatorChar, daLang + "_mobnames.yml");//\
		if(debug){logDebug("langFilePath=" + langNameFile.getPath());}
		if(!langNameFile.exists()){																	// checks if the yaml does not exist
			saveResource("lang" + File.separatorChar + "cs_CZ_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "de_DE_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "en_US_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "es_MX_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "fr_FR_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "nl_NL_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "pt_BR_mobnames.yml", true);
			saveResource("lang" + File.separatorChar + "zh_CN_mobnames.yml", true);
			log(Level.INFO, "lang_mobnames file not found! copied cs_CZ_mobnames.yml, de_DE_mobnames.yml, en_US_mobnames.yml, es_MX_mobnames.yml, fr_FR_mobnames.yml, nl_NL_mobnames.yml, pt_BR_mobnames.yml, and zh_CN_mobnames.yml to " + getDataFolder() + "" + File.separatorChar + "lang");
			//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
		}
	consoleLog("Loading language based mobnames file...");
		langName = new YamlConfiguration();
		try {
			langName.load(langNameFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		/** Mob Names update check */
		String checklangnameConfigversion = langName.getString("allay", "outdated");
		if(checklangnameConfigversion != null){
			if(checklangnameConfigversion.equalsIgnoreCase("outdated")){
				log(Level.INFO, "lang_mobnames file outdated! Updating.");
				saveResource("lang" + File.separatorChar + "cs_CZ_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "de_DE_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "en_US_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "es_MX_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "fr_FR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "nl_NL_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "pt_BR_mobnames.yml", true);
				saveResource("lang" + File.separatorChar + "zh_CN_mobnames.yml", true);
				log(Level.INFO, "cs_CZ_mobnames.yml, de_DE_mobnames.yml, en_US_mobnames.yml, es_MX_mobnames.yml, fr_FR_mobnames.yml, nl_NL_mobnames.yml, pt_BR_mobnames.yml, and zh_CN_mobnames.yml updated.");
				try {
					langName.load(langNameFile);
				} catch (IOException | InvalidConfigurationException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
			}
		}
		/** end Mob names translation */
		
		world_whitelist = config.getString("world.whitelist", "");
		world_blacklist = config.getString("world.blacklist", "");
		mob_whitelist = config.getString("mob.whitelist", "");
		mob_blacklist = config.getString("mob.blacklist", "");
		
		getServer().getPluginManager().registerEvents(this, this);
		
		String jarfilename = this.getFile().getAbsoluteFile().toString();
		consoleLog(Ansi.RED + " (  " + ChatColor.YELLOW + "-<[ PLEASE INCLUDE THIS WITH ANY ISSUE REPORTS ]>-" + Ansi.RESET);
		consoleLog(Ansi.RED + "  ) " + ChatColor.WHITE + "This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")" + Ansi.RESET);
		consoleLog(Ansi.RED + " (  " + ChatColor.WHITE + "vardebug=" + debug + " debug=" + getConfig().get("debug","error") + " in " + this.getDataFolder() + File.separatorChar + "config.yml" + Ansi.RESET);
		consoleLog(Ansi.RED + "  ) " + ChatColor.WHITE + "jarfilename=" + StrUtils.Right(jarfilename, jarfilename.length() - jarfilename.lastIndexOf(File.separatorChar)) + Ansi.RESET);
		consoleLog(Ansi.RED + " (  " + ChatColor.YELLOW + "-<[ PLEASE INCLUDE THIS WITH ANY ISSUE REPORTS ]>-" + Ansi.RESET);
		
		//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "version");
		if(getConfig().getBoolean("debug")==true&&!(jarfile.toString().contains("-DEV"))){
			logDebug("Config.yml DUMP - INCLUDE THIS WITH ANY ISSUE REPORT VVV");
			logDebug("auto_update_check=" + getConfig().getBoolean("auto_update_check"));
			logDebug("debug=" + getConfig().getBoolean("debug"));
			logDebug("lang=" + getConfig().getString("lang"));
			logDebug("vanilla_heads.creeper=" + getConfig().getBoolean("vanilla_heads.creeper"));
			logDebug("vanilla_heads.ender_dragon=" + getConfig().getBoolean("vanilla_heads.ender_dragon"));
			logDebug("vanilla_heads.skeleton=" + getConfig().getBoolean("vanilla_heads.skeleton"));
			logDebug("vanilla_heads.wither_skeleton=" + getConfig().getBoolean("vanilla_heads.wither_skeleton"));
			logDebug("vanilla_heads.zombie=" + getConfig().getBoolean("vanilla_heads.zombie"));
			logDebug("world.whitelist=" + getConfig().getString("world.whitelist"));
			logDebug("world.blacklist=" + getConfig().getString("world.blacklist"));
			logDebug("mob.whitelist=" + getConfig().getString("mob.whitelist"));
			logDebug("mob.blacklist=" + getConfig().getString("mob.blacklist"));
			logDebug("lore.show_killer=" + getConfig().getBoolean("lore.show_killer"));
			logDebug("lore.show_plugin_name=" + getConfig().getBoolean("lore.show_plugin_name"));
			logDebug("wandering_trades.custom_wandering_trader=" + getConfig().getBoolean("wandering_trades.custom_wandering_trader"));
			logDebug("wandering_trades.keep_default_trades=" + getConfig().getBoolean("wandering_trades.keep_default_trades"));
			logDebug("wandering_trades.player_heads.enabled=" + getConfig().getBoolean("wandering_trades.player_heads.enabled"));
			logDebug("wandering_trades.player_heads.min=" + getConfig().getString("wandering_trades.player_heads.min"));
			logDebug("wandering_trades.player_heads.max=" + getConfig().getString("wandering_trades.player_heads.max"));
			logDebug("wandering_trades.block_heads.enabled=" + getConfig().getBoolean("wandering_trades.block_heads.enabled"));
			logDebug("wandering_trades.block_heads.min=" + getConfig().getString("wandering_trades.block_heads.min"));
			logDebug("wandering_trades.block_heads.max=" + getConfig().getString("wandering_trades.block_heads.max"));
			logDebug("wandering_trades.custom_trades.enabled=" + getConfig().getBoolean("wandering_trades.custom_trades.enabled"));
			logDebug("wandering_trades.custom_trades.min=" + getConfig().getString("wandering_trades.custom_trades.min"));
			logDebug("wandering_trades.custom_trades.max=" + getConfig().getString("wandering_trades.custom_trades.max"));
			logDebug("apply_looting=" + getConfig().getBoolean("apply_looting"));
			logDebug("whitelist.enforce=" + getConfig().getBoolean("whitelist.enforce"));
			logDebug("whitelist.player_head_whitelist=" + getConfig().getString("whitelist.player_head_whitelist"));
			logDebug("blacklist.enforce=" + getConfig().getBoolean("blacklist.enforce"));
			logDebug("blacklist.player_head_blacklist=" + getConfig().getString("blacklist.player_head_blacklist"));
			logDebug("event.piston_extend=" + getConfig().getString("event.piston_extend"));
			logDebug("Config.yml DUMP - INCLUDE THIS WITH ANY ISSUE REPORT ^^^");
		}
		
		/** Register EventHandler */ // TODO: Register Events
		String packageName = this.getServer().getClass().getPackage().getName();
    	String version = packageName.substring(packageName.lastIndexOf('.') + 2);
    	if(debug)logDebug("version=" + version);
    	if( version.contains("1_16_R") || version.contains("1_15_R1") || version.contains("1_14_R1") ){
    		getServer().getPluginManager().registerEvents( new EventHandler_1_16_R2(this), this);
    		getCommand("mmh").setExecutor(new EventHandler_1_16_R2(this));
		}else if( version.contains("1_17_R1") || version.contains("1_18_R1")  || version.contains("1_18_R2")){
			getServer().getPluginManager().registerEvents( new EventHandler_1_17_R1(this), this);
			//getCommand("mmh").setExecutor(new EventHandler_1_17_R1(this));
			
		}else if( version.contains("1_19_R") ){
			getServer().getPluginManager().registerEvents( new EventHandler_1_19_R1(this), this);
			//getCommand("mmh").setExecutor(new EventHandler_1_17_R1(this));
			
		}else{
			logWarn("Not compatible with this version of Minecraft:" + version);
			getServer().getPluginManager().disablePlugin(this);
		}
		
    	/** update checker variable */
		newVerMsg = Ansi.YELLOW + this.getName() + Ansi.MAGENTA + " v{oVer}" + Ansi.RESET + " " + lang.get("newvers") + Ansi.GREEN + " v{nVer}" + Ansi.RESET;
    	/** Update Checker */
		if(UpdateCheck){
			consoleLog("Checking for updates...");
			try {
				UpdateChecker updater = new UpdateChecker(this, updateVersion, updateurl);
				if(updater.checkForUpdates()) {
					UpdateAvailable = true; // TODO: Update Checker
					UColdVers = updater.oldVersion();
					UCnewVers = updater.newVersion();
					
					log(Level.WARNING, "*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*");
					log(Level.WARNING, "* " + lang.get("newvers.message").toString().replace("<MyPlugin>", this.getName()) );
					log(Level.WARNING, "* " + lang.get("newvers.old_vers") + ChatColor.RED + UColdVers );
					log(Level.WARNING, "* " + lang.get("newvers.new_vers") + ChatColor.GREEN + UCnewVers );
					log(Level.WARNING, "*");
					log(Level.WARNING, "* " + lang.get("newvers.please_update") );
					log(Level.WARNING, "*");
					log(Level.WARNING, "* " + lang.get("newvers.download") + ": https://www.spigotmc.org/resources/moremobheads.73997/history");
					log(Level.WARNING, "* " + lang.get("newvers.donate") + ": https://ko-fi.com/joelgodofwar");
					log(Level.WARNING, "*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*!*");
					
					//Bukkit.getConsoleSender().sendMessage(newVerMsg.replace("{oVer}", UColdVers).replace("{nVer}", UCnewVers));
					//Bukkit.getConsoleSender().sendMessage(Ansi.GREEN + UpdateChecker.getResourceUrl() + Ansi.RESET);
				}else{
					UpdateAvailable = false;
				}
			}catch(Exception e) {
				consoleLog(ChatColor.RED + "Could not process update check - " + e.getCause());
				e.printStackTrace();
			}
		}
		/** end update checker */
    	
/** Experimental code 	
		for (World world : Bukkit.getServer().getWorlds()){
			world.setDifficulty(Difficulty.HARD);
		}
		//player.setPlayerListName(ChatColor.GREEN + player.getDisplayName());
 Experimental code */
		
		consoleInfo("Enabled");
		
		Metrics metrics	= new Metrics(this, 6128);
		// New chart here
		// myPlugins()
		metrics.addCustomChart(new Metrics.AdvancedPie("my_other_plugins", new Callable<Map<String, Integer>>() {
					@Override
					public Map<String, Integer> call() throws Exception {
							Map<String, Integer> valueMap = new HashMap<>();
							
							if(getServer().getPluginManager().getPlugin("DragonDropElytra") != null){valueMap.put("DragonDropElytra", 1);}
				    		if(getServer().getPluginManager().getPlugin("NoEndermanGrief") != null){valueMap.put("NoEndermanGrief", 1);}
				    		if(getServer().getPluginManager().getPlugin("PortalHelper") != null){valueMap.put("PortalHelper", 1);}
				    		if(getServer().getPluginManager().getPlugin("ShulkerRespawner") != null){valueMap.put("ShulkerRespawner", 1);}
				    		//if(getServer().getPluginManager().getPlugin("MoreMobHeads") != null){valueMap.put("MoreMobHeads", 1);}
				    		if(getServer().getPluginManager().getPlugin("SilenceMobs") != null){valueMap.put("SilenceMobs", 1);}
				    		if(getServer().getPluginManager().getPlugin("SinglePlayerSleep") != null){valueMap.put("SinglePlayerSleep", 1);}
							if(getServer().getPluginManager().getPlugin("VillagerWorkstationHighlights") != null){valueMap.put("VillagerWorkstationHighlights", 1);}
							if(getServer().getPluginManager().getPlugin("RotationalWrench") != null){valueMap.put("RotationalWrench", 1);}
							return valueMap;
					}
			}));
		metrics.addCustomChart(new Metrics.AdvancedPie("vanilla_heads", new Callable<Map<String, Integer>>() {
			@Override
			public Map<String, Integer> call() throws Exception {
					Map<String, Integer> valueMap = new HashMap<>();
					//int varTotal = myPlugins();
					valueMap.put("CREEPER " + getConfig().getString("vanilla_heads.creeper").toUpperCase(), 1);
					valueMap.put("ENDER_DRAGON " + getConfig().getString("vanilla_heads.ender_dragon").toUpperCase(), 1);
					valueMap.put("SKELETON " + getConfig().getString("vanilla_heads.skeleton").toUpperCase(), 1);
					valueMap.put("WITHER_SKELETON " + getConfig().getString("vanilla_heads.wither_skeleton").toUpperCase(), 1);
					valueMap.put("ZOMBIE " + getConfig().getString("vanilla_heads.zombie").toUpperCase(), 1);
					return valueMap;
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("auto_update_check", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("auto_update_check").toUpperCase();
					}
			}));
		// add to site
		metrics.addCustomChart(new Metrics.SimplePie("var_debug", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("debug").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("var_lang", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("lang").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("whitelist.enforce", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("whitelist.enforce").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("blacklist.enforce", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("blacklist.enforce").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("custom_wandering_trader", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("wandering_trades.custom_wandering_trader").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("player_heads", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("wandering_trades.player_heads.enabled").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("block_heads", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("wandering_trades.block_heads.enabled").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("custom_trades", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("wandering_trades.custom_trades.enabled").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("apply_looting", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("apply_looting").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("show_killer", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("lore.show_killer").toUpperCase();
					}
			}));
		metrics.addCustomChart(new Metrics.SimplePie("show_plugin_name", new Callable<String>() {
					@Override
					public String call() throws Exception {
							return "" + getConfig().getString("lore.show_plugin_name").toUpperCase();
					}
			}));
	}

	@Override // TODO: onDisable
	public void onDisable(){
		
		consoleInfo("Disabled");
	}
	
	public void consoleInfo(String state) {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(ChatColor.GREEN + "**************************************" + ChatColor.RESET);
		logger.info(ChatColor.YELLOW + pdfFile.getName() + " v" + pdfFile.getVersion() + ChatColor.RESET + " is " + state);
		logger.info(ChatColor.GREEN + "**************************************" + ChatColor.RESET);
	}
	
	public void log(String string) {
		log(Level.INFO, string);
	}
	
	public	void log(Level level, String dalog){// TODO: log
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.log(level, ChatColor.YELLOW + pdfFile.getName() + " v" + pdfFile.getVersion() + ChatColor.RESET + " " + dalog );
	}
	
	public	void log(Level level, String dalog, Throwable thrown){// TODO: log
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.log(level, ChatColor.YELLOW + pdfFile.getName() + " v" + pdfFile.getVersion() + ChatColor.RESET + " " + dalog );
	}
	
	public	void logDebug(String dalog){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); //"dd/MM HH:mm:ss"
        String date = dateFormat.format(new Date());
        String message = "[" + date + "] [v" + this.getDescription().getVersion() + "] [DEBUG]: " + ChatColor.stripColor(dalog);
        try {
        	FileWriter writer = new FileWriter(debugFile.toString(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(message + "\n");
            bw.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        log(Level.INFO, Ansi.RED + "[DEBUG] " + Ansi.RESET + dalog);
		//log(Ansi.RED + "[DEBUG] " + Ansi.RESET + dalog);
	}
	public void logWarn(String dalog){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
        String date = dateFormat.format(new Date());
        String message = "[" + date + "] [WARN]: " + dalog;
        logger.warning(message + "\n");
        try {
        	FileWriter writer = new FileWriter(debugFile, true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(message);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		log(Level.WARNING, Ansi.RED + "[WARN] " + Ansi.RESET  + " " + dalog);
	}
	
	public static String getMCVersion() {
		String strVersion = Bukkit.getVersion();
		strVersion = strVersion.substring(strVersion.indexOf("MC: "), strVersion.length());
		strVersion = strVersion.replace("MC: ", "").replace(")", "");
		return strVersion;
	}
	
	
	public void giveMobHead(LivingEntity mob, String name){
		ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta)helmet.getItemMeta();
			meta.setDisplayName(name + "'s Head");
		meta.setOwner(name); //.setOwner(name);
			helmet.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
		mob.getEquipment().setHelmet(helmet);
		helmet.setItemMeta(meta);
		mob.getEquipment().setHelmet(helmet);
		if(getServer().getPluginManager().getPlugin("WildStacker") != null){
			@Nonnull
			PersistentDataContainer pdc = mob.getPersistentDataContainer();
			pdc.set(NAMETAG_KEY, PersistentDataType.STRING, "nametag");
		}
	}
	
	public void givePlayerHead(Player player, String playerName){
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta)playerHead.getItemMeta();
			meta.setDisplayName(playerName + "'s Head");
		meta.setOwner(playerName); //.setOwner(name);
		ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("lore.show_plugin_name", true)){
			lore.add(ChatColor.AQUA + "" + this.getName());
		}
		meta.setLore(lore);
			playerHead.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
		//player.getEquipment().setHelmet(playerHead);
			
		playerHead.setItemMeta(meta);
		player.getWorld().dropItemNaturally(player.getLocation(), playerHead);
	}
	
	public void giveBlockHead(Player player, String blockName) {
		if(debug) {logDebug("giveBlockHead START");}
		ItemStack blockStack = null;
		int isBlock = isBlockHeadName(blockName);
		int isBlock2 = isBlockHeadName2(blockName);
		int isBlock3 = isBlockHeadName3(blockName);
		int isBlock4 = isBlockHeadName4(blockName);
		if(isBlock != -1){
			if(debug) {logDebug("GBH isBlock=" + isBlock);}
			blockStack = blockHeads.getItemStack("blocks.block_" + isBlock + ".itemstack", new ItemStack(Material.AIR));
		}else if(isBlock2 != -1){
			if(debug) {logDebug("GBH isBlock2=" + isBlock2);}
			blockStack = blockHeads2.getItemStack("blocks.block_" + isBlock2 + ".itemstack", new ItemStack(Material.AIR));
		}else if(isBlock3 != -1){
			if(debug) {logDebug("GBH isBlock3=" + isBlock3);}
			blockStack = blockHeads3.getItemStack("blocks.block_" + isBlock3 + ".itemstack", new ItemStack(Material.AIR));
		}else if(isBlock4 != -1){
			if(debug) {logDebug("GBH isBlock3=" + isBlock3);}
			blockStack = blockHeads4.getItemStack("blocks.block_" + isBlock4 + ".itemstack", new ItemStack(Material.AIR));
		}else {
			player.sendMessage(this.getName() + " v" + this.getDescription().getVersion() + " Sorry could not find \"" + blockName + "\"");
		}
		if( blockStack != null && blockStack.getType() != Material.AIR ) {
			player.getWorld().dropItemNaturally(player.getLocation(), blockStack);
			if(debug) {logDebug("GBH BlockHead given to " + player.getName());}
		}
		if(debug) {logDebug("giveBlockHead END");}
	}
	
	public boolean isInventoryFull(Player p)	{
	    return !(p.getInventory().firstEmpty() == -1);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent event){// TODO: PlayerInteractEntityEvent
		if(!(event.getPlayer() instanceof Player))
			return;
		try{
			Player player = event.getPlayer();
			if(player.hasPermission("moremobheads.nametag")){
				if(debug) {log("moremobheads.nametag=true");}
				if(getConfig().getBoolean("mob.nametag", false)) {
					if(debug) {log("mob.nametag=true");}
					Material material = player.getInventory().getItemInMainHand().getType();
					Material material2 = player.getInventory().getItemInOffHand().getType();
					String name = "";
					if(material.equals(Material.NAME_TAG)){
						name = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
						if(debug){logDebug("PIEE" + player.getDisplayName() + " Main hand name=" + name);};
					}
					if(material2.equals(Material.NAME_TAG)){
						name = player.getInventory().getItemInOffHand().getItemMeta().getDisplayName();
						if(debug){logDebug("PIEE " + player.getDisplayName() + " Off hand name=" + name);};
					}
					
		/** experimental code */
					/*ItemStack itemstack = player.getInventory().getItemInOffHand();
					getConfig().set("itemstack", itemstack);
					saveConfig();*/
			//		log("itemstack set");
	
					//player.getInventory().addItem(getConfig().getItemStack("itemstack"));
					
					/*Villager villager = (Villager) mob;
					List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
					MerchantRecipe recipe = new MerchantRecipe(getConfig().getItemStack("itemstack"), 1);
					recipe.addIngredient(new ItemStack(Material.EMERALD));
									recipes.add(recipe);
									villager.setRecipes(recipes);*/
			/** experimental code */
									
					//player.sendMessage("Testing");
					if(material.equals(Material.NAME_TAG)||material2.equals(Material.NAME_TAG)){
						if(getServer().getPluginManager().getPlugin("SilenceMobs") != null){
							if(name.toLowerCase().contains("silenceme")||name.toLowerCase().contains("silence me")){
								return;
							}
						}
						//player.sendMessage("Testing 2");
						LivingEntity mob = (LivingEntity) event.getRightClicked();
						//log("mob=" + mob.getType().toString());
						if(debug) {log("canwearhead=" + NameTag.canWearHead(mob));}
						if(NameTag.canWearHead(mob)){
							// Piglin, drowned, husk, pillager, stray
							
							//log("mob=" + mob.getType().toString());
							/**if(getConfig().getBoolean("enforce_whitelist", false)){ //
								if(getConfig().getString("whitelist.player_head_whitelist", "").toLowerCase().contains(name.toLowerCase())){
									// on whitelist make the head.
									giveMobHead(mob, name);
								}else{
									player.sendMessage("\"" + name + "\" " + lang.get("not_on_whitelist"));
									event.setCancelled(true);
								}
							}else if(getConfig().getBoolean("blacklist.enforce", false)){
								if(!getConfig().getString("blacklist.player_head_blacklist", "").toLowerCase().contains(name.toLowerCase())){
									// not on blacklist, make the head.
									giveMobHead(mob, name);
								}else{
									player.sendMessage("\"" + name + "\" " + lang.get("on_blacklist"));
									event.setCancelled(true);
								}
							}*/
							boolean enforcewhitelist = getConfig().getBoolean("whitelist.enforce", false);
							boolean enforceblacklist = getConfig().getBoolean("blacklist.enforce", false);
							boolean onwhitelist = getConfig().getString("whitelist.player_head_whitelist", "").toLowerCase().contains(name.toLowerCase());
							boolean onblacklist = getConfig().getString("blacklist.player_head_blacklist", "").toLowerCase().contains(name.toLowerCase());
							if(enforcewhitelist&&enforceblacklist){
								if(onwhitelist&&!(onblacklist)){
									giveMobHead(mob, name);
								}else{
									event.setCancelled(true); // return;
									if(debug){log(Level.INFO, "PIE - Name Error 1");}
								}
							}else if(enforcewhitelist&&!enforceblacklist){
								if(onwhitelist){
									giveMobHead(mob, name);
								}else{
									event.setCancelled(true); // return;
									if(debug){log(Level.INFO, "PIE - Name not on whitelist.");}
								}
							}else if(!enforcewhitelist&&enforceblacklist){
								if(!onblacklist){
									giveMobHead(mob, name);
								}else{
									event.setCancelled(true); // return;
									if(debug){log(Level.INFO, "PIE - Name is on blacklist.");}
								}
							}else{
								giveMobHead(mob, name);
							}
						}
					}
				}else {
					if(debug) {log("mob.nametag=false");}
				}
			}else{
				//ZombieVillager mob = (ZombieVillager) event.getRightClicked();
				if(debug) {log("moremobheads.nametag=false");}
				//player.sendMessage(mob.getName() + " profession= " + mob.getVillagerProfession());
			}
			//ZombieVillager mob = (ZombieVillager) event.getRightClicked();
			
			//player.sendMessage(mob.getName() + " profession= " + mob.getVillagerProfession());
		}catch (Exception e){
			stacktraceInfo();
			e.printStackTrace();
		}

	}
	 
	public ItemStack dropMobHead(Entity entity, String name, Player killer){// TODO: dropMobHead
		ItemStack helmet = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta)helmet.getItemMeta();
		meta.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(name)); //.setOwner(name);
		meta.setDisplayName(name + "'s Head");
		ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("lore.show_killer", true)){
			String killed_by = (String) lang.get("lore_killed_by", "Killed by <player>");
			lore.add(ChatColor.RESET + killed_by.replace("<player>", "" + ChatColor.RESET + ChatColor.YELLOW + killer.getDisplayName()) );
			//lore.add(ChatColor.RESET + "Killed by " + ChatColor.RESET + ChatColor.YELLOW + killer.getDisplayName());
		}
		if(getConfig().getBoolean("lore.show_plugin_name", true)){
			lore.add(ChatColor.AQUA + "" + this.getName());
		}
		meta.setLore(lore);
		meta.setLore(lore);
		helmet.setItemMeta(meta);//																	 e2d4c388-42d5-4a96-b4c9-623df7f5e026
		helmet.setItemMeta(meta);
		entity.getWorld().dropItemNaturally(entity.getLocation(), helmet);
		return helmet;
	}
	 
	public boolean DropIt(EntityDeathEvent event, double chancepercent){// TODO: DropIt
		ItemStack itemstack = event.getEntity().getKiller().getInventory().getItemInMainHand();
		if(itemstack != null){
				if(debug){logDebug("DI itemstack=" + itemstack.getType().toString() + " line:1023");}
			int enchantmentlevel = 0;
			if(getConfig().getBoolean("apply_looting", true)){
				enchantmentlevel = itemstack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
			}
			if(chancepercent == 0){
				enchantmentlevel = 0;
			}
				if(debug){logDebug("DI enchantmentlevel=" + enchantmentlevel + " line:1031");}
			double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
				if(debug){logDebug("DI enchantmentlevelpercent=" + enchantmentlevelpercent + " line:1033");}
			double chance = Math.random();
				if(debug){logDebug("DI chance=" + chance + " line:1035");}
			
				if(debug){logDebug("DI chancepercent=" + chancepercent + " line:1037");}
			chancepercent = chancepercent + enchantmentlevelpercent;
				if(debug){logDebug("DI chancepercent2=" + chancepercent + " line:1039");}
			if (chancepercent > chance){
				return true;
			}
		}
		return false;
	}
	
	public boolean DropIt2( double chancepercent){// TODO: DropIt
			int enchantmentlevel = 0;
			if(chancepercent == 0){
				enchantmentlevel = 0;
			}
				if(debug){logDebug("DI enchantmentlevel=" + enchantmentlevel + " line:1031");}
			double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
				if(debug){logDebug("DI enchantmentlevelpercent=" + enchantmentlevelpercent + " line:1033");}
			double chance = Math.random();
				if(debug){logDebug("DI chance=" + chance + " line:1035");}
			
				if(debug){logDebug("DI chancepercent=" + chancepercent + " line:1037");}
			chancepercent = chancepercent + enchantmentlevelpercent;
				if(debug){logDebug("DI chancepercent2=" + chancepercent + " line:1039");}
			if (chancepercent > chance){
				return true;
			}
		return false;
	}
	
	
	
	public int randomBetween(int min, int max){
		Random r = new Random();
		int random = r.nextInt(max + 1);
		if((min + random) > max){
			return max;
		}
		return min + random;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) // TODO: OnPlayerJoin
		{
			Player p = event.getPlayer();
			//if(p.isOp() && UpdateCheck||p.hasPermission("moremobheads.showUpdateAvailable")){	
			/** Notify Ops */
			if(UpdateAvailable&&(p.isOp()||p.hasPermission("moremobheads.showUpdateAvailable"))){
				String links = "[\"\",{\"text\":\"Download\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/moremobheads.73997/history\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Download the latest version.\"}},{\"text\":\" \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Download the latest version.\"}},{\"text\":\"| \"},{\"text\":\"Donate\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://ko-fi.com/joelgodofwar\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Donate to the plugin maker.\"}},{\"text\":\" | \"},{\"text\":\"ChangeLog\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/moremobheads.73997/updates\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"View the ChangeLog.\"}}]";
				String versions = "" + ChatColor.GRAY + lang.get("newvers.new_vers") + ": " + ChatColor.GREEN + "{nVers} | " + lang.get("newvers.old_vers") + ": " + ChatColor.RED + "{oVers}";
				links = links.replace("Download", "" + lang.get("newvers.download"))
						.replace("Donate", "" + lang.get("newvers.donate"))
						.replace("ChangeLog", "" + lang.get("newvers.changelog"));
				p.sendMessage("" + ChatColor.GRAY + lang.get("newvers.message").toString().replace("<MyPlugin>", ChatColor.GOLD + this.getName() + ChatColor.GRAY) );
				sendJson(p, links);
				p.sendMessage(versions.replace("{nVers}", UCnewVers).replace("{oVers}", UColdVers));
				//p.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " v" + UColdVers + ChatColor.RESET + " " + lang.get("newvers") + ChatColor.GREEN + " v" + UCnewVers + ChatColor.RESET + "\n" + ChatColor.GREEN + UpdateChecker.getResourceUrl() + ChatColor.RESET);
				//Bukkit.getConsoleSender().sendMessage(this.getName() + Ansi.RED + " v" + UColdVers + Ansi.RESET + " " + lang.get("newvers") + Ansi.GREEN + " v" + UCnewVers + Ansi.RESET);
			}

			if(p.getDisplayName().equals("JoelYahwehOfWar")||p.getDisplayName().equals("JoelGodOfWar")){
				p.sendMessage(this.getName() + " " + this.getDescription().getVersion() + " Hello father!");
				//p.sendMessage("seed=" + p.getWorld().getSeed());
			}
	}

	public void sendJson(Player player, String string) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw \"" + player.getName() + 
		        "\" " + string);
	}
	
	public void makeHead(EntityDeathEvent event, Material material){// TODO: makeHead
		 ItemStack itemstack = event.getEntity().getKiller().getInventory().getItemInMainHand();
			if(itemstack != null){
					if(debug){logDebug("itemstack=" + itemstack.getType().toString() + " line:954");}
				int enchantmentlevel = itemstack.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);//.containsEnchantment(Enchantment.LOOT_BONUS_MOBS);
					if(debug){logDebug("enchantmentlevel=" + enchantmentlevel + " line:956");}
				double enchantmentlevelpercent = ((double)enchantmentlevel / 100);
					if(debug){logDebug("enchantmentlevelpercent=" + enchantmentlevelpercent + " line:958");}
				double chance = Math.random();
					if(debug){logDebug("chance=" + chance + " line:960");}
				double chancepercent = 0.25; /** Set to check config.yml later*/
					if(debug){logDebug("chancepercent=" + chancepercent + " line:962");}
				chancepercent = chancepercent + enchantmentlevelpercent;
					if(debug){logDebug("chancepercent2=" + chancepercent + " line:964");}
				if(chancepercent > 0.00 && chancepercent < 0.99){
						if (chancepercent > chance){
							event.getDrops().add(new ItemStack(material, 1));
						}
				}
			}
	}
	
	public ItemStack makeTraderSkull(String textureCode, String headName, String uuid, int amount){// TODO: maketraderSkull
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
		if(textureCode == null) return item;
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
		profile.getProperties().put("textures", new Property("textures", textureCode));
		profile.getProperties().put("SkullOwner", new Property("id", uuid));
		profile.getProperties().put("display", new Property("Name", headName));
		setGameProfile(meta, profile);
		ArrayList<String> lore = new ArrayList();
		if(getConfig().getBoolean("lore.show_plugin_name", true)){
			lore.add(ChatColor.AQUA + "MoreMobHeads");
		}
		meta.setLore(lore);
		meta.setLore(lore);
		
		//meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
		meta.setDisplayName(headName);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack makeSkull(String textureCode, String headName, Player killer){// TODO: makeSkull
			ItemStack item = new ItemStack(Material.PLAYER_HEAD);
			if(textureCode == null) return item;
			SkullMeta meta = (SkullMeta) item.getItemMeta();

			GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
			profile.getProperties().put("textures", new Property("textures", textureCode));
			profile.getProperties().put("display", new Property("Name", headName));
			setGameProfile(meta, profile);
			ArrayList<String> lore = new ArrayList();
			
			if(getConfig().getBoolean("lore.show_killer", true)){
				lore.add(ChatColor.RESET + "Killed by " + ChatColor.RESET + ChatColor.YELLOW + killer.getName());
			}
			if(getConfig().getBoolean("lore.show_plugin_name", true)){
				lore.add(ChatColor.AQUA + "MoreMobHeads");
			}
				meta.setLore(lore);
			meta.setLore(lore);
			
			//meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
			meta.setDisplayName(headName);
			item.setItemMeta(meta);
			return item;
	}
	
	public ItemStack makeSkulls(String textureCode, String headName, int amount){
			ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
			if(textureCode == null) return item;
			SkullMeta meta = (SkullMeta) item.getItemMeta();

			GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(textureCode.getBytes()), textureCode);
			profile.getProperties().put("textures", new Property("textures", textureCode));
			profile.getProperties().put("display", new Property("Name", headName));
			setGameProfile(meta, profile);
			ArrayList<String> lore = new ArrayList();

			if(getConfig().getBoolean("lore.show_plugin_name", true)){
				lore.add(ChatColor.AQUA + "MoreMobHeads");
			}
				meta.setLore(lore);
			meta.setLore(lore);
			
			//meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUUID));
			meta.setDisplayName(headName);
			item.setItemMeta(meta);
			return item;
	}
	private static Field fieldProfileItem;
	public static void setGameProfile(SkullMeta meta, GameProfile profile){
			try{
				if(fieldProfileItem == null) fieldProfileItem = meta.getClass().getDeclaredField("profile");
				fieldProfileItem.setAccessible(true);
				fieldProfileItem.set(meta, profile);
			}
			catch(NoSuchFieldException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
			catch(SecurityException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
			catch(IllegalArgumentException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
			catch(IllegalAccessException e){
				stacktraceInfoStatic();
				e.printStackTrace();}
	}

	
	public boolean isInteger(String s){
	    try{
	        Integer.parseInt(s);
	        return true;
	    }catch (NumberFormatException ex){
	        return false;
	    }
	}
	
	//@SuppressWarnings("unused")
	@EventHandler
	public void OnTake(EntityPickupItemEvent e){
	/** //ItemStack item = new ItemStack(7);
	LivingEntity entity = e.getEntity();
	Item item = e.getItem();
		if(entity instanceof Player){
			//log("" + e.getItem().getItemStack().toString());//e.getItem().getType() instanceof Material.PLAYER_HEAD
			Material headtype = item.getItemStack().getType();
			if (headtype == Material.PLAYER_HEAD&&headtype != Material.CREEPER_HEAD&&headtype != Material.ZOMBIE_HEAD&&headtype != Material.SKELETON_SKULL
					&&headtype != Material.WITHER_SKELETON_SKULL&&headtype != Material.DRAGON_HEAD){//e.getItem().getItemStack().equals(new ItemStack(Material.PLAYER_HEAD))
					if(debug){logDebug("EPIE isPlayerEPIE=true");}
					if(debug){logDebug("EPIE item.length=" +	item.getName().length());}
				SkullMeta skullname = (SkullMeta) item.getItemStack().getItemMeta();
				if(skullname.getOwner() != null){
					String name = skullname.getOwner().toString();
					if(debug){logDebug("EPIE name=" + name);}
					if(debug){logDebug("EPIE lore=" + skullname.getLore());}
					if(skullname.getOwner().toString().length() >= 40){
						if(debug){logDebug("EPIE ownerName.lngth >= 40");}
							ItemStack itmStack = e.getItem().getItemStack();
							SkullMeta meta = (SkullMeta) e.getItem().getItemStack().getItemMeta();
							String daMobName = "null";
							if(meta != null){
								String isCat = CatHeads.getNameFromTexture(meta.getOwner().toString());
								String isHorse = HorseHeads.getNameFromTexture(meta.getOwner().toString());
								String isLlama = LlamaHeads.getNameFromTexture(meta.getOwner().toString());
								String isMobHead = MobHeads.getNameFromTexture(meta.getOwner().toString());
								String isRabbit = RabbitHeads.getNameFromTexture(meta.getOwner().toString());
								String isSheep = SheepHeads.getNameFromTexture(meta.getOwner().toString());
								String isVillager = VillagerHeads.getNameFromTexture(meta.getOwner().toString());
								String isZombieVillager = ZombieVillagerHeads.getNameFromTexture(meta.getOwner().toString());
								String isplayerhead = isPlayerHead(meta.getOwner().toString());
								String isblockhead = isBlockHead(meta.getOwner().toString());
								String isblockhead2 = isBlockHead2(meta.getOwner().toString());
								if(isCat != null){				daMobName = isCat;	}
								if(isHorse != null){			daMobName = isHorse;	}
								if(isLlama != null){			daMobName = isLlama;	}
								if(isMobHead != null){			daMobName = isMobHead;	}
								if(isRabbit != null){			daMobName = isRabbit;	}
								if(isSheep != null){			daMobName = isSheep;	}
								if(isVillager != null){			daMobName = isVillager;	}
								if(isZombieVillager != null){	daMobName = isZombieVillager;	}
								if(daMobName == null){
									if(blockHeads != null){
										if(isblockhead != null){	daMobName = isblockhead;	}
									}
								}
								if(daMobName == null){
									if(blockHeads2 != null){
										if(isblockhead2 != null){	daMobName = isblockhead2;	}
									}
								}
								if(daMobName == null){
									if(playerHeads != null){
										if(isplayerhead != null){	daMobName = isplayerhead;	}
									}
								}
								ArrayList<String> lore = new ArrayList();
								//log("" + meta.getOwner().toString());
								//String name = LlamaHeads.getNameFromTexture(meta.getOwner().toString());
								if(debug){logDebug("EPIE mobname from texture=" + daMobName);}
								List<String> skullLore = skullname.getLore();
								if(skullLore != null){
									if(skullLore.toString().contains("Killed by")){
										lore.addAll(skullname.getLore());
									}
								}
								if(skullLore == null||!skullname.getLore().toString().contains(this.getName())){
									if(getConfig().getBoolean("lore.show_plugin_name", true)){
										lore.add(ChatColor.AQUA + "" + this.getName());
									}
								}
								if(daMobName != "null"){
									daMobName = langName.getString(daMobName.toLowerCase().replace(" ", "."), daMobName);
								}else{
									daMobName = langName.getString(daMobName.toLowerCase().replace(" ", "."), "404 Name Not Found");
								}
								meta.setLore(lore);
								meta.setDisplayName(daMobName);
								itmStack.setItemMeta(meta);
								//if(debug){logDebug("test3a");}
							}else{
								if(debug){logDebug("EPIE test3b meta == null");}
						}
					}
				}
			}else{
				if(debug){logDebug("not player head");}
			}
		}
			//log("test4");*/
	}
	
	public String isPlayerHead(String string){
		try{
			playerFile = new File(getDataFolder() + "" + File.separatorChar + "player_heads.yml");//\
			if(!playerFile.exists()){																	// checks if the yaml does not exist
				return null;
			}
			int numOfCustomTrades = playerHeads.getInt("players.number", 0) + 1;
			if(debug){logDebug("iPH string=" + string);}
			for(int randomPlayerHead=1; randomPlayerHead<numOfCustomTrades; randomPlayerHead++){
				ItemStack itemstack = playerHeads.getItemStack("players.player_" + randomPlayerHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iPH getOwner_" + randomPlayerHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getOwner() != null){
							if(skullmeta.getOwner().toString().contains(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//playerHeads
		return null;
	}
	
	public String isBlockHead(String string){ // TODO: isBlockHead
		try{
			if(!(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16)){
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads.yml");//\
				if(!blockFile.exists()){																	// checks if the yaml does not exist
					return null;
				}
			}
			blockFile116 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
			//blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
			if(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16){
				if(!blockFile116.exists()){
					return null;
				}
			}
			int numOfCustomTrades = blockHeads.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).equals(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//blockHeads
		return null;
	}
	
	public String isBlockHead2(String string){
		try{
			if(!(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16)){																// checks if the yaml does not exist
					return null;
			}
			blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile1162.exists()){
					return null;
				}
			
			}
			int numOfCustomTrades = blockHeads2.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH2 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getOwner() != null){
							if(skullmeta.getOwner().toString().contains(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//blockHeads
		return null;
	}
	
	public String isBlockHead3(String string){
		try{
			if(!(Double.parseDouble(StrUtils.Left(getMCVersion(), 4)) >= 1.16)){																// checks if the yaml does not exist
					return null;
			}
			blockFile117 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17.yml");
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile117.exists()){
					return null;
				}
			
			}
			int numOfCustomTrades = blockHeads3.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH3 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads3.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getOwner() != null){
							if(skullmeta.getOwner().toString().contains(string)){
								return itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			return null;
		}
		//blockHeads
		return null;
	}
	
	
	public int isBlockHeadName(String string){ // TODO: isBlockHeadName
		if(debug){logDebug("iBHN START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads.yml");//\
				if(!blockFile.exists()){																	// checks if the yaml does not exist
					return -1;
				}
			}else if(mcVer == 1.16){
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
			}else if(mcVer >= 1.17) {
				blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17.yml");
			}

			if(debug){logDebug("iBH blockFile=" + blockFile.toString());}
			if(blockHeads.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads.load(blockFile);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			e.printStackTrace();
			if(debug){logDebug("iBHN END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN END Failure!");}
		return -1;
	}
	public int isBlockHeadName2(String string){
		if(debug){logDebug("iBHN2 START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){																// checks if the yaml does not exist
					return -1;
			}else if(mcVer == 1.16) {
				blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
			}else if(mcVer >= 1.17) {
				blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17_2.yml");
			}
			
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile1162.exists()){
					return -1;
				}
			
			}
			if(debug){logDebug("iBH blockFile1162=" + blockFile1162.toString());}
			if(blockHeads2.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads2.load(blockFile1162);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads2.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH2 number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH2 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads2.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			if(debug){logDebug("iBHN END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN2 END Failure!");}
		return -1;
	}
	
	public int isBlockHeadName3(String string){
		if(debug){logDebug("iBHN3 START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){																// checks if the yaml does not exist
					return -1;
			}else if(mcVer == 1.16) {
				return -1;
			}else if(mcVer >= 1.17) {
				blockFile117 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17_3.yml");
			}
			
			if(getMCVersion().startsWith("1.16")||getMCVersion().startsWith("1.17")){
				if(!blockFile117.exists()){
					return -1;
				}
			
			}
			if(debug){logDebug("iBHN3 blockFile117=" + blockFile117.toString());}
			if(blockHeads3.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads3.load(blockFile117);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads3.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH3 number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH3 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads3.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}else {
								//log(Level.INFO,"" + ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase());
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			if(debug){logDebug("iBHN3 END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN3 END Failure!");}
		return -1;
	}
	
	public int isBlockHeadName4(String string){
		if(debug){logDebug("iBHN4 START");}
		try{
			double mcVer = Double.parseDouble(StrUtils.Left(getMCVersion(), 4));
			if(!(mcVer >= 1.16)){																// checks if the yaml does not exist
					return -1;
			}else if(mcVer == 1.16) {
				return -1;
			}else if(mcVer == 1.19) {
				blockFile119 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_19.yml");
			}
			
			if(getMCVersion().startsWith("1.19")){
				if(!blockFile119.exists()){
					return -1;
				}
			
			}
			if(debug){logDebug("iBHN4 blockFile119=" + blockFile119.toString());}
			if(blockHeads4.getInt("blocks.number", 0) == 0) {
				try {
					blockHeads4.load(blockFile119);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			int numOfCustomTrades = blockHeads4.getInt("blocks.number", 0) + 1;
			if(debug){logDebug("iBH4 number=" + numOfCustomTrades);}
			if(debug){logDebug("iBH4 string=" + string);}
			for(int randomBlockHead = 1; randomBlockHead < numOfCustomTrades; randomBlockHead++){
				ItemStack itemstack = blockHeads4.getItemStack("blocks.block_" + randomBlockHead + ".itemstack", new ItemStack(Material.AIR));
				if(itemstack != null){
					SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
					if(skullmeta != null){
						//if(debug&&skullmeta != null){logDebug("iBH getOwner_" + randomBlockHead + "=" + skullmeta.getOwner().toString());}
						if(skullmeta.getDisplayName() != null){
							if(ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase().equals(string.toLowerCase())){
								if(debug){logDebug("iBHN4 END Sucess!");}
								return randomBlockHead; //itemstack.getItemMeta().getDisplayName();
							}else {
								//log(Level.INFO,"" + ChatColor.stripColor(skullmeta.getDisplayName()).toLowerCase());
							}
						}
					}
				}
			}
		}catch(Exception e){
			//stacktraceInfo();
			//e.printStackTrace();
			if(debug){logDebug("iBHN4 END Failure=Exception");}
			return -1;
		}
		//blockHeads
		if(debug){logDebug("iBHN4 END Failure!");}
		return -1;
	}

	public static void copyFile_Java7(String origin, String destination) throws IOException {
		Path FROM = Paths.get(origin);
		Path TO = Paths.get(destination);
		//overwrite the destination file if it exists, and copy
		// the file attributes, including the rwx permissions
		CopyOption[] options = new CopyOption[]{
			StandardCopyOption.REPLACE_EXISTING,
			StandardCopyOption.COPY_ATTRIBUTES
		}; 
		Files.copy(FROM, TO, options);
	}
	
	public void copyChance(String file, String file2){
		chanceConfig = new YmlConfiguration();
		oldchanceConfig = new YmlConfiguration();
		try {
			chanceConfig.load(new File(file2));
			oldchanceConfig.load(new File(file));
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		log(Level.INFO, "Copying values frome old_chance_config.yml to chance_config.yml");
		chanceConfig.set("chance_percent.player"							, oldchanceConfig.get("chance_percent.player", "0.50"));
		chanceConfig.set("chance_percent.named_mob"							, oldchanceConfig.get("chance_percent.named_mob", "0.10"));
		chanceConfig.set("chance_percent.allay"								, oldchanceConfig.get("chance_percent.allay", "0.20"));
		chanceConfig.set("chance_percent.axolotl.blue"						, oldchanceConfig.get("chance_percent.axolotl.blue", "1.00"));
		chanceConfig.set("chance_percent.axolotl.cyan"						, oldchanceConfig.get("chance_percent.axolotl.cyan", "0.20"));
		chanceConfig.set("chance_percent.axolotl.gold"						, oldchanceConfig.get("chance_percent.axolotl.gold", "0.20"));
		chanceConfig.set("chance_percent.axolotl.lucy"						, oldchanceConfig.get("chance_percent.axolotl.lucy", "0.20"));
		chanceConfig.set("chance_percent.axolotl.wild"						, oldchanceConfig.get("chance_percent.axolotl.wild", "0.20"));
		chanceConfig.set("chance_percent.bat"								, oldchanceConfig.get("chance_percent.bat", "0.10"));
		chanceConfig.set("chance_percent.bee.angry_pollinated"				, oldchanceConfig.get("chance_percent.bee.angry_pollinated", "0.20"));
		chanceConfig.set("chance_percent.bee.angry"							, oldchanceConfig.get("chance_percent.bee.angry", "0.20"));
		chanceConfig.set("chance_percent.bee.pollinated"					, oldchanceConfig.get("chance_percent.bee.pollinated", "0.20"));
		chanceConfig.set("chance_percent.bee.chance_percent"				, oldchanceConfig.get("chance_percent.bee.chance_percent", "0.20"));
		chanceConfig.set("chance_percent.blaze"								, oldchanceConfig.get("chance_percent.blaze", "0.005"));
		chanceConfig.set("chance_percent.cat.all_black"						, oldchanceConfig.get("chance_percent.cat.all_black", "0.33"));
		chanceConfig.set("chance_percent.cat.black"							, oldchanceConfig.get("chance_percent.cat.black", "0.33"));
		chanceConfig.set("chance_percent.cat.british_shorthair"				, oldchanceConfig.get("chance_percent.cat.british_shorthair", "0.33"));
		chanceConfig.set("chance_percent.cat.calico"						, oldchanceConfig.get("chance_percent.cat.calico", "0.33"));
		chanceConfig.set("chance_percent.cat.jellie"						, oldchanceConfig.get("chance_percent.cat.jellie", "0.33"));
		chanceConfig.set("chance_percent.cat.persian"						, oldchanceConfig.get("chance_percent.cat.persian", "0.33"));
		chanceConfig.set("chance_percent.cat.ragdoll"						, oldchanceConfig.get("chance_percent.cat.ragdoll", "0.33"));
		chanceConfig.set("chance_percent.cat.red"							, oldchanceConfig.get("chance_percent.cat.red", "0.33"));
		chanceConfig.set("chance_percent.cat.siamese"						, oldchanceConfig.get("chance_percent.cat.siamese", "0.33"));
		chanceConfig.set("chance_percent.cat.tabby"							, oldchanceConfig.get("chance_percent.cat.tabby", "0.33"));
		chanceConfig.set("chance_percent.cat.white"							, oldchanceConfig.get("chance_percent.cat.white", "0.33"));
		
		chanceConfig.set("chance_percent.cave_spider"						, oldchanceConfig.get("chance_percent.cave_spider", "0.005"));
		chanceConfig.set("chance_percent.chicken"							, oldchanceConfig.get("chance_percent.chicken", "0.01"));
		chanceConfig.set("chance_percent.cod"								, oldchanceConfig.get("chance_percent.cod", "0.10"));
		chanceConfig.set("chance_percent.cow"								, oldchanceConfig.get("chance_percent.cow", "0.01"));
		chanceConfig.set("chance_percent.creeper"							, oldchanceConfig.get("chance_percent.creeper", "0.50"));
		chanceConfig.set("chance_percent.creeper_charged"					, oldchanceConfig.get("chance_percent.creeper_charged", "1.00"));
		chanceConfig.set("chance_percent.dolphin"							, oldchanceConfig.get("chance_percent.dolphin", "0.33"));
		chanceConfig.set("chance_percent.donkey"							, oldchanceConfig.get("chance_percent.donkey", "0.20"));
		chanceConfig.set("chance_percent.drowned"							, oldchanceConfig.get("chance_percent.drowned", "0.05"));
		chanceConfig.set("chance_percent.elder_guardian"					, oldchanceConfig.get("chance_percent.elder_guardian", "1.00"));
		chanceConfig.set("chance_percent.ender_dragon"						, oldchanceConfig.get("chance_percent.ender_dragon", "1.00"));
		chanceConfig.set("chance_percent.enderman"							, oldchanceConfig.get("chance_percent.enderman", "0.005"));
		chanceConfig.set("chance_percent.endermite"							, oldchanceConfig.get("chance_percent.endermite", "0.10"));
		chanceConfig.set("chance_percent.evoker"							, oldchanceConfig.get("chance_percent.evoker", "0.25"));
		chanceConfig.set("chance_percent.fox.red"							, oldchanceConfig.get("chance_percent.fox.red", "0.10"));
		chanceConfig.set("chance_percent.fox.snow"							, oldchanceConfig.get("chance_percent.fox.snow", "0.10"));
		chanceConfig.set("chance_percent.frog.cold"							, oldchanceConfig.get("chance_percent.frog.cold", "0.20"));
		chanceConfig.set("chance_percent.frog.temperate"					, oldchanceConfig.get("chance_percent.frog.temperate", "0.20"));
		chanceConfig.set("chance_percent.frog.warm"							, oldchanceConfig.get("chance_percent.frog.warm", "0.20"));
		chanceConfig.set("chance_percent.ghast"								, oldchanceConfig.get("chance_percent.ghast", "0.0625"));
		chanceConfig.set("chance_percent.giant"								, oldchanceConfig.get("chance_percent.giant", "0.025"));
		chanceConfig.set("chance_percent.glow_squid"						, oldchanceConfig.get("chance_percent.glow_squid", "0.10"));
		chanceConfig.set("chance_percent.goat.mormal"						, oldchanceConfig.get("chance_percent.goat.normal", "0.01"));
		chanceConfig.set("chance_percent.goat.screaming"					, oldchanceConfig.get("chance_percent.goat.screaming", "1.00"));
		chanceConfig.set("chance_percent.guardian"							, oldchanceConfig.get("chance_percent.guardian", "0.005"));
		chanceConfig.set("chance_percent.hoglin"							, oldchanceConfig.get("chance_percent.hoglin", "0.03"));
		chanceConfig.set("chance_percent.horse.black"						, oldchanceConfig.get("chance_percent.horse.black", "0.27"));
		chanceConfig.set("chance_percent.horse.brown"						, oldchanceConfig.get("chance_percent.horse.brown", "0.27"));
		chanceConfig.set("chance_percent.horse.chestnut"					, oldchanceConfig.get("chance_percent.horse.chestnut", "0.27"));
		chanceConfig.set("chance_percent.horse.creamy"						, oldchanceConfig.get("chance_percent.horse.creamy", "0.27"));
		chanceConfig.set("chance_percent.horse.dark_brown"					, oldchanceConfig.get("chance_percent.horse.dark_brown", "0.27"));
		chanceConfig.set("chance_percent.horse.gray"						, oldchanceConfig.get("chance_percent.horse.gray", "0.27"));
		chanceConfig.set("chance_percent.horse.white"						, oldchanceConfig.get("chance_percent.horse.white", "0.27"));
		chanceConfig.set("chance_percent.husk"								, oldchanceConfig.get("chance_percent.husk", "0.06"));
		chanceConfig.set("chance_percent.illusioner"						, oldchanceConfig.get("chance_percent.illusioner", "0.25"));
		chanceConfig.set("chance_percent.iron_golem"						, oldchanceConfig.get("chance_percent.iron_golem", "0.05"));
		chanceConfig.set("chance_percent.llama.brown"						, oldchanceConfig.get("chance_percent.llama.brown", "0.24"));
		chanceConfig.set("chance_percent.llama.creamy"						, oldchanceConfig.get("chance_percent.llama.creamy", "0.24"));
		chanceConfig.set("chance_percent.llama.gray"						, oldchanceConfig.get("chance_percent.llama.gray", "0.24"));
		chanceConfig.set("chance_percent.llama.white"						, oldchanceConfig.get("chance_percent.llama.white", "0.24"));
		chanceConfig.set("chance_percent.magma_cube"						, oldchanceConfig.get("chance_percent.magma_cube", "0.005"));
		chanceConfig.set("chance_percent.mule"								, oldchanceConfig.get("chance_percent.mule", "0.20"));
		chanceConfig.set("chance_percent.mushroom_cow.red"					, oldchanceConfig.get("chance_percent.mushroom_cow.red", "0.10"));
		chanceConfig.set("chance_percent.mushroom_cow.brown"				, oldchanceConfig.get("chance_percent.mushroom_cow.brown", "0.10"));
		chanceConfig.set("chance_percent.ocelot"							, oldchanceConfig.get("chance_percent.cat.wild_ocelot", "0.20"));
		chanceConfig.set("chance_percent.panda.aggressive"					, oldchanceConfig.get("chance_percent.panda.aggressive", "0.27"));
		chanceConfig.set("chance_percent.panda.brown"						, oldchanceConfig.get("chance_percent.panda.brown", "0.27"));
		chanceConfig.set("chance_percent.panda.lazy"						, oldchanceConfig.get("chance_percent.panda.lazy", "0.27"));
		chanceConfig.set("chance_percent.panda.normal"						, oldchanceConfig.get("chance_percent.panda.normal", "0.27"));
		chanceConfig.set("chance_percent.panda.playful"						, oldchanceConfig.get("chance_percent.panda.playful", "0.27"));
		chanceConfig.set("chance_percent.panda.weak"						, oldchanceConfig.get("chance_percent.panda.weak", "0.27"));
		chanceConfig.set("chance_percent.panda.worried"						, oldchanceConfig.get("chance_percent.panda.worried", "0.27"));
		chanceConfig.set("chance_percent.parrot.blue"						, oldchanceConfig.get("chance_percent.parrot.blue", "0.25"));
		chanceConfig.set("chance_percent.parrot.cyan"						, oldchanceConfig.get("chance_percent.parrot.cyan", "0.25"));
		chanceConfig.set("chance_percent.parrot.gray"						, oldchanceConfig.get("chance_percent.parrot.gray", "0.25"));
		chanceConfig.set("chance_percent.parrot.green"						, oldchanceConfig.get("chance_percent.parrot.green", "0.25"));
		chanceConfig.set("chance_percent.parrot.red"						, oldchanceConfig.get("chance_percent.parrot.red", "0.25"));
		chanceConfig.set("chance_percent.phantom"							, oldchanceConfig.get("chance_percent.phantom", "0.10"));
		chanceConfig.set("chance_percent.pig"								, oldchanceConfig.get("chance_percent.pig", "0.01"));
		chanceConfig.set("chance_percent.piglin"							, oldchanceConfig.get("chance_percent.piglin", "0.04"));
		chanceConfig.set("chance_percent.pig_zombie"						, oldchanceConfig.get("chance_percent.pig_zombie", "0.005"));
		chanceConfig.set("chance_percent.pillager"							, oldchanceConfig.get("chance_percent.pillager", "0.025"));
		chanceConfig.set("chance_percent.polar_bear"						, oldchanceConfig.get("chance_percent.polar_bear", "0.20"));
		chanceConfig.set("chance_percent.pufferfish"						, oldchanceConfig.get("chance_percent.pufferfish", "0.15"));
		chanceConfig.set("chance_percent.rabbit.black"						, oldchanceConfig.get("chance_percent.rabbit.black", "0.26"));
		chanceConfig.set("chance_percent.rabbit.black_and_white"			, oldchanceConfig.get("chance_percent.rabbit.black_and_white", "0.26"));
		chanceConfig.set("chance_percent.rabbit.brown"						, oldchanceConfig.get("chance_percent.rabbit.brown", "0.26"));
		chanceConfig.set("chance_percent.rabbit.gold"						, oldchanceConfig.get("chance_percent.rabbit.gold", "0.26"));
		chanceConfig.set("chance_percent.rabbit.salt_and_pepper"			, oldchanceConfig.get("chance_percent.rabbit.salt_and_pepper", "0.26"));
		chanceConfig.set("chance_percent.rabbit.the_killer_bunny"			, oldchanceConfig.get("chance_percent.rabbit.the_killer_bunny", "1.00"));
		chanceConfig.set("chance_percent.rabbit.toast"						, oldchanceConfig.get("chance_percent.rabbit.toast", "0.26"));
		chanceConfig.set("chance_percent.rabbit.white"						, oldchanceConfig.get("chance_percent.rabbit.white", "0.26"));
		chanceConfig.set("chance_percent.ravager"							, oldchanceConfig.get("chance_percent.ravager", "0.25"));
		chanceConfig.set("chance_percent.salmon"							, oldchanceConfig.get("chance_percent.salmon", "0.10"));
		chanceConfig.set("chance_percent.sheep.black"						, oldchanceConfig.get("chance_percent.sheep.black", "0.0175"));
		chanceConfig.set("chance_percent.sheep.blue"						, oldchanceConfig.get("chance_percent.sheep.blue", "0.0175"));
		chanceConfig.set("chance_percent.sheep.brown"						, oldchanceConfig.get("chance_percent.sheep.brown", "0.0175"));
		chanceConfig.set("chance_percent.sheep.cyan"						, oldchanceConfig.get("chance_percent.sheep.cyan", "0.0175"));
		chanceConfig.set("chance_percent.sheep.gray"						, oldchanceConfig.get("chance_percent.sheep.gray", "0.0175"));
		chanceConfig.set("chance_percent.sheep.green"						, oldchanceConfig.get("chance_percent.sheep.green", "0.0175"));
		chanceConfig.set("chance_percent.sheep.jeb_"						, oldchanceConfig.get("chance_percent.sheep.jeb_", "0.0175"));
		chanceConfig.set("chance_percent.sheep.light_blue"					, oldchanceConfig.get("chance_percent.sheep.light_blue", "0.0175"));
		chanceConfig.set("chance_percent.sheep.light_gray"					, oldchanceConfig.get("chance_percent.sheep.light_gray", "0.0175"));
		chanceConfig.set("chance_percent.sheep.lime"						, oldchanceConfig.get("chance_percent.sheep.lime", "0.0175"));
		chanceConfig.set("chance_percent.sheep.magenta"						, oldchanceConfig.get("chance_percent.sheep.magenta", "0.0175"));
		chanceConfig.set("chance_percent.sheep.orange"						, oldchanceConfig.get("chance_percent.sheep.orange", "0.0175"));
		chanceConfig.set("chance_percent.sheep.pink"						, oldchanceConfig.get("chance_percent.sheep.pink", "0.0175"));
		chanceConfig.set("chance_percent.sheep.purple"						, oldchanceConfig.get("chance_percent.sheep.purple", "0.0175"));
		chanceConfig.set("chance_percent.sheep.red"							, oldchanceConfig.get("chance_percent.sheep.red", "0.0175"));
		chanceConfig.set("chance_percent.sheep.white"						, oldchanceConfig.get("chance_percent.sheep.white", "0.0175"));
		chanceConfig.set("chance_percent.sheep.yellow"						, oldchanceConfig.get("chance_percent.sheep.yellow", "0.0175"));
		chanceConfig.set("chance_percent.shulker"							, oldchanceConfig.get("chance_percent.shulker", "0.05"));
		chanceConfig.set("chance_percent.silverfish"						, oldchanceConfig.get("chance_percent.silverfish", "0.05"));
		chanceConfig.set("chance_percent.skeleton"							, oldchanceConfig.get("chance_percent.skeleton", "0.025"));
		chanceConfig.set("chance_percent.skeleton_horse"					, oldchanceConfig.get("chance_percent.skeleton_horse", "0.20"));
		chanceConfig.set("chance_percent.slime"								, oldchanceConfig.get("chance_percent.slime", "0.005"));
		chanceConfig.set("chance_percent.snowman"							, oldchanceConfig.get("chance_percent.snowman", "0.05"));
		chanceConfig.set("chance_percent.spider"							, oldchanceConfig.get("chance_percent.spider", "0.005"));
		chanceConfig.set("chance_percent.squid"								, oldchanceConfig.get("chance_percent.squid", "0.05"));
		chanceConfig.set("chance_percent.stray"								, oldchanceConfig.get("chance_percent.stray", "0.06"));
		chanceConfig.set("chance_percent.strider"							, oldchanceConfig.get("chance_percent.strider", "0.10"));
		chanceConfig.set("chance_percent.tadpole"							, oldchanceConfig.get("chance_percent.tadpole", "0.10"));
		chanceConfig.set("chance_percent.trader_llama.brown"				, oldchanceConfig.get("chance_percent.trader_llama.brown", "0.24"));
		chanceConfig.set("chance_percent.trader_llama.creamy"				, oldchanceConfig.get("chance_percent.trader_llama.creamy", "0.24"));
		chanceConfig.set("chance_percent.trader_llama.gray"					, oldchanceConfig.get("chance_percent.trader_llama.gray", "0.24"));
		chanceConfig.set("chance_percent.trader_llama.white"				, oldchanceConfig.get("chance_percent.trader_llama.white", "0.24"));
		chanceConfig.set("chance_percent.tropical_fish"						, oldchanceConfig.get("chance_percent.tropical_fish", "0.10"));
		chanceConfig.set("chance_percent.turtle"							, oldchanceConfig.get("chance_percent.turtle", "0.10"));
		chanceConfig.set("chance_percent.vex"								, oldchanceConfig.get("chance_percent.vex", "0.10"));
		chanceConfig.set("chance_percent.villager.desert.armorer"			, oldchanceConfig.get("chance_percent.villager.desert.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.butcher"			, oldchanceConfig.get("chance_percent.villager.desert.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.cartographer"		, oldchanceConfig.get("chance_percent.villager.desert.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.cleric"			, oldchanceConfig.get("chance_percent.villager.desert.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.farmer"			, oldchanceConfig.get("chance_percent.villager.desert.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.fisherman"			, oldchanceConfig.get("chance_percent.villager.desert.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.fletcher"			, oldchanceConfig.get("chance_percent.villager.desert.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.leatherworker"		, oldchanceConfig.get("chance_percent.villager.desert.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.librarian"			, oldchanceConfig.get("chance_percent.villager.desert.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.mason"				, oldchanceConfig.get("chance_percent.villager.desert.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.nitwit"			, oldchanceConfig.get("chance_percent.villager.desert.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.none"				, oldchanceConfig.get("chance_percent.villager.desert.none", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.shepherd"			, oldchanceConfig.get("chance_percent.villager.desert.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.toolsmith"			, oldchanceConfig.get("chance_percent.villager.desert.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.desert.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.desert.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.armorer"			, oldchanceConfig.get("chance_percent.villager.jungle.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.butcher"			, oldchanceConfig.get("chance_percent.villager.jungle.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.cartographer"		, oldchanceConfig.get("chance_percent.villager.jungle.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.cleric"			, oldchanceConfig.get("chance_percent.villager.jungle.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.farmer"			, oldchanceConfig.get("chance_percent.villager.jungle.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.fisherman"			, oldchanceConfig.get("chance_percent.villager.jungle.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.fletcher"			, oldchanceConfig.get("chance_percent.villager.jungle.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.leatherworker"		, oldchanceConfig.get("chance_percent.villager.jungle.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.librarian"			, oldchanceConfig.get("chance_percent.villager.jungle.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.mason"				, oldchanceConfig.get("chance_percent.villager.jungle.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.nitwit"			, oldchanceConfig.get("chance_percent.villager.jungle.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.none"				, oldchanceConfig.get("chance_percent.villager.jungle.none", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.shepherd"			, oldchanceConfig.get("chance_percent.villager.jungle.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.toolsmith"			, oldchanceConfig.get("chance_percent.villager.jungle.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.jungle.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.jungle.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.armorer"			, oldchanceConfig.get("chance_percent.villager.plains.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.butcher"			, oldchanceConfig.get("chance_percent.villager.plains.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.cartographer"		, oldchanceConfig.get("chance_percent.villager.plains.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.cleric"			, oldchanceConfig.get("chance_percent.villager.plains.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.farmer"			, oldchanceConfig.get("chance_percent.villager.plains.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.fisherman"			, oldchanceConfig.get("chance_percent.villager.plains.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.fletcher"			, oldchanceConfig.get("chance_percent.villager.plains.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.leatherworker"		, oldchanceConfig.get("chance_percent.villager.plains.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.librarian"			, oldchanceConfig.get("chance_percent.villager.plains.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.mason"				, oldchanceConfig.get("chance_percent.villager.plains.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.nitwit"			, oldchanceConfig.get("chance_percent.villager.plains.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.none"				, oldchanceConfig.get("chance_percent.villager.plains.none", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.shepherd"			, oldchanceConfig.get("chance_percent.villager.plains.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.toolsmith"			, oldchanceConfig.get("chance_percent.villager.plains.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.plains.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.plains.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.armorer"			, oldchanceConfig.get("chance_percent.villager.savanna.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.butcher"			, oldchanceConfig.get("chance_percent.villager.savanna.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.cartographer"		, oldchanceConfig.get("chance_percent.villager.savanna.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.cleric"			, oldchanceConfig.get("chance_percent.villager.savanna.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.farmer"			, oldchanceConfig.get("chance_percent.villager.savanna.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.fisherman"		, oldchanceConfig.get("chance_percent.villager.savanna.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.fletcher"			, oldchanceConfig.get("chance_percent.villager.savanna.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.leatherworker"	, oldchanceConfig.get("chance_percent.villager.savanna.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.librarian"		, oldchanceConfig.get("chance_percent.villager.savanna.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.mason"			, oldchanceConfig.get("chance_percent.villager.savanna.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.nitwit"			, oldchanceConfig.get("chance_percent.villager.savanna.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.none"				, oldchanceConfig.get("chance_percent.villager.savanna.none", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.shepherd"			, oldchanceConfig.get("chance_percent.villager.savanna.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.toolsmith"		, oldchanceConfig.get("chance_percent.villager.savanna.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.savanna.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.savanna.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.armorer"				, oldchanceConfig.get("chance_percent.villager.snow.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.butcher"				, oldchanceConfig.get("chance_percent.villager.snow.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.cartographer"		, oldchanceConfig.get("chance_percent.villager.snow.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.cleric"				, oldchanceConfig.get("chance_percent.villager.snow.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.farmer"				, oldchanceConfig.get("chance_percent.villager.snow.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.fisherman"			, oldchanceConfig.get("chance_percent.villager.snow.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.fletcher"			, oldchanceConfig.get("chance_percent.villager.snow.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.leatherworker"		, oldchanceConfig.get("chance_percent.villager.snow.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.librarian"			, oldchanceConfig.get("chance_percent.villager.snow.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.mason"				, oldchanceConfig.get("chance_percent.villager.snow.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.nitwit"				, oldchanceConfig.get("chance_percent.villager.snow.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.none"				, oldchanceConfig.get("chance_percent.villager.snow.none", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.shepherd"			, oldchanceConfig.get("chance_percent.villager.snow.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.toolsmith"			, oldchanceConfig.get("chance_percent.villager.snow.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.snow.weaponsmith"			, oldchanceConfig.get("chance_percent.villager.snow.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.armorer"			, oldchanceConfig.get("chance_percent.villager.swamp.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.butcher"			, oldchanceConfig.get("chance_percent.villager.swamp.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.cartographer"		, oldchanceConfig.get("chance_percent.villager.swamp.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.cleric"				, oldchanceConfig.get("chance_percent.villager.swamp.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.farmer"				, oldchanceConfig.get("chance_percent.villager.swamp.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.fisherman"			, oldchanceConfig.get("chance_percent.villager.swamp.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.fletcher"			, oldchanceConfig.get("chance_percent.villager.swamp.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.leatherworker"		, oldchanceConfig.get("chance_percent.villager.swamp.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.librarian"			, oldchanceConfig.get("chance_percent.villager.swamp.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.mason"				, oldchanceConfig.get("chance_percent.villager.swamp.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.nitwit"				, oldchanceConfig.get("chance_percent.villager.swamp.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.none"				, oldchanceConfig.get("chance_percent.villager.swamp.none", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.shepherd"			, oldchanceConfig.get("chance_percent.villager.swamp.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.toolsmith"			, oldchanceConfig.get("chance_percent.villager.swamp.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.swamp.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.swamp.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.armorer"			, oldchanceConfig.get("chance_percent.villager.taiga.armorer", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.butcher"			, oldchanceConfig.get("chance_percent.villager.taiga.butcher", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.cartographer"		, oldchanceConfig.get("chance_percent.villager.taiga.cartographer", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.cleric"				, oldchanceConfig.get("chance_percent.villager.taiga.cleric", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.farmer"				, oldchanceConfig.get("chance_percent.villager.taiga.farmer", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.fisherman"			, oldchanceConfig.get("chance_percent.villager.taiga.fisherman", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.fletcher"			, oldchanceConfig.get("chance_percent.villager.taiga.fletcher", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.leatherworker"		, oldchanceConfig.get("chance_percent.villager.taiga.leatherworker", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.librarian"			, oldchanceConfig.get("chance_percent.villager.taiga.librarian", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.mason"				, oldchanceConfig.get("chance_percent.villager.taiga.mason", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.nitwit"				, oldchanceConfig.get("chance_percent.villager.taiga.nitwit", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.none"				, oldchanceConfig.get("chance_percent.villager.taiga.none", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.shepherd"			, oldchanceConfig.get("chance_percent.villager.taiga.shepherd", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.toolsmith"			, oldchanceConfig.get("chance_percent.villager.taiga.toolsmith", "1.00"));
		chanceConfig.set("chance_percent.villager.taiga.weaponsmith"		, oldchanceConfig.get("chance_percent.villager.taiga.weaponsmith", "1.00"));
		chanceConfig.set("chance_percent.vindicator"						, oldchanceConfig.get("chance_percent.vindicator", "0.05"));
		chanceConfig.set("chance_percent.wandering_trader"					, oldchanceConfig.get("chance_percent.wandering_trader", "1.00"));
		chanceConfig.set("chance_percent.warden"							, oldchanceConfig.get("chance_percent.warden", "1.00"));
		chanceConfig.set("chance_percent.witch"								, oldchanceConfig.get("chance_percent.witch", "0.005"));
		chanceConfig.set("chance_percent.wither"							, oldchanceConfig.get("chance_percent.wither", "1.00"));
		chanceConfig.set("chance_percent.wither_skeleton"					, oldchanceConfig.get("chance_percent.wither_skeleton", "0.025"));
		chanceConfig.set("chance_percent.wolf"								, oldchanceConfig.get("chance_percent.wolf", "0.02"));
		chanceConfig.set("chance_percent.zoglin"							, oldchanceConfig.get("chance_percent.zoglin", "0.20"));
		chanceConfig.set("chance_percent.zombie"							, oldchanceConfig.get("chance_percent.zombie", "0.025"));
		chanceConfig.set("chance_percent.zombie_horse"						, oldchanceConfig.get("chance_percent.zombie_horse", "1.00"));
		chanceConfig.set("chance_percent.zombie_pigman"						, oldchanceConfig.get("chance_percent.zombie_pigman", "0.005"));
		chanceConfig.set("chance_percent.zombified_piglin"					, oldchanceConfig.get("chance_percent.zombified_piglin", "0.005"));
		chanceConfig.set("chance_percent.zombie_villager"					, oldchanceConfig.get("chance_percent.zombie_villager", "0.50"));
		try {
			chanceConfig.save(file2);
		} catch (IOException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		log(Level.INFO, "chance_config.yml has been updated!");
		oldchanceConfig = null;
	}
	
	public void stacktraceInfo(){
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Include this with the stacktrace when reporting issues.");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " vardebug=" + debug + " debug=" + getConfig().get("debug","error") + " in " + this.getDataFolder() + "/config.yml");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " jarfile name=" + this.getFile().getAbsoluteFile());
		debug = true;
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " DEBUG has been set as true until plugin reload or /mmh td, or /mmh reload.");
	}
	public static void stacktraceInfoStatic(){
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " Include this with the stacktrace when reporting issues.");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " vardebug=" + debug);
		debug = true;
		logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " DEBUG has been set as true until plugin reload or /mmh td, or /mmh reload.");
	}
	
	// Persistent Heads
	private final NamespacedKey NAME_KEY = new NamespacedKey(this, "head_name");
    private final NamespacedKey LORE_KEY = new NamespacedKey(this, "head_lore");
    private final PersistentDataType<String,String[]> LORE_PDT = new JsonDataType<>(String[].class);
    //@SuppressWarnings("unused")
	//private final PersistentDataType LORE_PDT2 = new JsonDataType<>(String.class);
    //private final NamespacedKey DISPLAY_KEY = new NamespacedKey(this, "head_display");
    //private final NamespacedKey SKULLOWNER_KEY = new NamespacedKey(this, "head_skullowner");
    
    // TODO: Persistent Heads    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        @Nonnull ItemStack headItem = event.getItemInHand();
        if (headItem.getType() != Material.PLAYER_HEAD) return;
        ItemMeta meta = headItem.getItemMeta();
        if (meta == null) return;
        @Nonnull String name = meta.getDisplayName();
        @Nullable List<String> lore = meta.getLore();
        @Nonnull Block block = event.getBlockPlaced();
        // NOTE: Not using snapshots is broken: https://github.com/PaperMC/Paper/issues/3913
        BlockStateSnapshotResult blockStateSnapshotResult = PaperLib.getBlockState(block, true);
        TileState skullState = (TileState) blockStateSnapshotResult.getState();
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        skullPDC.set(NAME_KEY, PersistentDataType.STRING, name);
        if (lore != null) skullPDC.set(LORE_KEY, LORE_PDT, lore.toArray(new String[0]));
        if (blockStateSnapshotResult.isSnapshot()) skullState.update();
        String strLore = "no lore";
        if(lore != null){ strLore = lore.toString(); }
        if(debug) {log(Level.INFO, "Player " + event.getPlayer().getName() + " placed a head named \"" + name + "\" with lore=\'" + strLore + "\' at " + event.getBlockPlaced().getLocation());};
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDropItemEvent(BlockDropItemEvent event) {
        @Nonnull BlockState blockState = event.getBlockState();
        Material blockType = blockState.getType();
        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
        TileState skullState = (TileState) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
        for (Item item: event.getItems()) { // Ideally should only be one...
            @Nonnull ItemStack itemstack = item.getItemStack();
            if (itemstack.getType() == Material.PLAYER_HEAD) {
                @Nullable ItemMeta meta = itemstack.getItemMeta();
                if (meta == null) continue; // This shouldn't happen
                meta.setDisplayName(name);
                if (lore != null) meta.setLore(Arrays.asList(lore));
                itemstack.setItemMeta(meta);
            }
        }
        if(debug) {log(Level.INFO, "BDIE - Persistent head completed.");};
    }
    
    /**
     * Prevents player from removing player-head NBT by water logging them
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
    	handleBlock(event.getBlock(), event, false);
    }
    
    /**
     * Prevents player from removing player-head NBT using running water
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLiquidFlow(BlockFromToEvent event) {
    	handleBlock(event.getToBlock(), event, true);
    }
    
    /*
     * Prevents explosion from removing player-head NBT using an explosion
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplosion(BlockExplodeEvent event) {
        handleExplosionEvent(event.blockList(), event.getYield());
    }
    
    /*
     * Prevents entity from removing player-head NBT using an explosion
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityExplosion(EntityExplodeEvent event) {
        handleExplosionEvent(event.blockList(), event.getYield());
    }
    
    /*
     * Prevents piston extending from removing NBT data.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPistonExtendEvent(BlockPistonExtendEvent event) {
    	if(!config.getBoolean("event.piston_extend", true)) {
    		return;
    	}
    	List<Block> blocks = event.getBlocks();
    	Iterator<Block> iter = blocks.iterator();
    	try {
	    	while (iter.hasNext()) {
	            Block block = iter.next();
	            if (block.getState() instanceof Skull) { //if (block.getState() instanceof Skull && random.nextFloat() <= explosionYield)
	                handleBlock(block, null, false);
	                iter.remove();
	            }
	        }
    	}catch(Exception e) {
         	
        }
    }
    
    @SuppressWarnings("unused")
	private void handleExplosionEvent(@Nonnull final List<Block> blocksExploded, final float explosionYield) {
    	final Random random = ThreadLocalRandom.current();
        Iterator<Block> iter = blocksExploded.iterator();
        try {
	        while (iter.hasNext()) {
	            Block block = iter.next();
	            if (block.getState() instanceof Skull) { //if (block.getState() instanceof Skull && random.nextFloat() <= explosionYield)
	                handleBlock(block, null, false);
	                iter.remove();
	            }
	        }
        }catch(Exception e) {
        	
        }
    }
    
    private void handleBlock(Block block, Cancellable event, boolean cancelEvent) {
    	@Nonnull BlockState blockState = block.getState();
        if (blockState.getType() != Material.PLAYER_HEAD && blockState.getType() != Material.PLAYER_WALL_HEAD) return;
        Skull skullState = (Skull) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
        @Nonnull Optional<ItemStack> skullStack = block.getDrops().stream().filter(is -> is.getType() == Material.PLAYER_HEAD).findAny();
        if (skullStack.isPresent()) {
            if (updateDrop(block, name, lore, skullStack.get())) return; // This shouldn't happen
            if (cancelEvent) event.setCancelled(true);
        }

        BlockState blockState1 = block.getWorld().getBlockAt(block.getLocation()).getState();
        blockState1.update(true, true);
        if(debug) {log(Level.INFO, "HB - Persistent head completed.");};
    }
    
    /**@EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
		List<Block> a = event.blockList();
		for (Block block: event.blockList()) { // Ideally should only be one...
			@Nonnull BlockState blockState = block.getState();
	        Material blockType = blockState.getType();
	        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
	        TileState skullState = (TileState) blockState;
	        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
	        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
	        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
	        if (name == null) return;
	        block.breakNaturally();
		}
    	
    }//*/
    
    @SuppressWarnings("unused")
	private void handleEvent(Supplier<Block> blockSupplier, Cancellable event, boolean cancelEvent) {
        Block block = blockSupplier.get();
        @Nonnull BlockState blockState = block.getState();
        if (blockState.getType() != Material.PLAYER_HEAD && blockState.getType() != Material.PLAYER_WALL_HEAD) return;
        Skull skullState = (Skull) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
        @Nonnull Optional<ItemStack> skullStack = block.getDrops().stream().filter(is -> is.getType() == Material.PLAYER_HEAD).findAny();
        if (skullStack.isPresent()) {
            if (updateDrop(block, name, lore, skullStack.get())) return; // This shouldn't happen
            if (cancelEvent) event.setCancelled(true);
        }

        BlockState blockState1 = block.getWorld().getBlockAt(block.getLocation()).getState();
        blockState1.update(true, true);
        if(debug) {log(Level.INFO, "HE - Persistent head completed.");};
    }

    private boolean updateDrop(Block block, @Nullable String name, @Nullable String[] lore, @Nonnull ItemStack itemstack) {
        @Nullable ItemMeta meta = itemstack.getItemMeta();
        if (meta == null) return true;
        meta.setDisplayName(name);
        if (lore != null) meta.setLore(Arrays.asList(lore));
        itemstack.setItemMeta(meta);

        block.getWorld().dropItemNaturally(block.getLocation(), itemstack);
        block.getDrops().clear();
        block.setType(Material.AIR);
        if(debug) {log(Level.INFO, "UD - Persistent head completed.");};
        return false;
    }
    // Persistent Heads
    
	/**@EventHandler()
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
    	Block block = event.getBlock();
    	Location loc = block.getLocation();
    	@Nonnull BlockState blockState = block.getState();
        Material blockType = blockState.getType();
        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
        TileState skullState = (TileState) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
    	Collection<ItemStack> drops = block.getDrops();
		ItemStack[] stackArray = drops.toArray(new ItemStack[0]);
    	@Nonnull ItemStack itemstack = stackArray[0];
        if (itemstack.getType() == Material.PLAYER_HEAD) {
            @Nullable ItemMeta meta = itemstack.getItemMeta();
            if (meta == null) {
            	logWarn("meta=null");
            	return; // This shouldn't happen
            }
            meta.setDisplayName(name);
            if (lore != null) meta.setLore(Arrays.asList(lore));
            itemstack.setItemMeta(meta);
            
        	block.getWorld().dropItemNaturally(block.getLocation(), itemstack);
        	block.getDrops().clear();
        	block.setType(Material.AIR);
        }

        BlockState blockS = block.getWorld().getBlockAt(loc).getState();
        blockS.update(true, true);
    }
	
    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        Block block = event.getToBlock();
        Location loc = block.getLocation();
        
        @Nonnull BlockState blockState = block.getState();
        Material blockType = blockState.getType();
        if (blockType != Material.PLAYER_HEAD && blockType != Material.PLAYER_WALL_HEAD) return;
        TileState skullState = (TileState) blockState;
        @Nonnull PersistentDataContainer skullPDC = skullState.getPersistentDataContainer();
        @Nullable String name = skullPDC.get(NAME_KEY, PersistentDataType.STRING);
        @Nullable String[] lore = skullPDC.get(LORE_KEY, LORE_PDT);
        if (name == null) return;
        Collection<ItemStack> drops = block.getDrops();
        ItemStack[] stackArray = drops.toArray(new ItemStack[0]);
        @Nonnull ItemStack itemstack = stackArray[0];
        if (itemstack.getType() == Material.PLAYER_HEAD) {
        	@Nullable ItemMeta meta = itemstack.getItemMeta();
        	if (meta == null) return; // This shouldn't happen
        	meta.setDisplayName(name);
        	if (lore != null) meta.setLore(Arrays.asList(lore));
        	itemstack.setItemMeta(meta);
        	
        	block.getWorld().dropItemNaturally(block.getLocation(), itemstack);
        	block.getDrops().clear();
        	block.setType(Material.AIR);
        	event.setCancelled(true);
        }

        BlockState blockS = block.getWorld().getBlockAt(loc).getState();
        blockS.update(true, true);
    }// */
    
    @SuppressWarnings("unused")
	public ItemStack fixHeadStack(ItemStack offHand, ItemStack mainHand){
    	NBTItem nbti = new NBTItem(offHand);
    	Set<String> SkullKeys = nbti.getKeys();
    	int damage = nbti.getInteger("Damage");
    	NBTCompound display = nbti.getCompound("display");
    	NBTCompound SkullOwner = nbti.getCompound("SkullOwner");
    	if(debug){logDebug("FHS Offhand damage=" + damage);}
    	if(debug){logDebug("FHS Offhand display=" + display.toString());}
    	if(debug){logDebug("FHS Offhand SkullOwner=" + SkullOwner.toString());}
    	
    	NBTItem nbti2 = new NBTItem(mainHand);
    	Set<String> SkullKeys2 = nbti2.getKeys();
    	int damage2 = nbti2.getInteger("Damage");
    	NBTCompound display2 = nbti2.getCompound("display");
    	NBTCompound SkullOwner2 = nbti2.getCompound("SkullOwner");
    	if(debug){logDebug("FHS Mainhand damage=" + damage2);}
    	if(debug){logDebug("FHS Mainhand display=" + display2.toString());}
    	if(debug){logDebug("FHS Mainhand SkullOwner=" + SkullOwner2.toString());}
    	
        if( display.equals(display2) && SkullOwner.equals(SkullOwner2) && damage != damage2){
        	ItemStack is = new ItemStack(offHand);
        	is.setAmount(mainHand.getAmount());
        	if(debug){logDebug("FHS d=d2, so=so2, d!=D2 - return offhand");}
        	return is;
        }else if( !display.equals(display2) && SkullOwner.equals(SkullOwner2) && ((damage == damage2)||(damage != damage2))){
        	ItemStack is = new ItemStack(offHand);
        	is.setAmount(mainHand.getAmount());
        	if(debug){logDebug("FHS d!=d2, so=so2, d ignored - return offhand");}
        	return is;
        }else if( display.equals(display2) && SkullOwner.equals(SkullOwner2) && damage == damage2){
        	if(debug){logDebug("FHS d=d2, so=so2, d=d2 - return mainhand");}
        	return mainHand;
        }
        return null;
	}
    public ItemStack fixHeadNBT(String textureValue, String displayName, ArrayList<String> lore) {
    	//String textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MjQxNjZmN2NlODhhNTM3MTU4NzY2YTFjNTExZTMyMmE5M2E1ZTExZGJmMzBmYTZlODVlNzhkYTg2MWQ4In19fQ=="; // Pulled from the head link, scroll to the bottom and the "Other Value" field has this texture id.

    	ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1); // Creating the ItemStack, your input may vary.
    	NBTItem nbti = new NBTItem(head); // Creating the wrapper.

    	NBTCompound disp = nbti.addCompound("display");
    	disp.setString("Name", displayName); // Setting the name of the Item
    	if(lore.isEmpty()) {
    		if(getConfig().getBoolean("lore.show_plugin_name", true)){
				lore.add(ChatColor.AQUA + "MoreMobHeads");
			}
    	}
    	if(!lore.isEmpty()) {
	    	NBTList l = disp.getStringList("Lore");
	    	l.add(lore); // Adding a bit of lore.
    	}
    	
    	NBTCompound skull = nbti.addCompound("SkullOwner"); // Getting the compound, that way we can set the skin information
    	skull.setString("Name", displayName); // Owner's name
    	//skull.setString("Id", uuid);
    	// The UUID, note that skulls with the same UUID but different textures will misbehave and only one texture will load
    	// (They'll share it), if skulls have different UUIDs and same textures they won't stack. See UUID.randomUUID();

    	NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
    	texture.setString("Value",  textureValue);

    	head = nbti.getItem(); // Refresh the ItemStack
    	return head;
    }
    
    public ItemStack makeHead(String textureValue, String displayName, String uuid, @Nullable ArrayList<String> lore) {
    	ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1); // Creating the ItemStack, your input may vary.
    	NBTItem nbti = new NBTItem(head); // Creating the wrapper.

    	NBTCompound disp = nbti.addCompound("display");
    	disp.setString("Name", displayName); // Setting the name of the Item

    	if(lore.isEmpty()) {
    		if(getConfig().getBoolean("lore.show_plugin_name", true)){
				lore.add(ChatColor.AQUA + "MoreMobHeads");
			}
    	}
    	if(!lore.isEmpty()) {
	    	NBTList l = disp.getStringList("Lore");
	    	l.add(lore); // Adding a bit of lore.
    	}

    	NBTCompound skull = nbti.addCompound("SkullOwner"); // Getting the compound, that way we can set the skin information
    	skull.setString("Name", displayName); // Owner's name
    	skull.setString("Id", uuid);

    	NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
    	texture.setString("Value",  textureValue);

    	head = nbti.getItem(); // Refresh the ItemStack
    	return head;
    }
    
    public void configReload() { //TODO: configReload
    	oldconfig = new YamlConfiguration();
		log(Level.INFO, "Checking config file version...");
		try {
			oldconfig.load(new File(getDataFolder() + "" + File.separatorChar + "config.yml"));
		} catch (Exception e2) {
			logWarn("Could not load config.yml");
			stacktraceInfo();
			e2.printStackTrace();
		}
		String checkconfigversion = oldconfig.getString("version", "1.0.0");
		if(checkconfigversion != null){
			if(!checkconfigversion.equalsIgnoreCase("1.0.19")){
				try {
					copyFile_Java7(getDataFolder() + "" + File.separatorChar + "config.yml",getDataFolder() + "" + File.separatorChar + "old_config.yml");
				} catch (IOException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
				saveResource("config.yml", true);
				
				try {
					config.load(new File(getDataFolder(), "config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("Could not load config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
				try {
					oldconfig.load(new File(getDataFolder(), "old_config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("Could not load old_config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
				config.set("auto_update_check", oldconfig.get("auto_update_check", true));
				config.set("debug", oldconfig.get("debug", false));
				config.set("lang", oldconfig.get("lang", "en_US"));
				config.set("console.colorful_console", oldconfig.get("colorful_console", true));
				config.set("vanilla_heads.creepers", oldconfig.get("vanilla_heads.creepers", false));
				config.set("vanilla_heads.ender_dragon", oldconfig.get("vanilla_heads.ender_dragon", false));
				config.set("vanilla_heads.skeleton", oldconfig.get("vanilla_heads.skeleton", false));
				config.set("vanilla_heads.wither_skeleton", oldconfig.get("vanilla_heads.wither_skeleton", false));
				config.set("vanilla_heads.zombie", oldconfig.get("vanilla_heads.zombie", false));
				config.set("world.whitelist", oldconfig.get("world.whitelist", ""));
				config.set("world.blacklist", oldconfig.get("world.blacklist", ""));
				config.set("mob.whitelist", oldconfig.get("mob.whitelist", ""));
				config.set("mob.blacklist", oldconfig.get("mob.blacklist", ""));
				config.set("mob.nametag", oldconfig.get("mob.nametag", false));
				config.set("lore.show_killer", oldconfig.get("lore.show_killer", true));
				config.set("lore.show_plugin_name", oldconfig.get("lore.show_plugin_name", true));
				config.set("wandering_trades.custom_wandering_trader", oldconfig.get("wandering_trades.custom_wandering_trader", true));
				config.set("wandering_trades.player_heads.enabled", oldconfig.get("wandering_trades.player_heads.enabled", true));
				config.set("wandering_trades.player_heads.min", oldconfig.get("wandering_trades.player_heads.min", 0));
				config.set("wandering_trades.player_heads.max", oldconfig.get("wandering_trades.player_heads.max", 5));
				config.set("wandering_trades.block_heads.enabled", oldconfig.get("wandering_trades.block_heads.enabled", true));
				config.set("wandering_trades.block_heads.pre_116.min", oldconfig.get("wandering_trader_min_block_heads", 0));
				config.set("wandering_trades.block_heads.pre_116.max", oldconfig.get("wandering_trader_max_block_heads", 5));
				config.set("wandering_trades.block_heads.is_116.min", oldconfig.get("wandering_trader_min_block_heads", 0));
				config.set("wandering_trades.block_heads.is_116.max", oldconfig.get("wandering_trader_max_block_heads", 5));
				config.set("wandering_trades.block_heads.is_117.min", oldconfig.get("wandering_trader_min_block_heads", 0));
				config.set("wandering_trades.block_heads.is_117.max", oldconfig.get("wandering_trader_max_block_heads", 5));
				
				config.set("wandering_trades.custom_trades.enabled", oldconfig.get("wandering_trades.custom_trades.enabled", false));
				config.set("wandering_trades.custom_trades.min", oldconfig.get("wandering_trades.custom_trades.min", 0));
				config.set("wandering_trades.custom_trades.max", oldconfig.get("wandering_trades.custom_trades.max", 5));
				config.set("apply_looting", oldconfig.get("apply_looting", true));
				config.set("whitelist.enforce", oldconfig.get("whitelist.enforce", true));
				config.set("whitelist.player_head_whitelist", oldconfig.get("whitelist.player_head_whitelist", "names_go_here"));
				config.set("blacklist.enforce", oldconfig.get("enforce_blacklist", true));
				config.set("blacklist.player_head_blacklist", oldconfig.get("blacklist.player_head_blacklist", "names_go_here"));
				//config.set("", oldconfig.get("", true));
				
				try {
					config.save(new File(getDataFolder(), "config.yml"));
				} catch (IOException e) {
					logWarn("Could not save old settings to config.yml");
					stacktraceInfo();
					e.printStackTrace();
				}
				saveResource("chance_config.yml", true);
				log(Level.INFO, "config.yml Updated! old config saved as old_config.yml");
				log(Level.INFO, "chance_config.yml saved.");
			}else{
				try {
					config.load(new File(getDataFolder(), "config.yml"));
				} catch (IOException | InvalidConfigurationException e1) {
					logWarn("Could not load config.yml");
					stacktraceInfo();
					e1.printStackTrace();
				}
			}
			oldconfig = null;
		}
		log(Level.INFO, "Loading config file...");
		try {
			getConfig().load(new File(getDataFolder(), "config.yml"));
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		try {
			config.load(new File(getDataFolder(), "config.yml"));
		} catch (IOException | InvalidConfigurationException e1) {
			logWarn("Could not load config.yml");
			stacktraceInfo();
			e1.printStackTrace();
		}
		
		world_whitelist = config.getString("world.whitelist", "");
		world_blacklist = config.getString("world.blacklist", "");
		mob_whitelist = config.getString("mob.whitelist", "");
		mob_blacklist = config.getString("mob.blacklist", "");
		colorful_console = getConfig().getBoolean("console.colorful_console", true);
		
		//if(getConfig().getBoolean("wandering_trades.custom_wandering_trader", true)){
			/** Trader heads load */
			playerFile = new File(getDataFolder() + "" + File.separatorChar + "player_heads.yml");//\
			if(debug){logDebug("player_heads=" + playerFile.getPath());}
			if(!playerFile.exists()){																	// checks if the yaml does not exist
				saveResource("player_heads.yml", true);
				log(Level.INFO, "player_heads.yml not found! copied player_heads.yml to " + getDataFolder() + "");
				//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
			}

		log(Level.INFO, "Loading player_heads file...");
				playerHeads = new YamlConfiguration();
				try {
					playerHeads.load(playerFile);
				} catch (IOException | InvalidConfigurationException e) {
					stacktraceInfo();
					e.printStackTrace();
				}
		log(Level.INFO, "" + playerHeads.getInt("players.number") + " player_heads Loaded...");
		log("MC Version=" + getMCVersion());
				if(!getMCVersion().startsWith("1.16")&&!getMCVersion().startsWith("1.17")&&!getMCVersion().startsWith("1.18")){
					blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads.yml");//\
					if(debug){logDebug("block_heads=" + blockFile.getPath());}
					if(!blockFile.exists()){																	// checks if the yaml does not exist
						saveResource("block_heads.yml", true);
						log(Level.INFO, "block_heads.yml not found! copied block_heads.yml to " + getDataFolder() + "");
						//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
					}
				}
				blockFile116 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
				blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16_2.yml");
				blockFile117 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17_3.yml");
				blockFile119 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_19.yml");

				
				if(getMCVersion().startsWith("1.16")){
					if(debug){logDebug("block_heads_1_16=" + blockFile116.getPath());}
					if(debug){logDebug("block_heads_1_16_2=" + blockFile1162.getPath());}
					if(!blockFile116.exists()){
						saveResource("block_heads_1_16.yml", true);
						log(Level.INFO, "block_heads_1_16.yml not found! copied block_heads_1_16.yml to " + getDataFolder() + "");
					}
					if(!blockFile1162.exists()){
						saveResource("block_heads_1_16_2.yml", true);
						log(Level.INFO, "block_heads_1_16_2.yml not found! copied block_heads_1_16_2.yml to " + getDataFolder() + "");
					}
					blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_16.yml");
				log(Level.INFO, "Loading block_heads_1_16 files...");
				}else if( getMCVersion().startsWith("1.17") || getMCVersion().startsWith("1.18") || getMCVersion().startsWith("1.19") ){
					if(debug){logDebug("block_heads_1_17=" + blockFile116.getPath());}
					if(debug){logDebug("block_heads_1_17_2=" + blockFile1162.getPath());}
					if(!blockFile116.exists()){
						saveResource("block_heads_1_17.yml", true);
						log(Level.INFO, "block_heads_1_17.yml not found! copied block_heads_1_17.yml to " + getDataFolder() + "");
					}
					if(!blockFile1162.exists()){
						saveResource("block_heads_1_17_2.yml", true);
						log(Level.INFO, "block_heads_1_17_2.yml not found! copied block_heads_1_17_2.yml to " + getDataFolder() + "");
					}
					if(!blockFile117.exists()){
						saveResource("block_heads_1_17_3.yml", true);
						log(Level.INFO, "block_heads_1_17_3.yml not found! copied block_heads_1_17_3.yml to " + getDataFolder() + "");
					}
					if(!blockFile119.exists()){
						saveResource("block_heads_1_19.yml", true);
						log(Level.INFO, "block_heads_1_19.yml not found! copied block_heads_1_19.yml to " + getDataFolder() + "");
					}
					blockFile = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17.yml");
					blockFile1162 = new File(getDataFolder() + "" + File.separatorChar + "block_heads_1_17_2.yml");
				log(Level.INFO, "Loading block_heads_1_17 files...");
				}else{
				}
				log(Level.INFO, "Loading block_heads file...");
			
				blockHeads = new YamlConfiguration();
				try {
					blockHeads.load(blockFile);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
				
				blockHeads2 = new YamlConfiguration();
				try {
					blockHeads2.load(blockFile1162);
				} catch (IOException | InvalidConfigurationException e1) {
					stacktraceInfo();
					e1.printStackTrace();
				}
				if(Double.parseDouble(getMCVersion().substring(0, 4)) >= 1.17) {
					blockHeads3 = new YamlConfiguration();
					try {
						blockHeads3.load(blockFile117);
					} catch (IOException | InvalidConfigurationException e1) {
						stacktraceInfo();
						e1.printStackTrace();
					}
					
					blockHeads4 = new YamlConfiguration();
					try {
						blockHeads4.load(blockFile119);
					} catch (IOException | InvalidConfigurationException e1) {
						stacktraceInfo();
						e1.printStackTrace();
					}
				}

			/** Custom Trades load */
			customFile = new File(getDataFolder() + "" + File.separatorChar + "custom_trades.yml");//\
			if(debug){logDebug("customFile=" + customFile.getPath());}
			if(!customFile.exists()){																	// checks if the yaml does not exist
				saveResource("custom_trades.yml", true);
				log(Level.INFO, "custom_trades.yml not found! copied custom_trades.yml to " + getDataFolder() + "");
				//ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
			}
		log(Level.INFO, "Loading custom_trades file...");
			traderCustom = new YamlConfiguration();
			try {
				traderCustom.load(customFile);
			} catch (IOException | InvalidConfigurationException e) {
				stacktraceInfo();
				e.printStackTrace();
			}

		log(Level.INFO, "Loading chance_config file...");
		chanceFile = new File(getDataFolder() + "" + File.separatorChar + "chance_config.yml");
		try {
			chanceConfig.load(chanceFile);
		} catch (IOException | InvalidConfigurationException e) {
			stacktraceInfo();
			e.printStackTrace();
		}
		//showkiller = getConfig().getBoolean("lore.show_killer", true);
		//showpluginname = getConfig().getBoolean("lore.show_plugin_name", true);
		debug = getConfig().getBoolean("debug", false);
		daLang = getConfig().getString("lang", "en_US");
    }
    
    public String getTextureFromEntity(LivingEntity entity) {
    	String name = entity.getName().toUpperCase();
    	switch (name) {
			case "CREEPER":
				Creeper creeper = (Creeper) entity;
				double cchance = chanceConfig.getDouble("chance_percent.creeper", defpercent);
				if(creeper.isPowered()) {
					name = "CREEPER_CHARGED";
					cchance = 1.00;
				}
				if(DropIt2(cchance)){
	 				return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "ZOMBIE":
				if(DropIt2(chanceConfig.getDouble("chance_percent.zombie", defpercent))){
 					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "SKELETON":
				if(DropIt2(chanceConfig.getDouble("chance_percent.skeleton", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "WITHER_SKELETON":
				if(DropIt2(chanceConfig.getDouble("chance_percent.wither_skeleton", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			case "ENDER_DRAGON":
				if(DropIt2(chanceConfig.getDouble("chance_percent.ender_dragon", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			/**case "TROPICAL_FISH":
				TropicalFish daFish = (TropicalFish) entity;
				DyeColor daFishBody = daFish.getBodyColor();
				DyeColor daFishPatternColor = daFish.getPatternColor();
				Pattern	daFishType = daFish.getPattern();
				log("bodycolor=" + daFishBody.toString() + "\nPatternColor=" + daFishPatternColor.toString() + "\nPattern=" + daFishType.toString());
				//TropicalFishHeads daFishEnum = TropicalFishHeads.getIfPresent(name);
				
				if(DropIt2(getConfig().getDouble(name + "_" +	daFishType, defpercent))){
					entity.getWorld().DropIt2emNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" +	daFishType).getTexture(), MobHeads.valueOf(name + "_" +	daFishType).getName(), entity.getKiller()));
				}
				if(debug){logDebug("Skeleton Head Dropped");}
				break;*/
			case "WITHER":
				//Wither wither = (Wither) event.getEntity();
				int random = randomBetween(1, 4);
				if(DropIt2(chanceConfig.getDouble("chance_percent.wither", defpercent))){
					return MobHeads.valueOf(name + "_" + random).getTexture().toString();
				}
				break;
			case "WOLF":
				Wolf wolf = (Wolf) entity;
				if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
 				if(wolf.isAngry()){
 					return MobHeads.valueOf(name + "_ANGRY").getTexture().toString();
 				}else{
 					return MobHeads.valueOf(name).getTexture().toString();
 				}
				}
				break;
			case "FOX":
				Fox dafox = (Fox) entity;
				String dafoxtype = dafox.getFoxType().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.fox." + dafoxtype.toString().toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + dafoxtype).getTexture().toString();
				}
				
				break;
			case "CAT":
				Cat dacat = (Cat) entity;
				String dacattype = dacat.getCatType().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.cat." + dacattype.toLowerCase(), defpercent))){
					return CatHeads.valueOf(dacattype).getTexture().toString();
				}
				break;
			case "OCELOT":
				if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				
				break;
			case "BEE":
				Bee daBee = (Bee) entity;
				int daAnger = daBee.getAnger();
				boolean daNectar = daBee.hasNectar();
 				if(daAnger >= 1&&daNectar == true){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry_pollinated", defpercent))){
 						return MobHeads.valueOf("BEE_ANGRY_POLLINATED").getTexture().toString();
 					}
 				}else if(daAnger >= 1&&daNectar == false){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry", defpercent))){
 						return MobHeads.valueOf("BEE_ANGRY").getTexture().toString();
 					}
 				}else if(daAnger == 0&&daNectar == true){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.pollinated", defpercent))){
 						return MobHeads.valueOf("BEE_POLLINATED").getTexture().toString();
 					}
 				}else if(daAnger == 0&&daNectar == false){
 					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.chance_percent", defpercent))){
 						return MobHeads.valueOf("BEE").getTexture().toString();
 					}
 				}
				break;
			case "LLAMA":
				Llama daLlama = (Llama) entity;
				String daLlamaColor = daLlama.getColor().toString();
				//log(name + "_" + daLlamaColor);
				if(DropIt2(chanceConfig.getDouble("chance_percent.llama." + daLlamaColor.toLowerCase(), defpercent))){		
					return LlamaHeads.valueOf(name + "_" + daLlamaColor).getTexture().toString();
				}
				break;
			case "HORSE":
				Horse daHorse = (Horse) entity;
				String daHorseColor = daHorse.getColor().toString();
						//.replace("g", "G").replace("wh", "Wh").replace("_", " ") + " Horse Head";
				if(DropIt2(chanceConfig.getDouble("chance_percent.horse." + daHorseColor.toLowerCase(), defpercent))){
					return HorseHeads.valueOf(name + "_" + daHorseColor).getTexture().toString();
				}
				break;
			case "MOOSHROOM":
				name = "MUSHROOM_COW";
			case "MUSHROOM_COW":
				MushroomCow daMushroom = (MushroomCow) entity;
				String daCowVariant = daMushroom.getVariant().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.mushroom_cow." + daCowVariant.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + daCowVariant).getTexture().toString();
				}
				break;
			case "PANDA":
				Panda daPanda = (Panda) entity;
				String daPandaGene = daPanda.getMainGene().toString();
				String daPandaName = daPandaGene.toLowerCase().replace("br", "Br").replace("ag", "Ag").replace("la", "La")
						.replace("no", "No").replace("p", "P").replace("we", "We").replace("wo", "Wo") + " Panda Head";
				if(daPandaGene.equalsIgnoreCase("normal")){daPandaName.replace("normal ", "");}
				if(DropIt2(chanceConfig.getDouble("chance_percent.panda." + daPandaGene.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + daPandaGene).getTexture().toString();
				}
				break;
			case "PARROT":
				Parrot daParrot = (Parrot) entity;
				String daParrotVariant = daParrot.getVariant().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.parrot." + daParrotVariant.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name + "_" + daParrotVariant).getTexture().toString();
				}
				break;
			case "RABBIT":
				String daRabbitType;
				Rabbit daRabbit = (Rabbit) entity;
				daRabbitType = daRabbit.getRabbitType().toString();
				if(daRabbit.getCustomName() != null){
					if(daRabbit.getCustomName().contains("Toast")){
 					daRabbitType = "Toast";
 				}
				}
				if(DropIt2(chanceConfig.getDouble("chance_percent.rabbit." + daRabbitType.toLowerCase(), defpercent))){
					return RabbitHeads.valueOf(name + "_" + daRabbitType).getTexture().toString();
				}
				break;
			case "VILLAGER":
				Villager daVillager = (Villager) entity; // Location jobsite = daVillager.getMemory(MemoryKey.JOB_SITE);
				String daVillagerType = daVillager.getVillagerType().toString();
				String daVillagerProfession = daVillager.getProfession().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.villager." + daVillagerType.toLowerCase() + "." + daVillagerProfession.toLowerCase(), defpercent))){
					return VillagerHeads.valueOf(name + "_" + daVillagerProfession + "_" + daVillagerType).getTexture().toString();
				}
				break;
			case "ZOMBIE_VILLAGER":
				ZombieVillager daZombieVillager = (ZombieVillager) entity;
				String daZombieVillagerProfession = daZombieVillager.getVillagerProfession().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.zombie_villager", defpercent))){
					return ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession).getTexture().toString();
				}
				break;
			case "SHEEP":
				Sheep daSheep = (Sheep) entity;
				String daSheepColor = daSheep.getColor().toString();
				
				if(daSheep.getCustomName() != null){
 				if(daSheep.getCustomName().contains("jeb_")){
 					daSheepColor = "jeb_";
 				}else{
 					daSheepColor = daSheep.getColor().toString();
 				}
				}
				if(DropIt2(chanceConfig.getDouble("chance_percent.sheep." + daSheepColor.toLowerCase(), defpercent))){
					return SheepHeads.valueOf(name + "_" + daSheepColor).getTexture().toString();
				}
				break;
			/**case "STRIDER":
				Strider strider = (Strider) entity;
				
				break;*/
			case "TRADER_LLAMA":
				TraderLlama daTraderLlama = (TraderLlama) entity;
				String daTraderLlamaColor = daTraderLlama.getColor().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.trader_llama." + daTraderLlamaColor.toLowerCase(), defpercent))){
					return LlamaHeads.valueOf(name + "_" + daTraderLlamaColor).getTexture().toString();
				}
				break;
			case "AXOLOTL":
				Axolotl daAxolotl = (Axolotl) entity;
				String daAxolotlVariant = daAxolotl.getVariant().toString();
				if(DropIt2(chanceConfig.getDouble("chance_percent.axolotl." + daAxolotlVariant.toLowerCase(), defpercent))){
					return MobHeads117.valueOf(name + "_" + daAxolotlVariant).getTexture().toString();
				}
				break;
			case "GOAT":
				Goat daGoat = (Goat) entity;
				String daGoatVariant;
				if(daGoat.isScreaming()) {
					// Giving screaming goat head
					daGoatVariant = "SCREAMING";
				}else {
					// give goat head
					daGoatVariant = "NORMAL";
				}
				if(DropIt2(chanceConfig.getDouble("chance_percent.goat." + daGoatVariant.toLowerCase(), defpercent))){
					return MobHeads117.valueOf(name + "_" + daGoatVariant).getTexture().toString();
				}
				break;
			default:
				//makeSkull(MobHeads.valueOf(name).getTexture(), name);
				if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
				}
				break;
			}
		return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI3YWY5ZTQ0MTEyMTdjN2RlOWM2MGFjYmQzYzNmZDY1MTk3ODMzMzJhMWIzYmM1NmZiZmNlOTA3MjFlZjM1In19fQ==";
    }
    
    public String getTexturefromEntityType(EntityType entityType, boolean randef) {
    	String name = entityType.toString();
    	switch (name) {
    	case "CREEPER":
			double cchance = chanceConfig.getDouble("chance_percent.creeper", defpercent);
			if(randef) {
				name = "CREEPER_CHARGED";
				cchance = 1.00;
			}
			if(DropIt2(cchance)){
 				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "ZOMBIE":
			if(DropIt2(chanceConfig.getDouble("chance_percent.zombie", defpercent))){
					return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "SKELETON":
			if(DropIt2(chanceConfig.getDouble("chance_percent.skeleton", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "WITHER_SKELETON":
			if(DropIt2(chanceConfig.getDouble("chance_percent.wither_skeleton", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "ENDER_DRAGON":
			if(DropIt2(chanceConfig.getDouble("chance_percent.ender_dragon", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;//*/
		/**case "TROPICAL_FISH":
			TropicalFish daFish = (TropicalFish) entity;
			DyeColor daFishBody = daFish.getBodyColor();
			DyeColor daFishPatternColor = daFish.getPatternColor();
			Pattern	daFishType = daFish.getPattern();
			log("bodycolor=" + daFishBody.toString() + "\nPatternColor=" + daFishPatternColor.toString() + "\nPattern=" + daFishType.toString());
			//TropicalFishHeads daFishEnum = TropicalFishHeads.getIfPresent(name);
			
			if(DropIt2(getConfig().getDouble(name + "_" +	daFishType, defpercent))){
				entity.getWorld().DropIt2emNaturally(entity.getLocation(), makeSkull(MobHeads.valueOf(name + "_" +	daFishType).getTexture(), MobHeads.valueOf(name + "_" +	daFishType).getName(), entity.getKiller()));
			}
			if(debug){logDebug("Skeleton Head Dropped");}
			break;*/
		case "WITHER":
			//Wither wither = (Wither) event.getEntity();
			int random = randomBetween(1, 4);
			if(randef) {
				name = name.concat("_" + random);
			}else {
				name = name.concat("_1");
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.wither", defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
		case "WOLF":
			String[] dawolftype = {"","_ANGRY"};
			int randomwolf;
			if(randef) {
				randomwolf = randomBetween(0, 1);
			}else {
				randomwolf = 0;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + dawolftype[randomwolf]).getTexture().toString();
			}
			break;
		case "FOX":
			String[] dafoxtype = {"RED","SNOW"};
			int randomfox;
			if(randef) {
				randomfox = randomBetween(0, 1);
			}else {
				randomfox = 0;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.fox." + dafoxtype[randomfox].toString().toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + dafoxtype[randomfox]).getTexture().toString();
			}
			
			break;
		case "CAT":
			int randomcat;
			//String dacattype = dacat.getCatType().toString();
			String[] dacattype = {"ALL_BLACK","BLACK","BRITISH_SHORTHAIR","CALICO","JELLIE","PERSIAN","RAGDOLL","RED","SIAMESE","TABBY","WHITE"};
			if(randef) {
				randomcat = randomBetween(0, 10);
			}else {
				randomcat = 10;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.cat." + dacattype[randomcat].toLowerCase(), defpercent))){
				return CatHeads.valueOf(dacattype[randomcat]).getTexture().toString();
			}
			break;
		case "OCELOT":
			if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			
			break;
		case "BEE":
			int randombee;
			if(randef) {
				randombee = randomBetween(1, 4);
			}else {
				randombee = 1;
			}
				if(randombee == 4){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry_pollinated", defpercent))){
						return MobHeads.valueOf("BEE_ANGRY_POLLINATED").getTexture().toString();
					}
				}else if(randombee == 3){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.angry", defpercent))){
						return MobHeads.valueOf("BEE_ANGRY").getTexture().toString();
					}
				}else if(randombee == 2){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.pollinated", defpercent))){
						return MobHeads.valueOf("BEE_POLLINATED").getTexture().toString();
					}
				}else if(randombee == 1){
					if(DropIt2(chanceConfig.getDouble("chance_percent.bee.chance_percent", defpercent))){
						return MobHeads.valueOf("BEE").getTexture().toString();
					}
				}
			break;
		case "LLAMA":
			String[] daLlamaColor = {"BROWN","CREAMY","GRAY","WHITE"};
			int randomllama;
			if(randef) {
				randomllama = randomBetween(0, 3);
			}else {
				randomllama = 4;
			}
			//log(name + "_" + daLlamaColor);
			if(DropIt2(chanceConfig.getDouble("chance_percent.llama." + daLlamaColor[randomllama].toLowerCase(), defpercent))){		
				return LlamaHeads.valueOf(name + "_" + daLlamaColor[randomllama]).getTexture().toString();
			}
			break;
		case "TRADER_LLAMA":
			String[] daTraderLlamaColor = {"BROWN","CREAMY","GRAY","WHITE"};
			int randomtllama;
			if(randef) {
				randomtllama = randomBetween(0, 3);
			}else {
				randomtllama = 4;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.trader_llama." + daTraderLlamaColor[randomtllama].toLowerCase(), defpercent))){
				return LlamaHeads.valueOf(name + "_" + daTraderLlamaColor[randomtllama]).getTexture().toString();
			}
			break;
		case "HORSE":
			String[] daHorseColor = {"BLACK","BROWN","CHESTNUT","CREAMY","DARK_BROWN","GRAY","WHITE"};
			int randomhorse;
			if(randef) {
				randomhorse = randomBetween(0, 6);
			}else {
				randomhorse = 1;
			}
					//.replace("g", "G").replace("wh", "Wh").replace("_", " ") + " Horse Head";
			if(DropIt2(chanceConfig.getDouble("chance_percent.horse." + daHorseColor[randomhorse].toLowerCase(), defpercent))){
				return HorseHeads.valueOf(name + "_" + daHorseColor).getTexture().toString();
			}
			break;
		case "MOOSHROOM":
			 	name = "MUSHROOM_COW";
		case "MUSHROOM_COW":
			String[] daCowVariant = {"BROWN","RED"};
			int randomcow;
			if(randef) {
				randomcow = randomBetween(0, 6);
			}else {
				randomcow = 1;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.mushroom_cow." + daCowVariant[randomcow].toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + daCowVariant[randomcow]).getTexture().toString();
			}
			break;
		case "PANDA":
			String[] daPandaGene = {"AGRESSIVE","BROWN","LAZY","NORMAL","PLAYFUL","WEAK","WORRIED"};
			int randompan;
			if(randef) {
				randompan = randomBetween(0, 6);
			}else {
				randompan = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.panda." + daPandaGene[randompan].toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + daPandaGene[randompan]).getTexture().toString();
			}
			break;
		case "PARROT":
			String[] daParrotVariant = {"BLUE","CYAN","GRAY","GREEN","RED"};
			int randompar;
			if(randef) {
				randompar = randomBetween(0, 4);
			}else {
				randompar = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.parrot." + daParrotVariant[randompar].toLowerCase(), defpercent))){
				return MobHeads.valueOf(name + "_" + daParrotVariant[randompar]).getTexture().toString();
			}
			break;
		case "RABBIT":
			String[] daRabbitType = {"BLACK","BLACK_AND_WHITE","BROWN","GOLD","SALT_AND_PEPPER","THE_KILLER_BUNNY","WHITE","Toast"};
			int randomrab;
			if(randef) {
				randomrab = randomBetween(0, 7);
			}else {
				randomrab = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.rabbit." + daRabbitType[randomrab].toLowerCase(), defpercent))){
				return RabbitHeads.valueOf(name + "_" + daRabbitType[randomrab]).getTexture().toString();
			}
			break;
		case "VILLAGER":
			String[] daVillagerType = {"DESERT","JUNGLE","PLAINS","SAVANNA","SNOW","SWAMP","TAIGA"};
			String[] daVillagerProfession = {"ARMORER","BUTCHER","CARTOGRAPHER","CLERIC","FARMER","FISHERMAN","FLETCHER"
					,"LEATHERWORKER","LIBRARIAN","MASON","NITWIT","NONE","SHEPHERD","TOOLSMITH","WEAPONSMITH"};
			int randomviltyp;
			int randomvilpro;
			if(randef) {
				randomviltyp = randomBetween(0, 6);
				randomvilpro = randomBetween(0, 14);
			}else {
				randomviltyp = 2;
				randomvilpro = 11;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.villager." + daVillagerType[randomviltyp].toLowerCase() + "." + 
					daVillagerProfession[randomvilpro].toLowerCase(), defpercent))){
				return VillagerHeads.valueOf(name + "_" + daVillagerProfession[randomvilpro] + "_" + daVillagerType[randomviltyp]).getTexture().toString();
			}
			break;
		case "ZOMBIE_VILLAGER":
			String[] daZombieVillagerProfession = {"ARMORER","BUTCHER","CARTOGRAPHER","CLERIC","FARMER","FISHERMAN","FLETCHER"
					,"LEATHERWORKER","LIBRARIAN","MASON","NITWIT","NONE","SHEPHERD","TOOLSMITH","WEAPONSMITH"};
			int randomzvpro;
			if(randef) {
				randomzvpro = randomBetween(0, 14);
			}else {
				randomzvpro = 11;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.zombie_villager", defpercent))){
				return ZombieVillagerHeads.valueOf(name + "_" + daZombieVillagerProfession[randomzvpro]).getTexture().toString();
			}
			break;
		case "SHEEP":
			String[] daSheepColor = {"BLACK","BLUE","BROWN","CYAN","GRAY","GREEN","jeb_","LIGHT_BLUE","LIGHT_GRAY","LIME","MAGENTA","ORANGE"
					,"PINK","PURPLE","RED","WHITE","YELLOW"};
			int randomshe;
			if(randef) {
				randomshe = randomBetween(0, 16);
			}else {
				randomshe = 6;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.sheep." + daSheepColor[randomshe].toLowerCase(), defpercent))){
				return SheepHeads.valueOf(name + "_" + daSheepColor[randomshe]).getTexture().toString();
			}
			break;//*/
		/**case "STRIDER":
			Strider strider = (Strider) entity;
			
			break;*/
		case "AXOLOTL":
			String[] daAxolotlVariant = {"BLUE","CYAN","GOLD","LUCY","WILD"};
			int randomaxl;
			if(randef) {
				randomaxl = randomBetween(0, 4);
			}else {
				randomaxl = 3;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.axolotl." + daAxolotlVariant[randomaxl].toLowerCase(), defpercent))){
				return MobHeads117.valueOf(name + "_" + daAxolotlVariant[randomaxl]).getTexture().toString();
			}
			break;
		case "GOAT":
			String[] daGoatVariant = {"NORMAL","SCREAMING"};
			int randomgoat;
			if(randef) {
				randomgoat = randomBetween(0, 1);
			}else {
				randomgoat = 0;
			}
			if(DropIt2(chanceConfig.getDouble("chance_percent.goat." + daGoatVariant[randomgoat].toLowerCase(), defpercent))){
				return MobHeads117.valueOf(name + "_" + daGoatVariant[randomgoat]).getTexture().toString();
			}
			break;
		default:
			//makeSkull(MobHeads.valueOf(name).getTexture(), name);
			if(DropIt2(chanceConfig.getDouble("chance_percent." + name.toLowerCase(), defpercent))){
				return MobHeads.valueOf(name).getTexture().toString();
			}
			break;
    	
    	}
    	return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI3YWY5ZTQ0MTEyMTdjN2RlOWM2MGFjYmQzYzNmZDY1MTk3ODMzMzJhMWIzYmM1NmZiZmNlOTA3MjFlZjM1In19fQ==";
    }//*/
    
    public boolean chance25oftrue() {
    	//For 25% chance of true
    	return (random.nextInt(4) == 0) ? true : false;
    }
    public void consoleLog(String string) {
    	if(!silent_console) {
    		log(string);
    	}
    }
}