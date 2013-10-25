package beop;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class mainClass extends JavaPlugin implements Listener {
	
	public static Economy econ = null;
	
	FileConfiguration cfg;
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		if (!setupEconomy() ) {
            getLogger().info("Не удалось подключиться к экономике!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		File file = new File(getDataFolder() + "/config.yml");
        cfg = getConfig();
        if(!file.exists())
        {
            cfg.addDefault("confusion.time", 500);
            cfg.addDefault("confusion.level", 1);
            cfg.addDefault("confusion.price", 10);
            cfg.options().copyDefaults(true);
            saveConfig();
        }
        getCommand("potion").setExecutor(new getEffect(this));
		getLogger().info("Enabled!");
	}
	@Override
	public void onDisable() {
		getLogger().info("Disabled!");
	}
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
}
