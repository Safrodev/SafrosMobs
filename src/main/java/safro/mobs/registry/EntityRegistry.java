package safro.mobs.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;
import safro.mobs.SafrosMobs;
import safro.mobs.config.SMConfig;
import safro.mobs.entity.FlaphawkEntity;
import safro.mobs.entity.GoblinGruntEntity;
import safro.mobs.entity.MockerEntity;
import safro.mobs.entity.PumpFrogEntity;
import safro.saflib.registry.BaseEntityRegistry;

public class EntityRegistry extends BaseEntityRegistry {
    static { MODID = SafrosMobs.MODID; }

    public static final EntityType<GoblinGruntEntity> GOBLIN_GRUNT = register("goblin_grunt", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinGruntEntity::new).dimensions(EntityDimensions.fixed(1.4F, 2.7F)).trackRangeChunks(10).build());
    public static final EntityType<PumpFrogEntity> PUMP_FROG = register("pump_frog", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PumpFrogEntity::new).dimensions(EntityDimensions.fixed(0.9F, 0.9F)).trackRangeChunks(10).build());
    public static final EntityType<FlaphawkEntity> FLAPHAWK = register("flaphawk", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FlaphawkEntity::new).dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeChunks(12).build());
    public static final EntityType<MockerEntity> MOCKER = register("mocker", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MockerEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.8F)).trackRangeChunks(16).build());

    public static void initRestrictions() {
        SpawnRestriction.register(GOBLIN_GRUNT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoblinGruntEntity::canSpawn);
        SpawnRestriction.register(FLAPHAWK, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlaphawkEntity::canSpawn);
        SpawnRestriction.register(MOCKER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
    }

    public static void initSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, GOBLIN_GRUNT, SMConfig.get().goblinGruntWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.SWAMP), SpawnGroup.CREATURE, PUMP_FROG, SMConfig.get().pumpFrogWeight, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(TagRegistry.HAS_FLAPHAWK), SpawnGroup.CREATURE, FLAPHAWK, SMConfig.get().flaphawkWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, MOCKER, SMConfig.get().mockerWeight, 1, 1);
    }

    public static void init() {
        addAttributes(GOBLIN_GRUNT, GoblinGruntEntity.createGoblinGruntAttributes());
        addAttributes(PUMP_FROG, PumpFrogEntity.createPumpFrogAttributes());
        addAttributes(FLAPHAWK, FlaphawkEntity.createFlaphawkAttributes());
        addAttributes(MOCKER, MockerEntity.createMockerAttributes());

        initRestrictions();
        initSpawns();
    }
}
