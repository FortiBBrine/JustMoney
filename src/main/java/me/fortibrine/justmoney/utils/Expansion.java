package me.fortibrine.justmoney.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fortibrine.justmoney.JustMoney;
import org.bukkit.OfflinePlayer;

public class Expansion extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "IJustFortiLive";
    }

    @Override
    public String getIdentifier() {
        return "justmoney";
    }

    @Override
    public String getVersion() {
        return JustMoney.getMain().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if (params.equalsIgnoreCase("money")) {
            return BalanceManager.getBalance(player.getName()).toString();
        }

        return null;
    }
}
