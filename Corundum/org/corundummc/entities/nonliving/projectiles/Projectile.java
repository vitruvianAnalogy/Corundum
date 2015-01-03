package org.corundummc.entities.nonliving.projectiles;

import org.corundummc.entities.Entity;
import org.corundummc.entities.Entity.EntityType;
import org.corundummc.entities.nonliving.NonLivingEntity;
import org.corundummc.entities.nonliving.projectiles.fireballs.Fireball.FireballTypes;
import org.corundummc.entities.nonliving.projectiles.Arrow.ArrowType;
import org.corundummc.entities.nonliving.projectiles.BottleOEnchanting.BottleOEnchantingType;
import org.corundummc.entities.nonliving.projectiles.Egg.EggType;
import org.corundummc.entities.nonliving.projectiles.EnderPearl.EnderPearlType;
import org.corundummc.entities.nonliving.projectiles.EyeOfEnder.EyeOfEnderType;
import org.corundummc.entities.nonliving.projectiles.Snowball.SnowballType;
import org.corundummc.entities.nonliving.projectiles.SplashPotion.SplashPotionType;
import org.corundummc.entities.nonliving.projectiles.WitherSkull.WitherSkullType;

/** This class represents all of the {@link Entity Entities} that result from throwing or shooting something, including {@link ShotArrow}s and {@link ThrownSnowball}s.
 * 
 * @param <S>
 *            is a self-parameterization; this type should be the same type as this class.
 * @param <MC>
 *            determines the type of {@link net.minecraft.entity.Entity Minecraft Entity} that this class represents.
 * @param <T>
 *            determines the type of {@link EntityType} that represents the type of this class. */
public abstract class Projectile<S extends Projectile<S, MC, T>, MC extends net.minecraft.entity.Entity, T extends Projectile.ProjectileType<T, MC, S>> extends
        NonLivingEntity<S, MC, T> {
    protected Projectile(MC entityMC) {
        super(entityMC);
    }

    public static interface ProjectileTypes extends FireballTypes {
        // throwable projectiles
        public static final EggType EGG = EggType.TYPE;
        public static final SnowballType SNOWBALL = SnowballType.TYPE;
        public static final EnderPearlType ENDER_PEARL = EnderPearlType.TYPE;
        public static final EyeOfEnderType EYE_OF_ENDER = EyeOfEnderType.TYPE;
        public static final SplashPotionType SPLASH_POTION = SplashPotionType.TYPE;
        public static final BottleOEnchantingType BOTTLE_O_ENCHANTING = BottleOEnchantingType.TYPE;

        // fired projectiles
        public static final ArrowType ARROW = ArrowType.TYPE;

        public static final WitherSkullType WITHER_SKULL = WitherSkullType.TYPE;

    }

    public static abstract class ProjectileType<S extends ProjectileType<S, MC, I>, MC extends net.minecraft.entity.Entity, I extends Projectile<I, MC, S>> extends
            NonLivingEntityType<S, MC, I> {

        protected ProjectileType(int id) {
            super(id);

            addValueAs(ProjectileType.class);
        }

        // pseudo-enum utilities
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static ProjectileType getByID(int id) {
            return getByID(ProjectileType.class, id);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static ProjectileType getByID(int id, int data) {
            return getByID(ProjectileType.class, id, data);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static ProjectileType[] values() {
            return values(ProjectileType.class);
        }
    }
}
