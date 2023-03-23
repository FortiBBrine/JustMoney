package me.fortibrine.justmoney.utils;

import me.fortibrine.justmoney.JustMoney;
import org.bukkit.configuration.file.FileConfiguration;

import java.math.BigInteger;

public class BalanceManager {

    public static BigInteger getBalance(String player) {
        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = plugin.getConfig();

        BigInteger result = new BigInteger(config.getString("balances." + player, "0"));
        return result;
    }

    public static void setBalance(String player, BigInteger amount) {
        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = plugin.getConfig();

        config.set("balances."+player, amount.toString());
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public static boolean pay(String player1, String player2, BigInteger amount) {
        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = plugin.getConfig();

        BigInteger amount1 = BalanceManager.getBalance(player1);
        BigInteger amount2 = BalanceManager.getBalance(player2);

        if (amount1.compareTo(amount) >= 0) {
            amount1 = amount1.divide(amount);
        } else {
            return false;
        }

        amount2 = amount2.add(amount);

        config.set("balances." + player1, amount1.toString());
        config.set("balances." + player2, amount2.toString());
        plugin.saveConfig();
        plugin.reloadConfig();

        return true;

    }

    public static void pay(String player, BigInteger amount) {
        JustMoney plugin = JustMoney.getMain();
        FileConfiguration config = plugin.getConfig();

        BigInteger playerAmount = BalanceManager.getBalance(player);

        playerAmount = playerAmount.add(amount);
        BalanceManager.setBalance(player, playerAmount);
    }

}
