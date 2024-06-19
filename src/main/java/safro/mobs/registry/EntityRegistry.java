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
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.world.Heightmap;
import safro.mobs.SafrosMobs;
import safro.mobs.config.SMConfig;
import safro.mobs.entity.*;
import safro.mobs.util.SMUtil;
import safro.saflib.registry.BaseEntityRegistry;

public class EntityRegistry extends BaseEntityRegistry {
    static { MODID = SafrosMobs.MODID; }

    public static final EntityType<GoblinGruntEntity> GOBLIN_GRUNT = register("goblin_grunt", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoblinGruntEntity::new).dimensions(EntityDimensions.fixed(1.4F, 2.7F)).trackRangeChunks(10).build());
    public static final EntityType<PumpFrogEntity> PUMP_FROG = register("pump_frog", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PumpFrogEntity::new).dimensions(EntityDimensions.fixed(0.9F, 0.9F)).trackRangeChunks(10).build());
    public static final EntityType<FlaphawkEntity> FLAPHAWK = register("flaphawk", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FlaphawkEntity::new).dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeChunks(12).build());
    public static final EntityType<MockerEntity> MOCKER = register("mocker", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MockerEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.8F)).trackRangeChunks(16).build());
    public static final EntityType<LeviathanEntity> LEVIATHAN = register("leviathan", FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, LeviathanEntity::new).dimensions(EntityDimensions.fixed(1.6F, 1.0F)).trackRangeChunks(10).build());
    public static final EntityType<ThundizardEntity> THUNDIZARD = register("thundizard", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ThundizardEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.3F)).trackRangeChunks(8).build());
    public static final EntityType<FairyEntity> FAIRY = register("fairy", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FairyEntity::new).dimensions(EntityDimensions.fixed(0.5F, 1.2F)).trackRangeChunks(8).build());
    public static final EntityType<AscendantEntity> ASCENDANT = register("ascendant", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AscendantEntity::new).dimensions(EntityDimensions.fixed(0.7F, 2.0F)).trackRangeChunks(8).fireImmune().build());

    public static final EntityType<ThunderboltEntity> THUNDERBOLT = register("thunderbolt", FabricEntityTypeBuilder.<ThunderboltEntity>create(SpawnGroup.MISC, ThunderboltEntity::new).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).trackRangeChunks(4).trackedUpdateRate(10).build());

    public static void initRestrictions() {
        SpawnRestriction.register(GOBLIN_GRUNT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoblinGruntEntity::canSpawn);
        SpawnRestriction.register(FLAPHAWK, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlaphawkEntity::canSpawn);
        SpawnRestriction.register(MOCKER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(LEVIATHAN, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);
        SpawnRestriction.register(THUNDIZARD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SMUtil::isValidSpawn);
        SpawnRestriction.register(FAIRY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SMUtil::isValidNaturalSpawn);
    }

    public static void initSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, GOBLIN_GRUNT, SMConfig.get().goblinGruntWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.SWAMP), SpawnGroup.CREATURE, PUMP_FROG, SMConfig.get().pumpFrogWeight, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(TagRegistry.HAS_FLAPHAWK), SpawnGroup.CREATURE, FLAPHAWK, SMConfig.get().flaphawkWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, MOCKER, SMConfig.get().mockerWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.OCEAN), SpawnGroup.WATER_CREATURE, LEVIATHAN, SMConfig.get().leviathanWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(TagRegistry.HAS_THUNDIZARD), SpawnGroup.CREATURE, THUNDIZARD, SMConfig.get().thundizardWeight, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.FOREST), SpawnGroup.CREATURE, FAIRY, SMConfig.get().fairyWeight, 2, 4);
    }

    public static void init() {
        addAttributes(GOBLIN_GRUNT, GoblinGruntEntity.createGoblinGruntAttributes());
        addAttributes(PUMP_FROG, PumpFrogEntity.createPumpFrogAttributes());
        addAttributes(FLAPHAWK, FlaphawkEntity.createFlaphawkAttributes());
        addAttributes(MOCKER, MockerEntity.createMockerAttributes());
        addAttributes(LEVIATHAN, LeviathanEntity.createLeviathanAttributes());
        addAttributes(THUNDIZARD, ThundizardEntity.createThundizardAttributes());
        addAttributes(FAIRY, FairyEntity.createFairyAttributes());
        addAttributes(ASCENDANT, AscendantEntity.createAscendantAttributes());

        initRestrictions();
        initSpawns();
    }
}
