package me.fortibrine.justmoney;

import me.fortibrine.justmoney.commands.BalanceCommand;
import me.fortibrine.justmoney.commands.MainCommand;
import me.fortibrine.justmoney.commands.PayCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JustMoney extends JavaPlugin {

    private static JustMoney plugin;

    public static JustMoney getMain() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        File config = new File(this.getDataFolder()+File.separator+"config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        this.getCommand("justmoney").setExecutor(new MainCommand());
        this.getCommand("balance").setExecutor(new BalanceCommand());
        this.getCommand("pay").setExecutor(new PayCommand());
    }

}
