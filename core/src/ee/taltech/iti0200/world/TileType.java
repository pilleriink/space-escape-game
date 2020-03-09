package ee.taltech.iti0200.world;

import java.util.HashMap;
import java.util.HashSet;

public enum TileType {

    UPLEFT_BLUE_TERRAIN(1, false, "Up left blue terrain"),
    UP_BLUE_TERRAIN(2, false, "Upper blue terrain"),
    UP_RIGHT_BLUE_TERRAIN(3, false, "Up right blue terrain"),
    BOTTOM_RIGHT_BLUE_TERRAIN_BITS(4, false, "Bottom right blue terrain bits"),
    BOTTOM_LEFT_BLUE_TERRAIN_BITS(5, false, "Bottom left blue terrain bits"),
    EMPTY1(6, false, "empty1"),
    SMALL_PLATFORM(7, true, "Small platform"),
    EMPTY2(8, false, "empty2"),
    EMPTY3(9, false, "empty3"),
    UP_PLATFORMDRIP(10, false, "Upper platform drip"),
    EMPTY4(11, false, "empty4"),
    BLUE_BACKGROUND(12, false, "Blue background"),
    LEFT_BLUE_TERRAIN(13, false, "Left blue terrain"),
    empty5(14, false, "empty5"),
    RIGHT_BLUE_TERRAIN(15, false, "Right blue terrain"),
    UPPER_RIGHT_BLUE_TERRAIN_BITS(16, false, "Upper right blue terrain bits"),
    UPPER_LEFT_BLUE_TERRAIN_BITS(17, false, "Upper left blue terrain bits"),
    PLATFORM_BLUE_MIDDLE(18, true, "Platform blue middle"),
    PLATFORM_BLUE_LEFT(19, true, "platform blue left"),
    PLATFORM_BLUE_RIGHT(20, true, "platform blue right"),
    PLATFORM_DRIPS_LEFT(21, false, "platform drips left"),
    PLATFORM_DRIPS_MIDDLE(22, false, ""),
    PLATFORM_DRIPS_RIGHT(23, false, ""),
    EMPTY6(24, false, ""),
    BOTTOM_LEFT_BLUE_TERRAIN(25, false, ""),
    BOTTOM_BLUE_TERRAIN(26, false, ""),
    BOTTOM_RIGHT_BLUE_TERRAIN(27, false, ""),
    BLUE_CEILING_DRIPS_SMALL(28, false, ""),
    BLUE_CEILING_DRIPS_MEDIUM_ONE(29, false, ""),
    BLUE_CEILING_DRIPS_MEDIUM_TWO(30, false, ""),
    BLUE_CEILING_DRIPS_BIG(31, false, ""),
    EMPTY7(32, false, ""),
    EMPTY8(33, false, ""),
    PLATFORM_DRIP_THIN(34, false, ""),
    EMPTY9(35, false, ""),
    EMPTY10(36, false, ""),
    UPLEFT_PURPLE_TERRAIN(37, false, ""),
    UP_PURPLE_TERRAIN(38, false, ""),
    UP_RIGHT_PURPLE_TERRAIN(39, false, ""),
    BOTTOM_RIGHT_PURPLE_TERRAIN_BITS(40, false, ""),
    BOTTOM_LEFT_PURPLE_TERRAIN_BITS(41, false, ""),
    PLATFORM_PURPLE_LEFT(42, true, ""),
    PLATFORM_PURPLE_MIDDLE(43, true, ""),
    PLATFORM_PURPLE_RIGHT(44, true, ""),
    PURPLE_TOWER_TOP(45, false, ""),
    EMPTY11(46, false, ""),
    EMPTY12(47, false, ""),
    PURPLE_BACKGROUND(48, false, ""),
    LEFT_PURPLE_TERRAIN(49, false, ""),
    EMPTY13(50, false, ""),
    RIGHT_PURPLE_TERRAIN(51, false, ""),
    UPPER_RIGHT_PURPLE_TERRAIN_BITS(52, false, ""),
    UPPER_LEFT_PURPLE_TERRAIN_BITS(53, false, ""),
    EMPTY14(54, false, ""),
    EMPTY15(55, false, ""),
    EMPTY16(56, false, ""),
    PURPLE_TOWER_MIDDLE(57, false, ""),
    EMPTY17(58, false, ""),
    EMPTY18(59, false, ""),
    EMPTY19(60, false, ""),
    BOTTOM_LEFT_PURPLE_TERRAIN(61, false, ""),
    BOTTOM_PURPLE_TERRAIN(62, false, ""),
    BOTTOM_RIGHT_PURPLE_TERRAIN(63, false, ""),
    EMPTY20(64, false, ""),
    EMPTY21(65, false, ""),
    EMPTY22(66, false, ""),
    EMPTY23(67, false, ""),
    EMPTY24(68, false, ""),
    PURPLE_TOWER_BOTTOM(69, false, ""),
    EMPTY25(70, false, ""),
    EMPTY26(71, false, ""),
    EMPTY27(72, false, ""),
    SMALL_THORNS_LEFT(73, true, "", 1),
    SMALL_THORNS_RIGHT(74, true, ", 1"),
    BIG_THORNS_LEFT(75, true, "", 1),
    EMPTY28(76, false, ""),
    BIG_THORNS_RIGHT(77, true, "", 1),
    GREEN_WEED_BIG_RIGHT(78, false, ""),
    GREEN_WEED_SMALL_LEFT(79, false, ""),
    GREEN_WEED_SMALL_RIGHT(80, false, ""),
    GREEN_WEED_BIG_LEFT(81, false, ""),
    EMPTY29(82, false, ""),
    EMPTY30(83, false, ""),
    EMPTY31(84, false, ""),
    SMALL_THORNS_BOTTOM_LEFT(85, true, "", 1),
    SMALL_THORNS_BOTTOM_RIGHT(86, true, "", 1),
    BIG_THORNS_BOTTOM_LEFT(87, true, "", 1),
    BIG_THORNS_BOTTOM(88, true, "", 1),
    BIG_THORNS_BOTTOM_RIGHT(89, true, "", 1),
    WATER_HALF(90, false, ""),
    WATER_FULL(91, false, ""),
    EMPTY32(92, false, ""),
    EMPTY33(93, false, ""),
    EMPTY34(94, false, ""),
    EMPTY35(95, false, ""),
    EMPTY36(96, false, ""),
    LONG_LEAVES(97, false, ""),
    SMALL_LEAVES(98, false, ""),
    PURPLE_LEAVES(99, false, ""),
    EMPTY37(100, false, ""),
    EMPTY38(101, false, ""),
    EMPTY39(102, false, ""),
    EMPTY40(103, false, ""),
    EMPTY41(104, false, ""),
    EMPTY42(105, false, ""),
    EMPTY43(106, false, ""),
    EMPTY44(107, false, ""),
    EMPTY45(108, false, ""),
    CHEST(109, true, ""),
    SMALL_THORNS_BOTTOM(110, true, "", 1),
    DUST(101, false, ""),
    WEIRD_ROCK(102, true, ""),
    TELEPORT(103, false, ""),
    BLUE_LIGHT(104, false, ""),
    GREEN_LIGHT(105, false, ""),
    EMPTY46(106, false, ""),
    EMPTY47(107, false, ""),
    EMPTY48(108, false, ""),
    EMPTY49(109, false, ""),
    EMPTY50(110, false, ""),
    BLACK_BACKGROUND(121, true, ""),
    EMPTY51(112, false, ""),
    EMPTY52(113, false, ""),
    EMPTY53(114, false, ""),
    EMPTY54(115, false, ""),
    EMPTY55(116, false, ""),
    EMPTY56(117, false, ""),
    EMPTY57(118, false, ""),
    EMPTY58(119, false, ""),
    EMPTY59(120, false, ""),
//    EMPTY60(121, false, ""),
    EMPTY61(122, false, ""),
    EMPTY_COLLISION(132, true, "");









//    GRASS(1, true, "Grass"),
//    DIRT(2, true, "Dirt"),
//    SKY(3, false, "Sky"),
//    LAVA(4, true, "Lava"),
//    CLOUD(5, true, "Cloud"),
//    STONE(6, true, "Stone");

    public static final int TILE_SIZE = 16;


    private int id;
    private boolean collision;
    private String name;
    private float damage;


    private TileType (int id, boolean collision, String name) {
        this(id, collision, name, 0);
    }

    private TileType(int id, boolean collision, String name, float damage) {
        this.id = id;
        this.collision = collision;
        this.name = name;
        this.damage = damage;
    }

    public int getId() {
        return id;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    private static HashMap<Integer, TileType> tileMap = new HashMap<>();

    static {
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }
}
