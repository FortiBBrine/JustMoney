package me.fortibrine.justmoney.commands;

import me.fortibrine.justmoney.JustMoney;
import me.fortibrine.justmoney.utils.BalanceManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.math.BigInteger;
import java.util.function.Consumer;

public class MainCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = plugin.getConfig();

        if (!sender.hasPermission(plugin.getDescription().getPermissions().get(0))) {
            config.getStringList("maincommand.without_permission").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });

            return true;
        }

        if (args.length < 2) {

            config.getStringList("maincommand.usage").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s);
                }
            });

            return true;
        }

        if (args[0].equals("pay") && args.length >= 3) {
            BigInteger amount = new BigInteger(args[2]);

            BalanceManager.pay(args[1], amount);

            config.getStringList("maincommand.pay").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s
                            .replace("%amount", amount.toString())
                            .replace("%player", args[1]));
                }
            });

            return true;
        }

        if (args[0].equals("set") && args.length >= 3) {
            BigInteger amount = new BigInteger(args[2]);

            BalanceManager.setBalance(args[1], amount);

            config.getStringList("maincommand.set").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s
                            .replace("%amount", amount.toString())
                            .replace("%player", args[1]));
                }
            });

            return true;
        }

        if (args[0].equals("get")) {
            BigInteger balance = BalanceManager.getBalance(args[1]);

            config.getStringList("maincommand.get").forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    sender.sendMessage(s
                            .replace("%amount", balance.toString())
                            .replace("%player", args[1]));
                }
            });

            return true;
        }

        config.getStringList("maincommand.usage").forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                sender.sendMessage(s);
            }
        });

        return true;
    }
}
