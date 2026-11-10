package com.onesignal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenerateNotificationSampleSizeTests {

   @Test
   public void bitmapSmallerThanMax_isNotSampled() {
      assertEquals(1, GenerateNotification.calculateInSampleSize(100, 50, 200, 200));
   }

   @Test
   public void noLimit_isNotSampled() {
      assertEquals(1, GenerateNotification.calculateInSampleSize(4000, 3000, 0, 0));
   }

   @Test
   public void invalidBounds_isNotSampled() {
      assertEquals(1, GenerateNotification.calculateInSampleSize(-1, -1, 100, 100));
   }

   @Test
   public void widthOnlyLimit_sampledToAtLeastMaxWidth() {
      // 4000 -> 2000 -> 1000 would go below 1024, so the sample size is 2
      assertEquals(2, GenerateNotification.calculateInSampleSize(4000, 3000, 1024, 0));
   }

   @Test
   public void fitInsideBox_neverGoesBelowScaledTarget() {
      // 4000x3000 fitted in 1000x64 gives 85x64, so it can be sampled until 125x93 (sample size 32)
      assertEquals(32, GenerateNotification.calculateInSampleSize(4000, 3000, 1000, 64));
   }

   @Test
   public void largeIconArea_keepsEnoughPixelsForFinalScale() {
      // 1000x500 into a 64x64 box gives 64x32, 1000/8=125 and 500/8=62 are still >= target
      assertEquals(8, GenerateNotification.calculateInSampleSize(1000, 500, 64, 64));
   }
}
