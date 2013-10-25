package beop;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import beop.mainClass;

public class getEffect implements CommandExecutor {
	private mainClass plugin;
	public getEffect(mainClass plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String [] args) {
		String effect1 = args[0];
		Player player1 = (Player) sender;
		String player = player1.getName();
		if(cmd.getName().equalsIgnoreCase("potion") && effect1.length() != 0) {
			for (PotionEffect pe : player1.getActivePotionEffects()) {
		           
			    if (pe.getType() == PotionEffectType.getByName(effect1)) {
			    	player1.sendMessage("У Вас уже имеется данный эффект!");
			    	return false;
			    }
			}
			if (plugin.cfg.getString(effect1) == null) {
				player1.sendMessage("Данный эффект нельзя купить!");
				return false;
			}
			int time = plugin.cfg.getInt(effect1 +".time");
			int level = plugin.cfg.getInt(effect1 +".level");
			int price = plugin.cfg.getInt(effect1 +".price");
			EconomyResponse r = mainClass.econ.withdrawPlayer(player, price);
						if(r.transactionSuccess()) {
							String effect = effect1.toUpperCase();
							player1.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), time, level));
							player1.sendMessage("Вы получили эффект зелья!");
							return true;
						}
						else {
							player1.sendMessage("Произошла ошибка при снятии средств с Вашего счета!");
						}
			return false;
		}
		return false;
	}
}
