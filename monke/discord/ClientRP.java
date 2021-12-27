package monke.discord;

public class ClientRP
{
    private static final ClientRP INSTANCE = new ClientRP();
    public static final ClientRP getInstance()
    {
        return INSTANCE;
    }

    private DiscordRP discordRP = new DiscordRP();

    /*public void init()
    {
       discordRP.startTask();
    }*/

    public void init()
    {
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
