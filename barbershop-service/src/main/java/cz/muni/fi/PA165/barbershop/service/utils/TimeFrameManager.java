package cz.muni.fi.PA165.barbershop.service.utils;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Konstantin Yarovoy
 */
public class TimeFrameManager {
    public static List<TimeFrame> joinTimeFrames(List<TimeFrame> timeFrames) {
        List<TimeFrame> frames = timeFrames.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
        Pair<Integer, Integer> intersection;
        Integer fit;
        do {
            intersection = null;
            fit = null;
            for(int i = 0; i < frames.size() - 1; i++) {
                var thisFrame = frames.get(i);
                var nextFrame = frames.get(i + 1);
                if (nextFrame.fitsCompletely(thisFrame)) {
                    fit = i + 1;
                    break;
                }
                if (thisFrame.haveIntersection(nextFrame)) {
                    intersection = Pair.of(i, i + 1);
                    break;
                }
            }
            if (fit != null) {
                frames.remove(fit.intValue());
            } else if (intersection != null) {
                var newFrame = new TimeFrame(frames.get(intersection.getFirst()).getFromTime(), frames.get(intersection.getSecond()).getToTime());
                frames.remove(intersection.getSecond().intValue());
                frames.remove(intersection.getFirst().intValue());
                frames.add(intersection.getFirst(), newFrame);
            }
        } while (intersection != null || fit != null);
        return frames;
    }

    public static List<TimeFrame> eraseTimeFromTimeFrames(List<TimeFrame> inputFrames, List<TimeFrame> toErase) {
        var frames = new ArrayList<>(inputFrames);
        for (TimeFrame eraser: toErase) {
            Integer divide = null;
            for(int i = 0; i < frames.size(); i++) {
                if (eraser.fitsCompletely(frames.get(i))) {
                    divide = i;
                    break;
                }
            }
            if (divide != null) {
                var toDivide = frames.get(divide);
                frames.remove(divide.intValue());
                if (!eraser.getToTime().equals(toDivide.getToTime())) {
                    //snd
                    frames.add(divide, new TimeFrame(eraser.getToTime(), toDivide.getToTime()));
                }
                if (!toDivide.getFromTime().equals(eraser.getFromTime())) {
                    //fst
                    frames.add(divide, new TimeFrame(toDivide.getFromTime(), eraser.getFromTime()));
                }
            }
        }
        return frames;
    }
}
