package com.booksaw.betterTeams.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.booksaw.betterTeams.Main;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.message.MessageManager;

public class ChatManagement implements Listener {

	private static PrefixType doPrefix;
	public final List<CommandSender> spy = new ArrayList<>();

	public static void enable() {
		doPrefix = PrefixType.getType(Main.plugin.getConfig().getString("prefix"));
	}

	@EventHandler
	public void spyQuit(PlayerQuitEvent e) {
		spy.remove(e.getPlayer());
	}

	enum PrefixType {
		NONE, NAME, TAG;

		public static PrefixType getType(String str) {
			str = str.toLowerCase().trim();
			switch (str) {
			case "name":
			case "true":
				return NAME;
			case "tag":
				return TAG;
			default:
				return NONE;
			}
		}

		public String getUpdatedFormat(Player p, String format, Team team) {
			switch (this) {
			case NAME:
				String syntax = MessageManager.getMessage(p, "prefixSyntax");
				return MessageManager.format(syntax, team.getDisplayName(), format);
			case TAG:
				syntax = MessageManager.getMessage(p, "prefixSyntax");
				return MessageManager.format(syntax, team.getColor() + team.getTag(), format);
			default:
				return format;
			}
		}

	}

}
