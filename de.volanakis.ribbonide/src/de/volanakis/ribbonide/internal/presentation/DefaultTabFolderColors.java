/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.volanakis.ribbonide.internal.presentation;

import org.eclipse.swt.graphics.Color;

/**
 * @since 3.1
 */
public final class DefaultTabFolderColors {

	public Color foreground;
	public int[] percentages;
	public Color[] background;
	public boolean vertical;

	public DefaultTabFolderColors() {
		this(null, null, null, false);
	}

	public DefaultTabFolderColors(Color fgColor, Color[] bgColors,
			int[] percentages, boolean vertical) {

		foreground = fgColor;
		background = bgColors;
		this.percentages = percentages;
		this.vertical = vertical;
	}

	public DefaultTabFolderColors setForeground(Color fg) {
		foreground = fg;
		return this;
	}

	public DefaultTabFolderColors setBackground(Color[] background,
			int[] percentages, boolean vertical) {
		this.background = background;
		this.percentages = percentages;
		this.vertical = vertical;
		return this;
	}
}
