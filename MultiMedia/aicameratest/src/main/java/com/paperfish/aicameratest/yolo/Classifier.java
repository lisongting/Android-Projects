package com.paperfish.aicameratest.yolo;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by lisongting on 2017/8/23.
 */

public interface Classifier {

    List<Recognition> recognizeImage(Bitmap bitmap);

    public class Recognition{

        private final String id;

        private final String title;

        private final Float confidence;

        private final Rect location;

        public Recognition(String id, String title, Float confidence, Rect location) {
            this.id = id;
            this.title = title;
            this.confidence = confidence;
            this.location = location;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Float getConfidence() {
            return confidence;
        }

        public Rect getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return "Recognition{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", confidence=" + confidence +
                    ", location=" + location +
                    '}';
        }
    }
}
