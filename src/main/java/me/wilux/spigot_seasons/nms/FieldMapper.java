package me.wilux.spigot_seasons.nms;

import net.minecraft.server.v1_16_R3.*;

import java.lang.reflect.Field;
import java.util.Optional;

public class FieldMapper<T> {
    private String fieldName;

    public static final class BiomeFog {
        public static final FieldMapper<Integer> fogColor = new FieldMapper<>("b") ;
        public static final FieldMapper<Integer> waterColor = new FieldMapper<>("c");
        public static final FieldMapper<Integer> waterFogColor = new FieldMapper<>("d");
        public static final FieldMapper<Integer> skyColor = new FieldMapper<>("e");
        public static final FieldMapper<Optional<Integer>> foliageColor = new FieldMapper<>("f");
        public static final FieldMapper<Optional<Integer>> grassColor = new FieldMapper<>("g");
        public static final FieldMapper<net.minecraft.server.v1_16_R3.BiomeFog.GrassColor> grassColorModifier = new FieldMapper<>("h");
        public static final FieldMapper<Optional<BiomeParticles>> particle = new FieldMapper<>("i");
        public static final FieldMapper<Optional<SoundEffect>> ambientSound = new FieldMapper<>("j");
        public static final FieldMapper<Optional<CaveSoundSettings>> moodSound = new FieldMapper<>("k");
        public static final FieldMapper<Optional<CaveSound>> additionsSound = new FieldMapper<>("l");
        public static final FieldMapper<Optional<Music>> music = new FieldMapper<>("m");
    }

    public FieldMapper(String fieldName){
        this.fieldName = fieldName;
    }

    public T getFrom(Object obj){
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (T)(f.get(obj));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setIn(Object obj, T setValue){
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj,setValue);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
