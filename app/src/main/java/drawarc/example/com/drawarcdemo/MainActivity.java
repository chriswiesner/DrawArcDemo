package drawarc.example.com.drawarcdemo;

import android.os.Bundle;
import android.support.v4.app.FrameMetricsAggregator;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private ArcView arcView;
	private FrameMetricsAggregator aggregator;
	private TextView slowFramesTextLabel;
	private SeekBar slider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		arcView = findViewById(R.id.arc_view);
		slowFramesTextLabel = findViewById(R.id.slow_frames_label);

		slider = findViewById(R.id.slider);
		slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
				arcView.setAngle(360 * progress / 100);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		arcView.setAngle(360 * slider.getProgress() / 100);
	}

	public void onStartTracingClicked(View v) {
		startPerformanceTracing();
		slowFramesTextLabel.setText("Tracing...");
	}

	public void onStopTracingClicked(View v ) {
		stopPerformanceTracing();
	}

	private void startPerformanceTracing() {
		aggregator = new FrameMetricsAggregator(FrameMetricsAggregator.TOTAL_DURATION);
		aggregator.add(this);
	}

	private void stopPerformanceTracing() {
		SparseIntArray[] stop = aggregator.stop();
		aggregator.reset();

		int slowCount = 0;
		for (int i= 0; i< stop.length;i++) {
			if (stop[FrameMetricsAggregator.TOTAL_INDEX].valueAt(i) > 16) {
				slowCount++;
			}
		}
		slowFramesTextLabel.setText(getString(R.string.slow_frames,slowCount));
	}
}
