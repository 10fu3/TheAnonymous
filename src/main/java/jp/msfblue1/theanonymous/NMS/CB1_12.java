package jp.msfblue1.theanonymous.NMS;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import jp.msfblue1.theanonymous.TheAnonymous;
import jp.msfblue1.theanonymous.Util.ReflectionUtil;
import jp.msfblue1.theanonymous.api.MultiverseCore;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.WorldType;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by msfblue1 on 2017/11/18.
 */
public class CB1_12 implements NMSAdapter {

    @Override
    public void setName(Player player) {

        String ename = encryptName(player.getAddress().getAddress().getHostAddress()+new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        CraftPlayer cp = (CraftPlayer) player;
        EntityHuman eh = getEntityPlayer(cp);
        GameProfile gp = new GameProfile(cp.getUniqueId(),ename);
        try {
            setObjectFINALPRVATE(eh,"g",gp);

            for (Player other : Bukkit.getOnlinePlayers()){
                if(!player.equals(other)){
                    EntityPlayer oep = ((CraftPlayer)player).getHandle();
                    oep.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(eh));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNavTitle(Player p, String Mes) {
        PacketPlayOutChat playOutChat = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Mes + "\"}"));
        getEntityPlayer(p).playerConnection.sendPacket(playOutChat);
    }

    private EntityPlayer getEntityPlayer(Player p){
        return ((CraftPlayer)p).getHandle();
    }

    public void setObjectFINALPRVATE(EntityHuman obj, String name, Object value) {
        Field f;
        try {
            f = obj.getClass().getSuperclass().getDeclaredField(name);
            f.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(f,
                    f.getModifiers() & ~Modifier.PRIVATE & ~Modifier.FINAL);
            f.set(obj, value);
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void applySkin(Player p,Property props){
        getEntityPlayer(p).getProfile().getProperties().clear();
        getEntityPlayer(p).getProfile().getProperties().put("textures", props);
    }

    private void updateSkin(Player player){

        try{
            EntityPlayer ep = getEntityPlayer(player);
            Location l = player.getLocation();

            Iterable<EntityPlayer> itr = new ArrayList<>(Collections.singletonList(ep));
            PacketPlayOutPlayerInfo removeinfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,itr);
            PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(player.getEntityId());
            PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn(ep);

            PacketPlayOutPlayerInfo addinfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,itr);

            World world = ep.getWorld();
            EnumDifficulty difficulty = world.getDifficulty();

            WorldType worldtype = world.worldData.getType();

            int dimension;

            if(MultiverseCore.check()){
                dimension = MultiverseCore.dimension(player.getWorld());
            }else{
                dimension = (int) ReflectionUtil.getObject(world, "dimension");
            }
            PlayerInteractManager playerIntManager = ep.playerInteractManager;
            EnumGamemode enumGamemode = playerIntManager.getGameMode();
            int gmid = enumGamemode.getId();

            PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(dimension,difficulty,worldtype,EnumGamemode.getById(gmid));

            PacketPlayOutPosition pos = new PacketPlayOutPosition(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(), new HashSet<>(), 0);

            PacketPlayOutEntityEquipment hand = new PacketPlayOutEntityEquipment(player.getEntityId(),EnumItemSlot.MAINHAND,CraftItemStack.asNMSCopy(player.getItemInHand()));

            PacketPlayOutEntityEquipment offhand = new PacketPlayOutEntityEquipment(player.getEntityId(),EnumItemSlot.OFFHAND,CraftItemStack.asNMSCopy(player.getItemInHand()));

            PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(player.getEntityId(),EnumItemSlot.HEAD,CraftItemStack.asNMSCopy(player.getItemInHand()));

            PacketPlayOutEntityEquipment chestplate = new PacketPlayOutEntityEquipment(player.getEntityId(),EnumItemSlot. CHEST,CraftItemStack.asNMSCopy(player.getItemInHand()));

            PacketPlayOutEntityEquipment leggings = new PacketPlayOutEntityEquipment(player.getEntityId(),EnumItemSlot.LEGS,CraftItemStack.asNMSCopy(player.getItemInHand()));

            PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(player.getEntityId(),EnumItemSlot.FEET,CraftItemStack.asNMSCopy(player.getItemInHand()));

            PacketPlayOutHeldItemSlot slot = new PacketPlayOutHeldItemSlot(player.getInventory().getHeldItemSlot());

            for(Player p : Bukkit.getOnlinePlayers()){
                EntityPlayer entityPlayer = ((CraftPlayer)p).getHandle();
                PlayerConnection pc = entityPlayer.playerConnection;
                if(p.equals(player)){
                    pc.sendPacket(removeinfo);
                    pc.sendPacket(addinfo);
                    pc.sendPacket(respawn);
                    Bukkit.getScheduler().runTask(TheAnonymous.getInstance(), entityPlayer::updateAbilities);
                    pc.sendPacket(pos);
                    pc.sendPacket(slot);
                    ((CraftPlayer) p).updateScaledHealth();
                    p.updateInventory();
                    entityPlayer.triggerHealthUpdate();

                    if(p.isOp()){
                        p.setOp(false);
                        p.setOp(true);
                    }
                    continue;
                }
                if(p.getWorld().equals(player.getWorld()) && p.canSee(player) && player.isOnline()){
                    pc.sendPacket(removeEntity);
                    pc.sendPacket(removeinfo);
                    pc.sendPacket(addinfo);
                    pc.sendPacket(addNamed);

                    pc.sendPacket(hand);
                    pc.sendPacket(offhand);
                    pc.sendPacket(helmet);
                    pc.sendPacket(chestplate);
                    pc.sendPacket(leggings);
                    pc.sendPacket(boots);
                }else{
                    pc.sendPacket(removeinfo);
                    pc.sendPacket(addinfo);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSkin(Player player){
        String sig = "vUMKC0HXGclYJZsSrhuDGF8v535QtoXNNInM2+xbVbD49czcPjDeDExPmekfB0Fx79sIOHrps4HbfPcKn2r1O+KWvOH1Qq+BbfInmEPjT70ah2U9+5/ZbY9tbwiq6eKxjp3QWvB3cIs9GldkKSV1Ql57ZvrgQltx3l3U4cpWPQ0/H1I02gyhme1xa1/kH5axEe+PQFLjHTnWATnOZJ4tgFNmnH0aiq/OV6WJa8t8pEF5iBkfe3OOyelQGKqpQ28v4+570t4iuFhj8S2t8kqsPWtaBbIKK+ozhhlA5d05kQSEf/Opa+oEVjgHSqedNoVxrQ7c0gv+nNW/r1ZvuXOlK7rrmZaSDqZCJ8gIrK2zKnKsYat18QZVUhev5OFQayiFjBosPU72yVzCuntXb6aP2CEd9inQceyf9e9o1FcZWpngikyoYVrZs9l0Sh9eRlj/z6/nca/EWlhWqDEViJE6B3OX/akZHj7YbAbU+zIL3h2+xzxVp9Gx1RcaBrrv9ybFgKbrSa50SNM9c/0JzO6jxd3Rcp4rjWwBSXBEwoCeU7i6pwt6yVhev+46RM6Qdq9CXGaXg7gMQ41JmGaHqhlcQgszFLQckitutRTf6uOfjzVvWFAM01VeZiurDfV3qbTuQsw0J7UUTYAzIGnyvL+9s6+h5s090b61YfkGnzzV3AY=";
        String value = "eyJ0aW1lc3RhbXAiOjE1MTA4MzcxODQwNTksInByb2ZpbGVJZCI6Ijg2NjdiYTcxYjg1YTQwMDRhZjU0NDU3YTk3MzRlZWQ3IiwicHJvZmlsZU5hbWUiOiJTdGV2ZSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU2ZWVjMWMyMTY5YzhjNjBhN2FlNDM2YWJjZDJkYzU0MTdkNTZmOGFkZWY4NGYxMTM0M2RjMTE4OGZlMTM4In0sIkNBUEUiOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iNzY3ZDQ4MzI1ZWE1MzI0NTYxNDA2YjhjODJhYmJkNGUyNzU1ZjExMTUzY2Q4NWFiMDU0NWNjMiJ9fX0";
        Property property = new Property("textures", value, sig);

        this.applySkin(player,property);
        this.updateSkin(player);
    }

    public String encryptName(String value) {
        String text = value;
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
                sb.append(String.format("%02x", b&0xff) );
            }
            String substring = sb.toString().substring(0, 8);
            return substring;
        } catch (Exception e) {
            e.printStackTrace();
            return "0000x0000";
        }
    }
}
