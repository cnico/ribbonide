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
package de.volanakis.ribbonide.internal;

import org.eclipse.ui.IWorkbenchWindow;

import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.hexapixel.widgets.ribbon.RibbonTab;

public final class SyncTabBuilder extends AbstractTabBuilder {

	private final IWorkbenchWindow window;

	public SyncTabBuilder(AbstractWindowBuilder shellBuilder) {
		super(shellBuilder, "Sync",
				"org.eclipse.team.ui.TeamSynchronizingPerspective");
		window = shellBuilder.getWindow();
	}

	@Override
	protected void fillRibbonTab(RibbonTab tab) {
		// createGroupSync(tab);
	}

	private void createGroupSync(RibbonTab tab) {
		RibbonGroup group = new RibbonGroup(tab, "Sync");

		// TODO [ev] show the following buttons
		// - add cvs repository
		// - most used button from sync view
	}

}
