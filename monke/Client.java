package monke;

import java.util.ArrayList;
import java.util.List;

/*
 * Alpha list:
 * Cinabutters
 * Gavin
 * teffy	
 * roudro
 * 
 */

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;

import monke.events.Event;
import monke.events.listeners.EventKey;
import monke.modules.Module;
import monke.modules.Module.Category;
import monke.modules.combat.*;
import monke.modules.movement.*;
import monke.modules.player.*;
import monke.modules.render.*;
import monke.ui.HUD;

public class Client 
{
	public static String name = "Monke", version = "0.1";
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static HUD hud = new HUD();
	
	public static void startup()
	{
		System.out.println("starting " + name + " - v" + version);
		Display.setTitle(name + " v" + version);
		
		modules.add(new Fly());
		modules.add(new Sprint());
		modules.add(new FullBright());
		modules.add(new NoFall());
		modules.add(new TabGUI());
		modules.add(new Killaura());
		modules.add(new Aimbot());
		modules.add(new Speed());
		modules.add(new Bunny());
		modules.add(new blockReach());
		modules.add(new Jump());
	}	
	
	public static void onEvent(Event e)//lol
	{
		for(Module m : modules)
		{
			if (!m.toggled)
			{
				continue;
			}
			
			m.onEvent(e);
		}
	}
	
	public static void keyPress(int key)
	{
		Client.onEvent(new EventKey(key));
		
		for(Module m : modules)
			if(m.getKey() == key)
			{
				m.toggle();
			}
	}
	
	public static List<Module> getModulesByCategory(Category c)
	{
		List<Module> modules = new ArrayList<Module>();
		
		for (Module m : Client.modules)
		{
			if (m.category == c)
			{
				modules.add(m);
			}
		}
		return modules;
	}
	
}
