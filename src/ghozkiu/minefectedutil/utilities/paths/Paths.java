package ghozkiu.minefectedutil.utilities.paths;

public enum Paths {

    CONFIG_RELOADED("Lang.config_reloaded"),
    BODY_INFECTED("Lang.body_infected"),
    WERE_CURE("Lang.were_cure"),
    FRIENDLYF("Config.friendly_fire"),
    BLEEDING("Lang.bleeding"),
    MAP("Lang.map"),
    BLEEDING_STOPED("Lang.bleeding_has_stoped"),
    JustCutTittle("Lang.just_amputated_tittle"),
    JustCutSubtittle("Lang.just_amputated_subtittle"),
    CANT_AMPUTATE("Lang.cant_amputate"),
    NOMORE("Lang.no_more"),
    AMPUTATED("Lang.amputated"),
    ARM_INFECTED("Lang.arm_infected"),
    LEG_INFECTED("Lang.leg_infected"),
    CANT_USE_ANTIBIOTICS("Lang.cant_use_antibiotics"),
    PROSTHESIS("Lang.prosthesis"),
    LEG_PROTHESIS("Lang.should_use_leg_prothesis"),
    ARM_PROTHESIS("Lang.should_use_arm_prothesis"),
    HELMET_EQUIPED("Lang.helmet_equiped"),
    HELMET_FAILED("Lang.helmet_not_equiped"),
    INFECTION("Config.infection"),
    PLAYER_DATA_COLLECTOR("Config.player_data_collector"),
    IS_THIRST_ENABLED("Thirst.thirst-enable"),
    THIRST_TITTLE("Thirst.set-title"),
    THIRST_SAVE_COOLDOWN("Thirst.data-save-cooldown"),
    THIRST_TIMER("Thirst.timer"),
    JOIN_WARMUP("Thirst.join-warmup"),
    THIRST_ENABLED_WORLDS("Thirst.enabled-worlds"),
    MIN_THIRST_RANDOM_INTERVAL("Thirst.min-random-interval"),
    MAX_THIRST_RANDOM_INTERVAL("Thirst.max-random-interval"),
    WAS_ONLINE("Thirst.was-online-limit"),
    WATER_GAIN("Thirst.water-gain"),
    MILK_GAIN("Thirst.milk-gain"),
    DRINKS_DATA_SAVE_COOLDOWN("Drinks.data-save-cooldown"),
    THIRST_REMAINDER("Lang.thirst-remainder"),
    THIRST_RANDOM_EFFECT_ONTIMER("Thirst.thirst-random-effect-ontimer"),
    THIRST_REMAINDER_TIMER("Thirst.remainder-timer");
    private final String type;

    Paths(String param) {
        this.type = param;
    }

    public String get() {
        return this.type;
    }
}
