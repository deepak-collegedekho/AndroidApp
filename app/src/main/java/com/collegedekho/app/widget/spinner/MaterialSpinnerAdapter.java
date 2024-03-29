/*
 * Copyright (C) 2016 Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.collegedekho.app.widget.spinner;

import android.content.Context;

import java.util.List;

public class MaterialSpinnerAdapter<T> extends MaterialSpinnerBaseAdapter {

  private final List<T> items;

  public MaterialSpinnerAdapter(Context context, List<T> items, boolean isNothingSelected) {
    super(context);
    this.items = items;
    isItemSelected = isNothingSelected;
  }

  @Override public int getCount() {
    if(isItemSelected)
      return  items.size();
    else
    return items.size() - 1;
  }

  @Override public T getItem(int position) {
    if (position >= getSelectedIndex() && !isItemSelected) {
      return items.get(position + 1);
    } else {
      return items.get(position);
    }
  }

  @Override public T get(int position) {
      if(position < 0)
        position =0;
      return items.get(position);
  }



}