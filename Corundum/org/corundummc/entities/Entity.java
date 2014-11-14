/** This code is property of the Corundum project managed under the Software Developers' Association of Arizona State University.
 *
 * Copying and use of this open-source code is permitted provided that the following requirements are met:
 *
 * - This code may not be used or distributed for private enterprise, including but not limited to personal or corporate profit. - Any products resulting from the copying,
 * use, or modification of this code may not claim endorsement by the Corundum project or any of its members or associates without written formal permission from the endorsing
 * party or parties. - This code may not be copied or used under any circumstances without the inclusion of this notice and mention of the contribution of the code by the
 * Corundum project. In source code form, this notice must be included as a comment as it is here; in binary form, proper documentation must be included with the final product
 * that includes this statement verbatim.
 *
 * @author REALDrummer */

package org.corundummc.entities;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.WorldServer;
import org.corundummc.types.TypedObject;
import org.corundummc.types.IDedTypeWithData;
import org.corundummc.world.Location;
import org.corundummc.world.Rotation;
import org.corundummc.world.World;

public abstract class Entity extends TypedObject {
    protected final net.minecraft.entity.Entity entityMC;

    protected Entity(net.minecraft.entity.Entity entityMC) {
        this.entityMC = entityMC;
    }

    /* TODO: implement the constructors below once we find a good way to make Entities without spawning them in the Minecraft code; I think this should be done one of two
     * ways:
     * 
     * - preferably, we can store the type and location into a Minecraft Entity object, but we need to make sure this can be done without spawning the entity
     * 
     * - if the above is not possible, we can make entityMC null and store the request type and location in a map to be retrieved later; the map allows us to save space by
     * avoiding having EntityType and Location instance variables with every entity when they're only used temporarily and only in optional cases */
    public Entity(EntityType type, Location location) {
        // TODO
        entityMC = null;
    }

    public Entity(EntityType type, Location location, Rotation rotation) {
        // TODO
        entityMC = null;
    }

    public static class EntityType<T extends EntityType<T>> extends IDedTypeWithData<EntityType<T>> {
        // TODO: see if a Player entity has the I.D. 0

        public static final EntityType PLAYER = new EntityType(0, -1), DROPPED_ITEM = new EntityType(), XP_ORB = new EntityType(), LEAD = new EntityType(8, -1),
                PAINTING = new EntityType(), ARROW = new EntityType(), SNOWBALL = new EntityType(), GHAST_FIREBALL = new EntityType(), BLAZE_FIREBALL = new EntityType(),
                ENDER_PEARL = new EntityType(), EYE_OF_ENDER = new EntityType(), SPLASH_POTION = new EntityType(), BOTTLE_O_ENCHANTING = new EntityType(),
                ITEM_FRAME = new EntityType(), WITHER_SKULL = new EntityType(),
                TNT = new EntityType(),
                FALLING_BLOCK = new EntityType(),
                ARMOR_STAND = new EntityType(30),
                COMMAND_MINECART = new EntityType(40),
                BOAT = new EntityType(),
                MINECART = new EntityType(),
                STORAGE_MINECART = new EntityType(),
                POWERED_MINECART = new EntityType(),
                TNT_MINECART = new EntityType(),
                HOPPER_MINECART = new EntityType(),
                SPAWNER_MINECART = new EntityType(),
                MOB = new EntityType(),
                MONSTER = new EntityType(),
                CREEPER = new EntityType(),
                // skeletons
                SKELETON = new EntityType(0),
                WITHER_SKELETON = new EntityType(),
                // END skeletons
                SPIDER = new EntityType(-1),
                GIANT = new EntityType(),
                // zombies
                // TODO: figure out if these data values are correct
                ZOMBIE = new EntityType(0),
                ZOMBIFIED_VILLAGER = new EntityType(),
                BABY_ZOMBIE = new EntityType(),
                BABY_ZOMBIFIED_VILLAGER = new EntityType(),
                // END zombies
                SLIME = new EntityType(-1), GHAST = new EntityType(), ZOMBIE_PIGMAN = new EntityType(), ENDERMAN = new EntityType(), CAVE_SPIDER = new EntityType(),
                SILVERFISH = new EntityType(), BLAZE = new EntityType(), MAGMA_CUBE = new EntityType(), ENDER_DRAGON = new EntityType(),
                WITHER = new EntityType(),
                BAT = new EntityType(),
                WITCH = new EntityType(),
                ENDERMITE = new EntityType(),
                // guardians
                // TODO: figure out if these data values are correct
                GUARDIAN = new EntityType(0),
                ELDER_GUARDIAN = new EntityType(),
                // END guardians
                PIG = new EntityType(90, -1), SHEEP = new EntityType(), COW = new EntityType(), CHICKEN = new EntityType(), SQUID = new EntityType(), WOLF = new EntityType(),
                MOOSHROOM = new EntityType(), SNOW_GOLEM = new EntityType(), OCELOT = new EntityType(), IRON_GOLEM = new EntityType(),
                HORSE = new EntityType(),
                // rabbits
                // TODO: figure out if these data values are correct
                RABBIT = new EntityType(0),
                KILLER_RABBIT = new EntityType(),
                // END rabbits
                // villagers
                /* TODO: Figure out if these I.D.s are correct and how "careers" (more specific types within professions, e.g. a "FARMER" can be a farmer, fisherman, shepherd,
                 * of fletcher) are handled. */
                FARMER_VILLAGER = new EntityType(120, 0), LIBRARIAN_VILLAGER = new EntityType(), PRIEST_VILLAGER = new EntityType(), BLACKSMITH_VILLAGER = new EntityType(),
                BUTCHER_VILLAGER = new EntityType(),
                // END villagers
                ENDER_CRYSTAL = new EntityType(200, -1);

