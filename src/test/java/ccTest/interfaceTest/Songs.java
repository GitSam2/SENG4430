package ccTest.interfaceTest;

import java.util.List;

public interface Songs {
    default void printPlaylist(List<String> tracks) {
        for (String track : tracks) {
            System.out.println("Playing: " + track);
        }
    }
    static boolean isValidTitle(String title) {
        return title != null && !title.isEmpty();
    }
    void play();
    default String getGenre(int bpm) {
        if (bpm > 150) {
            return "Drum and Bass";
        } else if (bpm > 120) {
            return "House";
        }
        return "Ambient";
    }
}
