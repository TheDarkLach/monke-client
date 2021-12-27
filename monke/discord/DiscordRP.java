package monke.discord;

//924458669725339699
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP
{
    private boolean running = true;
    private long created = 0;

    public void start()
    {
        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback()
        {
            @Override
            public void apply(DiscordUser user)
            {
                System.out.println("Welcome " + user.username + "#" + user.discriminator);
                update("Booting up . . .","");
            }
        }).build();

        //wonder what would happen if i put scoobys id
        DiscordRPC.discordInitialize("924458669725339699", handlers, true);

        new Thread("Discord RPC Callback")
        {
            @Override
            public void run()
            {
                while (running)
                {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown()
    {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine)
    {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("large",":(");
        b.setDetails(firstLine);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}

//some old code....

/*public class DiscordRP implements ReadyCallback
{
    private boolean running = true;
    private static final Minecraft mc = Minecraft.getMinecraft();
    DiscordRichPresence richPresence;

    public DiscordRP() {
        richPresence = new DiscordRichPresence
                .Builder("https://github.com/TheDarkLach")
                .setBigImage("large", "")
                .setDetails("Loading Monke...")
                .setStartTimestamps(System.currentTimeMillis())
                .build();

        init();
        startTask();
        DiscordRPC.discordUpdatePresence(richPresence);
    }

    private void init() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
                .setReadyEventHandler((user) ->
                        System.out.printf("Connected to %s#%s (%s)%n", user.username, user.discriminator,
                                user.userId)).build();

        DiscordRPC.discordInitialize("924458669725339699", handlers, true);
    }

    public void startTask() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(() -> {
                    richPresence.details =
                            mc.thePlayer == null ? "Avid Monke Lover" : "Nick: " + mc.thePlayer.getName();
                    richPresence.state = mc.getCurrentServerData() == null ? "Singleplayer"
                            : "Server: " + mc.getCurrentServerData().serverIP;
                    DiscordRPC.discordUpdatePresence(richPresence);
                }, 10, 10, TimeUnit.SECONDS);
    }

    public DiscordRichPresence getRichPresence() {
        return richPresence;
    }

    public void shutdown()
    {
        running = false;
        DiscordRPC.discordShutdown();
    }

    @Override
    public void apply(DiscordUser discordUser)
    {
        System.out.println("Initialized DiscordRichPresence API.");
    }
}*/

