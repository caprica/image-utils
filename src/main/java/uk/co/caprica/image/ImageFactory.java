/*
 * This file is part of image-utils.
 *
 * image-utils is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * image-utils is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with image-utils.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2014 Caprica Software Limited.
 */

package uk.co.caprica.image;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import com.jhlabs.image.BorderFilter;
import com.jhlabs.image.TileImageFilter;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Factory for images.
 * <p>
 * The hard work is done by third-party libraries, this factory is a simple wrapper.
 */
public class ImageFactory {

    /**
     * Fit an image to the specified width and height.
     * <p>
     * The aspect ratio of the image will <em>not</em> be preserved.
     *
     * @param sourceImage
     * @param targetWidth
     * @param targetHeight
     * @return
     */
	public static BufferedImage fitImage(BufferedImage sourceImage, Integer targetWidth, Integer targetHeight) {
        ResampleOp resizeOp = new ResampleOp(targetWidth, targetHeight);
        return resizeOp.filter(sourceImage, null);
	}

	/**
	 * Scale an image the specified width and height.
	 * <p>
	 * The aspect ratio of the image will be preserved.
	 *
	 * @param sourceImage
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
    public static BufferedImage scaleImage(BufferedImage sourceImage, Integer targetWidth, Integer targetHeight) {
        float sx = targetWidth / (float) sourceImage.getWidth();
        float sy = targetHeight / (float) sourceImage.getHeight();
        float sf = Math.min(sx, sy);
        targetWidth = (int) Math.ceil(sf * sourceImage.getWidth());
        targetHeight = (int) Math.ceil(sf * sourceImage.getHeight());
        ResampleOp resizeOp = new ResampleOp(targetWidth, targetHeight);
        return resizeOp.filter(sourceImage, null);
    }

    /**
     * Tile an image to fill the specified width and height.
     *
     * @param originalImage
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage tileImage(BufferedImage originalImage, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        TileImageFilter filter = new TileImageFilter(width, height);
        g2.drawImage(originalImage, filter, 0, 0);
        return image;
    }

    /**
     * Add a border to an image.
     * <p>
     * The image dimensions will be increased to accommodate the new border.
     *
     * @param originalImage
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param paint
     * @return
     */
    public static BufferedImage borderImage(BufferedImage originalImage, int left, int top, int right, int bottom, Paint paint) {
        BufferedImage image = new BufferedImage(left + originalImage.getWidth() + right, top + originalImage.getHeight() + bottom, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        BorderFilter filter = new BorderFilter(left, top, right, bottom, paint);
        g2.drawImage(originalImage, filter, 0, 0);
        return image;
    }
}