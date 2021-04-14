package me.constantindev.ccl.etc.mod;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.RenderHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.*;

public class OreSimManager {


    private final long worldSeed;
    private static OreSimManager INSTANCE;


    private HashMap<Long, HashMap<OreType, List<Vec3d>>> chunkRenderers = new HashMap<>();

    public OreSimManager(long worldSeed) {
        this.worldSeed = worldSeed;
        INSTANCE = this;
    }

    public static OreSimManager getInstance() {
        return INSTANCE;
    }

    public void render() {

        assert Cornos.minecraft.player != null;
        int chunkX = Cornos.minecraft.player.chunkX;
        int chunkZ = Cornos.minecraft.player.chunkZ;

        for (int x = chunkX - 4; x <= chunkX + 4; x++) {
            for (int z = chunkZ - 4; z <= chunkZ + 4; z++) {

                long chunkKey = (long) x + ((long) z << 32);

                if (chunkRenderers.containsKey(chunkKey)) {
                    for (Vec3d pos : chunkRenderers.get(chunkKey).get(OreType.IRON)) {
                        RenderHelper.renderBlockOutline(pos, new Vec3d(1, 1, 1), 200, 50, 200, 255);
                    }
                }
            }
        }
    }

    public void reload() {

        int renderdistance = MinecraftClient.getInstance().options.viewDistance;

        assert Cornos.minecraft.player != null;
        int playerChunkX = Cornos.minecraft.player.chunkX;
        int playerChunkZ = Cornos.minecraft.player.chunkZ;

        for (int i = playerChunkX - renderdistance; i < playerChunkX + renderdistance; i++) {
            for (int j = playerChunkZ - renderdistance; j < playerChunkZ + renderdistance; j++) {
                doMathOnChunk(i, j);
            }
        }
    }

    public void doMathOnChunk(int chunkX, int chunkZ) {
        System.out.println(chunkX);
        long chunkKey = (long) chunkX + ((long) chunkZ << 32);

        if (chunkRenderers.containsKey(chunkKey)) return;
        chunkX = chunkX << 4;
        chunkZ = chunkZ << 4;

        ChunkRandom random = new ChunkRandom();
        HashMap<OreType, List<Vec3d>> h = new HashMap<>();
        WorldAccess worldAccess = MinecraftClient.getInstance().world;

        long populationSeed = random.setPopulationSeed(worldSeed, chunkX, chunkZ);

        for(Ore ore:Ore.ORES) {
            random.setDecoratorSeed(populationSeed, ore.index, ore.step);
            ArrayList<Vec3d> ores = new ArrayList<>();
            for (int i = 0; i < ore.repeat; i++) {
                int x = random.nextInt(16) + chunkX;
                int z = random.nextInt(16) + chunkZ;
                int y;
                if(ore.normal) {
                    y = random.nextInt(ore.maxY- ore.minY) + ore.minY;
                } else {
                    //DepthAverage: maxY is spread and minY is baseline
                    y = random.nextInt(ore.maxY) + random.nextInt(ore.maxY) - ore.maxY + ore.minY;
                }
                ores.addAll(generate(worldAccess, random, new BlockPos(x, y, z), new OreFeatureConfig(ore.replacable, Blocks.DIAMOND_ORE.getDefaultState(), ore.size)));
            }
            h.put(ore.name, ores);
        }
        chunkRenderers.put(chunkKey, h);
    }


