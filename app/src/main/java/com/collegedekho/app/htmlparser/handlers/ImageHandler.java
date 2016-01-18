/*
 * Copyright (C) 2011 Alex Kuiper <http://www.nightwhistler.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.collegedekho.app.htmlparser.handlers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;


import com.collegedekho.app.htmlparser.SpanStack;
import com.collegedekho.app.htmlparser.TagNodeHandler;

import org.htmlcleaner.TagNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Handles image tags.
 * 
 * The default implementation tries to load images through a URL.openStream(),
 * override loadBitmap() to implement your own loading.
 * 
 * @author Alex Kuiper
 * 
 */
public class ImageHandler extends TagNodeHandler {

	@Override
	public void handleTagNode(TagNode node, final SpannableStringBuilder builder,
							 final int start, int end, final SpanStack stack) {
		String src = node.getAttributeByName("src");

		builder.append("\uFFFC");

		Bitmap bitmap=loadBitmap(src);
		if (bitmap != null) {
			Drawable drawable = new BitmapDrawable(bitmap);
			drawable.setBounds(0, 0, bitmap.getWidth() - 1,
					bitmap.getHeight() - 1);

            stack.pushSpan( new ImageSpan(drawable), start, builder.length() );
		}
	}

	/**
	 * Loads a Bitmap from the given url.
	 * 
	 * @param url
	 * @return a Bitmap, or null if it could not be loaded.
	 */
	protected Bitmap loadBitmap(String url) {
		try {
            try {
                return new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                     protected Bitmap doInBackground(String... params) {
                         try {
                         return BitmapFactory.decodeStream(new URL(params[0]).openStream());
                     } catch (IOException io) {
                         return null;
                     }
                     }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);

                    }
                }.execute(url).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return BitmapFactory.decodeStream(new URL(url).openStream());
		} catch (IOException io) {
			return null;
		}
	}

}
