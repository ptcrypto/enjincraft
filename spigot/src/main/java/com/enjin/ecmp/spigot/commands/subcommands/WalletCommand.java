package com.enjin.ecmp.spigot.commands.subcommands;

import com.enjin.ecmp.spigot.BasePlugin;
import com.enjin.ecmp.spigot.player.PlayerManager;
import com.enjin.ecmp.spigot.player.EnjinCoinPlayer;
import com.enjin.ecmp.spigot.util.MessageUtils;
import com.enjin.ecmp.spigot.wallet.TokenWalletView;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WalletCommand {

    private BasePlugin plugin;

    public WalletCommand(BasePlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                PlayerManager playerManager = this.plugin.getBootstrap().getPlayerManager();
                EnjinCoinPlayer enjinCoinPlayer = playerManager.getPlayer(player.getUniqueId());

                if (!enjinCoinPlayer.isLinked()) {
                    TextComponent text = TextComponent.of("You have not linked a wallet to your account.").color(TextColor.RED);
                    MessageUtils.sendMessage(sender, text);
                    text = TextComponent.of("Please type '/enj link' to link your account to your Enjin Wallet.").color(TextColor.RED);
                    MessageUtils.sendMessage(sender, text);
                    return;
                }

                if (enjinCoinPlayer.getTokenWallet() == null) {
                    TextComponent text = TextComponent.of("Your wallet balances are loading, try again in a few seconds.")
                            .color(TextColor.RED);
                    MessageUtils.sendMessage(sender, text);
                    return;
                }

                if (enjinCoinPlayer.isIdentityLoaded()) {
                    // we have an identity, but the wallet has not been linked yet.
                    if (!enjinCoinPlayer.isLinked()) {
                        TextComponent text = TextComponent.of("You have not linked a wallet to your account.").color(TextColor.RED);
                        MessageUtils.sendMessage(sender, text);
                        text = TextComponent.of("Please type '/enj link' to link your account to your Enjin Wallet.").color(TextColor.RED);
                        MessageUtils.sendMessage(sender, text);
                        return;
                    }

//                    Inventory inventory = LegacyWalletInventory.create(plugin, player, tokens);
//                    player.openInventory(inventory);

                    TokenWalletView view = new TokenWalletView(plugin, enjinCoinPlayer);
                    view.open(player);
                } else {
                    TextComponent text = TextComponent.of("You have not linked a wallet to your account.")
                            .color(TextColor.RED);
                    MessageUtils.sendMessage(sender, text);
                }
            });
        } else {
            TextComponent text = TextComponent.of("Only players can use this command.")
                    .color(TextColor.RED);
            MessageUtils.sendMessage(sender, text);
        }
    }

    private void sendMsg(CommandSender sender, String msg) {
        TextComponent text = TextComponent.of(msg)
                .color(TextColor.RED);
        MessageUtils.sendMessage(sender, text);
    }

}