package Game.biomes;

import java.util.ArrayList;

/**
 * This is the abstract biome class where all biomes start. A biome is a very simple
 * class that will only be used in world generation. It contains everything about where
 * and when a biome will spawn.
 */
public class Biome {
    private static ArrayList<Biome> allBiomes = new ArrayList<>();

    private final String name;
    private final String requiredTemperature;
    private final String requiredHumidity;
    private final String requiredElevation;

    public Biome(String name, String requiredTemperature, String requiredHumidity, String requiredElevation) {
        this.name = name;
        this.requiredTemperature = requiredTemperature;
        this.requiredHumidity = requiredHumidity;
        this.requiredElevation = requiredElevation;

        allBiomes.add(this);
    }

    public boolean canSpawn(String elevationRange, String temperatureRange, String humidityRange){
        return isRightElevation(elevationRange) && isRightHumidity(humidityRange) && isRightTemperature(temperatureRange);
    }

    /**
     * Determines if the humidity is within the acceptable range for this biome.
     */
    boolean isRightHumidity(String humidityRange) {
        return requiredHumidity.contains(humidityRange);
    }

    /**
     * Determines if the temperature is within the acceptable range for this biome.
     */
    boolean isRightTemperature(String temperatureRange) {
        return requiredTemperature.contains(temperatureRange);
    }

    /**
     * Determines if the elevation is within the acceptable range for this biome.
     */
    boolean isRightElevation(String elevationRange) {
        return requiredElevation.contains(elevationRange);
    }



    public static ArrayList<Biome> getAllBiomes() {
        return allBiomes;
    }

    public String getName() {
        return name;
    }

    public String getRequiredTemperature() {
        return requiredTemperature;
    }

    public String getRequiredHumidity() {
        return requiredHumidity;
    }

    public String getRequiredElevation() {
        return requiredElevation;
    }
}
