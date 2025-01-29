package org.pedalaq.Services;

public class DistanceUtil {

    private static final double EARTH_RADIUS_KM = 6371.01;
    // Raggio della Terra in chilometri

    // calcolo della distanza con la formula dell'emisenoverso
    public static double calculateDistance(Double lat1, Double lon1,Double lat2, Double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calcolo la differenza delle latitudini e delle longitudini
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Applico la formula dell'emisenoverso
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcolo la distanza
        return EARTH_RADIUS_KM * c;

    }
}
