package net.yscs.android.square_progressbar_example;

import net.yscs.android.square_progressbar.utils.PercentSettings;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint.Align;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class PercentDialog extends Dialog {

	private final Spinner spinner;
	private final CheckBox box;
	private final Button saveButton;
	private final SeekBar bar;
	private int size;
	private final PreviewView previewView;

	public PercentDialog(final Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.percentdialog);
		this.setCancelable(false);
		spinner = (Spinner) this.findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, R.array.alignstyle,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		previewView = (PreviewView) findViewById(R.id.previewView1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				redraw();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// nothing to do =)
			}
		});

		Button closeButton = (Button) this.findViewById(R.id.returnDialog);
		closeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();

			}
		});
		saveButton = (Button) this.findViewById(R.id.shareDialog);

		final TextView progress = (TextView) findViewById(R.id.textView3);

		bar = (SeekBar) findViewById(R.id.textSize);
		bar.setMax(400);
		bar.setProgress(125);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// nothing to do =)
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// nothing to do =)

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				size = arg1;
				progress.setText(arg1 + " dp");
				redraw();
			}
		});

		box = (CheckBox) this.findViewById(R.id.checkBox1);
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				redraw();
			}
		});

		previewView.drawText(150, Align.CENTER, true);
	}

	public PercentSettings getSettings() {
		return new PercentSettings(Align.valueOf((String) spinner
				.getSelectedItem()), Float.valueOf(bar.getProgress()),
				box.isChecked());
	}

	public Button getSaveButton() {
		return saveButton;
	}

	private Align returnAlign(int arg2) {
		switch (arg2) {
		case 0:
			return Align.CENTER;
		case 1:
			return Align.RIGHT;
		case 2:
			return Align.LEFT;
		default:
			return Align.CENTER;
		}
	}

	private void redraw() {
		previewView
				.drawText(size, returnAlign(spinner.getSelectedItemPosition()),
						box.isChecked());
	}

	public void setSettings(PercentSettings settings) {
		switch (settings.getAlign()) {
		case CENTER:
			spinner.setSelection(0);
			break;
		case RIGHT:
			spinner.setSelection(1);
			break;
		case LEFT:
			spinner.setSelection(2);
			break;
		default:
			spinner.setSelection(0);
			break;
		}

		bar.setProgress(Math.round(settings.getTextSize()));
		box.setChecked(settings.isPercentSign());

	}
}
