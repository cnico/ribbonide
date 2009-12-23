/*******************************************************************************
 * Copyright (c) 2009, Elias Volanakis
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Elias Volanakis - initial API and implementation
 *******************************************************************************/
package de.volanakis.ribbonide.internal.presentation;

import org.eclipse.swt.graphics.Font;

public interface IThemeCallback {

	void setColors(DefaultTabFolderColors colors, int activationState);

	void setColors(DefaultTabFolderColors colors, int activationState,
			boolean shellActivationState);

	void setFont(Font font);

}
