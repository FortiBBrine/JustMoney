package me.fortibrine.justmoney.commands;

import me.fortibrine.justmoney.JustMoney;
import me.fortibrine.justmoney.utils.BalanceManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.function.Consumer;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = JustMoney.getMain().getConfig();

        if (!(sender instanceof Player)) {
            config.getStringList("not_player").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(plugin.getDescription().getPermissions().get(1))) {
            config.getStringList("balance.without_permission").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    player.sendMessage(s);
                }
            });
        }

        BigInteger amount = BalanceManager.getBalance(player.getName());

        config.getStringList("balance.get").forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                player.sendMessage(s
                        .replace("%amount", amount.toString())
                        .replace("%player", player.getName()));
            }
        });

        return true;
    }
}
