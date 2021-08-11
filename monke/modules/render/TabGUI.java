package monke.modules.render;

import java.util.List;

import org.lwjgl.input.Keyboard;

import monke.Client;
import monke.events.Event;
import monke.events.listeners.EventKey;
import monke.events.listeners.EventRenderGUI;
import monke.events.listeners.EventUpdate;
import monke.modules.Module;
import monke.settings.BooleanSetting;
import monke.settings.KeybindSetting;
import monke.settings.ModeSetting;
import monke.settings.NumberSetting;
import monke.settings.Setting;
import monke.util.ColorUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class TabGUI extends Module
{
	public int currentTab;
	public boolean expanded;
	
	public TabGUI()
	{
		super("TabGUI", Keyboard.KEY_TAB, Category.RENDER);
		toggled = true;
	}
	

	public void onEvent(Event e)
	{
		if (e instanceof EventRenderGUI)
		{
			FontRenderer fr = mc.fontRendererObj;
			
			int primaryColor = 0xffff69b4, secondaryColor = 0xffff46b4;
			
			int color = ColorUtil.getRainbow(4, 0.8f, 1);
			
			//original tab gui
			Gui.drawRect(5.5, 5.5f, 70, 5 + Module.Category.values().length * 16 + 1.5, 0x90000000);
            Gui.drawRect(5.5, 5.5f + currentTab * 16, 70, 13 + currentTab * 16 + 7 + 2.5f, primaryColor);
            
			
			int count = 0;
			for (Category c : Module.Category.values())
			{
				fr.drawStringWithShadow(c.name, 11, 10 + count*16, -1);
				
				count++;
			}
			
			//expansion
			if (expanded)
			{
				Category category = Module.Category.values()[currentTab];
				List<Module> modules = Client.getModulesByCategory(category);
				
				if(modules.size() == 0)
				{
					return;
				}
				
				
				Gui.drawRect(70, 5.5, 138, 5 + modules.size() * 16 + 1.5, 0x90000000);
		        Gui.drawRect(70, 5.5 +  category.moduleIndex * 16, 138, 8 + category.moduleIndex * 16 + 12 + 2.5F, primaryColor);
				
				count = 0;
				for (Module m : modules)
				{
					fr.drawStringWithShadow(m.name, 73, 10 + count*16, -1);
					
					if(count == category.moduleIndex && m.expanded)
					{
						
						int index = 0, maxLength = 0;
						for (Setting setting : m.settings)
						{
							if(setting instanceof BooleanSetting)
							{
								BooleanSetting bool = (BooleanSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + (bool.enabled ? "Enabled" : "Disabled")))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + (bool.enabled ? "Enabled" : "Disabled"));
								}
							}
							
							if(setting instanceof NumberSetting)
							{
								NumberSetting number = (NumberSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + number.getValue()))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + number.getValue());
								}
								//fr.drawStringWithShadow(setting.name + ": " + number.getValue(), 73 + 68, 35 + index*16, -1);
							}
							
							if(setting instanceof ModeSetting)
							{
								ModeSetting mode = (ModeSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + mode.getMode()))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + mode.getMode());
								}
								//fr.drawStringWithShadow(setting.name + ": " + mode.getMode(), 73 + 68, 35 + index*16, -1);
							}
							if(setting instanceof KeybindSetting)
							{
								KeybindSetting keyBind = (KeybindSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keyBind.code)))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keyBind.code));
								}
								//fr.drawStringWithShadow(setting.name + ": " + mode.getMode(), 73 + 68, 35 + index*16, -1);
							}
							
							index++;
						}
						
						
						if(!m.settings.isEmpty())
						{
							
							Gui.drawRect(70 + 68, 5.5, 70 + 68 + maxLength + 9, 5 + m.settings.size() * 16 + 1.5, 0x90000000);
							Gui.drawRect(70 + 68, 5.5f + m.index * 16, 7 + 61 + maxLength + 70 + 9, 8 + m.index * 16 + 12 + 2.5f,m.settings.get(m.index).focused ? secondaryColor :  primaryColor); //0xffff69b4);
							
							index = 0;
							for (Setting setting : m.settings)
							{
								if(setting instanceof BooleanSetting)
								{
									BooleanSetting bool = (BooleanSetting) setting;
									fr.drawStringWithShadow(setting.name + ": " + (bool.enabled ? "Enabled" : "Disabled"), 73 + 68, 10 + index*16, -1);
								}
								
								if(setting instanceof NumberSetting)
								{
									NumberSetting number = (NumberSetting) setting;
									fr.drawStringWithShadow(setting.name + ": " + number.getValue(), 73 + 68, 10 + index*16, -1);
								}
								
								if(setting instanceof ModeSetting)
								{
									ModeSetting mode = (ModeSetting) setting;
									fr.drawStringWithShadow(setting.name + ": " + mode.getMode(), 73 + 68, 10 + index*16, -1);
								}
								if(setting instanceof KeybindSetting)
								{
									KeybindSetting keyBind = (KeybindSetting) setting;
									fr.drawStringWithShadow(setting.name + ": " + Keyboard.getKeyName(keyBind.code), 73 + 68, 10 + index*16, -1);
								}
								
								index++;
							}
						}
					}
					
					count++;
				}
				
			}
		}
		
		if(e instanceof EventKey)
		{
			int code = ((EventKey)e).code;
			
			Category category = Module.Category.values()[currentTab];
			List<Module> modules = Client.getModulesByCategory(category);
			
			if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded)
			{
				Module module = modules.get(category.moduleIndex);
				
				if (!module.settings.isEmpty() && module.settings.get(module.index).focused && module.settings.get(module.index) instanceof KeybindSetting)
				{
					if(code != Keyboard.KEY_RETURN && code != Keyboard.KEY_UP && code != Keyboard.KEY_DOWN && code != Keyboard.KEY_LEFT && code != Keyboard.KEY_RIGHT && code != Keyboard.KEY_ESCAPE)
					{
						KeybindSetting keyBind = (KeybindSetting)module.settings.get(module.index);
						keyBind.code = code;
						keyBind.focused = false;
						return;
					}
					
				}
			}
			
			if(code == Keyboard.KEY_UP)
			{
				if(expanded)
				{
					if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded)
					{
						Module module = modules.get(category.moduleIndex);
						
						if(!module.settings.isEmpty())
						{
							if (module.settings.get(module.index).focused)
							{
								Setting setting = module.settings.get(module.index);
								
								if (setting instanceof NumberSetting)
								{
									((NumberSetting)setting).increment(true);
								}
	
							}
							else
							{
								if(module.index <= 0)
								{
									module.index = module.settings.size() - 1;
								}
								else
								{
									module.index--;
								}
							}
						}
					}
					else
					{
						if(category.moduleIndex <= 0)
						{
							category.moduleIndex = modules.size() - 1;
						}else
							category.moduleIndex--;
					}
				}else
					if(currentTab <= 0)
					{
						currentTab = Module.Category.values().length - 1;
					}else
						currentTab--;
			}
			if(code == Keyboard.KEY_DOWN)
			{
				if(expanded)
				{
					if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded)
					{
						Module module = modules.get(category.moduleIndex);
						
						if(!module.settings.isEmpty())
						{
							if (module.settings.get(module.index).focused)
							{
								Setting setting = module.settings.get(module.index);
								
								if (setting instanceof NumberSetting)
								{
									((NumberSetting)setting).increment(false);
								}
							}
							else
							{
								if (module.index >= module.settings.size() - 1)
								{
									module.index = 0;
								}
								else
								{
									module.index++;
								}
							}
						}
					}
					else
					{
						if (category.moduleIndex >= modules.size() - 1)
						{
							category.moduleIndex = 0;
						}
						else
						{
							category.moduleIndex++;
						}
					}
				}else
					if (currentTab >= Module.Category.values().length - 1)
					{
						currentTab = 0;
					}
					else
					{
						currentTab++;
					}
			}
			
			if(code == Keyboard.KEY_RETURN)
			{
				if(expanded && modules.size() != 0)
				{
					Module module = modules.get(category.moduleIndex);
					if(!module.expanded && !module.settings.isEmpty())
						module.expanded = true;
					else if(module.expanded && !module.settings.isEmpty())
					{
						module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
					}
				}
			}
			
			
			if(code == Keyboard.KEY_RIGHT)
			{
				if(expanded && modules.size() != 0)
				{
					Module module = modules.get(category.moduleIndex);
					

					if(expanded && !modules.isEmpty() && module.expanded)
					{
						if(!module.settings.isEmpty())
						{
							Setting setting = module.settings.get(module.index);
							
							if (setting instanceof BooleanSetting)
							{
								((BooleanSetting)setting).toggle();
							}
							if (setting instanceof ModeSetting)
							{
								((ModeSetting)setting).cycle();
							}
						}

					}
					else
					{
						if(!module.name.equals("TabGUI"))
							module.toggle();
					}
				}else
					expanded = true;
			}
			if(code == Keyboard.KEY_LEFT)
			{
				if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded)
				{
					Module module = modules.get(category.moduleIndex);
					
					if(!module.settings.isEmpty())
					{
						if (module.settings.get(module.index).focused)
						{
							
						}
						else
							modules.get(category.moduleIndex).expanded = false;
					}
				}else
					expanded = false;
			}
		}
	}
}
