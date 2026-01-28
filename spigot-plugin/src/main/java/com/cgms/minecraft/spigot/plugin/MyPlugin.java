package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.spigot.command.GuiCommand;
import com.cgms.minecraft.spigot.listener.GuiListener;
import com.cgms.minecraft.spigot.listener.NpcNavigationCompleteListener;
import com.cgms.minecraft.spigot.listener.NpcSpawnItemDroppedListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MyPlugin extends JavaPlugin
{
    private static final Logger LOGGER = Logger.getLogger("MyPlugin");

    private static final double X_COORDINATE = 4.873;
    private static final double Y_COORDINATE = 78.00;
    private static final double Z_COORDINATE = 8.474;

/*** Remote Server *****/
//    private static double X_COORDINATE = 311.207;
//    private static double Y_COORDINATE = 73.00;
//    private static double Z_COORDINATE = -170.547;
/*** Remote Server *****/

    @Override
    public void onEnable() {
        // Called when the plugin is enabled
        LOGGER.info("[MyPlugin] MyPlugin has been enabled!");

        // You can add more initialization logic here, e.g., register commands or events
        getServer().getPluginManager().registerEvents( new NpcNavigationCompleteListener(), this);
        getServer().getPluginManager().registerEvents( new NpcSpawnItemDroppedListener(), this);
        getServer().getPluginManager().registerEvents( new GuiListener(), this );

        getCommand( "gui" ).setExecutor( new GuiCommand() );

        //CitizensAPI.getTraitFactory().registerTrait( TraitInfo.create( SentinelTrait.class ).withName("Sentinel") );

//        World world = Bukkit.getWorld("world");
//        Location location = new Location( world, X_COORDINATE, Y_COORDINATE, Z_COORDINATE );
//
//        //NPC npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.GIANT, "Armored knight");
//        NPC npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.PLAYER, "Armored knight");
//        npc.spawn( location );
//        npc.setFlyable( false );
//        npc.setProtected( false );
//        npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS, 1 ) );
//        npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.IRON_HELMET, 1 ) );
//        npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.IRON_CHESTPLATE, 1 ) );
//        npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS, 1 ) );
//        npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD, 1 ) );
//        npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.OFF_HAND, new ItemStack( Material.SHIELD, 1 ) );
    }

    @Override
    public void onDisable() {
        // Called when the plugin is disabled
        LOGGER.info("[MyPlugin] MyPlugin has been disabled!");
        // You can add cleanup logic here
    }
}
