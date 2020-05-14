package com.sebbaindustries.warps.commands.subs;

import com.sebbaindustries.warps.Core;
import com.sebbaindustries.warps.commands.creator.ICommand;
import com.sebbaindustries.warps.commands.permissions.IPermission;
import com.sebbaindustries.warps.message.IMessage;
import com.sebbaindustries.warps.utils.Color;
import com.sebbaindustries.warps.utils.Replace;
import com.sebbaindustries.warps.warp.SafetyCheck;
import com.sebbaindustries.warps.warp.Warp;
import com.sebbaindustries.warps.warp.WarpSettings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCreate extends ICommand {

    public WarpCreate() {
        super("create", "create [name] (SERVER/PLAYER)", 1);
        permissions().add(IPermission.ROOT, IPermission.COMMANDS, IPermission.CREATE);
        setPlayerOnly();
    }

    @Override
    public void execute(final @NotNull CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        final String name = args.length == 1 ? args[0] : player.getName();
        final Warp.Type type = args.length == 2 ? Warp.Type.valueOf(args[1]) : Warp.Type.PLAYER;

        if (WarpSettings.blacklistedWarpNames().contains(name) && !name.equalsIgnoreCase(player.getName())) {
            /*
            TODO: Blacklisted warp name - handle appropriately (by not creating the warp)
            @placeholder warpName = {warp-name}
             */
            player.sendMessage(Color.chat(Replace.replaceString(
                    Core.gCore.message.get(IMessage.BLACKLISTED_WARP_NAME)
                    , "{warp-name}", name)));
            return;
        }

        final Warp warp = new Warp(type, player, name);

        if (!SafetyCheck.isLocationSafe(warp.getLocation())) {
            /*
            TODO: add some fancy message
             */
            player.sendMessage(Color.chat("Unsafe location!"));
            return;
        }
        /*
         TODO: beautify the placeholder returns (environment capitalization, location toString)
         TODO: Add a safety check to ensure the created warps location is safe for teleportation
         @placeholder warpMame = {warp-name}
         @placeholder location = {warp-world}
         @placeholder world = {warp-environment}
          */
        if (Core.gCore.warpStorage.addWarp(warp)) {
            player.sendMessage(Color.chat(Replace.replaceString(
                    Core.gCore.message.get(IMessage.SUCCESSFULLY_CREATED_WARP)
                    , "{warp-name}", warp.getName()
                    , "{warp-location}", warp.getLocation().toString()
                    , "{warp-world}", String.valueOf(warp.getLocation().getWorld()))));
            return;
        }

        /*
        @placeholder warpName = {warp-name}
        @placeholder reason = {reason} - Reason for failure to create the warp - beautified
        */
        player.sendMessage(Color.chat(Replace.replaceString(Core.gCore.message.get(IMessage.FAILED_WARP_CREATION)
                , "{warp-name}", name
                , "{reason}", getReason())));
    }

    /*
    TODO: Add actual reason creation (dynamic)
     */
    private String getReason() {
        return "warp already exists!";
    }

}
