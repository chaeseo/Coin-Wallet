package org.tensorflow.lite.coin.detection.customview;

import java.util.List;
import org.tensorflow.lite.coin.detection.tflite.Classifier.Recognition;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
