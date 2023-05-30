/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.datasketches.kll;

import static org.apache.datasketches.kll.KllSketch.Error.TGT_IS_READ_ONLY;
import static org.apache.datasketches.kll.KllSketch.Error.kllSketchThrow;

abstract class KllGenericProxy<T> extends KllSketch {

  KllGenericProxy() {
    super(SketchType.ITEMS_SKETCH, null, null);
  }

  /**
   * @return full size of internal items array including garbage.
   */
  abstract T[] getItemsArray();

  abstract T getSingleItem();

  abstract T getMaxItem();

  abstract T getMinItem();

  abstract void setItemsArray(T[] Items);

  abstract void setItemsArrayAt(int index, T item);

  abstract void setMaxItem(T item);

  abstract void setMinItem(T item);

  /**
   * Merges another sketch into this one.
   * Attempting to merge a KllDoublesSketch with a KllFloatsSketch will
   * throw an exception.
   * @param other sketch to merge into this one
   */
  public final void merge(final KllItemsSketch<T> other) {
    if (readOnly) { kllSketchThrow(TGT_IS_READ_ONLY); }
    //KllItemsHelper.mergeItemImpl((KllItemsSketch)this, other); //TODO
  }

  /**
   * {@inheritDoc}
   * <p>The parameter <i>k</i> will not change.</p>
   */
  @SuppressWarnings("unchecked")
  @Override
  public final void reset() {
    if (readOnly) { kllSketchThrow(TGT_IS_READ_ONLY); }
    final int k = getK();
    setN(0);
    setMinK(k);
    setNumLevels(1);
    setLevelZeroSorted(false);
    setLevelsArray(new int[] {k, k});
    setMinItem(null);
    setMaxItem(null);
    setItemsArray((T[])(new Object[k]));
  }
}