        // TODO: Minecraft has no EntityType equivalent object, so we need to find a way to retrieve type info; EntityList will probably help

        /** This constructor makes a EntityType based on the previous value's I.D. and data. If the previous value has no strictly associated data value (data value = -1), it
         * means that it has no sub-types (e.g. the different colors of wool or types of wood), so use the next I.D.; if it has a data value, give this EntityType the same
         * I.D. and the next data value. Essentially, "I.D. items" (blocks of multiple enum constants that all have the same I.D., but different data values) are delimited by
         * the use of the {@link #EntityType(int)} and {@link #EntityType(int, int)} constructors; declaring a new enum value with a data value less than the previous will end
         * a block and declaring one with a data value >= 0 will start a new block. */
        protected EntityType() {
            super();
        }

        /** This constructor makes a EntityType based on the previous value's I.D. and the given data. If the previous value's data value is <= <b><tt>data</b></tt>, then the
         * I.D. block is ending, so it increments the I.D.; otherwise, it will use the same I.D. as the previous value to continue the I.D. block. Essentially, "I.D. blocks"
         * (blocks of multiple enum constants that all have the same I.D., but different data values) are delimited by the use of the {@link #EntityType(int)} and
         * {@link #EntityType(int, int)} constructors; declaring a new enum value with a data value less than the previous will end a block and declaring one with a data value
         * >= 0 will start a new block.
         * 
         * @param data
         *            is the data value for this {@link EntityType}. */
        protected EntityType(int data) {
            super(data);
        }

        /** This constructor makes a EntityType with the given I.D. and data. It's necessary for specifying I.D.s when Minecraft skips I.D.s.
         * 
         * @param id
         *            is the item I.D. that this {@link EntityType} is associated with.
         * @param data
         *            is the data value associated with this {@link EntityType}.
         * @see {@link #EntityType(int)} */
        protected EntityType(int id, int data) {
            super(id, data);
        }

        protected EntityType(EntityType<?> parent) {
            super(parent);
        }

        // instance utils
        public boolean isLiving() {
            // TODO: return LivingType.getByID(getID()) != null; (once LivingType is implemented)
            return false;
        }

        // pseudo-enum utils
        public static EntityType getByID(int id) {
            return getByID(EntityType.class, id);
        }

        public static EntityType getByID(int id, int data) {
            return getByID(EntityType.class, id, data);
        }

        public static EntityType[] values() {
            return values(EntityType.class);
        }
    }

    @Override
    public String getCustomName() {
        // TODO
        return null;
    }

    @Override
    public Location getLocation() {
        return new Location(entityMC.posX, entityMC.posY, entityMC.posZ, World.fromMCWorld((WorldServer) entityMC.worldObj));
    }

    @Override
    public EntityType getType() {
        /* because Minecraft decided to not use regular data values for different types of sibling entities, like skeletons and Wither skeletons, the data values will have to
         * be found based on different methods for different types of entities here */
        if (entityMC instanceof EntitySkeleton)
            if (((EntitySkeleton) entityMC).getSkeletonType() == 1)
                return EntityType.WITHER_SKELETON;
            else
                return EntityType.SKELETON;
        // TODO: add special cases for zombies/zombified villagers, (elder) guardians, (killer) rabbits, an villager professions and/or careers
        else
            /* NOTE: Entity.entityID is not the I.D. representing the Entity's type! It's actually the I.D. that helps track that specific Entity instance. */
            return EntityType.getByID(EntityList.getEntityID(entityMC), -1);
    }

    public Velocity getVelocity() {
        // TODO TEST
        return new Velocity(entityMC.motionX, entityMC.motionY, entityMC.motionZ, this);
    }

    public void setVelocity(Velocity velocity) {
        // TODO TEST
        entityMC.motionX = velocity.getX();
        entityMC.motionY = velocity.getY();
        entityMC.motionZ = velocity.getZ();
    }

    public void spawn() {
        // TODO: if a location was declared earlier, use that location in spawn(Location); otherwise, throw a custom CorundumException
    }

    public void spawn(Location location) {
        // TODO
    }
}