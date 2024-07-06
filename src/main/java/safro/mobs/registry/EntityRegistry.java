package safro.mobs.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.entity.*;
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

    public static final EntityType<GoblinGruntEntity> GOBLIN_GRUNT = register("goblin_grunt", EntityType.Builder.create(GoblinGruntEntity::new, SpawnGroup.MONSTER).dimensions(1.4F, 2.7F).maxTrackingRange(10).build());
    public static final EntityType<PumpFrogEntity> PUMP_FROG = register("pump_frog", EntityType.Builder.create(PumpFrogEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 0.9F).maxTrackingRange(10).build());
    public static final EntityType<FlaphawkEntity> FLAPHAWK = register("flaphawk", EntityType.Builder.create(FlaphawkEntity::new, SpawnGroup.CREATURE).dimensions(1.0F, 1.0F).maxTrackingRange(12).build());
    public static final EntityType<MockerEntity> MOCKER = register("mocker", EntityType.Builder.create(MockerEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.8F).maxTrackingRange(16).build());
    public static final EntityType<LeviathanEntity> LEVIATHAN = register("leviathan", EntityType.Builder.create(LeviathanEntity::new, SpawnGroup.WATER_CREATURE).dimensions(1.6F, 1.0F).maxTrackingRange(10).build());
    public static final EntityType<ThundizardEntity> THUNDIZARD = register("thundizard", EntityType.Builder.create(ThundizardEntity::new, SpawnGroup.CREATURE).dimensions(0.4F, 0.3F).maxTrackingRange(8).build());
    public static final EntityType<FairyEntity> FAIRY = register("fairy", EntityType.Builder.create(FairyEntity::new, SpawnGroup.CREATURE).dimensions(0.5F, 1.2F).maxTrackingRange(8).build());
    public static final EntityType<AscendantEntity> ASCENDANT = register("ascendant", EntityType.Builder.create(AscendantEntity::new, SpawnGroup.MONSTER).dimensions(0.7F, 2.0F).maxTrackingRange(8).makeFireImmune().build());

    public static final EntityType<ThunderboltEntity> THUNDERBOLT = register("thunderbolt", EntityType.Builder.<ThunderboltEntity>create(ThunderboltEntity::new, SpawnGroup.MISC).dimensions(0.3F, 0.3F).maxTrackingRange(4).trackingTickInterval(10).build());

    public static void initRestrictions() {
        SpawnRestriction.register(GOBLIN_GRUNT, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoblinGruntEntity::canSpawn);
        SpawnRestriction.register(FLAPHAWK, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlaphawkEntity::canSpawn);
        SpawnRestriction.register(MOCKER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(LEVIATHAN, SpawnLocationTypes.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn);
        SpawnRestriction.register(THUNDIZARD, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SMUtil::isValidSpawn);
        SpawnRestriction.register(FAIRY, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SMUtil::isValidNaturalSpawn);
        SpawnRestriction.register(ASCENDANT, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
    }

    public static void initSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, GOBLIN_GRUNT, SMConfig.get().goblinGruntWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.IS_SWAMP), SpawnGroup.CREATURE, PUMP_FROG, SMConfig.get().pumpFrogWeight, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(TagRegistry.HAS_FLAPHAWK), SpawnGroup.CREATURE, FLAPHAWK, SMConfig.get().flaphawkWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.MONSTER, MOCKER, SMConfig.get().mockerWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.IS_AQUATIC), SpawnGroup.WATER_CREATURE, LEVIATHAN, SMConfig.get().leviathanWeight, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.tag(TagRegistry.HAS_THUNDIZARD), SpawnGroup.CREATURE, THUNDIZARD, SMConfig.get().thundizardWeight, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.IS_FOREST), SpawnGroup.CREATURE, FAIRY, SMConfig.get().fairyWeight, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.IS_NETHER), SpawnGroup.MONSTER, ASCENDANT, SMConfig.get().ascendantWeight, 1, 1);
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
