package com.poqop.document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.vudroid.pdfdroid.PdfViewerActivity;

import com.poqop.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class ITestActivity extends Activity {
	private File mPath;
	private File file;
	private boolean isLock = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		findViewById(R.id.open).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (file != null && !isLock) {
					openPdf(file);
				}
			}
		});
		AssetManager assetManager = getAssets();
		try {
			isLock = true;
			InputStream open = assetManager.open("test.pdf");
			// OutputStream out = new Filio
			byte[] buffer = new byte[1024];
			int length = 0;
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				mPath = Environment.getExternalStorageDirectory();
			} else {
				mPath = getBaseContext().getFilesDir();
			}
			file = new File(mPath, "test.pdf");
			FileOutputStream out = new FileOutputStream(file);
			while ((length = open.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			
			isLock = false;
			open.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openPdf(File file) {
		Uri uri = Uri.fromFile(file);
		final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.setClass(this, PdfViewerActivity.class);
		startActivity(intent);
	}

}
