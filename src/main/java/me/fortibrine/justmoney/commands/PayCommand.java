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

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = plugin.getConfig();

        if (!(sender instanceof Player)) {
            config.getStringList("not_player").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });
            return true;
        }

        if (!sender.hasPermission(plugin.getDescription().getPermissions().get(2))) {
            config.getStringList("pay.without_permission").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });

            return true;
        }

        if (args.length < 2) {

            config.getStringList("pay.usage").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });

            return true;
        }

        BigInteger amount = new BigInteger(args[1]);
        Player player = (Player) sender;

        if (player.getName().equals(args[0])) {
            config.getStringList("pay.names_equals").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    player.sendMessage(s);
                }
            });
            return true;
        }

        if (BalanceManager.pay(player.getName(), args[0], amount)) {

            config.getStringList("pay.pay").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s
                            .replace("%amount", amount.toString())
                            .replace("%player", args[1]));
                }
            });
        } else {
            config.getStringList("pay.error").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });
        }

        return true;
    }
}
