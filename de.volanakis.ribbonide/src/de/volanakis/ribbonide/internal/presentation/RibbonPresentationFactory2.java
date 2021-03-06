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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.presentations.util.StandardViewSystemMenu;
import org.eclipse.ui.internal.presentations.util.TabbedStackPresentation;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.StackPresentation;
import org.eclipse.ui.presentations.WorkbenchPresentationFactory;

import de.volanakis.ribbonide.internal.Activator;
import de.volanakis.ribbonide.internal.SharedColors;

/**
 * TODO [ev] javadoc
 * 
 * @see WorkbenchPresentationFactory
 */
public class RibbonPresentationFactory2 extends WorkbenchPresentationFactory {

	private static final MouseTrackListener SASH_HOVER_LISTENER = new SashHoverListener();

	@Override
	public StackPresentation createEditorPresentation(Composite parent,
			IStackPresentationSite site) {
		StackPresentation presentation = super.createEditorPresentation(parent,
				site);
		parent.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		return presentation;
	}

	@Override
	public StackPresentation createStandaloneViewPresentation(Composite parent,
			IStackPresentationSite site, boolean showTitle) {
		// TODO [ev] get rid of RibbonStackPresentation
		return new RibbonStackPresentation(parent, site);
	}

	@Override
	public StackPresentation createViewPresentation(Composite parent,
			IStackPresentationSite site) {
		// see WorkbenchPresentationFactory.java

		parent.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));

		PresentationTabFolder folder = new PresentationTabFolder(parent,
				SWT.BORDER, site);
		folder.setMinimumCharacters(getViewMinimumCharsInTab());

		// PresentablePartFolder partFolder = new PresentablePartFolder(folder);

		TabbedStackPresentation result = new TabbedStackPresentation(site,
		/* partFolder */folder, new StandardViewSystemMenu(site));

		DefaultThemeListener themeListener = new DefaultThemeListener(folder,
				result.getTheme());
		result.getTheme().addListener(themeListener);

		return result;
	}

	@Override
	public Sash createSash(Composite parent, int style) {
		Sash sash = super.createSash(parent, style);
		sash.setBackground(Activator.getSharedColor(SharedColors.WINDOW_BG));
		sash.addMouseTrackListener(SASH_HOVER_LISTENER);
		return sash;
	}

	// helping methods
	// ////////////////

	private int getViewMinimumCharsInTab() {
		IPreferenceStore store = PlatformUI.getPreferenceStore();
		int minChars = store
				.getInt(IWorkbenchPreferenceConstants.VIEW_MINIMUM_CHARACTERS);
		return Math.max(0, minChars);
	}

}
