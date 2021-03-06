package pl.venixpll.system.command.impl;

import pl.venixpll.mc.bot.BotManager;
import pl.venixpll.mc.objects.Player;
import pl.venixpll.system.command.Command;
import pl.venixpll.utils.NetUtils;

public class CommandJoinBot extends Command {

    public CommandJoinBot() {
        super(",joinbot", "Connecting bots to server", "<host:port> <usernames> <amount> <delay> <ping> <proxies>");
    }

    @Override
    public void onExecute(String cmd, Player sender) throws Exception {
        final String[] args = cmd.split(" ");
        String host = args[1];
        int port = 25565;
        if(host.contains(":")){
            final String[] sp = host.split(":",2);
            host = sp[0];
            port = Integer.parseInt(sp[1]);
        }
        if(NetUtils.checkSocketConnection(host,port,500) == -1){
            final String[] resolved = NetUtils.getServerAddress(host);
            host = resolved[0];
            port = Integer.parseInt(resolved[1]);
            if(NetUtils.checkSocketConnection(host,port,500) == -1){
                sender.sendChatMessage("&cNo connection.");
                return;
            }
        }
        final String usernames = args[2];
        final int amount = Integer.parseInt(args[3]);
        final int delay = Integer.parseInt(args[4]);
        final boolean doPing = Boolean.parseBoolean(args[5]);
        final String proxy = args[6];
        sender.sendChatMessage("&aSending!");
        BotManager.connectSome(host,port,usernames,amount,delay,doPing,proxy,sender);
    }
}
