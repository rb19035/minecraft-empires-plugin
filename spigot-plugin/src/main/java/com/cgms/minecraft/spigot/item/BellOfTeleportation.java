package com.cgms.minecraft.spigot.item;

import com.cgms.minecraft.spigot.util.MinecraftAiConstants;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


public class BellOfTeleportation implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String uuid;
    private BellOfTeleportation entangledBellOfTeleportation;
    private BellOfTeleportationBlock bellOfTeleportationBlock;
    private BellOfTeleportationItemStack bellOfTeleportationItemStack;


    public BellOfTeleportation()
    {
        this.uuid = UUID.randomUUID().toString();
    }

    public BellOfTeleportation( String uuid)
    {
        this.uuid = uuid;
    }



    public boolean canTeleport()
    {
        if( this.entangledBellOfTeleportation != null )
        {
            if ( this.getEntangledBellOfTeleportation().getBellOfTeleportationBlock() != null )
            {
                return true;
            }
        }

        return false;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( @NonNull String uuid )
    {
        this.uuid = uuid;
    }

    public Location getSpigotLocationOfPlacedBell()
    {
        if( this.bellOfTeleportationBlock != null)
        {
            return new Location( Bukkit.getWorld( this.bellOfTeleportationBlock.getWorldName() ), this.bellOfTeleportationBlock.getLocationX(),
                                 this.bellOfTeleportationBlock.getLocationY(), this.bellOfTeleportationBlock.getLocationZ()
            );
        }

        if( this.bellOfTeleportationItemStack != null )
        {
            return null;
        }

        return null;
    }

    public void setLocationOfPlacedBell( Location location )
    {
        this.setLocationOfPlacedBell( location.getWorld(), location.getX(), location.getY(), location.getZ() );
    }

    public void setLocationOfPlacedBell( @NonNull World world, double x, double y, double z )
    {
        this.bellOfTeleportationBlock = new BellOfTeleportationBlock( world.getName(), x, y, z );
    }

    public void clearLocationOfPlacedBell( )
    {
        this.bellOfTeleportationBlock = null;
    }

    public BellOfTeleportation getEntangledBellOfTeleportation()
    {
        return entangledBellOfTeleportation;
    }

    public void setEntangledBellOfTeleportation( BellOfTeleportation entangledBellOfTeleportation )
    {
        this.entangledBellOfTeleportation = entangledBellOfTeleportation;
    }

    public BellOfTeleportationBlock getBellOfTeleportationBlock()
    {
        return bellOfTeleportationBlock;
    }

    public void setBellOfTeleportationBlock( BellOfTeleportationBlock bellOfTeleportationBlock )
    {
        this.bellOfTeleportationBlock = bellOfTeleportationBlock;
    }

    public void setBellOfTeleportationBlockFromSpigotBlock( Block bellOfTeleportationBlock )
    {
        this.bellOfTeleportationBlock = new BellOfTeleportationBlock( bellOfTeleportationBlock );
    }

    public BellOfTeleportationItemStack getBellOfTeleportationItemStack()
    {
        return bellOfTeleportationItemStack;
    }

    public void setBellOfTeleportationItemStack( BellOfTeleportationItemStack bellOfTeleportationItemStack )
    {
        this.bellOfTeleportationItemStack = bellOfTeleportationItemStack;
    }

    public void setBellOfTeleportationItemStack( ItemStack bellOfTeleportationItemStack )
    {
        this.bellOfTeleportationItemStack = new BellOfTeleportationItemStack( bellOfTeleportationItemStack.hashCode() );
    }

    @Transient
    public ItemStack getSpigotItemStack()
    {
        ItemStack itemStack = new ItemStack( Material.BELL, 1 );

        ItemMeta bellMeta = itemStack.getItemMeta();
        bellMeta.setItemName( MinecraftAiConstants.BELLS_OF_TELEPORTATION );
        bellMeta.setDisplayName( MinecraftAiConstants.BELLS_OF_TELEPORTATION );

        return itemStack;
    }

    @Override
    public boolean equals( Object o )
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BellOfTeleportation that = (BellOfTeleportation) o;

        return Objects.equals( uuid, that.uuid ) && Objects.equals( entangledBellOfTeleportation, that.entangledBellOfTeleportation ) && Objects.equals( bellOfTeleportationBlock, that.bellOfTeleportationBlock ) && Objects.equals( bellOfTeleportationItemStack, that.bellOfTeleportationItemStack );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( uuid, entangledBellOfTeleportation, bellOfTeleportationBlock, bellOfTeleportationItemStack );
    }
}