    private ArrayList<Vec3d> generate(WorldAccess worldAccess, Random random, BlockPos blockPos, OreFeatureConfig oreFeatureConfig) {
        float f = random.nextFloat() * 3.1415927F;
        float g = (float) oreFeatureConfig.size / 8.0F;
        int i = MathHelper.ceil(((float) oreFeatureConfig.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d = (double) blockPos.getX() + Math.sin(f) * (double) g;
        double e = (double) blockPos.getX() - Math.sin(f) * (double) g;
        double h = (double) blockPos.getZ() + Math.cos(f) * (double) g;
        double j = (double) blockPos.getZ() - Math.cos(f) * (double) g;
        double l = (blockPos.getY() + random.nextInt(3) - 2);
        double m = (blockPos.getY() + random.nextInt(3) - 2);
        int n = blockPos.getX() - MathHelper.ceil(g) - i;
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - MathHelper.ceil(g) - i;
        int q = 2 * (MathHelper.ceil(g) + i);
        int r = 2 * (2 + i);

        for (int s = n; s <= n + q; ++s) {
            for (int t = p; t <= p + q; ++t) {
                //if(o <= worldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING, s, t)) {
                    return this.generateVeinPart(worldAccess, random, oreFeatureConfig, d, e, h, j, l, m, n, o, p, q, r);
                //}
            }
        }

        return null;
    }

    private ArrayList<Vec3d> generateVeinPart(WorldAccess worldAccess, Random random, OreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int size, int i) {

        BitSet bitSet = new BitSet(size * i * size);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int k = config.size;
        double[] ds = new double[k * 4];

        ArrayList<Vec3d> poses = new ArrayList<>();

        int n;
        double p;
        double q;
        double r;
        double s;
        for (n = 0; n < k; ++n) {
            float f = (float) n / (float) k;
            p = MathHelper.lerp(f, startX, endX);
            q = MathHelper.lerp(f, startY, endY);
            r = MathHelper.lerp(f, startZ, endZ);
            s = random.nextDouble() * (double) k / 16.0D;
            double m = ((double) (MathHelper.sin(3.1415927F * f) + 1.0F) * s + 1.0D) / 2.0D;
            ds[n * 4 + 0] = p;
            ds[n * 4 + 1] = q;
            ds[n * 4 + 2] = r;
            ds[n * 4 + 3] = m;
        }

        for (n = 0; n < k - 1; ++n) {
            if (!(ds[n * 4 + 3] <= 0.0D)) {
                for (int o = n + 1; o < k; ++o) {
                    if (!(ds[o * 4 + 3] <= 0.0D)) {
                        p = ds[n * 4 + 0] - ds[o * 4 + 0];
                        q = ds[n * 4 + 1] - ds[o * 4 + 1];
                        r = ds[n * 4 + 2] - ds[o * 4 + 2];
                        s = ds[n * 4 + 3] - ds[o * 4 + 3];
                        if (s * s > p * p + q * q + r * r) {
                            if (s > 0.0D) {
                                ds[o * 4 + 3] = -1.0D;
                            } else {
                                ds[n * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for (n = 0; n < k; ++n) {
            double u = ds[n * 4 + 3];
            if (!(u < 0.0D)) {
                double v = ds[n * 4 + 0];
                double w = ds[n * 4 + 1];
                double aa = ds[n * 4 + 2];
                int ab = Math.max(MathHelper.floor(v - u), x);
                int ac = Math.max(MathHelper.floor(w - u), y);
                int ad = Math.max(MathHelper.floor(aa - u), z);
                int ae = Math.max(MathHelper.floor(v + u), ab);
                int af = Math.max(MathHelper.floor(w + u), ac);
                int ag = Math.max(MathHelper.floor(aa + u), ad);

                for (int ah = ab; ah <= ae; ++ah) {
                    double ai = ((double) ah + 0.5D - v) / u;
                    if (ai * ai < 1.0D) {
                        for (int aj = ac; aj <= af; ++aj) {
                            double ak = ((double) aj + 0.5D - w) / u;
                            if (ai * ai + ak * ak < 1.0D) {
                                for (int al = ad; al <= ag; ++al) {
                                    double am = ((double) al + 0.5D - aa) / u;
                                    if (ai * ai + ak * ak + am * am < 1.0D) {
                                        int an = ah - x + (aj - y) * size + (al - z) * size * i;
                                        if (!bitSet.get(an)) {
                                            bitSet.set(an);
                                            mutable.set(ah, aj, al);
                                            //if (config.target.test(worldAccess.getBlockState(mutable), random)) {
                                                poses.add(new Vec3d(ah, aj, al));
                                            //}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return poses;
    }

    private static class Ore {
        OreType name;
        int index;
        int step;
        int minY;
        int maxY;
        int size;
        RuleTest replacable;
        int repeat;
        boolean normal;

        private Ore(OreType name, int index, int step, int minY, int maxY, int size, RuleTest replacable, int repeat, boolean normal) {
            this.name = name;
            this.index = index;
            this.step = step;
            this.minY = minY;
            this.maxY = maxY;
            this.size = size;
            this.replacable = replacable;
            this.repeat = repeat;
            this.normal = normal;
        }

        public static final ArrayList<Ore> ORES = new ArrayList<>(
                Arrays.asList(
                        new Ore(OreType.DIAMOND, 9, 6, 0, 16, 8, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, 1, true),
                        new Ore(OreType.REDSTONE, 8, 6, 0, 16, 8, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, 8, true),
                        new Ore(OreType.GOLD, 7, 6, 0, 32, 9, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, 2, true),
                        new Ore(OreType.IRON, 6, 6, 0, 64, 9, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, 20, true),
                        new Ore(OreType.COAL, 5, 6, 0, 128, 17, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, 20, true)
                        //new Ore(OreType.SDEBRIS,16,7,8,112,2, OreFeatureConfig.Rules.BASE_STONE_NETHER, 1, true),
                        //new Ore(OreType.LDEBRIS,15,7,16,8,3, OreFeatureConfig.Rules.BASE_STONE_NETHER, 1, false),
                        //new Ore(OreType.LAPIS,10,6,16, 16,7, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,1,false)
                )
        );
    }

    private enum OreType {
        DIAMOND,
        REDSTONE,
        GOLD,
        IRON,
        COAL,
        SDEBRIS,
        LDEBRIS,
        LAPIS,
    }
}


