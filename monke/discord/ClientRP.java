package monke.discord;

import monke.ui.SplashProgress;

public class ClientRP
{
    private static final ClientRP INSTANCE = new ClientRP();
    public static final ClientRP getInstance()
    {
        return INSTANCE;
    }

    private DiscordRP discordRP = new DiscordRP();


    public void init()
    {
        SplashProgress.setProgress(1, "Client - Initializing Discord RP");
        discordRP.start();
    }

    public void shutdown()
    {
        discordRP.shutdown();

    }

    public DiscordRP getDiscordRP()
    {
        return discordRP;
    }
}
